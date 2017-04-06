package com.anyonehelps.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anyonehelps.common.constants.Constant;
import com.anyonehelps.common.constants.ResponseCode;
import com.anyonehelps.common.util.Base64Util;
import com.anyonehelps.common.util.DateUtil;
import com.anyonehelps.common.util.ExceptionUtil;
import com.anyonehelps.common.util.StringUtil;
import com.anyonehelps.dao.MessageUserDao;
import com.anyonehelps.dao.UserCommentDao;
import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.MessageUser;
import com.anyonehelps.model.PageSplit;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.model.UserComment;
import com.anyonehelps.service.UserCommentService;

@Service("userCommentService")
public class UserCommentServiceImpl extends BasicService implements UserCommentService {
	//private static final Logger log = Logger.getLogger(MessageUserServiceImpl.class);
	@Autowired
	private UserCommentDao userCommentDao;
	@Autowired
	private MessageUserDao messageUserDao;

	public ResponseObject<Object> saveUserComment(UserComment uc) throws ServiceException {
		if (uc == null) {
			return new ResponseObject<Object>(ResponseCode.PARAMETER_ERROR, "参数无效");
		}

		try {
			ResponseObject<Object> responseObj = new ResponseObject<Object>();
			// 处理留言中的特殊字符串
			uc.setContent(StringUtil.dealHtmlSpecialCharacters(uc.getContent()));
			// 添加时间
			String date = DateUtil.date2String(new Date());
			uc.setCreateDate(date);
			uc.setModifyDate(date);

			int iresult = this.userCommentDao.insert(uc);
			if (iresult > 0) {
				MessageUser mu = new MessageUser();
				mu.setContent(uc.getContent() );
				mu.setCreateDate(date);
				mu.setLink("/profile.jsp?userId="+Base64Util.encode(uc.getUserId())+"&view=tab_1_4#tab_1_4");
				mu.setModifyDate(date);
				mu.setSendUserNick("  "); 
				mu.setSendUserId(uc.getSenderId());
				mu.setState(Constant.MESSAGEUSER_STATE_UNREAD);
				mu.setUserId(uc.getUserId());
				this.messageUserDao.insertMessage(mu);
				
				if(!"-1".equals(uc.getParentId())){
					List<UserComment> ucs = this.userCommentDao.getUserByParentId(uc.getParentId());
					if(ucs!=null){
						for(UserComment tempUC:ucs){
							if(!tempUC.getSenderId().equals(uc.getUserId())&&!tempUC.getSenderId().equals(uc.getSenderId())){
								MessageUser tempMU = new MessageUser();
								tempMU.setContent("你有留言回复，请查看");
								tempMU.setCreateDate(date);
								tempMU.setLink("/profile.jsp?userId="+Base64Util.encode(uc.getUserId())+"&view=tab_1_4#tab_1_4");
								tempMU.setModifyDate(date);
								tempMU.setSendUserId(uc.getSenderId());
								tempMU.setSendUserNick(" ");
								tempMU.setState(Constant.MESSAGEUSER_STATE_UNREAD);
								tempMU.setUserId(tempUC.getSenderId()); 
								this.messageUserDao.insertMessage(tempMU); 
							}
						}
					}
				}
				
				responseObj.setCode(ResponseCode.SUCCESS_CODE);
				responseObj.setData(uc);
			} else {
				responseObj.setCode(ResponseCode.MESSAGEUSER_INSERT_FAILURE);
				responseObj.setMessage("增加留言失败");
			}
			return responseObj;
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public <T> ResponseObject<PageSplit<T>> searchByUserId(String key, String userId, int pageSize,
	        int pageNow) throws ServiceException {
		try {
			key = StringUtil.escapeStringOfSearchKey(key);
			int rowCount = 0;
			try {
				rowCount = this.userCommentDao.countByKey(key, userId);
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
					List<UserComment> result = this.userCommentDao.searchByKey(key, userId, startIndex, pageSize);
					if (result != null && !result.isEmpty()) {
						for (UserComment uc : result) {
							pageSplit.addData((T) uc);
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

}
