package com.anyonehelps.service;

import java.util.List;

import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.Education;
import com.anyonehelps.model.ResponseObject;

public interface EducationService {

	public ResponseObject<Object> addEducation(Education education) throws ServiceException;
	
	public ResponseObject<List<Education>> searchByUserId(String userId) throws ServiceException;

	public ResponseObject<Object> deleteEducation(String userId,String id) throws ServiceException;

	public ResponseObject<Object> modifyEducation(Education education) throws ServiceException;

	public ResponseObject<Object> modifyEnclosureName(String userId, String id, String enclosureName) throws ServiceException;

}
