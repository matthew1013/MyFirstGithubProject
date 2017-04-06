package com.anyonehelps.model;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
/**
 * 需求留言实体类
 * create chenkanghua 
 * phone +8615818853022
 * email 846645133@qq.com
 * at 2016-01-06
 * 
 */
public class DemandMessage implements Serializable {
	private static final long serialVersionUID = 2928462078206369808L;
	private String id;  //id
	private String demandId;//任务id
	private String userId;//用户id
	private String userNickName;//用户名
	private String userPicUrl; //用户头像
	private String receiverId;  //被@用户id 
	private String receiverNickName; //被@用户名称
	private String receiverPicUrl; //被@用户id头像
	private String content; //内容
	private String createDate; //创建时间
	private String modifyDate; //修改时间 。目前暂时是表示删除的时间 
	private String state; //状态，默认是0。 如果是1表示已经删除 
	//private User user; //留言所属用户
	@JsonIgnore
	private String parentId;   
	private List<DemandMessage> replyMessages;
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
	public String getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	public String getReceiverNickName() {
		return receiverNickName;
	}
	public void setReceiverNickName(String receiverNickName) {
		this.receiverNickName = receiverNickName;
	}
	public String getReceiverPicUrl() {
		return receiverPicUrl;
	}
	public void setReceiverPicUrl(String receiverPicUrl) {
		this.receiverPicUrl = receiverPicUrl;
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
	public List<DemandMessage> getReplyMessages() {
		return replyMessages;
	}
	public void setReplyMessages(List<DemandMessage> replyMessages) {
		this.replyMessages = replyMessages;
	}
	
}
