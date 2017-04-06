package com.anyonehelps.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.anyonehelps.exception.ServiceException;


/**
 * 时间工具类
 */
public class DateUtil {

	/**
	 * 时间格式
	 */
	public static String DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 将时间对象转换为定义格式的字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String date2String(Date date) {
		return new SimpleDateFormat(DATE_FORMAT_PATTERN).format(date);
	}

	/**
	 * 将时间格式转换为给定格式
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String date2String(Date date, String format) {
		return new SimpleDateFormat(format).format(date);
	}

	/**
	 * 将long型的时间time，转换为定义格式的时间字符串
	 * 
	 * @param time
	 * @return
	 */
	public static String long2String(long time) {
		return date2String(new Date(time));
	}

	/**
	 * 将规定格式的时间字符串转换为date对象<br/>
	 * 如果参数时间date不符合定义的格式，那么会抛出一个serviceexception。
	 * 
	 * @param date
	 * @return
	 * @throws ServiceException
	 */
	public static Date string2Date(String date) throws ServiceException {
		try {
			return new SimpleDateFormat(DATE_FORMAT_PATTERN).parse(date);
		} catch (ParseException e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
	}
}
