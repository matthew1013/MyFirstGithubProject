package com.anyonehelps.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anyonehelps.common.constants.ResponseCode;
import com.anyonehelps.common.util.ExceptionUtil;
import com.anyonehelps.dao.ChargesDao;
import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.Charges;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.service.ChargesService;

@Service("chargesService")
public class ChargesServiceImpl extends BasicService implements ChargesService {
	@Autowired
	private ChargesDao chargesDao;

	
	@Override
	public ResponseObject<Charges> searchById() throws ServiceException {
		ResponseObject<Charges> result = new ResponseObject<Charges>();
			try {
				Charges charges = this.chargesDao.getOne();
				result.setCode(ResponseCode.SUCCESS_CODE);
				result.setData(charges);
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException(e);
			}
		return result;
	}

}
