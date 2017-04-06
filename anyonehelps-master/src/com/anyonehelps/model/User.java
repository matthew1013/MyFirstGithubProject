package com.anyonehelps.model;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
/**
 * 用户实体类
 * create chenkanghua 
 * phone +8615818853022
 * email 846645133@qq.com
 * at 2015-10-14
 * 
 */
public class User implements Serializable {

	private static final long serialVersionUID = -5781336169509368890L;
	private String id; // 用户编号，自动生成
	private String nickName; //妮称、用户名
	private String email;    //邮箱
	private String emailState; //邮箱是否已验证 ，0表示未验证，1表示已验证
	private String areaCode;   //电话区号
	private String telphone;   //手机号码
	private String telphoneState; //手机是否已验证 ，0表示未验证，1表示已验证
	@JsonIgnore
	private String password; //密码
	private String otherContact; //其他联系方式 ---修改为实时联系方式   modify 2016-09-30 by chenkanghua
	private String country;	//国家
	private String province; //省、州
	private String city;     //城市
	private String zipCode;     //邮编
	private String signDate; //注册日期
	private String signIp; //注册IP
	private String type;     //用户类型
	private String picUrl;   //头像url
	private String coverUrl;   //haokun added 2017/02/24 昊坤增加获取封面图片
	private String recommender; //推荐人
	private String school;   //学校
	private String major;	//专业 
	private String abilityCertificateUrl; //专业认证
	private String brief;    //简介
	private String evaluate; //发布任务者给做任务者评分
	private String evaluateCount; //发布任务者给做任务者评分次数
	private String evaluatePublish;//做任务者给发布任务者评分  add by ckh at 2016-01-18
	private String evaluatePublishCount;//做任务者给发布任务者评分次数 add by ckh at 2016-01-18
	
	private String honest;//接单诚信总分 add by ckh at 2016-10-10
	private String quality;//接单质量总分 add by ckh at 2016-10-10
	private String punctual;//接单准时性总分 add by ckh at 2016-10-10
	private String honestPublish;//发单诚信总分 add by ckh at 2016-10-10 
	
	
	
	private String grade;	//等级
	
	private String usdBalance; //美元余额
	private String freezeUsd;  //冻结金额
	private double withdrawalUsd; //待提现金额
	private String forwardUsd; //待到帐金额
	
	private String occupation; //职业 add by ckh at 2016-01-15
	private String wechat;     //微信 add by ckh at 2016-01-15 
	private List<SpecialtyUser> su;  //用户技能 add by ckh at 2016-02-25 
	private User user; //推荐人信息
	private List<Education> education;  //用户技能 add by ckh at 2016-03-04 
	private List<WorkExperience> we;  //工作经验 add by ckh at 2016-03-05
	
	private String follow="0"; //是否已经关注,0表示未关注,大于0表示已关注
	private String followed="0"; //是否被关注,0表示未关注,大于0表示已关注
	
	private String open="0"; //是否向对方公开手机及邮箱,0表示不公开,大于0表示公开
	private List<Works> works;  //作品展示  add by ckh at 2016-03-11 
	//private List<Demand> finishRD; //已完成的接收任务
	//private List<Demand> pubDemand; //已发布任务
	
	private int countFinishRD; //已完成的接收任务
	private int countPubDemand; //已发布任务
	
	//最高学历,0表示未选择,1表示高中毕业或其他同等学力(High School diploma or equivalent),
	//2表示大专学历(College degree),3表示大学本科学历(Undergraduate degree),
	//4表示硕士研究生学历(Master's degree),5表示博士学位(Doctoral or professional degree)
	private String degree;  

	//faceboook  
	private String fbId;
	private String fbToken;
	private String fbName;
	private String fbEmail;
	
	private String wxId;
	private String wxToken;
	
	private String commentCount; //用户留言数
	
	private String adminRecommend = "0"; //管理员是否推荐任务

	private String securityQuestion = "0"; //是否设置了密保问题 0表示还没设置，大于0表示已经设置
	private int emailSubscribe = 0; //邮箱是否退订，0表示没退订，大于0表示已退订 add at 2016-10-04
	
	private int pro=0; //0表示不是大牛，1表示是大牛
	private List<ProUser> pu; //大牛申请  add by chenkanghua at 2016-11-16
	private List<Evaluate> evaluates; //评价列表
	

	private String blockState="0"; //是否黑名单用户,0表示不是,1表示是
	
