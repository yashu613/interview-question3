package com.example.demo.dao;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "TBL_QUESTIONS")
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "author")
	private String author;

	@Column(name = "message")
	private String message;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "questionId")
	@OrderColumn(name = "replies")
	private Set<Reply> replies;

}
