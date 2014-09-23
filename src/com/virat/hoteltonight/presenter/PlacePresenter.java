package com.virat.hoteltonight.presenter;

import android.util.Log;

import com.virat.hoteltonight.PlaceInterface;
import com.virat.hoteltonight.model.Place;
import com.virat.hoteltonight.model.PlacesJSONParser;
import com.virat.hoteltonight.view.PlaceActivity;

public class PlacePresenter implements PlaceInterface{
	
	private static final String TAG = "PlacePresenter";

	private PlaceActivity activity;
	
	private Thread mThread;
	
	public PlacePresenter(PlaceActivity activity) {
		this.activity = activity;
	}
	
	@Override
	public void downloadPlaceDetails(final String reference) {
		Log.d(TAG, "downloadPlaceDetails() called");
		mThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				Log.d(TAG, "thread is running");
				PlacesJSONParser parser = new PlacesJSONParser();
				final Place place = parser.getPlaceDetails(reference);
				activity.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						updateUI(place);
					}
				});
			}
		});
		mThread.start();
	}
	
	/**
	 * Updates PlaceActivity's UI with the given Google Place details
	 * @param place
	 */
	public void updateUI(Place place) {
		Log.d(TAG, "updateUI called");
		showPlaceName(place.getName());
		
		showPlaceAddress(place.getAddress());
		
		showPlacePhoneNumber(place.getPhoneNumber());
		
		showPlaceRating(place.getRating());
	}
	
	@Override
	public void showPlaceAddress(String address) {
		Log.d(TAG, "address is: " + address);
		activity.showPlaceAddress(address);
	}
	
	@Override
	public void showPlaceName(String name) {
		Log.d(TAG, "name is: " + name);
		activity.showPlaceName(name);
	}
	
	@Override
	public void showPlacePhoneNumber(String phone) {
		Log.d(TAG, "phone is: " + phone);
		activity.showPlacePhoneNumber(phone);
	}	
	
	@Override
	public void showPlaceRating(double rating) {
		activity.showPlaceRating(rating);
	}
}
