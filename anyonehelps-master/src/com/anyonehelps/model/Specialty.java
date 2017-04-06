package com.anyonehelps.model;

import java.io.Serializable;
/**
 * 专长类
 * create chenkanghua 
 * phone +8615818853022
 * email 846645133@qq.com
 * at 2015-12-01
 * 
 */
public class Specialty implements Serializable {
	private static final long serialVersionUID = -6506512250211736256L;
	private String id; // 编号，自动生成
	private String name; //专长名称
	private String specialtyTypeId; //专长类型
	private String flag;
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
	public String getSpecialtyTypeId() {
		return specialtyTypeId;
	}
	public void setSpecialtyTypeId(String specialtyTypeId) {
		this.specialtyTypeId = specialtyTypeId;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	
}
