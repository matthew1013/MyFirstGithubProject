package com.anyonehelps.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anyonehelps.model.DemandSpecialty;

public interface DemandSpecialtyDao {  
	public int insert(List<DemandSpecialty> list ) throws Exception; 
	public List<DemandSpecialty> getByDemandId(@Param("demandId") String demandId) throws Exception;
	public int deleteByDemandId(@Param("demandId") String demandId) throws Exception;
	
}
