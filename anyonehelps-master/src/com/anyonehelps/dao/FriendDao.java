package com.anyonehelps.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anyonehelps.model.Friend;
import com.anyonehelps.model.User;

public interface FriendDao {  
	public int insertFriends(@Param("friends") List<Friend> friends ) throws Exception;

	public Friend getByUIdAndFUId(@Param("userId") String userId,@Param("FUId") String friendUserId) throws Exception;
	
	public List<Friend> searchByKey(@Param("key") String key,
	        @Param("userId") String userId, @Param("index") int index, @Param("size") int size) throws Exception;
	public int countByKey(@Param("key") String key, @Param("userId") String userId)
	        throws Exception;
	
	public int deleteByIds(@Param("userId") String userId, @Param("ids") List<String> ids) throws Exception;
	
	public int countByUIdAndFUId(@Param("friendUserId") String friendUserId, @Param("userId") String userId)
	        throws Exception;
 
	public int countByKeyAndEd(@Param("key") String key, @Param("userId") String userId)throws Exception;

	public List<User> searchByKeyAndEd(@Param("key") String key, @Param("userId") String userId,
			@Param("index") int index, @Param("size") int size) throws Exception;
	 
	public List<Friend> getByFriendId(@Param("friendUserId") String friendUserId) throws Exception;
	
}
