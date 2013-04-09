package roxelmaster2000.demo;

import org.openspaces.core.GigaSpace;
import roxelmaster2000.*;
import roxelmaster2000.pojos.*;
import roxelmaster2000.spaces.SpacesUtility;

import java.util.*;

import org.openspaces.core.GigaSpace;

import com.j_spaces.core.client.SQLQuery;

import roxelmaster2000.Direction;
import roxelmaster2000.pojos.Roxel;
import roxelmaster2000.pojos.Structure;
import roxelmaster2000.spaces.SpacesUtility;

import roxelmaster2000.visualization.*;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Demo {

    public Visualization vis;

    public static final int CANVAS_WIDTH = 40;
    public static final int CANVAS_HEIGHT = 20;

    public static final int H_ROADS = 10;
    public static final int V_ROADS = 10;

    public static void main(String[] args) {
        new Demo();
    }

    public Demo() {



        GigaSpace gs = SpacesUtility.getGigaspace();


        (new Thread() {
            public void run() {
                vis = new Game(CANVAS_WIDTH, CANVAS_HEIGHT);
            }
        }).start();


        while(vis == null || !vis.isInitialized()) {
            try {
                Thread.sleep(100);
            } catch(InterruptedException e) {
                // So don't care if I'm interrupted!
            }
        }


        ArrayList<Roxel> roxels = new ArrayList<Roxel>(Arrays.asList(gs.readMultiple(new SQLQuery<Roxel>(Roxel.class, ""))));

        for(Roxel r : roxels) {
            vis.setRoadAt(r.getX(), r.getY(), r.getDirection());
        }


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








        /*Random rand = new Random();
        Set<Integer> horizontals = new HashSet<Integer>();
        while(horizontals.size() < H_ROADS) {
            horizontals.add(rand.nextInt(CANVAS_HEIGHT));
        }

        Set<Integer> verticals = new HashSet<Integer>();
        while(verticals.size() < V_ROADS) {
            verticals.add(rand.nextInt(CANVAS_WIDTH));
        }

        for(int v : verticals) {
            for(int h=0; h<CANVAS_HEIGHT; h++) {
                vis.setRoadAt(v, h, Direction.NORTH);
            }
        }

        for(int h : horizontals) {
            for(int w=0; w<CANVAS_WIDTH; w++) {
                vis.setRoadAt(w, h, Direction.WEST);
            }
        }

        /*for(int w=0; w<CANVAS_WIDTH; w++) {
            for(int h=0; h<CANVAS_HEIGHT; h++) {
                vis.setRoadAt(w, h, Direction.NORTH);
            }
        }*/




    }

}
