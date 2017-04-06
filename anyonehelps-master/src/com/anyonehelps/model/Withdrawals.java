package com.anyonehelps.model;

import java.io.Serializable;
/**
 * 提现实体类
 * create chenkanghua 
 * phone +8615818853022
 * email 846645133@qq.com
 * at 2015-11-19
 * 
 */
public class Withdrawals implements Serializable {
	private static final long serialVersionUID = -658452819554607288L;
	private String id; // 提现编号，自动生成
	private String userId;//用户id
	private String amount; //取现金额
	private String name; //收款人（持有人）名字
	private String account; //帐号
	private String accountType; //收款人类别：0表示个人，1表示商业
	private String bnkType; //收款人账号类型：0表示储蓄账号，1表示支票账号
	private String routingNumber; 
	private String type; //取现类型，1表示银行卡，2表示paypal
	
	private String state; // 0表示未处理,1表示处理中,2表示取现成功,3表示取现失败
	private String proc; //手续费
	private String createDate; //创建时间
	private String modifyDate; //修改时间
	private String remark;     //备注，说明
	private String invoiceNo;  //发票号 
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getBnkType() {
		return bnkType;
	}
	public void setBnkType(String bnkType) {
		this.bnkType = bnkType;
	}
	public String getRoutingNumber() {
		return routingNumber;
	}
	public void setRoutingNumber(String routingNumber) {
		this.routingNumber = routingNumber;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getProc() {
		return proc;
	}
	public void setProc(String proc) {
		this.proc = proc;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	
	

}
