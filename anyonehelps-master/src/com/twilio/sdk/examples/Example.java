package com.twilio.sdk.examples;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class Example {

  // Find your Account Sid and Token at twilio.com/console
  public static final String ACCOUNT_SID = "AC7977e2e9359886b1870236dc240c0835";
  public static final String AUTH_TOKEN = "fe8dd766e114144495753774468f6103";

  public static void main(String[] args) throws TwilioRestException {
    TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

    // Build a filter for the MessageList emailVerify
    List<NameValuePair> params = new ArrayList<NameValuePair>();
    params.add(new BasicNameValuePair("Body", "你的认证码是:999999 -- 14:13:00"));
    params.add(new BasicNameValuePair("To", "+861581885sssssssssss3022"));
    params.add(new BasicNameValuePair("From", "+16262381986"));

    MessageFactory messageFactory = client.getAccount().getMessageFactory();
    Message message = messageFactory.create(params);
    System.out.println(message.getSid());
    System.out.println("==============");
    System.out.println(message.toJSON());
    
  }
}