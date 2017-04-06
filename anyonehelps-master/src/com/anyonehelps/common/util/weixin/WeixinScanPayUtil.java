package com.anyonehelps.common.util.weixin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jxl.common.Logger;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.anyonehelps.common.util.MD5Util;

public class WeixinScanPayUtil {
	private static final Logger logger = Logger.getLogger(WeixinScanPayUtil.class);
	public static String getLocalIp(){
		String ip = null;
		Enumeration<NetworkInterface> allNetInterfaces;
		try{
			allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			while(allNetInterfaces.hasMoreElements()){
				NetworkInterface networkInterface = (NetworkInterface) allNetInterfaces.nextElement();
				List<InterfaceAddress> interfaceAddress  = networkInterface.getInterfaceAddresses();
				for(InterfaceAddress address : interfaceAddress){
					InetAddress inetAddress = address.getAddress();
					if(inetAddress != null && inetAddress instanceof Inet4Address){
						ip = inetAddress.getHostAddress();
					}
				}
			}
		}catch (SocketException e){
			e.printStackTrace();
			logger.error("获取本机Ip失败:异常信息:"+e.getMessage());
		}
		return ip;
		
	}
	public static String getSign(Map<String, String> hashMap){
		 ArrayList<String> list = new ArrayList<String>();
       for(Map.Entry<String, String> entry:hashMap.entrySet()){
           if(entry.getValue()!="" && !entry.getKey().equals("sign")){
               list.add(entry.getKey() + "=" + entry.getValue() + "&");
           }
       }

       int size = list.size();
       String [] arrayToSort = list.toArray(new String[size]);
       Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
       StringBuilder stringBuilder = new StringBuilder();
       for(int i = 0; i < size; i ++) {
    	   stringBuilder.append(arrayToSort[i]);
       }
       String result = stringBuilder.toString();
       result += "key=" + WeixinConfig.key;
       result = MD5Util.encode(result).toUpperCase();
       return result;
	        
   }
	public static String mapToXml(Map<String, String> map){
		 synchronized (WeixinScanPayUtil.class){
	            StringBuilder stringBuilder = new StringBuilder();
	            stringBuilder.append("<xml>");
	            Set<String> stringSet = map.keySet();
	            for (String key : stringSet){
	                if (key == null){
	                    continue;
	                }
	                stringBuilder.append("\n");
	                stringBuilder.append("<").append(key.toString()).append(">");
	                String value = map.get(key);
	                if(StringUtils.isEmpty(value)){
	                	stringBuilder.append("null");
	                }else{
	                	stringBuilder.append(value);
	                }
	                stringBuilder.append("</").append(key.toString()).append(">");
	            }
	            stringBuilder.append("\n");
	            stringBuilder.append("</xml>");
	            return stringBuilder.toString();
	        }
	}
	    
    private final static int CONNECT_TIMEOUT = 5000; // in milliseconds
	private final static String DEFAULT_ENCODING = "UTF-8";
	private final static String DEFAULT_POST_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	public static String postDataByMap(Map<String, String> map){
		String postUrl = DEFAULT_POST_URL;
		return postDataByMap(postUrl, map);
	}
	
	public static String postDataByMap(String postUrl, Map<String, String> map){
		String sign;
		String codeUrl = null;
		try {
			sign = getSign(map);
			map.put("sign", sign);
			String xmlData = mapToXml(map);
			String responseXml = postData(postUrl, xmlData, null);
			Document document = null;
			try{
				document = DocumentHelper.parseText(responseXml);
			}catch(DocumentException e){
				e.printStackTrace();
				return "";
			}
			if(document != null){
				Element rootElement = document.getRootElement();
				if(rootElement == null){
					return "";
				}
				Element codeUrlElement = rootElement.element("code_url");
				if(codeUrlElement == null){//what is this ? codeUrlElement?
					return "";
				}
				codeUrl = codeUrlElement.getText();
				return codeUrl;
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return codeUrl;
	}
	public static String postData(String urlString, String data){
		return postData(urlString, data, null);
	}
	public static String postData(String urlString, String data, String contentType){
		BufferedReader bufferedReader = null;
		try{
			URL url = new URL(urlString);
			URLConnection urlConnection =  url.openConnection();
			urlConnection.setDoOutput(true);
			urlConnection.setConnectTimeout(CONNECT_TIMEOUT);
			urlConnection.setReadTimeout(CONNECT_TIMEOUT);
			if(StringUtils.isNotBlank(contentType)){
				urlConnection.setRequestProperty("content-type", contentType);
			}
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(urlConnection.getOutputStream(), DEFAULT_ENCODING);
			if(data == null){
				data = "";
			}
			outputStreamWriter.write(data);
			outputStreamWriter.flush();
			outputStreamWriter.close();
			bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), DEFAULT_ENCODING));
			StringBuilder stringBuilder = new StringBuilder();
			String line = null;
			while((line = bufferedReader.readLine()) != null){
				stringBuilder.append(line);
				stringBuilder.append("\r\n");
			}
			return stringBuilder.toString();
		}catch(IOException e){
			e.printStackTrace();
			logger.error("Error connecting to " + urlString + ": " + e.getMessage());
		}finally{
			try{
				if(bufferedReader != null){
					bufferedReader.close();
				}
			}catch(IOException e){
				
			}
		}
		return null;
	}
	
	public static Map<String, String> xmlToMap(String xml){
		Map<String, String> map = new HashMap<String, String>();
		Document document = null;
		try {
			document = DocumentHelper.parseText(xml);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		if(document != null){
			Element rootElement = document.getRootElement();
			setElementToMap(rootElement, "appid", map);
			setElementToMap(rootElement, "nonce_str", map);
			setElementToMap(rootElement, "out_trade_no", map);
			setElementToMap(rootElement, "result_code", map);
			setElementToMap(rootElement, "return_code", map);
			setElementToMap(rootElement, "time_end", map);
			setElementToMap(rootElement, "total_fee", map);
			setElementToMap(rootElement, "trade_type", map);
			setElementToMap(rootElement, "transaction_id", map);
			setElementToMap(rootElement, "sign", map);
			setElementToMap(rootElement, "attach", map);
		}
		return map;
	}
	
	public static void setElementToMap(Element rootElement, String key, Map<String,String> map){
		if(rootElement != null){
			Element element = rootElement.element(key);
			if(element != null){
				map.put(key, element.getText());
			}
		}
	}
}
