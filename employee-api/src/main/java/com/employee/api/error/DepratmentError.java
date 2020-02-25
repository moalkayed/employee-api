package com.employee.api.error;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public enum DepratmentError implements IError {

	/**
	 * 
	 */
	DEPARTMENT_NOT_FOUND(2001, 404, "DEPARTMENT_NOT_FOUND", "Depratment not found");

	private final int code;
	private final int responseCode;
	private final String internalMessage;
	private final String localizationKey;

	private final static Map<Integer, Error> errorsMap = new HashMap<Integer, Error>();

	private DepratmentError(final int code, final int httpResponseCode, final String localizationKey,
			final String internalMessage) {
		this.code = code;
		this.internalMessage = internalMessage;
		this.localizationKey = localizationKey;
		this.responseCode = httpResponseCode;
	}

	@Override
	public int getCode() {
		return code;
	}

	@Override
	public String getInternalMessage() {
		return internalMessage;
	}

	@Override
	public int getResponseCode() {
		return responseCode;
	}

	@Override
	public String getLocalizationKey() {
		return localizationKey;
	}

	public static Error getError(int errorCode) {
		return errorsMap.get(errorCode);
	}

	public static class Factory {
		private static Map<String[], DepratmentError> errorCodeReference = new HashMap<String[], DepratmentError>();

		public static DepratmentError getByCode(String code) {
			for (Map.Entry<String[], DepratmentError> error : errorCodeReference.entrySet()) {
				DepratmentError employeeError = getByCode(error, code);
				if (employeeError == null) {
					continue;
				}

				return employeeError;

			}
			return null;

		}

		public static DepratmentError getByCode(Entry<String[], DepratmentError> error, String code) {
			String[] codes = error.getKey();

			if (codes == null || codes.length == 0) {
				return null;
			}
			for (int i = 0; i < codes.length; i++) {
				if (codes[i].equals(code)) {
					return error.getValue();
				}
			}
			return null;

		}

	}

}
