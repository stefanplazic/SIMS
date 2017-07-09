package com.guide.dto;

import java.text.SimpleDateFormat;

import com.guide.model.Comment;

public class CommentDTO {

	private Long id;
	private String content;
	private String publicationDate;
	private UserDTO user;

	public CommentDTO() {
		// TODO Auto-generated constructor stub
	}

	public CommentDTO(Comment c) {
		this.content = c.getContent();
		this.user = new UserDTO(c.getPerson());
		SimpleDateFormat endFor = new SimpleDateFormat(" dd/MM/yyyy");
		this.publicationDate = endFor.format(c.getPublicationDate());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(String publicationDate) {
		this.publicationDate = publicationDate;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

}
