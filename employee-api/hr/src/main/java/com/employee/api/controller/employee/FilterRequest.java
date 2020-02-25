package com.employee.api.controller.employee;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.employee.api.controller.Request;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(with = { JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES })
public class FilterRequest extends Request {

	@JsonProperty("firstName")
	private String firstName;

	@JsonProperty("lastName")
	private String lastName;

	@JsonProperty("email")
	private String email;

	@JsonProperty("phoneNumber")
	private String phoneNumber;

	@JsonProperty("maritalStatus")
	private String maritalStatus;

	@JsonProperty("DOB")
	private Integer YearOfBirthday;

	@JsonProperty("nationality")
	private String nationality;

	@JsonProperty("hireDateFrom")
	private Date hireDateFrom;

	@JsonProperty("hireDateTo")
	private Date hireDateTo;

	@JsonProperty("salary")
	private Double salary;

	@JsonProperty("managerId")
	private Long managerId;

	@JsonProperty("depratmentId")
	private Long depratmentId;

	@JsonProperty("active")
	private Boolean active;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Date getHireDateFrom() {
		return hireDateFrom;
	}

	public void setHireDateFrom(Date hireDateFrom) {
		this.hireDateFrom = hireDateFrom;
	}

	public Date getHireDateTo() {
		return hireDateTo;
	}

	public void setHireDateTo(Date hireDateTo) {
		this.hireDateTo = hireDateTo;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public Long getManagerId() {
		return managerId;
	}

	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}

	public Long getDepratmentId() {
		return depratmentId;
	}

	public void setDepratmentId(Long depratmentId) {
		this.depratmentId = depratmentId;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public Integer getYearOfBirthday() {
		return YearOfBirthday;
	}

	public void setYearOfBirthday(Integer yearOfBirthday) {
		YearOfBirthday = yearOfBirthday;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
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
