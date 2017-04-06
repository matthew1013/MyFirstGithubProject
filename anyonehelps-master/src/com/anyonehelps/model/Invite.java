package com.anyonehelps.model;

import java.io.Serializable;
/**
 * 邀请接单人实体类
 * create chenkanghua 
 * phone +8615818853022
 * email 846645133@qq.com
 * at 2015-11-29
 * 
 */
public class Invite implements Serializable {
	private static final long serialVersionUID = -773315677934904392L;
	private String id; // 编号，自动生成
	private String userId; //用户id
	private String demandId; //任务id
	private String username; //邀请的用户 ，邮箱或者手机
	private String state; //发送状态
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
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
}
