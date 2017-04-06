package com.anyonehelps.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anyonehelps.model.MessageSystem;


public interface MessageSystemDao {
	public int insertMessage(MessageSystem msg) throws Exception;
	
	public int countByKey(@Param("key") String key, @Param("state") String state, @Param("userId") String userId)
	        throws Exception;
	public List<MessageSystem> searchMessageByKey(@Param("key") String key, @Param("state") String state,
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

	public MessageSystem getById(@Param("id") String id) throws Exception;

	public int modifyAllRead(@Param("userId") String userId, @Param("date") String state) throws Exception;

	public void modifyStateByIds(@Param("userId") String userId, @Param("ids") List<String> ids,
			@Param("state") String state, @Param("date") String date);
	
	public void modifyRecommendState(@Param("userId") String userId, @Param("recommender") String recommender,
			@Param("recommenderState") String recommenderState, @Param("recommenderDate") String recommenderDate);
 
	public void modifyRecommendState2(@Param("userId") String userId, @Param("recommender") String recommender,
			@Param("recommenderState") String recommenderState, @Param("recommenderDate") String recommenderDate);
}
