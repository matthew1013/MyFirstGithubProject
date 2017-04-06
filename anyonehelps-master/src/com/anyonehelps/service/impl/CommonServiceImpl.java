package com.anyonehelps.service.impl;

import java.util.Date;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anyonehelps.common.constants.Constant;
import com.anyonehelps.common.constants.ResponseCode;
import com.anyonehelps.common.util.DateUtil;
import com.anyonehelps.common.util.ExceptionUtil;
import com.anyonehelps.dao.CommonDao;
import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.service.CommonService;

@Service("commonService")
public class CommonServiceImpl implements CommonService {
	@Autowired
	private CommonDao commonDao;

	public ResponseObject<String> checkToken(String email, String token, String type) throws ServiceException {
		try {
			String oldToken = this.getLastToken(email, type);
			if (token.equals(oldToken)) {
				return new ResponseObject<String>(ResponseCode.SUCCESS_CODE);
			} else {
				return new ResponseObject<String>(ResponseCode.TOKEN_ERROR, "Token 无效");
			}
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		} finally {
			try {
				// 将所有的email对应的改成无效状态。
				this.commonDao.updateStatus(email,type);
			} catch (Exception e) {
				// ignore
			}
		}
	}

	public ResponseObject<String> insertToken(String userId, String email, String token, String type) throws ServiceException {
		try {
			// 将所有的email对应的改成无效状态。
			this.commonDao.updateStatus(email,type);
			String date = DateUtil.date2String(new Date());
			int i = this.commonDao.insertToken(userId, email, token, date,type);
			if (i > 0) {
				ResponseObject<String> response = new ResponseObject<String>(ResponseCode.SUCCESS_CODE);
				response.setData(token);
				return response;
			} else {
				return new ResponseObject<String>(ResponseCode.TOKEN_INSERT_ERROR);
			}
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
	}

	public String getLastToken(String email, String type) throws ServiceException {
		String date = DateUtil.long2String(new Date().getTime() - Constant.RESET_PWD_TOKEN_TIME * 1000);
		try {
			return this.commonDao.retrieveToken(email, date, type);
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
	}

	@Override
	public String getUserIdByToken(String token, String type) throws ServiceException {
		try {
			return this.commonDao.getUserIdByToken(token, type);
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
	}
}
