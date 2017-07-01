package com.guide.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guide.model.Guide;

public interface GuideRepository extends JpaRepository<Guide, Long> {

}
