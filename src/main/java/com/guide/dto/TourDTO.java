package com.guide.dto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.guide.model.Comment;
import com.guide.model.Event;
import com.guide.model.Tour;

public class TourDTO {

	private Long id;
	private String name;
	private Date beginDate;
	private Date endDate;
	private List<EventDTO> events = new ArrayList<EventDTO>();
	private boolean isActive = false;
	private String bgDate;
	private String enDate;
	private List<CommentDTO> comments = new ArrayList<CommentDTO>();
	private boolean isApp = false;

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
		if (beginDate.compareTo(new Date()) > 0)
			this.isActive = true;
		// format dates
		SimpleDateFormat endFor = new SimpleDateFormat(" dd/MM/yyyy");
		this.bgDate = endFor.format(beginDate);
		this.enDate = endFor.format(endDate);

		// set comments
		for (Comment c : tour.getComments()) {
			comments.add(new CommentDTO(c));
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

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getBgDate() {
		return bgDate;
	}

	public void setBgDate(String bgDate) {
		this.bgDate = bgDate;
	}

	public String getEnDate() {
		return enDate;
	}

	public void setEnDate(String enDate) {
		this.enDate = enDate;
	}

	public List<CommentDTO> getComments() {
		return comments;
	}

	public void setComments(List<CommentDTO> comments) {
		this.comments = comments;
	}

	public boolean isApp() {
		return isApp;
	}

	public void setApp(boolean isApp) {
		this.isApp = isApp;
	}

}
