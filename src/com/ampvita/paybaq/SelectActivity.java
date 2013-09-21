package com.ampvita.paybaq;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SelectActivity extends ListActivity {

	TextView content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select);

		content = (TextView) findViewById(R.id.output);

		// listView = (ListView) findViewById(R.id.list);
		String[] values = new String[] { "Android Example ListActivity",
				"Adapter implementation", "Simple List View With ListActivity",
				"ListActivity Android", "Android Example",
				"ListActivity Source Code",
				"ListView ListActivity Array Adapter",
				"Android Example ListActivity" };

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, values);

		// Assign adapter to List
		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		super.onListItemClick(l, v, position, id);

		// ListView Clicked item index
		int itemPosition = position;

		// ListView Clicked item value
		String itemValue = (String) l.getItemAtPosition(position);
		content.setText("Click : \n  Position :" + itemPosition
				+ "  \n  ListItem : " + itemValue);
		 
		Intent i = new Intent("com.ampvita.paybaq.MesssageActivity");
        startActivity(i);
		
	}
}