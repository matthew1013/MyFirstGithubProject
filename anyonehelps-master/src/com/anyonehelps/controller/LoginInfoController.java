package com.anyonehelps.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jxl.common.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anyonehelps.common.constants.Constant;
import com.anyonehelps.common.constants.ResponseCode;
import com.anyonehelps.common.util.StringUtil;
import com.anyonehelps.dao.LoginInfoDao;
import com.anyonehelps.model.LoginInfo;
import com.anyonehelps.model.ResponseObject;

@Controller
public class LoginInfoController extends BasicController {
	private static final long serialVersionUID = -4432869353448470217L;
	private static final Logger log = Logger.getLogger(LoginInfoController.class);
	@Autowired
	private LoginInfoDao loginInfoDao;

	@RequestMapping(value = "/logininfo/get_self", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseObject<LoginInfo> getSelfLoginInfo(HttpServletRequest request) {
		ResponseObject<LoginInfo> infos = new ResponseObject<LoginInfo>(ResponseCode.SUCCESS_CODE);
		HttpSession session = request.getSession();
		String userId = StringUtil.obj2String(session.getAttribute(Constant.USER_ID_SESSION_KEY));

		try {
			infos.setData(this.loginInfoDao.getById(userId));
		} catch (Exception e) {
			log.error("获取登录信息失败", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取个人登录信息失败");
		}
		return infos;
	}
}
