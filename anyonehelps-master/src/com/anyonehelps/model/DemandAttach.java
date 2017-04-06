package com.anyonehelps.model;

import java.io.Serializable;
/**
 * 任务附加实体类
 * create chenkanghua 
 * phone +8615818853022
 * email 846645133@qq.com
 * at 2016-03-16
 * 
 */
public class DemandAttach implements Serializable {
	private static final long serialVersionUID = 1172625042801937631L;
	private String id; // 编号，自动生成
	private String userId; //用户id
	private String demandId; //任务id
	private String amount; //金额
	private String expireDate;   //到期时间
	private String content; //内容
	private String achieve; //成果要求
	private String createDate; //创建时间
	private String modifyDate; //修改时间

	private String state; //附加任务状态，0表示发布，1表示接单人接受，2表示接单人拒绝
	
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
	public String getDemandId() {
		return demandId;
	}
	public void setDemandId(String demandId) {
		this.demandId = demandId;
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
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}

}
