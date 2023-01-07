package com.servicedemo;

import org.junit.jupiter.api.Assertions;
// junit 5
import org.junit.jupiter.api.Test;
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

class ServiceTemplateApplicationTests {
	
	@Autowired
	CustomerController controller;

	// First test is to make sure that the application context can be
	// loaded correctly
	//
	@Test
	void contextLoads() {
		
		// if no exception is thrown then the application context loaded
		// successfully
		//
		Assertions.assertTrue(true);
		Assertions.assertNotNull(controller);
	}
	
	// Create a customer - Happy Path
	@Test
	void testCreateCustomerSuccess() throws Exception {
		
		// Create the customer and makes sure that server provides an
		// HTTP STatus = 200 (OK)
		//
		final String url = "http://localhost:8080/customers/v1/";
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("REQUEST_ID", "1001");
		headers.add("CORR_ID", "1001");
		HttpEntity<String> entity = new HttpEntity<String>(testRecord, headers);
		 
		RestTemplate rest = new RestTemplate();
		ResponseEntity<String> response = rest.exchange(url, HttpMethod.POST, entity, String.class);
		
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		
	}
	
	/*
	
	// Retrieve a Customer - Happy Path
	//@Test
	void testGetCustomerByIdSuccess() throws Exception {
		
		MvcResult result = 
				this.mockMvc.perform(
						MockMvcRequestBuilders
						.get("/customers/v1/" + "{id}", FieldValues.CUSTOMER_ID.toString())
						.header("REQUEST_ID", "1002")
						.header("CORR_ID", "1001")
						.accept("application/json"))
						.andExpect(MockMvcResultMatchers.status().isOk())
						.andDo(MockMvcResultHandlers.print())
						.andReturn();
				
		// Check to make sure the values are correct
		String customerId = JsonPath.read(result.getResponse().getContentAsString(), "$." + FieldNames.CUSTOMER_ID);
		Assertions.assertEquals(FieldValues.CUSTOMER_ID.toString(), customerId);
				
		String firstName = JsonPath.read(result.getResponse().getContentAsString(), "$." + FieldNames.FIRST_NAME);
		Assertions.assertEquals(FieldValues.FIRST_NAME.toString(), firstName);
				
		String lastName = JsonPath.read(result.getResponse().getContentAsString(), "$." + FieldNames.LAST_NAME);
		Assertions.assertEquals(FieldValues.LAST_NAME.toString(), lastName);
				
		String streetAddress1 = JsonPath.read(result.getResponse().getContentAsString(), "$." + FieldNames.STREET_1);
		Assertions.assertEquals(FieldValues.STREET_1.toString(), streetAddress1);
				
		String streetAddress2 = JsonPath.read(result.getResponse().getContentAsString(), "$." + FieldNames.STREET_2);
		Assertions.assertEquals(FieldValues.STREET_2.toString(), streetAddress2);
				
		String city = JsonPath.read(result.getResponse().getContentAsString(), "$." + FieldNames.CITY);
		Assertions.assertEquals(FieldValues.CITY.toString(), city);
				
		String state = JsonPath.read(result.getResponse().getContentAsString(), "$." + FieldNames.STATE);
		Assertions.assertEquals(FieldValues.STATE.toString(), state);
				
		String zipCode = JsonPath.read(result.getResponse().getContentAsString(), "$." + FieldNames.ZIP);
		Assertions.assertEquals(FieldValues.ZIP.toString(), zipCode);
				
	}
	
	//@Test
	void testDeleteCustomerByIdSuccess() throws Exception {
		
		
		// Setup the the customer to delete the customer
		testCreateCustomerSuccess();
		
		// Delete the customer and makes sure that server provides an
		// HTTP STatus = 200 (OK)
		//
		MvcResult result =
		this.mockMvc.perform(
				MockMvcRequestBuilders
				.delete("/customers/v1/" + "{id}", FieldValues.CUSTOMER_ID.toString())
				.header("REQUEST_ID", "1003")
				.header("CORR_ID", "1001")
		        .accept("application/json"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print())
				.andReturn();
		
		// and make sure that the customer was found
		String resultCode = JsonPath.read(result.getResponse().getContentAsString(), 
				"$." + "resultCode");
		Assertions.assertNotEquals("NOT_FOUND", resultCode);
		
		}
		
		/*ß
	
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
