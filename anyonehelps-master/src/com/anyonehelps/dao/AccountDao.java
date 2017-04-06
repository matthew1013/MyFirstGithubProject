package com.anyonehelps.dao;

import org.apache.ibatis.annotations.Param;

import com.anyonehelps.model.Account;

public interface AccountDao {
	/**
	 * 插入或者更新帐号
	 * @param account
	 * @return
	 * @throws Exception
	 */
	public int insertOrUpdateAccount(Account account) throws Exception;

	public int modifyUsdByUserId(@Param("userId")String userId, @Param("usd")String usd, @Param("modifyDate")String modifyDate) throws Exception;

	public Account getAccountByUserId(@Param("userId") String userId) throws Exception;
	
	public int modifyBonusPoint(@Param("userId")String userId, @Param("bonusPoint")String bonusPoint, @Param("modifyDate")String modifyDate) throws Exception;
	
	public int addForwardUsd(@Param("userId")String userId, @Param("forwardUsd")String forwardUsd, @Param("modifyDate")String modifyDate) throws Exception;
	public int reduceForwardUsd(@Param("userId")String userId, @Param("forwardUsd")String forwardUsd, @Param("modifyDate")String modifyDate) throws Exception;
	
	public int addFreezeUsd(@Param("userId")String userId, @Param("freezeUsd")String freezeUsd, @Param("modifyDate")String modifyDate) throws Exception;
	public int reduceFreezeUsd(@Param("userId")String userId, @Param("freezeUsd")String freezeUsd, @Param("modifyDate")String modifyDate) throws Exception;
	
}
