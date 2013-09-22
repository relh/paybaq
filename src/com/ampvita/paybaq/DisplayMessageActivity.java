package com.ampvita.paybaq;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class DisplayMessageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_message);
		
		Intent start = getIntent();
	
		((TextView)findViewById(R.id.textMessage)).setText(start.getStringExtra("message"));
		
		findViewById(R.id.returnHome).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent("com.ampvita.paybaq.StartActivity");
                startActivity(i);
			}
			
		});
	}
	
	
}