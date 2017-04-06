package com.anyonehelps.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anyonehelps.model.Demand;
import com.anyonehelps.model.NationalityCount;

public interface DemandDao {  
	/**
	 * 插入任务
	 * @param demand
	 * @return
	 * @throws Exception
	 */
	public int insertDemand(Demand demand) throws Exception;
	/**
	 * 用户发布任务列表
	 * @param userId
	 * @param key
	 * @param index
	 * @param size
	 * @return
	 * @throws Exception
	 */ 
	public List<Demand> searchByKey(@Param("userId") String userId,@Param("key") String key, @Param("states") List<String> states,
	         @Param("index") int index, @Param("size") int size) throws Exception;
	/**
	 * 用户发布任务总数
	 * @param userId
	 * @param key
	 * @param states
	 * @return
	 * @throws Exception
	 */
	public int countByKey(@Param("userId") String userId, @Param("key") String key, @Param("states") List<String> states)
	        throws Exception;

	
	/**
	 * 查找所有竞标任务
	 * @param key
	 * @param index
	 * @param size
	 * @return
	 * @throws Exception
	 */
	public List<Demand> searchAllByKey(@Param("sortType") String sortType,@Param("key") String key,@Param("nationality") String nationality,
			@Param("type") String type,@Param("typeSecond") String typeSecond,
			@Param("minAmount") String minAmount,@Param("maxAmount") String maxAmount, @Param("tags") List<String> tags,
			@Param("index") int index, @Param("size") int size) throws Exception;
	/**
	 * 所有竞标任务总数
	 * @param sortType
	 * @param key
	 * @param nationality
	 * @param type
	 * @param typeSecond
	 * @param minAmount
	 * @param maxAmount
	 * @param tags
	 * @return
	 * @throws Exception
	 */
	public int countAllByKey(@Param("sortType") String sortType,@Param("key") String key,@Param("nationality") String nationality,
			@Param("type") String type,@Param("typeSecond") String typeSecond,
			@Param("minAmount") String minAmount,@Param("maxAmount") String maxAmount, @Param("tags") List<String> tags)
	        throws Exception;
	
	/**
	 * 删除任务
	 * @param userId
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int deleteDemandById(@Param("userId") String userId,@Param("id") String id) throws Exception;

	/**
	 * 根据id获取任务
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Demand getDemandById(@Param("id") String id) throws Exception;
	/**
	 * 修改任务
	 * @param demand
	 * @return
	 * @throws Exception
	 */
	public int modifyDemand(Demand demand) throws Exception;
	/**
	 * 修改任务状态
	 * @param id
	 * @param userId
	 * @param state
	 * @param modifyDate
	 * @return
	 * @throws Exception
	 */
	public int modifyDemandState(@Param("id") String id,@Param("userId") String userId,@Param("state") String state,@Param("modifyDate") String modifyDate) throws Exception;
	/**
	 * 竞标任务总金额
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public String getSumAmount(List<String> list) throws Exception;
	/**
	 * 根据任务状态查询任务总数
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public int countDemandByState(List<String> list) throws Exception;
	/**
	 * 根据用户id和状态查询任务总数
	 * @param userId
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public int countByUserIdAndState(@Param("userId") String userId, @Param("list") List<String> list) throws Exception;
	
	/**
	 * 查看用户接收任务的任务信息
	 * @param userId 
	 * @return
	 * @throws Exception
	 */
	public List<Demand> searchByRDReceiverId(@Param("userId") String userId, @Param("states") List<String> states,
	         @Param("index") int index, @Param("size") int size) throws Exception;
	/**
	 * 查看用户接收任务总数
	 * @param userId 
	 * @return
	 * @throws Exception
	 */
	public int countByRDReceiverId(@Param("userId") String userId, @Param("states") List<String> states)
	        throws Exception;
	/**
	 * 根据任务地址查任务
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public List<Demand> searchDemandAllRegion(@Param("key") String key)throws Exception;
	
	//public List<Demand> searchByState8(@Param("userId") String userId)throws Exception;
	
	/**
	 * 查询地区任务数
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public List<NationalityCount> searchNCByState(List<String> list)throws Exception;
	/**
	 * 修改任务备注
	 * @param userId
	 * @param id
	 * @param remark
	 * @param modifyDate
	 * @return
	 * @throws Exception
	 */
	public int modifyRemark(@Param("userId") String userId,@Param("id") String id, @Param("remark") String remark,@Param("modifyDate") String modifyDate)throws Exception;
	
	/**
	 * 管理员查询任务
	 * @param sortType
	 * @param key
	 * @param states
	 * @param nationality
	 * @param type
	 * @param typeSecond
	 * @param minAmount
	 * @param maxAmount
	 * @param tags
	 * @return
	 * @throws Exception
	 */
	public int adminCountByKey(@Param("sortType") String sortType, @Param("key") String key,
			@Param("states") List<String> states, @Param("nationality") String nationality, @Param("type") String type,
			@Param("typeSecond") String typeSecond, @Param("minAmount") String minAmount, @Param("maxAmount") String maxAmount,
			@Param("tags") List<String> tags) throws Exception;
	/**
	 * 管理员查询任务总数
	 * @param sortType
	 * @param key
	 * @param states
	 * @param nationality
	 * @param type
	 * @param typeSecond
	 * @param minAmount
	 * @param maxAmount
	 * @param tags
	 * @param index
	 * @param size
	 * @return
	 * @throws Exception
	 */
	public List<Demand> adminSearchByKey(@Param("sortType") String sortType, @Param("key") String key,
			@Param("states") List<String> states, @Param("nationality") String nationality, @Param("type") String type,
			@Param("typeSecond") String typeSecond, @Param("minAmount") String minAmount, @Param("maxAmount") String maxAmount,
			@Param("tags") List<String> tags, @Param("index") int index, @Param("size") int size) throws Exception;
	/**
	 * 修改管理员备注
	 * @param id
	 * @param remarkAdmin
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public int modifyRemarkAdmin(@Param("id") String id, @Param("remarkAdmin") String remarkAdmin,@Param("date") String date)throws Exception;
	
	/**
	 * 发布任务总数
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public int countByUserId(@Param("userId") String userId)throws Exception;
	/**
	 * 发布任务列表
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<Demand> searchByUserId(@Param("userId") String userId, @Param("index") int index, @Param("size") int size)throws Exception;
	/**
	 * 解决任务总数
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public int countRDByState8(@Param("userId") String userId)throws Exception;
	/**
	 * 解决任务列表 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<Demand> searchRDByState8(@Param("userId") String userId, @Param("index") int index, @Param("size") int size)throws Exception;
	
	public int modifyPayState(@Param("id") String id, @Param("state") String state, @Param("payState") String payState, @Param("date") String date) throws Exception;
	
	
	public List<Demand> getByECS()throws Exception;
	
	public int modifyECS(@Param("id") String id, @Param("ep1") String ep1, @Param("ep2") String ep2, @Param("ep3") String ep3, @Param("ep4") String ep4, @Param("ep5") String ep5
			, @Param("eps1") String eps1, @Param("eps2") String eps2, @Param("eps3") String eps3, @Param("eps4") String eps4, @Param("eps5") String eps5) throws Exception;
	
	
	public void exeAutoDemand();
	public void exeAutoUser();
	
}
