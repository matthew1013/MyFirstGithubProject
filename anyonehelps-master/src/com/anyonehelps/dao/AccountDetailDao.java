package com.anyonehelps.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anyonehelps.model.AccountDetail;

public interface AccountDetailDao {
	public int insertAccountDetail(AccountDetail detail) throws Exception;

	//public int modifyAccountDetail(AccountDetail detail) throws Exception;

	//public int deleteAccountDetailByUserIds(List<String> list) throws Exception;
 
	public List<AccountDetail> getAccountDetailByUserId(@Param("userId") String userId) throws Exception;

	public AccountDetail getById(String id) throws Exception; 

	public int countByKey(@Param("userId") String userId, @Param("key") String key, @Param("state") String state,
	        @Param("types") List<String> types, @Param("demandId") String demandId,
	        @Param("sdate") String sdate,@Param("edate") String edate) throws Exception;

	public List<AccountDetail> searchByKey(@Param("userId") String userId, @Param("key") String key,
	        @Param("state") String state, @Param("types") List<String> types, @Param("demandId") String demandId,
	        @Param("sdate") String sdate,@Param("edate") String edate,
	        @Param("index") int index, @Param("size") int size);
	
	public int updateStateByPaySuccess(@Param("userId")String userId, @Param("remark")String remark) throws Exception;

	public String checkIfScanPayDetail(@Param("userId")String userId, @Param("amount")String amount, @Param("remark")String remark);
	
	public double sumAmountByType(@Param("userId") String userId,@Param("type") String type) throws Exception;

	/**
	 * 根据备注获取用户财务记录 ，目前只是在paypal充值中用到
	 * @param remark 
	 * @return
	 * @throws Exception
	 */
	public AccountDetail getByRemark(String remark) throws Exception;

}
