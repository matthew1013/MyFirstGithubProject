package com.anyonehelps.model;

import java.io.Serializable;
/**
 * 奖励记录实体类
 * create chenkanghua 
 * phone +8615818853022
 * email 846645133@qq.com
 * at 2016-01-20
 * 
 */
public class BonusPoint implements Serializable {
	private static final long serialVersionUID = -1635693165704660212L;
	private String id; 			// 编号，自动生成
	private String userId; 		//用户id
	private String consumerId; //发布任务人id，是邀请人或者邀请人的邀请人
	private String demandId; 	//任务id
	private String sublevel;    //子级，1-3，1表示第一层邀请人，2表示第二层邀请人，3表示第二层邀请人
	private String point;     	//奖励金额 (原积分)
	private String remark;     	//备注，说明
	private String createDate; 	//创建时间
	private User consumer;      //发布任务人 ，是邀请人或者邀请人的邀请人
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
	public String getConsumerId() {
		return consumerId;
	}
	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}
	public String getDemandId() {
		return demandId;
	}
	public void setDemandId(String demandId) {
		this.demandId = demandId;
	}
	public String getSublevel() {
		return sublevel;
	}
	public void setSublevel(String sublevel) {
		this.sublevel = sublevel;
	}
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public User getConsumer() {
		return consumer;
	}
	public void setConsumer(User consumer) {
		this.consumer = consumer;
	}
	
}
