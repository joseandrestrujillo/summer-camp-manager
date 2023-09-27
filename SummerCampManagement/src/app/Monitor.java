package app;

public class Monitor {
	private int id;
	private String firstName;
	private String lastName;
	private boolean SpecialEducator;
	
	
	public Monitor() {
		this.id= 0;
		this.firstName = "";
		this.lastName = "";
		this.SpecialEducator = false;
	}
	
	public Monitor(int id, String firstName, String lastName, boolean SpecialEducator) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.SpecialEducator = SpecialEducator;
		
	}
	
	public Integer getId() {
		return this.id;
	}
	
	public Object getFirstName() {
		return this.firstName;
	}
	
	public Object getLastName() {
		return this.lastName;
	}
	
	public Object isSpecialEducator() {
		return this.SpecialEducator;
	}
	
	public void setId(int id2) {
		this.id = id2;
	}
	
	public void setFirstName(String firstName2) {
		this.firstName = firstName2;
	}
	
	public void setLastName(String lastName2) {
		this.lastName = lastName2;
	}
	
	public void setSpecialEducator(boolean SpecialEducator2) {
		this.SpecialEducator = SpecialEducator2;
		
	}
	
	public String toString() {
		return "{id: " 
				+ this.id 
				+ ", firstName: '"
				+ this.firstName
				+ "', lastName: '"
				+ this.lastName 
				+ ", isSpecialEducator: "
				+ this.SpecialEducator + "}";
		
	}
		
}

	

