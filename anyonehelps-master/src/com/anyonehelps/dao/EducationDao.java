package com.anyonehelps.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anyonehelps.model.Education;

public interface EducationDao {  
	public int insertEducation(Education education ) throws Exception;
	public Education getById(@Param("id") String id) throws Exception;
	public List<Education> getByUserId(@Param("userId") String userId) throws Exception;
	public int deleteById(@Param("userId") String userId,@Param("id") String id) throws Exception;
	public int modifyEducation(Education education) throws Exception;
	public int modifyEnclosureName(@Param("id") String id, @Param("enclosureName")String enclosureName) throws Exception;

	public List<Education> getByECS()throws Exception;
	public int modifyECS(@Param("id") String id, @Param("ep") String ep, @Param("eps") String eps) throws Exception;
	
}
