package com.anyonehelps.model;

import java.io.Serializable;
import java.util.List;
/**
 * 大牛类型类
 * create chenkanghua 
 * phone +8615818853022
 * email 846645133@qq.com
 * at 2016-11-14
 * 
 */
public class ProType implements Serializable {
	private static final long serialVersionUID = -6506512250211736256L;
	private String id; // 编号，自动生成
	private String name; //专长类型名称
	
	private List<Pro> pros;
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
	public List<Pro> getPros() {
		return pros;
	}
	public void setPros(List<Pro> pros) {
		this.pros = pros;
	}
}
