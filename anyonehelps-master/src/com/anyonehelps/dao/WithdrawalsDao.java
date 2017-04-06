package com.anyonehelps.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anyonehelps.model.Withdrawals;

public interface WithdrawalsDao {
	public int insertWithdrawals(Withdrawals withdrawals) throws Exception;
	
	public List<Withdrawals> search(@Param("userId") String userId,@Param("type") String type,
			@Param("state") String state,@Param("index") int index, @Param("size") int size) throws Exception;

	public int count(@Param("userId") String userId,@Param("type") String type,
			@Param("state") String state)throws Exception;
	
	/**
	 * 未处理或者处理中总金额
	 * @param userId
	 * @param type
	 * @param state
	 * @return
	 * @throws Exception
	 */
	public int sumByUserId(@Param("userId") String userId)throws Exception;  
	
	
}
