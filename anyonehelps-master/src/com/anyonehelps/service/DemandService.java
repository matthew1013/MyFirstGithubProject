package com.anyonehelps.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.Demand;
import com.anyonehelps.model.DemandMessage;
import com.anyonehelps.model.Evaluate;
import com.anyonehelps.model.PageSplit;
import com.anyonehelps.model.ReceiveDemand;
import com.anyonehelps.model.ResponseObject;


public interface DemandService {

	/**
	 * 添加需求到数据库中去。
	 * 
	 * @param 
	 * @return
	 * @throws ServiceException
	 */
	public ResponseObject<Object> addDemand(Demand demand) throws ServiceException;
	/**
	 * 在column字段上根据key查询进行模糊查询
	 * 
	 * @param userId
	 *            TODO
	 * @param key
	 * @param searchColumn
	 * @param pageSize
	 * @param pageNow
	 * @param <T>
	 * @return
	 * @throws ServiceException
	 */
	public <T> ResponseObject<PageSplit<T>> searchByKey(String userId, String key,List<String>states, int pageSize,
	        int pageNow) throws ServiceException;
	
	/**
	 * 在column字段上根据key查询进行模糊查询
	 * @param sortType 排序类型
	 * @param key
	 * @param searchColumn
	 * @param pageSize
	 * @param pageNow
	 * @param <T>
	 * @return
	 * @throws ServiceException
	 */
	public <T> ResponseObject<PageSplit<T>> searchAllByKey(String sortType,String key,String nationality,
			String type,String typeSecond, String minAmount, String maxAmount, List<String> tags, int pageSize, int pageNow) throws ServiceException;
	
	public ResponseObject<Demand> getDemandById(String userId, String id) throws ServiceException;
	
	public ResponseObject<Demand> getDemandByIdOfWriter(String id,String userId) throws ServiceException;
	
	
	/**
	 * 添加接单任务到数据库中去。
	 * 
	 * @param 
	 * @return
	 * @throws ServiceException
	 */
	public ResponseObject<Object> addReceiveDemand(ReceiveDemand receiveDemand) throws ServiceException;
	
	public ResponseObject<List<ReceiveDemand>> searchByReceiveDemand(
			String userId, String id)throws ServiceException;
	public ResponseObject<Object> selectDemand(String userId, String demandId, String rdId)throws ServiceException;
	public ResponseObject<Object> startDemand(String userId, String nickName, String demandId)throws ServiceException;
	public ResponseObject<Object> finishDemandByReceiver(String userId, String nickName, ReceiveDemand rd)throws ServiceException;
	public ResponseObject<Object> finishDemand(String userId, String nickName, String demandId)throws ServiceException;
	public ResponseObject<Object> closeDemand(String userId, String demandId)throws ServiceException;
	/**
	 * 发布者向做任务者评分
	 * @param userId
	 * @param receiveDemandId
	 * @param evaluate
	 * @param describe
	 * @return
	 * @throws ServiceException
	 */
	public ResponseObject<Object> evaluate(String userId, Evaluate evaluate)throws ServiceException;
	/**
	 * 做任务者向发布者评分
	 * @param userId
	 * @param receiveDemandId
	 * @param evaluate
	 * @param describe
	 * @return
	 * @throws ServiceException
	 */
	//public ResponseObject<Object> evaluatePublish(String userId, String receiveDemandId, String evaluate,String describe)throws ServiceException;
	public ResponseObject<Object> getDemandSumAmountByState(List<String> states)throws ServiceException;
	public <T> ResponseObject<PageSplit<T>> searchByReceiverId(String userId, List<String>states, int pageSize, int pageNow)throws ServiceException;
	
	/**
	 * 添加留言。
	 * @param 
	 * @return
	 * @throws ServiceException
	 */
	public ResponseObject<Object> addDemandMessage(DemandMessage demandMessage) throws ServiceException;
	public ResponseObject<Map<String, Object>> getSumAmountAndCountFinish(
			String userId) throws ServiceException;
	public ResponseObject<Set<String>> searchDemandAllRegion(String region) throws ServiceException;
	public ResponseObject<Object> modifyDemand(String nickName, Demand demand) throws ServiceException;
	public ResponseObject<Object> modifyDemandRemark(String userId, String id,
			String remark) throws ServiceException;
	public ResponseObject<Object> modifyDemandRemarkAdmin(String id,
			String remarkAdmin) throws ServiceException;
	 
	/*public ResponseObject<Object> selectReceiveDemand(String userId,
			String demandId, String id) throws ServiceException;
	*/
	public ResponseObject<Object> modifyReceiveDemandRemark(String userId, String id,
			String remark) throws ServiceException;
	public ResponseObject<Object> payDemand(String userId, String nickName,
			ReceiveDemand rd) throws ServiceException;
	public ResponseObject<Object> agreePay(String userId, String nickName, String demandId) throws ServiceException;
	public ResponseObject<Object> searchDemandByIdForReceiver(String userId,
			String demandId) throws ServiceException;
	public ResponseObject<Object> opposePay(String userId, String nickName, ReceiveDemand rd) throws ServiceException;
	public ResponseObject<Object> addInvite(String nickName, String demandId, String friendId,
			String userId) throws ServiceException;
	public ResponseObject<Object> modifyReceiveDemandAmount(String userId,String username,
			String id, String amount) throws ServiceException;
	public ResponseObject<Object> deleteReceiveDemand(String userId, String demandId) throws ServiceException;
	public ResponseObject<Object> agreeArbitration(String userId, String nickName,
			String demandId) throws ServiceException;
	public ResponseObject<Object> endDemand(ReceiveDemand rd) throws ServiceException;
	
	
	/**
	 * 在column字段上根据key查询进行模糊查询
	 * 
	 * @param key
	 * @param searchColumn
	 * @param pageSize
	 * @param pageNow
	 * @param <T>
	 * @return
	 * @throws ServiceException
	 */
	public <T> ResponseObject<PageSplit<T>> adminSearchByKey(String key,List<String>states, String sortType,String nationality, String type, String typeSecond, String minAmount, String maxAmount, List<String> tagList, int pageSize, int pageNow) throws ServiceException;
	
	
	public ResponseObject<Set<Demand>> getRelevantDemandById(String id) throws ServiceException;

	
	/**
	 * 查找用户发布任务
	 * @param userId
	 * @param pageSize
	 * @param pageNow
	 * @return
	 * @throws ServiceException
	 */
	public <T> ResponseObject<PageSplit<T>> searchPubByUserId(String userId, int pageSize, int pageNow) throws ServiceException;
	
	/**
	 * 查找用户接受任务
	 * @param userId
	 * @param pageSize
	 * @param pageNow
	 * @return
	 * @throws ServiceException
	 */
	public <T> ResponseObject<PageSplit<T>> searchAccByUserId(String userId, int pageSize, int pageNow) throws ServiceException;
	
	public ResponseObject<Object> demandPayByBalance(String userId,
			String demandId, String rdId) throws ServiceException;
	public ResponseObject<Object> demandPayByCredit(String userId,
			String demandId, String rdId, String brand, String name,
			String creditNo, String securityCode, String expireYear,
			String expireMonth, String currency, String amount) throws ServiceException;
	
	//haokun added 2017/03/10 start 增加任务匹配后接单人可以拒绝任务
	public ResponseObject<Object> refuseDemand(String userId, String nickName, String demandId)throws ServiceException;
	//haokun added 2017/03/10 end 增加任务匹配后接单人可以拒绝任务
}
