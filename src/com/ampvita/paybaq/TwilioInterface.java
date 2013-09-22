//package com.ampvita.paybaq;
//
//import android.widget.EditText;
//
////import com.twilio.sdk.TwilioRestClient;
////import com.twilio.sdk.TwilioRestException;
////import com.twilio.sdk.resource.factory.SmsFactory;
////import com.twilio.sdk.resource.instance.Sms;
////import com.twilio.sdk.resource.list.SmsList;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.HashMap;
//import java.util.Map;
//  
//public class TwilioInterface { 
//  
//  // Find your Account Sid and Token at twilio.com/user/account
//  private static final String ACCOUNT_SID = "AC20d859d12f3d9c0bc13214dfb2c575cf";
//  private static final String AUTH_TOKEN = "5d0bff17823060e379cc6024361e4b23";  
//  private static final String ACCOUNT_NUM = "+12406692696";
//
//  //public static void send_message(String to, String msg) throws TwilioRestException {
//   // TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
//    
//    Map<String, String> params = new HashMap<String, String>();
//    params.put("Body", msg);
//    params.put("To", to);
//    params.put("From", ACCOUNT_NUM);
//
//   // SmsFactory messageFactory = client.getAccount().getSmsFactory();
//   // Sms message = messageFactory.create(params);
//   // System.out.println(message.getSid());
//  }
//}