package com.anyonehelps.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnore;
/**
 * 用户留言实体类
 * create chenkanghua 
 * phone +8615818853022
 * email 846645133@qq.com
 * at 2016-01-08
 * 
 */
public class MessageUser implements Serializable {
	private static final long serialVersionUID = 6288070157651640489L;
	private String id;
	@JsonIgnore
	private String userId;   //被留言者ID
	private String sendUserId; //留言者ID
	private String sendUserNick; //留言者的名称
	private String content;      //留言内容
	private String link;         //留言查看的连接
	private String createDate;   //创建时间
	private String modifyDate;   //修改时间
	private String state;        //状态，0表示未读，1表示已读，2已经已删
	private User sendUser;
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
	public String getSendUserId() {
		return sendUserId;
	}
	public void setSendUserId(String sendUserId) {
		this.sendUserId = sendUserId;
	}
	public String getSendUserNick() {
		return sendUserNick;
	}
	public void setSendUserNick(String sendUserNick) {
		this.sendUserNick = sendUserNick;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
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
	public User getSendUser() {
		return sendUser;
	}
	public void setSendUser(User sendUser) {
		this.sendUser = sendUser;
	}
}
