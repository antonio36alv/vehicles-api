package com.udacity.pricing;

import com.udacity.pricing.domain.price.Price;
import org.json.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PricingServiceApplicationTests {

	private final String baseUrl = "http://localhost:";

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	public void contextLoads() {
	}

	// Tests that a query for all the prices responds with a list
	@Test
	public void getAllPrices() throws Exception {
		ResponseEntity<JSONArray> response = this.testRestTemplate.getForEntity(baseUrl + port + "/prices/", JSONArray.class);

		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
	}

	// Tests that a query for a price with id responds with a price
	// Ensures currency and price come back with non null values
	@Test
	public void getPrice() {
		int vehicleId = 1;
		ResponseEntity<Price> response = this.testRestTemplate.getForEntity(baseUrl + port + "/prices/" + vehicleId, Price.class);

		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
		assertThat(response.getBody().getCurrency(), notNullValue());
		assertThat(response.getBody().getPrice(), notNullValue());
	}

}
