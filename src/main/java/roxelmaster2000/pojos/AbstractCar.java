package roxelmaster2000.pojos;

abstract public class AbstractCar {
	Integer id;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public abstract boolean isEmptyCar();
}
