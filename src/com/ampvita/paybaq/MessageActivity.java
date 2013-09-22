package com.ampvita.paybaq;

import java.io.FileOutputStream;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
	
		name = start.getStringExtra("name") + "\t";
		number = start.getStringExtra("number") + "\t";
		
		((TextView)findViewById(R.id.textOwe)).setText("So, " + name);
		((EditText)findViewById(R.id.editNumber)).setText("(" + number + ")");
		
		findViewById(R.id.submit).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				String why = ((EditText)findViewById(R.id.editWhy)).getText().toString() + "\t"; 
				String howMuch = ((EditText)findViewById(R.id.editAmount)).getText().toString() + "\t"; 
			
				try {
				    FileOutputStream fos = openFileOutput("ReminderList", Context.MODE_APPEND);
				    fos.write(name.getBytes()); //0 - Name
				    fos.write(number.getBytes()); //1 - Number
				    fos.write(why.getBytes()); //2 - Reason
				    fos.write(howMuch.getBytes()); //3 - Amount
				    fos.write("1".getBytes()); //4 - Level
				    fos.write(("\n").getBytes());
				    fos.close();
				} catch (Exception e) {
				    e.printStackTrace();
				}
				String msg = "Hi from Paybaq! We believe you owe " + StartActivity.owner
						+ " money. Hippo makes paying back easy: bit.ly/asdf.";
				new SendSMS().execute(number, msg);
				
				int val = (int) (Math.random()*Integer.parseInt(StartActivity.tiers[0][0]));
				msg = StartActivity.tiers[0][val+1];
				msg = msg.replace("[reason]", why.trim());
				msg = msg.replace("[price]", howMuch.trim());
				
				new SendSMS().execute(number, msg);
				Intent i = new Intent("com.ampvita.paybaq.DisplayMessageActivity");
				i.putExtra("message", msg);
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
