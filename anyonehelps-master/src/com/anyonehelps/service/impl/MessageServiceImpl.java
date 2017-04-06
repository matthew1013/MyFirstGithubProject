package com.anyonehelps.service.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anyonehelps.common.constants.Constant;
import com.anyonehelps.common.constants.ResponseCode;
import com.anyonehelps.common.util.DateUtil;
import com.anyonehelps.common.util.ExceptionUtil;
import com.anyonehelps.common.util.StringUtil;
import com.anyonehelps.dao.MessageBlacklistDao;
import com.anyonehelps.dao.MessageDao;
import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.Message;
import com.anyonehelps.model.PageSplit;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.service.MessageService;

@Service("messageService")
public class MessageServiceImpl extends BasicService implements MessageService {
	@Autowired
	private MessageDao messageDao;
	@Autowired
	private MessageBlacklistDao messageBlacklistDao;

	/*@SuppressWarnings("unchecked")
	public <T> ResponseObject<PageSplit<T>> getByUserId(String userId, int pageSize, int pageNow)
	        throws ServiceException {
		if (StringUtil.isEmpty(userId)) {
			return new ResponseObject<PageSplit<T>>(ResponseCode.PARAMETER_ERROR, "参数无效");
		}

		try {
			int rowCount = 0;
			try {
				rowCount = this.messageDao.count(userId);
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException("获取留言信息个数失败", e);
			}

			ResponseObject<PageSplit<T>> responseObj = new ResponseObject<PageSplit<T>>(ResponseCode.SUCCESS_CODE);
			if (rowCount > 0) {
				pageSize = Math.max(pageSize, 1);
				int pageCount = rowCount / pageSize + (rowCount % pageSize == 0 ? 0 : 1);
				pageNow = Math.min(pageNow, pageCount);
				PageSplit<T> pageSplit = new PageSplit<T>();
				pageSplit.setPageCount(pageCount);
				pageSplit.setPageNow(pageNow);
				pageSplit.setRowCount(rowCount);
				pageSplit.setPageSize(pageSize);

				int startIndex = (pageNow - 1) * pageSize;
				try {
					List<Message> result = this.messageDao.retrieveMessages(userId, startIndex, pageSize);
					if (result != null && !result.isEmpty()) {
						for (Message t : result) {
							// t.setContent(StringUtil.dealHtmlSpecialCharacters(t.getContent()));
							pageSplit.addData((T) t);
						}
					}
				} catch (Exception e) {
					throw ExceptionUtil.handle2ServiceException("获取留言信息列表失败", e);
				}
				responseObj.setData(pageSplit);
			} else {
				responseObj.setMessage("没有留言");
			}
			return responseObj;
		} catch (ServiceException e) {
			throw e;
		}
	}
	*/
	@SuppressWarnings("unchecked")
	public <T> ResponseObject<PageSplit<T>> searchByUserId(String key, String userId, String state, int pageSize,
	        int pageNow) throws ServiceException {
		try {
			key = StringUtil.escapeStringOfSearchKey(key);
			int rowCount = 0;
			try {
				rowCount = this.messageDao.countByKey(key, state, userId);
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException("获取私信个数失败", e);
			}

			ResponseObject<PageSplit<T>> responseObj = new ResponseObject<PageSplit<T>>(ResponseCode.SUCCESS_CODE);
			if (rowCount > 0) {
				pageSize = Math.max(pageSize, 1);
				int pageCount = rowCount / pageSize + (rowCount % pageSize == 0 ? 0 : 1);
				pageNow = Math.min(pageNow, pageCount);
				PageSplit<T> pageSplit = new PageSplit<T>();
				pageSplit.setPageCount(pageCount);
				pageSplit.setPageNow(pageNow);
				pageSplit.setRowCount(rowCount);
				pageSplit.setPageSize(pageSize);

				int startIndex = (pageNow - 1) * pageSize;
				try {
					List<Message> result = this.messageDao.searchByKey(key, state, userId, startIndex, pageSize);
					if (result != null && !result.isEmpty()) {
						for (Message m : result) {
							pageSplit.addData((T) m);
						}
					}
				} catch (Exception e) {
					throw ExceptionUtil.handle2ServiceException("获取私信列表失败", e);
				}
				responseObj.setData(pageSplit);
			} else {
				responseObj.setMessage("没有私信记录");
			}
			return responseObj;
		} catch (ServiceException e) {
			throw e;
		}
	}
	/*
	public ResponseObject<Map<String, String>> getMessageCount(String userId) throws ServiceException {
		try {  
			List<String> states = new ArrayList<String>();
			states.add(Constant.MESSAGE_STATE_UNREAD);
			states.add(Constant.MESSAGE_STATE_READ);
			
			int totalCount = this.messageDao.countByKey("%", null, userId);
			int nodealCount = this.messageDao.countByKey("%", state, userId);
			Map<String, String> map = new HashMap<String, String>();
			map.put("totalCount", String.valueOf(totalCount));
			map.put("count", String.valueOf(nodealCount));
			ResponseObject<Map<String, String>> responseObject = new ResponseObject<Map<String, String>>(
			        ResponseCode.SUCCESS_CODE);
			responseObject.setData(map);
			return responseObject;
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
	}
	*/

