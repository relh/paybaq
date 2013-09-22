package com.ampvita.paybaq;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class ViewRemindersActivity extends ListActivity {

	TextView content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_reminders);

		content = (TextView) findViewById(R.id.reminderOutput);

		// listView = (ListView) findViewById(R.id.list);
		ArrayList<String> values = new ArrayList<String>();
		
		try {
		    BufferedReader inputReader = new BufferedReader(new InputStreamReader(
		            openFileInput("ReminderList")));
		    String inputString;               
		    while ((inputString = inputReader.readLine()) != null) {
		       values.add(inputString);
		    }
		} catch (IOException e) {
		    e.printStackTrace();
		}
		PayBaqAdapter adapter = new PayBaqAdapter(this,
				R.layout.debtitem, values);
		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		super.onListItemClick(l, v, position, id);

		Intent i = new Intent("com.ampvita.paybaq.MessageActivity");
        startActivity(i);
		
	}
}