package com.anyonehelps.model;

import java.io.Serializable;

/**
 * 邮件发送实体类
 * create chenkanghua 
 * phone +8615818853022
 * email 846645133@qq.com 
 * at 2016-11-12
 * 
 */
public class EmailSend implements Serializable {
	private static final long serialVersionUID = -773315677934904392L;
	private String id; // 编号，自动生成
	private String userId; //用户id
	private String email; //发送邮件
	private String subject; //主题
	private String content; //内容
	private String state; //发送状态，0表示未发送，1表示发送中，2表示发送成功，3发送失败
	private String failCount; //失败次数
	private String modifyDate; // 修改时间
	private String createDate; // 创建时间
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getFailCount() {
		return failCount;
	}
	public void setFailCount(String failCount) {
		this.failCount = failCount;
	}
	public String getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
}
