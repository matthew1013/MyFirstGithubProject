package com.anyonehelps.model;

import java.io.Serializable;
/**
 * 任务完成手续费比例实体类
 * create chenkanghua 
 * phone +8615818853022
 * email 846645133@qq.com
 * at 2017-01-17
 * 
 */
public class Charges implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id; // 编号，自动生成
	private String newcustomerChargerate;    //新用户手续费比率，默认为100%
	private String oldcustomerChargerate;    //老用户手续费比率，默认100%
	private String createDate;    
	private String modifyDate;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNewcustomerChargerate() {
		return newcustomerChargerate;
	}
	public void setNewcustomerChargerate(String newcustomerChargerate) {
		this.newcustomerChargerate = newcustomerChargerate;
	}
	public String getOldcustomerChargerate() {
		return oldcustomerChargerate;
	}
	public void setOldcustomerChargerate(String oldcustomerChargerate) {
		this.oldcustomerChargerate = oldcustomerChargerate;
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