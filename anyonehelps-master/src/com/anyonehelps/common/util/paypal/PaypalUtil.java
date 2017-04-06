package com.anyonehelps.common.util.paypal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import jxl.common.Logger;

public class PaypalUtil {
	private static final Logger log = Logger.getLogger(PaypalUtil.class);
	public PaypalResultMode getPaypalResultMode(HttpServletRequest request) {    
		PaypalResultMode prm = new PaypalResultMode();
		try{
			// 从 PayPal 出读取 POST 信息同时添加变量„cmd‟
			@SuppressWarnings("rawtypes")
			Enumeration en = request.getParameterNames();
			String str = "cmd=_notify-validate";
			while(en.hasMoreElements()){
				String paramName = (String)en.nextElement();
				String paramValue = request.getParameter(paramName);
				str = str + "&" + paramName + "=" + URLEncoder.encode(paramValue, "iso-8859-1");
			}
			log.info("Paypal Cmd param:"+str);
			//建议在此将接受到的信息 str 记录到日志文件中以确认是否收到 IPN 信息
			//将信息 POST 回给 PayPal 进行验证
			//设置 HTTP 的头信息
			//在 Sandbox 情况下，设置：
			//URL u= new URL(“http://www.sandbox.paypal.com/cgi-bin/webscr”);
			URL u = new URL("http://www.paypal.com/cgi-bin/webscr");
			URLConnection uc = u.openConnection();
			uc.setDoOutput(true);
			
			uc.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			PrintWriter pw = new PrintWriter(uc.getOutputStream());
			pw.println(str);
			pw.close();
			//接受 PayPal 对 IPN 回发的回复信息
			BufferedReader in= new BufferedReader(new InputStreamReader(uc.getInputStream()));
			String res = in.readLine();
			in.close();
			
			//将 POST 信息分配给本地变量，可以根据您的需要添加
			//该付款明细所有变量可参考：
			//https://www.paypal.com/IntegrationCenter/ic_ipn-pdt-variable-reference.html
			prm.setItemName(request.getParameter("item_name"));
			prm.setItemNumber(request.getParameter("item_number"));
			prm.setPaymentStatus(request.getParameter("payment_status"));
			prm.setMcGross(request.getParameter("mc_gross"));
			prm.setMcCurrency(request.getParameter("mc_currency"));
			prm.setTxnId(request.getParameter("txn_id"));
			prm.setReceiverEmail(request.getParameter("receiver_email"));
			prm.setPayerEmail(request.getParameter("payer_email"));
			prm.setMcFee(request.getParameter("mc_fee"));
			prm.setCustom(request.getParameter("custom"));
			prm.setResultVerified(res);
		} catch (IOException e){
			System.out.println(e.getMessage());
			prm = null;	
		}
		return prm;

	}
}
