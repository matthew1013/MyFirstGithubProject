package com.anyonehelps.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anyonehelps.model.Pro;

public interface ProDao {  
	public int insert(Pro Pro ) throws Exception;
	public List<Pro> getByPTId(@Param("proTypeId") String proTypeId) throws Exception;
	public Pro getById(@Param("id") String id) throws Exception;  

	
}
