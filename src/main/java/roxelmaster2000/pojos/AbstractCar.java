package roxelmaster2000.pojos;

import java.io.Serializable;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceRouting;

@SpaceClass
abstract public class AbstractCar implements Serializable {
	String id;
	
	@SpaceId(autoGenerate=true)
    @SpaceRouting
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public abstract boolean getEmpty();
}
