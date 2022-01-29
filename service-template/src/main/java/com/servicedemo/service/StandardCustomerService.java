/**
 * 
 */
package com.servicedemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.servicedemo.model.Customer;
import com.servicedemo.repository.CustomerRepository;

/**
 * @author mikeknauff
 *
 */
public class StandardCustomerService implements CustomerService {
	
	@Autowired
	private CustomerRepository customerRepo;

	/**
	 * 
	 */
	public StandardCustomerService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createCustomer(Customer customer) {
		// TODO Auto-generated method stub

	}

	@Override
	public Customer getCustomer(String customerId) {
		// TODO Auto-generated method stub
		return null;
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
	public void updateCustomer(Customer customer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteCustomer(String customerId) {
		// TODO Auto-generated method stub

	}

}
