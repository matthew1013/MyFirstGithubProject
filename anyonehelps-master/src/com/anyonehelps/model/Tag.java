package com.anyonehelps.model;

import java.io.Serializable;
/**
 * 系统标签实体类
 * create chenkanghua 
 * phone +8615818853022
 * email 846645133@qq.com
 * at 2016-03-31
 * 
 */
public class Tag implements Serializable {
	private static final long serialVersionUID = 6803444916496365707L;
	private String id; 				// 编号，自动生成
	private String name;            //标签名
	private String level;			//排序，数值越大，就越排在前面
	private String createDate;      //创建时间
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
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	
}
