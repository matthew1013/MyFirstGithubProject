package com.anyonehelps.model;

import java.io.Serializable;
import java.util.List;
/**
 * 新闻评论实体类
 * create chenkanghua 
 * phone +8615818853022
 * email 846645133@qq.com
 * at 2017-03-27
 * 
 */
public class NewsComment implements Serializable {
	private static final long serialVersionUID = -773315677934904392L;
	private String id; // 编号，自动生成 
	private String newId; //新闻id
	private String userId; //评论者时间
	private String userNickName; //评论者名称
	private String userPicUrl; //评论者头像
	private String content; //评论内容
	private String createDate; //评论时间
	private String parentId; 
	private List<NewsComment> replyComments;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNewId() {
		return newId;
	}
	public void setNewId(String newId) {
		this.newId = newId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserNickName() {
		return userNickName;
	}
	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}
	public String getUserPicUrl() {
		return userPicUrl;
	}
	public void setUserPicUrl(String userPicUrl) {
		this.userPicUrl = userPicUrl;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public List<NewsComment> getReplyComments() {
		return replyComments;
	}
	public void setReplyComments(List<NewsComment> replyComments) {
		this.replyComments = replyComments;
	}
	
	
}
