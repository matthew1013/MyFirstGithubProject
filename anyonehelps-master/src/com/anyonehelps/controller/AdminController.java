package com.anyonehelps.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anyonehelps.common.constants.Constant;
import com.anyonehelps.common.constants.ParameterConstants;
import com.anyonehelps.common.constants.ResponseCode;
import com.anyonehelps.model.Admin;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.service.AdminService;
import com.anyonehelps.service.CommonService;

@Controller
public class AdminController extends BasicController {
	private static final long serialVersionUID = -8822585135044413060L;
	private static final Logger log = Logger.getLogger(AdminController.class);

	@Resource(name = "adminService")
	private AdminService adminService;
	
	@Resource(name = "commonService")
	private CommonService commonService;


	@RequestMapping(value = "/admin/login", method = { RequestMethod.POST })  
	@ResponseBody
	public ResponseObject<Admin> login(HttpServletRequest request,
	        @RequestParam(value = ParameterConstants.COMMON_ACCOUNT) String account,
	        @RequestParam(value = ParameterConstants.USER_PASSWORD) String password
	        ) {
		ResponseObject<Admin> responseObj = null;
		try {
			ResponseObject<Admin> admin = this.adminService.checkLogin(account, password);
			String code = admin.getCode();
			if (ResponseCode.SUCCESS_CODE.equals(code)) {
				HttpSession session = request.getSession();
				session.setAttribute(Constant.ADMIN_ID_SESSION_KEY, admin.getData().getId());
				session.setAttribute(Constant.ADMIN_EMAIL_SESSION_KEY, admin.getData().getEmail());
				session.setAttribute(Constant.LOGIN_ACCOUNT_TYPE, "1");
				
			} else {
				log.error("用户登录失败,code:" + code);
			}
			responseObj = admin;
		} catch (Exception e) {
			log.error("调用后台代码出错", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取个人信息失败!");
		}
		return responseObj;
	}

	@RequestMapping(value = "/admin/logout", method = {  RequestMethod.GET, RequestMethod.POST }) //RequestMethod.GET,
	@ResponseBody
	public ResponseObject<Object> logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute(Constant.ADMIN_ID_SESSION_KEY);
		session.removeAttribute(Constant.ADMIN_EMAIL_SESSION_KEY);
		session.removeAttribute(Constant.LOGIN_ACCOUNT_TYPE);
		
		session.invalidate();
		
		return generateResponseObject(ResponseCode.SUCCESS_CODE);
	}


}
