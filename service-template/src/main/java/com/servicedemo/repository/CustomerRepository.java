/**
 * 
 */
package com.servicedemo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.servicedemo.model.Customer;

/**
 * @author mikeknauff
 * 
 * To Create tables and add data:
 *    - create a file called data.sql in the /resources directory and Spring Boot
 *      will automatically execute the file
 *      
 * To enable the H2 database console:
 *    - place the following entry in the application.properties file
 *      spring.h2.console.enabled=true
 *       
 *    - to invoke the console enter the following URI in the browser
 *      http://localhost:8080/h2-console/
 *      
 *    - enter the following JDBC URL jdbc:h2:mem:testdb
 *
 */
@Component
public interface CustomerRepository extends JpaRepository<Customer, Long> {
		
	List<Customer> findAll();
	
	Optional<Customer>	findById(Long resourceId);
	
	Optional<Customer> findByCustomerId(String customerId);
	
	List<Customer> findByLastName(String lastName);

}
