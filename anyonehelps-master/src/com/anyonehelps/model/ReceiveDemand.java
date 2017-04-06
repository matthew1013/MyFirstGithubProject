package com.anyonehelps.model;

import java.io.Serializable;
/**
 * 接单实体类
 * create chenkanghua 
 * phone +8615818853022
 * email 846645133@qq.com
 * at 2015-10-20
 * 
 */
public class ReceiveDemand implements Serializable {

	private static final long serialVersionUID = -5781336169509368890L;
	private String id; // 用户编号，自动生成
	private String demandId;	  //任务id
	private String userId;		  //用户id
	//private String stageType;	  //阶段类型，1表示第一阶段，2表示第二阶段，3表示第三阶段
	private String state;         //是否被选中 0 表示不被选中，1表示被选中
	private String userNickName;  //
	private String createDate; 	  //创建时间
	private String remark; 		  //备注
	//private String evaluate;      //评分
	private String evaluateState; //发布任务者是否已对做任务者评分
	private String evaluateStateReceiver; //做任务者是否已对发布任务者评分
	
	private String amount; //还价
	private String finishDay; //完成天数
	private String readme; //自述
	
	
	private String ip; //应标时记录的ip
	private String country; //ip对应的国家
	private String city; //ip对应的城市
	private String region; //位置，
	
	//at 2016-02-18 by chenkanghua 地图位置
	private String locationOpen;//是否分享位置，0表示不分享，1表示分享
	private String locationName;//位置名称
	private String locationCountry;//位置国家
	private String locationProvince;//位置省/州
	private String locationDistrict;//位置县/地区
	private String latitude;//位置纬度
	private String longitude;//位置经度
	
	//add by chenkanghua at 2016-08-02
	private String payState;//支付状态
	private String payPercent;//发单人支付比例
	private String payReason;//发单人支付原因
	private String payReasonUrl1;//发单人支付原因附件url1
	private String payReasonUrl2;//发单人支付原因附件url2
	private String payReasonUrl3;//发单人支付原因附件url3
	private String payReasonUrl4;//发单人支付原因附件url4
	private String payReasonUrl5;//发单人支付原因附件url5
	private String payReasonUrl1Name;//发单人支付原因附件url1显示名
	private String payReasonUrl2Name;//发单人支付原因附件url2显示名
	private String payReasonUrl3Name;//发单人支付原因附件url3显示名
	private String payReasonUrl4Name;//发单人支付原因附件url4显示名
	private String payReasonUrl5Name;//发单人支付原因附件url5显示名
	
	private String enclosureConverterState;
	private String payReasonUrl1Pdf;
	private String payReasonUrl2Pdf;
	private String payReasonUrl3Pdf;
	private String payReasonUrl4Pdf;
	private String payReasonUrl5Pdf;
	private String payReasonPdf1State;
	private String payReasonPdf2State;
	private String payReasonPdf3State;
	private String payReasonPdf4State;
	private String payReasonPdf5State;
	
	private String refutePercent;//接单人仲裁举证比例
	private String refuteReason;//接单人仲裁举证原因
	private String refuteReasonUrl1;//接单人仲裁举证原因附件url1
	private String refuteReasonUrl2;//接单人仲裁举证原因附件url2
	private String refuteReasonUrl3;//接单人仲裁举证原因附件url3
	private String refuteReasonUrl4;//接单人仲裁举证原因附件url4
	private String refuteReasonUrl5;//接单人仲裁举证原因附件url5
	private String refuteReasonUrl1Name;//接单人仲裁举证原因附件url1显示名
	private String refuteReasonUrl2Name;//接单人仲裁举证原因附件url2显示名
	private String refuteReasonUrl3Name;//接单人仲裁举证原因附件url3显示名
	private String refuteReasonUrl4Name;//接单人仲裁举证原因附件url4显示名
	private String refuteReasonUrl5Name;//接单人仲裁举证原因附件url5显示名
	
	private String refuteReasonUrl1Pdf;
	private String refuteReasonUrl2Pdf;
	private String refuteReasonUrl3Pdf;
	private String refuteReasonUrl4Pdf;
	private String refuteReasonUrl5Pdf;
	private String refuteReasonPdf1State;
	private String refuteReasonPdf2State;
	private String refuteReasonPdf3State;
	private String refuteReasonPdf4State;
	private String refuteReasonPdf5State;
	
