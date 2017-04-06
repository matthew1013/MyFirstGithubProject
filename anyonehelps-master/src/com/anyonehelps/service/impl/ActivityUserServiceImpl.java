package com.anyonehelps.service.impl;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anyonehelps.common.constants.Constant;
import com.anyonehelps.common.constants.ResponseCode;
import com.anyonehelps.common.util.DateUtil;
import com.anyonehelps.common.util.ExceptionUtil;
import com.anyonehelps.common.util.MD5Util;
import com.anyonehelps.common.util.UserUtil;
import com.anyonehelps.dao.ActivityUserDao;
import com.anyonehelps.dao.UserDao;
import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.model.ActivityUser;
import com.anyonehelps.model.User;
import com.anyonehelps.service.ActivityUserService;
import com.anyonehelps.service.UserService;

@Service("activityUserService")
public class ActivityUserServiceImpl extends BasicService implements ActivityUserService {
	private static final Logger log = Logger.getLogger(ActivityUserServiceImpl.class);
	
	@Autowired
	private ActivityUserDao activityUserDao;

	@Autowired
	private UserDao userDao;
	
	@Resource(name = "userService")
	private UserService userService;
	
	public ResponseObject<Object> addActivityUser(HttpServletRequest request, ActivityUser activityUser, String userPicDefaultUrl, String clientIp) throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>();
		try {
			String date = DateUtil.date2String(new Date());
			
			if(activityUser.getUserId()==null||activityUser.getUserId().equals("")){
				//未登录
				if (!UserUtil.validateNickName(activityUser.getName())) {
					responseObj.setCode(ResponseCode.USER_NICK_NAME_ERROR);
					responseObj.setMessage("用户名允许长度是2-40个字!");
					return responseObj;
				}
				if (!UserUtil.validatePassword(activityUser.getPassword())) {
					responseObj.setCode(ResponseCode.USER_PASSWORD_ERROR);
					responseObj.setMessage("密码允许长度是6-20个字!");
					return responseObj;
				}
				User u = this.userDao.getUserOpenInfoByAccount(activityUser.getEmail());
				if (u==null) {
					// 该email对应的用户不存在
					if (this.userDao.getUserOpenInfoByAccount(activityUser.getName())==null) {
						//用户名不存在
						User user = new User();
						user.setPassword(MD5Util.encode(user.getPassword()));
						user.setSignDate(date);
						
						user.setNickName(activityUser.getName());
						user.setPassword(activityUser.getPassword());
						user.setEmail(activityUser.getEmail());
						user.setEmailState(Constant.USER_EMAIL_STATE0);
						user.setSignIp(clientIp);

						user.setRecommender("-1");
						user.setType(Constant.USER_TYPE_NORMAL);
						user.setAbilityCertificateUrl("");
						user.setCity("");
						user.setCountry("");
						user.setMajor("");
						user.setPicUrl(userPicDefaultUrl);
						user.setProvince("");
						user.setOtherContact("");
						user.setSchool("");
						user.setTelphone("");
						user.setTelphoneState(Constant.USER_TELPHONE_STATE0);
						
						responseObj = this.userService.addUser(user);
						if (responseObj != null && ResponseCode.SUCCESS_CODE.equals(responseObj.getCode())) {
							// 注册成功, 需要直接登录的
							@SuppressWarnings("unchecked")
							String regUserId = ((Map<String, String>)responseObj.getData()).get("id");
							HttpSession session = request.getSession();
							session.setAttribute(Constant.USER_ID_SESSION_KEY, regUserId);
							session.setAttribute(Constant.USER_NICK_NAME_SESSION_KEY, user.getNickName());
							session.setAttribute(Constant.USER_PIC_URL_SESSION_KEY, user.getPicUrl());
							session.setAttribute(Constant.USER_EMAIL_SESSION_KEY, user.getEmail());
							session.setAttribute(Constant.USER_TYPE_SESSION_KEY, user.getType());
							session.setAttribute(Constant.LOGIN_ACCOUNT_TYPE, "0");
							
							activityUser.setNewFlag("1");
							activityUser.setUserId(regUserId);
						}else{
							responseObj.setCode(ResponseCode.SHOW_EXCEPTION);
							responseObj.setMessage("系统异常请重试！");
							return responseObj;
						}
					} else {
						responseObj.setCode(ResponseCode.PARAMETER_ERROR);
						responseObj.setMessage("用户名已存在，请换一个用户名！");
						return responseObj;
					}
				} else {
					//已用帐号，设计帐号id
					activityUser.setUserId(u.getId());
				}
				
			}else{
				//已登录直接报号即可
			}
			
			activityUser.setCreateDate(date);
			/*这里没有检验活动是否存在,过期*/
			ActivityUser au = this.activityUserDao.getByUIdAndAId(activityUser.getUserId(), activityUser.getActivityId());
			if(au==null){
				int nResult = this.activityUserDao.insert(activityUser);
				if(nResult>0){
					responseObj.setCode(ResponseCode.SUCCESS_CODE);
				}else {
					//进行事务回滚
					throw new Exception();
				}
			}else{
				responseObj.setCode(ResponseCode.PARAMETER_ERROR);
				responseObj.setMessage("您已经在本活动报过名了，无需再次报名！");
			}
			
		} catch (Exception e) {
			log.error("报名失败");
			throw ExceptionUtil.handle2ServiceException(e);
		}
		return responseObj;
	}
	
	
}
