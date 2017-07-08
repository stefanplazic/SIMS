package com.guide.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.guide.model.Person;
import com.guide.repository.PersonRepository;

@Service
public class PersonService {

	@Autowired
	private PersonRepository repository;

	public Person findOne(Long id) {
		return repository.findOne(id);
	}

	public List<Person> findAll() {
		return repository.findAll();
	}

	public Page<Person> findAll(Pageable page) {
		return repository.findAll(page);
	}

	public Person save(Person user) {
		return repository.save(user);
	}

	public void remove(Long id) {
		repository.delete(id);
	}

}
