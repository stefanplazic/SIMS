package com.guide.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guide.model.LocationInfo;

public interface LocationInfoRepository extends JpaRepository<LocationInfo, Long> {

}
