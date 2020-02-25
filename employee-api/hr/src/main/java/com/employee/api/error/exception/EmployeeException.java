package com.employee.api.error.exception;

import com.employee.api.error.DefaultError;
import com.employee.api.error.EmployeeError;

@SuppressWarnings("serial")
public class EmployeeException extends GenericException {

	public EmployeeException(EmployeeError error, Throwable cause) {
		super(error, cause);
	}

	public EmployeeException(EmployeeError error, String cause) {
		super(error, cause);
	}

	public EmployeeException(DefaultError error, Throwable cause) {
		super(error, cause);
	}

	public EmployeeException(DefaultError error, String message) {
		super(error, message);
	}

}
