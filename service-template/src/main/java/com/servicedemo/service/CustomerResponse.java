/**
 * 
 */
package com.servicedemo.service;

import com.servicedemo.model.Customer;

/**
 * Provides the details of the result of a customer service call.
 * 
 * @author mikeknauff
 *
 */
public final class CustomerResponse {
	
	/**
	 * The valid results that can be returned by the service
	 * 
	 * @author mikeknauff
	 *
	 */
	public enum ResultCodes {
		
		SUCCESS(0),
		DUPLICATE(1),
		NOT_FOUND(2),
		BATCH_SIZE_EXCEEDED(3),
		NEW_SUBMITTED_AS_UPDATE(4),
		UNKNOWN(100);
		
		int value;
		
		ResultCodes(int value) { this.value = value; }
		
		public int getValue() { return value; }
	}
	
	private ResultCodes resultCode;
	private String responseDescription;
	private Customer customer;

	/**
	 * Creates a new CustomerResponse
	 * 
	 */
	public CustomerResponse() {
		// do nothing
	}
	
	/**
	 * Creates a new CustomerResponse
	 * 
	 * @param resultCode
	 * @param responseDescription
	 */
	public CustomerResponse(ResultCodes resultCode, String responseDescription, Customer customer) {
		
		this.resultCode = resultCode;
		this.responseDescription = responseDescription;
		this.customer = customer;
	}

	/**
	 * @return the resultCode
	 */
	public ResultCodes getResultCode() {
		return resultCode;
	}

	/**
	 * @param resultCode the resultCode to set
	 */
	public void setResultCode(ResultCodes resultCode) {
		this.resultCode = resultCode;
	}

	/**
	 * @return the responseDescription
	 */
	public String getResponseDescription() {
		return responseDescription;
	}

	/**
	 * @param responseDescription the responseDescription to set
	 */
	public void setResponseDescription(String responseDescription) {
		this.responseDescription = responseDescription;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
		
}
