package com.anyonehelps.common.util;

import java.text.SimpleDateFormat;

import com.anyonehelps.common.constants.ValidationConstants;


public class DemandUtil {
	public static boolean validateId(String id) {
		return !StringUtil.isEmpty(id)&& StringUtil.isPattern(ValidationConstants.COMMON_ID_REGEX, id);
	} 
	/**
	 * 检查title是否符合规定。如果符合则返回true。否则返回false。
	 * 
	 * @param title
	 * @return
	 */
	public static boolean validateTitle(String title) {
		return !StringUtil.isEmpty(title);
	}
	/**
	 * 检查achieve是否符合规定。如果符合则返回true。否则返回false。
	 * 
	 * @param achieve
	 * @return
	 */
	public static boolean validateAchieve(String achieve) {
		return !StringUtil.isEmpty(achieve);
	}
	
	/**
	 * 检查amount是否不为空且转换double是否于为0。如果符合则返回true。否则返回false。
	 * 
	 * @param amount
	 * @return
	 */
	public static boolean validateAmount(String amount) {
		if(StringUtil.isEmpty(amount)){
			return false;
		}
		return Double.valueOf(amount)>=10;
	}
	
	/**
	 * 转换时间字符串为yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String transformerDateString(String date, String endStr) {
		StringBuffer sb = new StringBuffer(10);
		int len = date.length();
		int k = 0;
		for (int i = 0; i < len; i++) {
			char ch = date.charAt(i);
			k++;
			sb.append(ch);
			if (ch == '-') {
				if (k == 2) {
					// 不是在那个位置的
					sb.insert(sb.length() - 2, '0');
				}
				k = 0;
			}
		}
		if (k == 1) {
			sb.insert(sb.length() - 1, '0');
		}
		return sb.toString() + endStr;
	}
	
	/**
	 * 判断上传文件是否符合格式  chenkanghua 2015-12-29
	 * 
	 * @param filename--上传的文件类型
	 *        typestr---要匹配的字符串类型
	 * @return
	 */
	public static boolean validateFileType(String filename,String typestr) {
		
		
		if((filename==null)||(filename.equalsIgnoreCase("")))
		{
			return false;
		}
		if((typestr==null)||(typestr.equalsIgnoreCase("")))
		{
			return false;
		}
		String[] typepic=typestr.split(";");		
		for(int i=0;i<typepic.length;i++)
		{
			String str1=typepic[i].toLowerCase();
			
			int index = filename.lastIndexOf('.');
			String str="";
			str=filename.substring(index+1);
			if(str1.equalsIgnoreCase(str))
			{
				return true;
			}			
		}
		
		return false;
	}
	/**
	 * 字符串日期是否符合 yyyy-MM-dd HH:mm:ss 这种格式 
	 * @param dameTime
	 * @return
	 */
	public static boolean isValidDateTime(String dameTime) {
		boolean convertSuccess = true;
		if(StringUtil.isEmpty(dameTime)){
			return false;
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			format.setLenient(false);
			format.parse(dameTime);
		} catch (Exception e) {
			convertSuccess = false;
		}
		return convertSuccess;
	}

	/**
	 * 获取客户端的ip地址
	 * @param request
	 * @return ip
	 */
	/*public static void getRegionByIp(Demand d) { 
		HttpRequestUtil hru = new HttpRequestUtil();
		
		Properties prop =  PropertiesReader.read(Constant.SYSTEM_PROPERTIES_FILE);
		String text = prop.getProperty("ip.address.url");
		text = MessageFormat.format(text, new Object[] {d.getIp()});
		String result = hru.sendGet(text, null);
		try{
			JSONObject json = JSONObject.fromObject(result);
			System.out.println(result);
			if (json.getString("status").equals("success")) {
				d.setCountry(json.getString("country"));
				d.setCity(json.getString("city"));
				d.setRegion(json.getString("regionName"));
			}else{
				d.setCountry("");
				d.setCity("");
				d.setRegion("");
			}
		}catch (Exception e) {
			
		}
	 }*/

}
