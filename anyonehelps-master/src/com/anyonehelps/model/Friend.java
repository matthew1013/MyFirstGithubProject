package com.anyonehelps.model;

import java.io.Serializable;
/**
 * 好友实体类
 * create chenkanghua 
 * phone +8615818853022
 * email 846645133@qq.com
 * at 2015-01-28
 * 
 */
public class Friend implements Serializable {
	private static final long serialVersionUID = -1381188455302868666L;
	private String id; // 编号，自动生成
	private String userId; //用户id
	private String friendUserId; //好友用户id
	private User friendUser;
	private String invite="0"; //是否已经发过邀请,0表示未关注,大于0表示已关注
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
	public String getFriendUserId() {
		return friendUserId;
	}
	public void setFriendUserId(String friendUserId) {
		this.friendUserId = friendUserId;
	}
	public User getFriendUser() {
		return friendUser;
	}
	public void setFriendUser(User friendUser) {
		this.friendUser = friendUser;
	}
	public String getInvite() {
		return invite;
	}
	public void setInvite(String invite) {
		this.invite = invite;
	}
	
}
