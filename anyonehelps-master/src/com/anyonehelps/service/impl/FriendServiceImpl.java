package com.anyonehelps.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.anyonehelps.dao.FriendDao;
import com.anyonehelps.dao.InviteDao;
import com.anyonehelps.dao.MessageSystemDao;
import com.anyonehelps.dao.UserCommentDao;
import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.Friend;
import com.anyonehelps.model.MessageSystem;
import com.anyonehelps.model.PageSplit;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.model.User;
import com.anyonehelps.service.FriendService;

@Service("friendService")
public class FriendServiceImpl extends BasicService implements FriendService {
	@Autowired
	private FriendDao friendDao;
	@Autowired
	private InviteDao inviteDao;
	@Autowired
	private UserCommentDao userCommentDao;
	@Autowired
	private MessageSystemDao messageSystemDao;
	//@Autowired
	//private UserDao userDao;
	public ResponseObject<Object> addFriends(String userId,String username,String[] ids) throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>();
		if(ids==null||userId==null){
			return new ResponseObject<Object>(ResponseCode.PARAMETER_ERROR,"参数无效");
		}
		try {
			List<Friend> friends = new ArrayList<Friend>();
			//List<String> failRecommend = new ArrayList<String>();
			for(String id:ids){
				Friend f = this.friendDao.getByUIdAndFUId(userId,id);
				if(f == null){
					Friend friend = new Friend();
					friend.setFriendUserId(id);
					friend.setUserId(userId);
					friends.add(friend);
					String date = DateUtil.date2String(new Date());
					MessageSystem ms = new MessageSystem();
					ms.setContent("您好!用户"+username+"关注了您");
					ms.setCreateDate(date);
					ms.setLink("/profile.jsp?userId="+Base64Util.encode(userId));
					ms.setModifyDate(date);
					ms.setTitle("您好!用户"+username+"关注了您");
					ms.setLevel(Constant.MESSAGESYSTEM_LEVEL_INFO);
					ms.setState(Constant.MESSAGESYSTEM_STATE_UNREAD);
					ms.setUserId(id);
					this.messageSystemDao.insertMessage(ms);
				}
			}
			if(!friends.isEmpty()){
				this.friendDao.insertFriends(friends);
			}
			responseObj.setCode(ResponseCode.SUCCESS_CODE);
			//responseObj.setData(failRecommend);
			
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException("关注失败", e);
		}
		return responseObj;
	}
	
	@SuppressWarnings("unchecked")
	public <T> ResponseObject<PageSplit<T>> searchByKey(String userId, String key, String demandId, int pageSize, int pageNow)
	        throws ServiceException {
		try {
			key = StringUtil.escapeStringOfSearchKey(key);  
			int rowCount = 0;
			try {
				rowCount = this.friendDao.countByKey(key,userId);
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException("获取关注总数失败", e);
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
					List<Friend> friends = this.friendDao.searchByKey( key, userId, startIndex, pageSize);
					if (friends != null && !friends.isEmpty()) {
						for (Friend f : friends) {
							if(!StringUtil.isEmpty(demandId)){
								int nResult =  this.inviteDao.getCountByUserId(f.getFriendUserId(), demandId);
								if(nResult>0){
									f.setInvite("1");
								}else{
									f.setInvite("0"); 
								}	
							}
							if(f.getFriendUser()!=null){
								f.getFriendUser().setCommentCount(String.valueOf(this.userCommentDao.countByKey("", f.getFriendUser().getId())));
								f.getFriendUser().setFollowed(String.valueOf(this.friendDao.countByUIdAndFUId(userId, f.getFriendUser().getId())));
							}
							pageSplit.addData((T)f);
						}
					}
				} catch (Exception e) {
					throw ExceptionUtil.handle2ServiceException("获取关注列表失败", e);
				}
				responseObj.setData(pageSplit);
			} else {
				responseObj.setMessage("暂无关注");
			}
			return responseObj;
		} catch (ServiceException e) {
			throw e;
		}
	}

	public ResponseObject<Object> deleteFriends(String userId, String[] ids)
			throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>(ResponseCode.SUCCESS_CODE);
		if (ids == null || ids.length == 0) {
			responseObj.setCode(ResponseCode.PARAMETER_ERROR);
			responseObj.setMessage("参数无效");
			return responseObj;
		}
		try {
			
			List<String> friendIds = Arrays.asList(ids);
			this.friendDao.deleteByIds(userId, friendIds);
			//if (result > 0) {
			responseObj.setCode(ResponseCode.SUCCESS_CODE);
			return responseObj;
			//} else {
				// 进行事务回滚
			//	throw new Exception();
			//}
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException("取消关注失败", e);
		}
	}

	@Override
	public ResponseObject<PageSplit<User>> searchByKeyAndEd(String userId,
			String key, int pageSize, int pageNow) throws ServiceException {
		try {
			key = StringUtil.escapeStringOfSearchKey(key);  
			int rowCount = 0;
			try {
				rowCount = this.friendDao.countByKeyAndEd(key,userId);
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException("获取关注我的人总数失败", e);
			}

			ResponseObject<PageSplit<User>> responseObj = new ResponseObject<PageSplit<User>>(ResponseCode.SUCCESS_CODE);
			if (rowCount > 0) {
				pageSize = Math.max(pageSize, 1);
				int pageCount = rowCount / pageSize + (rowCount % pageSize == 0 ? 0 : 1);
				pageNow = Math.min(pageNow, pageCount);
				PageSplit<User> pageSplit = new PageSplit<User>();
				pageSplit.setPageCount(pageCount);
				pageSplit.setPageNow(pageNow);
				pageSplit.setRowCount(rowCount);
				pageSplit.setPageSize(pageSize);

				int startIndex = (pageNow - 1) * pageSize;
				try {
					List<User> users = this.friendDao.searchByKeyAndEd( key, userId, startIndex, pageSize);
					if (users != null && !users.isEmpty()) {
						for (User u : users) {
							u.setFollow(String.valueOf(this.friendDao.countByUIdAndFUId(u.getId(), userId)));
							u.setCommentCount(String.valueOf(this.userCommentDao.countByKey("", u.getId())));
							pageSplit.addData(u);
						}
					}
				} catch (Exception e) {
					throw ExceptionUtil.handle2ServiceException("获取关注我的人列表失败", e);
				}
				responseObj.setData(pageSplit);
			} else {
				responseObj.setMessage("暂无关注我的人");
			}
			return responseObj;
		} catch (ServiceException e) {
			throw e;
		}
	}


}
