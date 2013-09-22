package com.ampvita.paybaq;

import java.io.FileOutputStream;

//import com.twilio.sdk.TwilioRestException;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class MessageActivity extends Activity {

	String name;
	String number;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);
		
		Intent start = getIntent();
	
		name = start.getStringExtra("name" + " ");
		number = start.getStringExtra("number");
		
		((TextView)findViewById(R.id.textOwe)).setText("So, " + name);
		((EditText)findViewById(R.id.editNumber)).setText("(" + number + ")");
		
		findViewById(R.id.submit).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				String howMuch = ((EditText)findViewById(R.id.editAmount)).getText().toString(); 
				String why = ((EditText)findViewById(R.id.editWhy)).getText().toString(); 
				
				try {
				    FileOutputStream fos = openFileOutput("ReminderList", Context.MODE_APPEND);
				    fos.write(name.getBytes());
				    //fos.write(number.getBytes());
				    fos.write(howMuch.getBytes());
				    //fos.write(why.getBytes());
				    fos.write(("\n").getBytes());
				    fos.close();
				    
				   // TwilioInterface.send_message("+12404419132", "YO DAWG");
				} catch (Exception e) {
				    e.printStackTrace();
				}
			
				Intent i = new Intent("com.ampvita.paybaq.ViewRemindersActivity");
                startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}

}
