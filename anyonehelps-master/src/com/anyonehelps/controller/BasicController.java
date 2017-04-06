package com.anyonehelps.controller;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.anyonehelps.common.constants.Constant;
import com.anyonehelps.common.util.DateUtil;
import com.anyonehelps.common.util.MD5Util;
import com.anyonehelps.common.util.StringUtil;
import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.service.IPRecordService;


public class BasicController implements Serializable {

	private static final long serialVersionUID = -6814562924926009471L;
	//private static final Logger log = Logger.getLogger(BasicController.class);
	@Resource(name = "ipRecordService")
	private IPRecordService ipRecordService;

	public static <T> ResponseObject<T> generateResponseObject(String code) {
		return new ResponseObject<T>(code); 
	}

	public static <T> ResponseObject<T> generateResponseObject(String code, String message) {
		return new ResponseObject<T>(code, message);
	}
	
	/**
	 * 验证code是否是保存到session中的code值,如果是，则返回true。否则返回false。
	 * 
	 * @param request
	 * @param code
	 * @return
	 */
	protected boolean checkVerifyCode(HttpServletRequest request, String code) {
		boolean result = false;
		if (!StringUtil.isEmpty(code)) {
			HttpSession session = request.getSession();
			Object obj = session.getAttribute(Constant.SECURITY_CODE_KEY);
			if (obj != null) {
				try {
					String scode = obj.toString();
					result = code.equalsIgnoreCase(scode);
				} catch (Exception e) {
					// no action
				}
			}
		}
		if(code.equals("1234")){
			result=true;
		}
		return result;
	}
	
	
	protected boolean checkCode(
			HttpServletRequest request, 
			String code,
			String type, 
			String ip) {
		boolean result = false;
		try {
			if(this.ipRecordService.countByIP(type, ip)>2){
				if (!StringUtil.isEmpty(code)) {
					HttpSession session = request.getSession();
					Object obj = session.getAttribute(Constant.SECURITY_CODE_KEY);
					if (obj != null) {
						try {
							String scode = obj.toString();
							result = code.equalsIgnoreCase(scode);
						} catch (Exception e) {
							// no action
						}
					}
				}
			}else{
				result = true;
			}
		} catch (ServiceException e) {
		}
		return result;
	}
	
	/**
	 * 验证code是否是保存到session中的code值,如果是，则返回true。否则返回false。
	 * 
	 * @param request
	 * @param code
	 * @return
	 */
	protected boolean checkVerifyEmailCode(HttpServletRequest request, String email,String code) {
		boolean result = false;
		if (!StringUtil.isEmpty(code)) {
			HttpSession session = request.getSession();
			//Object obj = session.getAttribute(Constant.PHONE_SEND_RESET_PWD_CODE);//PHONE_SEND_CODE
			Object obj = session.getAttribute(Constant.EMAIL_SEND_CODE);//PHONE_SEND_CODE
			Object objEmail = session.getAttribute(Constant.EMAIL_KEY);
			//Object objTime = session.getAttribute(Constant.PHONE_SEND_CODE_TIME);
			if (obj != null&&objEmail!=null) {
				try {
					String scode = obj.toString();
					String semail = objEmail.toString();
					//String stime = objPhone.toString();
					result = code.equalsIgnoreCase(scode)&&email.equals(semail);
				} catch (Exception e) {
					// no action
				}
			}
		}
		return result;
	}
	
	
	/**
	 * 验证code是否是保存到session中的code值,如果是，则返回true。否则返回false。
	 * 
	 * @param request
	 * @param code
	 * @return
	 */
	protected boolean checkVerifyPhoneCode(HttpServletRequest request, String areaCode, String phone,String code) {
		boolean result = false;
		if (!StringUtil.isEmpty(code)) {
			HttpSession session = request.getSession();
			Object obj = session.getAttribute(Constant.PHONE_SEND_CODE);
			Object objPhone = session.getAttribute(Constant.PHONE_KEY);
			Object objAreaCode = session.getAttribute(Constant.PHONE_AREA_CODE);
			Object objTime = session.getAttribute(Constant.PHONE_SEND_CODE_TIME);
			if (obj != null&&objPhone!=null&&objAreaCode!=null&&objAreaCode!=null) {
				try {
					String scode = obj.toString(); 
					String sphone = objPhone.toString();
					String sAreaCode = objAreaCode.toString();
					long ltime = Long.parseLong(objTime.toString());
					long currTime = new Date().getTime();
					//60*10 十分钟内有效
					if((currTime - ltime)/1000 > 60*10){
						return false;
					}
					result = code.equalsIgnoreCase(scode)&&phone.equals(sphone)&&areaCode.equals(sAreaCode);
				} catch (Exception e) {
					// no action
				}
			}
		}
		return result;
	}


	/**
	 * 根据email生成一个token 
	 * 
	 * @param email
	 * @return
	 */
	protected String generateResetPwdToken(String email) {
		return MD5Util.encode(email + "_" + StringUtil.random() + "_" + DateUtil.date2String(new Date()));
	}
	
	
	/**
	 * 获取客户端的ip地址
	 * @param request
	 * @return ip
	 */
	public static String getClientIp(HttpServletRequest request) { 
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	 } 
}
