package byu.codemonkeys.tickettoride.shared.model.map;


public class City {
	
	// region Properties
	// region Name Property
	private String name;
	
	public City(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	// endregion
	
	// region ID Property
	private int ID;
	
	public int getID() {
		return ID;
	}
	
	public void setID(int ID) {
		this.ID = ID;
	}
	// endregion
	// endregion
	
	public City(int ID, String name) {
		this.name = name;
		this.ID = ID;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		
		if (this == o) {
			return true;
		}
		
		if (getClass() != o.getClass()) {
			return false;
		}
		
		City other = (City) o;
		
		return this.name.equals(other.getName());
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
