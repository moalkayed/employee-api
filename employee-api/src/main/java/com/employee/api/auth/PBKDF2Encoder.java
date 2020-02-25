package com.employee.api.auth;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.employee.api.config.SecurityProps;

@Component
public class PBKDF2Encoder implements PasswordEncoder {

	@Autowired
	private SecurityProps configProperties;

	@Override
	public String encode(CharSequence cs) {
		String passwordSecret = configProperties.getPasswordEncoderSecret();
		int passwordIteration = configProperties.getPasswordEncoderIteration();
		int passwordKeyLength = configProperties.getPasswordEncoderKeylength();

		try {
			byte[] result = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512")
					.generateSecret(new PBEKeySpec(cs.toString().toCharArray(), passwordSecret.getBytes(),
							passwordIteration, passwordKeyLength))
					.getEncoded();
			return Base64.getEncoder().encodeToString(result);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public boolean matches(CharSequence cs, String string) {
		return encode(cs).equals(string);
	}

}
