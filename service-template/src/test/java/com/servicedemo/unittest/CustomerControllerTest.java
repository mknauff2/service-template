/**
 * 
 */
package com.servicedemo.unittest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.servicedemo.model.Customer;
import com.servicedemo.rest.CustomerController;
import com.servicedemo.service.CustomerService;

/**
 * @author mikeknauff
 *
 */

@WebMvcTest(controllers = CustomerController.class)
public class CustomerControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	CustomerService service;

	/*@Test
	void contextLoads() {
		Assertions.assertTrue(true);
	} */
	
	@Test
	void testGetCustomer() throws Exception {
		
		// Setup the test result from the Customer Service
		when(service.getCustomer("0000001")).thenReturn(new Customer());
		
		// Execute the test
		RequestBuilder request = MockMvcRequestBuilders
				.get("/customers/v1/" + "{id}", "0000001")
				.header("REQUEST_ID", "1002")
				.header("CORR_ID", "1001")
				.accept(MediaType.APPLICATION_JSON);
				
		mockMvc.perform(request)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(content().json("{customerId:null, firstName:null, lastName:null, streetAddress1:null, streetAddress2:null, city:null, state:null, zipCode:null}"))
				.andDo(MockMvcResultHandlers.print());
	
		// Look also at JSONAssert for testing json strings
		
	}

}
