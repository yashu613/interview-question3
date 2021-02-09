package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.demo.dao.Question;
import com.example.demo.dao.Reply;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes = DemoApplication.class)
@WebAppConfiguration
@TestMethodOrder(OrderAnnotation.class) 
public class QuestionControllerTest {

	MockMvc mvc;

	@Autowired
	WebApplicationContext webApplicationContext;

	@BeforeEach
	public void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	@Order(4)
	public void getQuestions() throws Exception {

		String uri = "/questions";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Question[] questionsList = mapFromJson(content, Question[].class);
		assertTrue(questionsList.length > 0);
	}

	@Test
	@Order(2)
	public void getQuestion() throws Exception {
		String uri = "/questions/1";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status); // if record is available
		String content = mvcResult.getResponse().getContentAsString();
		Question e = mapFromJson(content, Question.class);
		assertTrue(e != null);
	}

	@Test
	@Order(1)
	public void createQuestion() throws Exception {
		String uri = "/questions";
		Question questionrecord = new Question();
		questionrecord.setAuthor("Davidson");
		questionrecord.setMessage("My first message");
		String inputJson = mapToJson(questionrecord);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(201, status);
		String content = mvcResult.getResponse().getContentAsString();
		Question e = mapFromJson(content, Question.class);
		assertTrue(e.getId() != null);
	}
	
	@Test
	@Order(3)
	public void createReply() throws Exception {
		String uri = "/questions/1/reply";
		Reply replyRecord = new Reply();
		replyRecord.setAuthor("Reply Author");
		replyRecord.setMessage("My first reply message");
		String inputJson = mapToJson(replyRecord);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(201, status);
		String content = mvcResult.getResponse().getContentAsString();
		Reply e = mapFromJson(content, Reply.class);
		assertTrue(e.getId() != null);
	}

	protected String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

	protected <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}

}
