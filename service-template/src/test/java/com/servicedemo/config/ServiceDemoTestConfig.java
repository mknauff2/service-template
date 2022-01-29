/**
 * 
 */
package com.servicedemo.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.servicedemo.service.CustomerService;

/**
 * @author mikeknauff
 *
 */

// Define use for testing purposes only - must be referenced in the test class 
@Profile("test")

// Define class as configuration so that beans are automatically created
@Configuration

public class ServiceDemoTestConfig {

	/**
	 * Mocks the Customer Service for TDD purposes
	 *  
	 * @return The mocked customer service
	 */
	@Bean
	@Primary
	public CustomerService customerService() {
		
		return Mockito.mock(CustomerService.class);
	}

}
