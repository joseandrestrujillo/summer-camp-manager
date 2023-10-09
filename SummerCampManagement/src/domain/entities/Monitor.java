package domain.entities;

public class Monitor {
	private int id;
	private String firstName;
	private String lastName;
	private boolean specialEducator;
	
	
	public Monitor() {
		this.id= 0;
		this.firstName = "";
		this.lastName = "";
		this.specialEducator = false;
	}
	
	public Monitor(int id, String firstName, String lastName, boolean SpecialEducator) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.specialEducator = SpecialEducator;
		
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
		return this.specialEducator;
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
		this.specialEducator = SpecialEducator2;
		
	}
	
	public String toString() {
		return "{id: " 
				+ this.id 
				+ ", firstName: '"
				+ this.firstName
				+ "', lastName: '"
				+ this.lastName 
				+ "', isSpecialEducator: "
				+ this.specialEducator + "}";
		
	}
		
}

	

