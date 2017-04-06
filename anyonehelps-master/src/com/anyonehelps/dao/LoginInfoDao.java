package com.anyonehelps.dao;

import org.apache.ibatis.annotations.Param;

import com.anyonehelps.model.LoginInfo;

public interface LoginInfoDao {
	public int insertUpdate(@Param("id") String id, @Param("type") String type, @Param("date") String date)
	        throws Exception;

	public LoginInfo getById(@Param("id") String id) throws Exception;

	//public List<LoginInfo> getAll() throws Exception;
}
