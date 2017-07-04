package com.guide.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.guide.model.Event;
import com.guide.model.Guide;
import com.guide.repository.EventRepository;

@Service
public class EventService {

	@Autowired
	private EventRepository repository;

	public Event findOne(Long id) {
		return repository.findOne(id);
	}

	public List<Event> findAll() {
		return repository.findAll();
	}

	public Page<Event> findAll(Pageable page) {
		return repository.findAll(page);
	}

	public Event save(Event event) {
		return repository.save(event);
	}

	public void remove(Long id) {
		repository.delete(id);
	}

	public List<Event> findByPrice(double price) {
		return repository.findByPrice(price);
	}

	public List<Event> findByDate(Date date) {
		return repository.findByDate(date);
	}

	public List<Event> findByGuide(Guide guide) {
		return repository.findByGuide(guide);
	}

}
