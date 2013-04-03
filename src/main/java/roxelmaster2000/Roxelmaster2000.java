package roxelmaster2000;

import jgame.*;
import jgame.platform.*;

import roxelmaster2000.objects.*;

public class Roxelmaster2000 extends JGEngine {

    public static final int TILE_SIZE = 28;
    public static final int CANVAS_WIDTH = 40;
    public static final int CANVAS_HEIGHT = 20;


    Car car;


    public static void main(String[] args) {
        new Roxelmaster2000(new JGPoint(CANVAS_WIDTH*TILE_SIZE, CANVAS_HEIGHT*TILE_SIZE));
    }


    public Roxelmaster2000(JGPoint size) {
        initEngine(size.x, size.y);
    }


    public void initCanvas() {
        setCanvasSettings(
            CANVAS_WIDTH,  // width of the canvas in tiles
            CANVAS_HEIGHT,  // height of the canvas in tiles
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

        car = new Car(5, 5);

        setTiles(
            2, // tile x index
            2, // tile y index
            new String[]{"#####HHHHHH", "V", "#HHH", "V"}
            // A series of tiles. Each String represents a line of tiles.
        );
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
}
