package com.virat.hoteltonight;

import java.util.ArrayList;

import android.content.Intent;
import android.database.Cursor;

public interface SearchInterface {
	
	/**
	 * Requests names of Google Places based on a 
	 * user-specified Place search term.
	 * @param input: user-specified Place keyword
	 * @return an ArrayList of Google Place names
	 */
	public ArrayList<String> autoComplete(String input);
	
	/**
	 * Launches an activity using a specified Intent 
	 * @param intent
	 */
	public void launchActivity(Intent intent);
}