	@Override
	public ResponseObject<Object> saveMessage(String userId, String friendId,
			String content) throws ServiceException {
		String date = DateUtil.date2String(new Date());
		Message msg = new Message();
		msg.setUserId(userId);
		msg.setFriendId(friendId);
		msg.setSenderId(userId);
		msg.setReceiverId(friendId);
		// 处理留言中的特殊字符串
		msg.setContent(StringUtil.dealHtmlSpecialCharacters(content));
		msg.setCreateDate(date);
		msg.setModifyDate(date);
		msg.setState(Constant.MESSAGE_STATE_READ);
		
		Message msg0 = new Message();
		msg0.setUserId(friendId);
		msg0.setFriendId(userId);
		msg0.setSenderId(userId);
		msg0.setReceiverId(friendId);
		// 处理留言中的特殊字符串
		msg0.setContent(StringUtil.dealHtmlSpecialCharacters(content));
		msg0.setCreateDate(date);
		msg0.setModifyDate(date);
		msg0.setState(Constant.MESSAGE_STATE_UNREAD);
		
		try {
			ResponseObject<Object> responseObj = new ResponseObject<Object>();
			int nResult = this.messageDao.insert(msg);
			if (nResult > 0) {
				if(this.messageDao.insert(msg0)>0){
					responseObj.setCode(ResponseCode.SUCCESS_CODE);
				}else{
					throw new Exception();
				}
				
			} else {
				responseObj.setCode(ResponseCode.MESSAGE_INSERT_FAILURE);
				responseObj.setMessage("发送私信失败");
			}
			return responseObj;
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
	}

	@Override
	public ResponseObject<PageSplit<Message>> searchByFriendId(String key,
			String userId, String friendId, int pageSize, int pageIndex)
			throws ServiceException {
		try {
			key = StringUtil.escapeStringOfSearchKey(key);
			int rowCount = 0;
			try {
				rowCount = this.messageDao.countByKeyAndFriendId(key, friendId, userId);
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException("获取私信个数失败", e);
			}

			ResponseObject<PageSplit<Message>> responseObj = new ResponseObject<PageSplit<Message>>(ResponseCode.SUCCESS_CODE);
			if (rowCount > 0) {
				pageSize = Math.max(pageSize, 1);
				int pageCount = rowCount / pageSize + (rowCount % pageSize == 0 ? 0 : 1);
				pageIndex = Math.min(pageIndex, pageCount);
				PageSplit<Message> pageSplit = new PageSplit<Message>();
				pageSplit.setPageCount(pageCount);
				pageSplit.setPageNow(pageIndex);
				pageSplit.setRowCount(rowCount);
				pageSplit.setPageSize(pageSize);

				int startIndex = (pageIndex - 1) * pageSize;
				try {
					List<Message> result = this.messageDao.searchByKeyAndFriendId(key, friendId, userId, startIndex, pageSize);
					Collections.reverse(result);
					if (result != null && !result.isEmpty()) {
						for (Message t : result) {
							pageSplit.addData( t); 
						}
					}
					String date = DateUtil.date2String(new Date());
					this.messageDao.modifySessionState(userId, friendId, date, Constant.MESSAGE_STATE_READ);
				} catch (Exception e) {
					throw ExceptionUtil.handle2ServiceException("获取私信详情失败", e);
				}
				responseObj.setData(pageSplit);
			} else {
				responseObj.setMessage("没有私信记录了");
			}
			return responseObj;
		} catch (ServiceException e) {
			throw e;
		}

	}

	@Override
	public ResponseObject<Object> deleteAll(String userId, String friendId)
			throws ServiceException {
		try {
			ResponseObject<Object> responseObj = new ResponseObject<Object>();
			int nResult = this.messageDao.deleteByFriendId(userId,friendId);
			if (nResult > 0) {
				responseObj.setCode(ResponseCode.SUCCESS_CODE);
			} else {
				responseObj.setCode(ResponseCode.MESSAGE_DELETE_FAILURE);
				responseObj.setMessage("删除私信内容失败,请重试！");
			}
			return responseObj;
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
	}

	@Override
	public ResponseObject<Object> messageDelete(String userId, String[] ids)
			throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>(ResponseCode.SUCCESS_CODE);
		if (ids == null || ids.length == 0) {
			responseObj.setCode(ResponseCode.PARAMETER_ERROR);
			responseObj.setMessage("您没有选中要删除的私信记录");
			return responseObj;
		}
		try {
			
			List<String> messageIds = Arrays.asList(ids);
			this.messageDao.deleteByIds(userId, messageIds);
			responseObj.setCode(ResponseCode.SUCCESS_CODE);
			return responseObj;
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException("删除成功", e);
		}
	}

}
