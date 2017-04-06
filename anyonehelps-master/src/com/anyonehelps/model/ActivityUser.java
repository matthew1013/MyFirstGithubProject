package com.anyonehelps.model;

import java.io.Serializable;
/**
 * 活动用户注册实体类
 * create chenkanghua 
 * phone +8615818853022
 * email 846645133@qq.com
 * at 2016-09-27
 * 
 */
public class ActivityUser implements Serializable {
	private static final long serialVersionUID = -1381188455102868666L;
	private String id; // 编号，自动生成
	private String name; //提交人名字
	private String email; //提交人邮箱
	private String areaCode;   //电话区号
	private String telphone;   //手机号码
	private String status="0";   //处理状态，0未处理，1正处理，2处理完毕，关闭
	private String createDate; //创建时间
	private String userId;    //如果用户登陆了，就有，没登陆则为空或0
	private String mark;    //管理员备注。
	private String activityId; //活动ID

	private String password; //未登录用户报名时有用
	private String newFlag="0"; //是否新用户，1是表示是新用户，0是老用户
	
	private String description; //描述   modify chenkanghua这个删除，不用 
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getActivityId(){
		return activityId;
	}
	public void setActivityId(String activityId){
		this.activityId = activityId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNewFlag() {
		return newFlag;
	}
	public void setNewFlag(String newFlag) {
		this.newFlag = newFlag;
	}
	
}
