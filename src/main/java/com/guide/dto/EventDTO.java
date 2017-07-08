package com.guide.dto;

import java.util.Date;

import com.guide.model.Event;

public class EventDTO {

	private Long id;
	private String name;
	private String description;
	private double price;
	private Date date;
	private LocationInfoDTO info;

	public EventDTO() {
		// TODO Auto-generated constructor stub
	}

	public EventDTO(Event e) {
		this.id = e.getId();
		this.name = e.getName();
		this.description = e.getDescription();
		this.price = e.getPrice();
		this.date = e.getDate();
		this.info = new LocationInfoDTO(e.getLocInfo());
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

	public LocationInfoDTO getInfo() {
		return info;
	}

	public void setInfo(LocationInfoDTO info) {
		this.info = info;
	}

}
