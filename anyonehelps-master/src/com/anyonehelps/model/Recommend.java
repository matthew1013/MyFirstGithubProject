package com.anyonehelps.model;

import java.io.Serializable;
/**
 * 邀请记录实体类
 * create chenkanghua 
 * phone +8615818853022
 * email 846645133@qq.com
 * at 2016-01-18
 * 
 */
public class Recommend implements Serializable {
	private static final long serialVersionUID = -8571281070420853047L;
	private String id; // 编号，自动生成
	private String userId; //用户id
	//private String recommender; //推荐人
	private String email; //推荐人email
	private String areaCode; //推荐人areaCode
	private String telphone; //推荐人telphone
	private String state; //邀请状态，0表示还没成功，1表示邀请成功
	private String smsState; //短信或者邮件是否发送成功
	private String createDate; //创建时间
	private String modifyDate; //修改时间，与当前时间相差7天，这条推荐将失效
	
	private String recommender="-1"; //被关联用户id(只有关联请求才有效)
	
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
	/*public String getRecommender() {
		return recommender;
	}
	public void setRecommender(String recommender) {
		this.recommender = recommender;
	}*/
	
	public String getState() {
		return state;
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
	public void setState(String state) {
		this.state = state;
	}
	public String getSmsState() {
		return smsState;
	}
	public void setSmsState(String smsState) {
		this.smsState = smsState;
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
	public String getRecommender() {
		return recommender;
	}
	public void setRecommender(String recommender) {
		this.recommender = recommender;
	}
	
}
