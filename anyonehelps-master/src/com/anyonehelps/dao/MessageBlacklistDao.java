package com.anyonehelps.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anyonehelps.model.MessageBlacklist;


public interface MessageBlacklistDao {
	public int insert(MessageBlacklist mb) throws Exception;

	public List<MessageBlacklist> searchByKey(@Param("key") String key, @Param("userId") String userId, 
	        @Param("index") int index, @Param("size") int size) throws Exception;

	public int countByKey(@Param("key") String key, @Param("userId") String userId) throws Exception;
	
	public MessageBlacklist getByFriendId(@Param("userId") String userId, @Param("friendId") String friendId) throws Exception;

	public int deleteById(@Param("id") String id) throws Exception;
	

}
