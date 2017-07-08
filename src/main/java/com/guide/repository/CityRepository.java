package com.guide.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guide.model.City;

public interface CityRepository extends JpaRepository<City, Long> {

}
