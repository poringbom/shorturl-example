package com.bluebik.app.backend.response;

public class UserLoginResponse extends UserCreateResponse {
	
	private String username;
	
	private String secretKey;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	
	
	
}
