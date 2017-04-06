package com.anyonehelps.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anyonehelps.model.ProType;

public interface ProTypeDao {  
	public int insert(ProType proType ) throws Exception;
	public List<ProType> getAllProType() throws Exception;
	public ProType getPTById(@Param("id") String id) throws Exception;
}
