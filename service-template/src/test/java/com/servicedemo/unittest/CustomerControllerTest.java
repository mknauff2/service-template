/**
 * 
 */
package com.servicedemo.unittest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

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
import com.servicedemo.service.CustomerResponse;
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
	CustomerService mockService;

	/*@Test
	void contextLoads() {
		Assertions.assertTrue(true);
	} */
	
	@Test
	void testCreateCustomer() throws Exception {
		
		// Setup the customer service to return the customer
		// TODO: Complete this test...build will fail here
		// Create a customer object and return it from the customer service 
		// when create is called
		//
		
		when(mockService.createCustomer(any())).thenReturn(
				new CustomerResponse(CustomerResponse.ResultCodes.SUCCESS, "", initTestCustomer()));
		
		// Execute the test
		RequestBuilder request = MockMvcRequestBuilders
				.post("/customers/v1/")
				.header("REQUEST_ID", "1001")
				.header("CORR_ID", "1001")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"customerId\":\"BLEE2\", \"firstName\":\"Bruce\", \"lastName\":\"Lee\", \"streetAddress1\":\"41 Cumberland Road\", \"streetAddress2\":\"Unit A\", \"city\":\"Hong Kong\", \"state\":\"Kowloon\", \"zipCode\":\"5670-D\"}")
				.accept(MediaType.APPLICATION_JSON);
				
		mockMvc.perform(request)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(content().json("{resultCode:SUCCESS, responseDescription:\"\", customer:{resourceId:10001, customerId:BLEE2, firstName:Bruce, lastName:Lee, streetAddress1:'41 Cumberland Road', streetAddress2:'Unit A', city:'Hong Kong', state:Kowloon, zipCode:5670-D}}"))
				.andDo(MockMvcResultHandlers.print());		
		
	}
	
	@Test
	void testGetCustomer() throws Exception {
		
		// Setup the test result from the Customer Service
		when(mockService.getCustomer("0000001")).thenReturn(new Customer());
		
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
	
	/**
	 * Helper function for tests that returns standard customer
	 * 
	 * @return the standard test customer
	 */
	private Customer initTestCustomer() {
		
		// Create the customer to test
		Customer customer = new Customer.CustomerBuilder()
				.resourceId((long)10001)
				.customerId("BLEE2")
				.firstName("Bruce")
				.lastName("Lee")
				.streetAddress1("41 Cumberland Road")
				.streetAddress2("Unit A")
				.city("Hong Kong")
				.state("Kowloon")
				.zipCode("5670-D")
				.build();
		
		return customer;
		
	}
	
	private List<Customer> initCustomerList() {
		
		List<Customer> customers = new ArrayList<>();
		
		customers.add(new Customer.CustomerBuilder()
				.resourceId((long)11000)
				.customerId("KENT1")
				.firstName("Clark")
				.lastName("Kent")
				.streetAddress1("10 University Road")
				.streetAddress2("Unit 1")
				.city("Metropolis")
				.state("Ney York")
				.zipCode("10021")
				.build());
		
		customers.add(new Customer.CustomerBuilder()
				.resourceId((long)12000)
				.customerId("KENT2")
				.firstName("Lois")
				.lastName("Kent")
				.streetAddress1("10 University Road")
				.streetAddress2("Unit 1")
				.city("Metropolis")
				.state("Ney York")
				.zipCode("10021")
				.build());
		
		customers.add(new Customer.CustomerBuilder()
				.resourceId((long)13000)
				.customerId("KENT3")
				.firstName("Jon")
				.lastName("Kent")
				.streetAddress1("10 University Road")
				.streetAddress2("Unit 2")
				.city("Metropolis")
				.state("Ney York")
				.zipCode("10021")
				.build());
		
		customers.add(new Customer.CustomerBuilder()
				.resourceId((long)14000)
				.customerId("KENT4")
				.firstName("Martha")
				.lastName("Kent")
				.streetAddress1("10 University Road")
				.streetAddress2("Unit 2")
				.city("Metropolis")
				.state("Ney York")
				.zipCode("10021")
				.build());
		
		return customers;
	}
	
	
}