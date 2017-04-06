package com.anyonehelps.service;

import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.News;
import com.anyonehelps.model.PageSplit;
import com.anyonehelps.model.ResponseObject;


public interface NewsService {
	public <T> ResponseObject<PageSplit<T>> searchByKey(int pageSize,
	        int pageNow) throws ServiceException;

	public ResponseObject<News> searchById(String newsId) throws ServiceException;
}
