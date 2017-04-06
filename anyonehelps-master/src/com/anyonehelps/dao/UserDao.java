package com.anyonehelps.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anyonehelps.model.User;

public interface UserDao {
	/**
	 * 获取用户开放信息 ，可能会出现循环列锁
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public User getUserOpenInfoById(String id) throws Exception;
	/**
	 * 只获取用户表信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public User getOnlyUserOpenInfoById(String id) throws Exception;
	
	
	public User getUserById(String id) throws Exception;
	public User getUserByAccount(@Param("account") String account) throws Exception;
	public User getUserByPhone(@Param("areaCode") String areaCode, @Param("phone")String phone) throws Exception;
	/**
	 *  根据微信id 查找用户
	 * @param wxId 微信id
	 * @return user
	 * @throws Exception
	 */
	public User getUserByWxId(@Param("wxId") String wxId) throws Exception;
	/**
	 * 根据facebook id 查找用户
	 * @param fbId
	 * @return user
	 * @throws Exception
	 */
	public User getUserByFbId(@Param("fbId") String fbId) throws Exception;
	public int insertUser(User user) throws Exception;
	public int updateUserById(User user) throws Exception;
	public int deleteUserByIds(List<String> list) throws Exception;
	//public List<User> searchUserByKey(@Param("userId") String userId, @Param("index") int index, @Param("size") int size) throws Exception;
	//public int countByKey(@Param("userId") String userId) throws Exception;
	
	public int updatePassword(@Param("id") String id, @Param("areaCode") String areaCode, @Param("phone") String phone, @Param("password") String password,
	        @Param("oldpwd") String oldpwd) throws Exception;
	
	public int updatePasswordByPhone(@Param("areaCode") String areaCode, @Param("phone") String phone, @Param("password") String password) throws Exception;
	public int updatePasswordByEmail(@Param("id") String id, @Param("email") String phone, @Param("password") String password,
	        @Param("oldpwd") String oldpwd) throws Exception;
	public int updateUserPhoneById(@Param("userId") String userId, @Param("areaCode") String areaCode, @Param("telphone") String telphone,
			 @Param("telphoneState") String telphoneState) throws Exception;
	public int updateUserEmailById(@Param("userId") String userId,@Param("email")  String email,
			@Param("emailState") String emailState) throws Exception;
	
	public int updateEvaluateById(@Param("userId") String userId, @Param("evaluate")  String evaluate, @Param("honest")  String honest, @Param("quality")  String quality, @Param("punctual")  String punctual) throws Exception;
	
	public int updatePublishEvaluateById(@Param("userId") String userId, @Param("evaluatePublish") String evaluatePublish, @Param("honestPublish")  String honestPublish) throws Exception;
	
	public int updatePicUrlById(@Param("userId") String userId,@Param("picUrl")  String picUrl) throws Exception;
	
	
	public int getCountUserBySumAmount(@Param("key") String key) throws Exception;
	
	public List<User> getUserBySumAmount(@Param("key") String key, @Param("index") int index, @Param("size") int size) throws Exception;
	
	public int countUserByFamousEnable(@Param("key") String key) throws Exception;
	
	public List<User> getUserByFamousEnable(@Param("key") String key, @Param("index") int index, @Param("size") int size) throws Exception;
	
	
	public List<User> searchUserByRecommender(@Param("recommender") String recommender, @Param("index") int index, @Param("size") int size) throws Exception;
	public int countByRecommender(@Param("recommender") String recommender) throws Exception;

	public int getUserIsOpen(@Param("userId") String userId,@Param("friendUserId") String friendUserId) throws Exception;
	public User getUserOpenInfoByAccount(@Param("account") String account) throws Exception;
	
	
	public int countUserByKey(@Param("key") String key,@Param("nationality") String nationality, @Param("specialtyType") String specialtyType, @Param("specialtyId") String specialtyId) throws Exception;
	public List<User> searchUserByKey(@Param("key") String key, @Param("sortType") String sortType, @Param("nationality") String nationality,
			@Param("specialtyType") String specialtyType, @Param("specialtyId") String specialtyId, @Param("index") int index, @Param("size") int pageSize) throws Exception;
	
	public int modifyRecommender(@Param("recommender") String recommender,@Param("userId") String userId) throws Exception;
	public int modifyPhoneById(@Param("userId") String userId, @Param("areaCode") String areaCode, @Param("telphone") String telphone) throws Exception;
	public int modifyEmailById(@Param("userId") String userId, @Param("email") String email) throws Exception;
	
	public int countProUserByKey(@Param("key") String key, @Param("proTypeId") String proTypeId,
			@Param("proId") String proId) throws Exception;
	public List<User> searchProUserByKey(@Param("key") String key, @Param("proTypeId") String proTypeId,
			@Param("proId") String proId, @Param("index") int index, @Param("size") int size) throws Exception;
	
	public int modifyGradeById(@Param("userId") String userId, @Param("grade")  String grade) throws Exception;
	
	
	public int updateCoverUrlById(@Param("userId") String userId,@Param("coverUrl")  String coverUrl) throws Exception; //haokun added 2017/02/24
}
