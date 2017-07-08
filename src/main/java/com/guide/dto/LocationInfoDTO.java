package com.guide.dto;

import com.guide.model.LocationInfo;

public class LocationInfoDTO {

	private Long id;
	private String adress;
	private String postalCode;

	public LocationInfoDTO() {

	}

	public LocationInfoDTO(LocationInfo info) {
		this.id = info.getId();
		this.adress = info.getAdress();
		this.postalCode = info.getPostalCode();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

}
