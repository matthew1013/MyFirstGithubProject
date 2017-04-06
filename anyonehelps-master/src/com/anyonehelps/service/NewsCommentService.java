package com.anyonehelps.service;

import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.NewsComment;
import com.anyonehelps.model.PageSplit;
import com.anyonehelps.model.ResponseObject;


public interface NewsCommentService {
	public ResponseObject<Object> save(NewsComment nc) throws ServiceException;
	public <T> ResponseObject<PageSplit<T>> searchByNewId(String newId, int pageSize, int pageNow)
	        throws ServiceException;
}
