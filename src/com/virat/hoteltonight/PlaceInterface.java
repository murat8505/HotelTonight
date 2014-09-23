package com.virat.hoteltonight;

public interface PlaceInterface {
	
	/**
	 * Downloads Google Place Details
	 * @param reference: a unique Google Place id
	 */
	public void downloadPlaceDetails(String reference);
	
	/**
	 * Shows the Google Place's name in the UI
	 * @param name
	 */
	public void showPlaceName(String name);
	
	/**
	 * Shows the Google Place's address in the UI
	 * @param address
	 */
	public void showPlaceAddress(String address);
	
	/**
	 * Shows the Google Place's phone in the UI
	 * @param phone
	 */
	public void showPlacePhoneNumber(String phone);
	
	/**
	 * Shows the Google Place's rating in the UI
	 * @param rating
	 */
	public void showPlaceRating(double rating);
	
}
