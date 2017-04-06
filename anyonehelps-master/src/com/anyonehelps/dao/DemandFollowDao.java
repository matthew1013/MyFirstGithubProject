package com.anyonehelps.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anyonehelps.model.DemandFollow;

public interface DemandFollowDao {  
	public int insertDemandFollows(@Param("demandFollows") List<DemandFollow> demandFollows ) throws Exception;

	public DemandFollow getByUIdAndDFId(@Param("userId") String userId,@Param("DFId") String FemandFollowId) throws Exception;
	
	public List<DemandFollow> searchByKey(@Param("key") String key,
	        @Param("userId") String userId, @Param("index") int index, @Param("size") int size) throws Exception;
	public int countByKey(@Param("key") String key, @Param("userId") String userId) throws Exception;
	
	public int deleteByIds(@Param("userId") String userId, @Param("ids") List<String> ids) throws Exception;
	
	public int countByUserIdAndDemandId(@Param("demandId") String demandId, @Param("userId") String userId)
	        throws Exception;

	public List<DemandFollow> getByDemandId(@Param("demandId") String demandId) throws Exception;
}
