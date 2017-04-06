package com.anyonehelps.service;

import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.PageSplit;
import com.anyonehelps.model.ResponseObject;


public interface BonusPointService {

	public <T> ResponseObject<PageSplit<T>> searchByKey(String userId, String key, String sdate,
			String edate, int pageSize, int pageNow) throws ServiceException;

}
