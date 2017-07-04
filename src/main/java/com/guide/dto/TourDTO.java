package com.guide.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.guide.model.Event;
import com.guide.model.Tour;

public class TourDTO {

	private Long id;
	private String name;
	private Date beginDate;
	private Date endDate;
	private List<EventDTO> events = new ArrayList<EventDTO>();

	public TourDTO() {

	}

	public TourDTO(Tour tour) {
		this.id = tour.getId();
		this.name = tour.getName();
		this.beginDate = tour.getBeginDate();
		this.endDate = tour.getEndDate();
		for (Event e : tour.getEvents()) {
			this.events.add(new EventDTO(e));
		}
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

	public List<EventDTO> getEvents() {
		return events;
	}

	public void setEvents(List<EventDTO> events) {
		this.events = events;
	}

}
