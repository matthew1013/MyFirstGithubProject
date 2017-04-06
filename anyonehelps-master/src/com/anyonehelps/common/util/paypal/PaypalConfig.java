package com.anyonehelps.common.util.paypal;

import java.util.Properties;

import com.anyonehelps.common.constants.Constant;
import com.anyonehelps.common.util.PropertiesReader;

public class PaypalConfig {

	static { 
		Properties props = PropertiesReader.read(Constant.SYSTEM_PROPERTIES_FILE);
		//authToken = props.getProperty("paypal.authToken");
		//posturl = props.getProperty("paypal.posturl");
		business = props.getProperty("paypal.business");
		//returnurl = props.getProperty("paypal.returnurl");
	} 
	
    //private static String authToken;
    //private static String posturl;
    private static String business;
    //private static String returnurl;
    //private String cancelurl;
    //private String cmd;

	public static String getBusiness() {
		return business;
	}


    /*public PayPalConfig getConfig(HttpServletRequest request) {
        PayPalConfig pc = new PayPalConfig();
        try {        	
        	Properties prop = PropertiesReader.read(Constant.SYSTEM_PROPERTIES_FILE);
            pc.authToken = prop.getProperty("paypal.authToken");
            pc.posturl = prop.getProperty("paypal.posturl");
            pc.business = prop.getProperty("paypal.business");
            pc.returnurl = prop.getProperty("paypal.returnurl");
            
        } catch (Exception ex) {
            pc = null;
        }
        return pc;
    }*/
}
