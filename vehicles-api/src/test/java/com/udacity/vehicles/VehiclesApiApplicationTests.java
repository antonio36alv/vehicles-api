package com.udacity.vehicles;

import com.udacity.vehicles.domain.car.Car;
import org.json.JSONArray;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.JVM)
public class VehiclesApiApplicationTests {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private final String baseUrl = "http://localhost:";

    private ResponseEntity<Car> testCar;

    // need to post at least one item before running tests
    // this is specifically for find by id
    @Before
    public void postACarForSetup() {
        String json = "{\"condition\":\"NEW\",\"createdAt\":\"2019-09-17T14:34:28.043Z\",\"details\":{\"body\":\"mini-van\",\"engine\":\"3.6L V6\",\"externalColor\":\"white\",\"fuelType\":\"Gasoline\",\"manufacturer\":{\"code\":101,\"name\":\"Chevrolet\"},\"mileage\":32280,\"model\":\"Impala\",\"modelYear\":2018,\"numberOfDoors\":4,\"productionYear\":2018},\"location\":{\"lat\":20,\"lon\":47}}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(json, headers);
        testCar = testRestTemplate.postForEntity(baseUrl + port + "/cars/", request, Car.class);
    }

    @Test
    public void contextLoads() {
    }

    // need test for post mapping
    @Test
    public void postTest() {
        String json = "{\"condition\":\"NEW\",\"createdAt\":\"2019-09-17T14:34:28.043Z\",\"details\":{\"body\":\"mini-van\",\"engine\":\"3.6L V6\",\"externalColor\":\"white\",\"fuelType\":\"Gasoline\",\"manufacturer\":{\"code\":101,\"name\":\"Chevrolet\"},\"mileage\":32280,\"model\":\"Impala\",\"modelYear\":2018,\"numberOfDoors\":4,\"productionYear\":2018},\"location\":{\"lat\":20,\"lon\":47}}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(json, headers);
        ResponseEntity<Car> response = testRestTemplate.postForEntity(baseUrl + port + "/cars/", request, Car.class);
        // check that the id we get back is the id we queried for
        assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
    }

    // need test for find by id
    @Test
    public void getByIdTest() {
        long id = 1;
        ResponseEntity<Car> response = testRestTemplate.getForEntity(baseUrl + port + "/cars/" + id, Car.class);
        // check http status
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        // check that the id we get back is the id we queried for
        assertThat(response.getBody().getId(), equalTo(id));
    }

    // need test for get all
    @Test
    public void getListTest() {
        ResponseEntity<JSONArray> response = this.testRestTemplate.getForEntity(baseUrl + port + "/cars/", JSONArray.class);
        // check http status
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

//     need test for put mapping
    @Test
    public void putTest() {
        long id = testCar.getBody().getId();
        String json ="{\"condition\":\"USED\",\"details\":{\"body\":\"sedan\",\"model\":\"Impala\",\"manufacturer\":{\"code\":101,\"name\":\"Chevrolet\"},\"numberOfDoors\":4,\"fuelType\":\"Gasoline\",\"engine\":\"3.6L V6\",\"mileage\":32280,\"modelYear\":2018,\"productionYear\":2018,\"externalColor\":\"white\"},\"location\":{\"lat\":40.73061,\"lon\":-73.935242}}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(json, headers);
        ResponseEntity<Car> response = testRestTemplate.exchange(baseUrl + port + "/cars/" + id, HttpMethod.PUT, request, Car.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    // need test for delete
    @Test
    public void deleteTest() {
        long id = testCar.getBody().getId();
        ResponseEntity<Car> response = testRestTemplate.exchange(baseUrl + port + "/cars/" + id, HttpMethod.DELETE, null, Car.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.NO_CONTENT));
    }

}
