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
			
			// TODO: Place a policy that does not allow a duplicate customer to be
			//       created. A duplicate is defined by any customer with the same
			//       customer ID.
			if (getCustomer(customer.getCustomerId()).getResultCode() == CustomerResponse.ResultCodes.SUCCESS) {
				response =
						new CustomerResponse(CustomerResponse.ResultCodes.DUPLICATE,
								String.format("This customer %s already exists", customer.getCustomerId()), null);
				
				return response;
			}
			
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
	public CustomerResponse getCustomer(String customerId) {
		
		CustomerResponse response;
		
		Optional<Customer> optCustomer = repository.findByCustomerId(customerId);
		
		if (optCustomer.isPresent()) {
			
			Customer customer = optCustomer.get();
			
			response = 
					new CustomerResponse(CustomerResponse.ResultCodes.SUCCESS,"", customer);
			LOG.info("Retrieved customer: {}", customer);
			
			return response;
			
		} else {
			
			// customer not found
			response = 
					new CustomerResponse(CustomerResponse.ResultCodes.NOT_FOUND,"Customer not found", null);
			LOG.info("Customer not found with customerId {}", customerId);
			
			return response;			
		}
	}

	@Override
	public CustomerResponse getCustomer(Long resourceId) {
		
		CustomerResponse response; 
		
		Optional<Customer> optCustomer = repository.findById(resourceId);
		
		if (optCustomer.isPresent()) {
			
			Customer customer = optCustomer.get();
			
			response = 
					new CustomerResponse(CustomerResponse.ResultCodes.SUCCESS,"", customer);
			LOG.info("Retrieved customer: {}", customer);
			
			return response;
			
		} else {
			
			// customer not found
			response = 
					new CustomerResponse(CustomerResponse.ResultCodes.NOT_FOUND,"Customer not found", null);
			LOG.info("Customer not found with resourceID {}", resourceId);
			
			return response;
			
		}

	}

	@Override
	public List<Customer> getCustomerByLastName(String lastName) {
		
		return repository.findByLastName(lastName);
				
	}

	@Override
	public CustomerResponse updateCustomer(Customer customer) {
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
