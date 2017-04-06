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
import com.anyonehelps.common.util.NewsCommentUtil;
import com.anyonehelps.common.util.StringUtil;
import com.anyonehelps.model.NewsComment;
import com.anyonehelps.model.PageSplit;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.service.NewsCommentService;

@Controller
public class NewsCommentController extends BasicController {
	private static final long serialVersionUID = -3872980921595781142L;

	private static final Logger log = Logger.getLogger(NewsCommentController.class);

	@Resource(name = "newsCommentService")
	private NewsCommentService newsCommentService;

	
	@RequestMapping(value = "/new_comment/add", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> add( HttpServletRequest request,
			NewsComment nc) {
		ResponseObject<Object> responseObj = null;
		if (!NewsCommentUtil.validateContent(nc.getContent())) {
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "评论过长或者过短!");
		}

		if (!NewsCommentUtil.validateParentId(nc.getParentId())) {
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数错误!");
		}
		if (!NewsCommentUtil.validateNewId(nc.getNewId())) {
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数错误!");
		} 

		if (StringUtil.isEmpty(nc.getParentId())) {
			nc.setParentId("-1");
		}
		try {

			String userId = (String) request.getSession().getAttribute(
					Constant.USER_ID_SESSION_KEY);
			nc.setUserId(userId);
			responseObj = this.newsCommentService.save(nc);
			return responseObj;
		} catch (Exception e) {
			log.error("提交评论失败", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "提交评论失败，请重试!");
		}
		
	}
	
	@RequestMapping(value = "/new_comment/search", method = {RequestMethod.GET })
	@ResponseBody
	public ResponseObject<PageSplit<NewsComment>> search(  
	        HttpServletRequest request,
	        @RequestParam(value = ParameterConstants.NEW_ID) String newId,
	        @RequestParam(value = ParameterConstants.COMMON_PAGESPLIT_PAGE_INDEX, required = false, defaultValue = "1") int pageIndex,
	        @RequestParam(value = ParameterConstants.COMMON_PAGESPLIT_PAGE_SIZE, required = false, defaultValue = "10") int pageSize
	        
	        ) {
		try {
			pageIndex = Math.max(pageIndex, 1);
			return this.newsCommentService.searchByNewId(newId, pageSize, pageIndex);
		} catch (Exception e) {
			log.error("获取评论列表出现异常", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取评论列表失败!");
		}
	}
}
