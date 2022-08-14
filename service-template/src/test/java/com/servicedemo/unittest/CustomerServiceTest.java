package com.servicedemo.unittest;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Mockito imports
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.ArrayList;
import java.util.List;
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
import com.servicedemo.service.CustomerResponse.ResultCodes;
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
	void testCreateCustomer_Success() {

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
	void testCreateCustomer_Fail() {
		
		// Create a blank customer
		Customer customer = new Customer();
		
		// Initialize the jpa repo so that when a null customer is saved an
		// exception is thrown.
		//
		when(mockRepo.save(customer)).thenThrow(new RuntimeException("mocked jpa exception"));
		
		// Try to create the empty customer
		CustomerResponse response = service.createCustomer(customer);
		
		// Assert that the unknwon error is returned
		assertEquals(ResultCodes.UNKNOWN, response.getResultCode());
	}

	@Test
	void testGetCustomerByCustId_Success() {
		
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
	void testGetCustomerByCustId_Fail() {
		
		// Setup the mock repo so that a null is returned
		when(mockRepo.findByCustomerId(anyString())).thenReturn(Optional.empty());

		// Execute the test
		Customer cust = service.getCustomer("BLEE2");

		// Check that a null is returned
		assertEquals(null, cust);		
	}
	
	@Test
	void testGetCustomerById_Success() {
		
		// Create an optional customer that is required by the repo return
		// type
		//
		Optional<Customer> customer = Optional.of(initTestCustomer());
		customer.get().setResourceId(10001);
		
		// Setup the mock repo so that the test customer is returned when the 
		// find by customer Id is called.
		//
		when(mockRepo.findById((long)10001)).thenReturn(customer);

		// Execute the test
		Customer cust = service.getCustomer((long)10001);

		// Check that the correct customer is returned when the test customer
		// ID is used
		//
		assertEquals(10001, cust.getResourceId());

	}
	
	@Test
	void getCustomerByLastName_Success() {
		
		// Setup the mock repo so that a customer list is returned when the 
		// find by last name is called.
		//
		when(mockRepo.findByLastName("Kent")).thenReturn(initCustomerList());

		// Execute the test
		List<Customer> custs = service.getCustomerByLastName("Kent");

		// Check that a list with four customers are returned 
		assertEquals(4, custs.size());
	}
	
	@Test
	void getCustomerByLastName_Fail() {
		
		// Setup the mock repo so that an empty customer list is returned when the 
		// find by last name is called.
		//
		when(mockRepo.findByLastName(anyString())).thenReturn(new ArrayList<>());

		// Execute the test
		List<Customer> custs = service.getCustomerByLastName("Kent");

		// Check that an empty list is returned 
		assertEquals(0, custs.size());
	}
	
	@Test
	void testGetCustomerById_Fail() {
		
		// Setup the mock repo so that a null is returned
		when(mockRepo.findById(anyLong())).thenReturn(Optional.empty());

		// Execute the test
		Customer cust = service.getCustomer((long)10001);

		// Check that a null is returned
		assertEquals(null, cust);		
	}

	@Test
	void testDeleteCustomer_Success() {
		
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
	
	@Test
	void testDeleteCustomer_Fail() {
		
		// Setup the mock repo so that an empty Optional is returned
		when(mockRepo.findByCustomerId(anyString())).thenReturn(Optional.empty());

		// Execute the test
		CustomerResponse cust = service.deleteCustomer(anyString());

		// Check that a not found result code is returned
		assertEquals(ResultCodes.NOT_FOUND, cust.getResultCode());
		
	}
	
	@Test
	void testUpdateCustomer_Success() {
		
		// Create an optional customer that is required by the repo return
		// type
		//
		Optional<Customer> customer = Optional.of(initTestCustomer());
		
		// Update the customer object
		customer.get().setFirstName("Robert");
				
		// Setup the mock repo so that the test customer is returned when the 
		// the delete method checks to see if the customer exists before 
		// attempting to delete it using the customer ID.
		//
		when(mockRepo.findByCustomerId("BLEE2")).thenReturn(customer);
		
		// Update the customer object
		customer.get().setFirstName("Robert");
		
		// Setup the mock to return the updated customer object	
		when(mockRepo.save(any())).thenReturn(customer.get());
			
		// Execute the test
		CustomerResponse response = service.updateCustomer(customer.get());

		// Check that the correct customer is returned when the test customer
		// ID is used
		//
		assertEquals("Robert", response.getCustomer().getFirstName());
	}
	
	@Test
	void testUpdateCustomer_Fail() {
		
		// Setup the mock repo so that an empty Optional is returned
		when(mockRepo.findByCustomerId(anyString())).thenReturn(Optional.empty());

		// Execute the test
		CustomerResponse cust = service.updateCustomer(initTestCustomer());

		// Check that a not found result code is returned
		assertEquals(ResultCodes.NOT_FOUND, cust.getResultCode());
		
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
