package com.anyonehelps.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anyonehelps.common.constants.ResponseCode;
import com.anyonehelps.common.util.DateUtil;
import com.anyonehelps.common.util.ExceptionUtil;
import com.anyonehelps.common.util.StringUtil;
import com.anyonehelps.dao.MessageBlacklistDao;
import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.MessageBlacklist;
import com.anyonehelps.model.PageSplit;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.service.MessageBlacklistService;

@Service("messageBlacklistService")
public class MessageBlacklistServiceImpl extends BasicService implements MessageBlacklistService {
	@Autowired
	private MessageBlacklistDao messageBlacklistDao;

	@SuppressWarnings("unchecked")
	public <T> ResponseObject<PageSplit<T>> searchByUserId(String key, String userId, int pageSize,
	        int pageNow) throws ServiceException {
		try {
			key = StringUtil.escapeStringOfSearchKey(key);
			int rowCount = 0;
			try {
				rowCount = this.messageBlacklistDao.countByKey(key, userId);
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException("获取黑名单个数失败", e);
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
					List<MessageBlacklist> mbs = this.messageBlacklistDao.searchByKey(key, userId, startIndex, pageSize);
					if (mbs != null && !mbs.isEmpty()) {
						for (MessageBlacklist mb : mbs) {
							pageSplit.addData((T) mb);
						}
					}
				} catch (Exception e) {
					throw ExceptionUtil.handle2ServiceException("获取黑名单列表失败", e);
				}
				responseObj.setData(pageSplit);
			} else {
				responseObj.setMessage("没有黑名单记录");
			}
			return responseObj;
		} catch (ServiceException e) {
			throw e;
		}
	}
	

	@Override
	public ResponseObject<Object> saveMessageBlacklist(String userId, String friendId) throws ServiceException {
		String date = DateUtil.date2String(new Date());
		MessageBlacklist mb = new MessageBlacklist();
		mb.setUserId(userId);
		mb.setFriendId(friendId);
		mb.setCreateDate(date);
		
		try {
			ResponseObject<Object> responseObj = new ResponseObject<Object>();
			MessageBlacklist tempMB = this.messageBlacklistDao.getByFriendId(userId, friendId);
			if(tempMB!=null){
				responseObj.setCode(ResponseCode.SUCCESS_CODE);
			}else{
				int nResult = this.messageBlacklistDao.insert(mb);
				if (nResult > 0) {
					responseObj.setCode(ResponseCode.SUCCESS_CODE);
				} else {
					responseObj.setCode(ResponseCode.MESSAGE_BLACKLIST_INSERT_FAILURE);
					responseObj.setMessage("添加黑名单失败,请重试！");
				}
			}
			
			return responseObj;
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
	}


	@Override
	public ResponseObject<Object> delete(String userId, String friendId)
			throws ServiceException {
		try {
			ResponseObject<Object> responseObj = new ResponseObject<Object>();
			MessageBlacklist mb = this.messageBlacklistDao.getByFriendId(userId, friendId);
			if(mb!=null){
				int nResult = this.messageBlacklistDao.deleteById(mb.getId());
				if (nResult > 0) {
					responseObj.setCode(ResponseCode.SUCCESS_CODE);
					responseObj.setData(mb);
				} else {
					
					responseObj.setCode(ResponseCode.MESSAGE_BLACKLIST_DELETE_ERROR);
					responseObj.setMessage("删除黑名单失败,请重试！");
				}
			}else{
				responseObj.setCode(ResponseCode.MESSAGE_BLACKLIST_DELETE_ERROR);
				responseObj.setMessage("黑名单不存在，或者已经被删除。请刷新页面再操作！");
			}
			return responseObj;
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
	}

}
