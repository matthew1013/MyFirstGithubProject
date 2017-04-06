package com.anyonehelps.common.util.sms;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.Properties;

import com.anyonehelps.common.constants.Constant;
import com.anyonehelps.common.util.Base64Util;
import com.anyonehelps.common.util.PropertiesReader;


public class SmsSendUtil {
	 
	/**
	 * 邀请用户注册信息
	 * @param userId
	 * @param nick_name
	 * @param areaCode
	 * @param mobile
	 * @return
	 * @throws Exception
	 */
	public static String sendInviteUserMsg(String userId,String nick_name, String areaCode, String mobile) throws Exception {
		try {
			Properties prop = null;
			String content = null;
			mobile = areaCode + mobile;
			if(areaCode.equals("+86")||areaCode.equals("0086")){
				prop = PropertiesReader.read(SmsConfigUtil.getSmsConfigFile(Constant.COUNTRY_CODE_CN));
				content = prop.getProperty("anyonehelps.invite.user.content");
				content = MessageFormat.format(content, new Object[] {nick_name,userId});
				SimpleSmsSender sender = SmsSenderFactory.generateSmsSender();
				return sender.sendSms(content, mobile);
			}else {
				prop = PropertiesReader.read(SmsConfigUtil.getSmsConfigFile(Constant.COUNTRY_CODE_US));
				content = prop.getProperty("anyonehelps.invite.user.content");
				content = MessageFormat.format(content, new Object[] {nick_name,userId});
				return SmsTwilioSender.sendSms(content, mobile);
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	/**
	 * 一般短信
	 * @param content
	 * @param areaCode
	 * @param mobile
	 * @return
	 * @throws Exception
	 */
	public static String sendGeneralMsg(String content, String areaCode, String mobile) throws Exception {
		try {
			mobile = areaCode + mobile;
			if(areaCode.equals("+86")||areaCode.equals("0086")){
				SimpleSmsSender sender = SmsSenderFactory.generateSmsSender();
				return sender.sendSms(content, mobile);
			}else{
				return SmsTwilioSender.sendSms(content, mobile);
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	/*public static String sendResetPwdMsg(String password,  String areaCode, String mobile) throws Exception {
		try {
			Properties prop = null;
			String content = null;
			mobile = areaCode + mobile;
			if(mobile.length()==10){
				mobile="+1"+mobile;
				prop = PropertiesReader.read(SmsConfigUtil.getSmsConfigFile(Constant.COUNTRY_CODE_US));
				content = prop.getProperty("anyonehelps.register.content");
				content = MessageFormat.format(content, new Object[] {password});
				return SmsTwilioSender.sendSms(content, mobile);
			}else {
				mobile="+86"+mobile;
				prop = PropertiesReader.read(SmsConfigUtil.getSmsConfigFile(Constant.COUNTRY_CODE_CN));
				content = prop.getProperty("anyonehelps.register.content");
				content = MessageFormat.format(content, new Object[] {password});
				SimpleSmsSender sender = SmsSenderFactory.generateSmsSender();
				return sender.sendSms(content, mobile);
			}
			
		} catch (Exception e) {
			throw new Exception(e);
		}
	}*/
	
	/**
	 * 发送验证码
	 * @param code
	 * @param areaCode
	 * @param mobile
	 * @return
	 * @throws Exception
	 */
	public static String sendRegisterMsg(String code, String areaCode, String mobile) throws Exception {
		try {
			Properties prop = null;
			String content = null;
			mobile = areaCode + mobile;
			if(areaCode.equals("+86")||areaCode.equals("0086")){
				prop = PropertiesReader.read(SmsConfigUtil.getSmsConfigFile(Constant.COUNTRY_CODE_CN));
				content = prop.getProperty("anyonehelps.register.content");
				content = MessageFormat.format(content, new Object[] {code});
				SimpleSmsSender sender = SmsSenderFactory.generateSmsSender();
				return sender.sendSms(content, mobile);
			}else {
				prop = PropertiesReader.read(SmsConfigUtil.getSmsConfigFile(Constant.COUNTRY_CODE_US));
				content = prop.getProperty("anyonehelps.register.content");
				content = MessageFormat.format(content, new Object[] {code});
				return SmsTwilioSender.sendSms(content, mobile);
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	/**
	 * 给手机新用户发送password
	 * @param password
	 * @param areaCode
	 * @param mobile
	 * @return
	 * @throws Exception
	 */
	public static String sendNewUserMsg(String password, String areaCode, String mobile) throws Exception {
		try {
			Properties prop = null;
			String content = null;
			mobile = areaCode + mobile;
			if(areaCode.equals("+86")||areaCode.equals("0086")){
				prop = PropertiesReader.read(SmsConfigUtil.getSmsConfigFile(Constant.COUNTRY_CODE_CN));
				content = prop.getProperty("anyonehelps.new.user.content");
				content = MessageFormat.format(content, new Object[] {password});
				SimpleSmsSender sender = SmsSenderFactory.generateSmsSender();
				return sender.sendSms(content, mobile);
			}else{
				prop = PropertiesReader.read(SmsConfigUtil.getSmsConfigFile(Constant.COUNTRY_CODE_US));
				content = prop.getProperty("anyonehelps.new.user.content");
				content = MessageFormat.format(content, new Object[] {password});
				return SmsTwilioSender.sendSms(content, mobile);
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	/**
	 * 给手机用户发任务邀请
	 * @param nick_name
	 * @param title
	 * @param url
	 * @param areaCode
	 * @param mobile
	 * @param amount
	 * @return
	 * @throws Exception
	 */
	public static String sendInviteMsg(String nick_name,String title,String url, String areaCode, String mobile, String amount) throws Exception {
		try {
			Properties prop = null;
			String content = null;
			mobile = areaCode + mobile;
			if(areaCode.equals("+86")||areaCode.equals("0086")){
				prop = PropertiesReader.read(SmsConfigUtil.getSmsConfigFile(Constant.COUNTRY_CODE_CN));
				content = prop.getProperty("anyonehelps.invite.task.content");
				content = MessageFormat.format(content, new Object[] {nick_name, title, url});
				SimpleSmsSender sender = SmsSenderFactory.generateSmsSender();
				return sender.sendSms(content, mobile);
			}else{
				prop = PropertiesReader.read(SmsConfigUtil.getSmsConfigFile(Constant.COUNTRY_CODE_US));
				content = prop.getProperty("anyonehelps.invite.task.content");
				content = MessageFormat.format(content, new Object[] {nick_name, title, url});
				return SmsTwilioSender.sendSms(content, mobile);
			}
			
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	
	public static String sendArbitrationMsg(String nick_name,String title,String id, String mobile) throws Exception {
		//这里还没做国际化
		try {
			String content = null;
			if(mobile.length()==10){
				mobile="+1"+mobile;
				String demandUrl = "www.anyonehelps.com/task/detail.jsp?id="+Base64Util.encode(id);
				content= nick_name +"在任务标题："+title+",的任务中向您发起了仲裁,请您5天内到AnyoneHelps后台处理,任务链接:"+demandUrl;
				return SmsTwilioSender.sendSms(content, mobile);
			}else if(mobile.length()==11){
				mobile="+86"+mobile;
				SimpleSmsSender sender = SmsSenderFactory.generateSmsSender();
				String demandUrl = "www.anyonehelps.com/task/detail.jsp?id="+Base64Util.encode(id);
				content= nick_name +"在任务标题："+title+",的任务中向您发起了仲裁,请您5天内到AnyoneHelps后台处理,任务链接:"+demandUrl;
				return sender.sendSms(content, mobile);
			}else {
				return null;
			}
			
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	/**
	 * 增加附加任务
	 * @param nick_name
	 * @param title
	 * @param url
	 * @param areaCode
	 * @param mobile
	 * @param amount
	 * @return
	 * @throws Exception
	 */
	public static String sendAddDemandAttachMsg(String nick_name,String title,String url, String areaCode, String mobile, String amount) throws Exception {
		try {
			Properties prop = null;
			String content = null;
			mobile = areaCode + mobile;
			if(areaCode.equals("+86")||areaCode.equals("0086")){
				prop = PropertiesReader.read(SmsConfigUtil.getSmsConfigFile(Constant.COUNTRY_CODE_CN));
				content = prop.getProperty("anyonehelps.task.attach.content");
				content = MessageFormat.format(content, new Object[] {nick_name, title, url});
				SimpleSmsSender sender = SmsSenderFactory.generateSmsSender();
				return sender.sendSms(content, mobile);
			}else{
				prop = PropertiesReader.read(SmsConfigUtil.getSmsConfigFile(Constant.COUNTRY_CODE_US));
				content = prop.getProperty("anyonehelps.task.attach.content");
				content = MessageFormat.format(content, new Object[] {nick_name, title, url});
				return SmsTwilioSender.sendSms(content, mobile);
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	/**
	 * 有人接单，给发接人发短信
	 * @param taskNum
	 * @param receiveNickName
	 * @param mobile
	 * @return
	 * @throws Exception
	 */
	public static String sendHasRceciveDemandMsg(String taskNum,String receiveNickName, String areaCode, String mobile) throws Exception {
		try {
			Properties prop = null;
			String content = null;
			mobile = areaCode + mobile;
			if(areaCode.equals("+86")||areaCode.equals("0086")){
				prop = PropertiesReader.read(SmsConfigUtil.getSmsConfigFile(Constant.COUNTRY_CODE_CN));
				content = prop.getProperty("anyonehelps.hsa.receive.demand");
				content = MessageFormat.format(content, new Object[] {taskNum},new Object[] {receiveNickName});
				SimpleSmsSender sender = SmsSenderFactory.generateSmsSender();
				return sender.sendSms(content, mobile);
			}else{
				prop = PropertiesReader.read(SmsConfigUtil.getSmsConfigFile(Constant.COUNTRY_CODE_US));
				content = prop.getProperty("anyonehelps.hsa.receive.demand");
				content = MessageFormat.format(content, new Object[] {taskNum},new Object[] {receiveNickName});
				return SmsTwilioSender.sendSms(content, mobile);
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	/**
	 * 发送任务开始短信
	 * @param taskNum
	 * @param areaCode
	 * @param mobile
	 * @return
	 * @throws Exception
	 */
	public static String sendStartDemandMsg(String taskNum, String areaCode, String mobile) throws Exception {
		try {
			Properties prop = null;
			String content = null;
			mobile = areaCode + mobile;
			if(areaCode.equals("+86")||areaCode.equals("0086")){
				prop = PropertiesReader.read(SmsConfigUtil.getSmsConfigFile(Constant.COUNTRY_CODE_CN));
				content = prop.getProperty("anyonehelps.demand.start");
				content = MessageFormat.format(content, new Object[] {taskNum});
				SimpleSmsSender sender = SmsSenderFactory.generateSmsSender();
				return sender.sendSms(content, mobile);
			}else{
				prop = PropertiesReader.read(SmsConfigUtil.getSmsConfigFile(Constant.COUNTRY_CODE_US));
				content = prop.getProperty("anyonehelps.demand.start");
				return SmsTwilioSender.sendSms(content, mobile);
			}
			
			
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
    public static void main(String[] args) throws IOException, URISyntaxException, ParseException {
    	/*DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
		Date signDate = df.parse("2016-09-20 00:08:14");  
        long signTime = signDate.getTime();  
     	long currTime = new Date().getTime();  
     	System.out.println("=====================");
     	System.out.println();
     	System.out.println("=====================");
     	*/
    }
}


