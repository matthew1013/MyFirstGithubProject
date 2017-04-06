package com.anyonehelps.model;

import java.io.Serializable;
import java.util.List;
/**
 * 需求实体类
 * create chenkanghua 
 * phone +8615818853022
 * email 846645133@qq.com
 * at 2015-10-18
 * 
 */
public class Demand implements Serializable {

	private static final long serialVersionUID = -5781336169509368890L;
	private String id; // 用户编号，自动生成
	private String userId; //用户id
	private String title;  //标题
	private String content; //内容
	private String achieve; //成果要求
	private String secretAchieve;   // 私密成功要求 2017/03/07
	private String tag;     //标签
	private String nationality; //国家
	private String type;      //类型
	private String typeSecond; //第二类型
	private String state;       //状态
	private String createDate;   //创建日期
	private String modifyDate;   //修改日期
	private String bidCount;     //最大中标人数
	private String amount; //金额    create at 2015-12-22
	private String expireDate;  //任务到期时间 create at 2015-12-22
	private String urgent; //加急
	private String open;//公开或私密
	
	//at 2015-12-30 by chenkanghua
	private String enclosure1; //附件1
	private String enclosure2; //附件2
	private String enclosure3; //附件3
	private String enclosure4; //附件4
	private String enclosure5; //附件5
	
	//at 2016-07-21 by chenkanghua
	private String enclosure1Name; //附件1文件名
	private String enclosure2Name; //附件2文件名
	private String enclosure3Name; //附件3文件名
	private String enclosure4Name; //附件3文件名
	private String enclosure5Name; //附件3文件名
	
	
	//at 2017-02-14 by chenkanghua
	private String enclosure1Pdf; 	   //附件1转换pdf路径
	private String enclosure2Pdf; 	   //附件2转换pdf路径
	private String enclosure3Pdf; 	   //附件3转换pdf路径
	private String enclosure4Pdf; 	   //附件4转换pdf路径
	private String enclosure5Pdf; 	   //附件5转换pdf路径
		
	
	//at 2017-02-14 by chenkanghua
	private String enclosure1PdfState; 	   //附件1文件转换状态，0表示未转换，1表示成功，2表示失败 
	private String enclosure2PdfState; 	   //附件2文件转换状态，0表示未转换，1表示成功，2表示失败 
	private String enclosure3PdfState; 	   //附件3文件转换状态，0表示未转换，1表示成功，2表示失败 
	private String enclosure4PdfState; 	   //附件4文件转换状态，0表示未转换，1表示成功，2表示失败 
	private String enclosure5PdfState; 	   //附件5文件转换状态，0表示未转换，1表示成功，2表示失败 
	
	private String enclosureConverterState; //附件转换状态，0表示未转换，1表示已经转换
	
	//at 2016-02-15 by chenkanghua
	private String ip; //应标时记录的ip
	private String country; //ip对应的国家
	private String city; //ip对应的城市
	private String region; //位置
	
	//at 2016-02-18 by chenkanghua 地图位置
	private String locationOpen;//是否分享位置，0表示不分享，1表示分享
	private String locationName;//位置名称
	private String locationCountry;//位置国家
	private String locationProvince;//位置省/州
	private String locationDistrict;//位置县/地区 
	private String latitude;//位置纬度
	private String longitude;//位置经度
	
	private String remark;
	private String remarkAdmin;
	private String userNickName;
	private List<Invite> invites;
	//去掉阶段  at 2015-12-22
	//private String stageCount; 
	//private List<Stage> stages;
	private List<ReceiveDemand> receiveDemands;
	private List<DemandMessage> demandMessages;
	private User user; //任务所属用户
	private String messageCount="0";
	
	private String follow="0"; //是否已经关注任务,0表示未关注,大于0表示已关注
	private String receive="0"; //是否已经接受任务,0表示未接受,大于1表示已接受
	private List<DemandAttach> da; //附加任务 add by 2016-03-16
	private List<DemandSpecialty> ds; //任务所需要技能
	

	private String proUserId; //求助大牛用户id
	private String payState;  //支付状态，前3次支付才有作用,0表示未支付，1表示已支付
	private String finishDate;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getAchieve() {
		return achieve;
	}
	public void setAchieve(String achieve) {
		this.achieve = achieve;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}
	
	public List<ReceiveDemand> getReceiveDemands() {
		return receiveDemands;
	}
	public void setReceiveDemands(List<ReceiveDemand> receiveDemands) {
		this.receiveDemands = receiveDemands;
	}

	public String getBidCount() {
		return bidCount;
	}
	public void setBidCount(String bidCount) {
		this.bidCount = bidCount;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
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
	//去掉阶段  at 2015-12-22
	/*public String getStageCount() {
		return stageCount;
	}
	public void setStageCount(String stageCount) {
		this.stageCount = stageCount;
	}
	public List<Stage> getStages() {
		return stages;
	}
	public void setStages(List<Stage> stages) {
		this.stages = stages;
	}*/
	public String getUserNickName() {
		return userNickName;
	}
	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}

	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getTypeSecond() {
		return typeSecond;
	}
	public void setTypeSecond(String typeSecond) {
		this.typeSecond = typeSecond;
	}
	public String getUrgent() {
		return urgent;
	}
	public void setUrgent(String urgent) {
		this.urgent = urgent;
	}
	public String getOpen() {
		return open;
	}
	public void setOpen(String open) {
		this.open = open;
	}
	public List<Invite> getInvites() {
		return invites;
	}
	public void setInvites(List<Invite> invites) {
		this.invites = invites;
	}
	public String getEnclosure1() {
		return enclosure1;
	}
	public void setEnclosure1(String enclosure1) {
		this.enclosure1 = enclosure1;
	}
	public String getEnclosure2() {
		return enclosure2;
	}
	public void setEnclosure2(String enclosure2) {
		this.enclosure2 = enclosure2;
	}
	public String getEnclosure3() {
		return enclosure3;
	}
	public void setEnclosure3(String enclosure3) {
		this.enclosure3 = enclosure3;
	}
	
