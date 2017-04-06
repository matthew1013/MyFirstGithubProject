package com.anyonehelps.common.util;

import java.text.SimpleDateFormat;


public class DemandAttachUtil {
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
	 * 检查userId是否符合规定。如果符合则返回true。否则返回false。
	 * 
	 * @param userId
	 * @return
	 */
	public static boolean validateUserId(String userId) {
		return !StringUtil.isEmpty(userId);
	}
	
	/**
	 * 检查content是否符合规定。如果符合则返回true。否则返回false。
	 * 
	 * @param content
	 * @return
	 */
	public static boolean validateContent(String content) {
		return !StringUtil.isEmpty(content);
	}
	
	/**
	 * 检查demandId是否符合规定。如果符合则返回true。否则返回false。
	 * 
	 * @param demandId
	 * @return
	 */
	public static boolean validateDemandId(String demandId) {
		return !StringUtil.isEmpty(demandId);
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
		return Double.valueOf(amount)>0;
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

}
