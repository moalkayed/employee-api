package com.employee.api.service;

public enum FilterableParam {

	FIRST_NAME("firstName"),
	LAST_NAME("lastName"),
	EMAIL("email"),
	PHONE_NUMBER("phoneNumber"),
	MARITAL_STATUS("maritalStatus"),
	DOB("DOB"),
    NATIONALITY("nationality"),
	HIRE_DATE("hireDate"),
	SALARY("salary"),
	MANAGER_ID("managerId"),
	DEPARTMENT_ID("depratmentId"),
	ACTIVE("active");
	
	private String value;

	private FilterableParam(String value) {
		this.value = value;
	}

	public String getParam() {
		return value;
	}

}
