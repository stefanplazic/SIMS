package com.guide.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guide.model.Comment;
import com.guide.model.Tour;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	public List<Comment> findByPublicationDate(Date publicationDate);

	public List<Comment> findByTour(Tour tour);
}
