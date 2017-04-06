package com.anyonehelps.model;

import java.io.Serializable;
import java.util.List;
/**
 * create chenkanghua 
 * phone +8615818853022
 * email 846645133@qq.com
 * at 2016-08-02
 * 
 */
public class ReceiveDemandListForm implements Serializable{
	private static final long serialVersionUID = 1L;
	private String demandId;
    private List<ReceiveDemand> rds;
    
	public String getDemandId() {
		return demandId;
	}
	public void setDemandId(String demandId) {
		this.demandId = demandId;
	}
	public List<ReceiveDemand> getRds() {
		return rds;
	}
	public void setRds(List<ReceiveDemand> rds) {
		this.rds = rds;
	}
	
}