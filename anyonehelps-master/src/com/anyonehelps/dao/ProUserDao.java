package com.anyonehelps.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anyonehelps.model.ProUser;

public interface ProUserDao { 
	public int insert(ProUser pro) throws Exception;
	public int modify(ProUser pro) throws Exception;
	public int modifyState(@Param("id") String id,@Param("userId") String userId,@Param("state") String state,@Param("modifyDate") String modifyDate) throws Exception;
	public ProUser getByUserId(@Param("userId") String userId) throws Exception;
	public ProUser getByUserIdAndProId(@Param("userId") String userId, @Param("proId") String proId) throws Exception;
	public List<ProUser> getOpenByUserId(@Param("userId") String userId) throws Exception; 
	
	public int countByAuthorizeFlag1(@Param("userId") String userId) throws Exception;
	
	public int countByUserId(@Param("userId") String userId) throws Exception;
	public List<ProUser> searchByUserId(@Param("userId") String userId, @Param("index") int index, @Param("size") int size) throws Exception;
	
	public ProUser getById(@Param("id") String id) throws Exception;
	
	
	public List<ProUser> getByECS()throws Exception;
	public int modifyECS(@Param("id") String id, @Param("idp") String idp, @Param("idps") String idps,
			@Param("ep") String ep, @Param("eps") String eps, @Param("op") String op, @Param("ops") String ops) throws Exception;
	
}
