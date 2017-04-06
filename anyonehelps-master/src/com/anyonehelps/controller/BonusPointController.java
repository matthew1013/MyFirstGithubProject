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
import com.anyonehelps.model.BonusPoint;
import com.anyonehelps.model.PageSplit;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.service.BonusPointService;

@Controller
public class BonusPointController extends BasicController {
	private static final long serialVersionUID = -3986872516387643972L;

	private static final Logger log = Logger.getLogger(BonusPointController.class);

	@Resource(name = "bonusPointService")
	private BonusPointService bonusPointService;
	
	@Value(value = "${page_size}")
	private int defaultPageSize; 
	
	@RequestMapping(value = "/bonus_point/search", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseObject<PageSplit<BonusPoint>> searchRecommendsByKey(HttpServletRequest request,
			@RequestParam(value = ParameterConstants.COMMON_SEARCH_KEY, required = false, defaultValue = "") String key,
			@RequestParam(value = ParameterConstants.COMMON_SEARCH_SDATE, required = false, defaultValue = "") String sdate,
	        @RequestParam(value = ParameterConstants.COMMON_SEARCH_EDATE, required = false, defaultValue = "") String edate,
			@RequestParam(value = ParameterConstants.COMMON_PAGESPLIT_PAGE_INDEX, required = false, defaultValue = "1") int pageIndex,
			@RequestParam(value = ParameterConstants.COMMON_PAGESPLIT_PAGE_SIZE, required = false, defaultValue = "10") int pageSize) {
		try {
			if (StringUtil.isEmpty(sdate)
					|| !StringUtil.validateExportDate(sdate)) {
				sdate = "";
			} else {
				sdate = StringUtil.transformerDateString(sdate, " 00:00:00");
			}

			if (StringUtil.isEmpty(edate)
					|| !StringUtil.validateExportDate(edate)) {
				edate = "";
			} else {
				edate = StringUtil.transformerDateString(edate, " 23:59:59");
			}

			key =  new String(key.getBytes("ISO-8859-1"), "utf-8");
			String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			return this.bonusPointService.searchByKey(userId, key, sdate,edate ,pageSize, pageIndex);
		} catch (Exception e) {
			log.error("获取奖励列表出现异常", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取奖励列表出现异常!");
		}
	}
	
	
}
