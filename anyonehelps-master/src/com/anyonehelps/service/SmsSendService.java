package com.anyonehelps.service;

import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.model.SmsSend;

public interface SmsSendService {
	public ResponseObject<Object> addSmsSend(SmsSend ss) throws ServiceException;
}
