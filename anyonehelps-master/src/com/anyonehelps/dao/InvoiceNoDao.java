package com.anyonehelps.dao;

import org.apache.ibatis.annotations.Param;

import com.anyonehelps.model.InvoiceNo;

public interface InvoiceNoDao {  
	public int modifyStateById(@Param("id") String id,@Param("modifyDate") String modifyDate) throws Exception;
	public InvoiceNo getByType(@Param("type") String type) throws Exception;
}
