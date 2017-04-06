package com.anyonehelps.common.util;


import javax.servlet.http.HttpServletRequest;

import com.anyonehelps.common.constants.ValidationConstants;

public class ReceiveDemandUtil {
	
	/**
	 * 验证付款比例是否正确。
	 * 
	 * @param percentage
	 * @return
	 */
	public static boolean validatePercentage(String percentage) {
		double dPer = Double.valueOf(percentage != null ? percentage.trim() : "");
		if (dPer >= 0 && dPer <= 100) { 
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 检查demand_id 是否符合规定。如果符合则返回true。否则返回false。
	 * 
	 * @param demandId
	 * @return
	 */
	public static boolean validateDemandId(String demandId) {
		return !StringUtil.isEmpty(demandId)&& StringUtil.isPattern(ValidationConstants.COMMON_ID_REGEX, demandId);
	}
	
	/**
	 * 获取客户端的ip地址
	 * @param request
	 * @return ip
	 */
	public static String getIpAddr(HttpServletRequest request) { 
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	 } 
	
	/**
	 * 获取客户端的ip地址
	 * @param request
	 * @return ip
	 */
	/*public static void getRegionByIp(ReceiveDemand rd) { 
		HttpRequestUtil hru = new HttpRequestUtil();
		
		Properties prop =  PropertiesReader.read(Constant.SYSTEM_PROPERTIES_FILE);
		String text = prop.getProperty("ip.address.url");
		text = MessageFormat.format(text, new Object[] {rd.getIp()});
		String result = hru.sendGet(text, null);
		try{
			JSONObject json = JSONObject.fromObject(result);
			System.out.println(result);
			if (json.getString("status").equals("success")) {
				rd.setCountry(json.getString("country"));
				rd.setCity(json.getString("city"));
				rd.setRegion(json.getString("regionName"));
			}else{
				rd.setCountry("");
				rd.setCity("");
				rd.setRegion("");
			}
		}catch (Exception e) {
			
		}
	 }*/ 
	
	/**
	 * 获取客户端的ip地址信息
	 * @param request
	 * @return ip
	 */
	/*public static IPInfo getIPInfoByIp(String ip) { 
		ip = "116.22.199.252"; //test 
		HttpRequestUtil hru = new HttpRequestUtil();
		IPInfo ipInfo = new IPInfo();
		Properties prop =  PropertiesReader.read(Constant.SYSTEM_PROPERTIES_FILE);
		String text = prop.getProperty("ip.address.url");
		text = MessageFormat.format(text, new Object[] {ip});
		System.out.println("====================");
		System.out.println("text:"+text);
		String result = hru.sendGet(text, null);
		try{
			JSONObject json = JSONObject.fromObject(result);
			System.out.println(result);
			ipInfo.setAs(json.getString("as"));
			ipInfo.setCity(json.getString("city"));
			ipInfo.setCountry(json.getString("country"));
			ipInfo.setCountryCode(json.getString("countryCode"));
			ipInfo.setIsp(json.getString("isp"));
			ipInfo.setLat(json.getString("lat"));
			ipInfo.setLon(json.getString("lon"));
			ipInfo.setOrg(json.getString("org"));
			ipInfo.setQuery(ip);
			ipInfo.setRegion(json.getString("region"));
			ipInfo.setRegionName(json.getString("regionName"));
			ipInfo.setStatus(json.getString("status"));
			ipInfo.setTimezone(json.getString("timezone"));
			ipInfo.setZip(json.getString("zip"));
			
		}catch (Exception e) {
			
		}
		return ipInfo;
	 }*/

	public static boolean validateId(String id) {
		return !StringUtil.isEmpty(id)&& StringUtil.isPattern(ValidationConstants.COMMON_ID_REGEX, id);
	}

	public static boolean validateResultDesc(String resultDesc) {
		return !StringUtil.isEmpty(resultDesc);
	} 
}
