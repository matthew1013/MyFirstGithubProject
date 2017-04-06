package com.anyonehelps.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnore;
/**
 * 系统消息实体类
 * create chenkanghua 
 * phone +8615818853022
 * email 846645133@qq.com
 * at 2016-01-09
 * 
 */
public class MessageSystem implements Serializable {
	private static final long serialVersionUID = -5683398271352663897L;
	private String id;
	@JsonIgnore
	private String userId;   //用户ID
	private String level;	//消息等级，1:default 2:primary 3:success 4:info 5:warning 6:danger
 	private String title;   //消息标题
	private String content;      //消息内容
	private String link;         //消息查看的连接
	private String createDate;   //创建时间
	private String modifyDate;   //修改时间
	private String state;        //状态，0表示未读，1表示已读，2已经已删
	 
	private String recommender="-1";     //推荐人用户id(只有这个是请求邀请关联时，这个才有用)
	private String recommendState;  //关系状态，0表示还未操作，1表示已经接受，2表示拒绝(只有这个是请求邀请关联时，这个才有用)
	private String recommendDate;   //关联操作时间 
	 
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
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRecommender() {
		return recommender;
	}
	public void setRecommender(String recommender) {
		this.recommender = recommender;
	}
	public String getRecommendState() {
		return recommendState;
	}
	public void setRecommendState(String recommendState) {
		this.recommendState = recommendState;
	}
	public String getRecommendDate() {
		return recommendDate;
	}
	public void setRecommendDate(String recommendDate) {
		this.recommendDate = recommendDate;
	}
	
	
	
}
