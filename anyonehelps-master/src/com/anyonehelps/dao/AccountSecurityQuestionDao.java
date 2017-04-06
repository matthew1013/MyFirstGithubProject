package com.anyonehelps.dao;

import org.apache.ibatis.annotations.Param;

import com.anyonehelps.model.AccountSecurityQuestion;

public interface AccountSecurityQuestionDao {  
	public int insert(AccountSecurityQuestion asq ) throws Exception;
	public int getCountByUserId(@Param("userId") String userId) throws Exception;
	public AccountSecurityQuestion getByUserId(@Param("userId") String userId) throws Exception;

}