	private String sex = "0"; //性别  0表示保密，1表示男性，2表示女性,3表示其他
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNickName() {
		return nickName;
	}
	
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmailState() {
		return emailState;
	}
	public void setEmailState(String emailState) {
		this.emailState = emailState;
	}
	
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getTelphone() {
		return telphone;
	}
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	
	public String getTelphoneState() {
		return telphoneState;
	}
	public void setTelphoneState(String telphoneState) {
		this.telphoneState = telphoneState;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getOtherContact() {
		return otherContact; 
	}
	public void setOtherContact(String otherContact) {
		this.otherContact = otherContact;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getSignDate() {
		return signDate;
	}
	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}
	
	public String getSignIp() {
		return signIp;
	}
	public void setSignIp(String signIp) {
		this.signIp = signIp;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getRecommender() {
		return recommender;
	}
	public void setRecommender(String recommender) {
		this.recommender = recommender;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getAbilityCertificateUrl() {
		return abilityCertificateUrl;
	}
	public void setAbilityCertificateUrl(String abilityCertificateUrl) {
		this.abilityCertificateUrl = abilityCertificateUrl;
	}
	
	public String getEvaluate() {
		return evaluate;
	}
	public void setEvaluate(String evaluate) {
		this.evaluate = evaluate;
	}
	public String getEvaluateCount() {
		return evaluateCount;
	}
	public void setEvaluateCount(String evaluateCount) {
		this.evaluateCount = evaluateCount;
	}
	
	public String getEvaluatePublish() {
		return evaluatePublish;
	}
	public void setEvaluatePublish(String evaluatePublish) {
		this.evaluatePublish = evaluatePublish;
	}
	public String getEvaluatePublishCount() {
		return evaluatePublishCount;
	}
	public void setEvaluatePublishCount(String evaluatePublishCount) {
		this.evaluatePublishCount = evaluatePublishCount;
	}
	
	public String getHonest() {
		return honest;
	}
	public void setHonest(String honest) {
		this.honest = honest;
	}
	public String getQuality() {
		return quality;
	}
	public void setQuality(String quality) {
		this.quality = quality;
	}
	public String getPunctual() {
		return punctual;
	}
	public void setPunctual(String punctual) {
		this.punctual = punctual;
	}
	public String getHonestPublish() {
		return honestPublish;
	}
	public void setHonestPublish(String honestPublish) {
		this.honestPublish = honestPublish;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getUsdBalance() {
		return usdBalance;
	}
	public void setUsdBalance(String usdBalance) {
		this.usdBalance = usdBalance;
	}
	
	public String getFreezeUsd() {
		return freezeUsd;
	}
	public void setFreezeUsd(String freezeUsd) {
		this.freezeUsd = freezeUsd;
	}
	
	public double getWithdrawalUsd() {
		return withdrawalUsd;
	}
	public void setWithdrawalUsd(double withdrawalUsd) {
		this.withdrawalUsd = withdrawalUsd;
	}
	public String getForwardUsd() {
		return forwardUsd;
	}
	public void setForwardUsd(String forwardUsd) {
		this.forwardUsd = forwardUsd;
	}
	public String getBrief() {
		return brief;
	}
	public void setBrief(String brief) {
		this.brief = brief;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getWechat() {
		return wechat;
	}
	public void setWechat(String wechat) {
		this.wechat = wechat;
	}
	public List<SpecialtyUser> getSu() {
		return su;
	}
	public void setSu(List<SpecialtyUser> su) {
		this.su = su;
	} 
	
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Education> getEducation() {
		return education;
	}
	public void setEducation(List<Education> education) {
		this.education = education;
	}
	public List<WorkExperience> getWe() {
		return we;
	}
	public void setWe(List<WorkExperience> we) {
		this.we = we;
	}
	public String getFollow() {
		return follow;
	}
	public void setFollow(String follow) {
		this.follow = follow;
	}
	
	public String getFollowed() {
		return followed;
	}
	public void setFollowed(String followed) {
		this.followed = followed;
	}
	public String getOpen() {
		return open;
	}
	public void setOpen(String open) {
		this.open = open;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public List<Works> getWorks() {
		return works;
	}
	public void setWorks(List<Works> works) {
		this.works = works;
	}
	
	
	public int getCountFinishRD() {
		return countFinishRD;
	}
	public void setCountFinishRD(int countFinishRD) {
		this.countFinishRD = countFinishRD;
	}
	public int getCountPubDemand() {
		return countPubDemand;
	}
	public void setCountPubDemand(int countPubDemand) {
		this.countPubDemand = countPubDemand;
	}
	public String getFbId() {
		return fbId;
	}
	public void setFbId(String fbId) {
		this.fbId = fbId;
	}
	public String getFbToken() {
		return fbToken;
	}
	public void setFbToken(String fbToken) {
		this.fbToken = fbToken;
	}
	public String getFbName() {
		return fbName;
	}
	public void setFbName(String fbName) {
		this.fbName = fbName;
	}
	public String getFbEmail() {
		return fbEmail;
	}
	public void setFbEmail(String fbEmail) {
		this.fbEmail = fbEmail;
	}
	public String getWxId() {
		return wxId;
	}
	public void setWxId(String wxId) {
		this.wxId = wxId;
	}
	public String getWxToken() {
		return wxToken;
	}
	public void setWxToken(String wxToken) {
		this.wxToken = wxToken;
	}
	public String getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(String commentCount) {
		this.commentCount = commentCount;
	}
	public String getAdminRecommend() {
		return adminRecommend;
	}
	public void setAdminRecommend(String adminRecommend) {
		this.adminRecommend = adminRecommend;
	}
	public String getSecurityQuestion() {
		return securityQuestion;
	}
	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}
	public int getEmailSubscribe() { 
		return emailSubscribe;
	}
	public void setEmailSubscribe(int emailSubscribe) {
		this.emailSubscribe = emailSubscribe;
	}
	public List<ProUser> getPu() {
		return pu;
	}
	public void setPu(List<ProUser> pu) {
		this.pu = pu;
	}
	public List<Evaluate> getEvaluates() {
		return evaluates;
	}
	public void setEvaluates(List<Evaluate> evaluates) {
		this.evaluates = evaluates;
	}
	public int getPro() {
		return pro;
	}
	public void setPro(int pro) {
		this.pro = pro;
	}
	public String getBlockState() {
		return blockState;
	}
	public void setBlockState(String blockState) {
		this.blockState = blockState;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}

	//haokun added 2017/02/24 昊坤增加获取封面图片
	public String getCoverUrl() {
		return coverUrl;
	}
	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}
	
}
