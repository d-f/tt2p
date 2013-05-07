package roxelmaster2000.visualization;

import jgame.JGColor;
import jgame.JGObject;
import jgame.platform.JGEngine;

/**
 * Created with IntelliJ IDEA.
 * User: lucas
 * Date: 5/7/13
 * Time: 3:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class TrafficLight extends JGObject {

    private JGEngine engine;
    private int leftState;
    private int topState;
    private int x;
    private int y;

    public static final int INACTIVE = 1;
    public static final int RED = 2;
    public static final int GREEN = 4;
    public static final int TODECIDE = 8;




    public TrafficLight(int x, int y, JGEngine engine) {
        super("Car", true, // name
            x * Game.TILE_SIZE,
            y * Game.TILE_SIZE,
            1, // collision ID
            null // name of sprite or animation to use
        );



        this.engine = engine;
        this.leftState = INACTIVE;
        this.topState = INACTIVE;
        this.x = x;
        this.y = y;
    }




    public void paint() {
        drawVerticalTrafficLight(x, y, this.leftState);
        drawHorizontalTrafficLight(x, y, this.topState);
    }

    public void drawTrafficLights(int leftState, int topState) {
        System.out.println("Left: " + leftState + " Top: " + topState);
        this.leftState = leftState;
        this.topState = topState;
    }

    public void drawVerticalTrafficLight(int x, int y, int state) {
        setColors(true, state);

        // Upper light
        engine.drawOval(
            (x-0.25) * Game.TILE_SIZE,
            (y+0.35) * Game.TILE_SIZE,
            Game.TILE_SIZE * 0.25,
            Game.TILE_SIZE * 0.25,
            true, true
        );

        setColors(false, state);

        // lower light
        engine.drawOval(
            (x-0.25) * Game.TILE_SIZE,
            (y+0.65) * Game.TILE_SIZE,
            Game.TILE_SIZE * 0.25,
            Game.TILE_SIZE * 0.25,
            true, true
        );
    }

    public void drawHorizontalTrafficLight(int x, int y, int state) {
        setColors(true, state);

        // Upper light
        engine.drawOval(
            (x+0.65) * Game.TILE_SIZE,
            (y-0.25) * Game.TILE_SIZE,
            Game.TILE_SIZE * 0.25,
            Game.TILE_SIZE * 0.25,
            true, true
        );

        setColors(false, state);

        // lower light
        engine.drawOval(
            (x+0.35) * Game.TILE_SIZE,
            (y-0.25) * Game.TILE_SIZE,
            Game.TILE_SIZE * 0.25,
            Game.TILE_SIZE * 0.25,
            true, true
        );
    }


    // Upper = true if setting colors for upper traffic light bulb
    public void setColors(boolean upper, int state) {



        if(upper) {

            if(state == INACTIVE || state == GREEN) {
                engine.setColor(JGColor.grey);
            } else if(state == RED) {
                engine.setColor(JGColor.red);
            } else if(state == TODECIDE) {
                engine.setColor(JGColor.blue);
            } else {
                engine.setColor(JGColor.pink);
            }
        } else {

            if(state == RED || state == INACTIVE) {
                engine.setColor(JGColor.grey);
            } else if(state == GREEN) {
                engine.setColor(JGColor.green);
            } else if(state == TODECIDE) {
                engine.setColor(JGColor.blue);
            } else {
                engine.setColor(JGColor.pink);
            }
        }

    }







}
