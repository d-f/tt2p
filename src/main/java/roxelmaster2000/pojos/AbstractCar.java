package roxelmaster2000.pojos;

import java.io.Serializable;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceRouting;

@SpaceClass
abstract public class AbstractCar implements Serializable {
	public abstract String getId();
	public abstract boolean getEmpty();
}
