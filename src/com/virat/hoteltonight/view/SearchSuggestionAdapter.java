package com.virat.hoteltonight.view;

import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.virat.hoteltonight.R;

/**
 * A simple CursorAdapter that exposes data from a Cursor to a SearchView
 * @author Virat
 * 
 */
public class SearchSuggestionAdapter extends CursorAdapter {

	private static final String TAG = "SearchSuggestionAdapter";

	private List<String> items;

	private TextView text;

	public SearchSuggestionAdapter(Context context, Cursor cursor, List items) {
		super(context, cursor, false);

		this.items = items;
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View view = inflater.inflate(R.layout.item, parent, false);

		text = (TextView) view.findViewById(R.id.text);

		return view;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {

		// Show list item data from cursor
		text.setText(items.get(cursor.getPosition()));

		Log.d(TAG, "bindView text is: " + items.get(cursor.getPosition()));

	}
}
