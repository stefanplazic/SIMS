package com.guide.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.guide.model.Guide;
import com.guide.model.Tour;
import com.guide.repository.TourRepository;

@Service
public class TourService {

	@Autowired
	private TourRepository repository;

	public Tour findOne(Long id) {
		return repository.findOne(id);
	}

	public List<Tour> findAll() {
		return repository.findAll();
	}

	public Page<Tour> findAll(Pageable page) {
		return repository.findAll(page);
	}

	public Tour save(Tour tour) {
		return repository.save(tour);
	}

	public void remove(Long id) {
		repository.delete(id);
	}

	public List<Tour> findByGuide(Guide guide) {
		return repository.findByGuide(guide);
	}

}
