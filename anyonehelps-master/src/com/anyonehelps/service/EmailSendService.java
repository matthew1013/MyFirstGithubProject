package com.anyonehelps.service;

import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.EmailSend;
import com.anyonehelps.model.ResponseObject;

public interface EmailSendService {
	public ResponseObject<Object> addEmailSend(EmailSend es) throws ServiceException;
}
