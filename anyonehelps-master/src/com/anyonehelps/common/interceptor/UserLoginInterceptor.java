package com.anyonehelps.common.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.anyonehelps.common.constants.Constant;
import com.anyonehelps.common.constants.ResponseCode;
import com.anyonehelps.common.util.StringUtil;

public class UserLoginInterceptor extends HandlerInterceptorAdapter {
	private List<String> needLoginUrlPrefix;
	private List<String> notNeedLoginUrl;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		boolean needLoged = false;
		String URI = request.getRequestURI();
		String projectName = request.getContextPath();
		URI = URI.substring(projectName.length());
		String responseCode = null;
		String message = null;

		if (URI.indexOf(".") < 0) {
			for (String prefix : needLoginUrlPrefix) {
				if (URI.startsWith(prefix)) {
					needLoged = true;
					break;
				}
			}
		}

		if (needLoged) {
			for (String url : notNeedLoginUrl) {
				if (URI.startsWith(url)) {
					needLoged = false;
				}
			}
		}

		if (needLoged) {
			HttpSession session = request.getSession();
			Object userId = session.getAttribute(Constant.USER_ID_SESSION_KEY);
			if (userId == null || StringUtil.isEmpty(userId.toString())) {
				responseCode = ResponseCode.NEED_LOGIN;
				message = "请登录系统后再操作";
			}
		}

		if (responseCode != null) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("code", responseCode);
			jsonObject.put("message", message);
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(jsonObject.toString());
			return false;
		}

		return super.preHandle(request, response, handler);
	}

	public void setNeedLoginUrlPrefix(List<String> needLoginUrlPrefix) {
		this.needLoginUrlPrefix = needLoginUrlPrefix;
	}

	public void setNotNeedLoginUrl(List<String> notNeedLoginUrl) {
		this.notNeedLoginUrl = notNeedLoginUrl;
	}

}
