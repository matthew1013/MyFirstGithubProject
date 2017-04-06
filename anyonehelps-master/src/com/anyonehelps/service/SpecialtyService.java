package com.anyonehelps.service;

import java.util.List;

import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.model.SpecialtyType;
import com.anyonehelps.model.SpecialtyUser;

public interface SpecialtyService {
	/**
	 * 获取所有的专长类型和类型下的专长
	 * @return
	 * @throws ServiceException
	 */
	public ResponseObject<List<SpecialtyType>> getAllSpecialtyType() throws ServiceException;
	
	public ResponseObject<List<SpecialtyUser>> getSpecialtyUser(String userId, String type, String state) throws ServiceException;
	
	public ResponseObject<Object> deleteSpecialtyUser(String userId,String id) throws ServiceException;

	public ResponseObject<Object> addSpecialtyUser(SpecialtyUser su) throws ServiceException;
	public ResponseObject<Object> authSpecialtyUser(SpecialtyUser su) throws ServiceException;

	public ResponseObject<Object> getSpecialtyUserById(String userId, String id) throws ServiceException;
	
}
