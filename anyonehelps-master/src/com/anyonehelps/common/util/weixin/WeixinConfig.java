package com.anyonehelps.common.util.weixin;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.anyonehelps.common.constants.Constant;
import com.anyonehelps.common.util.PropertiesReader;
import com.anyonehelps.common.util.StringUtil;

public class WeixinConfig {
	
	static { 
		Properties props = PropertiesReader.read(Constant.SYSTEM_PROPERTIES_FILE);
		key = props.getProperty("weixin.apikey");
		appid = props.getProperty("weixin.appid");
		//body = props.getProperty("weixin.body");
		mch_id = props.getProperty("weixin.mch.id");
		notify_url = props.getProperty("weixin.notify.url");
		//trade_type = props.getProperty("weixin.trade.type");
	} 
	/*public static final String DEFAULT_KEY = "XW56zlrXLbjZ97R9IocxkGUfqvtbPHyF";
	public static final String DEFAULT_APPID = "wxe1dd8f26dd6189a8";
	public static final String DEFAULT_BODY = "扫码充值";
	public static final String DEFAULT_MCH_ID = "1295576401";
	public static final String DEFAULT_NOTIFY_URL = "http://127.0.0.1:8080/anyonehelps/weixin/scanRechargeSuccess";
	public static final String DEFAULT_TRADE_TYPE = "NATIVE";
	*/
	public static String key;//= DEFAULT_KEY;
	//公众账号ID
	public static String appid;//= DEFAULT_APPID;
	//商品描述
	public static String body = "AnyoneHelps扫码充值";//= DEFAULT_BODY;
	//商户号
	public static String mch_id;// = DEFAULT_MCH_ID;
	public static String nonce_str = StringUtil.generateRandomString(10);//"sdhouigvjasoignwap";//StringUtil.generateRandomString(10)
	public static String notify_url;// = DEFAULT_NOTIFY_URL;//http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/user/index.jsp
	//商户订单号
	public static String out_trade_no = "";
	public static String product_id = "";
	
	//APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。
	public static String spbill_create_ip = "127.0.0.1";
	
	/*JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付，统一下单接口trade_type的传参可参考这里
		MICROPAY--刷卡支付，刷卡支付有单独的支付接口，不调用统一下单接口*/
	public static String trade_type = "NATIVE";
	
	/**
	 * need to set up total_fee, body, spbill_create_ip, out_trade_no, product_id by self
	 * @return map with default key: appid, mch_id, nonce_str, notify_url, trade_type 
	 */
	public static Map<String, String> getDefaultMap(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("appid", appid);
		map.put("mch_id", mch_id);
		map.put("nonce_str", nonce_str);
		map.put("notify_url", notify_url);
		map.put("trade_type", trade_type);
		return map;
	}
	
	/**
	 * set up necessary keys after getDefaultMap() method;
	 * @param map
	 */
	public static void setOtherFields(Map<String, String> map, String totalFee, String body, String outTradeNo, String productId){
		map.put("total_fee", totalFee);  //总金额
		map.put("body", body);
		map.put("spbill_create_ip", WeixinScanPayUtil.getLocalIp());
		map.put("out_trade_no", outTradeNo);
		map.put("product_id", productId);
	}
}
