package com.anyonehelps.service;

import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.AccountSecurityQuestion;
import com.anyonehelps.model.ResponseObject;


public interface AccountSecurityQuestionService {
	
	public ResponseObject<Object> addASQ(AccountSecurityQuestion asq) throws ServiceException;

	public ResponseObject<Object> getAsqByUserId(String userId) throws ServiceException;

	public ResponseObject<Object> getAsqByEmailOrPhone(String email,
			String areaCode, String phone)throws ServiceException;

	public ResponseObject<Object> checkSecurityQuestion(String userId,
			String answer1, String answer2, String answer3)throws ServiceException;
	
}


