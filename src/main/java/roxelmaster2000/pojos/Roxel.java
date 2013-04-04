package roxelmaster2000.pojos;

import roxelmaster2000.*;

/**
 * Created with IntelliJ IDEA.
 * User: lucas
 * Date: 4/4/13
 * Time: 3:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class Roxel {

    public int id;
    public int x;
    public int y;

    // Being an int from or'd values from the Direction enum
    // int test = 0 | Direction.EAST.value() | Direction.NORTH.value();
    public int direction;

    // May be an EmptyCar
    // http://wiki.gigaspaces.com/wiki/display/XAP95/Modeling+your+data -> Embedded Model
    public Car car;

}
