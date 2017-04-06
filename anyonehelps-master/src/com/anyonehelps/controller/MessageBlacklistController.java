package com.anyonehelps.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anyonehelps.common.constants.Constant;
import com.anyonehelps.common.constants.ParameterConstants;
import com.anyonehelps.common.constants.ResponseCode;
import com.anyonehelps.common.util.StringUtil;
import com.anyonehelps.common.util.UserUtil;
import com.anyonehelps.model.MessageUser;
import com.anyonehelps.model.PageSplit;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.service.MessageBlacklistService;

@Controller
public class MessageBlacklistController extends BasicController {
	private static final long serialVersionUID = -3872980921595781142L;

	private static final Logger log = Logger.getLogger(MessageBlacklistController.class);

	@Resource(name = "messageBlacklistService")
	private MessageBlacklistService messageBlacklistService;
	
	@RequestMapping(value = "/blacklist/add", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> addMessageBlacklist(
	        HttpServletRequest request,
	        @RequestParam(value = ParameterConstants.MESSAGE_BLACKLIST_FRIEND_ID) String friendId) {
		if (!UserUtil.validateId(friendId)) {
			return generateResponseObject(ResponseCode.MESSAGE_CONTENT_ERROR, "参数错误!");
		}

		String userId = StringUtil.obj2String(request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY));
		try {
			return this.messageBlacklistService.saveMessageBlacklist(userId,friendId);
		} catch (Exception e) {
			log.error("添加黑名单失败,请重试！", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "添加黑名单失败,请重试！");
		}
	}
	
	@RequestMapping(value = "/blacklist/search", method = {RequestMethod.GET })
	@ResponseBody
	public ResponseObject<PageSplit<MessageUser>> search(
	        HttpServletRequest request,
	        @RequestParam(value = ParameterConstants.COMMON_SEARCH_KEY, required = false, defaultValue = "") String key,
	        @RequestParam(value = ParameterConstants.COMMON_PAGESPLIT_PAGE_INDEX, required = false, defaultValue = "1") int pageIndex,
	        @RequestParam(value = ParameterConstants.COMMON_PAGESPLIT_PAGE_SIZE, required = false, defaultValue = "10") int pageSize
	        
	        ) {
		try {
			key =  new String(key.getBytes("ISO-8859-1"), "utf-8");
			String userId = StringUtil.obj2String(request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY));
			pageIndex = Math.max(pageIndex, 1);
			return this.messageBlacklistService.searchByUserId(key, userId,  pageSize, pageIndex);
		} catch (Exception e) {
			log.error("获取黑名单列表出现异常", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取黑名单列表失败!");
		}
	}
	
	@RequestMapping(value = "/blacklist/delete", method = {RequestMethod.GET })
	@ResponseBody
	public ResponseObject<Object> delete(
	        HttpServletRequest request,
	        @RequestParam(value = ParameterConstants.MESSAGE_BLACKLIST_FRIEND_ID) String friendId	        
	        ) {
		try {
			String userId = StringUtil.obj2String(request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY));
			
			return this.messageBlacklistService.delete(userId, friendId);
		} catch (Exception e) {
			log.error("删除黑名单失败！", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "删除黑名单失败!");
		}
	}

}
