package com.anyonehelps.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anyonehelps.common.constants.ResponseCode;
import com.anyonehelps.common.util.ExceptionUtil;
import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.PageSplit;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.dao.ActivityDao;
import com.anyonehelps.model.Activity;
import com.anyonehelps.service.ActivityService;

@Service("activityService")
public class ActivityServiceImpl extends BasicService implements ActivityService {
	@Autowired
	private ActivityDao activityDao;

	@Override
	public ResponseObject<PageSplit<Activity>> getActivity(int pageNow,
	        int pageSize) throws ServiceException {
		ResponseObject<PageSplit<Activity>> responseObj = new ResponseObject<PageSplit<Activity>>(ResponseCode.SUCCESS_CODE);
	
		try {
			int rowCount = 0;
			try {
				rowCount = this.activityDao.countByKey();
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException("获取活动总数失败", e);
			}
			
			if (rowCount > 0) {
				pageSize = Math.max(pageSize, 1);
				int pageCount = rowCount / pageSize + (rowCount % pageSize == 0 ? 0 : 1);
				pageNow = Math.min(pageNow, pageCount);
				PageSplit<Activity> pageSplit = new PageSplit<Activity>();
				pageSplit.setPageCount(pageCount);
				pageSplit.setPageNow(pageNow);
				pageSplit.setRowCount(rowCount);
				pageSplit.setPageSize(pageSize);

				int startIndex = (pageNow - 1) * pageSize;
				try {
					
					List<Activity> activitys = this.activityDao.searchByKey(startIndex, pageSize);
					if (activitys != null && !activitys.isEmpty()) {
						for (Activity u : activitys) {
							//去掉不必要的信息

							pageSplit.addData(u);
						}
					}
					
				} catch (Exception e) {
					throw ExceptionUtil.handle2ServiceException("获取活动失败", e);
				}
				responseObj.setData(pageSplit);
			}else{
				responseObj.setMessage("没有活动");
			}
			return responseObj;
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
		
	}
	
	@Override
	public ResponseObject<Activity> getOneActivity(String id) throws ServiceException {
		ResponseObject<Activity> responseObj = new ResponseObject<Activity>(ResponseCode.SUCCESS_CODE);
		try {
             Activity activity = this.activityDao.getById(id);
			 if(activity !=null){
				 activity.setContent(StringEscapeUtils.unescapeHtml4(activity.getContent()));
			 }
			 responseObj.setData(activity);
			 return responseObj;
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
	}
	

}
