package roxelmaster2000;

public enum Direction {
    NORTH(1), SOUTH(2), WEST(4), EAST(8);

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
    	} else {
    		return Direction.NORTH;
    	}
    }

    public int value() {
        return code;
    }
}
