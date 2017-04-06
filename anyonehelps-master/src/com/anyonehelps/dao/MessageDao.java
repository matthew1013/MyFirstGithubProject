package com.anyonehelps.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anyonehelps.model.Message;


public interface MessageDao {
	public int insert(Message msg) throws Exception;

	//public List<Message> retrieveMessages(@Param("userId") String userId, @Param("index") int index,
	//        @Param("size") int size) throws Exception;

	//public List<Message> retrieveMessagesByParentId(@Param("id") String id) throws Exception;

	public List<Message> searchByKey(@Param("key") String key, @Param("state") String state,
	        @Param("userId") String userId, @Param("index") int index, @Param("size") int size) throws Exception;

	public int countByKey(@Param("key") String key, @Param("state") String state, @Param("userId") String userId)
	        throws Exception;

	public int modifyState(@Param("id") String id, @Param("date") String date, @Param("state") String state)
	        throws Exception;
	
	public int modifySessionState(@Param("userId") String userId, @Param("friendId") String friendId, @Param("date") String date, @Param("state") String state)
	        throws Exception; 
 
	public int countByKeyAndFriendId(@Param("key") String key, @Param("friendId")String friendId, @Param("userId") String userId) throws Exception;

	public List<Message> searchByKeyAndFriendId(@Param("key") String key, @Param("friendId") String friendId,
			@Param("userId") String userId, @Param("index") int startIndex, @Param("size") int pageSize) throws Exception;

	public int deleteByFriendId(@Param("userId") String userId, @Param("friendId") String friendId) throws Exception;

	public void deleteByIds(@Param("userId") String userId, @Param("ids") List<String> ids) throws Exception;
}
