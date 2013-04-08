package roxelmaster2000.pojos;

import com.gigaspaces.annotation.pojo.*;

@SpaceClass
public class Structure {
	// map dimensions in roxel
	public int width;
	public int height;
	
	// size of a roxel in meter
	public int roxelMeters;
	
	// random seed
	public int seed;
	
	public String id;
	
	@SpaceId(autoGenerate=true)
    @SpaceRouting
    public String getId() { return id;}
	
	public void setId(String id) {  this.id = id; }
    
    public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getRoxelMeters() {
		return roxelMeters;
	}

	public void setRoxelMeters(int roxelMeters) {
		this.roxelMeters = roxelMeters;
	}

	public int getSeed() {
		return seed;
	}

	public void setSeed(int seed) {
		this.seed = seed;
	}
}
