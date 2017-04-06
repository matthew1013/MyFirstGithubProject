package com.anyonehelps.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anyonehelps.common.constants.ResponseCode;
import com.anyonehelps.common.util.DateUtil;
import com.anyonehelps.common.util.ExceptionUtil;
import com.anyonehelps.dao.IPRecordDao;
import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.IPRecord;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.service.IPRecordService;

@Service("ipRecordService")
public class IPRecordServiceImpl extends BasicService implements IPRecordService {
	@Autowired
	private IPRecordDao ipRecordDao;

	@Override
	public int countByIP(String type, String ip)
			throws ServiceException {
		try {
			return this.ipRecordDao.countByIP(type, ip);
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
	}
	
	@Override
	public int countByPhone(String type, String areaCod, String phone)
			throws ServiceException {
		try {
			return this.ipRecordDao.countByPhone(type, areaCod, phone);
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
	}

	@Override
	public ResponseObject<Object> addIPRecord(IPRecord ipRecord)
			throws ServiceException {
		try {
			ResponseObject<Object> responseObj = new ResponseObject<Object>();
			String date = DateUtil.date2String(new Date());
			ipRecord.setCreateDate(date);
			int nResult = this.ipRecordDao.insert(ipRecord);
			if (nResult > 0) {
				responseObj.setCode(ResponseCode.SUCCESS_CODE);
			} else {
				responseObj.setCode(ResponseCode.IPRECORD_INSERT_FAILURE);
			}
			return responseObj;
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
	}
	

}
