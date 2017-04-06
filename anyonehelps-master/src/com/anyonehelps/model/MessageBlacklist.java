package com.anyonehelps.model;

import java.io.Serializable;

public class MessageBlacklist implements Serializable {

	private static final long serialVersionUID = -1L;
	
	private String id;
	private String userId;   //发送者id，非真实发送者id 
	private String friendId;  //接受者id，非真实接受者id 
	private String createDate;   //创建时间

	private String friendNickName; //对方名称
	private String friendPicUrl; //对方头像
	
	private String friendEvaluate; //接单评价总分
	private String friendEvaluateCount; //接单评价总数
	private String friendEvaluatePublish; //发布任务评价总分
	private String friendEvaluatePublishCount; //发布任务评价数
	private String friendOccupation; //职业
	
	private String friendHonest; 
	private String friendQuality; 
	private String friendPunctual; 
	private String friendHonestPublish; 
	
	
	
	
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
	public String getFriendId() {
		return friendId;
	}
	public void setFriendId(String friendId) {
		this.friendId = friendId;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getFriendNickName() {
		return friendNickName;
	}
	public void setFriendNickName(String friendNickName) {
		this.friendNickName = friendNickName;
	}
	public String getFriendPicUrl() {
		return friendPicUrl;
	}
	public void setFriendPicUrl(String friendPicUrl) {
		this.friendPicUrl = friendPicUrl;
	}
	public String getFriendEvaluate() {
		return friendEvaluate;
	}
	public void setFriendEvaluate(String friendEvaluate) {
		this.friendEvaluate = friendEvaluate;
	}
	public String getFriendEvaluateCount() {
		return friendEvaluateCount;
	}
	public void setFriendEvaluateCount(String friendEvaluateCount) {
		this.friendEvaluateCount = friendEvaluateCount;
	}
	public String getFriendEvaluatePublish() {
		return friendEvaluatePublish;
	}
	public void setFriendEvaluatePublish(String friendEvaluatePublish) {
		this.friendEvaluatePublish = friendEvaluatePublish;
	}
	public String getFriendEvaluatePublishCount() {
		return friendEvaluatePublishCount;
	}
	public void setFriendEvaluatePublishCount(String friendEvaluatePublishCount) {
		this.friendEvaluatePublishCount = friendEvaluatePublishCount;
	}
	
	public String getFriendHonest() {
		return friendHonest;
	}
	public void setFriendHonest(String friendHonest) {
		this.friendHonest = friendHonest;
	}
	public String getFriendQuality() {
		return friendQuality;
	}
	public void setFriendQuality(String friendQuality) {
		this.friendQuality = friendQuality;
	}
	public String getFriendPunctual() {
		return friendPunctual;
	}
	public void setFriendPunctual(String friendPunctual) {
		this.friendPunctual = friendPunctual;
	}
	public String getFriendHonestPublish() {
		return friendHonestPublish;
	}
	public void setFriendHonestPublish(String friendHonestPublish) {
		this.friendHonestPublish = friendHonestPublish;
	}
	public String getFriendOccupation() {
		return friendOccupation;
	}
	public void setFriendOccupation(String friendOccupation) {
		this.friendOccupation = friendOccupation;
	}
	
	
}
