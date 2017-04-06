package com.anyonehelps.service;

import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.MessageUser;
import com.anyonehelps.model.PageSplit;
import com.anyonehelps.model.ResponseObject;


public interface MessageUserService {
	/**
	 * 保存留言对象到数据库中
	 * 
	 * @param msg
	 *            要保存的留言
	 * @return
	 * @throws ServiceException
	 */
	public ResponseObject<Object> saveMessage(MessageUser msg) throws ServiceException;

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
	
	public ResponseObject<Object> modifyState(String userId,String id,String state) throws ServiceException;
	 
	public ResponseObject<Object> modifyAllReadState(String userId) throws ServiceException;
	
	public ResponseObject<Object> modifyStateByIds(String userId, String[] ids,
			String state) throws ServiceException;
	
}
