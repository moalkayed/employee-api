package com.employee.api.error.exception;

import java.util.ArrayList;
import java.util.List;

import com.employee.api.error.DefaultError;
import com.employee.api.error.DepratmentError;
import com.employee.api.error.EmployeeError;
import com.employee.api.error.Fault;
import com.employee.api.error.IError;

@SuppressWarnings("serial")
public class GenericException extends Exception {

	private final List<Fault> faults;

	private DefaultError defaultError;

	private IError error;

	public GenericException(Fault fault, Throwable cause) {
		super(cause);

		faults = new ArrayList<Fault>();
		faults.add(fault);
	}

	public GenericException(DefaultError error) {
		faults = new ArrayList<Fault>();
		Fault fault = new Fault(error);
		faults.add(fault);
	}

	public GenericException(IError error, String message) {
		super(message);

		this.error = error;

		faults = new ArrayList<Fault>();
		Fault fault = new Fault(error);
		faults.add(fault);
	}

	public GenericException(EmployeeError error, Throwable cause) {
		super(cause.getMessage());

		this.error = error;

		faults = new ArrayList<Fault>();
		Fault fault = new Fault(error);
		faults.add(fault);
	}

	public GenericException(DepratmentError error, Throwable cause) {
		super(cause.getMessage());

		this.error = error;

		faults = new ArrayList<Fault>();
		Fault fault = new Fault(error);
		faults.add(fault);
	}

	public GenericException(List<Fault> faults) {
		this.faults = faults;
	}

	public GenericException(DefaultError error, Throwable cause) {
		super(cause.getMessage());

		this.error = error;

		faults = new ArrayList<Fault>();
		Fault fault = new Fault(error);
		faults.add(fault);
	}

	public List<Fault> getFaults() {
		return faults;
	}

	public void addFault(Fault fault) {
		this.faults.add(fault);
	}

	public DefaultError getDefaultError() {
		return defaultError;
	}

	public IError getError() {
		return error;
	}

}
