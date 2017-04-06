package com.anyonehelps.service;

import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.Admin;
import com.anyonehelps.model.ResponseObject;


public interface AdminService {

	/**
	 * 根据账号，检测数据库中是否存在一样用户名的记录，如果有，则返回true。否则返回false。
	 * 
	 * @param name
	 * @return
	 * @throws ServiceException
	 */
	public Admin getAdminByEmail(String email) throws ServiceException;

	
	/**
	 * 根据账号和密码进行登录校验。校验成功返回200.否则返回其他code
	 * 
	 * @param email
	 *            账号，即email
	 * @param password
	 * @return
	 * @throws ServiceException
	 */
	public ResponseObject<Admin> checkLogin(String email, String password) throws ServiceException;

}
