package com.ampvita.paybaq;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class StartActivity extends Activity {

	final static int PICK_CONTACT = 1;
	final Context context = this;
	public static String tiers[][] = new String[10][30];
	static String username = "User";
	static String hippo = "www.hippo.io";
	SharedPreferences.Editor editor;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		setupTiers();

		findViewById(R.id.send).setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
				startActivityForResult(i, PICK_CONTACT);
			}
		});

		findViewById(R.id.reminder).setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				Intent i = new Intent("com.ampvita.paybaq.ViewRemindersActivity");
				i.putExtra("message", "None!");
				startActivity(i);
				finish();
			}
		});

		final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

		findViewById(R.id.bEditAccount).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle("Edit Account");
				final View myView = getLayoutInflater().inflate(R.layout.account, null);
				builder.setView(myView);
				builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						username = ((EditText) myView.findViewById(R.id.etName)).getText().toString().trim();
						hippo = "http://hippo.io/" + ((EditText) myView.findViewById(R.id.etHippo)).getText().toString().trim();
						//shorten URL
						new ShortenURL().execute();

						editor = prefs.edit();
//						SharedPreferences.Editor editor = prefs.edit();
//						editor.putString("name", username);
//						editor.putString("hippo", hippo);
//						editor.commit();
					}
				});
				builder.setNegativeButton("Cancel", null);
				builder.create().show();
			}
		});

		username = prefs.getString("name", "User").trim();
		hippo = prefs.getString("hippo", "www.hippo.io").trim();

	}

	private class ShortenURL extends AsyncTask<String, Void, HttpResponse>  {

		protected void onPostExecute(HttpResponse result) {
			super.onPostExecute(result);

		}

		protected HttpResponse doInBackground(String... params) {
			try {
				HttpPost post = new HttpPost("https://www.googleapis.com/urlshortener/v1/url");
				post.setHeader(HTTP.CONTENT_TYPE, "application/json");
				
//				List<NameValuePair> myParams = new ArrayList<NameValuePair>(1);
//				myParams.add(new BasicNameValuePair("longUrl", hippo));
//				post.setEntity(new UrlEncodedFormEntity(myParams));
				
				StringEntity myParams = new StringEntity("{\"longUrl\":\"" + hippo + "\"}");
				post.setEntity(myParams);
				
				HttpResponse response = new DefaultHttpClient().execute(post);
				Log.v("zzz", "response:" + response + ";");
				BufferedReader rd = new BufferedReader(new InputStreamReader(
						response.getEntity().getContent()));
				String line = "";
				String end = "";
				while ((line = rd.readLine()) != null) {
					if (line.contains("id")) {
						end = line.substring(line.indexOf(":") + 3);
						end = end.substring(0, end.length()-2);
						Log.v("zzz", "end:" + end);
						hippo = end;
					}
					Log.v("zzz", line);
				}

				editor.putString("name", username);
				editor.putString("hippo", hippo);
				editor.commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

	}

	public void onActivityResult(int reqCode, int resultCode, Intent data) {
		super.onActivityResult(reqCode, resultCode, data);

		switch (reqCode) {
		case (PICK_CONTACT) :
			if (resultCode == Activity.RESULT_OK) {
				Uri contactData = data.getData();
				Cursor c =  getContentResolver().query(contactData, null, null, null, null);
				Intent i = new Intent("com.ampvita.paybaq.MessageActivity");
				if (c.moveToFirst()) {
					String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
					String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
					String number = "none";
					if (hasPhone.equalsIgnoreCase("1")) {
						Cursor phones = getContentResolver().query( 
								ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, 
								ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, 
								null, null);
						phones.moveToFirst();
						number = phones.getString(phones.getColumnIndex("data1"));
					}
					String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
					number = formatNumber(number);

					i.putExtra("name", name);
					i.putExtra("number", number);
					i.putExtra("hippo", hippo);
				}
				startActivity(i);
			}
		break;
		}
	}

	public static String formatNumber(String unformatted) {
		String number = unformatted.replaceAll("[^1234567890]", "");
		if (number.length() <= 10) {
			if (number.length() < 5)
				number = "Re-enter: Invalid";
			else
				number = "+1" + number;
		} else {
			number = "+" + number;
		}
		return number;
	}

	public void setupTiers() {
		InputStream is = getResources().openRawResource(R.raw.messagebody);
		try {
			BufferedReader inputReader = new BufferedReader(new InputStreamReader(is));
			String inputString;               
			int i = -1; int k = 1;
			while ((inputString = inputReader.readLine()) != null) {
				if (inputString.contains("Tier") && inputString.contains(":")) {
					k -= 2;
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
	}

	public static String getOwner() {
		return username;
	}

	public void onBackPressed() {
		finish();
	}
}
