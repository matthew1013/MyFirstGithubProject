package com.anyonehelps.exception.handler;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.anyonehelps.common.constants.ResponseCode;

public class GlobalExceptionFilter extends HttpServlet implements Filter {

	private static final long serialVersionUID = -7366505914232643258L;
	private static final Logger log = Logger.getLogger(GlobalExceptionFilter.class);

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
	        throws IOException, ServletException {
		try {
			filterChain.doFilter(servletRequest, servletResponse);
		} catch (Throwable e) {
			log.error(e);
			String result = "";
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("code", ResponseCode.REQUEST_ALL_EXCEPTION);
			jsonObject.put("message", e.getMessage());
			result = jsonObject.toString();
			servletResponse.setContentType("application/json;charset=UTF-8");
			servletResponse.getWriter().write(result);
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		super.init();
	}

}
