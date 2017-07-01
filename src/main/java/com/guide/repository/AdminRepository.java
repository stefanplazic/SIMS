package com.guide.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guide.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {

}
