package com.servicedemo.unittest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.servicedemo.model.Customer;
import com.servicedemo.repository.CustomerRepository;
import com.servicedemo.service.CustomerService;
import com.servicedemo.service.StandardCustomerService;

// Mockito imports
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
	

	//@Test
	void testCreateCustomer() {
		
		//when(mockRepo.findByLastName("Lee").thenReturn();
		
	}

	@Test
	void testGetCustomer() {
		Optional<Customer> customer = Optional.of(new Customer());
		// Setup the test
		when(mockRepo.findById((long) 100001)).thenReturn(customer);
		
		// Execute the test
		Customer cust = service.getCustomer("100001");
		
		assertEquals(null, cust);
		
	}

	//@Test
	void testDeleteCustomer() {
		fail("Not yet implemented");
	}
	
}
