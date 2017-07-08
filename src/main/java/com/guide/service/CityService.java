package com.guide.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.guide.model.City;
import com.guide.repository.CityRepository;

@Service
public class CityService {

	@Autowired
	private CityRepository repository;

	public City findOne(Long id) {
		return repository.findOne(id);
	}

	public List<City> findAll() {
		return repository.findAll();
	}

	public Page<City> findAll(Pageable page) {
		return repository.findAll(page);
	}

	public City save(City city) {
		return repository.save(city);
	}

	public void remove(Long id) {
		repository.delete(id);
	}

}