	private String rulePercent;//接单人仲裁举证比例
	private String ruleReason;//接单人仲裁举证原因
	private String ruleReasonUrl1;//接单人仲裁举证原因附件url1
	private String ruleReasonUrl2;//接单人仲裁举证原因附件url2
	private String ruleReasonUrl3;//接单人仲裁举证原因附件url3
	private String ruleReasonUrl4;//接单人仲裁举证原因附件url4
	private String ruleReasonUrl5;//接单人仲裁举证原因附件url5
	private String ruleReasonUrl1Name;//接单人仲裁举证原因附件url1显示名
	private String ruleReasonUrl2Name;//接单人仲裁举证原因附件url2显示名
	private String ruleReasonUrl3Name;//接单人仲裁举证原因附件url3显示名
	private String ruleReasonUrl4Name;//接单人仲裁举证原因附件url4显示名
	private String ruleReasonUrl5Name;//接单人仲裁举证原因附件url5显示名
	

	private String ruleReasonUrl1Pdf;
	private String ruleReasonUrl2Pdf;
	private String ruleReasonUrl3Pdf;
	private String ruleReasonUrl4Pdf;
	private String ruleReasonUrl5Pdf;
	private String ruleReasonPdf1State;
	private String ruleReasonPdf2State;
	private String ruleReasonPdf3State;
	private String ruleReasonPdf4State;
	private String ruleReasonPdf5State;
	
	private String resultDesc;//接单人提交成果要求
	private String resultUrl1;//接单人提交成果要求附件url1
	private String resultUrl2;//接单人提交成果要求附件url2
	private String resultUrl3;//接单人提交成果要求附件url3
	private String resultUrl4;//接单人提交成果要求附件url4
	private String resultUrl5;//接单人提交成果要求附件url5
	private String resultUrl1Name;//接单人提交成果要求附件url1显示名
	private String resultUrl2Name;//接单人提交成果要求附件url2显示名
	private String resultUrl3Name;//接单人提交成果要求附件url3显示名
	private String resultUrl4Name;//接单人提交成果要求附件url4显示名
	private String resultUrl5Name;//接单人提交成果要求附件url5显示名

	private String resultUrl1Pdf;
	private String resultUrl2Pdf;
	private String resultUrl3Pdf;
	private String resultUrl4Pdf;
	private String resultUrl5Pdf;
	private String resultPdf1State;
	private String resultPdf2State;
	private String resultPdf3State;
	private String resultPdf4State;
	private String resultPdf5State;
	
