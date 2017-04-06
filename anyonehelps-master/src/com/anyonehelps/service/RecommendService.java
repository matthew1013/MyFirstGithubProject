package com.anyonehelps.service;

import java.util.List;

import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.PageSplit;
import com.anyonehelps.model.ResponseObject;


public interface RecommendService {

	public ResponseObject<Object> addRecommends(String userId,String userNickName,List<String> recommender) throws ServiceException;
	
	public <T> ResponseObject<PageSplit<T>> searchByKey(String userId, String key, String state, int pageSize,
	        int pageNow) throws ServiceException;

	public ResponseObject<Object> modifyModifyDate(String userId,String id) throws ServiceException;

	public ResponseObject<Object> addRecommendByPhone(String userId, String userNickName, String areaCode, String telphone) throws ServiceException;

	public ResponseObject<Object> addUserRecommend(String userId,
			String userNickName, String recommender) throws ServiceException;

	public ResponseObject<Object> addUserRecommendByPhone(String userId,
			String userNickName, String areaCode, String telphone) throws ServiceException;

	public ResponseObject<Object> acceptedRecommend(String userId,
			String recommender) throws ServiceException;

	public ResponseObject<Object> rejectRecommend(String userId,
			String recommender) throws ServiceException;
}
