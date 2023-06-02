package com.servicedemo;

//import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
// junit 5
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
// import org.springframework.test.context.TestPropertySource;

import com.jayway.jsonpath.JsonPath;
//import com.servicedemo.model.Customer;
import com.servicedemo.rest.CustomerController;

// Provides the required Spring Boot Test Environment
// including the proper setup of the application context
//
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)

// Use the "test" profile to load the proper autowired mocks
//
// NOTE: Disabled after adding actual Customer Service
//
// @ActiveProfiles("test")

// Set any required test properties in the environment
// @TestPropertySource(properties = {"name.attribute=value"})

// Set the order of the tests due to dependencies between data in the tests 
// Not considered a best practice - we should look at setting up test data 
// through SQL scripts at initialization
//
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class ServiceTemplateApplicationTests {
	
	@Autowired
	CustomerController controller;
	
	static final String URL = "http://localhost:8080/customers/v1/";

	// First test is to make sure that the application context can be
	// loaded correctly
	//
	@Test
	@Order(1)
	void contextLoads() {
		
		// if no exception is thrown then the application context loaded
		// successfully
		//
		Assertions.assertTrue(true);
		Assertions.assertNotNull(controller);
	}
	
	// Create a customer - Happy Path
	@Test
	@Order(2)
	void testCreateCustomerSuccess() throws Exception {
		
		// Create the customer and makes sure that server provides an
		// HTTP STatus = 200 (OK)
		//
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("REQUEST_ID", "1001");
		headers.add("CORR_ID", "1001");
		HttpEntity<String> entity = new HttpEntity<String>(testRecord, headers);
		 
		RestTemplate rest = new RestTemplate();
		ResponseEntity<String> response = rest.exchange(URL, HttpMethod.POST, entity, String.class);
				
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
				
	}
	
	// Retrieve a customer by ID - Happy Path
	@Test
	@Order(3)
	void testGetCustomerByIdSuccess() throws Exception {
		
		// Create the customer and makes sure that server provides an
		// HTTP STatus = 200 (OK)
		//
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("REQUEST_ID", "1002");
		headers.add("CORR_ID", "1001");
		HttpEntity<String> entity = new HttpEntity<String>(new String(), headers);
		 
		RestTemplate rest = new RestTemplate();
		ResponseEntity<String> response = rest.exchange(URL + FieldValues.CUSTOMER_ID.toString(), HttpMethod.GET, entity, String.class);
		
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		
		// Test the response body
		String jsonString = response.getBody();
		
		String responseCode = JsonPath.read(jsonString, "$.resultCode");
		Assertions.assertEquals("SUCCESS", responseCode);
		
		String responseDescription = JsonPath.read(jsonString, "$.responseDescription");
		Assertions.assertEquals("", responseDescription);
		
		int responseResourceId = JsonPath.read(jsonString, "$.customer.resourceId");
		Assertions.assertTrue(responseResourceId > 0);
		
		String responseCustomerId = JsonPath.read(jsonString, "$.customer." + FieldNames.CUSTOMER_ID);
		Assertions.assertEquals(FieldValues.CUSTOMER_ID.toString(), responseCustomerId);		
			
		String responseFirstName = JsonPath.read(jsonString, "$.customer." + FieldNames.FIRST_NAME);
		Assertions.assertEquals(FieldValues.FIRST_NAME.toString(), responseFirstName);
		
		String responseLastName = JsonPath.read(jsonString, "$.customer." + FieldNames.LAST_NAME);
		Assertions.assertEquals(FieldValues.LAST_NAME.toString(), responseLastName);
		
		String responseStreet1 = JsonPath.read(jsonString, "$.customer." + FieldNames.STREET_1);
		Assertions.assertEquals(FieldValues.STREET_1.toString(), responseStreet1);
		
		String responseStreet2 = JsonPath.read(jsonString, "$.customer." + FieldNames.STREET_2);
		Assertions.assertEquals(FieldValues.STREET_2.toString(), responseStreet2);
		
		String responseCity = JsonPath.read(jsonString, "$.customer." + FieldNames.CITY);
		Assertions.assertEquals(FieldValues.CITY.toString(), responseCity);
		
		String responseState = JsonPath.read(jsonString, "$.customer." + FieldNames.STATE);
		Assertions.assertEquals(FieldValues.STATE.toString(), responseState);
		
		String responseZip = JsonPath.read(jsonString, "$.customer." + FieldNames.ZIP);
		Assertions.assertEquals(FieldValues.ZIP.toString(), responseZip);
		
	}
	
	// Delete a customer by ID - Happy Path
		@Test
		@Order(4)
		void testDeleteCustomerByIdSuccess() throws Exception {
					
			// Create the customer and makes sure that server provides an
			// HTTP STatus = 200 (OK)
			//
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("REQUEST_ID", "1002");
			headers.add("CORR_ID", "1001");
			HttpEntity<String> entity = new HttpEntity<String>(new String(), headers);
			 
			RestTemplate rest = new RestTemplate();
			ResponseEntity<String> response = rest.exchange(URL + FieldValues.CUSTOMER_ID.toString(), HttpMethod.DELETE, entity, String.class);
			
			Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
			
			// Test the response body
			String jsonString = response.getBody();
			
			String responseCode = JsonPath.read(jsonString, "$.resultCode");
			Assertions.assertEquals("SUCCESS", responseCode);
			
		}
	
	/**
	 * Test Values
	 */
	enum FieldNames {
		CUSTOMER_ID("customerId"),
		FIRST_NAME("firstName"),
		LAST_NAME("lastName"),
		STREET_1("streetAddress1"),
		STREET_2("streetAddress2"),
		CITY("city"),
		STATE("state"),
		ZIP("zipCode");
		
		String name;
		FieldNames(String name) { this.name = name; }
		
		public String toString() { return name; }
		
	}
	
	enum FieldValues {
		CUSTOMER_ID("0000001"),
		FIRST_NAME("Bruce"),
		LAST_NAME("Lee"),
		STREET_1("41 Cumberland Road"),
		STREET_2("Number 4"),
		CITY("Hong Kong"),
		STATE("Kowloon"),
		ZIP("05-673");
		
		String name;
		FieldValues(String name) { this.name = name; }
		
		public String toString() { return name; }
		
	}
	
	final static String testRecord = 
			"{ \"" + FieldNames.CUSTOMER_ID + "\": \"" + FieldValues.CUSTOMER_ID + "\", "
			+ "\"" + FieldNames.FIRST_NAME + "\": \"" + FieldValues.FIRST_NAME + "\", " 
			+ "\"" + FieldNames.LAST_NAME  + "\": \"" + FieldValues.LAST_NAME + "\", "
			+ "\"" + FieldNames.STREET_1   + "\": \"" + FieldValues.STREET_1 + "\", "
			+ "\"" + FieldNames.STREET_2   + "\": \"" + FieldValues.STREET_2 + "\", "
			+ "\"" + FieldNames.CITY       + "\": \"" + FieldValues.CITY + "\",  "
			+ "\"" + FieldNames.STATE      + "\": \"" + FieldValues.STATE + "\", "
			+ "\"" + FieldNames.ZIP        + "\": \"" + FieldValues.ZIP + "\" }"; 
}
