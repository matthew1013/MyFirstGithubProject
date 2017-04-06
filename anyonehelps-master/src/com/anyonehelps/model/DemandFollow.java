package com.anyonehelps.model;

import java.io.Serializable;
/**
 * 任务关注实体类
 * create chenkanghua 
 * phone +8615818853022
 * email 846645133@qq.com
 * at 2016-02-22
 * 
 */
public class DemandFollow implements Serializable {
	private static final long serialVersionUID = -1404346091896649233L;
	private String id; // 编号，自动生成
	private String userId; //用户id
	private String demandId; //任务id
	private Demand demand;
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
	public String getDemandId() {
		return demandId;
	}
	public void setDemandId(String demandId) {
		this.demandId = demandId;
	}
	public Demand getDemand() {
		return demand;
	}
	public void setDemand(Demand demand) {
		this.demand = demand;
	}

}
