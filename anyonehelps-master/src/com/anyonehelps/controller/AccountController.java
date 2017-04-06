package com.anyonehelps.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.common.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anyonehelps.common.constants.Constant;
import com.anyonehelps.common.constants.ParameterConstants;
import com.anyonehelps.common.constants.ResponseCode;
import com.anyonehelps.common.util.AccountUtil;
import com.anyonehelps.common.util.DateUtil;
import com.anyonehelps.common.util.PropertiesReader;
import com.anyonehelps.common.util.StringUtil;
import com.anyonehelps.common.util.UserUtil;
import com.anyonehelps.common.util.sms.SmsConfigUtil;
import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.AccountDetail;
import com.anyonehelps.model.PageSplit;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.model.SmsSend;
import com.anyonehelps.model.User;
import com.anyonehelps.model.Withdrawals;
import com.anyonehelps.service.AccountService;
import com.anyonehelps.service.SmsSendService;
import com.anyonehelps.service.UserService;
import com.anyonehelps.service.WeixinService;

@Controller
public class AccountController extends BasicController {
	
	private static final long serialVersionUID = -2836718794551189036L;
	private static final Logger log = Logger.getLogger(AccountController.class);
	@Resource(name = "accountService")
	private AccountService accountService;
	@Resource(name = "smsSendService")
	private SmsSendService smsSendService;
	@Value(value = "${page_size}")
	private int defaultPageSize;
	
	@Resource(name = "weixinService")
	private WeixinService weixinService;
	
	@Resource(name = "userService")
	private UserService userService;
	
	@Value(value = "${min_withdrawal_amount}")
	private double minWithdrawalAmount;
	@Value(value = "${max_withdrawal_amount}")
	private double maxWithdrawalAmount;

