package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.Question;
import com.example.demo.dao.Reply;
import com.example.demo.persistence.RecordNotFoundException;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.repository.ReplyRepository;

@Service
public class QuestionService {

	@Autowired
	QuestionRepository questionRepository;

	@Autowired
	ReplyRepository replyRepository;

	public Question createOrUpdateQuestion(Question entity) {
		// TODO Auto-generated method stub
		if (entity.getId() != null) {
			Optional<Question> question = questionRepository.findById(entity.getId());

			if (question.isPresent()) {
				Question newEntity = question.get();
				newEntity.setAuthor(entity.getAuthor());
				newEntity.setMessage(entity.getMessage());

				newEntity = questionRepository.save(newEntity);

				return newEntity;
			}
			return null;
		} else {
			entity = questionRepository.save(entity);

			return entity;
		}
	}

	public Reply createOrUpdateReply(Reply entity, Long questionId) throws RecordNotFoundException {
		Optional<Question> question = questionRepository.findById(questionId);

		if (entity.getId() == null) {
			entity.setQuestionId(questionId);
			entity = replyRepository.save(entity);
			Set<Reply> replies = question.get().getReplies();
			replies.add(entity);
			createOrUpdateQuestion(question.get());
			return entity;
		} else {
			Optional<Reply> reply = replyRepository.findById(entity.getId());

			if (reply.isPresent()) {
				Reply newEntity = reply.get();
				newEntity.setAuthor(entity.getAuthor());
				newEntity.setMessage(entity.getMessage());
				newEntity = replyRepository.save(newEntity);
				Set<Reply> replies = question.get().getReplies();
				replies.add(entity);
				createOrUpdateQuestion(question.get());
				return newEntity;
			}
			return null;
		}
	}

	public Question getQuestionById(Long id) throws RecordNotFoundException {
		Optional<Question> question = questionRepository.findById(id);

		if (question.isPresent()) {
			return question.get();
		} else {
			throw new RecordNotFoundException("No question record exist for given id");
		}

	}

	public List<Question> getAllQuestion() {
		List<Question> questions = (List<Question>) questionRepository.findAll();

		if (questions.size() > 0) {
			return questions;
		} else {
			return new ArrayList<Question>();
		}
	}

}
