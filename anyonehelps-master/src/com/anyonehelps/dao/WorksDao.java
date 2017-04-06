package com.anyonehelps.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anyonehelps.model.Works;

public interface WorksDao {    
	public int insertWorks(Works works ) throws Exception;
	public Works getById(@Param("id") String id) throws Exception;
	public List<Works> getByUserId(@Param("userId") String userId) throws Exception;
	public int deleteById(@Param("userId") String userId,@Param("id") String id) throws Exception;
	public int countByUserId(@Param("userId") String userId) throws Exception;
	public int modifyTitle(@Param("id")String id, @Param("title")String title) throws Exception;

	public List<Works> getByECS()throws Exception;
	public int modifyECS(@Param("id") String id, @Param("up") String wp, @Param("ups") String wps) throws Exception;
	
}
