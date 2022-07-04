/**
 * 
 */
package com.servicedemo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

/**
 * @author mikeknauff
 *
 */
@Component
@Entity
@Table(name="customer")
public class Customer {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="RES_ID", unique=true)
	private long resourceId;

	@Column(name="CUST_ID")
	private String customerId;
	
	@Column(name="FIRST_NM")
	private String firstName;
	
	@Column(name="LAST_NM")
	private String lastName;
	
	@Column(name="ADD_ST_1")
	private String streetAddress1;
	
	@Column(name="ADD_ST_2")
	private String streetAddress2;
	
	@Column(name="ADD_CITY")
	private String city;
	
	@Column(name="ADD_STAT")
	private String state;
	
	@Column(name="ADD_ZIP")
	private String zipCode;
	
	/**
	 * 
	 */
	public Customer() {
		//do nothing		
	}
	
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getStreetAddress1() {
		return streetAddress1;
	}

	public void setStreetAddress1(String streetAddress1) {
		this.streetAddress1 = streetAddress1;
	}
	
	public String getStreetAddress2() {
		return streetAddress2;
	}

	public void setStreetAddress2(String streetAddress2) {
		this.streetAddress2 = streetAddress2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Override
	public String toString() {
		return "Customer [resourceId=" + resourceId + ", customerId=" + customerId + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", streetAddress1=" + streetAddress1 + ", streetAddress2=" + streetAddress2
				+ ", city=" + city + ", state=" + state + ", zipCode=" + zipCode + "]";
	}

	
}
