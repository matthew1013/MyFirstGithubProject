package com.anyonehelps.model;

import java.io.Serializable;
/**
 * 登录信息实体类
 * create chenkanghua 
 * phone +8615818853022
 * email 846645133@qq.com
 * at 2015-09-24
 * 
 */
public class LoginInfo implements Serializable {

	private static final long serialVersionUID = 7578452010387389262L;
	private String id;
	private String name;
	private String times;
	private String lastDate;
	private String type;

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

	public String getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
	}

	public String getLastDate() {
		return lastDate;
	}

	public void setLastDate(String lastDate) {
		this.lastDate = lastDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
