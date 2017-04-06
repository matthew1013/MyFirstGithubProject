package com.anyonehelps.model;

import java.io.Serializable;
/**
 * 教育经历实体类
 * create chenkanghua 
 * phone +8615818853022
 * email 846645133@qq.com
 * at 2016-03-03
 * 
 */
public class Education implements Serializable {
	private static final long serialVersionUID = -4695814863816173872L;
	private String id; // 用户编号，自动生成 
	private String userId; // 所属用户id
	private String startDate; // 开始时间
	private String endDate; // 结束时间
	private String school; // 学校
	private String major; // 专业、主修
	private String education; // 学历
	private String majorDesc; // 专业描述
	private String modifyDate; // 修改时间
	private String createDate; // 创建时间
	private String enclosure; //附件
	private String enclosureName; //附件显示 add 2016-07-21
	
	
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
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getMajorDesc() {
		return majorDesc;
	}
	public void setMajorDesc(String majorDesc) {
		this.majorDesc = majorDesc;
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
