package com.anyonehelps.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anyonehelps.common.constants.ResponseCode;
import com.anyonehelps.common.util.ExceptionUtil;
import com.anyonehelps.common.util.StringUtil;
import com.anyonehelps.dao.NewsDao;
import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.News;
import com.anyonehelps.model.PageSplit;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.service.NewsService;

@Service("newsService")
public class NewsServiceImpl extends BasicService implements NewsService {
	@Autowired
	private NewsDao newsDao;

	@SuppressWarnings("unchecked")
	public <T> ResponseObject<PageSplit<T>> searchByKey(int pageSize, int pageNow)
	        throws ServiceException {
		try {
			//key = StringUtil.escapeStringOfSearchKey(key);  
			int rowCount = 0;
			try {
				rowCount = this.newsDao.countByKey();
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException("获取新闻总数失败", e);
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
					List<News> news = this.newsDao.searchByKey(startIndex, pageSize);
					if(news!=null){
						for(News n:news){
							//反转义
							n.setContent(StringEscapeUtils.unescapeHtml4(n.getContent()));
						}
					}
					pageSplit.setDatas((List<T>) news);
				} catch (Exception e) {
					throw ExceptionUtil.handle2ServiceException("获取新闻列表失败", e);
				}
				responseObj.setData(pageSplit);
			} else {
				responseObj.setMessage("暂无新闻");
			}
			return responseObj;
		} catch (ServiceException e) {
			throw e;
		}
	}

	@Override
	public ResponseObject<News> searchById(String newsId)
			throws ServiceException {
		ResponseObject<News> result = new ResponseObject<News>();
		if (StringUtil.isEmpty(newsId)||StringUtil.isEmpty(newsId)) {
			result.setCode(ResponseCode.PARAMETER_ERROR);
			result.setMessage("参数无效");
		} else {
			try {
				News news = this.newsDao.getById(newsId);
				if(news!=null){
					news.setVisitNum(String.valueOf(Integer.parseInt(news.getVisitNum())+1));
					//反转义
					news.setContent(StringEscapeUtils.unescapeHtml4(news.getContent()));
					this.newsDao.modifyVisitNum(news.getId());
				}
				result.setCode(ResponseCode.SUCCESS_CODE);
				result.setData(news);
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException(e);
			}
		}
		return result;
	}

}
