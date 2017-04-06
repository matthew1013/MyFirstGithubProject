package com.anyonehelps.model;

import java.io.Serializable;
/**
 * ip记录实体类，主要用来防止恶意攻击
 * create chenkanghua 
 * phone +8615818853022
 * email 846645133@qq.com
 * at 2016-11-06
 * 
 */
public class IPRecord implements Serializable {
	private static final long serialVersionUID = -4548417125768273780L;
	private String id; 				// 编号，自动生成
	private String ip; 				// ip
	private String type; 			// 类型,0表示未知，1表示登录，2表示邮箱注册，3表示手机验证码
	private String areaCode;   //电话区号
	private String telphone;   //手机号码
	private String createDate;      // 创建时间
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	
}
