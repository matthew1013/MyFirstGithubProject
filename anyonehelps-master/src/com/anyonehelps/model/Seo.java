package com.anyonehelps.model;

import java.io.Serializable;
/**
 * seo实体类
 * create chenkanghua 
 * phone +8615818853022
 * email 846645133@qq.com
 * at 2016-12-16
 * 
 */
public class Seo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id; // 用户编号，自动生成
	private String title;
	private String keywords;
	private String description;
	private String webFlag;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getWebFlag() {
		return webFlag;
	}
	public void setWebFlag(String webFlag) {
		this.webFlag = webFlag;
	}
	
	
}