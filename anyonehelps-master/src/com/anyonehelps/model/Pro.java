package com.anyonehelps.model;

import java.io.Serializable;
/**
 * 大牛类
 * create chenkanghua 
 * phone +8615818853022
 * email 846645133@qq.com
 * at 2016-11-14
 * 
 */
public class Pro implements Serializable {
	private static final long serialVersionUID = -6506512250211736256L;
	private String id; // 编号，自动生成
	private String name; //专长名称
	private String ProTypeId; //专长类型
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
	public String getProTypeId() {
		return ProTypeId;
	}
	public void setProTypeId(String proTypeId) {
		ProTypeId = proTypeId;
	}
	
	
}
