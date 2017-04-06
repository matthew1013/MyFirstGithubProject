package com.anyonehelps.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anyonehelps.common.constants.ResponseCode;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.model.Tag;
import com.anyonehelps.service.TagService;

@Controller
public class TagController extends BasicController {
	private static final long serialVersionUID = 4523930062055882981L;

	private static final Logger log = Logger.getLogger(TagController.class);

	@Resource(name = "tagService")
	private TagService tagService;
	
	@RequestMapping(value = "/tag/get_all", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseObject<List<Tag>> searchAll(HttpServletRequest request) {
		try {
			return this.tagService.searchAll();
		} catch (Exception e) {
			log.error("获取所有标签失败！", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取所有标签失败!");
		}
	}
	

}
