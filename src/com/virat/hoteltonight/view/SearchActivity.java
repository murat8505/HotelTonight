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

public class SearchActivity extends Activity implements SearchInterface {

	// A reference to the Search presenter
	private SearchPresenter presenter;
	
	private SearchView mSearchView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		presenter = new SearchPresenter(this);
		

	}
	
	@Override
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.options_menu, menu);
	    
	    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
	    	 
	        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	        
	        mSearchView = (SearchView) menu.findItem(R.id.search).getActionView();
	        
	        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
	        
	        mSearchView.setOnQueryTextListener(new OnQueryTextListener() { 
	 
	            @Override 
	            public boolean onQueryTextChange(String query) {
	            	if (query.length() > 2) {
	            		autoComplete(query);
	            	}
	
	                return true; 
	            }

				@Override
				public boolean onQueryTextSubmit(String query) {
					// TODO Auto-generated method stub
					return false;
				} 
	        });
	        
	        mSearchView.setOnSuggestionListener(new OnSuggestionListener() {
				
				@Override
				public boolean onSuggestionSelect(int position) {
					// TODO Auto-generated method stub
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
	
	public void setSuggestionsAdapter(ArrayList<String> results) {
		
		Cursor cursor = presenter.getSuggestionsCursor();
		
		mSearchView.setSuggestionsAdapter(new SearchSuggestionAdapter(this, cursor, results));
	}
}
