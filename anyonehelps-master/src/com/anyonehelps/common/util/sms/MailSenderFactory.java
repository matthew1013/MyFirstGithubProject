package com.anyonehelps.common.util.sms;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import com.anyonehelps.common.constants.Constant;
import com.anyonehelps.common.util.PropertiesReader;


public class MailSenderFactory {
	private static Map<String, SimpleMailSender> cache = new ConcurrentHashMap<String, SimpleMailSender>();

	/**
	 * 根据配置参数获取发送邮件信息的类。
	 * 
	 * @return
	 */
	public static SimpleMailSender generateMailSender() {
		String userName = "";
		String password = "";
		Properties prop = null;

		try {
			Properties props = PropertiesReader.read(Constant.EMAIL_PROPERTIES_FILE);
			userName = props.getProperty("mail.user.name");
			password = props.getProperty("mail.password");
			prop = new Properties();
			prop.put("mail.smtp.host", props.get("mail.smtp.host"));
			prop.put("mail.smtp.auth", props.get("mail.smtp.auth"));
			if (userName == null || userName.trim().isEmpty() || password == null || password.trim().isEmpty()) {
				userName = "hnzjj2014@126.com";
				password = "hnzjj2010";
			}
		} catch (Exception e) {
			userName = "hnzjj2014@126.com";
			password = "hnzjj2010";
		}
		SimpleMailSender sender = cache.get(userName);

		if (sender == null) {
			sender = new SimpleMailSender(userName, password, prop);
			cache.put(userName, sender);
		}
		return sender;
	}
}
