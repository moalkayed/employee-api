package com.employee.api.error;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public enum DefaultError implements IError {

	INTERNAL_SERVER_ERROR(1001, 500, "INTERNAL_SERVER_ERROR", "internal server error"), 
	CLIENT_SIDE_ERROR_BAD_REQUEST(1002, 400, "CLIENT_SIDE_ERROR_BAD_REQUEST", "Please check your request format"), 
	CLIENT_SIDE_ERROR_CONTENT_TYPE_JSON(1003, 415, "CLIENT_SIDE_ERROR_CONTENT_TYPE_JSON", "Client side error content type json");	

	private final int code;
	private final int responseCode;
	private final String internalMessage;
	private final String localizationKey;

	private final static Map<Integer, Error> errorsMap = new HashMap<Integer, Error>();

	private DefaultError(final int code, final int httpResponseCode, final String localizationKey,
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
		private static Map<String[], DefaultError> errorCodeReference = new HashMap<String[], DefaultError>();

		public static DefaultError getByCode(String code) {
			for (Map.Entry<String[], DefaultError> error : errorCodeReference.entrySet()) {
				DefaultError defaultError = getByCode(error, code);
				if (defaultError == null) {
					continue;
				}

				return defaultError;

			}
			return null;

		}

		public static DefaultError getByCode(Entry<String[], DefaultError> error, String code) {
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
