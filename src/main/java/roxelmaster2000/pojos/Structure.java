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
	
	public int id;
	
	@SpaceId(autoGenerate=false)
    @SpaceRouting
    public Integer getId() { return id;}
    
    public void setId(Integer id) {  this.id = id; }
}
