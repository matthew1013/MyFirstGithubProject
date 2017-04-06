package com.anyonehelps.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anyonehelps.common.constants.ResponseCode;
import com.anyonehelps.common.util.DateUtil;
import com.anyonehelps.common.util.ExceptionUtil;
import com.anyonehelps.common.util.StringUtil;
import com.anyonehelps.dao.NewsCommentDao;
import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.NewsComment;
import com.anyonehelps.model.PageSplit;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.service.NewsCommentService;

@Service("newsCommentService")
public class NewsCommentServiceImpl extends BasicService implements NewsCommentService {
	//private static final Logger log = Logger.getLogger(MessageUserServiceImpl.class);
	@Autowired
	private NewsCommentDao newsCommentDao;

	public ResponseObject<Object> save(NewsComment nc) throws ServiceException {
		if (nc == null) {
			return new ResponseObject<Object>(ResponseCode.PARAMETER_ERROR, "参数无效");
		}

		try {
			ResponseObject<Object> responseObj = new ResponseObject<Object>();
			// 处理留言中的特殊字符串
			nc.setContent(StringUtil.dealHtmlSpecialCharacters(nc.getContent()));
			// 添加时间
			String date = DateUtil.date2String(new Date());
			nc.setCreateDate(date);

			int iresult = this.newsCommentDao.insert(nc);
			if (iresult > 0) {
				responseObj.setCode(ResponseCode.SUCCESS_CODE);
				responseObj.setData(nc);
			} else {
				responseObj.setCode(ResponseCode.MESSAGEUSER_INSERT_FAILURE);
				responseObj.setMessage("增加评论失败");
			}
			return responseObj;
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public <T> ResponseObject<PageSplit<T>> searchByNewId(String newId, int pageSize,
	        int pageNow) throws ServiceException {
		try {
			int rowCount = 0;
			try {
				rowCount = this.newsCommentDao.countByKey(newId);
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException("获取评论信息个数失败", e);
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
					List<NewsComment> result = this.newsCommentDao.searchByKey(newId, startIndex, pageSize);
					if (result != null && !result.isEmpty()) {
						for (NewsComment nc : result) {
							pageSplit.addData((T) nc);
						}
					}
				} catch (Exception e) {
					throw ExceptionUtil.handle2ServiceException("获取评论信息失败", e);
				}
				responseObj.setData(pageSplit);
			} else {
				responseObj.setMessage("没有评论");
			}
			return responseObj;
		} catch (ServiceException e) {
			throw e;
		}
	}

}
