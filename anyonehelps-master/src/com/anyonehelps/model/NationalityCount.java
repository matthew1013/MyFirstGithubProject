package com.anyonehelps.model;

import java.io.Serializable;
/**
 * 地区任务总数实体类
 * create chenkanghua 
 * phone +8615818853022
 * email 846645133@qq.com
 * at 2016-05-15
 * 
 */
public class NationalityCount implements Serializable {
	private static final long serialVersionUID = -1L;
	private String nationality; //地区
	private String count; 		//总数
	
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	
	
}
