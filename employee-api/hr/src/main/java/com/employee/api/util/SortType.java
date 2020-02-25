package com.employee.api.util;

public enum SortType {

	ASC("asc"), DESC("desc");

	private String value;

	private SortType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
