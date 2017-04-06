package com.anyonehelps.dao;

import org.apache.ibatis.annotations.Param;

import com.anyonehelps.model.IPRecord;

public interface IPRecordDao {  
	public int insert(IPRecord ipRecord ) throws Exception; 
	public int countByIP(@Param("type") String type, @Param("ip") String ip)throws Exception;
	public int countByPhone(@Param("type") String type, @Param("areaCode") String areaCode, @Param("phone") String phone)throws Exception;

}
