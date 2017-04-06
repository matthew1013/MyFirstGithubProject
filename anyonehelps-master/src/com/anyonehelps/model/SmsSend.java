package com.anyonehelps.model;

import java.io.Serializable;
/**
 * 短信发送实体类
 * create chenkanghua 
 * phone +8615818853022
 * email 846645133@qq.com
 * at 2016-11-12
 * 
 */
public class SmsSend implements Serializable {
	private static final long serialVersionUID = -773315677934904392L;
	private String id; // 编号，自动生成
	private String userId; //用户id
	private String areaCode; //国家区号
	private String telphone; //手机号码
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
	
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getTelphone() {
		return telphone;
	}
	public void setTelphone(String telphone) {
		this.telphone = telphone;
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
