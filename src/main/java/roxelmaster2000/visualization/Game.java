package roxelmaster2000.visualization;


import jgame.*;
import jgame.platform.*;
import roxelmaster2000.Direction;
import roxelmaster2000.Visualization;

import java.util.HashMap;
import java.util.Map;

public class Game extends JGEngine implements Visualization {

    public static final int TILE_SIZE = 28;

    Car car;
    protected int canvasWidth;
    protected int canvasHeight;

    protected boolean initialized = false;

    protected Map<String, Car> cars;

    public Game(int canvasWidth, int canvasHeight) {
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        this.cars = new HashMap<String, Car>();
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
        setFrameRate(60, 2);
        defineMedia("assets.tbl");

        car = new Car(30, 3);
        initialized = true;
    }


    public void doFrame() {

        if(getKey(KeyLeft)) {
            car.move(-1, 0);
        } else if(getKey(KeyRight)) {
            car.move(1, 0);
        } else if(getKey(KeyUp)) {
            car.move(0, -1);
        } else if(getKey(KeyDown)) {
            car.move(0, 1);
        }
    }

    @Override
    public synchronized boolean isInitialized() {
        return initialized;
    }

    @Override
    public synchronized void setRoadAt(int x, int y, int direction) {


        if(direction == (Direction.EAST.value() | Direction.SOUTH.value())) {
            setTile(x, y, "#");
        }

        else if(direction == Direction.SOUTH.value()) {
            setTile(x, y, "V");
        } else {
            setTile(x, y, "H");
        }

    }

    @Override
    public synchronized void moveCarTo(String id, int x, int y) {
        Car car;
        int prevX;
        int prevY;

        if(!cars.containsKey(id)) {
            car = new Car(x, y);
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
            car.move(diffX, diffY);
            System.out.println("Moving " + id + " car by " + diffX + ", " + diffY);
        }
    }
}
