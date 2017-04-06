package com.anyonehelps.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anyonehelps.model.SpecialtyUser;

public interface SpecialtyUserDao {  
	
	/**
	 * 插入技能
	 * @param specialtyUser
	 * @return
	 * @throws Exception
	 */
	public int insertSpecialtyUser(SpecialtyUser specialtyUser ) throws Exception;
	
	/**
	 * 查询用户技能
	 * @param userId 用户id
	 * @param type	 技能类型
	 * @param state 认证状态
	 * @return
	 * @throws Exception
	 */
	public List<SpecialtyUser> getByUserId(@Param("userId") String userId, @Param("type") String type, @Param("state") String state) throws Exception;
	/**
	 * 删除技能
	 * @param userId
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int delete(@Param("userId") String userId,@Param("id") String id) throws Exception;
	/**
	 * 认证技能
	 * @param su 
	 * @return
	 * @throws Exception
	 */
	public int authSpecialtyUser(SpecialtyUser su) throws Exception;
	//public SpecialtyUser getByUserIdTypeIdAndSpecialtyId(@Param("userId") String userId,
	//		@Param("specialtyTypeId") String specialtyTypeId,
	//		@Param("specialtyId") String specialtyId) throws Exception;
	/**
	 * 获取用户公开技能
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<SpecialtyUser> getOpenByUserId(@Param("userId") String userId) throws Exception;
	
	/**
	 * 根据id获取技能信息
	 * @param userId
	 * @param id
	 * @return
	 */
	public SpecialtyUser getById(@Param("userId") String userId, @Param("id") String id) throws Exception;
	
	/**
	 * 查找是否已经添加过该技能 
	 * @param userId
	 * @param specialtyId 
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public int countByName(@Param("userId") String userId, @Param("specialtyId") String specialtyId, @Param("name") String name) throws Exception;
	
	
	public List<SpecialtyUser> getByECS()throws Exception;
	
	public int modifyECS(@Param("id") String id, @Param("ep1") String ep1, @Param("ep2") String ep2, @Param("ep3") String ep3, @Param("ep4") String ep4, @Param("ep5") String ep5
			, @Param("eps1") String eps1, @Param("eps2") String eps2, @Param("eps3") String eps3, @Param("eps4") String eps4, @Param("eps5") String eps5) throws Exception;
	
}
