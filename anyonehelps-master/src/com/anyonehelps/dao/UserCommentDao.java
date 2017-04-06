package com.anyonehelps.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anyonehelps.model.UserComment;


public interface UserCommentDao {
	public int insert(UserComment uc) throws Exception; 

	public List<UserComment> searchByKey(@Param("key") String key, @Param("userId") String userId, @Param("index") int index, @Param("size") int size) throws Exception;

	public int countByKey(@Param("key") String key, @Param("userId") String userId) throws Exception;
	
	public List<UserComment> searchByParentId(@Param("parentId") String parentId) throws Exception;

	/**
	 * 主要查询回复下的所有用户
	 */
	public List<UserComment> getUserByParentId(@Param("id") String id) throws Exception;
	

}
