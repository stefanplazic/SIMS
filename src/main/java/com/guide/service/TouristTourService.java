package com.guide.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.guide.model.Tour;
import com.guide.model.Tourist;
import com.guide.model.TouristTour;
import com.guide.repository.TouristTourRepository;

@Service
public class TouristTourService {

	@Autowired
	private TouristTourRepository repository;

	public TouristTour findOne(Long id) {
		return repository.findOne(id);
	}

	public List<TouristTour> findAll() {
		return repository.findAll();
	}

	public Page<TouristTour> findAll(Pageable page) {
		return repository.findAll(page);
	}

	public TouristTour save(TouristTour touristTour) {
		return repository.save(touristTour);
	}

	public void remove(Long id) {
		repository.delete(id);
	}

	public List<TouristTour> findByTourist(Tourist tourist) {
		return repository.findByTourist(tourist);
	}

	public List<TouristTour> findByTour(Tour tour) {
		return repository.findByTour(tour);
	}

}
