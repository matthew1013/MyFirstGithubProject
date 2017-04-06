package com.anyonehelps.controller;

import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anyonehelps.common.constants.Constant;
import com.anyonehelps.common.constants.ResponseCode;
import com.anyonehelps.common.util.DemandAttachUtil;
import com.anyonehelps.model.DemandAttach;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.service.DemandAttachService;
import com.anyonehelps.service.DemandFollowService;

@Controller
public class DemandAttachController extends BasicController {
	private static final long serialVersionUID = -7689887719749338196L;

	private static final Logger log = Logger.getLogger(DemandAttachController.class);

	@Resource(name = "demandFollowService")
	private DemandFollowService demandFollowService;
	
	@Resource(name = "demandAttachService")
	private DemandAttachService demandAttachService;
	
	@RequestMapping(value = "/demand_attach/add", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> addDemandAttach( HttpServletRequest request,
			DemandAttach demandAttach) {
		ResponseObject<Object> responseObj = null;
		if (demandAttach == null) {
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数无效");
		}
		if(!DemandAttachUtil.validateContent(demandAttach.getContent())){
			return new ResponseObject<Object>(ResponseCode.PARAMETER_ERROR,"请输入任务描述");
		}
		if(!DemandAttachUtil.validateDemandId(demandAttach.getDemandId())){
			return new ResponseObject<Object>(ResponseCode.PARAMETER_ERROR,"主任务不存在，请刷新页面再操作");
		}
		if(!DemandAttachUtil.validateAmount(demandAttach.getAmount())){
			return new ResponseObject<Object>(ResponseCode.PARAMETER_ERROR,"附加任务金额不能底于10美元");
		}
		if(!DemandAttachUtil.isValidDateTime(demandAttach.getExpireDate())){
			return new ResponseObject<Object>(ResponseCode.PARAMETER_ERROR,"附加任务截止时间格式不正确，请重新输入");
		}
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = format.parse(demandAttach.getExpireDate());
			if(date.getTime()<=new Date().getTime()){
				responseObj = generateResponseObject(ResponseCode.DEMAND_EXPIRE_DATE_ERROR, "附加任务截止时间小于当前时间，请重新输入!");
				return responseObj;
			}
		} catch (ParseException e) {
			responseObj = generateResponseObject(ResponseCode.DEMAND_EXPIRE_DATE_ERROR, "附加任务截止时间格式不正确，请重新输入!");
			return responseObj;
		}
		
		if( !DemandAttachUtil.validateAchieve(demandAttach.getAchieve())){
			responseObj = generateResponseObject(ResponseCode.DEMANDATTACH_ACHIEVE_ERROR, "附加任务成果要求不正确，请重新输入!");
			return responseObj;
		}
		try {
			String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			demandAttach.setUserId(userId);
			demandAttach.setState(Constant.DEMAND_ATTACH_STATE0);
			demandAttach.setContent(URLDecoder.decode(demandAttach.getContent(), "UTF-8")); 
			demandAttach.setAchieve(URLDecoder.decode(demandAttach.getAchieve(), "UTF-8")); 
			responseObj = this.demandAttachService.addDemandAttach(demandAttach);
		} catch (Exception e) {
			log.error("附加任务添加失败", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "附加任务添加失败,请重试!");
		}
		return responseObj;
	}
	
	@RequestMapping(value = "/demand_attach/get_one", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseObject<DemandAttach> searchById(
			@RequestParam(value = "id", required = true) String id){
		try {
			return this.demandAttachService.searchById(id);
		}catch (Exception e) {
			log.error("获取附加任务出现异常", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取附加任务失败,请重试!");
		}
	}
	
	@RequestMapping(value = "/demand_attach/delete", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> deleteDemandAttachById(HttpServletRequest request,
			@RequestParam(value = "id", required = true) String id) {
		try {
			String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			return this.demandAttachService.deleteDemandAttach(userId, id);
		} catch (Exception e) {
			log.error("删除附加任务出现异常", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "删除任务失败,请重试!");
		}
	}
	
	@RequestMapping(value = "/demand_attach/modify_state", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> modifyStateById(HttpServletRequest request,
			@RequestParam(value = "state", required = true) String state,
			@RequestParam(value = "id", required = true) String id) {
		if(!Constant.DEMAND_ATTACH_STATE1.equals(state)
				&&!Constant.DEMAND_ATTACH_STATE2.equals(state)){
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数无效");
		}
		try {
			String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			return this.demandAttachService.modifyState(userId, id, state);
		} catch (Exception e) {
			log.error("修改任务状态出现异常", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "请求失败,请重试!");
		}
	}
	
}
