package roxelmaster2000.objects;

import jgame.JGObject;
import roxelmaster2000.Roxelmaster2000;

public class Car extends JGObject {

    public Car(int x, int y) {
        super("Car", true, // name
            x * Roxelmaster2000.TILE_SIZE,
            y * Roxelmaster2000.TILE_SIZE,
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

        this.x += (x * Roxelmaster2000.TILE_SIZE);
        this.y += (y * Roxelmaster2000.TILE_SIZE);
    }
}

