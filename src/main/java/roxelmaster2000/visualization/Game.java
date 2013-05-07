package roxelmaster2000.visualization;


import jgame.*;
import jgame.platform.*;
import roxelmaster2000.Direction;
import roxelmaster2000.Visualization;
import roxelmaster2000.pojos.Roxel;

import java.util.HashMap;
import java.util.Map;

public class Game extends JGEngine implements Visualization {

    public static final int TILE_SIZE = 28;

    Car car;
    protected int canvasWidth;
    protected int canvasHeight;

    protected boolean initialized = false;

    protected KeyEventReceiver ker;

    protected Map<String, Car> cars;
    protected Map<String, TrafficLight> trafficLights;

    public Game(int canvasWidth, int canvasHeight, KeyEventReceiver ker) {
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        this.cars = new HashMap<String, Car>();
        this.trafficLights = new HashMap<String, TrafficLight>();
        this.ker = ker;
        initEngine(canvasWidth * TILE_SIZE, canvasHeight * TILE_SIZE);
    }


    public void initCanvas() {
        setCanvasSettings(
            canvasWidth,  // width of the canvas in tiles
            canvasHeight,  // height of the canvas in tiles
            TILE_SIZE,          // width of one tile
            TILE_SIZE,          // height of one tile
            JGColor.black,      // foreground colour -> use default colour white
            new JGColor(0, 181, 81), // background colour -> use default colour black
            null // standard font -> use default font
        );
    }


    public void initGame() {
        setFrameRate(10, 2);
        defineMedia("assets.tbl");

        //car = new Car(30, 3);
        initialized = true;
    }


    public void doFrame() {

        if(getKey(KeyLeft)) {
            //car.move(-1, 0);
            ker.onKeyPress(KeyLeft);
        } else if(getKey(KeyRight)) {
            //car.move(1, 0);
            ker.onKeyPress(KeyRight);
        } else if(getKey(KeyUp)) {
            //car.move(0, -1);
            ker.onKeyPress(KeyUp);
        } else if(getKey(KeyDown)) {
            //car.move(0, 1);
            ker.onKeyPress(KeyDown);
        }
    }

    @Override
    public synchronized boolean isInitialized() {
        return initialized;
    }

    @Override
    public synchronized void setRoadAt(int x, int y, int direction, Roxel r) {
        if(direction == (Direction.EAST.value() | Direction.SOUTH.value())) {
            setTile(x, y, "#");
            //System.out.println("Setting traffic light with id " + r.getId());
            trafficLights.put(r.getId(), new TrafficLight(x, y, this));
        }

        else if(direction == Direction.SOUTH.value()) {
            setTile(x, y, "V");
        } else {
            setTile(x, y, "H");
        }



    }

    public void paintFrame() {
        /*int x=50;
        int y=50;
        drawString("testfoo", x, y, 0);
        drawOval(x, y, 10, 10, true, true);*/
    }

    @Override
    public synchronized void moveCarTo(String id, int x, int y, Roxel r) {
        Car car;
        int prevX;
        int prevY;

        if(!cars.containsKey(id)) {
            if(id.equals("manual")) {
                car = new Car(x, y, true, this);
            } else {
                car = new Car(x, y, false, this);
            }

            prevX = x;
            prevY = y;
            cars.put(id, car);

        } else {
            car = cars.get(id);
            prevX = car.getTiles().x;
            prevY = car.getTiles().y;
        }


        int diffX = x - prevX;
        int diffY = y - prevY;


        if(diffX != 0 || diffY != 0) {
            car.move(diffX, diffY, r);
            System.out.println("Moving " + id + " car by " + diffX + ", " + diffY);
        }
    }

    @Override
    public void updateTrafficLight(Roxel r) {
        //System.out.println("Getting traffic light with id " + r.getId());

        int leftState;
        int topState;





        if(r.getDrivingDirection() == Direction.TODECIDE.value()) {
            leftState = TrafficLight.TODECIDE;
            topState = TrafficLight.TODECIDE;
        } else {
            leftState = r.getDrivingDirection() == Direction.EAST.value() ? TrafficLight.GREEN : TrafficLight.RED;
            topState = r.getDrivingDirection() == Direction.SOUTH.value() ? TrafficLight.GREEN : TrafficLight.RED;
        }

        System.out.println("DrivingDir: " + r.getDrivingDirection() + " Left: " + leftState + " Top: " + topState);
        if(trafficLights.get(r.getId()) == null) {
            System.out.println("Didn't find traffic light for roxel");
        } else {
            trafficLights.get(r.getId()).drawTrafficLights(leftState, topState);
        }
    }
}
