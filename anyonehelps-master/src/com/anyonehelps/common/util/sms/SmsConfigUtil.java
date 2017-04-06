package com.anyonehelps.common.util.sms;

import com.anyonehelps.common.constants.Constant;

public class SmsConfigUtil {	
	public static String getSmsConfigFile(String countryCode){
		countryCode = countryCode.toUpperCase();
		String result = null;
		if(countryCode.equals(Constant.COUNTRY_CODE_CN)){
			result = Constant.SYSTEM_CN_PROPERTIES_FILE;
		}else if(countryCode.equals(Constant.COUNTRY_CODE_US)){
			result = Constant.SYSTEM_US_PROPERTIES_FILE;
		}else {
			result = Constant.SYSTEM_CN_PROPERTIES_FILE;
		}
		return result;
	}
}
