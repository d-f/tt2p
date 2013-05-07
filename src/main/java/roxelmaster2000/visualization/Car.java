package roxelmaster2000.visualization;

import jgame.JGColor;
import jgame.JGObject;
import jgame.platform.JGEngine;
import roxelmaster2000.Direction;
import roxelmaster2000.pojos.Roxel;

public class Car extends JGObject {

    public boolean manual;
    public JGEngine engine;
    public String id = "";

    public Car(int x, int y, boolean manual, JGEngine engine) {

        super("Car", true, // name
            x * Game.TILE_SIZE,
            y * Game.TILE_SIZE,
            1, // collision ID
            "" // name of sprite or animation to use
        );

        this.engine = engine;
        this.manual = manual;
        setGraphic(sprite("l"));

    }

    public String sprite(String dir) {
        if(manual) {
            return "car_m_" + dir;
        } else {
            return "car_" + dir;
        }
    }


    public void setSprite(int x, int y, Roxel r) {
        id = r.getCar().getId();
        if(manual) {
            if(x > 0) {
                setGraphic(sprite("r"));
            } else if(x < 0) {
                setGraphic(sprite("l"));
            }

            if(y > 0) {
                setGraphic(sprite("d"));
            } else if(y < 0) {
                setGraphic(sprite("u"));
            }
        } else {
            Direction d = ((roxelmaster2000.pojos.Car) r.getCar()).getDirection();
            if(d.equals(Direction.EAST)) {
                setGraphic(sprite("r"));
            } else {
                setGraphic(sprite("d"));
            }

        }
    }


    public void move(int x, int y, Roxel r) {
        setSprite(x, y,r );

        this.x += (x * Game.TILE_SIZE);
        this.y += (y * Game.TILE_SIZE);
    }



    public void paint() {
        engine.setColor(JGColor.yellow);

        engine.drawString(id, x, y, 0);

        //engine.drawOval(x,y,16,16,true,true);
    }


}

