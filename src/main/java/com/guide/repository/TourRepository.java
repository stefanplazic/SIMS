package com.guide.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guide.model.Guide;
import com.guide.model.Tour;

public interface TourRepository extends JpaRepository<Tour, Long> {

	public List<Tour> findByGuide(Guide guide);
}
