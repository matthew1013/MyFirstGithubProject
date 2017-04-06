package com.anyonehelps.service;

import java.util.List;

import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.model.WorkExperience;

public interface WorkExperienceService {

	public ResponseObject<Object> addWE(WorkExperience we) throws ServiceException;
	
	public ResponseObject<List<WorkExperience>> searchByUserId(String userId) throws ServiceException;

	public ResponseObject<Object> deleteWE(String userId,String id) throws ServiceException;

	public ResponseObject<Object> modifyWE(WorkExperience we) throws ServiceException;
	
	public ResponseObject<Object> modifyEnclosureName(String userId, String id, String enclosureName) throws ServiceException;


}
