package com.ampvita.paybaq;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class MessageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);
		
		findViewById(R.id.submit).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				 Intent i = new Intent("com.ampvita.paybaq.ViewRemindersActivity");
                 i.putExtra("message", ((EditText)findViewById(R.id.editMessage)).getText().toString());
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
