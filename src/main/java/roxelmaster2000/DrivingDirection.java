package roxelmaster2000;

import java.io.Serializable;

import com.gigaspaces.annotation.pojo.SpaceClass;

@SpaceClass
public enum DrivingDirection implements Serializable {
	SOUTH, EAST, TODECIDE;
}
