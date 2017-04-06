package com.anyonehelps.service;

import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.PageSplit;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.model.UserComment;


public interface UserCommentService {
	public ResponseObject<Object> saveUserComment(UserComment uc) throws ServiceException;
	public <T> ResponseObject<PageSplit<T>> searchByUserId(String key, String userId, int pageSize, int pageNow)
	        throws ServiceException;
}
