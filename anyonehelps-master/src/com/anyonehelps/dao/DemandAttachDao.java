package com.anyonehelps.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anyonehelps.model.DemandAttach;

public interface DemandAttachDao {  
	public int insertDemandAttach(DemandAttach demandAttach ) throws Exception;

	public DemandAttach getById(@Param("id") String id) throws Exception;
	
	public List<DemandAttach> getByDemandId(@Param("demandId") String demandId) throws Exception;
	
	public int deleteById(@Param("id") String id) throws Exception;
	
	public int modifyState(@Param("id") String id, @Param("state") String state,@Param("modifyDate") String modifyDate) throws Exception;
	
}
