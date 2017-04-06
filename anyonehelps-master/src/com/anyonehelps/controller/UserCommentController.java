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
import com.anyonehelps.common.util.UserCommentUtil;
import com.anyonehelps.model.PageSplit;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.model.UserComment;
import com.anyonehelps.service.UserCommentService;

@Controller
public class UserCommentController extends BasicController {
	private static final long serialVersionUID = -3872980921595781142L;

	private static final Logger log = Logger.getLogger(UserCommentController.class);

	@Resource(name = "userCommentService")
	private UserCommentService userCommentService;

	
	@RequestMapping(value = "/user_comment/add", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> addUserComment( HttpServletRequest request,
			UserComment uc) {
		ResponseObject<Object> responseObj = null;
		if (!UserCommentUtil.validateContent(uc.getContent())) {
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "留言过长或者过短!");
		}

		if (!UserCommentUtil.validateParentId(uc.getParentId())) {
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数错误!");
		}
		if (!UserCommentUtil.validateUserId(uc.getUserId())) {
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数错误!");
		} 

		if (StringUtil.isEmpty(uc.getParentId())) {
			uc.setParentId("-1");
		}
		try {

			String userId = (String) request.getSession().getAttribute(
					Constant.USER_ID_SESSION_KEY);
			//String userNick = (String) request.getSession().getAttribute(
			//		Constant.USER_NICK_NAME_SESSION_KEY);
			uc.setSenderId(userId);
			uc.setState("0");
			responseObj = this.userCommentService.saveUserComment(uc);
			return responseObj;
		} catch (Exception e) {
			log.error("提交留言失败", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION,
					"提交留言失败，请重试!");
		}
		
	}
	
	@RequestMapping(value = "/user_comment/search", method = {RequestMethod.GET })
	@ResponseBody
	public ResponseObject<PageSplit<UserComment>> search(  
	        HttpServletRequest request,
	        @RequestParam(value = ParameterConstants.USER_ID) String userId,
	        @RequestParam(value = ParameterConstants.COMMON_SEARCH_KEY, required = false, defaultValue = "") String key,
	        @RequestParam(value = ParameterConstants.COMMON_PAGESPLIT_PAGE_INDEX, required = false, defaultValue = "1") int pageIndex,
	        @RequestParam(value = ParameterConstants.COMMON_PAGESPLIT_PAGE_SIZE, required = false, defaultValue = "10") int pageSize
	        
	        ) {
		try {
			key =  new String(key.getBytes("ISO-8859-1"), "utf-8");
			pageIndex = Math.max(pageIndex, 1);
			return this.userCommentService.searchByUserId(key, userId, pageSize, pageIndex);
		} catch (Exception e) {
			log.error("获取用户留言列表出现异常", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取留言列表失败!");
		}
	}
}
