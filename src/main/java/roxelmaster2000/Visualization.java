package roxelmaster2000;

public interface Visualization {

    public boolean isInitialized();
    public void setRoadAt(int x, int y, int direction);
    public void moveCarTo(String id, int x, int y);

}
