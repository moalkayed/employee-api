package com.employee.api.util;

public enum SortableParam {

	ID("id"), HIRE_DATE("hireDate"), SALARY("salary");

	private String value;

	private SortableParam(String value) {
		this.value = value;
	}

	public String getParam() {
		return value;
	}

}
