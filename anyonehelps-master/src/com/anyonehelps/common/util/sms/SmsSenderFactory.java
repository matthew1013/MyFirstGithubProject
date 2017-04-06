package com.anyonehelps.common.util.sms;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import com.anyonehelps.common.constants.Constant;
import com.anyonehelps.common.util.PropertiesReader;


public class SmsSenderFactory {
	private static Map<String, SimpleSmsSender> cache = new ConcurrentHashMap<String, SimpleSmsSender>();

	/**
	 * 根据配置参数获取发送信息的类。
	 * 
	 * @return
	 */
	public static SimpleSmsSender generateSmsSender() {
		String apikey = "";
		try {
			Properties props = PropertiesReader.read(Constant.SYSTEM_PROPERTIES_FILE);
			apikey = props.getProperty("sms.user.apikey");
			
		} catch (Exception e) {
			apikey = "e55f09f4109f6f7a3aef6e99b2175f9f";
		}
		SimpleSmsSender sender = cache.get(apikey);

		if (sender == null) {
			sender = new SimpleSmsSender(apikey);
			cache.put(apikey, sender);
		}
		return sender;
	}
}
