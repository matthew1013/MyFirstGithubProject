package com.anyonehelps.dao;

import org.apache.ibatis.annotations.Param;

import com.anyonehelps.model.Admin;

public interface AdminDao {
	
	public Admin getByEmail(@Param("email") String email) throws Exception;
	
}
