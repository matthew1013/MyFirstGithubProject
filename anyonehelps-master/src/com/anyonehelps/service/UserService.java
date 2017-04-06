package com.anyonehelps.service;

import java.util.Map;

import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.PageSplit;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.model.User;


public interface UserService {

	/**
	 * 根据账号，检测数据库中是否存在一样用户名的记录，如果有，则返回true。否则返回false。
	 * 
	 * @param email
	 * @return
	 * @throws ServiceException
	 */
	public boolean checkExistsByEmail(String email) throws ServiceException;
	/**
	 * 根据账号，检测数据库中是否存在一样用户名的记录，如果有，则返回true。否则返回false。
	 * 
	 * @param name
	 * @return
	 * @throws ServiceException
	 */
	public boolean checkExistsByName(String name) throws ServiceException;
	
	/**
	 * 根据账号，检测数据库中是否存在一样用户名的记录，如果有，则返回true。否则返回false。
	 * 
	 * @param name
	 * @return
	 * @throws ServiceException
	 */
	public User getUserByAccount(String account) throws ServiceException;
	
	/**
	 * 根据微信唯一id，检测数据库中是否存在一样用户名的记录，如果有，则返回User。否则返回null。
	 * 
	 * @param wxId
	 * @return
	 * @throws ServiceException
	 */
	public User getUserByWxId(String wxId) throws ServiceException;
	
	/**
	 * 根据facebook唯一id，检测数据库中是否存在一样用户名的记录，如果有，则返回User。否则返回null。
	 * 
	 * @param fbId
	 * @return
	 * @throws ServiceException
	 */
	public User getUserByFbId(String fbId) throws ServiceException;

	/**
	 * 根据账号，检测数据库中是否存在一样手机的记录，如果有，则返回true。否则返回false。
	 * 
	 * @param phone
	 * @return
	 * @throws ServiceException
	 */
	public boolean checkExistsByPhone(String areaCode, String phone) throws ServiceException;

	/**
	 * 根据账号和密码进行登录校验。校验成功返回200.否则返回其他code
	 * 
	 * @param username
	 *            账号，即用户名或者email
	 * @param password
	 * @return
	 * @throws ServiceException
	 */
	public ResponseObject<User> checkLogin(String username, String password, String ip) throws ServiceException;
	
	public ResponseObject<User> checkLoginByPhone(String areaCode,
			String telphone, String password,String ip) throws ServiceException;
	/**
	 * 根据用户id获取用户信息，如果获取成功，并且数据库中有数据，则返回200.否则返回其他code值<br/>
	 * 如果在此过程中出现异常，则抛出ServiceException
	 * 
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public ResponseObject<User> getUserById(String id) throws ServiceException;

	public ResponseObject<Map<String,Object>> getUserOpenInfoById(String id,String selfId) throws ServiceException;
	public ResponseObject<User> getUserOpenInfoByAccount(
			String account, String selfId) throws ServiceException;
	/**
	 * 添加用户user到数据库中去。
	 * 
	 * @param user
	 * @return
	 * @throws ServiceException
	 */
	public ResponseObject<Object> addUser(User user) throws ServiceException;

	/**
	 * 根据id删除用户
	 * 
	 * @param ids
	 * @return
	 * @throws ServiceException
	 */
	public ResponseObject<Object> deleteUserByIds(String[] ids) throws ServiceException;

	/**
	 * 修改user用户数据
	 * 
	 * @param user
	 * @return
	 * @throws ServiceException
	 */
	public ResponseObject<Object> modifyUser(User user) throws ServiceException;
	

	public ResponseObject<Object> modifyPasswordByPhone(String areaCode, String phone, String password)
	        throws ServiceException;
	/**
	 * 修改用户的密码
	 * 
	 * @param id
	 *            TODO
	 * @param email
	 * @param password
	 * @param oldPassword
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public ResponseObject<Object> modifyPasswordByEmail(String id, String email, String password, String oldPassword)throws ServiceException;
	
	/**
	 * 用户修改电话
	 */
	public ResponseObject<Object> modifyUserPhone(String userId, String areaCode, String telphone, String telphoneState)
	        throws ServiceException;
	/**
	 * 用户修改邮箱
	 */
	public ResponseObject<Object> modifyUserEmail(String userId, String email,
			String emailState) throws ServiceException;

	public ResponseObject<Object> modifyPasswordByUser(String id,String password, String oldPassword)throws ServiceException;
	public ResponseObject<PageSplit<User>> getBySumAmount(String userId, String key, int pageNow,
	        int pageSize) throws ServiceException;
	
	/**
	 * 修改头像
	 * @param id
	 * @param userId
	 * @param picUrl
	 * @return
	 * @throws ServiceException
	 */
	public ResponseObject<Object> modifyUserPic(String userId, String picUrl)throws ServiceException;
	
	public <T> ResponseObject<PageSplit<T>> searchByRecommender(String userId,int pageSize,
	        int pageNow) throws ServiceException;
	public <T> ResponseObject<PageSplit<T>> searchUserByKey(String userId, String key, String sortType, String nationality,String specialtyType,
			String specialtyId, int pageSize, int pageNow) throws ServiceException;
	public ResponseObject<User> modifyRecommender(String userId,String account) throws ServiceException;
	public ResponseObject<User> modifyRecommenderByPhone(String userId,
			String areaCode, String telphone) throws ServiceException;
	public ResponseObject<User> getRecommendedByUserId(String userId) throws ServiceException;

	public <T> ResponseObject<PageSplit<T>> adminSearchUserByKey(String key, String sortType, String nationality,String specialtyType,String demandId, 
			int pageSize, int pageNow) throws ServiceException;
	//public ResponseObject<Object> checkSecurityQuestion(String email,
	//		String areaCode, String phone, String index1, String index2,
	//		String index3, String answer1, String answer2, String answer3) throws ServiceException;
	
	//public ResponseObject<Object> checkSecurityQuestionByUserId(String userId,
	//		String index1, String index2, String index3, String answer1,
	//		String answer2, String answer3) throws ServiceException;
	public ResponseObject<Object> modifyPhone(String userId, String areaCode, String telphone) throws ServiceException;
	public ResponseObject<Object> modifyEmail(String userId, String email) throws ServiceException;
	public ResponseObject<Object> emailSubscribe(String userId, String subscribe) throws ServiceException;
	public ResponseObject<Object> modifyPwd(String userId, String password) throws ServiceException;
	
	
	public ResponseObject<Object> modifyUserCover(String userId, String picUrl)throws ServiceException;  /*haokun added 2017/02/23*/
	
}
