package com.guide.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guide.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public User findByUsername(String username);
}
