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
		String[] values = new String[] { "USER ME" };

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, values);

		// Assign adapter to List
		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		super.onListItemClick(l, v, position, id);

		Intent i = new Intent("com.ampvita.paybaq.MessageActivity");
        startActivity(i);
		
	}
}