package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.Question;
import com.example.demo.dao.Reply;
import com.example.demo.persistence.EmployeeEntity;
import com.example.demo.persistence.RecordNotFoundException;
import com.example.demo.service.QuestionService;

@RestController
@RequestMapping("/questions")
public class QuestionController {

	@Autowired
	QuestionService questionService;

	@PostMapping
	public ResponseEntity<Question> createOrUpdateQuestion(@RequestBody Question question) throws RecordNotFoundException {
		Question updated = questionService.createOrUpdateQuestion(question);
		return new ResponseEntity<Question>(updated, new HttpHeaders(), HttpStatus.CREATED);
	}

	@PostMapping("{questionId}/reply")
	public ResponseEntity<Reply> createOrUpdateReply(@PathVariable Long questionId, @RequestBody Reply reply) throws RecordNotFoundException {
		Reply updated = questionService.createOrUpdateReply(reply, questionId);
		return new ResponseEntity<Reply>(updated, new HttpHeaders(), HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable("id") Long id)
            throws RecordNotFoundException {
        Question entity = questionService.getQuestionById(id);

        return new ResponseEntity<Question>(entity, new HttpHeaders(), HttpStatus.OK);
    }
	

    @GetMapping
    public ResponseEntity<List<Question>> getAllQuestions() {
        List<Question> list = questionService.getAllQuestion();

        return new ResponseEntity<List<Question>>(list, new HttpHeaders(), HttpStatus.OK);
    }

}
