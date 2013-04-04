package roxelmaster2000.visualization;

import jgame.JGObject;

public class Car extends JGObject {

    public Car(int x, int y) {
        super("Car", true, // name
            x * Game.TILE_SIZE,
            y * Game.TILE_SIZE,
            1, // collision ID
            "car_l" // name of sprite or animation to use
        );
    }


    public void move(int x, int y) {
        if(x > 0) {
            setGraphic("car_r");
        } else if(x < 0) {
            setGraphic("car_l");
        }

        if(y > 0) {
            setGraphic("car_d");
        } else if(y < 0) {
            setGraphic("car_u");
        }

        this.x += (x * Game.TILE_SIZE);
        this.y += (y * Game.TILE_SIZE);
    }
}

