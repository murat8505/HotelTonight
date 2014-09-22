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

public class SearchPresenter implements SearchInterface {

	private static final String TAG = "SearchPresenter";
	
	private static final String column_id = "_id";
	private static final String column_text = "text";
	
	// A reference to the main Activity
	private SearchActivity activity;
	
	
	private ArrayList<String> mSuggestionsList;
	private Map<String, String> mSuggestionsMap;
	
	private Thread mThread;
	
	public SearchPresenter(SearchActivity activity) {
		this.activity = activity;
	}
	
	@Override
	public ArrayList<String> autoComplete(final String input) {
		mSuggestionsList = new ArrayList<String>();
	
		// Stop any previous thread(s) before requesting
		// any new Google Place suggestions
		if (mThread != null) {
			mThread = null;
		}
		
		// Create and start a new Thread to perform
		// a Google Place search request.  Use the request's results
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
		
		// Wait for mThread to finish before updating
		// the SearchView's adapter with Place suggestions
		/*try {
			mThread.join();
		} catch (InterruptedException ie) {
			// mThread was interrupted 
			mThread = null;
		}*/
		return mSuggestionsList;
	}
	
	public Cursor getSuggestionsCursor() {
		
		// Load data from list to cursor
        String[] columns = new String[] { column_id, column_text };
        Object[] temp = new Object[] { 0, "default" };
 
        MatrixCursor cursor = new MatrixCursor(columns);
        
        for(int i = 0; i < mSuggestionsList.size(); i++) {
        	 
            temp[0] = i;
            temp[1] = mSuggestionsList.get(i);
 
            cursor.addRow(temp);
        }
        
        Log.d(TAG, "suggestions size is: " + mSuggestionsList.size());
		
		return cursor;
	}
	
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
		
		Intent intent = new Intent(activity, PlaceActivity.class);
		intent.putExtra("Place Name", place_reference);
		launchActivity(intent);
	}
	
	@Override
	public void launchActivity(Intent intent) {
		activity.launchActivity(intent);
	}
}
