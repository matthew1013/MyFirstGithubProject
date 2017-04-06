package com.anyonehelps.dao;

import org.apache.ibatis.annotations.Param;
import java.util.List;
import com.anyonehelps.model.Activity;

public interface ActivityDao { 
	public List<Activity> searchByKey(@Param("index")Integer index, @Param("size")Integer size) throws Exception;
	public Integer countByKey() throws Exception;
	public Activity getById(@Param("id")String id) throws Exception;
	public int modifyVisitNum(@Param("id")String id) throws Exception;
	
}
