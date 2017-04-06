package com.anyonehelps.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anyonehelps.model.WorkExperience;

public interface WorkExperienceDao {  
	public int insertWE(WorkExperience we ) throws Exception;
	public WorkExperience getById(@Param("id") String id) throws Exception;
	public List<WorkExperience> getByUserId(@Param("userId") String userId) throws Exception;
	public int deleteById(@Param("userId") String userId,@Param("id") String id) throws Exception;
	public int modifyWE(WorkExperience we) throws Exception;
	public int modifyEnclosureName(@Param("id") String id, @Param("enclosureName") String enclosureName) throws Exception;

	public List<WorkExperience> getByECS()throws Exception;
	public int modifyECS(@Param("id") String id, @Param("wep") String wep, @Param("weps") String weps) throws Exception;
	
}
