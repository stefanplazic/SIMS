package com.guide.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.guide.model.LocationInfo;
import com.guide.repository.LocationInfoRepository;

@Service
public class LocationInfoService {

	@Autowired
	private LocationInfoRepository repository;

	public LocationInfo findOne(Long id) {
		return repository.findOne(id);
	}

	public List<LocationInfo> findAll() {
		return repository.findAll();
	}

	public Page<LocationInfo> findAll(Pageable page) {
		return repository.findAll(page);
	}

	public LocationInfo save(LocationInfo info) {
		return repository.save(info);
	}

	public void remove(Long id) {
		repository.delete(id);
	}
}
