package com.anyonehelps.model;

import java.io.Serializable;
/**
 * 工作经验实体类
 * create chenkanghua 
 * phone +8615818853022
 * email 846645133@qq.com
 * at 2016-03-05
 * 
 */
public class WorkExperience implements Serializable {
	private static final long serialVersionUID = -7288668231523785364L;
	private String id; // 用户编号，自动生成
	private String userId; // 所属用户id
	private String startDate; // 开始时间
	private String endDate; // 结束时间
	private String company; //公司
	private String jobTitle; //职位名称
	private String jobDesc;  //工作描述
	private String modifyDate; // 修改时间
	private String createDate; // 创建时间
	private String enclosure; //附件   at 2016-05-01 
	private String enclosureName; //附件显示名   at 2016-07-21 
	
	//at 2017-02-15 by chenkanghua
	private String enclosurePdf; 	   //附件转换pdf路径
	private String enclosurePdfState; 	   //附件文件转换状态，0表示未转换，1表示成功，2表示失败 
	private String enclosureConverterState; //附件转换状态，0表示未转换，1表示已经转换
	
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
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getJobDesc() {
		return jobDesc;
	}
	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}
	public String getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getEnclosure() {
		return enclosure;
	}
	public void setEnclosure(String enclosure) {
		this.enclosure = enclosure;
	}
	public String getEnclosureName() {
		return enclosureName;
	}
	public void setEnclosureName(String enclosureName) {
		this.enclosureName = enclosureName;
	}
	public String getEnclosurePdf() {
		return enclosurePdf;
	}
	public void setEnclosurePdf(String enclosurePdf) {
		this.enclosurePdf = enclosurePdf;
	}
	public String getEnclosurePdfState() {
		return enclosurePdfState;
	}
	public void setEnclosurePdfState(String enclosurePdfState) {
		this.enclosurePdfState = enclosurePdfState;
	}
	public String getEnclosureConverterState() {
		return enclosureConverterState;
	}
	public void setEnclosureConverterState(String enclosureConverterState) {
		this.enclosureConverterState = enclosureConverterState;
	}
	
}
