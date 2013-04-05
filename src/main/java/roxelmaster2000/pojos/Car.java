package roxelmaster2000.pojos;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: lucas
 * Date: 4/4/13
 * Time: 3:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class Car implements Serializable {
	Integer id;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
}
