package com.employee.api.error.exception;

import com.employee.api.error.DefaultError;
import com.employee.api.error.DepratmentError;

@SuppressWarnings("serial")
public class DepartmentException extends GenericException {

	public DepartmentException(DepratmentError error, Throwable cause) {
		super(error, cause);
	}

	public DepartmentException(DepratmentError error, String cause) {
		super(error, cause);
	}

	public DepartmentException(DefaultError error, Throwable cause) {
		super(error, cause);
	}

	public DepartmentException(DefaultError error, String message) {
		super(error, message);
	}

}
