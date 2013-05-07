package roxelmaster2000;

import roxelmaster2000.pojos.Roxel;

public interface Visualization {

    public boolean isInitialized();
    public void setRoadAt(int x, int y, int direction, Roxel r);
    public void moveCarTo(String id, int x, int y, Roxel r);
    public void updateTrafficLight(Roxel r);

}
