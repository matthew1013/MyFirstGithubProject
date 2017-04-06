package com.anyonehelps.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anyonehelps.model.BonusPoint;

public interface BonusPointDao {  
	public int insertBonusPoint(BonusPoint bonusPoint ) throws Exception;
	
	public List<BonusPoint> searchByKey(@Param("key") String key, @Param("sdate") String sdate, @Param("edate") String edate,
			@Param("userId") String userId,@Param("index") int index, @Param("size") int size) throws Exception;
	public int countByKey(@Param("key") String key, @Param("sdate") String sdate, @Param("edate") String edate, @Param("userId") String userId)
	        throws Exception;	
}
