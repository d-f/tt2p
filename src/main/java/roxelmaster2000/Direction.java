package roxelmaster2000;

import java.io.Serializable;

import com.gigaspaces.annotation.pojo.SpaceClass;

@SpaceClass
public enum Direction implements  Serializable {
    NORTH(1), SOUTH(2), WEST(4), EAST(8), TODECIDE(16);

    private int code;

    Direction(int i) {
        this.code = i;
    }
    
    static public Direction valueOf(int i) {
    	if ((i & Direction.EAST.value()) != 0) {
    		return Direction.EAST;
    	} else if ((i & Direction.WEST.value()) != 0) {
    		return Direction.WEST;
    	} else if ((i & Direction.SOUTH.value()) != 0) {
    		return Direction.SOUTH;
    	} else if ((i & Direction.NORTH.value()) != 0){
    		return Direction.NORTH;
    	} else {
    		return Direction.TODECIDE;
    	}
    }

    public int value() {
        return code;
    }
}
