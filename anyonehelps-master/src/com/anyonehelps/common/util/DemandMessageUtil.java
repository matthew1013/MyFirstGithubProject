package com.anyonehelps.common.util;

import com.anyonehelps.common.constants.ValidationConstants;

public class DemandMessageUtil {
	
	/**
	 * 检查demand_id 是否符合规定。如果符合则返回true。否则返回false。
	 * 
	 * @param demandId
	 * @return
	 */
	public static boolean validateDemandId(String demandId) {
		return !StringUtil.isEmpty(demandId);
	}
	/**
	 * 验证留言内容是否符合规定，如果不符合，则返回false。
	 * 
	 * @param content
	 * @return
	 */
	public static boolean validateContent(String content) {
		return !StringUtil.isEmpty(content);
	}

	/**
	 * 验证留言的父节点，是否符合规定。如果不符合，则返回false。
	 * 
	 * @param fatherId
	 * @return
	 */
	public static boolean validateParentId(String fatherId) {
		return StringUtil.isEmpty(fatherId) || "-1".equals(fatherId)
		        || StringUtil.isPattern(ValidationConstants.COMMON_ID_REGEX, fatherId);
	}

	/**
	 * 
	 * @param state
	 * @return
	 */
	public static String getSearchColumnByState(String state) {
		if ("0".equals(state)) {
			return "0";
		} else if ("1".equals(state)) {
			return "1";
		} else {
			return null;
		}
	}
}
