package com.anyonehelps.common.util;

import com.anyonehelps.common.constants.ValidationConstants;

public class NewsCommentUtil {
	
	/**
	 * 检查new_id 是否符合规定。如果符合则返回true。否则返回false。
	 * 
	 * @param newId
	 * @return
	 */
	public static boolean validateNewId(String newId) {
		return !StringUtil.isEmpty(newId)&& StringUtil.isPattern(ValidationConstants.COMMON_ID_REGEX, newId);
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
	
}
