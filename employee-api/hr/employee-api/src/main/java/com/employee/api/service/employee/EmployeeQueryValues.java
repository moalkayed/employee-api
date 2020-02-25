package com.employee.api.service.employee;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class EmployeeQueryValues {

	private final String firstName;
	private final String lastName;
	private final String email;
	private final String phoneNumber;
	private final String maritalStatus;
	private final Integer yearOfBirthday;
	private final String nationality;
	private final Date hireDateFrom;
	private final Date hireDateTo;
	private final Double salary;
	private final Long managerId;
	private final Long depratmentId;
	private final Boolean active;

	private EmployeeQueryValues(EmployeeQueryValuesBuilder builder) {
		this.firstName = builder.firstName;
		this.lastName = builder.lastName;
		this.email = builder.email;
		this.phoneNumber = builder.phoneNumber;
		this.maritalStatus = builder.maritalStatus;
		this.yearOfBirthday = builder.yearOfBirthday;
		this.nationality = builder.nationality;
		this.hireDateFrom = builder.hireDateFrom;
		this.hireDateTo = builder.hireDateTo;
		this.salary = builder.salary;
		this.managerId = builder.managerId;
		this.depratmentId = builder.depratmentId;
		this.active = builder.active;
	}

	public static class EmployeeQueryValuesBuilder {

		private String firstName;
		private String lastName;
		private String email;
		private String phoneNumber;
		private String maritalStatus;
		private Integer yearOfBirthday;
		private String nationality;
		private Date hireDateFrom;
		private Date hireDateTo;
		private Double salary;
		private Long managerId;
		private Long depratmentId;
		private Boolean active;

		public EmployeeQueryValuesBuilder withFirstName(String firstName) {
			this.firstName = firstName;
			return this;
		}

		public EmployeeQueryValuesBuilder withLastName(String lastName) {
			this.lastName = lastName;
			return this;
		}

		public EmployeeQueryValuesBuilder withEmail(String email) {
			this.email = email;
			return this;
		}

		public EmployeeQueryValuesBuilder withPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
			return this;
		}

		public EmployeeQueryValuesBuilder withMaritalStatus(String maritalStatus) {
			this.maritalStatus = maritalStatus;
			return this;
		}

		public EmployeeQueryValuesBuilder withYearOfBirthday(Integer yearOfBirthday) {
			this.yearOfBirthday = yearOfBirthday;
			return this;
		}

		public EmployeeQueryValuesBuilder withNationality(String nationality) {
			this.nationality = nationality;
			return this;
		}

		public EmployeeQueryValuesBuilder withHireDateFrom(Date hireDateFrom) {
			this.hireDateFrom = hireDateFrom;
			return this;
		}

		public EmployeeQueryValuesBuilder withHireDateTo(Date hireDateTo) {
			this.hireDateTo = hireDateTo;
			return this;
		}

		public EmployeeQueryValuesBuilder withSalary(Double salary) {
			this.salary = salary;
			return this;
		}

		public EmployeeQueryValuesBuilder withManagerId(Long managerId) {
			this.managerId = managerId;
			return this;
		}

		public EmployeeQueryValuesBuilder withDepratmentId(Long depratmentId) {
			this.depratmentId = depratmentId;
			return this;
		}

		public EmployeeQueryValuesBuilder withActive(Boolean active) {
			this.active = active;
			return this;
		}

		public EmployeeQueryValues build() {
			return new EmployeeQueryValues(this);
		}

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

	public String getNationality() {
		return nationality;
	}

	public Date getHireDateFrom() {
		return hireDateFrom;
	}

	public Date getHireDateTo() {
		return hireDateTo;
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

	public Integer getYearOfBirthday() {
		return yearOfBirthday;
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