	public String getEnclosure4() {
		return enclosure4;
	}
	public void setEnclosure4(String enclosure4) {
		this.enclosure4 = enclosure4;
	}
	public String getEnclosure5() {
		return enclosure5;
	}
	public void setEnclosure5(String enclosure5) {
		this.enclosure5 = enclosure5;
	}
	public String getEnclosure1Name() {
		return enclosure1Name;
	}
	public void setEnclosure1Name(String enclosure1Name) {
		this.enclosure1Name = enclosure1Name;
	}
	public String getEnclosure2Name() {
		return enclosure2Name;
	}
	public void setEnclosure2Name(String enclosure2Name) {
		this.enclosure2Name = enclosure2Name;
	}
	public String getEnclosure3Name() {
		return enclosure3Name;
	}
	public void setEnclosure3Name(String enclosure3Name) {
		this.enclosure3Name = enclosure3Name;
	}
	
	public String getEnclosure4Name() {
		return enclosure4Name;
	}
	public void setEnclosure4Name(String enclosure4Name) {
		this.enclosure4Name = enclosure4Name;
	}
	public String getEnclosure5Name() {
		return enclosure5Name;
	}
	public void setEnclosure5Name(String enclosure5Name) {
		this.enclosure5Name = enclosure5Name;
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
	public List<DemandMessage> getDemandMessages() {
		return demandMessages;
	}
	public void setDemandMessages(List<DemandMessage> demandMessages) {
		this.demandMessages = demandMessages;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public String getMessageCount() {
		return messageCount;
	}
	public void setMessageCount(String messageCount) {
		this.messageCount = messageCount;
	}
	public String getFollow() {
		return follow;
	}
	public void setFollow(String follow) {
		this.follow = follow;
	}
	public String getReceive() {
		return receive;
	}
	public void setReceive(String receive) {
		this.receive = receive;
	}
	public List<DemandAttach> getDa() {
		return da;
	}
	public void setDa(List<DemandAttach> da) {
		this.da = da;
	}
	public List<DemandSpecialty> getDs() {
		return ds;
	}
	public void setDs(List<DemandSpecialty> ds) {
		this.ds = ds;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRemarkAdmin() {
		return remarkAdmin;
	}
	public void setRemarkAdmin(String remarkAdmin) {
		this.remarkAdmin = remarkAdmin;
	}
	public String getProUserId() {
		return proUserId;
	}
	public void setProUserId(String proUserId) {
		this.proUserId = proUserId;
	}
	public String getPayState() {
		return payState;
	}
	public void setPayState(String payState) {
		this.payState = payState;
	}
	public String getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}
	public String getEnclosure1Pdf() {
		return enclosure1Pdf;
	}
	public void setEnclosure1Pdf(String enclosure1Pdf) {
		this.enclosure1Pdf = enclosure1Pdf;
	}
	public String getEnclosure2Pdf() {
		return enclosure2Pdf;
	}
	public void setEnclosure2Pdf(String enclosure2Pdf) {
		this.enclosure2Pdf = enclosure2Pdf;
	}
	public String getEnclosure3Pdf() {
		return enclosure3Pdf;
	}
	public void setEnclosure3Pdf(String enclosure3Pdf) {
		this.enclosure3Pdf = enclosure3Pdf;
	}
	public String getEnclosure4Pdf() {
		return enclosure4Pdf;
	}
	public void setEnclosure4Pdf(String enclosure4Pdf) {
		this.enclosure4Pdf = enclosure4Pdf;
	}
	public String getEnclosure5Pdf() {
		return enclosure5Pdf;
	}
	public void setEnclosure5Pdf(String enclosure5Pdf) {
		this.enclosure5Pdf = enclosure5Pdf;
	}
	public String getEnclosure1PdfState() {
		return enclosure1PdfState;
	}
	public void setEnclosure1PdfState(String enclosure1PdfState) {
		this.enclosure1PdfState = enclosure1PdfState;
	}
	public String getEnclosure2PdfState() {
		return enclosure2PdfState;
	}
	public void setEnclosure2PdfState(String enclosure2PdfState) {
		this.enclosure2PdfState = enclosure2PdfState;
	}
	public String getEnclosure3PdfState() {
		return enclosure3PdfState;
	}
	public void setEnclosure3PdfState(String enclosure3PdfState) {
		this.enclosure3PdfState = enclosure3PdfState;
	}
	public String getEnclosure4PdfState() {
		return enclosure4PdfState;
	}
	public void setEnclosure4PdfState(String enclosure4PdfState) {
		this.enclosure4PdfState = enclosure4PdfState;
	}
	public String getEnclosure5PdfState() {
		return enclosure5PdfState;
	}
	public void setEnclosure5PdfState(String enclosure5PdfState) {
		this.enclosure5PdfState = enclosure5PdfState;
	}
	public String getEnclosureConverterState() {
		return enclosureConverterState;
	}
	public void setEnclosureConverterState(String enclosureConverterState) {
		this.enclosureConverterState = enclosureConverterState;
	}
	//haokun added 2017/03/07 私密成果要求
	public String getSecretAchieve() {
		return secretAchieve;
	}
	public void setSecretAchieve(String secretAchieve) {
		this.secretAchieve = secretAchieve;
	}
		//haokun added 2017/03/07私密成果要求
	
}
