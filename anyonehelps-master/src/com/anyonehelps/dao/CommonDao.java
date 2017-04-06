package com.anyonehelps.dao;

import org.apache.ibatis.annotations.Param;
 
public interface CommonDao {
	public int insertToken(@Param("userId") String userId, @Param("email") String email, @Param("token") String token, @Param("date") String date, @Param("type") String type)
	        throws Exception;

	public String retrieveToken(@Param("email") String email, @Param("date") String date, @Param("type") String type) throws Exception;

	public int updateStatus(@Param("email") String email, @Param("type") String type) throws Exception;
	
	public String getUserIdByToken(@Param("token") String token, @Param("type") String type) throws Exception;
}