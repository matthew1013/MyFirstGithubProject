package com.anyonehelps.dao;

import org.apache.ibatis.annotations.Param;

import com.anyonehelps.model.EmailSubscribe;

public interface EmailSubscribeDao {  
	public int insertOrUpdate(EmailSubscribe es ) throws Exception;
	public int deleteByUserId(@Param("userId") String userId) throws Exception;
	public int countByUserId(@Param("userId") String userId) throws Exception;
}
