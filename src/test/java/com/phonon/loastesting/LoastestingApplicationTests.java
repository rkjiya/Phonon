package com.phonon.loastesting;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.phonon.loastesting.enums.RequestStatus;
import com.phonon.loastesting.model.RequestData;
import com.phonon.loastesting.repository.RequestRepository;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class LoastestingApplicationTests {

	private static final String REQUEST_NAME = "request-1";
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private RequestRepository requestRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	public void setup(){
		requestRepository.deleteAll();
	}

	@Test
	void addAReqeust_add_retunReqeustUuid() throws Exception {
		RequestData requestData = new RequestData();
		requestData.setName(REQUEST_NAME);

		 ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/request/add")
				.accept(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(requestData)));

		 resultActions.andDo(MockMvcResultHandlers.print())
				 .andExpect(MockMvcResultMatchers.status().isCreated());
	}

	@Test
	void addAReqeust_getAll_retunSavedReqeustData() throws Exception {
		RequestData requestData = new RequestData();
		requestData.setName(REQUEST_NAME);

		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/request/add")
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(requestData)));

		resultActions.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isCreated());


		mockMvc.perform(MockMvcRequestBuilders.get("/request/getAll"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(REQUEST_NAME)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.requestStatus", CoreMatchers.is(RequestStatus.PENDING)));
	}

}
