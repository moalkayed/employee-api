package com.employee.api.domain;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Department implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1513448273082781775L;

	private final Long id;
	private final String name;
	private final Long managerId;

	private Department(DepartmentBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.managerId = builder.managerId;
	}

	public static class DepartmentBuilder {

		private Long id;
		private String name;
		private Long managerId;

		public DepartmentBuilder withId(Long id) {
			this.id = id;
			return this;
		}

		public DepartmentBuilder withName(String name) {
			this.name = name;
			return this;
		}

		public DepartmentBuilder withManagerId(Long managerId) {
			this.managerId = managerId;
			return this;
		}

		public Department build() {
			return new Department(this);
		}

	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Long getManagerId() {
		return managerId;
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
