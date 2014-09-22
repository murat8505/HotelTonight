package com.virat.hoteltonight;

import java.util.ArrayList;

import android.content.Intent;

public interface SearchInterface {
	
	public ArrayList<String> autoComplete(String input);
	
	public void launchActivity(Intent intent);

}
