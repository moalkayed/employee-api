package com.employee.api.error;

public class GlobalizedFault {

	private final String userLanguage;
	private final IError error;
	private String bundlePath;

	public GlobalizedFault(String language, String bundlePath, Fault fault) {
		this.userLanguage = language;
		this.bundlePath = bundlePath;
		this.error = fault.getError();
	}

	public int getCode() {
		return error.getCode();
	}

	public String getInternalMessage() {
		return error.getInternalMessage();
	}

	public int getResponseCode() {
		return error.getResponseCode();
	}

	public String getUserMessage() {
		return GlobalizedMessage.getUserErrorMessage(userLanguage, error.getLocalizationKey(), bundlePath);
	}
}