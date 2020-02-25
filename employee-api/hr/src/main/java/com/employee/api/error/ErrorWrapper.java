package com.employee.api.error;

import java.util.ArrayList;
import java.util.List;

public class ErrorWrapper {

	private List<GlobalizedFault> faults = new ArrayList<>();

	public ErrorWrapper() {
		super();
	}

	public ErrorWrapper(List<Fault> faults, String language, String bundlePath) {
		this.setFaults(faults, language, bundlePath);
	}

	public void setFaults(List<Fault> faults, String language, String bundlePath) {
		for (Fault fault : faults) {
			addFault(fault, language, bundlePath);
		}
	}

	public void addFault(Fault fault, String language, String bundlePath) {
		GlobalizedFault globalizedFault = new GlobalizedFault(language, bundlePath, fault);
		faults.add(globalizedFault);
	}

	public boolean hasError() {
		return faults != null && !faults.isEmpty();
	}

	public int getResponseCode() {
		return getFaults().get(0).getResponseCode();
	}

	public List<GlobalizedFault> getFaults() {
		return faults;
	}
}
