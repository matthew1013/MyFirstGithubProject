package com.anyonehelps.service;

import java.util.List;

import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.PageSplit;
import com.anyonehelps.model.ProType;
import com.anyonehelps.model.ProUser;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.model.User;

public interface ProService {
	public ResponseObject<List<ProType>> getAllProType() throws ServiceException;
	public ResponseObject<List<ProUser>> getProUser(String userId, String type, String state) throws ServiceException;
	public ResponseObject<Object> addProUser(ProUser pu) throws ServiceException;
	public ResponseObject<PageSplit<User>> searchProUser(String userId, String key,
			String proTypeId, String proId, int pageSize, int pageIndex) throws ServiceException;
	public ResponseObject<PageSplit<ProUser>> searchProUserByUserId(String userId,
			int pageSize, int pageIndex) throws ServiceException;
	public ResponseObject<ProUser> getProUserById(String userId, String id) throws ServiceException;
}