	@RequestMapping(value = "/account/detail/search", method = RequestMethod.GET)
	@ResponseBody
	public ResponseObject<PageSplit<AccountDetail>> searchUserOfAccountDetail(
	        HttpServletRequest request,
	        @RequestParam(value = ParameterConstants.COMMON_SEARCH_KEY, required = false, defaultValue = "") String key,
	        @RequestParam(value = ParameterConstants.COMMON_SEARCH_SDATE, required = false, defaultValue = "") String sdate,
	        @RequestParam(value = ParameterConstants.COMMON_SEARCH_EDATE, required = false, defaultValue = "") String edate,
	        @RequestParam(value = ParameterConstants.ACCOUNTDETAIL_TYPE,required = false) String[] types,
	        @RequestParam(value = ParameterConstants.ACCOUNTDETAIL_STATE, required = false, defaultValue = "") String state,
	        @RequestParam(value = ParameterConstants.DEMAND_ID, required = false, defaultValue = "") String demandId,
	        @RequestParam(value = ParameterConstants.COMMON_PAGESPLIT_PAGE_INDEX, required = false, defaultValue = "1") int pageIndex) {
		try {
			
			if (StringUtil.isEmpty(sdate)
					|| !StringUtil.validateExportDate(sdate)) {
				sdate = "";
			} else {
				sdate = StringUtil.transformerDateString(sdate, " 00:00:00");
			}

			if (StringUtil.isEmpty(edate)
					|| !StringUtil.validateExportDate(edate)) {
				edate = "";
			} else {
				edate = StringUtil.transformerDateString(edate, " 23:59:59");
			}
			
			key =  new String(key.getBytes("ISO-8859-1"), "utf-8");
			String userId = StringUtil.obj2String(request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY));
			pageIndex = Math.max(pageIndex, 1);
			Set<String> typeSet = new HashSet<String>();
			if(types!=null){
				for (String type : types) {
					typeSet.add(type);
				}
			}
			List<String> typeList = null;
			if(typeSet.size()>0){
				typeList = new ArrayList<String>(typeSet);  
			}
			return this.accountService.search(userId, key, state, typeList, demandId, sdate, edate, defaultPageSize, pageIndex);
		} catch (Exception e) {
			log.error("获得财务记录失败", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取财务记录列表失败");
		}
	}
	
	
	@RequestMapping(value = "/stripe/recharge", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ResponseObject<Object> rechargeByStripe(
			HttpServletRequest request, 
			@RequestParam(value = "brand") String brand,
			@RequestParam(value = "name") String name,
			@RequestParam(value = "creditNo") String creditNo,
			@RequestParam(value = "securityCode") String securityCode,
			@RequestParam(value = "expireYear") String expireYear,
			@RequestParam(value = "expireMonth") String expireMonth,
			@RequestParam(value = "currency") String currency,
			@RequestParam(value = "amount") String amount
			) {
			if (!AccountUtil.validateCurrency(currency)) {
				return generateResponseObject(ResponseCode.PARAMETER_ERROR, "充值币种不正确，请重新币种！");
			}
			if (!AccountUtil.validateCreditName(name)) {
				return generateResponseObject(ResponseCode.PARAMETER_ERROR, "信用卡持卡人姓名输入错误，请重新输入！");
			}
			if (!AccountUtil.validateCreditNo(creditNo)) {
				return generateResponseObject(ResponseCode.PARAMETER_ERROR, "信用卡号码输入错误，请重新输入！");
			}
			if (!AccountUtil.validateCreditSecurityCode(securityCode)) {
				return generateResponseObject(ResponseCode.PARAMETER_ERROR, "信用卡安全码输入错误，请重新输入！");
			}
			if (!AccountUtil.validateCreditExpireYear(expireYear)) {
				return generateResponseObject(ResponseCode.PARAMETER_ERROR, "信用卡到期年信息输入错误，请重新输入！");
			}
			if (!AccountUtil.validateCreditExpireMonth(expireMonth)) {
				return generateResponseObject(ResponseCode.PARAMETER_ERROR, "信用卡到期月信息输入错误，请重新输入！");
			}
			if (!AccountUtil.validateAmount(amount)) {
				return generateResponseObject(ResponseCode.PARAMETER_ERROR, "充值金额输入错误，请重新输入！");
			}
			String userId = StringUtil.obj2String(request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY));
			try {
				return this.accountService.rechargeByStripe(userId,brand,name,creditNo,securityCode,expireYear,expireMonth,amount,currency);
			} catch (ServiceException e) {
				return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "充值失败，请重试");
			}
		
	}
	
	@RequestMapping(value = "/anet/recharge", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ResponseObject<Object> rechargeByAnet(
			HttpServletRequest request, 
			@RequestParam(value = "creditNo") String creditNo,
			@RequestParam(value = "securityCode") String securityCode,
			@RequestParam(value = "expireYear") String expireYear,
			@RequestParam(value = "expireMonth") String expireMonth,
			@RequestParam(value = "amount") String amount
			) {

			if (!AccountUtil.validateCreditNo(creditNo)) {
				return generateResponseObject(ResponseCode.PARAMETER_ERROR, "信用卡号码输入错误，请重新输入！");
			}

			if (!AccountUtil.validateCreditSecurityCode(securityCode)) {
				return generateResponseObject(ResponseCode.PARAMETER_ERROR, "信用卡安全码输入错误，请重新输入！");
			}

			if (!AccountUtil.validateCreditExpireYear(expireYear)) {
				return generateResponseObject(ResponseCode.PARAMETER_ERROR, "信用卡到期年信息输入错误，请重新输入！");
			}

			if (!AccountUtil.validateCreditExpireMonth(expireMonth)) {
				return generateResponseObject(ResponseCode.PARAMETER_ERROR, "信用卡到期月信息输入错误，请重新输入！");
			}

			if (!AccountUtil.validateAmount(amount)) {
				return generateResponseObject(ResponseCode.PARAMETER_ERROR, "充值金额输入错误，请重新输入！");
			}
			
			String userId = StringUtil.obj2String(request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY));
			try {
				return this.accountService.rechargeByAnet(userId, creditNo, securityCode, expireYear,
						expireMonth, amount);
			} catch (ServiceException e) {
				return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "充值失败，请重试");
			}
			
	}
	
	/**
	 * 获取取现验证码
	 * @param request
	 * @param response
	 * @param withdrawals
	 * @return
	 */
	@RequestMapping(value = "/account/withdrawals/get_phone_code", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ResponseObject<Object> withdrawalsPhoneCode(HttpServletRequest request) {
		try {
			String userId = StringUtil.obj2String(request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY));
			ResponseObject<User> returnObj = this.userService.getUserById(userId);
			if(ResponseCode.SUCCESS_CODE.equals(returnObj.getCode())){
				User user = returnObj.getData();
				if(user==null){
					return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "发送验证码失败,请重试!");
				}
				if(!Constant.USER_TELPHONE_STATE1.equals(user.getTelphoneState())){
					return generateResponseObject(ResponseCode.USER_PHONE_NEEL_VALIDATE, "为了保障您的资金安全，取现需要手机验证，请认证后再操作！");
				}
				if(!Constant.USER_EMAIL_STATE1.equals(user.getEmailState())){
					return generateResponseObject(ResponseCode.USER_EMAIL_NEEL_VALIDATE, "为了保障您的资金安全，取现需要邮箱验证，请认证后再操作！");
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
					// smsSendDao   System.out.println(SmsSendUtil.sendRegisterMsg(code.toString(), user.getAreaCode(), user.getTelphone()));
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
	 * 申请取现
	 * @param request
	 * @param response
	 * @param withdrawals
	 * @return
	 */
	@RequestMapping(value = "/account/withdrawals/add", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ResponseObject<Object> withdrawalsAdd(HttpServletRequest request,
			@RequestParam(value = ParameterConstants.WITHDRAWALS_TYPE) String type,
			@RequestParam(value = ParameterConstants.WITHDRAWALS_NAME) String name,
	        @RequestParam(value = ParameterConstants.WITHDRAWALS_ACCOUNT) String account,
			@RequestParam(value = ParameterConstants.WITHDRAWALS_ACCOUNT_TYPE) String accountType,
			@RequestParam(value = ParameterConstants.WITHDRAWALS_BNK_TYPE) String bnkType,
			@RequestParam(value = ParameterConstants.WITHDRAWALS_ROUTING_NUMBER) String routingNumber,
	        @RequestParam(value = ParameterConstants.WITHDRAWALS_AMOUNT) String amount,
	        @RequestParam(value = ParameterConstants.COMMON_VALIDATE_CODE) String code) {
		
		if(StringUtil.isEmpty(account)){
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "收款帐号输入错误!");
		}
		if(StringUtil.isEmpty(name)){
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "收款人姓名!");
		}
		if(StringUtil.isEmpty(type)){
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "取现输入错误!");
		}
		if(Constant.WITHDRAWALS_TYPE_BNK.equals(type)){
			if(!Constant.WITHDRAWALS_ACCOUNT_TYPE0.equals(accountType)&&
					!Constant.WITHDRAWALS_ACCOUNT_TYPE1.equals(accountType)){
				return generateResponseObject(ResponseCode.PARAMETER_ERROR, "请选择收款人类别!");
			}
			if(!Constant.WITHDRAWALS_BNK_TYPE0.equals(bnkType)&&
					!Constant.WITHDRAWALS_BNK_TYPE1.equals(bnkType)){
				return generateResponseObject(ResponseCode.PARAMETER_ERROR, "请选择收款人账号类型!");
			}
			if(StringUtil.isEmpty(routingNumber)){
				return generateResponseObject(ResponseCode.PARAMETER_ERROR, "汇款路线号码输入错误!");
			}
		}else if(Constant.WITHDRAWALS_TYPE_PAYPAL.equals(type)){
			accountType = null;
			bnkType = null;
			routingNumber = null;
		}else {
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "取现类型错误!");
		}
		if(StringUtil.isEmpty(code)){
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "验证码输入错误!");
		}
		
		try {
			double da = Double.valueOf(amount != null ? amount.trim() : "");
			if (da >= minWithdrawalAmount&&da <= maxWithdrawalAmount) {
				String userId = StringUtil.obj2String(request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY));
				Withdrawals withdrawals = new Withdrawals();
				withdrawals.setUserId(userId);
				withdrawals.setRoutingNumber(routingNumber);
				withdrawals.setAccount(account);
				withdrawals.setAccountType(accountType);
				withdrawals.setBnkType(bnkType);
				withdrawals.setAmount(amount);
				withdrawals.setType(type);
				withdrawals.setName(name);
				withdrawals.setState(Constant.WITHDRAWALS_STATE0);
				ResponseObject<User> returnObj = this.userService.getUserById(userId);
				if(ResponseCode.SUCCESS_CODE.equals(returnObj.getCode())){
					User user = returnObj.getData();
					if(user==null){
						return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "您没有登录,请重新登录!");
					}
					if(!Constant.USER_TELPHONE_STATE1.equals(user.getTelphoneState())){
						return new ResponseObject<Object>(ResponseCode.USER_PHONE_NEEL_VALIDATE, "为了保障您的资金安全，取现需要手机验证，请认证后再操作！");
					}
					if(!Constant.USER_EMAIL_STATE1.equals(user.getEmailState())){
						return new ResponseObject<Object>(ResponseCode.USER_EMAIL_NEEL_VALIDATE, "为了保障您的资金安全，取现需要邮箱验证，请认证后再操作！");
					}
					if (!checkVerifyPhoneCode(request, user.getAreaCode(), user.getTelphone(),code)) {
						return new ResponseObject<Object>(ResponseCode.VALIDATE_CODE_ERROR, "验证码输入错误，请重新输入");
					}
					HttpSession session = request.getSession();  
					session.removeAttribute(Constant.PHONE_KEY);
					session.removeAttribute(Constant.PHONE_AREA_CODE);
					session.removeAttribute(Constant.PHONE_SEND_CODE);
					session.removeAttribute(Constant.PHONE_SEND_CODE_TIME);
					return this.accountService.addWithdrawals(withdrawals,user);
				}else{
					return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "申请取现失败,请重试!");
				}
				
			} else {
				// "充值金额必须大于0";
				log.error("取现金额超出范围!");
				return generateResponseObject(ResponseCode.PARAMETER_ERROR, "取现金额超出范围!");
			}
		} catch (Exception e) {
			log.error("申请取现写入数据库出错!");
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "系统繁忙，请稍后再试!");
		}
	}
	
	@RequestMapping(value = "/account/withdrawals/search", method = RequestMethod.GET)
	@ResponseBody
	public ResponseObject<PageSplit<Withdrawals>> searchWithdrawals(
	        HttpServletRequest request,
	        @RequestParam(value = ParameterConstants.WITHDRAWALS_TYPE, required = false, defaultValue = "") String type,
	        @RequestParam(value = ParameterConstants.WITHDRAWALS_STATE, required = false, defaultValue = "") String state,
	        @RequestParam(value = ParameterConstants.COMMON_PAGESPLIT_PAGE_INDEX, required = false, defaultValue = "1") int pageIndex,
	        @RequestParam(value = ParameterConstants.COMMON_PAGESPLIT_PAGE_SIZE, required = false, defaultValue = "10") int pageSize) {
		try {
			String userId = StringUtil.obj2String(request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY));
			pageIndex = Math.max(pageIndex, 1);
			return this.accountService.search(userId, type,state, pageSize, pageIndex);
		} catch (Exception e) {
			log.error("获取取现记录失败", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取取现记录失败");
		}
	}
	
	/**
	 * paypal 返回过来的付款信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/account/paypal_notify_url", method = { RequestMethod.GET, RequestMethod.POST })
	public void paypalNotifyUrl(HttpServletRequest request, HttpServletResponse response) {
		try {
			ResponseObject<Object> responseObj = this.accountService.PorcPaypalNotify(request);
			if(responseObj.getCode().equals(ResponseCode.SUCCESS_CODE)){
				PrintWriter out = response.getWriter();
				response.setContentType("text/html;charset=utf-8");
				out.println("success");
			}
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			log.error("Paypal 处理异常!");
			//return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "系统繁忙，请稍后再试!");
		}
		

	}
	
	/**
	 * 模拟paypal测试数据 
	 */
	/*@RequestMapping(value = "/account/paypal_notify_test", method = { RequestMethod.GET, RequestMethod.POST })
	public void paypalNotifyUrlTest(HttpServletRequest request, HttpServletResponse response) {
		try {
			ResponseObject<Object> responseObj = this.accountService.PorcPaypalNotifyTest(request);
			if(responseObj.getCode().equals(ResponseCode.SUCCESS_CODE)){
				PrintWriter out = response.getWriter();
				response.setContentType("text/html;charset=utf-8");
				out.println("success");
			}
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			log.error("Paypal 处理异常!");
		}
	}*/
	
	/*暂时alipay start*/
	/*
	@RequestMapping(value = "/alipay/recharge", method = { RequestMethod.POST, RequestMethod.GET })
	public void rechargeByALiPay(HttpServletRequest request, HttpServletResponse response,
	        @RequestParam(value = "amount") String amount) {
		
		String outStr = "";
		try {
			double da = Double.valueOf(amount != null ? amount.trim() : "");
			if (da > 0) {
				
				String userId = StringUtil.obj2String(request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY));
				String userName = StringUtil.obj2String(request.getSession().getAttribute(
				        Constant.USER_NICK_NAME_SESSION_KEY));
				AccountDetail detail = new AccountDetail();
				detail.setAmount(String.valueOf(da));
				detail.setRealAmount(detail.getAmount());
				detail.setUserId(userId);
				detail.setCurrency("人民币");
				detail.setName("支付宝充值");
				detail.setType(Constant.ACCOUNT_DETAIL_TYPE13);
				detail.setState(Constant.ACCOUNT_DETAIL_STATE1);
				
				ResponseObject<Object> result = this.accountService.addAccountDetail(detail);
				try {
					@SuppressWarnings("unchecked")
					Map<String, String> map = (Map<String, String>) result.getData();
					String notifyUrl = "http://" + request.getServerName() + "/account/alipay_notify_url";
					String returnUrl = "http://" + request.getServerName() + "/account/alipay_return_url";

					outStr = ALiPayUtil.createPayMoneyByALiPay(BigDecimal.valueOf(Double.valueOf(amount)), AccountUtil
					        .getAlipayOrderIdByAccountDetailId(map.get("id")), userName, notifyUrl, returnUrl);
				} catch (Exception e) {
					outStr = "获取连接支付宝字符串失败";
				}
			} else {
				outStr = "充值金额必须大于0";
			}
		} catch (Exception e) {
			outStr = "充值金额必须为数字,充值失败";
		}

		try {
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(outStr);
			
		} catch (IOException e) {
			log.error("写出出现异常");
		}
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/account/alipay_return_url", method = { RequestMethod.GET, RequestMethod.POST })
	public void alipayReturnUrl(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, String> params = new HashMap<String, String>();
			Map<String, String> params2 = new HashMap<String, String>();
			Map requestParams = request.getParameterMap();
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
				}
				params2.put(name, valueStr);
				params.put(name, new String(valueStr.getBytes("ISO-8859-1"), "utf-8"));
			}
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
			String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");

			response.setContentType("text/html;charset=utf-8");
			AccountDetail detail = this.accountService.getAccountDetailById(
			        AccountUtil.getAccountDetailIdByAlipayOrderId(out_trade_no)).getData();
			detail.setRealAmount(detail.getAmount());
			boolean verify_result = AlipayNotify.verify(params) || AlipayNotify.verify(params2);
			if (verify_result) {
				if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
					detail.setState(Constant.ACCOUNT_DETAIL_STATE2);
					detail.setType(Constant.ACCOUNT_DETAIL_TYPE13);
					this.accountService.modifyAccountDetail(detail);
					log.info("充值成功" + out_trade_no);
					// out.println("充值成功<br />");
				} else {
					detail.setState(Constant.ACCOUNT_DETAIL_STATE3);
					this.accountService.modifyAccountDetail(detail);
					log.info("充值失败" + out_trade_no);
					// out.println("充值失败，如果您确认你充值成功，请联系客服！");
				}
			} else {
				detail.setState(Constant.ACCOUNT_DETAIL_STATE3);
				this.accountService.modifyAccountDetail(detail);
				log.info("充值失败" + out_trade_no);
				// out.println("充值失败，如果您确认你充值成功，请联系客服！");
			}
			// 跳转到充值详细界面
			response.sendRedirect(request.getContextPath() + "/dashboard/Finance/records.jsp");//caiwujilu.jsp
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (ServiceException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/account/alipay_notify_url", method = { RequestMethod.GET, RequestMethod.POST })
	public void alipayNoticyUrl(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, String> params = new HashMap<String, String>();
			Map<String, String> params2 = new HashMap<String, String>();
			Map requestParams = request.getParameterMap();
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
				}
				params2.put(name, valueStr);
				params.put(name, new String(valueStr.getBytes("ISO-8859-1"), "utf-8"));
			}
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
			String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");

			// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
			PrintWriter out = response.getWriter();
			response.setContentType("text/html;charset=utf-8");
			AccountDetail detail = this.accountService.getAccountDetailById(
			        AccountUtil.getAccountDetailIdByAlipayOrderId(out_trade_no)).getData();
			detail.setRealAmount(detail.getAmount());
			if (AlipayNotify.verify(params) || AlipayNotify.verify(params2)) {// 验证成功
				if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
					detail.setState(Constant.ACCOUNT_DETAIL_STATE2);
					detail.setType(Constant.ACCOUNT_DETAIL_TYPE13);
					this.accountService.modifyAccountDetail(detail);
				} else {
					detail.setState(Constant.ACCOUNT_DETAIL_STATE3);
					this.accountService.modifyAccountDetail(detail);
				}
				out.println("success"); // 请不要修改或删除
			} else {// 验证失败  
				detail.setState(Constant.ACCOUNT_DETAIL_STATE3);
				this.accountService.modifyAccountDetail(detail);
				out.println("fail");
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	*/
	/*暂停alipay end*/
	
	/*暂停weixin pay start*/
	/*
	@RequestMapping(value="/weixin/recharge", method=RequestMethod.GET)
	@ResponseBody
	public ResponseObject<String> weixinRecharge(HttpServletRequest request,
			@RequestParam(value = "amount") double amount){
		ResponseObject<String> responseObject = new ResponseObject<String>();
		String payOrderIdsStr = new Date().getTime() + StringUtil.generateRandomString(5);
		Map<String, String> map = WeixinConfig.getDefaultMap();
		try {
			WeixinConfig.setOtherFields(map, String.valueOf((int)(amount*100)), "AnyoneHelps(互联帮)扫码充值", payOrderIdsStr, payOrderIdsStr);
			map.put("notify_url", "http://" + request.getServerName() + "/weixin/scanRechargeSuccess");//http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/weixin/scanRechargeSuccess
			map.put("attach", (String) request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY));
			String codeUrl = WeixinScanPayUtil.postDataByMap(map);
			if(StringUtil.isEmpty(codeUrl)){
				return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "扫码加载异常");
			}else{
				responseObject.setData(codeUrl);
				String userId = (String) request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
				ResponseObject<Object> addResponseObject = this.weixinService.addPreRecharge(userId, amount, payOrderIdsStr);
				if(!ResponseCode.SUCCESS_CODE.equals(addResponseObject.getCode())){
					responseObject.setCode(addResponseObject.getCode());
					responseObject.setMessage(addResponseObject.getMessage());
				}
				request.getSession().setAttribute(Constant.USER_WEIXIN_SCAN_PAY_SESSION_KEY, payOrderIdsStr);
			}
			
		} catch (ServiceException e) {
			e.printStackTrace();
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "扫码加载异常");
		}
		return responseObject;
	}
	@RequestMapping("qr_code.img")
	@ResponseBody
	public void getQRCode(HttpServletResponse response, String codeUrl){
		QRCodeUtil.encodeQRCode(response, codeUrl);
	}
	
	@RequestMapping(value="/weixin/scanRechargeSuccess", method={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public void rechargeSuccess(HttpServletRequest request, HttpServletResponse response){
		String requestXml = "";//微信传过来的
		String responseXml = "";//我们这里返回去给微信的
		String nextLine = "";
		try {
			while((nextLine = request.getReader().readLine()) != null){
				requestXml += nextLine;
			}
			request.getReader().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.print(requestXml);
		
		Map<String, String> map = WeixinScanPayUtil.xmlToMap(requestXml);
		String returnCode = "FAIL";
		String returnMsg = "报文为空";
		if(map == null){
			//没有数据,用户恶意进入
			return ;
		}else if("SUCCESS".equals(map.get("result_code"))){
			returnCode = "SUCCESS";
			returnMsg = "ok";
		}
		responseXml = "<xml><return_code><![CDATA[" + returnCode + "]]></return_code><return_msg><![CDATA[" + returnMsg + "]]></return_msg></xml>"; 
		BufferedOutputStream bufferedOutputStream = null;
		try {
			bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());
			bufferedOutputStream.write(responseXml.getBytes());
			bufferedOutputStream.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(null != bufferedOutputStream){
				try {
					bufferedOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		String amount = String.format("%.2f", StringUtil.string2Double(map.get("total_fee"))/100);// String.format("%.2f", StringUtil.string2Double(map.get("total_fee"))/100);//String.format("%.2f", StringUtil.string2Double("1")/100);
		String userId = map.get("attach");//map.get("attach");//(String) request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
		String outTradeNo = map.get("out_trade_no");//map.get("outTradeNo");//(String) request.getSession().getAttribute(Constant.USER_WEIXIN_SCAN_PAY_SESSION_KEY);
		log.info(DateUtil.date2String(new Date()) + "---用户" + userId + "微信扫码支付:" + amount + ", 现开始数据库持久化操作");//万一数据库持久化失败，保存用户存款记录
		ResponseObject<String> responseObject = null;
		try {
			responseObject = weixinService.scanRecharge(userId, amount, outTradeNo);
		} catch (ServiceException e) {
			log.error(DateUtil.date2String(new Date()) + "---用户" + userId + "微信扫码支付:" + amount + ", 数据库持久化操作---失败");
			e.printStackTrace();
		}
		if(responseObject.getCode().equals(ResponseCode.SUCCESS_CODE)){
			
			log.info(DateUtil.date2String(new Date()) + "---用户" + userId + "微信扫码支付:" + amount + ", 数据库持久化操作---完成");
		}else{
			log.error(DateUtil.date2String(new Date()) + "---用户" + userId + "微信扫码支付:" + amount + ", 数据库持久化操作---失败");
			
		}
	}
	
	@RequestMapping(value="/user/weixin/checkIfScanPay", method=RequestMethod.GET)
	@ResponseBody
	public ResponseObject<Object> rechargeSuccess(HttpServletRequest request,
			@RequestParam(value = "amount") String amount){
		String userId = (String) request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
		String payOrderIdsStr = (String) request.getSession().getAttribute(Constant.USER_WEIXIN_SCAN_PAY_SESSION_KEY);
		if(null != payOrderIdsStr){
			try{
				ResponseObject<Object> responseObject =  this.weixinService.checkIfScanPay(userId, amount, payOrderIdsStr);
				if(responseObject.getCode().equals(ResponseCode.SUCCESS_CODE)){
					request.getSession().removeAttribute(Constant.USER_WEIXIN_SCAN_PAY_SESSION_KEY);
				}
				return responseObject;
			}catch(Exception e){
				log.error("检查扫码支付出现异常");
				e.printStackTrace();
				return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "检查扫码支付出现异常");
			}
		}
		return generateResponseObject(ResponseCode.ACCOUNT_SCAN_PAY_NOT_EXISTS, "已没有待充值的信息");
	}
	*/
	/*暂停weixin pay end*/
	
	/*暂停用户之间转帐  start*/
	/*@RequestMapping(value = "/account/transfer", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ResponseObject<Object> transfer(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "account") String account,
			@RequestParam(value = "amount") String amount) {
		if(StringUtil.isEmpty(account)){
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "输入收款帐号错误!");
		}
		if(StringUtil.isEmpty(amount)){
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "输入转帐金额错误!");
		}
		try {
			double da = Double.valueOf(amount != null ? amount.trim() : "");
			if (da > 0) {
				
				String userId = StringUtil.obj2String(request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY));
				String nickName = StringUtil.obj2String(request.getSession().getAttribute(Constant.USER_NICK_NAME_SESSION_KEY));
				return this.accountService.transfer(userId,nickName,account,amount);
			} else {
				log.error("输入错误,转帐金额必须大于0!");
				return generateResponseObject(ResponseCode.PARAMETER_ERROR, "输入错误,转帐金额必须大于0!");
			}
		} catch (Exception e) {
			log.error("转帐服务发生异常!:"+e.getMessage());
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "系统繁忙，请稍后再试!");
		}
	}*/
	/*暂停用户之间转帐  end*/

}
