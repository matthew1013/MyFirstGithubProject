package com.anyonehelps.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anyonehelps.common.constants.ResponseCode;
import com.anyonehelps.common.util.DemandUtil;
import com.anyonehelps.common.util.UserUtil;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.service.AdminRecommendService;

@Controller
public class AdminRecommendController extends BasicController {

	private static final long serialVersionUID = -6449578974919047517L;

	private static final Logger log = Logger.getLogger(AdminRecommendController.class);

	@Resource(name = "adminRecommendService")
	private AdminRecommendService adminRecommendService;
	
	@RequestMapping(value = "/admin/user_recommend", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> addRecommend( HttpServletRequest request,
			@RequestParam(value = "demandId") String demandId,
			@RequestParam(value = "userId") String userId) {
		ResponseObject<Object> responseObj = null;
		if (!UserUtil.validateId(userId)) {
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数无效");
		}
		if (!DemandUtil.validateId(demandId)) {
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数无效");
		}
		try {
			responseObj = this.adminRecommendService.addRecommend(userId, demandId);
		} catch (Exception e) {
			log.error("推荐任务失败", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "推荐任务失败!");
		}
		return responseObj;
	}

	
}
