package com.guide.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.guide.model.Tourist;
import com.guide.repository.TouristRepository;

@Service
public class TouristService {

	@Autowired
	private TouristRepository repository;

	public Tourist findOne(Long id) {
		return repository.findOne(id);
	}

	public List<Tourist> findAll() {
		return repository.findAll();
	}

	public Page<Tourist> findAll(Pageable page) {
		return repository.findAll(page);
	}

	public Tourist save(Tourist tourist) {
		return repository.save(tourist);
	}

	public void remove(Long id) {
		repository.delete(id);
	}

}
