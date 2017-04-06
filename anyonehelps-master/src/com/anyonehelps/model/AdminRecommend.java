package com.anyonehelps.model;

import java.io.Serializable;
/**
 * 管理员推荐任务给用户记录表实体类
 * create chenkanghua 
 * phone +8615818853022
 * email 846645133@qq.com
 * at 2016-09-19
 * 
 */
public class AdminRecommend implements Serializable {
	private static final long serialVersionUID = -773315677934904392L;
	private String id; // 编号，自动生成
	private String userId; //用户id
	private String demandId; //任务id
	private String createDate;   //创建日期
	private String modifyDate;   //修改日期
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
