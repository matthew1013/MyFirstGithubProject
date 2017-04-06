package com.anyonehelps.model;

import java.io.Serializable;
/**
 * 活动
 * create haokun 
 * at 2016-12-05
 * 
 */
public class Activity implements Serializable {

	private static final long serialVersionUID = 1L;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public String getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getVisitNum() {
		return visitNum;
	}
	public void setVisitNum(String visitNum) {
		this.visitNum = visitNum;
	}
	public String getCoverImg() {
		return coverImg;
	}
	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}
	
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}

	private String id; // 用户编号，自动生成
    private String createBy;
    private String createDate;
    private String updateBy;
    private String expireDate;
    private String remarks;
    private String title;
    private String content;
	private String author;
	private String source;
	private String visitNum;
	private String coverImg;
	private String link;
	
	
	
}
