package com.guide.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.guide.model.Comment;
import com.guide.repository.CommentRepository;

@Service
public class CommentService {

	@Autowired
	private CommentRepository repository;

	public Comment findOne(Long id) {
		return repository.findOne(id);
	}

	public List<Comment> findAll() {
		return repository.findAll();
	}

	public Page<Comment> findAll(Pageable page) {
		return repository.findAll(page);
	}

	public Comment save(Comment comment) {
		return repository.save(comment);
	}

	public void remove(Long id) {
		repository.delete(id);
	}

	public List<Comment> findByPublicationDate(Date publicationDate) {
		return repository.findByPublicationDate(publicationDate);
	}
}
