package com.anyonehelps.common.util.sms;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;

public class SmsTwilioSender {

	// Find your Account Sid and Token at twilio.com/console
	public static final String ACCOUNT_SID = "AC7977e2e9359886b1870236dc240c0835";
	public static final String AUTH_TOKEN = "fe8dd766e114144495753774468f6103";
	public static final String FROM_NUMBER = "+15598887080";
	
	//fail {"code": 21211, "message": "The 'To' number +861581885sssssssssss3022 is not a valid phone number.", "more_info": "https://www.twilio.com/docs/errors/21211", "status": 400}
	//success {"to":"+8615818853022","body":"你的认证码是:999999 -- 14:13:00","sid":"SMd4cace80c6614b3883b060f92fe52b0b","status":"queued","direction":"outbound-api","error_code":null,"date_created":"Sun, 13 Nov 2016 16:34:21 +0000","from":"+16262381986","date_sent":null,"messaging_service_sid":null,"api_version":"2010-04-01","uri":"\/2010-04-01\/Accounts\/AC7977e2e9359886b1870236dc240c0835\/Messages\/SMd4cace80c6614b3883b060f92fe52b0b.json","num_media":"0","account_sid":"AC7977e2e9359886b1870236dc240c0835","price":null,"date_updated":"Sun, 13 Nov 2016 16:34:21 +0000","error_message":null,"price_unit":"USD","subresource_uris":{"media":"\/2010-04-01\/Accounts\/AC7977e2e9359886b1870236dc240c0835\/Messages\/SMd4cace80c6614b3883b060f92fe52b0b\/Media.json"},"num_segments":"1"}
	public static void main(String[] args) throws TwilioRestException {
		TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

		// Build a filter for the MessageList emailVerify
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("Body", "你的认证码是:999999 -- 14:13:00"));
		params.add(new BasicNameValuePair("To", "+8615818853022"));
		params.add(new BasicNameValuePair("From", "+16262381986"));

		MessageFactory messageFactory = client.getAccount().getMessageFactory();
		Message message = messageFactory.create(params);
		System.out.println("message:"+message.toJSON());
	}
	public static String sendSms(String content,String mobile) throws TwilioRestException {
		TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

		// Build a filter for the MessageList emailVerify
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("Body", content));
		params.add(new BasicNameValuePair("To", mobile));
		params.add(new BasicNameValuePair("From", FROM_NUMBER));

		MessageFactory messageFactory = client.getAccount().getMessageFactory();
		Message message = messageFactory.create(params);
		return message.toJSON();
	
	}
}