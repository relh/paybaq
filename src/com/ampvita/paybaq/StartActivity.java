package com.ampvita.paybaq;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;

public class StartActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		findViewById(R.id.send).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				 Intent i = new Intent("com.ampvita.paybaq.SelectActivity");
                 startActivity(i);
			}
		});
		
		findViewById(R.id.reminder).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				 Intent i = new Intent("com.ampvita.paybaq.ViewRemindersActivity");
                 i.putExtra("message", "None!");
				 startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}

}
