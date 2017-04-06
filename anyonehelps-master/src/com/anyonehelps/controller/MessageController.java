package com.anyonehelps.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anyonehelps.common.constants.Constant;
import com.anyonehelps.common.constants.ParameterConstants;
import com.anyonehelps.common.constants.ResponseCode;
import com.anyonehelps.common.util.MessageUtil;
import com.anyonehelps.common.util.StringUtil;
import com.anyonehelps.model.Message;
import com.anyonehelps.model.PageSplit;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.service.MessageService;
/**
 * 私信
 * @author chenkanghua
 *
 */
@Controller
public class MessageController extends BasicController {

	private static final long serialVersionUID = -8158555007757915055L;
	private static final Logger log = Logger.getLogger(MessageController.class);

	@Resource(name = "messageService")
	private MessageService messageService;
	@Value(value = "${page_size}")
	private int defaultPageSize;

	@RequestMapping(value = "/message/add", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> addMessage(
	        HttpServletRequest request,
	        @RequestParam(value = ParameterConstants.MESSAGE_CONTENT) String content,
	        @RequestParam(value = ParameterConstants.MESSAGE_FRIEND_ID) String friendId) {
		if (!MessageUtil.validateContent(content)) {
			return generateResponseObject(ResponseCode.MESSAGE_CONTENT_ERROR, "私信内容不能为空!");
		}

		if (!MessageUtil.validateId(friendId)) {
			return generateResponseObject(ResponseCode.MESSAGE_FATHER_ID_ERROR, "参数错误!");
		}

		String userId = StringUtil.obj2String(request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY));
		if(friendId.equals(userId)){
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "不能给自己发私信！");
		}
		try {
			return this.messageService.saveMessage(userId,friendId,content);
		} catch (Exception e) {
			log.error("私信发送失败", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "私信发送失败");
		}
	}

	@RequestMapping(value = "/message/search", method = {RequestMethod.GET })
	@ResponseBody
	public ResponseObject<PageSplit<Message>> searchAll(
			HttpServletRequest request,
	        @RequestParam(value = ParameterConstants.COMMON_SEARCH_KEY, required = false, defaultValue = "") String key,
	        @RequestParam(value = ParameterConstants.MESSAGEUSER_STATE, required = false, defaultValue = "") String state,
	        @RequestParam(value = ParameterConstants.COMMON_PAGESPLIT_PAGE_INDEX, required = false, defaultValue = "1") int pageIndex,
	        @RequestParam(value = ParameterConstants.COMMON_PAGESPLIT_PAGE_SIZE, required = false, defaultValue = "10") int pageSize
	        ) {
		String userId = StringUtil.obj2String(request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY));
		 
		try {
			pageIndex = Math.max(pageIndex, 1);
			if(!Constant.MESSAGE_STATE_UNREAD.equals(state)&&!Constant.MESSAGE_STATE_READ.equals(state)){
				state="";
			}
			return this.messageService.searchByUserId(key, userId, state, pageSize, pageIndex);
		} catch (Exception e) {
			log.error("获取私信列表出现异常", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取私信列表失败!");
		}
	}
	
	
	@RequestMapping(value = "/message/search_by_friend_id", method = {RequestMethod.GET })
	@ResponseBody
	public ResponseObject<PageSplit<Message>> searchByFriendId(
			HttpServletRequest request,
	        @RequestParam(value = ParameterConstants.COMMON_SEARCH_KEY, required = false, defaultValue = "") String key,
	        @RequestParam("friendId") String friendId,
	        @RequestParam(value = ParameterConstants.COMMON_PAGESPLIT_PAGE_INDEX, required = false, defaultValue = "1") int pageIndex,
	        @RequestParam(value = ParameterConstants.COMMON_PAGESPLIT_PAGE_SIZE, required = false, defaultValue = "10") int pageSize
	        ) {
		if(StringUtil.isEmpty(friendId)){
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数错误!");
		}
		String userId = StringUtil.obj2String(request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY));
		
		try {
			pageIndex = Math.max(pageIndex, 1);
			return this.messageService.searchByFriendId(key, userId, friendId, pageSize, pageIndex);
		} catch (Exception e) {
			log.error("获取私信详情出现异常", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取私信详情失败!");
		}
	}
	
	@RequestMapping(value = "/message/delete_all", method = {RequestMethod.GET })
	@ResponseBody
	public ResponseObject<Object> deleteAll(
	        HttpServletRequest request,
	        @RequestParam(value = ParameterConstants.MESSAGE_BLACKLIST_FRIEND_ID) String friendId	        
	        ) {
		try {
			String userId = StringUtil.obj2String(request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY));
			
			return this.messageService.deleteAll(userId, friendId);
		} catch (Exception e) {
			log.error("删除私信内容失败！", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "删除私信内容失败!");
		}
	}
	/*
	@RequestMapping(value = "/message/getcount", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseObject<Map<String, String>> getSelfMessageCount(HttpServletRequest request) {
		try {
			String userId = StringUtil.obj2String(request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY));
			return this.messageService.getMessageCount(userId);
		} catch (Exception e) {
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取留言数量失败");
		}
	}*/
	
	@RequestMapping(value = "/message/delete", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> deleteFriendById(HttpServletRequest request,
			@RequestParam(value = "ids", required = true) String[] ids) {
		try {
			String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			return this.messageService.messageDelete(userId, ids);
		} catch (Exception e) {
			log.error("删除私信出现异常", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "删除私信出现异常!");
		}
	}
}
