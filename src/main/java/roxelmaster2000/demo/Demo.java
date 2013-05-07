package roxelmaster2000.demo;

import com.j_spaces.map.MapEntryFactory;
import jgame.platform.JGEngine;
import org.openspaces.core.GigaSpace;
import com.j_spaces.core.client.SQLQuery;

import roxelmaster2000.*;
import roxelmaster2000.spaces.SpacesUtility;
import roxelmaster2000.pojos.Roxel;
import roxelmaster2000.visualization.*;

import java.awt.*;
import java.awt.geom.Point2D;

public class Demo implements KeyEventReceiver {

    public Visualization vis;

    public static final int CANVAS_WIDTH = 40;
    public static final int CANVAS_HEIGHT = 20;
    public Roxel manualRoxel;
    public GigaSpace gs;

    public static void main(String[] args) {
        new Demo();
    }

    // Type is one of JGEngine.Key* constants
    public void onKeyPress(int type) {
        int rX = 0,
            rY = 0;
        if(type == JGEngine.KeyLeft) {
            rX = -1;
        } else if(type == JGEngine.KeyRight) {
            rX = 1;
        } else if(type == JGEngine.KeyUp) {
            rY = -1;
        } else if(type == JGEngine.KeyDown) {
            rY = 1;
        }

        System.out.println("===> Trying to move car to " + rX + ", " + rY);
        Roxel r = SpacesUtility.moveCar(gs, manualRoxel, CANVAS_WIDTH, CANVAS_HEIGHT, rX, rY);
        if(r != null) {
            manualRoxel = r;
        } else {
            System.err.println("Couldn't move manual car :(");
        }
    }

    public Demo() {
        System.out.println("Starting visualization...");
        GigaSpace gs = SpacesUtility.getGigaspace();
        this.gs = gs;


        final Demo demo = this;
        // Start JGame in separate thread
        (new Thread() {
            public void run() {
                vis = new Game(CANVAS_WIDTH, CANVAS_HEIGHT, demo);
            }
        }).start();

        // Wait for engine to be initialized
        while(vis == null || !vis.isInitialized()) {
            try {
                Thread.sleep(100);
            } catch(InterruptedException e) { /* So don't care if I'm interrupted! */ }
        }


        //Point pManual = null;
        Roxel[] roxels = gs.readMultiple(new SQLQuery<Roxel>(Roxel.class, ""));
        for(Roxel r : roxels) {
            if(r.getCar().getId() != null && r.getCar().getId().equals("manual")) {
                //pManual = new Point(r.getX(), r.getY());
                manualRoxel = r;
            }
            vis.setRoadAt(r.getX(), r.getY(), r.getDirection(), r);
        }

        if(manualRoxel == null) {
            System.err.println("Did not find manual car :(");
        } else {
            System.out.println("Found manual car at x,y: " + manualRoxel.getX() + ", " + manualRoxel.getY());
        }


        // Simulation update loop
        while(true) {
            SQLQuery<Roxel> query = new SQLQuery<Roxel>(Roxel.class, "");
            Roxel[] roxel = gs.readMultiple(query);

            System.out.println("About to draw " + roxel.length + " Roxels!");
            int nullCars = 0;
            for(Roxel r : roxel) {
                // Kreuzung
                if(r.getDirection() == (Direction.EAST.value() | Direction.SOUTH.value())) {
                    vis.updateTrafficLight(r);
                }

                if(r.getCar() == null || r.getCar().getEmpty()) { nullCars++; continue; }
                vis.moveCarTo(r.car.getId(), r.getX(), r.getY(), r);
            }
            System.out.println("There where " + nullCars + " nullCars");

            try {
                Thread.sleep(1000);
            } catch(InterruptedException e) {}
        }

    }

}
