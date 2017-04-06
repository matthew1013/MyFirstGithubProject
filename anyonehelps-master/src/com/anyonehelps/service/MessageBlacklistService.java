package com.anyonehelps.service;

import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.PageSplit;
import com.anyonehelps.model.ResponseObject;


public interface MessageBlacklistService {

	/**
	 * 根据key分页获取黑名单。
	 * @param key TODO
	 * @param userId
	 * @param pageSize
	 * @param pageNow
	 * 
	 * @param <T>
	 * @return
	 * @throws ServiceException
	 */
	public <T> ResponseObject<PageSplit<T>> searchByUserId(String key, String userId, int pageSize, int pageNow)
	        throws ServiceException;

	/**
	 * 保存黑名单到数据库
	 * @param userId 
	 * @param friendId
	 * @return
	 * @throws ServiceException
	 */
	public ResponseObject<Object> saveMessageBlacklist(String userId, String friendId) throws ServiceException;

	public ResponseObject<Object> delete(String userId, String friendId) throws ServiceException;
}
