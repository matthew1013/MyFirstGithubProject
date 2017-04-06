package com.anyonehelps.service;

import java.util.List;

import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.model.Works;

public interface WorksService {

	public ResponseObject<Object> addWorks(Works works) throws ServiceException;
	
	public ResponseObject<List<Works>> searchByUserId(String userId) throws ServiceException;

	public ResponseObject<Object> deleteWorks(String userId,String id) throws ServiceException;

	public ResponseObject<Object> modifyWorkTitle(String userId, String id,
			String title) throws ServiceException;

}
