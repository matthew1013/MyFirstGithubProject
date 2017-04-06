package com.anyonehelps.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anyonehelps.model.Evaluate;

public interface EvaluateDao {  
	public int insertEvaluate(Evaluate evaluate) throws Exception;
	public Evaluate getByRDId(@Param("rdid") String rdid) throws Exception;
	public Evaluate getByRDIdReceiver(@Param("rdid") String rdid) throws Exception;
	
	public List<Evaluate> getByUserId(@Param("userId") String userId) throws Exception;
	
}
