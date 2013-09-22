package com.ampvita.paybaq;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;

public class SendSMS extends AsyncTask<String, Void, HttpResponse> {

	protected HttpResponse doInBackground(String... params) {
		try {
			HttpPost post = new HttpPost("http://paybaq.herokuapp.com/send-sms.php");
			List<NameValuePair> postParams = new ArrayList<NameValuePair>();
			postParams.add(new BasicNameValuePair("to", params[0]));
			postParams.add(new BasicNameValuePair("msg", params[1]));
			post.setEntity(new UrlEncodedFormEntity(postParams));
			new DefaultHttpClient().execute(post);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}