package com.anyonehelps.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anyonehelps.model.Specialty;

public interface SpecialtyDao {  
	public int insertSpecialty(Specialty specialty ) throws Exception;
	public List<Specialty> getSpecialtyBySTId(@Param("specialtyTypeId") String specialtyTypeId) throws Exception;
	public Specialty getSpecialtyById(@Param("id") String id) throws Exception;

	
}
