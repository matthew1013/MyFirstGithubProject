package com.anyonehelps.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import com.anyonehelps.common.constants.ValidationConstants;

public class UserUtil {
	
	public static void downloadPicToS3(String urlString, String filename,  
            String savePath,String S3Path) throws Exception {  
		// 构造URL    
        URL url = new URL(urlString);  
        // 打开连接    
        URLConnection con = url.openConnection();  
        //设置请求超时为5s    
        con.setConnectTimeout(5 * 1000);  
        // 输入流    
        InputStream is = con.getInputStream();  
  
        // 1K的数据缓冲    
        byte[] bs = new byte[1024];  
        // 读取到的数据长度    
        int len;  
        // 输出的文件流    
        File sf = new File(savePath);  
        if (!sf.exists()) {  
            sf.mkdirs();  
        }  
        OutputStream os = new FileOutputStream(sf.getPath() + "\\" + filename);  
        // 开始读取    
        while ((len = is.read(bs)) != -1) {  
            os.write(bs, 0, len);  
        }  
        // 完毕，关闭所有链接    
        os.close();  
        is.close();  
        File file = new File(sf.getPath() + "\\" + filename);  
        AmazonS3Samples amazonS3Samples = new AmazonS3Samples();
		amazonS3Samples.pushFile(S3Path+"/"+filename,file);
		file.delete();
    }  

	public static void downloadPic(String urlString, String filename,  
            String savePath) throws Exception {  
        // 构造URL    
        URL url = new URL(urlString);  
        // 打开连接    
        URLConnection con = url.openConnection();  
        //设置请求超时为5s    
        con.setConnectTimeout(5 * 1000);  
        // 输入流    
        InputStream is = con.getInputStream();  
  
        // 1K的数据缓冲    
        byte[] bs = new byte[1024];  
        // 读取到的数据长度    
        int len;  
        // 输出的文件流    
        File sf = new File(savePath);  
        if (!sf.exists()) {  
            sf.mkdirs();  
        }  
        OutputStream os = new FileOutputStream(sf.getPath() + "\\" + filename);  
        // 开始读取    
        while ((len = is.read(bs)) != -1) {  
            os.write(bs, 0, len);  
        }  
        // 完毕，关闭所有链接    
        os.close();  
        is.close();  
    }  
	
	/**
	 * 验证用户编码是否正确。
	 * 
	 * @param id
	 * @return
	 */
	public static boolean validateId(String id) {
		return !StringUtil.isEmpty(id) && StringUtil.isPattern(ValidationConstants.COMMON_ID_REGEX, id);
	}
	
	/**
	 * 验证微信号是否正确。
	 * 
	 * @param wechat
	 * @return
	 */
	public static boolean validateWechat(String wechat) {
		return StringUtil.isEmpty(wechat) || StringUtil.isPattern(ValidationConstants.WE_CHAT_REGEX, wechat);
	}
	
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
	 * 检查zip code是否符合规定。如果符合则返回true。否则返回false。
	 * 
	 * @param zipCode
	 * @return
	 */
	public static boolean validateZipCode(String zipCode) {
		return StringUtil.isEmpty(zipCode) || StringUtil.isPattern(ValidationConstants.USER_ZIP_CODE_REGEX, zipCode);
	}

	/**
	 * 校验昵称，如果账户为空或者是不符合规定，则返回false。否则返回true。
	 * 
	 * @param nickName
	 * @return
	 */
	public static boolean validateNickName(String nickName) {
		return !StringUtil.isEmpty(nickName)
		        && StringUtil.isPattern(ValidationConstants.USER_NICK_NAME_REGEX, nickName);
	}

	/**
	 * 检查密码，如果密码为空或者不符合规定，则返回false。否则返回true。
	 * 
	 * @param password
	 * @return
	 */
	public static boolean validatePassword(String password) {
		return !StringUtil.isEmpty(password) && StringUtil.isPattern(ValidationConstants.USER_PASSWORD_REGEX, password);
	}


	/**
	 * 检查电话号码国家区号是否符合规定。如果符合则返回true。否则返回false。
	 * 
	 * @param phone
	 * @return
	 */
	public static boolean validateAreaCode(String areaCode) {
		return !StringUtil.isEmpty(areaCode) && StringUtil.isPattern(ValidationConstants.USER_AREA_CODE_REGEX, areaCode);
	}
	
	/**
	 * 检查电话号码是否符合规定。如果符合则返回true。否则返回false。
	 * 
	 * @param phone
	 * @return
	 */
	public static boolean validatePhone(String phone) {
		return !StringUtil.isEmpty(phone) && StringUtil.isPattern(ValidationConstants.USER_PHONE_REGEX, phone);
	}
	
	/**
	 * 检查实时联系方式是否符合规定。如果符合则返回true。否则返回false。
	 * 
	 * @param otherContact
	 * @return
	 */
	public static boolean validateOtherContact(String oc) {
		return !StringUtil.isEmpty(oc) && StringUtil.isPattern(ValidationConstants.USER_OTHER_CONTACT_REGEX, oc);
	}
	
	/**
	 * 检查推荐人账户是否符合规定。如果符合则返回true。否则返回false。
	 * 
	 * @param realName
	 * @return
	 */
	public static boolean validateRecommender(String recommender) {
		return StringUtil.isEmpty(recommender) || "-1".equals(recommender)
		        || StringUtil.isPattern(ValidationConstants.COMMON_ID_REGEX, recommender);
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

	public static boolean validateSex(String sex) {
		return !StringUtil.isEmpty(sex) && StringUtil.isPattern(ValidationConstants.USER_SEX, sex);
	}
}
