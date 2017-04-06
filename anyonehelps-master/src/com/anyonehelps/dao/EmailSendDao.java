package com.anyonehelps.dao;

import org.apache.ibatis.annotations.Param;

import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.EmailSend;

public interface EmailSendDao {  
	public int insert(EmailSend es ) throws Exception;
	public EmailSend getOneByState0() throws Exception;
	public int modifyState(@Param("id") String id, @Param("date") String date, @Param("state") String state) throws ServiceException;
	public int modifyFailCount(@Param("id") String id, @Param("date") String date) throws ServiceException;
}
