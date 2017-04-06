package com.anyonehelps.model;

import java.io.Serializable;
/**
 * 申请大牛实体类
 * create chenkanghua 
 * phone +8615818853022
 * email 846645133@qq.com
 * at 2016-07-18
 * 
 */
public class ProUser implements Serializable {

	private static final long serialVersionUID = 1L; 
	private String id; // 用户编号，自动生成
	private String userId; //用户id
	private String realname; //真实姓名
	private String sex; //性别，0表示保密，1表示男性，2表示女性
	private String idUpload;            //身份证明附件
	private String idUploadName;        //身份证明附件原名
	private String proTypeId;               //领域类型
	private String proId;               //领域
	private String reason;              //申请理由
	private String educationUpload;     //学历证明附件
	private String educationUploadName; //学历证明附件原名
	private String otherUpload;     //其他附件
	private String otherUploadName; //其他附件原名
	private String state;   //0表示未支付，1表示已支付，2表示认证通过，3表示认证未通过
	private String createDate;  //创建时间
	private String modifyDate;  //修改时间
	
	private String authorizeFlag;
	private String authorizeContent; 
	
	private String proName; //领域名称
	
	private String enclosureConverterState;
	private String idUploadPdf;
	private String idUploadPdfState;
	private String educationUploadPdf;
	private String educationUploadPdfState;
	private String otherUploadPdf;
	private String otherUploadPdfState;
	
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
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getIdUpload() {
		return idUpload;
	}
	public void setIdUpload(String idUpload) {
		this.idUpload = idUpload;
	}
	public String getIdUploadName() {
		return idUploadName;
	}
	public void setIdUploadName(String idUploadName) {
		this.idUploadName = idUploadName;
	}
	public String getProTypeId() {
		return proTypeId;
	}
	public void setProTypeId(String proTypeId) {
		this.proTypeId = proTypeId;
	}
	public String getProId() {
		return proId;
	}
	public void setProId(String proId) {
		this.proId = proId;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getEducationUpload() {
		return educationUpload;
	}
	public void setEducationUpload(String educationUpload) {
		this.educationUpload = educationUpload;
	}
	public String getEducationUploadName() {
		return educationUploadName;
	}
	public void setEducationUploadName(String educationUploadName) {
		this.educationUploadName = educationUploadName;
	}
	public String getOtherUpload() {
		return otherUpload;
	}
	public void setOtherUpload(String otherUpload) {
		this.otherUpload = otherUpload;
	}
	public String getOtherUploadName() {
		return otherUploadName;
	}
	public void setOtherUploadName(String otherUploadName) {
		this.otherUploadName = otherUploadName;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public String getAuthorizeFlag() {
		return authorizeFlag;
	}
	public void setAuthorizeFlag(String authorizeFlag) {
		this.authorizeFlag = authorizeFlag;
	}
	public String getAuthorizeContent() {
		return authorizeContent;
	}
	public void setAuthorizeContent(String authorizeContent) {
		this.authorizeContent = authorizeContent;
	}
	public String getEnclosureConverterState() {
		return enclosureConverterState;
	}
	public void setEnclosureConverterState(String enclosureConverterState) {
		this.enclosureConverterState = enclosureConverterState;
	}
	public String getIdUploadPdf() {
		return idUploadPdf;
	}
	public void setIdUploadPdf(String idUploadPdf) {
		this.idUploadPdf = idUploadPdf;
	}
	public String getIdUploadPdfState() {
		return idUploadPdfState;
	}
	public void setIdUploadPdfState(String idUploadPdfState) {
		this.idUploadPdfState = idUploadPdfState;
	}
	public String getEducationUploadPdf() {
		return educationUploadPdf;
	}
	public void setEducationUploadPdf(String educationUploadPdf) {
		this.educationUploadPdf = educationUploadPdf;
	}
	public String getEducationUploadPdfState() {
		return educationUploadPdfState;
	}
	public void setEducationUploadPdfState(String educationUploadPdfState) {
		this.educationUploadPdfState = educationUploadPdfState;
	}
	public String getOtherUploadPdf() {
		return otherUploadPdf;
	}
	public void setOtherUploadPdf(String otherUploadPdf) {
		this.otherUploadPdf = otherUploadPdf;
	}
	public String getOtherUploadPdfState() {
		return otherUploadPdfState;
	}
	public void setOtherUploadPdfState(String otherUploadPdfState) {
		this.otherUploadPdfState = otherUploadPdfState;
	}
	
	
	
}
