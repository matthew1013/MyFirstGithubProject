package com.anyonehelps.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anyonehelps.common.constants.Constant;
import com.anyonehelps.common.constants.ResponseCode;
import com.anyonehelps.common.util.UserUtil;
import com.anyonehelps.model.ActivityUser;
//import com.anyonehelps.common.util.TicketUtil;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.service.ActivityUserService;

@Controller
public class ActivityUserController extends BasicController {
	private static final long serialVersionUID = 6517783699624173115L;

	private static final Logger log = Logger.getLogger(ActivityUserController.class);

	@Resource(name = "activityUserService")
	private ActivityUserService activityUserService;
	//默认头像路径
	@Value(value = "${user_pic_default_url}")
	private String userPicDefaultUrl;
	
	@RequestMapping(value = "/activity_user/add", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> addActivityUser(HttpServletRequest request, ActivityUser activityUser ) {
		if (!UserUtil.validateEmail(activityUser.getEmail())) {
			return generateResponseObject(ResponseCode.USER_EMAIL_ERROR, "联系邮箱输入不正确，请重新输入！");
		}
		
		ResponseObject<Object> responseObj = null;
		try {
			String userId = (String) request.getSession().getAttribute(
					Constant.USER_ID_SESSION_KEY);
			activityUser.setStatus("0");
			activityUser.setUserId(userId);
			String clientIp = getClientIp(request);
			responseObj = this.activityUserService.addActivityUser(request, activityUser, userPicDefaultUrl, clientIp);
		} catch (Exception e) {
			log.error("报名失败", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "报名失败!");
		}
		return responseObj;
	}
}
