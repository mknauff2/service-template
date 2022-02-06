/**
 * 
 */
package com.servicedemo.service;

import java.text.MessageFormat;
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
			
			repository.save(customer);
			response = 
					new CustomerResponse(CustomerResponse.ResultCodes.SUCCESS,"");
			LOG.info("Customer saved: {}", customer);
						
		} catch (Exception e) {
			
			// TODO: see if we can catch specific errors such as duplicates and 
			//       fields that are null
			//
			response =
					new CustomerResponse(CustomerResponse.ResultCodes.UNKNOWN,
							"An unknown error occured attempting to save the customer");
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Customer> getCustomerByLastName(String lastName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CustomerResponse updateCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return null;

	}

	@Override
	public CustomerResponse deleteCustomer(String customerId) {
		// TODO Auto-generated method stub
		return null;

	}

}
