package com.anyonehelps.model;

import java.io.Serializable;
import java.util.List;

public class Account implements Serializable {

	private static final long serialVersionUID = -7715776590686348690L;
	private String userId;
	private String usd;
	private String forwardUsd; //用户间转帐,未到帐金额
	private String freezeUsd;  //冻结金额
	private String bonusPoint; //奖励
	private String createDate;
	private String modifyDate;
	private User user;
	private List<AccountDetail> details ;
	
	public List<AccountDetail> getDetails() {
    	return details;
    }

	public void setDetails(List<AccountDetail> details) {
    	this.details = details;
    }

	public User getUser() {
    	return user;
    }

	public void setUser(User user) {
    	this.user = user;
    }

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsd() {
		return usd;
	}

	public void setUsd(String usd) {
		this.usd = usd;
	}

	public String getForwardUsd() {
		return forwardUsd;
	}

	public void setForwardUsd(String forwardUsd) {
		this.forwardUsd = forwardUsd;
	}

	
	public String getFreezeUsd() {
		return freezeUsd;
	}

	public void setFreezeUsd(String freezeUsd) {
		this.freezeUsd = freezeUsd;
	}

	public String getBonusPoint() {
		return bonusPoint;
	}

	public void setBonusPoint(String bonusPoint) {
		this.bonusPoint = bonusPoint;
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
