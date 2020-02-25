package com.employee.api.domain;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Employee implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -396451475058349369L;

	private final Long id;
	private final String firstName;
	private final String lastName;
	private final String email;
	private final String phoneNumber;
	private final String maritalStatus;
	private final Date DOB;
	private final String nationality;
	private final Date hireDate;
	private final Double salary;
	private final Long managerId;
	private final Long depratmentId;
	private final Boolean active;

	private Employee(EmployeeBuilder builder) {
		this.id = builder.id;
		this.firstName = builder.firstName;
		this.lastName = builder.lastName;
		this.email = builder.email;
		this.phoneNumber = builder.phoneNumber;
		this.maritalStatus = builder.maritalStatus;
		this.DOB = builder.DOB;
		this.nationality = builder.nationality;
		this.hireDate = builder.hireDate;
		this.salary = builder.salary;
		this.managerId = builder.managerId;
		this.depratmentId = builder.depratmentId;
		this.active = builder.active;
	}

	public static class EmployeeBuilder {

		private Long id;
		private String firstName;
		private String lastName;
		private String email;
		private String phoneNumber;
		private String maritalStatus;
		private Date DOB;
		private String nationality;
		private Date hireDate;
		private Double salary;
		private Long managerId;
		private Long depratmentId;
		private Boolean active;

		public EmployeeBuilder withId(Long id) {
			this.id = id;
			return this;
		}

		public EmployeeBuilder withFirstName(String firstName) {
			this.firstName = firstName;
			return this;
		}

		public EmployeeBuilder withLastName(String lastName) {
			this.lastName = lastName;
			return this;
		}

		public EmployeeBuilder withEmail(String email) {
			this.email = email;
			return this;
		}

		public EmployeeBuilder withPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
			return this;
		}

		public EmployeeBuilder setMaritalStatus(String maritalStatus) {
			this.maritalStatus = maritalStatus;
			return this;
		}

		public EmployeeBuilder setDOB(Date dOB) {
			DOB = dOB;
			return this;
		}

		public EmployeeBuilder setNationality(String nationality) {
			this.nationality = nationality;
			return this;

		}

		public EmployeeBuilder withHireDate(Date hireDate) {
			this.hireDate = hireDate;
			return this;
		}

		public EmployeeBuilder withSalary(Double salary) {
			this.salary = salary;
			return this;
		}

		public EmployeeBuilder withManagerId(Long managerId) {
			this.managerId = managerId;
			return this;
		}

		public EmployeeBuilder withDepratmentId(Long depratmentId) {
			this.depratmentId = depratmentId;
			return this;
		}

		public EmployeeBuilder withActive(Boolean active) {
			this.active = active;
			return this;
		}

		public Employee build() {
			return new Employee(this);
		}

	}

	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public Date getDOB() {
		return DOB;
	}

	public String getNationality() {
		return nationality;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public Double getSalary() {
		return salary;
	}

	public Long getManagerId() {
		return managerId;
	}

	public Long getDepratmentId() {
		return depratmentId;
	}

	public Boolean getActive() {
		return active;
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
