package com.guide.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class Guide extends Person {

	@OneToMany(mappedBy = "guide", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private Set<Event> events = new HashSet<Event>();

	@OneToMany(mappedBy = "guide", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private Set<Tour> tours = new HashSet<Tour>();

	public Guide() {

	}

	public Set<Event> getEvents() {
		return events;
	}

	public void setEvents(Set<Event> events) {
		this.events = events;
	}

	public Set<Tour> getTours() {
		return tours;
	}

	public void setTours(Set<Tour> tours) {
		this.tours = tours;
	}

}
