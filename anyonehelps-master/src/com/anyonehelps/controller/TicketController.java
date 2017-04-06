package com.anyonehelps.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anyonehelps.common.constants.Constant;
import com.anyonehelps.common.constants.ResponseCode;
import com.anyonehelps.common.util.TicketUtil;
import com.anyonehelps.common.util.UserUtil;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.model.Ticket;
import com.anyonehelps.service.TicketService;

@Controller
public class TicketController extends BasicController {
	private static final long serialVersionUID = 6517783699624173105L;

	private static final Logger log = Logger.getLogger(TicketController.class);

	@Resource(name = "ticketService")
	private TicketService ticketService;
	
	@RequestMapping(value = "/ticket/add", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> addTicket(HttpServletRequest request, Ticket ticket ) {
		ResponseObject<Object> responseObj = null;
		if (!TicketUtil.validateEmail(ticket.getEmail())&&
				!(UserUtil.validateAreaCode(ticket.getAreaCode())&&UserUtil.validatePhone(ticket.getTelphone()))) {
			return generateResponseObject( ResponseCode.PARAMETER_ERROR, "邮箱或者手机填一项!");
		}
		if (!TicketUtil.validateName(ticket.getName())) {
			return generateResponseObject( ResponseCode.PARAMETER_ERROR, "名字输入不正确!");
		}
		if (!TicketUtil.validateSubject(ticket.getSubject())) {
			return generateResponseObject( ResponseCode.PARAMETER_ERROR, "主题输入不正确!");
		}
		if (!TicketUtil.validateDescription(ticket.getDescription())) {
			return generateResponseObject( ResponseCode.PARAMETER_ERROR, "内容输入不正确!");
		}
		try {
			String userId = (String) request.getSession().getAttribute(
					Constant.USER_ID_SESSION_KEY);
			ticket.setStatus("0");
			ticket.setUserId(userId);
			responseObj = this.ticketService.addTicket(ticket);
		} catch (Exception e) {
			log.error("提交失败", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "提交失败!");
		}
		return responseObj;
	}
}
