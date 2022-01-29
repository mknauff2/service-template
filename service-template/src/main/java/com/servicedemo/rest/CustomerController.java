/**
 * 
 */
package com.servicedemo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.servicedemo.model.Customer;
import com.servicedemo.rest.CustomerResponse.ResultCodes;
import com.servicedemo.service.CustomerService;

/**
 * @author mikeknauff
 *
 */

@RestController
@RequestMapping("customers/v1")
public class CustomerController {
	
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
		
		try {
		
			service.createCustomer(newCustomer);
					
			response = new CustomerResponse(ResultCodes.SUCCESS, "Customer created successfully");
		
		} catch (Exception e) {
			
			response = new CustomerResponse(ResultCodes.UNKNOWN, "Error creating customer");
						
		}
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
}
