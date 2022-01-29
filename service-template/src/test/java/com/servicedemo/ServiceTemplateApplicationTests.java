package com.servicedemo;

import org.junit.jupiter.api.Assertions;
// junit 5
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.context.TestPropertySource;
import org.springframework.beans.factory.annotation.Autowired;

// Mock MVC
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.jayway.jsonpath.JsonPath;


// Provides the required Spring Boot Test Environment
// including the proper setup of the application context
//
@SpringBootTest

// Create a simulated web environment without a web server
// and injects a mock MVC web environment
//
@AutoConfigureMockMvc()

// Use the "test" profile to load the proper autowired mocks
@ActiveProfiles("test")

// Set any required test properties in the environment
// @TestPropertySource(properties = {"name.attribute=value"})

class ServiceTemplateApplicationTests {

	// Provide a simplified interface for REST communication with the
	// REST controller - no server required
	//
	@Autowired
	private MockMvc mockMvc;
	
	// First test is to make sure that the application context can be
	// loaded correctly
	//
	@Test
	void contextLoads() {
		
		// if no exception is thrown then the application context loaded
		// successfully
		//
		Assertions.assertTrue(true);
	}
	
	// Create a customer - Happy Path
	@Test
	void testCreateCustomerSuccess() throws Exception {
		// do nothing for now
		MvcResult result = 
		this.mockMvc.perform(
				MockMvcRequestBuilders
				.post("/customers/v1/")
				.content(testRecord)
				.contentType("application/json")
				.header("REQUEST_ID", "1001")
				.header("CORR_ID", "1001")
		        .accept("application/json"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print())
				.andReturn();
		
		// Check to make sure the values are correct
		/*
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
		*/		

	}
	
	/**
	 * Test Values
	 */
	private enum FieldNames {
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
	
	private enum FieldValues {
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
	
	private final static String testRecord = 
			"{ \"" + FieldNames.FIRST_NAME + "\": \"" + FieldValues.FIRST_NAME + "\", " 
			+ "\"" + FieldNames.LAST_NAME  + "\": \"" + FieldValues.LAST_NAME + "\", "
			+ "\"" + FieldNames.STREET_1   + "\": \"" + FieldValues.STREET_1 + "\", "
			+ "\"" + FieldNames.STREET_2   + "\": \"" + FieldValues.STREET_2 + "\", "
			+ "\"" + FieldNames.CITY       + "\": \"" + FieldValues.CITY + "\",  "
			+ "\"" + FieldNames.STATE      + "\": \"" + FieldValues.STATE + "\", "
			+ "\"" + FieldNames.ZIP        + "\": \"" + FieldValues.ZIP + "\" }"; 
}
