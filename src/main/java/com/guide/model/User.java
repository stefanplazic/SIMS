package com.guide.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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

	@OneToMany(mappedBy = "blockRequester", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private Set<UserReport> requestedReports = new HashSet<UserReport>();

	@OneToMany(mappedBy = "accused", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private Set<UserReport> accused = new HashSet<UserReport>();

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

	public Set<UserReport> getRequestedReports() {
		return requestedReports;
	}

	public void setRequestedReports(Set<UserReport> requestedReports) {
		this.requestedReports = requestedReports;
	}

	public Set<UserReport> getAccused() {
		return accused;
	}

	public void setAccused(Set<UserReport> accused) {
		this.accused = accused;
	}

	public UserStates getUserState() {
		return userState;
	}

	public void setUserState(UserStates userState) {
		this.userState = userState;
	}

}