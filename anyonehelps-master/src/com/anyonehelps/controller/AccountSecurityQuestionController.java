package com.anyonehelps.controller;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jxl.common.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anyonehelps.common.constants.Constant;
import com.anyonehelps.common.constants.ParameterConstants;
import com.anyonehelps.common.constants.ResponseCode;
import com.anyonehelps.common.util.DateUtil;
import com.anyonehelps.common.util.PropertiesReader;
import com.anyonehelps.common.util.StringUtil;
import com.anyonehelps.common.util.UserUtil;
import com.anyonehelps.common.util.sms.SmsConfigUtil;
import com.anyonehelps.model.AccountSecurityQuestion;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.model.SmsSend;
import com.anyonehelps.model.User;
import com.anyonehelps.service.AccountSecurityQuestionService;
import com.anyonehelps.service.SmsSendService;
import com.anyonehelps.service.UserService;

@Controller
public class AccountSecurityQuestionController extends BasicController {
	
	private static final long serialVersionUID = -2836718794551189036L;
	private static final Logger log = Logger.getLogger(AccountSecurityQuestionController.class);
	@Resource(name = "asqService")
	private AccountSecurityQuestionService asqService;
	@Resource(name = "userService")
	private UserService userService;
	@Resource(name = "smsSendService")
	private SmsSendService smsSendService;
	
	/**
	 * 获取密保验证码
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/asq/get_phone_code", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ResponseObject<Object> asqPhoneCode(HttpServletRequest request) {
		try {
			String userId = StringUtil.obj2String(request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY));
			ResponseObject<User> returnObj = this.userService.getUserById(userId);
			if(ResponseCode.SUCCESS_CODE.equals(returnObj.getCode())){
				User user = returnObj.getData();
				if(user==null){
					return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "发送验证码失败,请重试!");
				}
				if(!Constant.USER_TELPHONE_STATE1.equals(user.getTelphoneState())){
					return generateResponseObject(ResponseCode.USER_PHONE_NEEL_VALIDATE, "你手机还未认证，请认证后再操作！");
				}
			    if (!UserUtil.validateAreaCode(user.getAreaCode())) {
			    	return generateResponseObject(ResponseCode.PARAMETER_ERROR,"您手机区域号不正确，请联系客服！");
			    }
			    if (!UserUtil.validatePhone(user.getTelphone())) {
			    	return generateResponseObject(ResponseCode.PARAMETER_ERROR,"您验证手机号码不正确，请联系客服！");
				}
			    StringBuilder code = new StringBuilder();  
			    Random random = new Random();
			     // 6位验证码 
			     for (int i = 0; i < 6; i++) {  
			    	 code.append(String.valueOf(random.nextInt(10)));  
			     }  
			    
			     try {
					//System.out.println(SmsSendUtil.sendRegisterMsg(code.toString(), user.getAreaCode(), user.getTelphone()));
					
					Properties prop = null;
					String content = null;
					if("+86".equals(user.getAreaCode())||"0086".equals(user.getAreaCode())){
						prop = PropertiesReader.read(SmsConfigUtil.getSmsConfigFile(Constant.COUNTRY_CODE_CN));
						content = prop.getProperty("anyonehelps.register.content");
						content = MessageFormat.format(content, new Object[] {code});
					}else {
						prop = PropertiesReader.read(SmsConfigUtil.getSmsConfigFile(Constant.COUNTRY_CODE_US));
						content = prop.getProperty("anyonehelps.register.content");
						content = MessageFormat.format(content, new Object[] {code});
					}
					String date = DateUtil.date2String(new Date());
					
					SmsSend ss = new SmsSend();
					ss.setContent(content);
					ss.setFailCount(Constant.EMAILSEND_FAIL_COUNT0);
					ss.setState(Constant.EMAILSEND_STATE0);
					ss.setAreaCode(user.getAreaCode());
					ss.setTelphone(user.getTelphone());
					ss.setUserId(userId);
					ss.setCreateDate(date);
					ss.setModifyDate(date);
					ResponseObject<Object> ssObj = smsSendService.addSmsSend(ss);
					if(ResponseCode.SUCCESS_CODE.equals(ssObj.getCode())){
						//pass
					}else{
						throw new Exception("发送验证码失败");
					}
					
					HttpSession session = request.getSession();  
					session.setAttribute(Constant.PHONE_AREA_CODE, user.getAreaCode());  
				    session.setAttribute(Constant.PHONE_KEY, user.getTelphone());  
				    session.setAttribute(Constant.PHONE_SEND_CODE, code.toString());  
				    session.setAttribute(Constant.PHONE_SEND_CODE_TIME, new Date().getTime());
				    return generateResponseObject(ResponseCode.SUCCESS_CODE);
				} catch (Exception e) {
					log.error("/code/phone发送验证码异常,areaCode:"+user.getAreaCode()+",phone:"+user.getTelphone(), e);
					return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "发送验证码失败,请重试!");
				}
			     
			     
			}else{
				return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "发送验证码失败,请重试!");
			}
		} catch (Exception e) {
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "发送验证码失败,请重试!");
		}
	}

	/**
	 * 提交密保
	 * @param request
	 * @param response
	 * @param asq
	 * @return
	 */
	@RequestMapping(value = "/asq/add", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ResponseObject<Object> asqAdd(HttpServletRequest request,
			@RequestParam(value = "index1") String index1,
			@RequestParam(value = "index2") String index2,
			@RequestParam(value = "index3") String index3,

			@RequestParam(value = "answer1") String answer1,
			@RequestParam(value = "answer2") String answer2,
			@RequestParam(value = "answer3") String answer3,
	        @RequestParam(value = ParameterConstants.COMMON_VALIDATE_CODE) String code) {
		
		if(StringUtil.isEmpty(index1)||StringUtil.isEmpty(index2)||StringUtil.isEmpty(index3)){
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数错误，请刷新页面再操作!");
		}
		if(StringUtil.isEmpty(answer1)||StringUtil.isEmpty(answer2)||StringUtil.isEmpty(answer3)){
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "问题回答不能为空!");
		}
		if(StringUtil.isEmpty(code)){
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "验证码输入错误!");
		}
		
		
		try {
			
				String userId = StringUtil.obj2String(request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY));
				AccountSecurityQuestion asq = new AccountSecurityQuestion();
				asq.setUserId(userId);
				asq.setAnswer1(answer1);
				asq.setAnswer2(answer2);
				asq.setAnswer3(answer3);
				asq.setQuestion1(index1);
				asq.setQuestion2(index2);
				asq.setQuestion3(index3);
				ResponseObject<User> returnObj = this.userService.getUserById(userId);
				if(ResponseCode.SUCCESS_CODE.equals(returnObj.getCode())){
					User user = returnObj.getData();
					if(user==null){
						return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "您没有登录,请重新登录!");
					}
					if(!Constant.USER_TELPHONE_STATE1.equals(user.getTelphoneState())){
						return new ResponseObject<Object>(ResponseCode.USER_PHONE_NEEL_VALIDATE, "你手机还未认证，请认证后再操作！");
					}
					
