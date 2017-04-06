package com.anyonehelps.service;

import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.ResponseObject;


public interface AdminRecommendService {

	public ResponseObject<Object> addRecommend(String userId, String demandId) throws ServiceException;
	
}
