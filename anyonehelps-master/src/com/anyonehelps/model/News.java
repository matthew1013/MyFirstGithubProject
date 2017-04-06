package com.anyonehelps.model;

import java.io.Serializable;
/**
 * 新闻实体类
 * create chenkanghua 
 * phone +8615818853022
 * email 846645133@qq.com
 * at 2016-09-25
 * 
 */
public class News implements Serializable {
	private static final long serialVersionUID = -773315677934904392L;
	private String id; // 编号，自动生成 
	private String createBy; //创建者id
	private String createDate; //创建时间
	private String priority; //优先级
	private String expireDate; //截止时间
	private String remarks; //备注信息
	private String delFlag; //逻辑删除标记（0：显示；1：隐藏）
	private String title; //标题
	private String content; //内容
	private String author; //作者
	private String source; //来源
	private String visitNum; //游览量
	private String coverImg; //标题前面图像，建议78*48px
	private String hotFlag; //是否是热门新闻
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
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
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
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
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
	public String getHotFlag() {
		return hotFlag;
	}
	public void setHotFlag(String hotFlag) {
		this.hotFlag = hotFlag;
	}
	
	
	
}
