package com.anyonehelps.model;

import java.io.Serializable;
/**
 * 评价记录实体类
 * create chenkanghua 
 * phone +8615818853022
 * email 846645133@qq.com
 * at 2016-01-18
 * 
 */
public class Evaluate implements Serializable {
	private static final long serialVersionUID = -279156746705567147L;
	private String id; 				// 编号，自动生成
	private String userId;			//评价人的用户id
	private String evaluateUserId;  //被评价用户id
	private String evaluate;        //诚信质量评分 0-5
	private String quality;        //任务质量评分 0-5
	private String punctual;        //准时性评分 0-5
	private String platform;        //平台评分 0-5
	private String description;        //评分描述
	private String direction;       //评价方向，0表示做任务者向发布者评价，1表示发任务者向做任务者评价
	private String demandId;        //任务id
	private String receiveDemandId; //接受任务id
	private String createDate;      //创建时间

	private User user;      
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
	public String getEvaluateUserId() {
		return evaluateUserId;
	}
	public void setEvaluateUserId(String evaluateUserId) {
		this.evaluateUserId = evaluateUserId;
	}
	public String getEvaluate() {
		return evaluate;
	}
	public void setEvaluate(String evaluate) {
		this.evaluate = evaluate;
	}
	
	public String getQuality() {
		return quality;
	}
	public void setQuality(String quality) {
		this.quality = quality;
	}
	public String getPunctual() {
		return punctual;
	}
	public void setPunctual(String punctual) {
		this.punctual = punctual;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getDemandId() {
		return demandId;
	}
	public void setDemandId(String demandId) {
		this.demandId = demandId;
	}
	public String getReceiveDemandId() {
		return receiveDemandId;
	}
	public void setReceiveDemandId(String receiveDemandId) {
		this.receiveDemandId = receiveDemandId;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
}
