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
}

