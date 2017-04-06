package com.anyonehelps.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anyonehelps.model.MessageUser;


public interface MessageUserDao {
	public int insertMessage(MessageUser msg) throws Exception;
	
	public int countByKey(@Param("key") String key, @Param("state") String state, @Param("userId") String userId)
	        throws Exception;
	public List<MessageUser> searchMessageByKey(@Param("key") String key, @Param("state") String state,
	        @Param("userId") String userId, @Param("index") int index, @Param("size") int size) throws Exception;
	/**
	 * 未读总数
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public int countUnread(@Param("userId") String userId) throws Exception;

	
	public int updateModifyDate(@Param("id") String id, @Param("date") String date, @Param("state") String state)
	        throws Exception;

	public MessageUser getById(@Param("id") String id) throws Exception;

	public int modifyAllRead(@Param("userId") String userId, @Param("date") String date) throws Exception;
	
	public void modifyStateByIds(@Param("userId") String userId, @Param("ids") List<String> ids,
			@Param("state") String state, @Param("date") String date);
}
