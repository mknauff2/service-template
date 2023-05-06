/**
 * 
 */
package com.servicedemo.model;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


/**
 * @author mikeknauff
 *
 */
@Component
@Entity
@Table(name="customer")
public class Customer {
	
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="RES_ID", unique=true)
	private long resourceId;

	@Column(name="CUST_ID", unique=true)
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
	 * default constructor
	 */
	public Customer() {
		//do nothing		
	}
	
	/**
	 * Copy constructor 
	 * 
	 * @param other customer to copy
	 * 
	 */
	public Customer(Customer other) {
		super();
		
		this.resourceId = other.resourceId;
		this.customerId = other.customerId;
		this.firstName = other.firstName;
		this.lastName = other.lastName;
		this.streetAddress1 = other.streetAddress1;
		this.streetAddress2 = other.streetAddress2;
		this.city = other.city;
		this.state = other.state;
		this.zipCode = other.zipCode;
		
	}
	
	/**
	 * Construct a customer from a builder object
	 * 
	 * @param builder
	 */
	private Customer(CustomerBuilder builder) {
		super();
		
		this.resourceId = builder.resourceId;
		this.customerId = builder.customerId;
		this.firstName = builder.firstName;
		this.lastName = builder.lastName;
		this.streetAddress1 = builder.streetAddress1;
		this.streetAddress2 = builder.streetAddress2;
		this.city = builder.city;
		this.state = builder.state;
		this.zipCode = builder.zipCode;
		
	}
	
		
	public long getResourceId() {
		return resourceId;
	}

	public void setResourceId(long resourceId) {
		this.resourceId = resourceId;
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
	
	/**
	 * Convenience class to build a customer object
	 * 
	 * @author mikeknauff
	 *
	 */
	public static class CustomerBuilder {
		
		private Long   resourceId;
		private String customerId;
		private String firstName;
		private String lastName;
		private String streetAddress1;
		private String streetAddress2;
		private String city;
		private String state;
		private String zipCode;
		
		public CustomerBuilder() {
			// do nothing
		}
		
		public CustomerBuilder resourceId(Long resourceId) {
			this.resourceId = resourceId;
			return this;
		}

		public CustomerBuilder customerId(String customerId) {
			this.customerId = customerId;
			return this;
		}

		public CustomerBuilder firstName(String firstName) {
			this.firstName = firstName;
			return this;
		}

		public CustomerBuilder lastName(String lastName) {
			this.lastName = lastName;
			return this;
		}

		public CustomerBuilder streetAddress1(String streetAddress1) {
			this.streetAddress1 = streetAddress1;
			return this;
		}

		public CustomerBuilder streetAddress2(String streetAddress2) {
			this.streetAddress2 = streetAddress2;
			return this;
		}

		public CustomerBuilder city(String city) {
			this.city = city;
			return this;
		}

		public CustomerBuilder state(String state) {
			this.state = state;
			return this;
		}

		public CustomerBuilder zipCode(String zipCode) {
			this.zipCode = zipCode;
			return this;
		}
		
		public Customer build() {
			return new Customer(this);
		}
				
	}
	
}
