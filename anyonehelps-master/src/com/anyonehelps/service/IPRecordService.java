package com.anyonehelps.service;

import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.IPRecord;
import com.anyonehelps.model.ResponseObject;

public interface IPRecordService {

	public int countByIP(String type, String ip) throws ServiceException;
	
	public int countByPhone(String type, String areaCod, String phone) throws ServiceException;
	
	public ResponseObject<Object> addIPRecord(IPRecord ipRecord) throws ServiceException;
}
