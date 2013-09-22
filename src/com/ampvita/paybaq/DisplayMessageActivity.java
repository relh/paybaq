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
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_message);
		
		Intent intent = getIntent();
		
		((TextView)findViewById(R.id.textMessage)).setText("You just told " + 
				intent.getStringExtra("name") + ": \n" + intent.getStringExtra("message"));
		
		findViewById(R.id.returnHome).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
                startActivity(new Intent("com.ampvita.paybaq.StartActivity"));
			}
		});
	}
}