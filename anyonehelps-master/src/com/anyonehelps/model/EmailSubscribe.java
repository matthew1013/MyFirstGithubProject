package com.anyonehelps.model;

import java.io.Serializable;
/**
 * 邮箱订阅类
 * create chenkanghua 
 * phone +8615818853022
 * email 846645133@qq.com
 * at 2016-10-04
 * 
 */
public class EmailSubscribe implements Serializable {
	private static final long serialVersionUID = -4695814863816173872L;
	private String userId; // 所属用户id
	private String createDate; // 创建时间
	private String modifyDate; // 创建时间
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
}
