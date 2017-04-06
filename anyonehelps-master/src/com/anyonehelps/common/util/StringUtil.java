package com.anyonehelps.common.util;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParser.Feature;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.util.StringUtils;

import com.anyonehelps.exception.ServiceException;

public class StringUtil {
	private static Logger logger = Logger.getLogger(StringUtil.class);
	private static final char ESCAPE_CHARACTER = '\\';
	private static final char PERCENT = '%';
	private static final char ASTERISK = '*';
	private static final char SLIDA_LINE = '_';
	private static final char LESS_THAN_CHAR = '<';
	private static final String REPLACE_LESS_THAN = "&lt;";
	private static final char GRENTER_THAN_CHAR = '>';
	private static final String REPLACE_GRENTER_THAN = "&gt;";
	private static final char AMPERSAND = '&';
	private static final String REPLACE_AMPERSAND = "&amp;";
	
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
	 * 检测date是否符合yyyy-mm-dd或者yyyy-m-d或者其他的时间格式
	 * 
	 * @param date
	 * @return
	 */
	public static boolean validateExportDate(String date) {
		return StringUtil.isPattern("\\d{4}-(\\d|\\d{2})-(\\d|\\d{2})", date);
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
	
	public static boolean isValidDate(String dateTime) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			dateFormat.parse(dateTime);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isMoreZeroInteger(String str) {
		try {
			int k = Integer.valueOf(str.trim());
			return k > 0;
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * 判断字符串是否为空，如果为空，则返回true。否则返回false。
	 * 
	 * @param str
	 *            要判断的字符串
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return !StringUtils.hasText(str);
	}

	/**
	 * 如果字符串str符合regex正则字符串的样式，则返回true。否则返回false。<br/>
	 * 如果要检测的字符串为null也返回false。
	 * 
	 * @param regex
	 * @param str
	 * @return
	 */
	public static boolean isPattern(String regex, String str) {
		return str != null && Pattern.matches(regex, str);
	}

	/**
	 * 获取size长度的字符串。字符串内容是value的后size个字符，如果value长度不够size，前面填充0
	 * 
	 * @param value
	 * @param size
	 * @return
	 */
	public static String getTruncationString(String value, int size) {
		int len = value.length();
		if (len >= size) {
			int i = len - size;
			return value.substring(i);
		} else {
			StringBuffer sb = new StringBuffer(size);
			int i = size - len;
			while (i-- > 0) {
				sb.append("0");
			}
			sb.append(value);
			return sb.toString();
		}
	}

	/**
	 * 产生一个定长数字组成的字符串。
	 * 
	 * @param length
	 * @return
	 */
	public static String generateRandomInteger(int length) {
		StringBuilder result = new StringBuilder();
		Random random = new Random(System.currentTimeMillis());
		for (int i = 0; i < length; i++) {
			result.append(random.nextInt(10));
		}
		return result.toString();
	}

	/**
	 * 产生一个给定长度的随机字符串
	 * 
	 * @param length
	 *            要产生字符串的长度
	 * @return
	 */
	public static String generateRandomString(int length) {
		StringBuilder result = new StringBuilder();
		Random random = new Random(System.currentTimeMillis());
		for (int i = 0; i < length; i++) {
			int randomNum = random.nextInt(26);
			if (random.nextBoolean()) {
				randomNum += 32;
			}
			result.append((char) ('A' + randomNum));
		}
		return result.toString();
	}

	/**
	 * 产生一个随机字符串
	 * 
	 * @return
	 */
	public static String random() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 将json字符串转换为给定的Class类型
	 * 
	 * @param <T>
	 * @param json
	 * @param clazz
	 * @return
	 * @throws ServiceException
	 */
	public static <T> T json2Object(String json, Class<T> clazz) throws ServiceException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		mapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
		T t;
		try {
			t = mapper.readValue(json, clazz);
		} catch (Exception e) {
			logger.error("将json数据转换成" + clazz.getClass() + "对象失败", e);
			throw new ServiceException(e.getMessage());
		}
		return t;
	}

	/**
	 * 将给定的json字符串转换为set对象
	 * 
	 * @param <T>
	 * @param json
	 * @param clazz
	 * @return
	 * @throws ServiceException
	 */
	public static <T> Set<T> json2ObjectSet(String json, Class<T> clazz) throws ServiceException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		mapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
		Set<T> set = new HashSet<T>();
		JSONArray jsonArray = JSONArray.fromObject(json);
		T t;
		for (Object jsonObject : jsonArray) {
			t = json2Object(jsonObject.toString(), clazz);
			set.add(t);
		}
		return set;
	}

