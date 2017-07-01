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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Event {

	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String description;
	private double price;
	private Date date;

	@ManyToMany(mappedBy = "events")
	private Set<Tour> tours = new HashSet<Tour>();

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	private Guide guide;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "locationInfo_id")
	private LocationInfo locInfo;

	public Event() {
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Set<Tour> getTours() {
		return tours;
	}

	public void setTours(Set<Tour> tours) {
		this.tours = tours;
	}

	public Guide getGuide() {
		return guide;
	}

	public void setGuide(Guide guide) {
		this.guide = guide;
	}

	public LocationInfo getLocInfo() {
		return locInfo;
	}

	public void setLocInfo(LocationInfo locInfo) {
		this.locInfo = locInfo;
	}

}
