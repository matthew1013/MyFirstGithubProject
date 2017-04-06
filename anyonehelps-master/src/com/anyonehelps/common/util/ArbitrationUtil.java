package com.anyonehelps.common.util;

import com.anyonehelps.common.constants.ValidationConstants;

public class ArbitrationUtil {

	/**
	 * 验证仲裁是否正确。
	 * 
	 * @param id
	 * @return
	 */
	public static boolean validateId(String id) {
		return !StringUtil.isEmpty(id) && StringUtil.isPattern(ValidationConstants.COMMON_ID_REGEX, id);
	}
	
	/**
	 * 验证任务编号是否正确。
	 * 
	 * @param id
	 * @return
	 */
	public static boolean validateDemandId(String id) {
		return !StringUtil.isEmpty(id) && StringUtil.isPattern(ValidationConstants.COMMON_ID_REGEX, id);
	}
	/**
	 * 验证付款比例是否正确。
	 * 
	 * @param percentage
	 * @return
	 */
	public static boolean validatePercentage(String percentage) {
		double dPer = Double.valueOf(percentage != null ? percentage.trim() : "");
		if (dPer > 0 && dPer <= 100) {
			return true;
		}else {
			return false;
		}
	}
	/**
	 * 验证接受任务编号是否正确。
	 * 
	 * @param id
	 * @return
	 */
	public static boolean validateReceiveDemandId(String id) {
		return !StringUtil.isEmpty(id) && StringUtil.isPattern(ValidationConstants.COMMON_ID_REGEX, id);
	}
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
}
