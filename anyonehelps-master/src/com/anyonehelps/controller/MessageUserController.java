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
import com.anyonehelps.model.MessageUser;
import com.anyonehelps.model.PageSplit;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.service.MessageUserService;

@Controller
public class MessageUserController extends BasicController {
	private static final long serialVersionUID = -3872980921595781142L;

	private static final Logger log = Logger.getLogger(MessageUserController.class);

	@Resource(name = "messageUserService")
	private MessageUserService messageUserService;

	@RequestMapping(value = "/message_user/search", method = {RequestMethod.GET })
	@ResponseBody
	public ResponseObject<PageSplit<MessageUser>> search(
	        HttpServletRequest request,
	        @RequestParam(value = ParameterConstants.COMMON_SEARCH_KEY, required = false, defaultValue = "") String key,
	        @RequestParam(value = ParameterConstants.MESSAGEUSER_STATE, required = false, defaultValue = "") String state,
	        @RequestParam(value = ParameterConstants.COMMON_PAGESPLIT_PAGE_INDEX, required = false, defaultValue = "1") int pageIndex,
	        @RequestParam(value = ParameterConstants.COMMON_PAGESPLIT_PAGE_SIZE, required = false, defaultValue = "10") int pageSize
	        
	        ) {
		try {
			key =  new String(key.getBytes("ISO-8859-1"), "utf-8");
			String userId = StringUtil.obj2String(request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY));
			if(!Constant.MESSAGEUSER_STATE_UNREAD.equals(state)&&!Constant.MESSAGEUSER_STATE_READ.equals(state)){
				state="";
			}
			pageIndex = Math.max(pageIndex, 1);
			return this.messageUserService.searchByUserId(key, userId, state, pageSize, pageIndex);
		} catch (Exception e) {
			log.error("获取用户留言列表出现异常", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取留言列表失败!");
		}
	}
	
	@RequestMapping(value = "/message_user/read", method = {RequestMethod.GET })
	@ResponseBody
	public ResponseObject<Object> read(
	        HttpServletRequest request,
	        @RequestParam(value = ParameterConstants.MESSAGEUSER_ID, required = true) String id	        
	        ) {
		try {
			String userId = StringUtil.obj2String(request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY));
			
			return this.messageUserService.modifyState(userId, id, Constant.MESSAGEUSER_STATE_READ );
		} catch (Exception e) {
			log.error("更新MessageUser状态出现异常", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "更新留言状态失败!");
		}
	}
	
	@RequestMapping(value = "/message_user/read_all", method = {RequestMethod.GET })
	@ResponseBody
	public ResponseObject<Object> readAll( HttpServletRequest request ) {
		try {
			String userId = StringUtil.obj2String(request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY));
			
			return this.messageUserService.modifyAllReadState(userId);
		} catch (Exception e) {
			log.error("更新MessageUser状态出现异常", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "更新留言状态失败!");
		}
	}
	
	@RequestMapping(value = "/message_user/delete", method = {RequestMethod.GET })
	@ResponseBody
	public ResponseObject<Object> delete(
	        HttpServletRequest request,
	        @RequestParam(value = ParameterConstants.MESSAGEUSER_ID, required = true) String id	        
	        ) {
		try {
			String userId = StringUtil.obj2String(request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY));
			
			return this.messageUserService.modifyState(userId, id, Constant.MESSAGEUSER_STATE_DELETE );
		} catch (Exception e) {
			log.error("删除留言失败！", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "删除留言失败!");
		}
	}
	
	@RequestMapping(value = "/message_user/delete_ids", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> deleteByIds(HttpServletRequest request,
			@RequestParam(value = "ids", required = true) String[] ids) {
		try {
			String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			return this.messageUserService.modifyStateByIds(userId, ids, Constant.MESSAGE_STATE_DELETE);
		} catch (Exception e) {
			log.error("删除留言消息出现异常", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "删除留言消息失败!");
		}
	}

}
