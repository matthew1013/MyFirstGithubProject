package com.anyonehelps.common.util.paypal;

public class PaypalResultMode {
	
	private String resultVerified;  //通过验证为 VERIFIED，不通过则为 INVALD
	
	//更多属性请查看https://www.paypal-biz.com/product/pdf/PayPal_IPN&PDT_Guide_V1.0.pdf
	
	//由您（商家）传递的物品名称。如果不是由您传递，则由您的客户输入。如果是
	//购物车交易，PayPal 将附加物品号（例如， item_name1、item_name2）
	private String itemName;
	//您用于跟踪购买的传递变量。在付款完成时，它会传回给您。如果省略，则将没
	//有变量传回给您。
	private String itemNumber;
	//Completed：对于集中付款，表示您的所有付款已认领，或在 30
	//天后，无主付款已退回给您。 Denied：对于集中付款，表示您
	//的资金未发送，而集中付款未 开始。可能是由于资金不足所
	//致。 Processed：您的集中付款已处理，所有付款已发送。
	private String paymentStatus;
	//扣除交易费之前的客户付款全部金额。等于美元付款
	//payment_gross。如果该金额为负，则表示退款或撤销，原定交
	//易费的全部或部分金额都可以是这两种付款状态之
	private String mcGross;
	//对于付款 IPN，此为付款货币（即， txn_type= subscr_payment）
	private String mcCurrency;
	//商家从买家接受付款的原始交易号，事件是针对该交易号注册的。
	private String txnId;
	//收款人（即商家）的主要邮件地址。如果付款不是发送到 PayPal 账户上的主要
	//邮件地址，则 receiver_email 依旧是主要邮件地址。
	private String receiverEmail;
	//客户的主要邮件地址。使用该电子邮件提供所有信用记录。
	private String payerEmail;
	//与付款关联的交易费。mc_gross 减去 mc_fee 等于存入
	//receiver_email账户的金额。等于美元付款 payment_fee。如果
	//该金额为负，则表示退款或撤销，原定交易费的全部或部分金额
	//都可以是这两种付款状态之一
	private String mcFee;
	
	private String custom; //自定义值    ,用户id 
	
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemNumber() {
		return itemNumber;
	}
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getMcGross() {
		return mcGross;
	}
	public void setMcGross(String mcGross) {
		this.mcGross = mcGross;
	}
	public String getMcCurrency() {
		return mcCurrency;
	}
	public void setMcCurrency(String mcCurrency) {
		this.mcCurrency = mcCurrency;
	}
	public String getTxnId() {
		return txnId;
	}
	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}
	public String getReceiverEmail() {
		return receiverEmail;
	}
	public void setReceiverEmail(String receiverEmail) {
		this.receiverEmail = receiverEmail;
	}
	public String getPayerEmail() {
		return payerEmail;
	}
	public void setPayerEmail(String payerEmail) {
		this.payerEmail = payerEmail;
	}
	public String getMcFee() {
		return mcFee;
	}
	public void setMcFee(String mcFee) {
		this.mcFee = mcFee;
	}
	
	public String getCustom() {
		return custom;
	}
	public void setCustom(String custom) {
		this.custom = custom;
	}
	public String getResultVerified() {
		return resultVerified;
	}
	public void setResultVerified(String resultVerified) {
		this.resultVerified = resultVerified;
	}
	@Override
	public String toString() {
		return "PaypalResultMode [resultVerified=" + resultVerified
				+ ", itemName=" + itemName + ", itemNumber=" + itemNumber
				+ ", paymentStatus=" + paymentStatus + ", mcGross=" + mcGross
				+ ", mcCurrency=" + mcCurrency + ", txnId=" + txnId
				+ ", receiverEmail=" + receiverEmail + ", payerEmail="
				+ payerEmail + ", mcFee=" + mcFee + ", custom=" + custom + "]";
	}

	
}
