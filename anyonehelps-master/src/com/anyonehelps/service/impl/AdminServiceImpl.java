package com.anyonehelps.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anyonehelps.common.constants.ResponseCode;
import com.anyonehelps.common.util.DateUtil;
import com.anyonehelps.common.util.ExceptionUtil;
import com.anyonehelps.common.util.MD5Util;
import com.anyonehelps.common.util.StringUtil;
import com.anyonehelps.dao.AdminDao;
import com.anyonehelps.dao.LoginInfoDao;
import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.Admin;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.service.AdminService;

@Service("adminService")
public class AdminServiceImpl extends BasicService implements AdminService {
	@Autowired
	private AdminDao adminDao;

	@Autowired
	private LoginInfoDao loginInfoDao;
	
	public Admin getAdminByEmail(String email) throws ServiceException {
		if (StringUtil.isEmpty(email)) {
			return null;
		} else {
			try {
				Admin admin = this.adminDao.getByEmail(email);
				return admin;
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException(e);
			}
		}
	}
	
	public ResponseObject<Admin> checkLogin(String email, String password)
			throws ServiceException {
		ResponseObject<Admin> result = new ResponseObject<Admin>();
		try {
			String pwd = MD5Util.encode(password);
			Admin admin = this.adminDao.getByEmail(email);
		
			if (admin != null && pwd.equals(admin.getPassword())
			        && (email.equals(admin.getEmail()) )) {
				result.setCode(ResponseCode.SUCCESS_CODE);
				result.setData(getSecurityValue(admin));
				try {
					this.loginInfoDao.insertUpdate(admin.getId(), "1", DateUtil.date2String(new Date()));
				} catch (Exception e) {
					// ignore
				}
			} else {
				result.setCode(ResponseCode.USER_LOGIN_ACCOUNT_ERROR);
				result.setMessage("账户或者密码错误");
			}
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException("检验管理员登录失败", e);
		}
		return result;
	}

	
}
