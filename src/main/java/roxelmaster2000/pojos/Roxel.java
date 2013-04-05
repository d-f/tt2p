package roxelmaster2000.pojos;

import com.gigaspaces.annotation.pojo.*;

import roxelmaster2000.*;

/**
 * Created with IntelliJ IDEA.
 * User: lucas
 * Date: 4/4/13
 * Time: 3:55 PM
 * To change this template use File | Settings | File Templates.
 */
@SpaceClass
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

    @SpaceId(autoGenerate=false)
    @SpaceRouting
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@SpaceIndex
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
	
	@SpaceIndex
	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	@SpaceIndex(path = "id")
	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}
}
