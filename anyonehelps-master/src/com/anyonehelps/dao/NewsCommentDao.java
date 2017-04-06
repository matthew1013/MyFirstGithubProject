package com.anyonehelps.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anyonehelps.model.NewsComment;


public interface NewsCommentDao {
	public int insert(NewsComment nc) throws Exception; 

	public List<NewsComment> searchByKey(@Param("newId") String newId, @Param("index") int index, @Param("size") int size) throws Exception;

	public int countByKey(@Param("newId") String newId) throws Exception;
	
	public List<NewsComment> searchByParentId(@Param("parentId") String parentId) throws Exception;
}
