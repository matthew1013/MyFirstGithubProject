package com.anyonehelps.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anyonehelps.model.Recommend;

public interface RecommendDao {  
	public int insertRecommends(@Param("recommends") List<Recommend> recommends ) throws Exception;
	
	public List<Recommend> searchByKey(@Param("key") String key, @Param("state") String state,
	        @Param("userId") String userId, @Param("index") int index, @Param("size") int size) throws Exception;
	public int countByKey(@Param("key") String key, @Param("state") String state, @Param("userId") String userId)
	        throws Exception;
	/**
	 * 查找还没过期的Recommend
	 * @param recommender 
	 * @return
	 * @throws Exception
	 */
	public Recommend searchByEmail(@Param("email") String email) throws Exception;
	public int updateModifyDate(@Param("modifyDate") String modifyDate,@Param("smsState") String smsState,@Param("id") String id) throws Exception;
	public Recommend getById(@Param("id") String id) throws Exception;
	
	public Recommend getByUserIdAndEmail(@Param("userId") String userId, @Param("email") String email) throws Exception;
	public Recommend getByUserIdAndPhone(@Param("userId") String userId, @Param("areaCode") String areaCode, @Param("telphone") String telphone) throws Exception;

	/**
	 * 状态不为4、5、6
	 * @param userId
	 * @param email
	 * @return
	 * @throws Exception
	 */
	public Recommend getByUserIdAndEmail2(@Param("userId") String userId, @Param("email") String email) throws Exception;
	/**
	 * 状态不为4、5、6
	 * @param userId
	 * @param areaCode
	 * @param telphone
	 * @return
	 * @throws Exception
	 */
	public Recommend getByUserIdAndPhone2(@Param("userId") String userId, @Param("areaCode") String areaCode, @Param("telphone") String telphone) throws Exception;

	
	public Recommend searchByPhone(@Param("areaCode") String areaCode, @Param("telphone") String telphone) throws Exception;
	
	public int modifyState(@Param("modifyDate") String modifyDate,@Param("state") String state,@Param("id") String id) throws Exception;
	
	public int modifyRecommendState(@Param("userId") String userId, @Param("modifyDate") String modifyDate,@Param("state") String state,@Param("recommender") String recommender) throws Exception;
	
}
