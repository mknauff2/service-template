/**
 * 
 */
package com.servicedemo.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.servicedemo.model.Customer;
import com.servicedemo.service.CustomerResponse;
import com.servicedemo.service.CustomerService;
import com.servicedemo.service.CustomerResponse.ResultCodes;

/**
 * @author mikeknauff
 *
 */

@RestController
@RequestMapping("customers/v1")
public class CustomerController {
	
	private static final Logger LOG = 
			LoggerFactory.getLogger(CustomerController.class);
	
	@Autowired
	CustomerService service;
	
	public CustomerController() {
		// do nothing
	}
	
	@PostMapping(
			value="/",
			headers={"REQUEST_ID", "CORR_ID"},
			consumes="application/json",
			produces="application/json")
	public ResponseEntity<CustomerResponse> createCustomer(
			@RequestHeader HttpHeaders httpHeaders,
			@RequestBody Customer newCustomer) {
		
		// TODO: Logging, Idempotency?, Swagger Doc
		
		CustomerResponse response;
		ResponseEntity<CustomerResponse> entity;
		
		try {
		
			response = service.createCustomer(newCustomer);
			entity = responseHandler(response);
					
					
		} catch (Exception e) {
			
			response = new CustomerResponse(ResultCodes.UNKNOWN, "Error creating customer");
			entity = responseHandler(response);
			LOG.debug(e.toString());
						
		}
		
		return entity;
	}
	
	@GetMapping(
			value="/{customerId}",
			headers={"REQUEST_ID", "CORR_ID"},
			produces="application/json")
	public ResponseEntity<Customer> getCustomer (
			@RequestHeader HttpHeaders httpHeaders,
			@PathVariable String customerId) {
		
		Customer customer = null;
		ResponseEntity<Customer> entity = null;
		
		try {
			
			customer = service.getCustomer(customerId);
			entity = responseHandler(customer);
			
		} catch (Exception e) {
			
			entity = responseHandler(customer);
			LOG.debug(e.toString());
		}
		
		return entity;
	}
	
	private ResponseEntity<CustomerResponse> responseHandler(final CustomerResponse response) {
		
		ResponseEntity<CustomerResponse> entity = null;
		ResultCodes result = response.getResultCode();
		
		if (result == ResultCodes.SUCCESS) {
			
			entity = new ResponseEntity<>(response, HttpStatus.OK);
			
		} else if (result == ResultCodes.UNKNOWN) {
			
			entity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			
		} else {
			
			// never get here
			throw new IllegalStateException("Invalid CustomerResponse State!");
			
		}
		
		return entity;
	}
	
private ResponseEntity<Customer> responseHandler(final Customer customer) {
		
		ResponseEntity<Customer> entity;
		
		if (customer != null) {
			
			entity = new ResponseEntity<>(customer, HttpStatus.OK);
			
		} else {
			
			entity = new ResponseEntity<>(customer, HttpStatus.NOT_FOUND);
			
		}
			
		return entity;
	}
	
}
