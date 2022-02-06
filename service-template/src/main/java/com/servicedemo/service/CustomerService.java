/**
 * 
 */
package com.servicedemo.service;

import java.util.List;

import com.servicedemo.model.Customer;


/**
 * Defines the standard processing methods for a Customer
 * 
 * @author mikeknauff
 *
 */
public interface CustomerService {
	
	/**
	 * Create a unique customer (idempotent).
	 * 
	 * @param customer The customer to create
	 * @return The status of the request if successful, otherwise the error and 
	 * description
	 */
	CustomerResponse createCustomer(Customer customer);
	
	/**
	 * Returns the customer with the Customer ID natural key (safe and idempotent).
	 * 
	 * @param customerID The natural key of the customer
	 * @return The Customer object if found, otherwise an empty or null object
	 */
	Customer getCustomer(final String customerId);
	
	/**
	 * Returns the customer with the internal key (safe and idempotent).
	 *  
	 * @param resourceId The internally generated key of the customer
	 * @return The Customer object if found, otherwise an empty or null object  
	 */
	Customer getCustomer(final Long resourceId);
	
	/**
	 * Returns one or more customers with the matching last name (safe and idempotent).
	 *  
	 * @param lastName The last name of the customer(s)
	 * @return Returns one of more customers as a list if found, otherwise returns
	 *  an empty or null list
	 */
	List<Customer> getCustomerByLastName(String lastName);
	
	/**
	 * Updates the customer resource with the data provided in the customer object.
	 * (idempotent)
	 * 
	 * @param customer The customer data to update
	 * @return The status of the customer update, otherwise the error and the
	 * error description
	 */
	CustomerResponse updateCustomer(Customer customer);
	
	/**
	 * Deletes the customer specified by the CustomerId (idempotent).
	 * 
	 * @param customerId The natural key of the customer to delete
	 * @return The status of the deletion, otherwise the error and description
	 * of the error 
	 */
	CustomerResponse deleteCustomer(String customerId);

}
