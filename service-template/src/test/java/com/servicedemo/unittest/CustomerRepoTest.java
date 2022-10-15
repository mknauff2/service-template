/**
 * 
 */
package com.servicedemo.unittest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import com.servicedemo.model.Customer;
import com.servicedemo.repository.CustomerRepository;

// We want to test only he Customer Service class without any of the Spring Boot
// context and dependencies. So we will use only the Mockito JUnit Test Runner
// to avoid any the the Spring Boot context dependencies
//
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes= {MockitoJUnitRunner.class})
// Launches the In-Memory Database
@DataJpaTest
// Use a different application properties that disables automatic schema initialization
@TestPropertySource(locations = {"classpath:test-application.properties"})
/**
 * @author mikeknauff
 *
 */
class CustomerRepoTest {
	
	@Autowired
	private CustomerRepository repo;

	@Test
	void test() {
		List<Customer> customers = repo.findAll();
		
		assertEquals(5, customers.size());
		
	}
	
	@Test
	void testFindByCustomerId() {
		Optional<Customer> customer = repo.findById((long)100);
		
		assertEquals(true, customer.isPresent());
		assertEquals("LEE_2", customer.get().getCustomerId());
	}
	
	@Test
	void testSaveCustomer_Success() {
		
		Customer savedCustomer = repo.save(initTestCustomer());
		
		assertNotEquals(null, savedCustomer);
		assertEquals("BLEE2", savedCustomer.getCustomerId());
		
		// Clean-up the db test state
		repo.delete(savedCustomer);
	}
	
	/**
	 * Helper function for tests that returns standard customer
	 * 
	 * @return the standard test customer
	 */
	private Customer initTestCustomer() {
		
		// Create the customer to test
		Customer customer = new Customer.CustomerBuilder()
				.resourceId((long)1)
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
