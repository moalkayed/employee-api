package com.employee.api.error;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "code")
public interface IError {

	public abstract int getCode();

	public abstract String getInternalMessage();

	public abstract String getLocalizationKey();

	public abstract int getResponseCode();

}
