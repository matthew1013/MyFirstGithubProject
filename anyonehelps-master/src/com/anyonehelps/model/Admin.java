package com.anyonehelps.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnore;
/**
 * 管理员实体类
 * create chenkanghua 
 * phone +8615818853022
 * email 846645133@qq.com
 * at 2016-03-25
 * 
 */
public class Admin implements Serializable {
	private static final long serialVersionUID = 2809650210249432311L;
	private String id; // 用户编号，自动生成
	private String email;    //邮箱
	@JsonIgnore
	private String password; //密码
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}