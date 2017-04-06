package com.anyonehelps.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anyonehelps.model.News;

public interface NewsDao {  
	public List<News> searchByKey(@Param("index") int index, @Param("size") int size) throws Exception;
	public int countByKey() throws Exception;
	public News getById(@Param("id") String newsId) throws Exception;
	public void modifyVisitNum(@Param("id") String newsId) throws Exception;
}
