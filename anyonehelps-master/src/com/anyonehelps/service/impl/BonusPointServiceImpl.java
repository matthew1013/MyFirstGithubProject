package com.anyonehelps.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anyonehelps.common.constants.ResponseCode;
import com.anyonehelps.common.util.ExceptionUtil;
import com.anyonehelps.common.util.StringUtil;
import com.anyonehelps.dao.BonusPointDao;
import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.BonusPoint;
import com.anyonehelps.model.PageSplit;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.service.BonusPointService;

@Service("bonusPointService")
public class BonusPointServiceImpl extends BasicService implements BonusPointService {
	@Autowired
	private BonusPointDao bonusPointDao;

	@SuppressWarnings("unchecked")
	public <T> ResponseObject<PageSplit<T>> searchByKey(String userId, String key, String sdate,
			String edate, int pageSize, int pageNow)
	        throws ServiceException {
		try {
			key = StringUtil.escapeStringOfSearchKey(key);  
			int rowCount = 0;
			try {
				rowCount = this.bonusPointDao.countByKey(key,sdate,edate,userId);
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException("获取奖励总数失败", e);
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
					List<BonusPoint> bonusPoints = this.bonusPointDao.searchByKey( key,sdate,edate,userId,startIndex, pageSize);
					if (bonusPoints != null && !bonusPoints.isEmpty()) {
						for (BonusPoint d : bonusPoints) {
							pageSplit.addData((T)d);
						}
					}
				} catch (Exception e) {
					throw ExceptionUtil.handle2ServiceException("获取奖励列表失败", e);
				}
				responseObj.setData(pageSplit);
			} else {
				responseObj.setMessage("暂无奖励");
			}
			return responseObj;
		} catch (ServiceException e) {
			throw e;
		}
	}

}
