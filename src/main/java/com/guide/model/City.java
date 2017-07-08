package com.guide.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class City {

	@Id
	@GeneratedValue
	private Long id;
	private String name;

	@OneToMany(mappedBy = "city", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private Set<LocationInfo> locationInfos = new HashSet<LocationInfo>();

	public City() {
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

	public Set<LocationInfo> getLocationInfos() {
		return locationInfos;
	}

	public void setLocationInfos(Set<LocationInfo> locationInfos) {
		this.locationInfos = locationInfos;
	}

}
