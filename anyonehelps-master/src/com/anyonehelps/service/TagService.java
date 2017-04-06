package com.anyonehelps.service;

import java.util.List;

import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.model.Tag;

public interface TagService {

	public ResponseObject<Object> addTag(Tag tag) throws ServiceException;
	
	public ResponseObject<List<Tag>> searchAll() throws ServiceException;

	public ResponseObject<Object> deleteById(String id) throws ServiceException;

}
