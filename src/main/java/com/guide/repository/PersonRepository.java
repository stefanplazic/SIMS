package com.guide.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guide.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
