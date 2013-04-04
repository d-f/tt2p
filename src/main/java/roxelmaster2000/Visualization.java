package roxelmaster2000;

public interface Visualization {

    public boolean isInitialized();
    public void setRoadAt(int x, int y, Direction direction);
    public void createCarAt(int id, int x, int y);
    public void moveCarTo(int id, int x, int y);

}
