package com.anyonehelps.common.util.sms;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Properties;

import com.anyonehelps.common.constants.Constant;
import com.anyonehelps.common.util.Base64Util;
import com.anyonehelps.common.util.DateUtil;
import com.anyonehelps.common.util.PropertiesReader;
import com.anyonehelps.common.util.StringUtil;
import com.anyonehelps.model.Ticket;


public class MailSendUtil {
	/**
	 * 邮箱邀请用户注册
	 * chenkanghua 20151130
	 * nick_name 发送者用户名
	 * recipient 被邀请人邮箱
	 * */
	public static boolean sendInviteUserMsg(String userId,String nick_name,String recipient) throws Exception {
		try {
			Properties prop = PropertiesReader.read(Constant.EMAIL_PROPERTIES_FILE);
			String subject = prop.getProperty("anyonehelps.invite.user.subject");
			subject = MessageFormat.format(subject, new Object[] { nick_name });
			String content = prop.getProperty("anyonehelps.invite.user.content");
			content = MessageFormat.format(content, new Object[] { nick_name,userId });
			SimpleMailSender sender = MailSenderFactory.generateMailSender();
			return sender.send(recipient, subject, content);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	/**
	 * 发送修改密码的邮件
	 * @param baseUrl
	 * @param recipient
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public static boolean sendResetPwdMsg(String baseUrl, String recipient, String token) throws Exception {
		try {
			Properties prop = PropertiesReader.read(Constant.EMAIL_PROPERTIES_FILE);
			String subject = prop.getProperty("anyonehelps.reset.pwd.subject");
			String url = baseUrl + prop.getProperty("anyonehelps.reset.pwd.page.name", "/resetPwdByEmail.jsp") + "?email="
			        + recipient + "&token=" + token;
			String content = prop.getProperty("anyonehelps.reset.pwd.content");
			content = MessageFormat.format(content, new Object[] { Constant.RESET_PWD_TOKEN_TIME_OF_HOUR, url, url });
			SimpleMailSender sender = MailSenderFactory.generateMailSender();
			return sender.send(recipient, subject, content);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	/**
	 * 邮箱发送验证码2
	 * chenkanghua 20160427
	 * baseUrl 公网链接
	 * recipient 接收邮箱
	 * token 生成的token
	 * */
	public static boolean sendVerifyMsg2(String baseUrl, String recipient, String token) throws Exception {
		try {
			Properties prop = PropertiesReader.read(Constant.EMAIL_PROPERTIES_FILE);
			String subject = prop.getProperty("anyonehelps.email.verify.subject");
			String url = baseUrl + "/emailVerify.jsp?email="
			        + recipient + "&token=" + token;
			String content = prop.getProperty("anyonehelps.email.verify.content");
			content = MessageFormat.format(content, new Object[] { url, url });
			SimpleMailSender sender = MailSenderFactory.generateMailSender();
			return sender.send(recipient, subject, content);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	/**
	 * 邮箱邀请任务
	 * chenkanghua 20151130
	 * nick_name 发送者用户名
	 * title 任务标题
	 * url 任务链接
	 * recipient 接收邮箱
	 * amount 金额
	 * 
	 * */
	public static boolean sendInviteMsg(String nickName,String inviteNickName,String title,String url, String recipient, String amount, String expireDate) throws Exception {
		try {
			Properties prop = PropertiesReader.read(Constant.EMAIL_PROPERTIES_FILE);
			String subject = prop.getProperty("anyonehelps.invite.task.subject");
			subject = MessageFormat.format(subject, new Object[] { inviteNickName , nickName });
			String content = prop.getProperty("anyonehelps.invite.task.content");
			content = MessageFormat.format(content, new Object[] { inviteNickName, nickName, title, amount, expireDate,url});
			SimpleMailSender sender = MailSenderFactory.generateMailSender();
			return sender.send(recipient, subject, content);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	/**
	 * 管理员邀请任务
	 * chenkanghua 20161004
	 * nickName 接收者用户名
	 * title 任务标题
	 * url 任务链接
	 * expireDate 任务到期时间
	 * recipient 接收邮箱
	 * amount 金额
	 * 
	 * */
	public static boolean adminSendInviteMsg(String nickName,String title, String amount, String expireDate, String url, String recipient ) throws Exception {
		try {
			Properties prop = PropertiesReader.read(Constant.EMAIL_PROPERTIES_FILE);
			String subject = prop.getProperty("anyonehelps.admin.invite.task.subject");
			subject = MessageFormat.format(subject, new Object[] { nickName});
			String content = prop.getProperty("anyonehelps.admin.invite.task.content");
			content = MessageFormat.format(content, new Object[] { nickName, title, amount, expireDate, url});
			SimpleMailSender sender = MailSenderFactory.generateMailSender();
			return sender.send(recipient, subject, content);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	
	/**
	 * 发送发布任务人发起仲裁消息
	 * chenkanghua 20151130
	 * nick_name 发送者用户名
	 * id 任务id
	 * url 任务链接
	 * recipient 接收邮箱
	 * reason 仲裁理由
	 * percentage 付款比例
	 * */
	public static boolean sendArbitrationMsg(String nick_name,String title,String id, String recipient,String reason,String percentage) throws Exception {
		try {
			
			String str = "";
			String demandUrl = "www.anyonehelps.com/task/detail.jsp?id="+Base64Util.encode(id);
			str += nick_name +"在任务标题："+title+",的任务中向您发起了仲裁,请您及时到AnyoneHelps(互联帮)后台处理";
			str +="任务链接:<a href='"+demandUrl+"'>"+demandUrl+"</a><br/>";
			str +="仲裁理由:"+reason+"<br/>";
			str +="仲裁付款比例:"+percentage+"<br/>";
			SimpleMailSender sender = MailSenderFactory.generateMailSender();
			return sender.send(recipient, nick_name +"在任务标题："+title+",的任务中向您发起了仲裁,请您5天内到AnyoneHelps(互联帮)后台处理", str);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	/**
	 * 附加任务
	 * chenkanghua 2016-03-16
	 * nick_name 发送者用户名
	 * title 任务标题
	 * url 任务链接
	 * recipient 接收邮箱
	 * amount 金额
	 * 
	 * */
	public static boolean sendAddDemandAttachMsg(String nick_name,String title,String url, String recipient, String amount) throws Exception {
		try {
			Properties prop = PropertiesReader.read(Constant.EMAIL_PROPERTIES_FILE);
			String subject = prop.getProperty("anyonehelps.attach.task.subject");
			subject = MessageFormat.format(subject, new Object[] { nick_name, title});
			String content = prop.getProperty("anyonehelps.attach.task.content");
			content = MessageFormat.format(content, new Object[] { amount, url, url});
			SimpleMailSender sender = MailSenderFactory.generateMailSender();
			return sender.send(recipient, subject, content);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	/**
	 * 申请取现通知邮件
	 * @param recipient
	 * @param type
	 * @param amount
	 * @return
	 * @throws Exception
	 */
	public static boolean sendRequestWithdrawalsMsg(String recipient, String type, String amount) throws Exception {
		try {
			String date = DateUtil.date2String(new Date());
			Properties prop = null;
			prop = PropertiesReader.read(Constant.EMAIL_PROPERTIES_FILE);
			String subject = prop.getProperty("anyonehelps.request.withdrawals.subject");
			
			String content = prop.getProperty("anyonehelps.request.withdrawals.content"); 
			if("1".equals(type)){
				content = MessageFormat.format(content, new Object[] {amount, "银行卡", date});
			}else if("2".equals(type)){
				content = MessageFormat.format(content, new Object[] {amount, "paypal", date});
			}else{
				content = MessageFormat.format(content, new Object[] {amount, "", date});
			}
			SimpleMailSender sender = MailSenderFactory.generateMailSender();
			return sender.send(recipient, subject, content);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	/**
	 * 用户发起tick，邮箱通知
	 * @param ticket
	 * @return
	 * @throws Exception
	 */
	public static boolean sendTicketMsg(Ticket ticket) throws Exception {
		try {
			
			Properties prop = null;
			prop = PropertiesReader.read(Constant.EMAIL_PROPERTIES_FILE);
			String recipient = prop.getProperty("anyonehelps.ticket.email");
			String subject = prop.getProperty("anyonehelps.ticket.subject");
			String phone = "";
			if(!StringUtil.isEmpty(ticket.getTelphone())){
				phone = ",phone:"+ticket.getAreaCode()+ticket.getTelphone();
			}
			
			subject = MessageFormat.format(subject, new Object[] {ticket.getName(), ticket.getEmail(), phone, ticket.getId(), ticket.getSubject()});
			String content = prop.getProperty("anyonehelps.ticket.content");
			content = MessageFormat.format(content, new Object[] {ticket.getDescription()});
			SimpleMailSender sender = MailSenderFactory.generateMailSender();
			return sender.send(recipient, subject, content);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
}
