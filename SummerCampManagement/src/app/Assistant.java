package app;

import java.util.Date;

public class Assistant {
	private int id;
	private String firstName;
	private String lastName;
	private Date birthDate;
	private boolean requireSpecialAttention;

	public Assistant(int id, String firstName, String lastName, String birthDateString, boolean requireSpecialAttention) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = Utils.parseDate(birthDateString);
		this.requireSpecialAttention = requireSpecialAttention;
	}

	public Assistant() {
		this.id = 0;
		this.firstName = "";
		this.lastName = "";
		this.birthDate = null;
		this.requireSpecialAttention = false;
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

	public Object getBirthDate() {
		return this.birthDate;
	}

	public Object isRequireSpecialAttention() {
		return this.requireSpecialAttention;
	}

}
