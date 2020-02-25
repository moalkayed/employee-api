package com.employee.api.error;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = FaultCustomSerializer.class)
public class Fault {

	private final IError error;

	public Fault(final IError error) {
		this.error = error;
	}

	public IError getError() {
		return error;
	}

}