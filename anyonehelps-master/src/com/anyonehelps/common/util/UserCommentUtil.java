package com.anyonehelps.common.util;

import com.anyonehelps.common.constants.ValidationConstants;

public class UserCommentUtil {
	
	/**
	 * 检查user_id 是否符合规定。如果符合则返回true。否则返回false。
	 * 
	 * @param demandId
	 * @return
	 */
	public static boolean validateUserId(String userId) {
		return !StringUtil.isEmpty(userId)&& StringUtil.isPattern(ValidationConstants.COMMON_ID_REGEX, userId);
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
