package com.guide.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.guide.model.Guide;
import com.guide.model.Person;
import com.guide.repository.GuideRepository;

@Service
public class GuideService {

	@Autowired
	private GuideRepository repository;

	public Guide findOne(Long id) {
		return repository.findOne(id);
	}

	public List<Guide> findAll() {
		return repository.findAll();
	}

	public Page<Guide> findAll(Pageable page) {
		return repository.findAll(page);
	}

	public Person save(Guide user) {
		return repository.save(user);
	}

	public void remove(Long id) {
		repository.delete(id);
	}
}
