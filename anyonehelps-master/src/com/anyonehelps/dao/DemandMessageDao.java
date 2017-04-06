package com.anyonehelps.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anyonehelps.model.DemandMessage;


public interface DemandMessageDao {
	public int insertMessage(DemandMessage msg) throws Exception;

	public List<DemandMessage> retrieveMessages(@Param("demandId") String demandId) throws Exception;

	public List<DemandMessage> retrieveMessagesByParentId(@Param("id") String id) throws Exception;
	
	/**
	 * 主要查询回复下的所有用户
	 */
	public List<DemandMessage> getUserByParentId(@Param("id") String id) throws Exception;
	
	public int count(@Param("demandId") String demandId) throws Exception;
	public int countByDemandId(@Param("demandId") String demandId) throws Exception;
	
	public int updateModifyDate(@Param("id") String id, @Param("date") String date, @Param("state") String state)
	        throws Exception;
}
