package com.employee.api.service.employee;

import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.employee.api.domain.Employee;
import com.employee.api.util.Pageable;

public class EmployeeList {

	private final List<Employee> employees;
	private final Pageable pageable;

	private EmployeeList(EmployeeListBuilder employeeBuilder) {
		this.employees = employeeBuilder.employees;
		this.pageable = employeeBuilder.pageable;
	}

	public static class EmployeeListBuilder {

		private List<Employee> employees;
		private Pageable pageable;

		public EmployeeListBuilder withEmployees(List<Employee> employees) {
			this.employees = employees;
			return this;
		}

		public EmployeeListBuilder withPageable(Pageable pageable) {
			this.pageable = pageable;
			return this;
		}

		public EmployeeList build() {
			return new EmployeeList(this);
		}
	}

	

	public List<Employee> getEmployees() {
		return employees;
	}

	public Pageable getPageable() {
		return pageable;
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
