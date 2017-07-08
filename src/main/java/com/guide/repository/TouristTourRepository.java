package com.guide.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.guide.model.Tour;
import com.guide.model.Tourist;
import com.guide.model.TouristTour;

public interface TouristTourRepository extends JpaRepository<TouristTour, Long> {

	public List<TouristTour> findByTourist(Tourist tourist);

	public List<TouristTour> findByTour(Tour tour);

	@Transactional
	public List<TouristTour> removeByTour(Tour tour);
}
