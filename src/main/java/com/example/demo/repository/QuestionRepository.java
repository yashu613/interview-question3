package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.dao.Question;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Long> {

}
