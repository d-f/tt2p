package roxelmaster2000.demo;

import org.openspaces.core.GigaSpace;
import com.j_spaces.core.client.SQLQuery;

import roxelmaster2000.*;
import roxelmaster2000.spaces.SpacesUtility;
import roxelmaster2000.pojos.Roxel;
import roxelmaster2000.visualization.*;

public class Demo {

    public Visualization vis;

    public static final int CANVAS_WIDTH = 40;
    public static final int CANVAS_HEIGHT = 20;

    public static void main(String[] args) {
        new Demo();
    }

    public Demo() {
        GigaSpace gs = SpacesUtility.getGigaspace();

        // Start JGame in separate thread
        (new Thread() {
            public void run() {
                vis = new Game(CANVAS_WIDTH, CANVAS_HEIGHT);
            }
        }).start();

        // Wait for engine to be initialized
        while(vis == null || !vis.isInitialized()) {
            try {
                Thread.sleep(100);
            } catch(InterruptedException e) { /* So don't care if I'm interrupted! */ }
        }


        Roxel[] roxels = gs.readMultiple(new SQLQuery<Roxel>(Roxel.class, ""));
        for(Roxel r : roxels) {
            vis.setRoadAt(r.getX(), r.getY(), r.getDirection());
        }


        // Simulation update loop
        while(true) {
            SQLQuery<Roxel> query = new SQLQuery<Roxel>(Roxel.class, "");
            Roxel[] roxel = gs.readMultiple(query);

            System.out.println("About to draw " + roxel.length + " Roxels!");
            int nullCars = 0;
            for(Roxel r : roxel) {
                if(r.getCar() == null || r.getCar().getEmpty()) { nullCars++; continue; }
                vis.moveCarTo(r.car.getId(), r.getX(), r.getY());
            }
            System.out.println("There where " + nullCars + " nullCars");

            try {
                Thread.sleep(1000);
            } catch(InterruptedException e) {}
        }

    }

}
