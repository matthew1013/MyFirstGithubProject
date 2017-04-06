package com.anyonehelps.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anyonehelps.model.ReceiveDemand;

public interface ReceiveDemandDao {  
	public int insertReceiveDemand(ReceiveDemand receiveDemand) throws Exception;
	public List<ReceiveDemand> getReceiveDemandByDemandId(@Param("demandId") String demandId) throws Exception;
	public ReceiveDemand getReceiveDemandById(@Param("id") String id) throws Exception;
	
	public int modifyEvaluate(@Param("id") String id) throws Exception;
	public int modifyEvaluatePublish(@Param("id") String id) throws Exception;
	public int modifyState(@Param("id") String id, @Param("state") String state) throws Exception;
	public int modifyStateByDemandId(@Param("demandId") String demandId, @Param("state") String state) throws Exception;
	
	public int countByUserIdAndDemandId(@Param("demandId") String demandId, @Param("userId") String userId)
	        throws Exception;
	
	public int modifyRemark(@Param("userId") String userId,@Param("id") String id, @Param("remark") String remark,@Param("modifyDate") String modifyDate)throws Exception;
	
	public int modifyResult(@Param("resultDesc") String resultDesc, @Param("resultUrl1") String resultUrl1, @Param("resultUrl2") String resultUrl2, 
			@Param("resultUrl3") String resultUrl3,@Param("resultUrl4") String resultUrl4,@Param("resultUrl5") String resultUrl5,  
			@Param("resultUrl1Name") String resultUrl1Name, @Param("resultUrl2Name") String resultUrl2Name, 
			@Param("resultUrl3Name") String resultUrl3Name,@Param("resultUrl4Name") String resultUrl4Name,@Param("resultUrl5Name") String resultUrl5Name,
			@Param("id") String id, @Param("demandId") String demandId, @Param("modifyDate") String modifyDate)throws Exception; 
	
	
	public int modifyPay(@Param("payState") String payState, @Param("payPercent") String payPercent, 
			@Param("payReason") String payReason, @Param("payReasonUrl1") String payReasonUrl1, @Param("payReasonUrl2") String payReasonUrl2, 
			@Param("payReasonUrl3") String payReasonUrl3,@Param("payReasonUrl4") String payReasonUrl4,@Param("payReasonUrl5") String payReasonUrl5,  
			@Param("payReasonUrl1Name") String payReasonUrl1Name, @Param("payReasonUrl2Name") String payReasonUrl2Name, 
			@Param("payReasonUrl3Name") String payReasonUrl3Name,@Param("payReasonUrl4Name") String payReasonUrl4Name,@Param("payReasonUrl5Name") String payReasonUrl5Name,
			@Param("id") String id, @Param("demandId") String demandId, @Param("modifyDate") String modifyDate)throws Exception; 
	
	public int modifyPayState(@Param("id") String id, @Param("payState") String payState, @Param("modifyDate") String modifyDate)throws Exception;
	
	public int opposePay(@Param("payState") String payState, @Param("refutePercent") String refutePercent, 
			@Param("refuteReason") String refuteReason, @Param("refuteReasonUrl1") String refuteReasonUrl1, @Param("refuteReasonUrl2") String refuteReasonUrl2, 
			@Param("refuteReasonUrl3") String refuteReasonUrl3,@Param("refuteReasonUrl4") String refuteReasonUrl4,@Param("refuteReasonUrl5") String refuteReasonUrl5,  
			@Param("refuteReasonUrl1Name") String refuteReasonUrl1Name, @Param("refuteReasonUrl2Name") String refuteReasonUrl2Name, 
			@Param("refuteReasonUrl3Name") String refuteReasonUrl3Name,@Param("refuteReasonUrl4Name") String refuteReasonUrl4Name,@Param("refuteReasonUrl5Name") String refuteReasonUrl5Name,
			@Param("id") String id, @Param("demandId") String demandId, @Param("modifyDate") String modifyDate) throws Exception;
	
	public int endDemand(@Param("payState") String payState, @Param("rulePercent") String rulePercent, 
			@Param("ruleReason") String ruleReason, @Param("ruleReasonUrl1") String ruleReasonUrl1, @Param("ruleReasonUrl2") String ruleReasonUrl2, 
			@Param("ruleReasonUrl3") String ruleReasonUrl3,@Param("ruleReasonUrl4") String ruleReasonUrl4,@Param("ruleReasonUrl5") String ruleReasonUrl5,  
			@Param("ruleReasonUrl1Name") String ruleReasonUrl1Name, @Param("ruleReasonUrl2Name") String ruleReasonUrl2Name, 
			@Param("ruleReasonUrl3Name") String ruleReasonUrl3Name,@Param("ruleReasonUrl4Name") String ruleReasonUrl4Name,@Param("ruleReasonUrl5Name") String refuteReasonUrl5Name,
			@Param("id") String id, @Param("modifyDate") String modifyDate) throws Exception;
	
	public int modifyAmount(@Param("userId") String userId, @Param("id")String id, @Param("amount") String amount, @Param("modifyDate") String date) throws Exception;
	
	public int deleteById(@Param("id") String id) throws Exception;
	
	
	
	public List<ReceiveDemand> getByECS()throws Exception;
	public int modifyECS(ReceiveDemand rd) throws Exception;
	
	
}
