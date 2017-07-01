package com.guide.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class Tourist extends Person {

	@OneToMany(mappedBy = "tourist", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private Set<TouristTour> tours = new HashSet<TouristTour>();

	public Tourist() {

	}

	public Set<TouristTour> getTours() {
		return tours;
	}

	public void setTours(Set<TouristTour> tours) {
		this.tours = tours;
	}

}
