package com.kaucar.biggit.biggit.models;

public class BitBucketCommit implements Commit{
	
	private String message;

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		return message;
	}

}
