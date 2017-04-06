package com.anyonehelps.dao;

import org.apache.ibatis.annotations.Param;

import com.anyonehelps.model.ActivityUser;

public interface ActivityUserDao {  
	public int insert(ActivityUser activityUser) throws Exception;
	
	public ActivityUser getByUIdAndAId(@Param("userId") String userId,@Param("activityId") String activityId) throws Exception;
	
}
