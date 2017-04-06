package com.anyonehelps.model;

import java.io.Serializable;
/**
 * 作品展示实体类
 * create chenkanghua 
 * phone +8615818853022
 * email 846645133@qq.com
 * at 2016-03-11
 * 
 */
public class Works implements Serializable {
	private static final long serialVersionUID = 8419950225962615043L;
	private String id; // 用户编号，自动生成
	private String userId; // 所属用户id
	private String url; // 作品路径
	private String title; // 作品标题
	private String type;  //文件类型
	private String createDate; // 创建时间

	private String urlPdf; 
	private String urlPdfState; 
	private String enclosureConverterState;
	
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
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUrlPdf() {
		return urlPdf;
	}
	public void setUrlPdf(String urlPdf) {
		this.urlPdf = urlPdf;
	}
	public String getUrlPdfState() {
		return urlPdfState;
	}
	public void setUrlPdfState(String urlPdfState) {
		this.urlPdfState = urlPdfState;
	}
	public String getEnclosureConverterState() {
		return enclosureConverterState;
	}
	public void setEnclosureConverterState(String enclosureConverterState) {
		this.enclosureConverterState = enclosureConverterState;
	}
	
	
}