					if (!checkVerifyPhoneCode(request, user.getAreaCode(), user.getTelphone(),code)) {
						return new ResponseObject<Object>(ResponseCode.VALIDATE_CODE_ERROR, "验证码输入错误，请重新输入");
					}
					HttpSession session = request.getSession();  
					session.removeAttribute(Constant.PHONE_KEY);
					session.removeAttribute(Constant.PHONE_AREA_CODE);
					session.removeAttribute(Constant.PHONE_SEND_CODE);
					session.removeAttribute(Constant.PHONE_SEND_CODE_TIME);
					return this.asqService.addASQ(asq);
				}else{
					return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "提交密保出错,请重试!");
				}
				
		} catch (Exception e) {
			log.error("密保问题写入数据库出错!");
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "提交密保出错，请稍后再试!");
		}
	}

	/**
	 * 根据密保问题修改手机
	 * @param request
	 * @param answer1
	 * @param answer2
	 * @param answer3
	 * @param areaCode
	 * @param telphone
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/asq/phone_modify", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ResponseObject<Object> phoneModify(HttpServletRequest request,
			@RequestParam(value = "answer1") String answer1,
			@RequestParam(value = "answer2") String answer2,
			@RequestParam(value = "answer3") String answer3,
			@RequestParam(value = ParameterConstants.USER_AREA_CODE) String areaCode,
			@RequestParam(value = ParameterConstants.USER_PHONE) String telphone,
	        @RequestParam(value = ParameterConstants.COMMON_VALIDATE_CODE) String code) {
		
		ResponseObject<Object> responseObj = null;
		
		if(StringUtil.isEmpty(answer1)||StringUtil.isEmpty(answer2)||StringUtil.isEmpty(answer3)){
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "问题回答不能为空!");
		}else if (!UserUtil.validateAreaCode(areaCode)) {
			return generateResponseObject(ResponseCode.USER_PHONE_ERROR, "手机所属地区码输入错误，请重新输入!");
		}else if (!UserUtil.validatePhone(telphone)) {
			return generateResponseObject(ResponseCode.USER_PHONE_ERROR, "手机号码输入不正确，请重新输入!");
		}else if (!checkVerifyPhoneCode(request,areaCode, telphone,code)) {
			return generateResponseObject(ResponseCode.VALIDATE_CODE_ERROR, "验证码输入错误，请重新输入");
		}
		
		try {
			String userId = StringUtil.obj2String(request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY));
			if(this.userService.checkExistsByPhone(areaCode, telphone)){
				return generateResponseObject(ResponseCode.PARAMETER_ERROR, "该手机已经注册了，请换个手机!");
			}
			responseObj = this.asqService.checkSecurityQuestion(userId,answer1,answer2,answer3);
			HttpSession session = request.getSession();  
			session.removeAttribute(Constant.PHONE_KEY);
			session.removeAttribute(Constant.PHONE_AREA_CODE);
			session.removeAttribute(Constant.PHONE_SEND_CODE);
			session.removeAttribute(Constant.PHONE_SEND_CODE_TIME);
			if(ResponseCode.SUCCESS_CODE.equals(responseObj.getCode())){
				return this.userService.modifyPhone(userId, areaCode, telphone);
			}else{
				return generateResponseObject(ResponseCode.PARAMETER_ERROR, "密保问题不正确!");
			}
		} catch (Exception e) {
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "提交密保出错，请稍后再试!");
		}
	}

	/**
	 * 根据密保问题修改邮箱
	 * @param request
	 * @param answer1
	 * @param answer2
	 * @param answer3
	 * @param email
	 * @return
	 */
	@RequestMapping(value = "/asq/email_modify", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ResponseObject<Object> emailModify(HttpServletRequest request,
			@RequestParam(value = "answer1") String answer1,
			@RequestParam(value = "answer2") String answer2,
			@RequestParam(value = "answer3") String answer3,
			@RequestParam(value = ParameterConstants.USER_EMAIL) String email) {
		
		ResponseObject<Object> responseObj = null;
		
		if(StringUtil.isEmpty(answer1)||StringUtil.isEmpty(answer2)||StringUtil.isEmpty(answer3)){
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "问题回答不能为空!");
		}else if (!UserUtil.validateEmail(email)) {
			return generateResponseObject(ResponseCode.USER_PHONE_ERROR, "邮箱输入错误，请重新输入!");
		}  
		try {
			String userId = StringUtil.obj2String(request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY));
			if(this.userService.checkExistsByEmail(email)){
				return generateResponseObject(ResponseCode.PARAMETER_ERROR, "该邮箱已经注册了，请换个邮箱!");
			}
			responseObj = this.asqService.checkSecurityQuestion(userId, answer1, answer2, answer3);
			if(ResponseCode.SUCCESS_CODE.equals(responseObj.getCode())){
				return this.userService.modifyEmail(userId, email);
			}else{
				return generateResponseObject(ResponseCode.PARAMETER_ERROR, "密保问题不正确!");
			}
		} catch (Exception e) {
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "提交密保出错，请稍后再试!");
		}
	}
	
	/**
	 * 获取用户保密问题索引
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/asq/get_asq", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ResponseObject<Object> getAsq(HttpServletRequest request) {
		try {
			String userId = StringUtil.obj2String(request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY));
			return this.asqService.getAsqByUserId(userId);
			
		} catch (Exception e) {
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取密保问题失败，请重试!");
		}
	}
	
	/**
	 * 根据邮箱或者电话查找密保问题索引
	 * @param request
	 * @param email
	 * @param phone
	 * @param areaCode
	 * @return
	 */
	@RequestMapping(value = "/asq/get_asq2", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ResponseObject<Object> getAsqByPhoneOrEmail(HttpServletRequest request,
			@RequestParam(value = ParameterConstants.USER_EMAIL, required = false, defaultValue = "") String email,
	        @RequestParam(value = ParameterConstants.USER_PHONE, required = false, defaultValue = "") String phone,
	        @RequestParam(value = ParameterConstants.USER_AREA_CODE, required = false, defaultValue = "") String areaCode
	      ) {
		
		try {
			return this.asqService.getAsqByEmailOrPhone(email, areaCode, phone);
			
		} catch (Exception e) {
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取密保问题失败，请重试!");
		}
	}
	
	@RequestMapping(value = "/asq/validate", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> asqValidate(
	        HttpServletRequest request,
	        @RequestParam(value = "userId") String userId,
	        @RequestParam(value = "answer1") String answer1,
	        @RequestParam(value = "answer2") String answer2,
	        @RequestParam(value = "answer3") String answer3) {
		
		ResponseObject<Object> responseObj = null;
		
		if (StringUtil.isEmpty(answer1)||StringUtil.isEmpty(answer2)||StringUtil.isEmpty(answer3)) {
			responseObj = generateResponseObject(ResponseCode.PARAMETER_ERROR, "问题回答不能为空！");
		} else {
			try {
				responseObj = this.asqService.checkSecurityQuestion(userId, answer1, answer2, answer3);
				if(ResponseCode.SUCCESS_CODE.equals(responseObj.getCode())){
					//HttpSession session = request.getSession();
					request.getSession().setAttribute(Constant.SECURITY_QUESTION_RESETPW_USERID, userId);  
				}
				
			} catch (Exception e) {
				responseObj = generateResponseObject(ResponseCode.SHOW_EXCEPTION, "重置密码失败,请重试!");
			}
		}
		return responseObj;
	}

}
