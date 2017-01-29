package com.kaucar.biggit.biggit.models;

import javax.persistence.Entity;

import com.google.gson.annotations.SerializedName;

@Entity
public class BitBucketRepository implements Repository {
	
	@SerializedName("full_name")
	private String fullName;
	
	private String name;
	private int id;
	
	private int internalId;

	public int getInternalId() {
		return internalId;
	}

	public void setInternalId(int internalId) {
		this.internalId = internalId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return fullName;
	}

}
