package com.anyonehelps.service;

import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.PageSplit;
import com.anyonehelps.model.ResponseObject;


public interface DemandFollowService {

	public ResponseObject<Object> addDemandFollows(String userId, String id) throws ServiceException;
	
	public <T> ResponseObject<PageSplit<T>> searchByKey(String userId, String key, int pageSize,
	        int pageNow) throws ServiceException;

	public ResponseObject<Object> deleteDemanFollows(String userId,String[] ids) throws ServiceException;
}
