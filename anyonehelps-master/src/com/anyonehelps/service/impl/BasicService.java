package com.anyonehelps.service.impl;

import com.anyonehelps.model.Admin;
import com.anyonehelps.model.User;


public class BasicService {
	

	/**
	 * 处理user对象中的安全属性，比如密码等
	 * 
	 * @param user
	 * @return
	 */
	public User getSecurityValue(User user) {
		user.setPassword(null);
		return user;
	}

	/**
	 * 处理admin对象中的安全属性，比如密码等
	 * 
	 * @param admin
	 * @return
	 */
	public Admin getSecurityValue(Admin admin) {
		admin.setPassword(null);
		return admin;
	}

}
