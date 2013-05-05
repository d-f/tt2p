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
    public String id;
    public Integer x;
    public Integer y;

    // Being an int from or'd values from the Direction enum
    // int test = 0 | Direction.EAST.value() | Direction.NORTH.value();
    public Integer direction;
    
    // Allowed driving direction, cars return TODECIDE on junctions so traffic lights can decide
    public DrivingDirection drivingDirection;

    public DrivingDirection getDrivingDirection() {
		return drivingDirection;
	}

	public void setDrivingDirection(DrivingDirection drivingDirection) {
		this.drivingDirection = drivingDirection;
	}

	// May be an EmptyCar
    // http://wiki.gigaspaces.com/wiki/display/XAP95/Modeling+your+data -> Embedded Model
    public AbstractCar car;
    
    @SpaceId(autoGenerate=true)
    @SpaceRouting
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@SpaceIndex
	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}
	
	@SpaceIndex
	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Integer getDirection() {
		return direction;
	}

	public void setDirection(Integer direction) {
		this.direction = direction;
	}

	@SpaceIndex(path = "id")
	public AbstractCar getCar() {
		return car;
	}

	public void setCar(AbstractCar car) {
		this.car = car;
	}
	
	public String toString() {
		return "Roxel(" + id + ", " + x + ", " + y + ", " + direction + ", " + car + ")";
	}
}
