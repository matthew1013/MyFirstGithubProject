package com.anyonehelps.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anyonehelps.common.constants.ResponseCode;
import com.anyonehelps.common.util.DateUtil;
import com.anyonehelps.common.util.ExceptionUtil;
import com.anyonehelps.common.util.StringUtil;
import com.anyonehelps.dao.MessageUserDao;
import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.MessageUser;
import com.anyonehelps.model.PageSplit;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.service.MessageUserService;

@Service("messageUserService")
public class MessageUserServiceImpl extends BasicService implements MessageUserService {
	//private static final Logger log = Logger.getLogger(MessageUserServiceImpl.class);
	@Autowired
	private MessageUserDao messageUserDao;

	public ResponseObject<Object> saveMessage(MessageUser msg) throws ServiceException {
		if (msg == null) {
			return new ResponseObject<Object>(ResponseCode.PARAMETER_ERROR, "参数无效");
		}

		try {
			ResponseObject<Object> responseObj = new ResponseObject<Object>();
			// 处理留言中的特殊字符串
			msg.setContent(StringUtil.dealHtmlSpecialCharacters(msg.getContent()));
			// 添加时间
			String date = DateUtil.date2String(new Date());
			msg.setCreateDate(date);
			msg.setModifyDate(date);

			int iresult = this.messageUserDao.insertMessage(msg);
			if (iresult > 0) {
				responseObj.setCode(ResponseCode.SUCCESS_CODE);
			} else {
				responseObj.setCode(ResponseCode.MESSAGEUSER_INSERT_FAILURE);
				responseObj.setMessage("写入失败");
			}
			return responseObj;
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public <T> ResponseObject<PageSplit<T>> searchByUserId(String key, String userId, String state, int pageSize,
	        int pageNow) throws ServiceException {
		try {
			key = StringUtil.escapeStringOfSearchKey(key);
			int rowCount = 0;
			try {
				rowCount = this.messageUserDao.countByKey(key, state, userId);
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
					List<MessageUser> result = this.messageUserDao.searchMessageByKey(key, state, userId, startIndex, pageSize);
					if (result != null && !result.isEmpty()) {
						for (MessageUser t : result) {
							pageSplit.addData((T) t);
						}
					}
				} catch (Exception e) {
					throw ExceptionUtil.handle2ServiceException("获取留言信息失败", e);
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

	public ResponseObject<Object> modifyState(String userId,String id, String state)
			throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>(ResponseCode.SUCCESS_CODE);
		
		try {
			MessageUser mu = this.messageUserDao.getById(id);
			if(mu==null){
				responseObj.setCode(ResponseCode.MESSAGEUSER_UPDATE_ERROR);
				responseObj.setMessage("没有这个留言信息");
			}
			if(!mu.getUserId().equals(userId)){
				responseObj.setCode(ResponseCode.MESSAGEUSER_UPDATE_ERROR);
				responseObj.setMessage("不是您的留言！");
			}
			String date = DateUtil.date2String(new Date());
			int nResult = this.messageUserDao.updateModifyDate(id, date, state);
			if(nResult>0){
				responseObj.setCode(ResponseCode.SUCCESS_CODE);
				responseObj.setMessage("");
			}else{
				responseObj.setCode(ResponseCode.MESSAGEUSER_UPDATE_ERROR);
				responseObj.setMessage("更新状态失败");
			}
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException("获取留言信息失败", e);
		}
		return responseObj;
	}
	
	public ResponseObject<Object> modifyAllReadState(String userId)
			throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>(ResponseCode.SUCCESS_CODE);
		
		try {
			String date = DateUtil.date2String(new Date());
			this.messageUserDao.modifyAllRead(userId, date);
			responseObj.setCode(ResponseCode.SUCCESS_CODE);
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException("更新状态失败", e);
		}
		return responseObj;
	}
	
	@Override
	public ResponseObject<Object> modifyStateByIds(String userId, String[] ids,
			String state) throws ServiceException {
		
		ResponseObject<Object> responseObj = new ResponseObject<Object>(ResponseCode.SUCCESS_CODE);
		if (ids == null || ids.length == 0) {
			responseObj.setCode(ResponseCode.PARAMETER_ERROR);
			responseObj.setMessage("参数无效");
			return responseObj;
		}
		String date = DateUtil.date2String(new Date());
		
		try {
			
			this.messageUserDao.modifyStateByIds(userId, Arrays.asList(ids), state, date);
			responseObj.setCode(ResponseCode.SUCCESS_CODE);
			return responseObj;
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException("删除失败", e);
		}
	}
}
