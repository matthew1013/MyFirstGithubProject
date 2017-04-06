package com.anyonehelps.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anyonehelps.model.SpecialtyType;

public interface SpecialtyTypeDao {  
	public int insertSpecialtyType(SpecialtyType specialtyType ) throws Exception;
	public List<SpecialtyType> getAllSpecialtyType() throws Exception;
	public SpecialtyType getSTById(@Param("id") String id) throws Exception;
}
