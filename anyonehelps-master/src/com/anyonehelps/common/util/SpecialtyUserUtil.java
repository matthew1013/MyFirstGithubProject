package com.anyonehelps.common.util;

import com.anyonehelps.common.constants.ValidationConstants;


public class SpecialtyUserUtil {
	
	/**
	 * 验证用户技能类型是否符合规定，如果不符合，则返回false。
	 * 
	 * @param type
	 * @return
	 */
	public static boolean validateType(String type) {
		return "0".equals(type)|| "1".equals(type);
	}
	
	/**
	 * 验证用户专长类型ID是否符合规定，如果不符合，则返回false。
	 * 
	 * @param content
	 * @return
	 */
	public static boolean validateSpecialtyTypeId(String STId) {
		return !StringUtil.isEmpty(STId) && StringUtil.isPattern(ValidationConstants.SPECIALTYTYPE_ID, STId);
	}
	
	/**
	 * 验证专长ID是否符合规定，如果不符合，则返回false。
	 * 
	 * @param content
	 * @return
	 */
	public static boolean validateSpecialtyId(String sId) {
		return !StringUtil.isEmpty(sId) && StringUtil.isPattern(ValidationConstants.SPECIALTY_ID, sId);
	}
	/**
	 * 验证用户专长状态是否符合规定，如果不符合，则返回false。
	 * 
	 * @param content
	 * @return
	 */
	public static boolean validateState(String state) {
		return !StringUtil.isEmpty(state) && StringUtil.isPattern(ValidationConstants.SPECIALTYUSER_STATE, state);
	}

	public static boolean validateId(String id) {
		return !StringUtil.isEmpty(id) && StringUtil.isPattern(ValidationConstants.COMMON_ID_REGEX, id);
	}


}
