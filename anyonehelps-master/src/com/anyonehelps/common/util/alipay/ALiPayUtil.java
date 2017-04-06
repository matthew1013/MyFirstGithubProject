package com.anyonehelps.common.util.alipay;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.anyonehelps.common.util.alipay.config.AlipayConfig;
import com.anyonehelps.common.util.alipay.util.AlipaySubmit;

public class ALiPayUtil {

	
    public static String createPayMoneyByALiPay(BigDecimal decimal, String accountDetailId, String userName, String notifyUrl, String returnUrl) {
    	
    	// 支付类型
        String payment_type = "1";
        String notify_url = notifyUrl;
        String return_url = returnUrl;
        String seller_email = "teanjuly@gmail.com";
        String out_trade_no = accountDetailId; 
        String subject = userName + "AnyoneHelps(anyonehelps.com)会员充值";
        String total_fee = decimal.toString();
        String body = "AnyoneHelps(anyonehelps.com)会员充值，请选择即时到帐服务，如果是其他服务，可能对导致出现无法及时更新您的帐户余额！";
        String anti_phishing_key = "";
        String exter_invoke_ip = "";

        // 把请求参数打包成数组
        Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("service", "create_direct_pay_by_user");
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
        sParaTemp.put("payment_type", payment_type);
        sParaTemp.put("notify_url", notify_url);
        sParaTemp.put("return_url", return_url);
        sParaTemp.put("seller_email", seller_email);
        sParaTemp.put("out_trade_no", out_trade_no);
        sParaTemp.put("subject", subject);
        sParaTemp.put("total_fee", total_fee);
        sParaTemp.put("body", body);
        sParaTemp.put("anti_phishing_key", anti_phishing_key);
        sParaTemp.put("exter_invoke_ip", exter_invoke_ip);
        
        return AlipaySubmit.buildRequest(sParaTemp, "get", "Submit");
    }
}
