package com.anyonehelps.service;

import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.Message;
import com.anyonehelps.model.PageSplit;
import com.anyonehelps.model.ResponseObject;


public interface MessageService {

	/**
	 * 根据用户id获取该分页获取该用户的留言列表。
	 * 
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	//public <T> ResponseObject<PageSplit<T>> getByUserId(String userId, int pageSize, int pageNow)
	//        throws ServiceException;

	/**
	 * 根据key获取数据和留言状态分页获取留言信息。
	 * @param key TODO
	 * @param userId
	 * @param state
	 * @param pageSize
	 * @param pageNow
	 * 
	 * @param <T>
	 * @return
	 * @throws ServiceException
	 */
	public <T> ResponseObject<PageSplit<T>> searchByUserId(String key, String userId, String state, int pageSize, int pageNow)
	        throws ServiceException;

	/**
	 * 获取留言数量，保存总数量，以及未回复的留言数量
	 * @param userId TODO
	 * @return
	 * @throws ServiceException
	 */
	//public ResponseObject<Map<String, String>> getMessageCount(String userId) throws ServiceException;
	/**
	 * 保存私信到数据库
	 * @param userId 
	 * @param friendId
	 * @param content
	 * @return
	 * @throws ServiceException
	 */
	public ResponseObject<Object> saveMessage(String userId, String friendId,
			String content) throws ServiceException;

	public ResponseObject<PageSplit<Message>> searchByFriendId(String key,
			String userId, String friendId, int pageSize, int pageIndex) throws ServiceException;

	public ResponseObject<Object> deleteAll(String userId, String friendId) throws ServiceException;

	public ResponseObject<Object> messageDelete(String userId, String[] ids) throws ServiceException;
}
