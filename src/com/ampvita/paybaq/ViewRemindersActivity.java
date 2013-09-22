package com.ampvita.paybaq;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class ViewRemindersActivity extends ListActivity {

	TextView content;
	PayBaqAdapter adapter;
	public ArrayList<String> values = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_reminders);

		content = (TextView) findViewById(R.id.reminderOutput);

		// listView = (ListView) findViewById(R.id.list);
		values = new ArrayList<String>();

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
		adapter = new PayBaqAdapter(this,
				R.layout.debtitem, values);
		setListAdapter(adapter);
	}

//	@Override
//	protected void onListItemClick(ListView l, View v, int position, long id) {
//		super.onListItemClick(l, v, position, id);
//
//		Intent i = new Intent("com.ampvita.paybaq.MessageActivity");
//		startActivity(i);	
//	}

	@Override
	protected void onPause() {
		super.onPause();

		try {
			File dir = getFilesDir();
			File file = new File(dir, "ReminderList");
			boolean deleted = file.delete();
			
			FileOutputStream fos = openFileOutput("ReminderList", Context.MODE_APPEND);

			int size = adapter.getCount();
			for (int i = 0; i <= size; i++) {
				String[] parts = adapter.getItem(i).split("\\t");

				if (parts.length >= 4) {
					fos.write((parts[0] + "\t").getBytes()); // Name
					fos.write((parts[1] + "\t").getBytes()); // Number
					fos.write((parts[2] + "\t").getBytes()); // Reason
					fos.write((parts[3] + "\t").getBytes()); // Amount
					fos.write(parts[4].getBytes()); // Level
					fos.write(("\n").getBytes());
				}
			}
			fos.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}