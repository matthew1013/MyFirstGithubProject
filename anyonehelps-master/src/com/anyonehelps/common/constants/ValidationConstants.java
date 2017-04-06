package com.anyonehelps.common.constants;

/**
 * 校验常量类 现在没用到。
 * 
 */
public class ValidationConstants {
	public static final String COMMON_ID_REGEX = "\\d{1,20}";
	// email为任意字符串,只要中间包含一个.
	public static final String USER_EMAIL_REGEX = "([a-zA-Z0-9_.-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-]+)+";
	// password为任意非空白字符，长度为6到20
	public static final String USER_PASSWORD_REGEX = "\\S{6,20}";
	// 账户为任意非空白字符，长度为2到20
	public static final String USER_NICK_NAME_REGEX = "[A-Za-z]+[A-Za-z0-9_]{2,19}";
	// 手机国家区号
	public static final String USER_AREA_CODE_REGEX = ".{1,6}";
	// 手机号码为十一位数字
	public static final String USER_PHONE_REGEX = "\\d{5,20}";
	// 其它联系方式
	public static final String USER_OTHER_CONTACT_REGEX = ".{0,254}";
	// 邮编
	public static final String USER_ZIP_CODE_REGEX = "\\d{4,10}";
	
	public static final String WE_CHAT_REGEX = "[A-Za-z0-9_\\+]{6,20}";
		
	public static final String MESSAGE_CONTENT_REGEX = ".{2,2048}";
	
	public static final String USERCOMMENT_CONTENT_REGEX = ".{2,2048}";
	/*
	public static final String ERROR_ORDER_CONTENT_REGEX = ".{2,200}";
	public static final String ERROR_TORDER_CONTENT_REGEX = ".{2,200}";
	public static final String ORDER_ID_REGEX = ".{2,30}";
	public static final String PRE_TRANSPORT_NUMBER = "\\d{8,}";
	public static final String PRE_COMPANY_NAME = "\\S{1,10}";
	public static final String PRE_GOODS_INFO = ".{1,500}";
	*/
	// qq号码为5到20位数字组成
	//public static final String USER_QQ_REGEX = "\\d{0,20}";
	
	
	/////////////////////////////////////////////////////////////
	//public static final String CLIENT_CUSTOMER_REGEX = ".{2,254}";
	// 手机号码为十一位数字
	//public static final String CLIENT_PHONE_REGEX = ".{0,20}";
	
	public static final String CREDIT_NAME_REGEX = ".{1,50}";
	public static final String CREDIT_ZIP_CODE_REGEX = "\\d{4,10}";
	public static final String CREDIT_ADDRESS_REGEX = ".{1,100}";
	public static final String CREDIT_NO_REGEX = ".{5,30}";
	public static final String CREDIT_CODE_REGEX = ".{1,10}";
	public static final String CREDIT_PROVINCE_REGEX = ".{1,90}";
	public static final String CREDIT_CITY_REGEX = ".{1,90}";
	public static final String CREDIT_EXPIRE_YEAR = "\\d{1,10}";
	public static final String CREDIT_EXPIRE_MONTH = "((0)?[1-9])|(1[0-2])";
	public static final String CREDIT_TYPE_REGEX = ".{1,10}";
	
	//Specialty Type
	public static final String SPECIALTYTYPE_ID = ".{1,100}";
	public static final String SPECIALTY_ID = ".{1,100}";
	public static final String SPECIALTYUSER_STATE = ".{1,10}";
	public static final String USER_SEX = "([0-3])";
	
}
