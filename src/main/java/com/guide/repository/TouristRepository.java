package com.guide.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guide.model.Tourist;

public interface TouristRepository extends JpaRepository<Tourist, Long> {

}
