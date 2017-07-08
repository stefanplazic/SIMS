package com.guide.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public abstract class User {

	public enum UserStates {
		Active, Reported, Blocked
	};

	@Id
	@GeneratedValue
	private Long id;
	private String username;
	private String pass;
	private Date registrationDate;
	private UserStates userState = UserStates.Active;

	public User() {

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

	public UserStates getUserState() {
		return userState;
	}

	public void setUserState(UserStates userState) {
		this.userState = userState;
	}

}