	//private Arbitration arbitration; //仲裁 add by chenkanghua 2016-03-23
	private Evaluate evaluate; //评价 add by chenkanghua 2016-03-23
	private Evaluate evaluateReceiver;
	private User user; //用户信息
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDemandId() {
		return demandId;
	}
	public void setDemandId(String demandId) {
		this.demandId = demandId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserNickName() {
		return userNickName;
	}
	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/*public String getEvaluate() {
		return evaluate;
	}
	public void setEvaluate(String evaluate) {
		this.evaluate = evaluate;
	}*/
	
	public String getEvaluateState() {
		return evaluateState;
	}
	public Evaluate getEvaluate() {
		return evaluate;
	}
	public void setEvaluate(Evaluate evaluate) {
		this.evaluate = evaluate;
	}
	
	public Evaluate getEvaluateReceiver() {
		return evaluateReceiver;
	}
	public void setEvaluateReceiver(Evaluate evaluateReceiver) {
		this.evaluateReceiver = evaluateReceiver;
	}
	public void setEvaluateState(String evaluateState) {
		this.evaluateState = evaluateState;
	}
	

	public String getEvaluateStateReceiver() {
		return evaluateStateReceiver;
	}
	public void setEvaluateStateReceiver(String evaluateStateReceiver) {
		this.evaluateStateReceiver = evaluateStateReceiver;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getLocationOpen() {
		return locationOpen;
	}
	public void setLocationOpen(String locationOpen) {
		this.locationOpen = locationOpen;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	
	public String getLocationCountry() {
		return locationCountry;
	}
	public void setLocationCountry(String locationCountry) {
		this.locationCountry = locationCountry;
	}
	public String getLocationProvince() {
		return locationProvince;
	}
	public void setLocationProvince(String locationProvince) {
		this.locationProvince = locationProvince;
	}
	public String getLocationDistrict() {
		return locationDistrict;
	}
	public void setLocationDistrict(String locationDistrict) {
		this.locationDistrict = locationDistrict;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getFinishDay() {
		return finishDay;
	}
	public void setFinishDay(String finishDay) {
		this.finishDay = finishDay;
	}
	public String getReadme() {
		return readme;
	}
	public void setReadme(String readme) {
		this.readme = readme;
	}
	public String getPayState() {
		return payState;
	}
	public void setPayState(String payState) {
		this.payState = payState;
	}
	public String getPayPercent() {
		return payPercent;
	}
	public void setPayPercent(String payPercent) {
		this.payPercent = payPercent;
	}
	public String getPayReason() {
		return payReason;
	}
	public void setPayReason(String payReason) {
		this.payReason = payReason;
	}
	public String getPayReasonUrl1() {
		return payReasonUrl1;
	}
	public void setPayReasonUrl1(String payReasonUrl1) {
		this.payReasonUrl1 = payReasonUrl1;
	}
	public String getPayReasonUrl2() {
		return payReasonUrl2;
	}
	public void setPayReasonUrl2(String payReasonUrl2) {
		this.payReasonUrl2 = payReasonUrl2;
	}
	public String getPayReasonUrl3() {
		return payReasonUrl3;
	}
	public void setPayReasonUrl3(String payReasonUrl3) {
		this.payReasonUrl3 = payReasonUrl3;
	}
	
	public String getPayReasonUrl4() {
		return payReasonUrl4;
	}
	public void setPayReasonUrl4(String payReasonUrl4) {
		this.payReasonUrl4 = payReasonUrl4;
	}
	public String getPayReasonUrl5() {
		return payReasonUrl5;
	}
	public void setPayReasonUrl5(String payReasonUrl5) {
		this.payReasonUrl5 = payReasonUrl5;
	}
	
	public String getPayReasonUrl1Name() {
		return payReasonUrl1Name;
	}
	public void setPayReasonUrl1Name(String payReasonUrl1Name) {
		this.payReasonUrl1Name = payReasonUrl1Name;
	}
	public String getPayReasonUrl2Name() {
		return payReasonUrl2Name;
	}
	public void setPayReasonUrl2Name(String payReasonUrl2Name) {
		this.payReasonUrl2Name = payReasonUrl2Name;
	}
	public String getPayReasonUrl3Name() {
		return payReasonUrl3Name;
	}
	public void setPayReasonUrl3Name(String payReasonUrl3Name) {
		this.payReasonUrl3Name = payReasonUrl3Name;
	}
	public String getPayReasonUrl4Name() {
		return payReasonUrl4Name;
	}
	public void setPayReasonUrl4Name(String payReasonUrl4Name) {
		this.payReasonUrl4Name = payReasonUrl4Name;
	}
	public String getPayReasonUrl5Name() {
		return payReasonUrl5Name;
	}
	public void setPayReasonUrl5Name(String payReasonUrl5Name) {
		this.payReasonUrl5Name = payReasonUrl5Name;
	}
	
	public String getRefutePercent() {
		return refutePercent;
	}
	public void setRefutePercent(String refutePercent) {
		this.refutePercent = refutePercent;
	}
	public String getRefuteReason() {
		return refuteReason;
	}
	public void setRefuteReason(String refuteReason) {
		this.refuteReason = refuteReason;
	}
	public String getRefuteReasonUrl1() {
		return refuteReasonUrl1;
	}
	public void setRefuteReasonUrl1(String refuteReasonUrl1) {
		this.refuteReasonUrl1 = refuteReasonUrl1;
	}
	public String getRefuteReasonUrl2() {
		return refuteReasonUrl2;
	}
	public void setRefuteReasonUrl2(String refuteReasonUrl2) {
		this.refuteReasonUrl2 = refuteReasonUrl2;
	}
	public String getRefuteReasonUrl3() {
		return refuteReasonUrl3;
	}
	public void setRefuteReasonUrl3(String refuteReasonUrl3) {
		this.refuteReasonUrl3 = refuteReasonUrl3;
	}
	public String getRefuteReasonUrl4() {
		return refuteReasonUrl4;
	}
	public void setRefuteReasonUrl4(String refuteReasonUrl4) {
		this.refuteReasonUrl4 = refuteReasonUrl4;
	}
	public String getRefuteReasonUrl5() {
		return refuteReasonUrl5;
	}
	public void setRefuteReasonUrl5(String refuteReasonUrl5) {
		this.refuteReasonUrl5 = refuteReasonUrl5;
	}
	public String getRefuteReasonUrl1Name() {
		return refuteReasonUrl1Name;
	}
	public void setRefuteReasonUrl1Name(String refuteReasonUrl1Name) {
		this.refuteReasonUrl1Name = refuteReasonUrl1Name;
	}
	public String getRefuteReasonUrl2Name() {
		return refuteReasonUrl2Name;
	}
	public void setRefuteReasonUrl2Name(String refuteReasonUrl2Name) {
		this.refuteReasonUrl2Name = refuteReasonUrl2Name;
	}
	public String getRefuteReasonUrl3Name() {
		return refuteReasonUrl3Name;
	}
	public void setRefuteReasonUrl3Name(String refuteReasonUrl3Name) {
		this.refuteReasonUrl3Name = refuteReasonUrl3Name;
	}
	public String getRefuteReasonUrl4Name() {
		return refuteReasonUrl4Name;
	}
	public void setRefuteReasonUrl4Name(String refuteReasonUrl4Name) {
		this.refuteReasonUrl4Name = refuteReasonUrl4Name;
	}
	public String getRefuteReasonUrl5Name() {
		return refuteReasonUrl5Name;
	}
	public void setRefuteReasonUrl5Name(String refuteReasonUrl5Name) {
		this.refuteReasonUrl5Name = refuteReasonUrl5Name;
	}
	
	
	
	public String getRulePercent() {
		return rulePercent;
	}
	public void setRulePercent(String rulePercent) {
		this.rulePercent = rulePercent;
	}
	public String getRuleReason() {
		return ruleReason;
	}
	public void setRuleReason(String ruleReason) {
		this.ruleReason = ruleReason;
	}
	public String getRuleReasonUrl1() {
		return ruleReasonUrl1;
	}
	public void setRuleReasonUrl1(String ruleReasonUrl1) {
		this.ruleReasonUrl1 = ruleReasonUrl1;
	}
	public String getRuleReasonUrl2() {
		return ruleReasonUrl2;
	}
	public void setRuleReasonUrl2(String ruleReasonUrl2) {
		this.ruleReasonUrl2 = ruleReasonUrl2;
	}
	public String getRuleReasonUrl3() {
		return ruleReasonUrl3;
	}
	public void setRuleReasonUrl3(String ruleReasonUrl3) {
		this.ruleReasonUrl3 = ruleReasonUrl3;
	}
	public String getRuleReasonUrl4() {
		return ruleReasonUrl4;
	}
	public void setRuleReasonUrl4(String ruleReasonUrl4) {
		this.ruleReasonUrl4 = ruleReasonUrl4;
	}
	public String getRuleReasonUrl5() {
		return ruleReasonUrl5;
	}
	public void setRuleReasonUrl5(String ruleReasonUrl5) {
		this.ruleReasonUrl5 = ruleReasonUrl5;
	}
	public String getRuleReasonUrl1Name() {
		return ruleReasonUrl1Name;
	}
	public void setRuleReasonUrl1Name(String ruleReasonUrl1Name) {
		this.ruleReasonUrl1Name = ruleReasonUrl1Name;
	}
	public String getRuleReasonUrl2Name() {
		return ruleReasonUrl2Name;
	}
	public void setRuleReasonUrl2Name(String ruleReasonUrl2Name) {
		this.ruleReasonUrl2Name = ruleReasonUrl2Name;
	}
	public String getRuleReasonUrl3Name() {
		return ruleReasonUrl3Name;
	}
	public void setRuleReasonUrl3Name(String ruleReasonUrl3Name) {
		this.ruleReasonUrl3Name = ruleReasonUrl3Name;
	}
	public String getRuleReasonUrl4Name() {
		return ruleReasonUrl4Name;
	}
	public void setRuleReasonUrl4Name(String ruleReasonUrl4Name) {
		this.ruleReasonUrl4Name = ruleReasonUrl4Name;
	}
	public String getRuleReasonUrl5Name() {
		return ruleReasonUrl5Name;
	}
	public void setRuleReasonUrl5Name(String ruleReasonUrl5Name) {
		this.ruleReasonUrl5Name = ruleReasonUrl5Name;
	}
	public String getResultDesc() {
		return resultDesc;
	}
	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}
	public String getResultUrl1() {
		return resultUrl1;
	}
	public void setResultUrl1(String resultUrl1) {
		this.resultUrl1 = resultUrl1;
	}
	public String getResultUrl2() {
		return resultUrl2;
	}
	public void setResultUrl2(String resultUrl2) {
		this.resultUrl2 = resultUrl2;
	}
	public String getResultUrl3() {
		return resultUrl3;
	}
	public void setResultUrl3(String resultUrl3) {
		this.resultUrl3 = resultUrl3;
	}
	public String getResultUrl4() {
		return resultUrl4;
	}
	public void setResultUrl4(String resultUrl4) {
		this.resultUrl4 = resultUrl4;
	}
	public String getResultUrl5() {
		return resultUrl5;
	}
	public void setResultUrl5(String resultUrl5) {
		this.resultUrl5 = resultUrl5;
	}
	public String getResultUrl1Name() {
		return resultUrl1Name;
	}
	public void setResultUrl1Name(String resultUrl1Name) {
		this.resultUrl1Name = resultUrl1Name;
	}
	public String getResultUrl2Name() {
		return resultUrl2Name;
	}
	public void setResultUrl2Name(String resultUrl2Name) {
		this.resultUrl2Name = resultUrl2Name;
	}
	public String getResultUrl3Name() {
		return resultUrl3Name;
	}
	public void setResultUrl3Name(String resultUrl3Name) {
		this.resultUrl3Name = resultUrl3Name;
	}
	public String getResultUrl4Name() {
		return resultUrl4Name;
	}
	public void setResultUrl4Name(String resultUrl4Name) {
		this.resultUrl4Name = resultUrl4Name;
	}
	public String getResultUrl5Name() {
		return resultUrl5Name;
	}
	public void setResultUrl5Name(String resultUrl5Name) {
		this.resultUrl5Name = resultUrl5Name;
	}
	/*public Arbitration getArbitration() {
		return arbitration;
	}
	public void setArbitration(Arbitration arbitration) {
		this.arbitration = arbitration;
	}*/
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getEnclosureConverterState() {
		return enclosureConverterState;
	}
	public void setEnclosureConverterState(String enclosureConverterState) {
		this.enclosureConverterState = enclosureConverterState;
	}
	public String getPayReasonUrl1Pdf() {
		return payReasonUrl1Pdf;
	}
	public void setPayReasonUrl1Pdf(String payReasonUrl1Pdf) {
		this.payReasonUrl1Pdf = payReasonUrl1Pdf;
	}
	public String getPayReasonUrl2Pdf() {
		return payReasonUrl2Pdf;
	}
	public void setPayReasonUrl2Pdf(String payReasonUrl2Pdf) {
		this.payReasonUrl2Pdf = payReasonUrl2Pdf;
	}
	public String getPayReasonUrl3Pdf() {
		return payReasonUrl3Pdf;
	}
	public void setPayReasonUrl3Pdf(String payReasonUrl3Pdf) {
		this.payReasonUrl3Pdf = payReasonUrl3Pdf;
	}
	public String getPayReasonUrl4Pdf() {
		return payReasonUrl4Pdf;
	}
	public void setPayReasonUrl4Pdf(String payReasonUrl4Pdf) {
		this.payReasonUrl4Pdf = payReasonUrl4Pdf;
	}
	public String getPayReasonUrl5Pdf() {
		return payReasonUrl5Pdf;
	}
	public void setPayReasonUrl5Pdf(String payReasonUrl5Pdf) {
		this.payReasonUrl5Pdf = payReasonUrl5Pdf;
	}
	public String getPayReasonPdf1State() {
		return payReasonPdf1State;
	}
	public void setPayReasonPdf1State(String payReasonPdf1State) {
		this.payReasonPdf1State = payReasonPdf1State;
	}
	public String getPayReasonPdf2State() {
		return payReasonPdf2State;
	}
	public void setPayReasonPdf2State(String payReasonPdf2State) {
		this.payReasonPdf2State = payReasonPdf2State;
	}
	public String getPayReasonPdf3State() {
		return payReasonPdf3State;
	}
	public void setPayReasonPdf3State(String payReasonPdf3State) {
		this.payReasonPdf3State = payReasonPdf3State;
	}
	public String getPayReasonPdf4State() {
		return payReasonPdf4State;
	}
	public void setPayReasonPdf4State(String payReasonPdf4State) {
		this.payReasonPdf4State = payReasonPdf4State;
	}
	public String getPayReasonPdf5State() {
		return payReasonPdf5State;
	}
	public void setPayReasonPdf5State(String payReasonPdf5State) {
		this.payReasonPdf5State = payReasonPdf5State;
	}
	public String getRefuteReasonUrl1Pdf() {
		return refuteReasonUrl1Pdf;
	}
	public void setRefuteReasonUrl1Pdf(String refuteReasonUrl1Pdf) {
		this.refuteReasonUrl1Pdf = refuteReasonUrl1Pdf;
	}
	public String getRefuteReasonUrl2Pdf() {
		return refuteReasonUrl2Pdf;
	}
	public void setRefuteReasonUrl2Pdf(String refuteReasonUrl2Pdf) {
		this.refuteReasonUrl2Pdf = refuteReasonUrl2Pdf;
	}
	public String getRefuteReasonUrl3Pdf() {
		return refuteReasonUrl3Pdf;
	}
	public void setRefuteReasonUrl3Pdf(String refuteReasonUrl3Pdf) {
		this.refuteReasonUrl3Pdf = refuteReasonUrl3Pdf;
	}
	public String getRefuteReasonUrl4Pdf() {
		return refuteReasonUrl4Pdf;
	}
	public void setRefuteReasonUrl4Pdf(String refuteReasonUrl4Pdf) {
		this.refuteReasonUrl4Pdf = refuteReasonUrl4Pdf;
	}
	public String getRefuteReasonUrl5Pdf() {
		return refuteReasonUrl5Pdf;
	}
	public void setRefuteReasonUrl5Pdf(String refuteReasonUrl5Pdf) {
		this.refuteReasonUrl5Pdf = refuteReasonUrl5Pdf;
	}
	public String getRefuteReasonPdf1State() {
		return refuteReasonPdf1State;
	}
	public void setRefuteReasonPdf1State(String refuteReasonPdf1State) {
		this.refuteReasonPdf1State = refuteReasonPdf1State;
	}
	public String getRefuteReasonPdf2State() {
		return refuteReasonPdf2State;
	}
	public void setRefuteReasonPdf2State(String refuteReasonPdf2State) {
		this.refuteReasonPdf2State = refuteReasonPdf2State;
	}
	public String getRefuteReasonPdf3State() {
		return refuteReasonPdf3State;
	}
	public void setRefuteReasonPdf3State(String refuteReasonPdf3State) {
		this.refuteReasonPdf3State = refuteReasonPdf3State;
	}
	public String getRefuteReasonPdf4State() {
		return refuteReasonPdf4State;
	}
	public void setRefuteReasonPdf4State(String refuteReasonPdf4State) {
		this.refuteReasonPdf4State = refuteReasonPdf4State;
	}
	public String getRefuteReasonPdf5State() {
		return refuteReasonPdf5State;
	}
	public void setRefuteReasonPdf5State(String refuteReasonPdf5State) {
		this.refuteReasonPdf5State = refuteReasonPdf5State;
	}
	public String getRuleReasonUrl1Pdf() {
		return ruleReasonUrl1Pdf;
	}
	public void setRuleReasonUrl1Pdf(String ruleReasonUrl1Pdf) {
		this.ruleReasonUrl1Pdf = ruleReasonUrl1Pdf;
	}
	public String getRuleReasonUrl2Pdf() {
		return ruleReasonUrl2Pdf;
	}
	public void setRuleReasonUrl2Pdf(String ruleReasonUrl2Pdf) {
		this.ruleReasonUrl2Pdf = ruleReasonUrl2Pdf;
	}
	public String getRuleReasonUrl3Pdf() {
		return ruleReasonUrl3Pdf;
	}
	public void setRuleReasonUrl3Pdf(String ruleReasonUrl3Pdf) {
		this.ruleReasonUrl3Pdf = ruleReasonUrl3Pdf;
	}
	public String getRuleReasonUrl4Pdf() {
		return ruleReasonUrl4Pdf;
	}
	public void setRuleReasonUrl4Pdf(String ruleReasonUrl4Pdf) {
		this.ruleReasonUrl4Pdf = ruleReasonUrl4Pdf;
	}
	public String getRuleReasonUrl5Pdf() {
		return ruleReasonUrl5Pdf;
	}
	public void setRuleReasonUrl5Pdf(String ruleReasonUrl5Pdf) {
		this.ruleReasonUrl5Pdf = ruleReasonUrl5Pdf;
	}
	public String getRuleReasonPdf1State() {
		return ruleReasonPdf1State;
	}
	public void setRuleReasonPdf1State(String ruleReasonPdf1State) {
		this.ruleReasonPdf1State = ruleReasonPdf1State;
	}
	public String getRuleReasonPdf2State() {
		return ruleReasonPdf2State;
	}
	public void setRuleReasonPdf2State(String ruleReasonPdf2State) {
		this.ruleReasonPdf2State = ruleReasonPdf2State;
	}
	public String getRuleReasonPdf3State() {
		return ruleReasonPdf3State;
	}
	public void setRuleReasonPdf3State(String ruleReasonPdf3State) {
		this.ruleReasonPdf3State = ruleReasonPdf3State;
	}
	public String getRuleReasonPdf4State() {
		return ruleReasonPdf4State;
	}
	public void setRuleReasonPdf4State(String ruleReasonPdf4State) {
		this.ruleReasonPdf4State = ruleReasonPdf4State;
	}
	public String getRuleReasonPdf5State() {
		return ruleReasonPdf5State;
	}
	public void setRuleReasonPdf5State(String ruleReasonPdf5State) {
		this.ruleReasonPdf5State = ruleReasonPdf5State;
	}
	public String getResultUrl1Pdf() {
		return resultUrl1Pdf;
	}
	public void setResultUrl1Pdf(String resultUrl1Pdf) {
		this.resultUrl1Pdf = resultUrl1Pdf;
	}
	public String getResultUrl2Pdf() {
		return resultUrl2Pdf;
	}
	public void setResultUrl2Pdf(String resultUrl2Pdf) {
		this.resultUrl2Pdf = resultUrl2Pdf;
	}
	public String getResultUrl3Pdf() {
		return resultUrl3Pdf;
	}
	public void setResultUrl3Pdf(String resultUrl3Pdf) {
		this.resultUrl3Pdf = resultUrl3Pdf;
	}
	public String getResultUrl4Pdf() {
		return resultUrl4Pdf;
	}
	public void setResultUrl4Pdf(String resultUrl4Pdf) {
		this.resultUrl4Pdf = resultUrl4Pdf;
	}
	public String getResultUrl5Pdf() {
		return resultUrl5Pdf;
	}
	public void setResultUrl5Pdf(String resultUrl5Pdf) {
		this.resultUrl5Pdf = resultUrl5Pdf;
	}
	public String getResultPdf1State() {
		return resultPdf1State;
	}
	public void setResultPdf1State(String resultPdf1State) {
		this.resultPdf1State = resultPdf1State;
	}
	public String getResultPdf2State() {
		return resultPdf2State;
	}
	public void setResultPdf2State(String resultPdf2State) {
		this.resultPdf2State = resultPdf2State;
	}
	public String getResultPdf3State() {
		return resultPdf3State;
	}
	public void setResultPdf3State(String resultPdf3State) {
		this.resultPdf3State = resultPdf3State;
	}
	public String getResultPdf4State() {
		return resultPdf4State;
	}
	public void setResultPdf4State(String resultPdf4State) {
		this.resultPdf4State = resultPdf4State;
	}
	public String getResultPdf5State() {
		return resultPdf5State;
	}
	public void setResultPdf5State(String resultPdf5State) {
		this.resultPdf5State = resultPdf5State;
	}
	
	
	
	
}
