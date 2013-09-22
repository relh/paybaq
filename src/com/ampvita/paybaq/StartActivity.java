package com.ampvita.paybaq;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;

public class StartActivity extends Activity {

	final static int PICK_CONTACT = 1;
	public String tiers[][] = new String[10][30];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		InputStream is = getResources().openRawResource(R.raw.messagebody);
		try {
		    BufferedReader inputReader = new BufferedReader(new InputStreamReader(is));
		    String inputString;               
		    int i = -1; int k = 1;
		    while ((inputString = inputReader.readLine()) != null) {
		       if (inputString.contains("Tier") && inputString.contains(":")) {
			       if (i >= 0) tiers[i][0] = k+"";
		    	   i += 1;
			       k = 1;
		       } else {
		    	   if (inputString != "") {
		    		   tiers[i][k] = inputString;
		    		   k += 1;
		    	   }
		       }
		    }
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		findViewById(R.id.send).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
				startActivityForResult(i, PICK_CONTACT);
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
	public void onActivityResult(int reqCode, int resultCode, Intent data) {
	  super.onActivityResult(reqCode, resultCode, data);

	  switch (reqCode) {
	    case (PICK_CONTACT) :
	      if (resultCode == Activity.RESULT_OK) {
	        Uri contactData = data.getData();
	        Cursor c =  getContentResolver().query(contactData, null, null, null, null);
	        Intent i = new Intent("com.ampvita.paybaq.MessageActivity");
	        if (c.moveToFirst()) {
	            String id =c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
	            String hasPhone =c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
	            String number = "none";
	            if (hasPhone.equalsIgnoreCase("1")) {
	            	Cursor phones = getContentResolver().query( 
	                          ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null, 
	                          ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id, 
	                          null, null);
	                phones.moveToFirst();
	                number = phones.getString(phones.getColumnIndex("data1"));
	            }
	            String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
	            number = number.replaceAll("[^123456890]", "");
	            if (number.length() <= 10) {
	            	if (number.length() < 5)
	            		number = "Re-enter: Invalid";
	            	else
	            		number = "+1" + number;
	            } else {
	            	number = "+" + number;
	            }
	            
	            i.putExtra("name", name);
	            i.putExtra("number", number);
	        }
	        startActivity(i);
	      }
	      break;
	  }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}

}
