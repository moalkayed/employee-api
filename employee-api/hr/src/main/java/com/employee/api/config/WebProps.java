package com.employee.api.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "web")
@Component
public class WebProps {

	private List<String> allowHeaders = new ArrayList<String>();
	private List<String> allowMethods = new ArrayList<String>();
	private String allowOrigin;
	private String cacheControl;

	public List<String> getAllowHeaders() {
		return allowHeaders;
	}

	public void setAllowHeaders(List<String> allowHeaders) {
		this.allowHeaders = allowHeaders;
	}

	public List<String> getAllowMethods() {
		return allowMethods;
	}

	public void setAllowMethods(List<String> allowMethods) {
		this.allowMethods = allowMethods;
	}

	public String getAllowOrigin() {
		return allowOrigin;
	}

	public void setAllowOrigin(String allowOrigin) {
		this.allowOrigin = allowOrigin;
	}

	public String getCacheControl() {
		return cacheControl;
	}

	public void setCacheControl(String cacheControl) {
		this.cacheControl = cacheControl;
	}

}
