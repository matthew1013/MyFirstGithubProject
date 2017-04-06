package com.anyonehelps.model;

import java.io.Serializable;
/**
 * 用户专长类
 * create chenkanghua 
 * phone +8615818853022
 * email 846645133@qq.com
 * at 2015-12-01
 * 
 */
public class SpecialtyUser implements Serializable {
	private static final long serialVersionUID = -6506512250211736256L;
	private String id; // 编号，自动生成
	private String userId; //用户id 
	private String specialtyTypeId; //专长类型id
	private String specialtyId; //专长id
	private String state; //认证状态,0表示未认证，1表示提交认证，2表示认证成功，3认证失败
	private String type; //0表示系统技能，1表示自定义技能
	private String name; //自定义技能名称，系统技能没用
	
	private String authContent; //认证内容
	private String authDate; //认证内容
	
	private String enclosure1; //附件1
	private String enclosure2; //附件2
	private String enclosure3; //附件3
	private String enclosure4; //附件4
	private String enclosure5; //附件5
	private String enclosure1Name; //附件1文件名
	private String enclosure2Name; //附件2文件名
	private String enclosure3Name; //附件3文件名
	private String enclosure4Name; //附件4文件名
	private String enclosure5Name; //附件5文件名
	
	
	//at 2017-02-14 by chenkanghua
	private String enclosure1Pdf; 	   //附件1转换pdf路径
	private String enclosure2Pdf; 	   //附件2转换pdf路径
	private String enclosure3Pdf; 	   //附件3转换pdf路径
	private String enclosure4Pdf; 	   //附件4转换pdf路径
	private String enclosure5Pdf; 	   //附件5转换pdf路径
			
		
	//at 2017-02-14 by chenkanghua
	private String enclosure1PdfState; 	   //附件1文件转换状态，0表示未转换，1表示成功，2表示失败 
	private String enclosure2PdfState; 	   //附件2文件转换状态，0表示未转换，1表示成功，2表示失败 
	private String enclosure3PdfState; 	   //附件3文件转换状态，0表示未转换，1表示成功，2表示失败 
	private String enclosure4PdfState; 	   //附件4文件转换状态，0表示未转换，1表示成功，2表示失败 
	private String enclosure5PdfState; 	   //附件5文件转换状态，0表示未转换，1表示成功，2表示失败 
		
	private String enclosureConverterState; //附件转换状态，0表示未转换，1表示已经转换
		
	
	private String remark; 
	
	private Specialty specialty;
	private SpecialtyType st;
	
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
	public String getSpecialtyTypeId() {
		return specialtyTypeId;
	}
	public void setSpecialtyTypeId(String specialtyTypeId) {
		this.specialtyTypeId = specialtyTypeId;
	}
	public String getSpecialtyId() {
		return specialtyId;
	}
	public void setSpecialtyId(String specialtyId) {
		this.specialtyId = specialtyId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getAuthContent() {
		return authContent;
	}
	public void setAuthContent(String authContent) {
		this.authContent = authContent;
	}
	
	public String getAuthDate() {
		return authDate;
	}
	public void setAuthDate(String authDate) {
		this.authDate = authDate;
	}
	public Specialty getSpecialty() {
		return specialty;
	}
	public void setSpecialty(Specialty specialty) {
		this.specialty = specialty;
	}
	public SpecialtyType getSt() {
		return st;
	}
	public void setSt(SpecialtyType st) {
		this.st = st;
	}
	public String getEnclosure1() {
		return enclosure1;
	}
	public void setEnclosure1(String enclosure1) {
		this.enclosure1 = enclosure1;
	}
	public String getEnclosure2() {
		return enclosure2;
	}
	public void setEnclosure2(String enclosure2) {
		this.enclosure2 = enclosure2;
	}
	public String getEnclosure3() {
		return enclosure3;
	}
	public void setEnclosure3(String enclosure3) {
		this.enclosure3 = enclosure3;
	}
	public String getEnclosure4() {
		return enclosure4;
	}
	public void setEnclosure4(String enclosure4) {
		this.enclosure4 = enclosure4;
	}
	public String getEnclosure5() {
		return enclosure5;
	}
	public void setEnclosure5(String enclosure5) {
		this.enclosure5 = enclosure5;
	}
	public String getEnclosure1Name() {
		return enclosure1Name;
	}
	public void setEnclosure1Name(String enclosure1Name) {
		this.enclosure1Name = enclosure1Name;
	}
	public String getEnclosure2Name() {
		return enclosure2Name;
	}
	public void setEnclosure2Name(String enclosure2Name) {
		this.enclosure2Name = enclosure2Name;
	}
	public String getEnclosure3Name() {
		return enclosure3Name;
	}
	public void setEnclosure3Name(String enclosure3Name) {
		this.enclosure3Name = enclosure3Name;
	}
	public String getEnclosure4Name() {
		return enclosure4Name;
	}
	public void setEnclosure4Name(String enclosure4Name) {
		this.enclosure4Name = enclosure4Name;
	}
	public String getEnclosure5Name() {
		return enclosure5Name;
	}
	public void setEnclosure5Name(String enclosure5Name) {
		this.enclosure5Name = enclosure5Name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEnclosure1Pdf() {
		return enclosure1Pdf;
	}
	public void setEnclosure1Pdf(String enclosure1Pdf) {
		this.enclosure1Pdf = enclosure1Pdf;
	}
	public String getEnclosure2Pdf() {
		return enclosure2Pdf;
	}
	public void setEnclosure2Pdf(String enclosure2Pdf) {
		this.enclosure2Pdf = enclosure2Pdf;
	}
	public String getEnclosure3Pdf() {
		return enclosure3Pdf;
	}
	public void setEnclosure3Pdf(String enclosure3Pdf) {
		this.enclosure3Pdf = enclosure3Pdf;
	}
	public String getEnclosure4Pdf() {
		return enclosure4Pdf;
	}
	public void setEnclosure4Pdf(String enclosure4Pdf) {
		this.enclosure4Pdf = enclosure4Pdf;
	}
	public String getEnclosure5Pdf() {
		return enclosure5Pdf;
	}
	public void setEnclosure5Pdf(String enclosure5Pdf) {
		this.enclosure5Pdf = enclosure5Pdf;
	}
	public String getEnclosure1PdfState() {
		return enclosure1PdfState;
	}
	public void setEnclosure1PdfState(String enclosure1PdfState) {
		this.enclosure1PdfState = enclosure1PdfState;
	}
	public String getEnclosure2PdfState() {
		return enclosure2PdfState;
	}
	public void setEnclosure2PdfState(String enclosure2PdfState) {
		this.enclosure2PdfState = enclosure2PdfState;
	}
	public String getEnclosure3PdfState() {
		return enclosure3PdfState;
	}
	public void setEnclosure3PdfState(String enclosure3PdfState) {
		this.enclosure3PdfState = enclosure3PdfState;
	}
	public String getEnclosure4PdfState() {
		return enclosure4PdfState;
	}
	public void setEnclosure4PdfState(String enclosure4PdfState) {
		this.enclosure4PdfState = enclosure4PdfState;
	}
	public String getEnclosure5PdfState() {
		return enclosure5PdfState;
	}
	public void setEnclosure5PdfState(String enclosure5PdfState) {
		this.enclosure5PdfState = enclosure5PdfState;
	}
	public String getEnclosureConverterState() {
		return enclosureConverterState;
	}
	public void setEnclosureConverterState(String enclosureConverterState) {
		this.enclosureConverterState = enclosureConverterState;
	}
	
	
}
