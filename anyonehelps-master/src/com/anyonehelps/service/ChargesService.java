package com.anyonehelps.service;

import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.Charges;
import com.anyonehelps.model.ResponseObject;


public interface ChargesService {

	public ResponseObject<Charges> searchById() throws ServiceException;
}
