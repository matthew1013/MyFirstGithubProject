package com.anyonehelps.model;

import java.io.Serializable;
/**
 * 发票号实体类
 * create chenkanghua 
 * phone +8615818853022
 * email 846645133@qq.com
 * at 2016-10-05
 * 
 */
public class InvoiceNo implements Serializable {
	private static final long serialVersionUID = 2809650210249432311L;
	private String id; // 用户编号，自动生成
	private String invoiceNo;    //发票号
	private String type;         //发票类型
	private String state;    	 //状态，0表示未用，1表示已用
	private String createDate;    
	private String modifyDate;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	
}