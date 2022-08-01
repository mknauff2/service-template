package com.servicedemo.unittest;

import static org.junit.jupiter.api.Assertions.assertEquals;
// Mockito imports
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.servicedemo.model.Customer;
import com.servicedemo.repository.CustomerRepository;
import com.servicedemo.service.CustomerResponse;
import com.servicedemo.service.CustomerService;
import com.servicedemo.service.StandardCustomerService;

// We want to test only he Customer Service class without any of the Spring Boot
// context and dependencies. So we will use only the Mockito JUnit Test Runner
// to avoid any the the Spring Boot context dependencies
//
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes= {MockitoJUnitRunner.class})
class CustomerServiceTest {

	@InjectMocks
	private CustomerService service = new StandardCustomerService();

	@Mock
	private CustomerRepository mockRepo;


	@Test
	void testCreateCustomer() {

		// Create the customer to test
		Customer customer = initTestCustomer();

		// Initialize the jpa repo so that when save() is called it returns the
		// test customer object
		//
		when(mockRepo.save(customer)).thenReturn(customer);

		// Call the service to save the customer and expect it to return
		// the same customer object from the jpa repo mock after it is saved
		//
		CustomerResponse response = service.createCustomer(customer);

		// Now test to see if the expected customer object is returned from the
		// service call
		//
		assertEquals("BLEE2", response.getCustomer().getCustomerId());


	}

	@Test
	void testGetCustomer() {
		
		// Create an optional customer that is required by the repo return
		// type
		//
		Optional<Customer> customer = Optional.of(initTestCustomer());
		
		// Setup the mock repo so that the test customer is returned when the 
		// find by customer Id is called.
		//
		when(mockRepo.findByCustomerId("BLEE2")).thenReturn(customer);

		// Execute the test
		Customer cust = service.getCustomer("BLEE2");

		// Check that the correct customer is returned when the test customer
		// ID is used
		//
		assertEquals("BLEE2", cust.getCustomerId());

	}

	@Test
	void testDeleteCustomer() {
		
		// Create an optional customer that is required by the repo return
		// type
		//
		Optional<Customer> customer = Optional.of(initTestCustomer());
		
		// Setup the mock repo so that the test customer is returned when the 
		// the delete method checks to see if the customer exists before 
		// attempting to delete it using the customer ID.
		//
		when(mockRepo.findByCustomerId("BLEE2")).thenReturn(customer);

		// Execute the test
		CustomerResponse response = service.deleteCustomer("BLEE2");

		// Check that the correct customer is returned when the test customer
		// ID is used
		//
		assertEquals("BLEE2", response.getCustomer().getCustomerId());
	}
	
	/**
	 * Helper function for tests that returns standard customer
	 * 
	 * @return the standard test customer
	 */
	private Customer initTestCustomer() {
		
		// Create the customer to test
		Customer customer = new Customer.CustomerBuilder()
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

}
