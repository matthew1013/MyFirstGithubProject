package com.anyonehelps.model;

import java.io.Serializable;

public class Message implements Serializable {

	private static final long serialVersionUID = 3946308915015650920L;
	
	private String id;
	private String userId;   //发送者id，非真实发送者id 
	private String friendId;  //接受者id，非真实接受者id 
	private String senderId;  //发送者id，真实的发送者id 
	private String receiverId;  //接受者id，真实的接受者id 
	private String content;  //消息内容
	private String createDate;   //创建时间
	private String modifyDate;   //修改时间
	private String state;        //状态，0表示未读，1表示已读，2已经已删
	
	private String count; //对话消息总数
	private String friendNickName; //对方名称
	private String friendPicUrl; //对方头像
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
	public String getSenderId() {
		return senderId;
	}
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	
	public String getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
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
	
	
}
