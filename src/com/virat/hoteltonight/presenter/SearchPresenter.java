package com.virat.hoteltonight.presenter;

import java.util.ArrayList;
import java.util.Map;

import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.util.Log;

import com.virat.hoteltonight.SearchInterface;
import com.virat.hoteltonight.model.PlacesJSONParser;
import com.virat.hoteltonight.view.PlaceActivity;
import com.virat.hoteltonight.view.SearchActivity;

/*
 * A presenter object 
 */
public class SearchPresenter implements SearchInterface {

	// For debugging/logging
	private static final String TAG = "SearchPresenter";

	// Columns for a Cursor object
	private static final String column_id = "_id";
	private static final String column_text = "text";

	// A reference to the main Activity
	private SearchActivity activity;

	// Collection objects for holding references to Google Places
	private ArrayList<String> mSuggestionsList;
	private Map<String, String> mSuggestionsMap;

	// A thread for doing network operations in the background
	private Thread mThread;

	public SearchPresenter(SearchActivity activity) {
		this.activity = activity;
	}

	@Override
	public ArrayList<String> autoComplete(final String input) {
		mSuggestionsList = new ArrayList<String>();

		// Limit Place requests to keywords that are over 2 characters long
		if (input.length() > 2) {
        	
			// Stop any previous thread(s) before requesting
			// any new Google Place suggestions
			if (mThread != null) {
				mThread = null;
			}
	
			// Create and start a new Thread to perform
			// a Google Place search request. Use the request's results
			// to populate an ArrayList, which the SearchActivity's
			// SearchView will use for displaying search suggestions
			mThread = new Thread(new Runnable() {
	
				@Override
				public void run() {
					// Download Google Place suggestions using the user's input
					PlacesJSONParser parser = new PlacesJSONParser();
					mSuggestionsList.addAll(parser.autoComplete(input));
					mSuggestionsMap = parser.getPlaceSuggestionsMap();
	
					// Update the SearchView's suggestions
					activity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							activity.setSuggestionsAdapter(mSuggestionsList);
						}
					});
					Log.d(TAG, "suggestions list is: " + mSuggestionsList);
				}
			});
			mThread.start();
		}

		return mSuggestionsList;
	}

	@Override
	public void launchActivity(Intent intent) {
		activity.launchActivity(intent);
	}

	/**
	 * Loads data from a list of Google Places (mSuggestionsList) and
	 * returns the data as a Cursor object.
	 */
	public Cursor getSuggestionsCursor() {

		// Load data from list to cursor
		String[] columns = new String[] { column_id, column_text };
		Object[] temp = new Object[] { 0, "default" };

		// Create a new Cursor object
		MatrixCursor cursor = new MatrixCursor(columns);

		for (int i = 0; i < mSuggestionsList.size(); i++) {

			temp[0] = i;
			temp[1] = mSuggestionsList.get(i);

			// Add the  Google Place data as a row in the Cursor object
			cursor.addRow(temp);
		}

		Log.d(TAG, "suggestions size is: " + mSuggestionsList.size());

		return cursor;
	}

	/**
	 * Launches a PlaceActivity using an Intent that contains the unique
	 * reference id of a Google Place
	 * @param cursor
	 */
	public void onSuggestionClick(Cursor cursor) {
		if (cursor == null) {
			Log.d(TAG, "column text (cursor) is null");
			return;
		}

		// Retrieve the Place name from the Cursor
		int column_index = cursor.getColumnIndex(column_text);
		String place_name = cursor.getString(column_index);

		Log.d(TAG, "column text is: " + place_name);

		// Get the Place's unique reference id
		String place_reference = "";
		if (mSuggestionsMap != null) {
			place_reference = mSuggestionsMap.get(place_name);
		}

		// Launch a Place Activity with a unique Google Place reference id
		Intent intent = new Intent(activity, PlaceActivity.class);
		intent.putExtra(PlaceActivity.PLACE_REFERENCE, place_reference);
		launchActivity(intent);
	}
}
