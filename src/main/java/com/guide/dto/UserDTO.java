package com.guide.dto;

import java.util.Date;

import com.guide.model.Person;
import com.guide.model.User;

public class UserDTO {

	private Long id;
	private String username;
	private String pass;
	private Date registrationDate;
	private String firstName;
	private String lastName;

	public UserDTO() {

	}
	
	public UserDTO(User user) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.registrationDate = user.getRegistrationDate();
		this.firstName = ((Person) user).getFirstName();
		this.lastName = ((Person) user).getLastName();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
