package com.anyonehelps.common.util;

import java.util.Date;

import com.anyonehelps.common.constants.ValidationConstants;


public class AccountUtil {
	public static final String getAlipayOrderIdByAccountDetailId(String id) {
		return DateUtil.date2String(new Date(), "yyyyMMdd") + "_" + id; 
	} 

	public static final String getAccountDetailIdByAlipayOrderId(String id) {
		int index = id.indexOf("_") + 1;
		return id.substring(index);
	}

	public static boolean validateCurrency(String currency) {
		return currency.equals("usd")/*||currency.equals("rmb")*/;
	}
	public static boolean validateAmount(String amount) {
		return !StringUtil.isEmpty(amount) ;
	}
	
	public static boolean validateCreditName(String name) {
		return !StringUtil.isEmpty(name) && StringUtil.isPattern(ValidationConstants.CREDIT_NAME_REGEX, name);
	}

	public static boolean validateCreditZipCode(String zipCode) {
		return !StringUtil.isEmpty(zipCode) && StringUtil.isPattern(ValidationConstants.CREDIT_ZIP_CODE_REGEX, zipCode);
	}

	public static boolean validateCreditAddress(String address) {
		return !StringUtil.isEmpty(address) && StringUtil.isPattern(ValidationConstants.CREDIT_ADDRESS_REGEX, address);
	}

	public static boolean validateCreditCity(String city) {
		return !StringUtil.isEmpty(city) && StringUtil.isPattern(ValidationConstants.CREDIT_CITY_REGEX, city);
	}

	public static boolean validateCreditProvince(String province) {
		return !StringUtil.isEmpty(province)
		        && StringUtil.isPattern(ValidationConstants.CREDIT_PROVINCE_REGEX, province);
	}

	public static boolean validateCreditNo(String creditNo) {
		return !StringUtil.isEmpty(creditNo) && StringUtil.isPattern(ValidationConstants.CREDIT_NO_REGEX, creditNo);
	}

	public static boolean validateCreditSecurityCode(String code) {
		return !StringUtil.isEmpty(code) && StringUtil.isPattern(ValidationConstants.CREDIT_CODE_REGEX, code);
	}

	public static boolean validateCreditExpireYear(String year) {
		return StringUtil.isEmpty(year) || StringUtil.isPattern(ValidationConstants.CREDIT_EXPIRE_YEAR, year);
	}

	public static boolean validateCreditExpireMonth(String month) {
		return StringUtil.isEmpty(month) || StringUtil.isPattern(ValidationConstants.CREDIT_EXPIRE_MONTH, month);
	}

	public static boolean validateCreditType(String type) {
		return !StringUtil.isEmpty(type) && StringUtil.isPattern(ValidationConstants.CREDIT_TYPE_REGEX, type);
	}

}
