package com.anyonehelps.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anyonehelps.common.constants.ResponseCode;
import com.anyonehelps.common.util.ExceptionUtil;
import com.anyonehelps.common.util.StringUtil;
import com.anyonehelps.dao.DemandDao;
import com.anyonehelps.dao.DemandFollowDao;
import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.Demand;
import com.anyonehelps.model.DemandFollow;
import com.anyonehelps.model.PageSplit;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.service.DemandFollowService;

@Service("demandFollowService")
public class DemandFollowServiceImpl extends BasicService implements DemandFollowService {
	@Autowired
	private DemandFollowDao demandFollowDao;
	@Autowired
	private DemandDao demandDao;
	public ResponseObject<Object> addDemandFollows(String userId,String id) throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>();
		if(id==null||userId==null){
			return new ResponseObject<Object>(ResponseCode.PARAMETER_ERROR,"参数无效");
		}
		try {
			List<DemandFollow> demandFollows = new ArrayList<DemandFollow>();
			DemandFollow df = this.demandFollowDao.getByUIdAndDFId(userId, id);
			if(df == null){
				Demand demand = this.demandDao.getDemandById(id);
				if(demand == null){
					return new ResponseObject<Object>(ResponseCode.DEMAND_FOLLOW_FAIL,"任务不存在，请刷新页面再操作！");
				}
				
				if(demand.getUserId().equals(userId)){
					return new ResponseObject<Object>(ResponseCode.PARAMETER_ERROR,"无需关注自己发布的任务！");
				}
				
				DemandFollow demandFollow = new DemandFollow();
				demandFollow.setDemandId(id);
				demandFollow.setUserId(userId);
				demandFollows.add(demandFollow);
			}else{
				return new ResponseObject<Object>(ResponseCode.DEMAND_FOLLOW_EXISTS,"您已经关注了该任务！");
			}
			if(!demandFollows.isEmpty()){
				int nResult = this.demandFollowDao.insertDemandFollows(demandFollows);
				if(nResult>0){
					responseObj.setCode(ResponseCode.SUCCESS_CODE);
				}else{
					responseObj.setCode(ResponseCode.DEMAND_FOLLOW_FAIL);
					responseObj.setMessage("关注任务失败，请重试！");
				}
			}
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException("关注任务失败", e);
		}
		return responseObj;
	}
	
	@SuppressWarnings("unchecked")
	public <T> ResponseObject<PageSplit<T>> searchByKey(String userId, String key, int pageSize, int pageNow)
	        throws ServiceException {
		try {
			key = StringUtil.escapeStringOfSearchKey(key);  
			int rowCount = 0;
			try {
				rowCount = this.demandFollowDao.countByKey(key,userId);
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException("获取关注任务总数失败", e);
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
					List<DemandFollow> demandFollows = this.demandFollowDao.searchByKey( key, userId, startIndex, pageSize);
					if (demandFollows != null && !demandFollows.isEmpty()) {
						for (DemandFollow df : demandFollows) {
							pageSplit.addData((T)df);
						}
					}
				} catch (Exception e) {
					throw ExceptionUtil.handle2ServiceException("获取关注任务列表失败", e);
				}
				responseObj.setData(pageSplit);
			} else {
				responseObj.setMessage("暂无关注任务");
			}
			return responseObj;
		} catch (ServiceException e) {
			throw e;
		}
	}

	public ResponseObject<Object> deleteDemanFollows(String userId, String[] ids)
			throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>(ResponseCode.SUCCESS_CODE);
		if (ids == null || ids.length == 0) {
			responseObj.setCode(ResponseCode.PARAMETER_ERROR);
			responseObj.setMessage("参数无效");
			return responseObj;
		}
		try {
			
			List<String> friendIds = Arrays.asList(ids);
			this.demandFollowDao.deleteByIds(userId, friendIds);
			responseObj.setCode(ResponseCode.SUCCESS_CODE);
			return responseObj;
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException("删除关注任务失败", e);
		}
	}


}
