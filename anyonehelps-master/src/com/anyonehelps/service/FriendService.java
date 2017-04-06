package com.anyonehelps.service;

import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.PageSplit;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.model.User;


public interface FriendService {

	public ResponseObject<Object> addFriends(String userId, String username, String[] ids) throws ServiceException;
	
	public <T> ResponseObject<PageSplit<T>> searchByKey(String userId, String key, String demandId, int pageSize,
	        int pageNow) throws ServiceException;

	public ResponseObject<Object> deleteFriends(String userId,String[] ids) throws ServiceException;

	public ResponseObject<PageSplit<User>> searchByKeyAndEd(String userId,
			String key, int pageSize, int pageNow) throws ServiceException;
}
