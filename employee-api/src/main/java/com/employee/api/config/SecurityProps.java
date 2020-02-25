package com.employee.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "security")
@Component
public class SecurityProps {

	private String passwordEncoderSecret;
	private int passwordEncoderIteration;
	private int passwordEncoderKeylength;
	private String jjwtSecret;
	private long jjwtExpiration;
	private String adminUsername;
	private String adminPassword;
	private String adminEncryptedPassword;

	public String getPasswordEncoderSecret() {
		return passwordEncoderSecret;
	}

	public void setPasswordEncoderSecret(String passwordEncoderSecret) {
		this.passwordEncoderSecret = passwordEncoderSecret;
	}

	public int getPasswordEncoderIteration() {
		return passwordEncoderIteration;
	}

	public void setPasswordEncoderIteration(int passwordEncoderIteration) {
		this.passwordEncoderIteration = passwordEncoderIteration;
	}

	public int getPasswordEncoderKeylength() {
		return passwordEncoderKeylength;
	}

	public void setPasswordEncoderKeylength(int passwordEncoderKeylength) {
		this.passwordEncoderKeylength = passwordEncoderKeylength;
	}

	public String getJjwtSecret() {
		return jjwtSecret;
	}

	public void setJjwtSecret(String jjwtSecret) {
		this.jjwtSecret = jjwtSecret;
	}

	public long getJjwtExpiration() {
		return jjwtExpiration;
	}

	public void setJjwtExpiration(long jjwtExpiration) {
		this.jjwtExpiration = jjwtExpiration;
	}

	public String getAdminUsername() {
		return adminUsername;
	}

	public void setAdminUsername(String adminUsername) {
		this.adminUsername = adminUsername;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	public String getAdminEncryptedPassword() {
		return adminEncryptedPassword;
	}

	public void setAdminEncryptedPassword(String adminEncryptedPassword) {
		this.adminEncryptedPassword = adminEncryptedPassword;
	}

}