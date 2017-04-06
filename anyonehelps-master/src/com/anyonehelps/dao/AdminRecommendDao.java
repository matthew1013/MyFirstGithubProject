package com.anyonehelps.dao;

import org.apache.ibatis.annotations.Param;

import com.anyonehelps.model.AdminRecommend;

public interface AdminRecommendDao {  
	public int insert(AdminRecommend adminRecommend ) throws Exception;
	public int getCountByUserId(@Param("userId") String userId, @Param("demandId") String demandId ) throws Exception;
}
