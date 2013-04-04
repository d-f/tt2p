package roxelmaster2000;

public enum Direction {
    NORTH(1), SOUTH(2), WEST(4), EAST(8);

    private int code;

    Direction(int i) {
        this.code = i;
    }

    public int value() {
        return code;
    }
}
