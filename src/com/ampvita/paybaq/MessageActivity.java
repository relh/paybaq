package com.ampvita.paybaq;

import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class MessageActivity extends Activity {

	String name;
	String number;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);

		Intent start = getIntent();

		name = start.getStringExtra("name") + "\t";
		number = start.getStringExtra("number") + "\t";

		((TextView)findViewById(R.id.textOwe)).setText("So, " + name);
		((EditText)findViewById(R.id.editNumber)).setText(number);

		findViewById(R.id.submit).setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				String reason = ((EditText)findViewById(R.id.editWhy)).getText().toString() + "\t"; 
				String amount = ((EditText)findViewById(R.id.editAmount)).getText().toString() + "\t"; 
				number = ((EditText)findViewById(R.id.editNumber)).getText().toString();
				number = StartActivity.formatNumber(number) + "\t";

				try {
					FileOutputStream fos = openFileOutput("ReminderList", Context.MODE_APPEND);
					fos.write(name.getBytes()); //0 - Name
					fos.write(number.getBytes()); //1 - Number
					fos.write(reason.getBytes()); //2 - Reason
					fos.write(amount.getBytes()); //3 - Amount
					fos.write("1".getBytes()); //4 - Tier
					fos.write(("\n").getBytes());
					fos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				String msg = "Hello from Paybaq! We believe you owe " + StartActivity.getOwner()
						+ " some money. Hippo makes paying back easy: goo.gl/q01uH3";
				new SendSMS().execute(number, msg);

				int val = (int) (Math.random()*Integer.parseInt(StartActivity.tiers[0][0]));
				msg = StartActivity.tiers[0][val+1];
				msg = msg.replace("[reason]", reason.trim());
				msg = msg.replace("[price]", amount.trim());

				new SendSMS().execute(number, msg);
				Intent i = new Intent("com.ampvita.paybaq.DisplayMessageActivity");
				i.putExtra("name", name);
				i.putExtra("message", msg);
				startActivity(i);
			}
		});
	}
}
