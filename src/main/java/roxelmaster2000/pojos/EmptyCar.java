package roxelmaster2000.pojos;

import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceRouting;

public class EmptyCar extends AbstractCar {
	String id;
	
	@SpaceId(autoGenerate=true)
    @SpaceRouting
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public boolean getEmpty() {
		return true;
	}

}
