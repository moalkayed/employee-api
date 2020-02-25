package com.employee.api.error;

import java.util.Locale;
import java.util.ResourceBundle;

public class GlobalizedMessage {

	public static String getUserErrorMessage(String language, String localizationKey, String bundlePath) {
		Locale locale = new Locale(language);
		ResourceBundle resourceBundle = ResourceBundle.getBundle(bundlePath, locale);
		if (resourceBundle.containsKey(localizationKey)) {
			String userMessage = resourceBundle.getString(localizationKey);
			return userMessage;
		}
		return localizationKey;
	}

}
