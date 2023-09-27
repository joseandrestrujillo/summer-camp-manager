package app;

import java.util.Date;

public class Assistant {
	private int id;
	private String firstName;
	private String lastName;
	private Date birthDate;
	private boolean requireSpecialAttention;

	public Assistant(int id, String firstName, String lastName, Date birthDate, boolean requireSpecialAttention) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
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

	public void setId(int id2) {
		this.id = id2;
	}

	public void setFirstName(String firstName2) {
		this.firstName = firstName2;
	}

	public void setLastName(String lastName2) {
		this.lastName = lastName2;
	}

	public void setBirthDate(Date birthDate2) {
		this.birthDate = birthDate2;
	}

	public void setRequireSpecialAttention(boolean requireSpecialAttention2) {
		this.requireSpecialAttention = requireSpecialAttention2;
	}

}
