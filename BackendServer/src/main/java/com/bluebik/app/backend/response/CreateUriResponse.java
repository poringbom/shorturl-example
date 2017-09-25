package com.bluebik.app.backend.response;

public class CreateUriResponse extends DefaultResponse{
	private String uri;
	private String shortUri;
	
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getShortUri() {
		return shortUri;
	}
	public void setShortUri(String shortUri) {
		this.shortUri = shortUri;
	}
}
