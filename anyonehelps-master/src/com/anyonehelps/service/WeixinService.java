package com.anyonehelps.service;

import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.ResponseObject;

public interface WeixinService {
	ResponseObject<String> scanRecharge(String userId, String amount, String flag) throws ServiceException;

	ResponseObject<Object> checkIfScanPay(String userId, String amount,
			String flag) throws ServiceException;
	 
	ResponseObject<Object> addPreRecharge(String userId, double amount, String payOrderIdsStr) throws ServiceException;

	/**
	 * set appid, mch_id, key from database
	 * @param map
	 * @return
	 * @throws ServiceException
	 */
	//boolean setMapFromDataBase(Map<String, String> map) throws ServiceException;
}
