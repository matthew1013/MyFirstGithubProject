package com.anyonehelps.common.util;

import com.anyonehelps.common.constants.ValidationConstants;

public class TicketUtil {
	
	/**
	 * 检查email是否符合规定。如果符合则返回true。否则返回false。
	 * 
	 * @param email
	 * @return
	 */
	public static boolean validateEmail(String email) {
		return !StringUtil.isEmpty(email) && StringUtil.isPattern(ValidationConstants.USER_EMAIL_REGEX, email);
	}
	
	/**
	 * 检查name是否符合规定。如果符合则返回true。否则返回false。
	 * 
	 * @param name
	 * @return
	 */
	public static boolean validateName(String name) {
		return !StringUtil.isEmpty(name);
	}
	
	/**
	 * 检查subject是否符合规定。如果符合则返回true。否则返回false。
	 * 
	 * @param subject
	 * @return
	 */
	public static boolean validateSubject(String subject) {
		return !StringUtil.isEmpty(subject);
	}
	
	/**
	 * 检查description是否符合规定。如果符合则返回true。否则返回false。
	 * 
	 * @param description
	 * @return
	 */
	public static boolean validateDescription(String description) {
		return !StringUtil.isEmpty(description);
	}
}
