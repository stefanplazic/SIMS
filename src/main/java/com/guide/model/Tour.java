package com.guide.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Tour {

	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private Date beginDate;
	private Date endDate;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "tour_event", joinColumns = @JoinColumn(name = "tour_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"))
	private Set<Event> events = new HashSet<Event>();

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	private Guide guide;

	@OneToMany(mappedBy = "tour", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private Set<TouristTour> tours = new HashSet<TouristTour>();

	public Tour() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Set<Event> getEvents() {
		return events;
	}

	public void setEvents(Set<Event> events) {
		this.events = events;
	}

	public Guide getGuide() {
		return guide;
	}

	public void setGuide(Guide guide) {
		this.guide = guide;
	}

	public Set<TouristTour> getTours() {
		return tours;
	}

	public void setTours(Set<TouristTour> tours) {
		this.tours = tours;
	}

}
