package com.anyonehelps.service;

import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.DemandAttach;
import com.anyonehelps.model.ResponseObject;


public interface DemandAttachService {

	public ResponseObject<Object> addDemandAttach(DemandAttach demandAttach) throws ServiceException;
	 
	public ResponseObject<DemandAttach> searchById(String id) throws ServiceException;

	public ResponseObject<Object> deleteDemandAttach(String userId,String id) throws ServiceException;

	public ResponseObject<Object> modifyState(String userId, String id,
			String state) throws ServiceException;
}
