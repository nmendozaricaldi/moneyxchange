package com.moneyxchange.io.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.moneyxchange.io.ResourceServerApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceServerApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class MoneyChangeControllerTest {

	@Autowired
	WebApplicationContext context;

	@Autowired
	private FilterChainProxy springSecurityFilterChain;

	@InjectMocks
	MoneyChangeController controller;

	private MockMvc mvc;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mvc = MockMvcBuilders.webAppContextSetup(context).addFilter(springSecurityFilterChain).build();
	}

	@Test
	public void moneyChangeUnauthorized() throws Exception {
		// @formatter:off
		mvc.perform(get("/latest?base=USD&symbols=EU").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized()).andExpect(jsonPath("$.error", is("unauthorized")));
		// @formatter:on
	}

	@Test
	public void moneyChangeAuthorized() throws Exception {
		String accessToken = obtainAccessToken("moneyChangeRate", "admin", "admin");

		// @formatter:off
		mvc.perform(get("/latest?base=USD&symbols=EU").header("Authorization", "Bearer " + accessToken))
				.andExpect(status().isOk()).andExpect(jsonPath("$.base", is("USD")));
		// @formatter:on
	}

	private String obtainAccessToken(String clientId, String username, String password) {
		final Map<String, String> params = new HashMap<String, String>();
		params.put("grant_type", "password");
		params.put("client_id", clientId);
		params.put("username", username);
		params.put("password", password);
		final Response response = RestAssured.given().auth().preemptive().basic(clientId, "secret").and().with()
				.params(params).when().post("http://localhost:8087/oauth.server/oauth/token");
		return response.jsonPath().getString("access_token");
	}

}
