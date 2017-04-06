package com.anyonehelps.dao;

import org.apache.ibatis.annotations.Param;

import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.SmsSend;

public interface SmsSendDao {  
	public int insert(SmsSend ss ) throws Exception;
	public SmsSend getOneByState0() throws Exception;
	public int modifyState(@Param("id") String id, @Param("date") String date, @Param("state") String state) throws ServiceException;
	public int modifyFailCount(@Param("id") String id, @Param("date") String date) throws ServiceException;

}
