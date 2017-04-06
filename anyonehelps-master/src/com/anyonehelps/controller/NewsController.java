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

import com.anyonehelps.common.constants.ParameterConstants;
import com.anyonehelps.common.constants.ResponseCode;
import com.anyonehelps.model.News;
import com.anyonehelps.model.PageSplit;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.service.NewsService;

@Controller
public class NewsController extends BasicController {
	private static final long serialVersionUID = 6517783699624173105L;

	private static final Logger log = Logger.getLogger(NewsController.class);

	@Resource(name = "newsService")
	private NewsService newsService;
	
	@Value(value = "${page_size}")
	private int defaultPageSize;
	
	@RequestMapping(value = "/news/search", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseObject<PageSplit<News>> searchByKey(HttpServletRequest request,
			@RequestParam(value = ParameterConstants.COMMON_PAGESPLIT_PAGE_INDEX, required = false, defaultValue = "1") int pageIndex,
			@RequestParam(value = ParameterConstants.COMMON_PAGESPLIT_PAGE_SIZE, required = false, defaultValue = "10") int pageSize
			
		) {
		try {
			return this.newsService.searchByKey(pageSize, pageIndex);
		} catch (Exception e) {
			log.error("获取新闻出现异常", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取新闻列表出现异常!");
		}
	}
	@RequestMapping(value = "/news/get_one", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseObject<News> searchById(HttpServletRequest request,
			@RequestParam(value = "id") String newsId ) {
		try {
			return this.newsService.searchById(newsId);
		} catch (Exception e) {
			log.error("获取新闻出现异常 新闻id="+newsId, e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取新闻出现异常!");
		}
	}
}
