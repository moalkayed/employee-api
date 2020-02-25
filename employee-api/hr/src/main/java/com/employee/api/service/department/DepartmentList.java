package com.employee.api.service.department;

import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.employee.api.domain.Department;
import com.employee.api.util.Pageable;

public class DepartmentList {

	private final List<Department> departments;
	private final Pageable pageable;

	private DepartmentList(DepartmentListBuilder employeeBuilder) {
		this.departments = employeeBuilder.departments;
		this.pageable = employeeBuilder.pageable;
	}

	public static class DepartmentListBuilder {

		private List<Department> departments;
		private Pageable pageable;

		public DepartmentListBuilder withDepartments(List<Department> departments) {
			this.departments = departments;
			return this;
		}

		public DepartmentListBuilder withPageable(Pageable pageable) {
			this.pageable = pageable;
			return this;
		}

		public DepartmentList build() {
			return new DepartmentList(this);
		}
	}

	public List<Department> getDepartments() {
		return departments;
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
