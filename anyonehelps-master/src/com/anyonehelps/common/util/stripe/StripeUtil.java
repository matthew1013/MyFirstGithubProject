package com.anyonehelps.common.util.stripe;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.anyonehelps.common.constants.Constant;
import com.anyonehelps.common.util.PropertiesReader;
import com.stripe.Stripe;
import com.stripe.model.Charge;

public class StripeUtil {
	/**
	 * add param brand at 2016-01-05
	 * @param brand 银行卡类型，如万事达 VISA等 ，现在用上好像会报错
	 * @param name  名字
	 * @param creditNo 卡号
	 * @param securityCode 信用卡后面3号安全码
	 * @param expireYear   到期年
	 * @param expireMonth  到期月
	 * @param amount       扣费金额，至少0.5美元，单位是以美分提交的，也就是要扣1美元，这个参数要是100
	 * @param currency     币种，现在只用到美元
	 * @param order        自定义的参数，一般是自己系统的订单号
	 * @return
	 * @throws Exception
	 */
    public static Charge createPayMoneyByStrip(String brand,String name,String creditNo,String securityCode,String expireYear,String expireMonth,int amount,String currency,String order) throws Exception {
    	try {
			Properties props = PropertiesReader.read(Constant.SYSTEM_PROPERTIES_FILE);
			Stripe.apiKey = props.getProperty("stripe.apikey");
		} catch (Exception e) {
			//Stripe.apiKey = "1a34d5adddbaf841";
			throw e; 
		}
		Map<String, Object> chargeMap = new HashMap<String, Object>();
        chargeMap.put("amount", amount);

        chargeMap.put("currency", currency);
        Map<String, Object> cardMap = new HashMap<String, Object>();
        cardMap.put("number", creditNo);
        cardMap.put("exp_month",expireMonth);
        cardMap.put("exp_year", expireYear);
        cardMap.put("cvc", securityCode);
        cardMap.put("name", name);
        cardMap.put("brand", brand);
        chargeMap.put("card", cardMap);
        
        Map<String, String> initialMetadata = new HashMap<String, String>();
        initialMetadata.put("order_id", order);
        initialMetadata.put("type", order);
        
        chargeMap.put("metadata", initialMetadata);
        Charge charge = Charge.create(chargeMap);
		return charge;
    }
}
