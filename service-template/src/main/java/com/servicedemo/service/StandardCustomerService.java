/**
 * 
 */
package com.servicedemo.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.servicedemo.model.Customer;
import com.servicedemo.repository.CustomerRepository;



/**
 * @author mikeknauff
 *
 */
@Component
public class StandardCustomerService implements CustomerService {
	
	private static final Logger LOG = 
			LoggerFactory.getLogger(StandardCustomerService.class);
	
	@Autowired
	private CustomerRepository repository;

	/**
	 * 
	 */
	public StandardCustomerService() {
		// do nothing
	}

	@Override
	public CustomerResponse createCustomer(Customer customer) {
		
		CustomerResponse response;
		
		try {
			
			Customer savedCustomer = repository.save(customer);
			response = 
					new CustomerResponse(CustomerResponse.ResultCodes.SUCCESS,"", savedCustomer);
			LOG.info("Customer saved: {}", customer);
						
		} catch (Exception e) {
			
			// TODO: see if we can catch specific errors such as duplicates and 
			//       fields that are null - look into "policy" objects from DDD
			//
			response =
					new CustomerResponse(CustomerResponse.ResultCodes.UNKNOWN,
							"An unknown error occured attempting to save the customer", null);
			LOG.debug(e.toString());
						
		} 
			
		return response;
					
	}

	@Override
	public Customer getCustomer(String customerId) {
				
		Optional<Customer> customer = repository.findByCustomerId(customerId);
		
		if (customer.isPresent()) {
			
			return customer.get();
			
		} else {
			
			// customer not found
			return null;
			
		}
	}

	@Override
	public Customer getCustomer(Long resourceId) {
		
		Optional<Customer> customer = repository.findById(resourceId);
		
		if (customer.isPresent()) {
			
			return customer.get();
			
		} else {
			
			// customer not found
			return null;
			
		}

	}

	@Override
	public List<Customer> getCustomerByLastName(String lastName) {
		
		return repository.findByLastName(lastName);
				
	}

	@Override
	public CustomerResponse updateCustomer(Customer customer) {
		// TODO Auto-generated method stub
		// TODO Look at HTTP PATCH Method
		CustomerResponse response;		
		Optional<Customer> updateCustomer = 
				repository.findByCustomerId(customer.getCustomerId());
		
		if (updateCustomer.isPresent()) {
			
			Customer updatedCustomer = repository.save(customer);
			response = new CustomerResponse(CustomerResponse.ResultCodes.SUCCESS,
					"Successfully updated the customer", updatedCustomer);
			LOG.info("Customer updated: {}", customer);
			
		} else {
			
			String message = 
					String.format("Unable to update the customer - customer not found - ID: %s", 
							customer.getCustomerId()); 
			response =
					new CustomerResponse(CustomerResponse.ResultCodes.NOT_FOUND,
						message, null);
			LOG.info(message);

		}
		
		return response;

	}

	@Override
	public CustomerResponse deleteCustomer(String customerId) {
		
		CustomerResponse response = null;
		Optional<Customer> customer = repository.findByCustomerId(customerId);
		
		if (customer.isPresent()) {
			
			repository.delete(customer.get());
			response = new CustomerResponse(CustomerResponse.ResultCodes.SUCCESS,"", customer.get());
			LOG.info("Customer deleted: {}", customer.get());
			
		} else {
			
			String message = 
					String.format("Unable to delete the customer - customer not found - ID: %s", 
							customerId); 
			response =
					new CustomerResponse(CustomerResponse.ResultCodes.NOT_FOUND,
						message, null);
			LOG.info(message);

		}
		
		return response;

	}

}
