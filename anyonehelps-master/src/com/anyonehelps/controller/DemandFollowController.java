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
import com.anyonehelps.common.util.StringUtil;
import com.anyonehelps.model.DemandFollow;
import com.anyonehelps.model.PageSplit;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.service.DemandFollowService;

@Controller
public class DemandFollowController extends BasicController {

	private static final long serialVersionUID = -6449578974919047517L;

	private static final Logger log = Logger.getLogger(DemandFollowController.class);

	@Resource(name = "demandFollowService")
	private DemandFollowService demandFollowService;
	
	@Value(value = "${page_size}")
	private int defaultPageSize;
	
	@RequestMapping(value = "/demand_follow/add", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> addDemandFollow( HttpServletRequest request,
			@RequestParam(value = "id") String id) {
		ResponseObject<Object> responseObj = null;
		if (StringUtil.isEmpty(id)) {
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数无效");
		}
		try {
			String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			responseObj = this.demandFollowService.addDemandFollows(userId, id);
		} catch (Exception e) {
			log.error("关注任务失败", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "关注任务失败!");
		}
		return responseObj;
	}
	
	@RequestMapping(value = "/demand_follow/search", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseObject<PageSplit<DemandFollow>> searchDemandFollowByKey(HttpServletRequest request,
			@RequestParam(value = ParameterConstants.COMMON_SEARCH_KEY, required = false, defaultValue = "") String key,
			@RequestParam(value = ParameterConstants.COMMON_PAGESPLIT_PAGE_INDEX, required = false, defaultValue = "1") int pageIndex,
			@RequestParam(value = ParameterConstants.COMMON_PAGESPLIT_PAGE_SIZE, required = false, defaultValue = "10") int pageSize) {
		try {
			key =  new String(key.getBytes("ISO-8859-1"), "utf-8");
			String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			return this.demandFollowService.searchByKey(userId, key, pageSize, pageIndex);
		} catch (Exception e) {
			log.error("获取关注任务列表出现异常", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取关注任务列表出现异常!");
		}
	}
	
	@RequestMapping(value = "/demand_follow/delete", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> deleteDemandFollowById(HttpServletRequest request,
			@RequestParam(value = "ids", required = true) String[] ids) {
		try {
			String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			return this.demandFollowService.deleteDemanFollows(userId, ids);
		} catch (Exception e) {
			log.error("取消关注任务出现异常", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "取消关注任务出现异常!");
		}
	}
	
}
