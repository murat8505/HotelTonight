package com.virat.hoteltonight.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.virat.hoteltonight.PlaceInterface;
import com.virat.hoteltonight.R;
import com.virat.hoteltonight.presenter.PlacePresenter;

public class PlaceActivity extends Activity implements PlaceInterface {
	public static final String PLACE_REFERENCE = "place_reference";

	private PlacePresenter presenter;
	
	private TextView mPlaceName;
	private TextView mPlaceAddress;
	private TextView mPlacePhone;
	private TextView mPlaceRating;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_place);
		
		// Create a presenter object
		presenter = new PlacePresenter(this);
		
		// Retrieve the Place's unique reference id and
		// download its Google Place Details
		Intent intent = getIntent();
		String place_reference = intent.getStringExtra(PLACE_REFERENCE);
		presenter.downloadPlaceDetails(place_reference);
		
		// Wire up the TextViews
		mPlaceName = (TextView) findViewById(R.id.place_name_textView);
		mPlaceAddress = (TextView) findViewById(R.id.place_address_textView);
		mPlacePhone = (TextView) findViewById(R.id.place_phone_textView);
		mPlaceRating = (TextView) findViewById(R.id.place_rating_textView);
		
	}

	@Override
	public void downloadPlaceDetails(String reference) {
		// This method intentionally blank	
	}

	@Override
	public void showPlaceName(String name) {
		mPlaceName.setText(name);
	}

	@Override
	public void showPlaceAddress(String address) {
		mPlaceAddress.setText(address);
	}

	@Override
	public void showPlacePhoneNumber(String phone) {
		mPlacePhone.setText(phone);
	}
	
	@Override
	public void showPlaceRating(double rating) {
		mPlaceRating.setText("Rating: " + rating + " out of 5");
	}
}
