package roxelmaster2000.pojos;

import java.io.Serializable;

import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceRouting;

/**
 * Created with IntelliJ IDEA.
 * User: lucas
 * Date: 4/4/13
 * Time: 3:55 PM
 * To change this template use File | Settings | File Templates.
 */

public class Car extends AbstractCar {
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
		return false;
	}
	
}
