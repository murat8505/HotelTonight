package com.virat.hoteltonight.view;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.SearchView.OnSuggestionListener;

import com.virat.hoteltonight.R;
import com.virat.hoteltonight.SearchInterface;
import com.virat.hoteltonight.presenter.SearchPresenter;

/*
 * This class is the main launcher activity for the application.
 * This Activity has a simple UI that consists of a SearchView in the 
 * ActionBar and is managed by a Presenter controller (SearchPresenter) 
 */
public class SearchActivity extends Activity implements SearchInterface {
	
	// A reference to the Search presenter
	private SearchPresenter presenter;
	
	// The main SearchView object
	private SearchView mSearchView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		// Create a new SearchPresenter`object
		presenter = new SearchPresenter(this);
	}
	
	@Override
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.options_menu, menu);
	    
	    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
	    	 
	    	// Wire up the SearchView
	        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	        mSearchView = (SearchView) menu.findItem(R.id.search).getActionView();
	        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
	        mSearchView.setOnQueryTextListener(new OnQueryTextListener() { 
	 
	            @Override 
	            public boolean onQueryTextChange(String query) {
	            	autoComplete(query);
	                
	            	return true; 
	            }

				@Override
				public boolean onQueryTextSubmit(String query) {
					// This function is intentionally blank
					return false;
				} 
	        });
	        
	        mSearchView.setOnSuggestionListener(new OnSuggestionListener() {
				
				@Override
				public boolean onSuggestionSelect(int position) {
					// This function is intentionally blank
					return false;
				}
				
				@Override
				public boolean onSuggestionClick(int position) {
					Cursor cursor = (Cursor) mSearchView.getSuggestionsAdapter().getItem(position);
					presenter.onSuggestionClick(cursor);
					
					return true;
				}
			});
	    }
	    
	    return true;
	}
	
	@Override
	public ArrayList<String> autoComplete(String input) {
		ArrayList<String> results = presenter.autoComplete(input);
		
		return results;
	}
	
	@Override
	public void launchActivity(Intent intent) {
		if (intent != null) {
			startActivity(intent);
		}
	}
	
	/**
	 * Sets the SearchView's suggestion adapter
	 * @param results
	 */
	public void setSuggestionsAdapter(ArrayList<String> results) {
		Cursor cursor = presenter.getSuggestionsCursor();
		
		mSearchView.setSuggestionsAdapter(new SearchSuggestionAdapter(this, cursor, results));
	}
}
