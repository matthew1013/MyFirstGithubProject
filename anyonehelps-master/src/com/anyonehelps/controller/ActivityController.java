package com.anyonehelps.controller;


import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anyonehelps.common.constants.ParameterConstants;
import com.anyonehelps.common.constants.ResponseCode;
import com.anyonehelps.model.PageSplit;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.model.Activity;
import com.anyonehelps.service.ActivityService;

@Controller
public class ActivityController extends BasicController {
	private static final long serialVersionUID = 1562088471869154201L;
	private static final Logger log = Logger.getLogger(NewsController.class);

	@Resource(name = "activityService")
	private ActivityService activityService;
	
	/**
	 * 获取活动
	 * @return
	 */
	@RequestMapping(value = "/activity/search", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseObject<PageSplit<Activity>> getBySumAmount(
			@RequestParam(value = ParameterConstants.COMMON_PAGESPLIT_PAGE_INDEX, required = false, defaultValue = "1") int pageIndex,
			@RequestParam(value = ParameterConstants.COMMON_PAGESPLIT_PAGE_SIZE, required = false, defaultValue = "6") int pageSize
			) {
		try {
			return this.activityService.getActivity(pageIndex, pageSize);
		} catch (Exception e) {
			log.error("获取活动异常", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取活动异常!");
		}
	}
	
	/**
	 * 获取一个活动
	 * @return
	 */
	@RequestMapping(value = "/activity/get_one", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseObject<Activity> getBySumAmount(String id) {
		try {
			return this.activityService.getOneActivity(id);
		} catch (Exception e) {
			log.error("获取活动异常", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取活动异常!");
		}
	}
	
	
}
