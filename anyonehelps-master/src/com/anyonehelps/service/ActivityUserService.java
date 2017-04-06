package com.anyonehelps.service;

import javax.servlet.http.HttpServletRequest;

import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.model.ActivityUser;


public interface ActivityUserService {

	public ResponseObject<Object> addActivityUser(HttpServletRequest request, ActivityUser activityUser, String userPicDefaultUrl, String clientIp) throws ServiceException;
	
}
