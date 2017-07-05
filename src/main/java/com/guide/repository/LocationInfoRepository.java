package com.guide.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guide.model.City;
import com.guide.model.LocationInfo;

public interface LocationInfoRepository extends JpaRepository<LocationInfo, Long> {

	public List<LocationInfo> findByCity(City city);

}
