package com.anyonehelps.model;

import java.io.Serializable;

public class AccountDetail implements Serializable {

	private static final long serialVersionUID = -7715776590686348690L;
	private String id;
	private String name;    
	private String userId;
	private String type;
	private String state;
	private String createDate;
	private String modifyDate;
	private String amount;
	private String currency;
	private String remark;
	private User user;
	private Demand demand;    //haokun add 2017/03/09
	private String realAmount;
	
	private String demandId;
	private String invoiceNo;
	private String thirdNo;
	private String remark1;
	
	public User getUser() {
    	return user;
    }

	public void setUser(User user) {
    	this.user = user;
    }

	
	public String getRealAmount() {
    	return realAmount;
    }

	public void setRealAmount(String realAmount) {
    	this.realAmount = realAmount;
    }

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

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

	public String getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDemandId() {
		return demandId;
	}

	public void setDemandId(String demandId) {
		this.demandId = demandId;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
 
	public String getThirdNo() {
		return thirdNo;
	}

	public void setThirdNo(String thirdNo) {
		this.thirdNo = thirdNo;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public Demand getDemand() {
		return demand;
	}

	public void setDemand(Demand demand) {
		this.demand = demand;
	}

	
}
