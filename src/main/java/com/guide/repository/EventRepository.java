package com.guide.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guide.model.Event;
import com.guide.model.Guide;

public interface EventRepository extends JpaRepository<Event, Long> {

	public List<Event> findByPrice(double price);

	public List<Event> findByDate(Date date);

	public List<Event> findByGuide(Guide guide);
}
