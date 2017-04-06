package com.anyonehelps.service;

import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.Activity;
import com.anyonehelps.model.PageSplit;
import com.anyonehelps.model.ResponseObject;

public interface ActivityService {
	public ResponseObject<PageSplit<Activity>> getActivity(int pageNow,
	        int pageSize) throws ServiceException;
	
	public ResponseObject<Activity> getOneActivity(String id) throws ServiceException;
}
