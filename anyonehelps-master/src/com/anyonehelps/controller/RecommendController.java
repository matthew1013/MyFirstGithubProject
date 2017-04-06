package com.anyonehelps.controller;

import java.util.Arrays;

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
import com.anyonehelps.common.util.UserUtil;
import com.anyonehelps.model.Demand;
import com.anyonehelps.model.PageSplit;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.service.RecommendService;

@Controller
public class RecommendController extends BasicController {
	private static final long serialVersionUID = -3028267751799490422L;

	private static final Logger log = Logger.getLogger(RecommendController.class);

	@Resource(name = "recommendService")
	private RecommendService recommendService;
	
	@Value(value = "${page_size}")
	private int defaultPageSize;
	
	@RequestMapping(value = "/recommend/add", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> addRecommend( HttpServletRequest request,
			@RequestParam(value = "recommenders") String[] recommenders) {
		ResponseObject<Object> responseObj = null;
		if (recommenders == null || recommenders.length == 0) {
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数无效");
		}
		//此处应该还没验证recommender 是否符合手机号码或者邮箱规则
		try {
			String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			String userNickName = (String)request.getSession().getAttribute(Constant.USER_NICK_NAME_SESSION_KEY);
			responseObj = this.recommendService.addRecommends(userId,userNickName,Arrays.asList(recommenders));
		} catch (Exception e) {
			log.error("邀请失败", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "邀请失败!");
		}
		return responseObj;
	}
	@RequestMapping(value = "/recommend/add_phone", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> addRecommendByPhone( HttpServletRequest request,
			@RequestParam(value = ParameterConstants.USER_AREA_CODE) String areaCode,
	        @RequestParam(value = ParameterConstants.USER_PHONE) String telphone) {
		if (!UserUtil.validateAreaCode(areaCode) &&!UserUtil.validatePhone(telphone)) {
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数无效");
		} 
		ResponseObject<Object> responseObj = null;
		
		try {
			String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			String userNickName = (String)request.getSession().getAttribute(Constant.USER_NICK_NAME_SESSION_KEY);
			responseObj = this.recommendService.addRecommendByPhone(userId,userNickName,areaCode,telphone);
		} catch (Exception e) {
			log.error("邀请失败", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "邀请失败!");
		}
		return responseObj;
	}
	
	@RequestMapping(value = "/recommend/search", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseObject<PageSplit<Demand>> searchRecommendsByKey(HttpServletRequest request,
			@RequestParam(value = ParameterConstants.COMMON_SEARCH_KEY, required = false, defaultValue = "") String key,
			@RequestParam(value = ParameterConstants.RECOMMEND_STATE, required = false, defaultValue = "") String state,
			@RequestParam(value = ParameterConstants.COMMON_PAGESPLIT_PAGE_INDEX, required = false, defaultValue = "1") int pageIndex,
			@RequestParam(value = ParameterConstants.COMMON_PAGESPLIT_PAGE_SIZE, required = false, defaultValue = "10") int pageSize) {
		try {
			key =  new String(key.getBytes("ISO-8859-1"), "utf-8");
			String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			return this.recommendService.searchByKey(userId, key ,state,pageSize,pageIndex);
		} catch (Exception e) {
			log.error("获取邀请人列表出现异常", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取邀请人列表出现异常!");
		}
	}
	
	@RequestMapping(value = "/recommend/refresh", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseObject<Object> refreshRecommendsById(HttpServletRequest request,
			@RequestParam(value = ParameterConstants.RECOMMEND_ID, required = true) String id) {
		try {
			String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			return this.recommendService.modifyModifyDate(userId, id);
		} catch (Exception e) {
			log.error("刷新邀请人出现异常", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "刷新邀请人出现异常!");
		}
	}
	
	
	@RequestMapping(value = "/recommend/user_add", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> addRecommendUser( HttpServletRequest request,
			@RequestParam(value = "recommender") String recommender) {
		ResponseObject<Object> responseObj = null;
		if (recommender==null|| "-1".equals(recommender)|| "".equals(recommender)) {
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数无效");
		}
		try {
			String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			String userNickName = (String)request.getSession().getAttribute(Constant.USER_NICK_NAME_SESSION_KEY);
			responseObj = this.recommendService.addUserRecommend(userId,userNickName,recommender);
		} catch (Exception e) {
			log.error("请求关联失败", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "请求关联失败!");
		}
		return responseObj;
	}
	
	@RequestMapping(value = "/recommend/user_add_phone", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> addUserRecommendByPhone( HttpServletRequest request,
			@RequestParam(value = ParameterConstants.USER_AREA_CODE) String areaCode,
	        @RequestParam(value = ParameterConstants.USER_PHONE) String telphone) {
		if (!UserUtil.validateAreaCode(areaCode) &&!UserUtil.validatePhone(telphone)) {
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数无效");
		} 
		ResponseObject<Object> responseObj = null;
		
		try {
			String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			String userNickName = (String)request.getSession().getAttribute(Constant.USER_NICK_NAME_SESSION_KEY);
			responseObj = this.recommendService.addUserRecommendByPhone(userId,userNickName,areaCode,telphone);
		} catch (Exception e) {
			log.error("请求关联失败", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "请求关联失败!");
		}
		return responseObj;
	}
	
	
	
	@RequestMapping(value = "/recommend/accepted", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> acceptedRecommend( HttpServletRequest request,
			@RequestParam(value = "recommender") String recommender) {
		ResponseObject<Object> responseObj = null;
		if (!UserUtil.validateId(recommender)) {
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数无效");
		}
		try {
			String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			responseObj = this.recommendService.acceptedRecommend(userId,recommender);
		} catch (Exception e) {
			log.error("接受请求关联失败", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "接受失败!");
		}
		return responseObj;
	}
	
	@RequestMapping(value = "/recommend/reject", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> rejectRecommend( HttpServletRequest request,
			@RequestParam(value = "recommender") String recommender) {
		ResponseObject<Object> responseObj = null;
		if (!UserUtil.validateId(recommender)) {
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数无效");
		}
		try {
			String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			responseObj = this.recommendService.rejectRecommend(userId,recommender);
		} catch (Exception e) {
			log.error("拒绝请求关联失败", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "拒绝失败!");
		}
		return responseObj;
	}
	
	
	
}
