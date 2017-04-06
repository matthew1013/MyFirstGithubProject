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
import com.anyonehelps.model.Friend;
import com.anyonehelps.model.PageSplit;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.model.User;
import com.anyonehelps.service.FriendService;

@Controller
public class FriendController extends BasicController {
	private static final long serialVersionUID = 6517783699624173105L;

	private static final Logger log = Logger.getLogger(FriendController.class);

	@Resource(name = "friendService")
	private FriendService friendService;
	
	@Value(value = "${page_size}")
	private int defaultPageSize;
	
	@RequestMapping(value = "/friend/add", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> addFriend( HttpServletRequest request,
			@RequestParam(value = "ids") String[] ids) {
		ResponseObject<Object> responseObj = null;
		if (ids == null || ids.length == 0) {
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数无效");
		}
		String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
		
		for(String id:ids){
			if(id.equals(userId)){
				return generateResponseObject(ResponseCode.PARAMETER_ERROR, "不能关注自己");
			}
		}
		//此处应该还没验证ids 
		try {
			String username = (String)request.getSession().getAttribute(Constant.USER_NICK_NAME_SESSION_KEY);
			responseObj = this.friendService.addFriends(userId, username, ids);
		} catch (Exception e) {
			log.error("关注添加失败", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "关注失败!");
		}
		return responseObj;
	}
	
	@RequestMapping(value = "/friend/search", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseObject<PageSplit<Friend>> searchFriendByKey(HttpServletRequest request,
			@RequestParam(value = ParameterConstants.COMMON_SEARCH_KEY, required = false, defaultValue = "") String key,
			@RequestParam(value = ParameterConstants.DEMAND_ID, required = false, defaultValue = "") String demandId,  /*这个参数是为了获取这个任务是否邀请了用户*/
			@RequestParam(value = ParameterConstants.COMMON_PAGESPLIT_PAGE_INDEX, required = false, defaultValue = "1") int pageIndex,
			@RequestParam(value = ParameterConstants.COMMON_PAGESPLIT_PAGE_SIZE, required = false, defaultValue = "10") int pageSize
			
		) {
		try {
			key =  new String(key.getBytes("ISO-8859-1"), "utf-8");
			String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			return this.friendService.searchByKey(userId, key,demandId, pageSize, pageIndex);
		} catch (Exception e) {
			log.error("获取关注列表出现异常", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取关注列表出现异常!");
		}
	}
	
	@RequestMapping(value = "/friend/delete", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> deleteFriendById(HttpServletRequest request,
			@RequestParam(value = "ids", required = true) String[] ids) {
		try {
			String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			return this.friendService.deleteFriends(userId, ids);
		} catch (Exception e) {
			log.error("取消关注用户出现异常", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "取消关注用户出现异常!");
		}
	}
	/**
	 * 查找关注我的人
	 * @param request
	 * @param key
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/friend/search_ed", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseObject<PageSplit<User>> searchFriendByKeyAndEd(HttpServletRequest request,
			@RequestParam(value = ParameterConstants.COMMON_SEARCH_KEY, required = false, defaultValue = "") String key,
			@RequestParam(value = ParameterConstants.COMMON_PAGESPLIT_PAGE_INDEX, required = false, defaultValue = "1") int pageIndex,
			@RequestParam(value = ParameterConstants.COMMON_PAGESPLIT_PAGE_SIZE, required = false, defaultValue = "10") int pageSize
			
		) {
		try {
			key =  new String(key.getBytes("ISO-8859-1"), "utf-8");
			String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			return this.friendService.searchByKeyAndEd(userId, key, pageSize, pageIndex);
		} catch (Exception e) {
			log.error("获取关注我的人列表出现异常", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取关注关注我的人列表出现异常!");
		}
	}
}
