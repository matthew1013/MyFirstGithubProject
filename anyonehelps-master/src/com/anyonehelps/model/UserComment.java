package com.anyonehelps.model;

import java.io.Serializable;
import java.util.List;
/**
 * 用户留言实体类
 * create chenkanghua 
 * phone +8615818853022
 * email 846645133@qq.com
 * at 2016-08-12
 * 
 */
public class UserComment implements Serializable {
	private static final long serialVersionUID = -1L;
	private String id;  //id
	private String userId;//接收者id
	private String userNickName; //接收者名称
	private String userPicUrl; //接收者头像
	
	private String senderId;//发送者id
	private String senderNickName; //发送者名称
	private String senderPicUrl; //发送者头像
	
	private String content; //内容
	private String createDate; //创建时间
	private String modifyDate; //修改时间 。目前暂时是表示删除的时间 
	private String state; //状态，默认是0。 如果是1表示已经删除
	private String parentId;  
	
	private List<UserComment> replyMessages;

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

	public String getUserNickName() {
		return userNickName;
	}

	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}

	public String getUserPicUrl() {
		return userPicUrl;
	}

	public void setUserPicUrl(String userPicUrl) {
		this.userPicUrl = userPicUrl;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getSenderNickName() {
		return senderNickName;
	}

	public void setSenderNickName(String senderNickName) {
		this.senderNickName = senderNickName;
	}

	public String getSenderPicUrl() {
		return senderPicUrl;
	}

	public void setSenderPicUrl(String senderPicUrl) {
		this.senderPicUrl = senderPicUrl;
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

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public List<UserComment> getReplyMessages() {
		return replyMessages;
	}

	public void setReplyMessages(List<UserComment> replyMessages) {
		this.replyMessages = replyMessages;
	}
	
	
	
}
