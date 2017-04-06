package com.anyonehelps.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anyonehelps.common.constants.ResponseCode;
import com.anyonehelps.common.util.DateUtil;
import com.anyonehelps.common.util.ExceptionUtil;
import com.anyonehelps.common.util.StringUtil;
import com.anyonehelps.dao.FriendDao;
import com.anyonehelps.dao.ProUserDao;
import com.anyonehelps.dao.UserDao;
import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.PageSplit;
import com.anyonehelps.model.ProType;
import com.anyonehelps.model.ProUser;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.model.User;
import com.anyonehelps.service.ProService;

@Service("proService")
public class ProServiceImpl extends BasicService implements ProService {
	@Autowired
	private ProUserDao proUserDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private FriendDao friendDao;

	public ResponseObject<Object> addProUser(ProUser pu) throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>();
		if(pu==null){
			return new ResponseObject<Object>(ResponseCode.PARAMETER_ERROR,"参数无效");
		}
		String date = DateUtil.date2String(new Date());
		pu.setCreateDate(date);
		pu.setModifyDate(date);
		try {
			if(this.proUserDao.getByUserIdAndProId(pu.getUserId(), pu.getProId())!=null){
				return new ResponseObject<Object>(ResponseCode.PARAMETER_ERROR,"您已经提交过该领域的申请！");
			}
			int nR = this.proUserDao.insert(pu);
			if(nR>0){
				responseObj.setCode(ResponseCode.SUCCESS_CODE);
				responseObj.setData(pu);
			}else{
				//进行事务回滚
				throw new Exception();
			}
			
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException("提交失败，请重试！", e);
		}
		return responseObj;
	}

	@Override
	public ResponseObject<List<ProType>> getAllProType()
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseObject<List<ProUser>> getProUser(String userId, String type,
			String state) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseObject<PageSplit<User>> searchProUser(String userId, String key,
			String proTypeId, String proId, int pageSize, int pageNow)
			throws ServiceException {
		try {
			key = StringUtil.escapeStringOfSearchKey(key);  
			int rowCount = 0;
			try {
				rowCount = this.userDao.countProUserByKey(key, proTypeId, proId);
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException("获取大牛个数失败", e);
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
					List<User> users = this.userDao.searchProUserByKey(key, proTypeId, proId, startIndex, pageSize);
					if (users != null && !users.isEmpty()) {
						for (User u : users) {
							u.setFollow(String.valueOf(this.friendDao.countByUIdAndFUId(u.getId(), userId)));
						}
					}
					pageSplit.setDatas(users);
				} catch (Exception e) {
					throw ExceptionUtil.handle2ServiceException("获取大牛列表失败", e);
				}
				responseObj.setData(pageSplit);
			} else {
				responseObj.setMessage("没有大牛");
			}
			return responseObj;
		} catch (ServiceException e) {
			throw e;
		}
	}

	@Override
	public ResponseObject<PageSplit<ProUser>> searchProUserByUserId(String userId,
			int pageSize, int pageNow) throws ServiceException {
		try {
			int rowCount = 0;
			try {
				rowCount = this.proUserDao.countByUserId(userId);
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException("获取申请大 牛记录总数失败", e);
			}

			ResponseObject<PageSplit<ProUser>> responseObj = new ResponseObject<PageSplit<ProUser>>(ResponseCode.SUCCESS_CODE);
			if (rowCount > 0) {
				pageSize = Math.max(pageSize, 1);
				int pageCount = rowCount / pageSize + (rowCount % pageSize == 0 ? 0 : 1);
				pageNow = Math.min(pageNow, pageCount);
				PageSplit<ProUser> pageSplit = new PageSplit<ProUser>();
				pageSplit.setPageCount(pageCount);
				pageSplit.setPageNow(pageNow);
				pageSplit.setRowCount(rowCount);
				pageSplit.setPageSize(pageSize);

				int startIndex = (pageNow - 1) * pageSize;
				try {
					List<ProUser> pus = this.proUserDao.searchByUserId(userId, startIndex, pageSize);
					pageSplit.setDatas(pus);
				} catch (Exception e) {
					throw ExceptionUtil.handle2ServiceException("获取申请大 牛记录失败", e);
				}
				responseObj.setData(pageSplit);
			} else {
				responseObj.setMessage("没有记录");
			}
			return responseObj;
		} catch (ServiceException e) {
			throw e;
		}
	}

	@Override
	public ResponseObject<ProUser> getProUserById(String userId, String id)
			throws ServiceException {
		ResponseObject<ProUser> result = new ResponseObject<ProUser>();
		if (StringUtil.isEmpty(userId)||StringUtil.isEmpty(id)) {
			result.setCode(ResponseCode.PARAMETER_ERROR);
			result.setMessage("参数无效");
			return result;
		} else {
			
			try {
				ProUser pu = this.proUserDao.getById(id);
				if(pu==null){
					result.setCode(ResponseCode.PARAMETER_ERROR);
					result.setMessage("大牛申请不存在！");
					return result;
				}
				if(!userId.equals(pu.getUserId())){
					result.setCode(ResponseCode.PARAMETER_ERROR);
					result.setMessage("大牛申请不属于你！");
					return result;
				}
				result.setCode(ResponseCode.SUCCESS_CODE);
				result.setData(pu);
				return result;
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException(e);
			}
		}
	}


}
