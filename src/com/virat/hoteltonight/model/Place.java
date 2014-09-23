package com.virat.hoteltonight.model;

import java.util.UUID;

/**
 * A POJO that represents a Google Place 
 * @author Virat
 *
 */
public class Place {
	private UUID mId;
	private String mName;
	private String mAddress;
	private String mPhoneNumber;
	private double mRating;

	public Place() {
		this("", "", "", 0.0);
	}
	
	public Place(String name, String address, String phoneNumber, double rating) {
		mId = UUID.randomUUID();
		mName = name;
		mAddress = address;
		mPhoneNumber = phoneNumber;
		mRating = rating;
	}
	
	public UUID getId() {
		return mId;
	}
	
	public String getName() {
		return mName;
	}
	
	public void setName(String name) {
		mName = name;
	}

	public String getAddress() {
		return mAddress;
	}

	public void setAddress(String address) {
		mAddress = address;
	}

	public String getPhoneNumber() {
		return mPhoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		mPhoneNumber = phoneNumber;
	}

	public double getRating() {
		return mRating;
	}

	public void setRating(double rating) {
		mRating = rating;
	}
}