	/**
	 * 将value对象转换成字符串，如果value为null，则返回""字符串。否则调用value的tostring方法。
	 * 
	 * @param value
	 * @return
	 */
	public static String obj2String(Object value) {
		if (value == null) {
			return "";
		}
		return value.toString();
	}

	/**
	 * 将符合bigdecimal格式的字符串转换为bigdecimal格式的数据
	 * 
	 * @param value
	 * @return
	 */
	public static double string2Double(String value) {
		if (StringUtil.isEmpty(value)) {
			return 0;
		}
		return Double.valueOf(value.trim());
	}

	/**
	 * 将字符串转换成为数字
	 * 
	 * @param value
	 * @return
	 */
	public static int string2Integer(String value) {
		return Integer.valueOf(value);
	}

	/**
	 * 获取value值，如果为空，则返回defaultvalue值
	 * 
	 * @param value
	 * @param defaultvalue
	 * @return
	 */
	public static String getValue(String value, String defaultvalue) {
		if (StringUtil.isEmpty(value)) {
			return defaultvalue;
		}
		return value.trim();
	}

	/**
	 * 处理搜索查询中的key word
	 * 
	 * @param content
	 * @return
	 */
	public static String escapeStringOfSearchKey(String content) {
		if (StringUtil.isEmpty(content)) {
			return "%";
		}

		int length = content.length();
		StringBuffer stringBuffer = new StringBuffer(length);
		stringBuffer.append("%");

		for (int i = 0; i < length; i++) {
			char ch = content.charAt(i);

			if (ch == StringUtil.PERCENT) {
				stringBuffer.append(StringUtil.ESCAPE_CHARACTER);
			} else if (ch == StringUtil.ESCAPE_CHARACTER) {
				stringBuffer.append(StringUtil.ESCAPE_CHARACTER);
			} else if (ch == StringUtil.SLIDA_LINE) {
				stringBuffer.append(StringUtil.ESCAPE_CHARACTER);
			} else if (ch == StringUtil.ASTERISK) {
				stringBuffer.append(StringUtil.ESCAPE_CHARACTER);
			}

			if (ch == StringUtil.LESS_THAN_CHAR) {
				stringBuffer.append(StringUtil.REPLACE_LESS_THAN);
			} else if (ch == StringUtil.GRENTER_THAN_CHAR) {
				stringBuffer.append(StringUtil.REPLACE_GRENTER_THAN);
			} else if (ch == StringUtil.AMPERSAND) {
				stringBuffer.append(StringUtil.REPLACE_AMPERSAND);
			} else {
				stringBuffer.append(ch);
			}
		}

		stringBuffer.append("%");
		return stringBuffer.toString();
	}

	/**
	 * 反处理特殊字符串。和encodeSpecialWord方法恰恰相反。
	 * 
	 * @param str
	 * @return
	 */
	public static String dealHtmlSpecialCharacters(String str) {
		return str;
	}

	/**
	 * 反处理特殊字符串。和encodeSpecialWord方法恰恰相反。
	 * 
	 * @param str
	 * @return
	 */
	public static String escapeStringBack(String str) {
		return str;
	}
	

}
