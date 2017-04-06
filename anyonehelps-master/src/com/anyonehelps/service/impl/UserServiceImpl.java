package com.anyonehelps.service.impl;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anyonehelps.common.constants.Constant;
import com.anyonehelps.common.constants.ResponseCode;
import com.anyonehelps.common.util.Base64Util;
import com.anyonehelps.common.util.DateUtil;
import com.anyonehelps.common.util.ExceptionUtil;
import com.anyonehelps.common.util.MD5Util;
import com.anyonehelps.common.util.PropertiesReader;
import com.anyonehelps.common.util.StringUtil;
import com.anyonehelps.common.util.UserUtil;
import com.anyonehelps.common.util.sms.SmsConfigUtil;
import com.anyonehelps.dao.AccountDao;
import com.anyonehelps.dao.AccountDetailDao;
import com.anyonehelps.dao.AccountSecurityQuestionDao;
import com.anyonehelps.dao.AdminRecommendDao;
import com.anyonehelps.dao.DemandDao;
import com.anyonehelps.dao.EmailSendDao;
import com.anyonehelps.dao.EmailSubscribeDao;
import com.anyonehelps.dao.FriendDao;
import com.anyonehelps.dao.IPRecordDao;
import com.anyonehelps.dao.LoginInfoDao;
import com.anyonehelps.dao.MessageSystemDao;
import com.anyonehelps.dao.RecommendDao;
import com.anyonehelps.dao.SmsSendDao;
import com.anyonehelps.dao.UserCommentDao;
import com.anyonehelps.dao.UserDao;
import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.Account;
import com.anyonehelps.model.Demand;
import com.anyonehelps.model.EmailSend;
import com.anyonehelps.model.EmailSubscribe;
import com.anyonehelps.model.IPRecord;
import com.anyonehelps.model.MessageSystem;
import com.anyonehelps.model.PageSplit;
import com.anyonehelps.model.Recommend;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.model.SmsSend;
import com.anyonehelps.model.User;
import com.anyonehelps.service.UserService;

@Service("userService")
public class UserServiceImpl extends BasicService implements UserService {
	private static final Logger log = Logger.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private AccountDao accountDao;
	//@Autowired
	//private AccountDetailDao accountDetailDao;
	@Autowired
	private LoginInfoDao loginInfoDao;
	
	@Autowired
	private DemandDao demandDao;
	
	@Autowired
	private RecommendDao recommendDao;
	@Autowired
	private FriendDao friendDao;
	@Autowired
	private AccountDetailDao accountDetailDao;
	@Autowired
	private UserCommentDao userCommentDao;
	
	@Autowired
	private AdminRecommendDao adminRecommendDao;

	@Autowired
	private AccountSecurityQuestionDao accountSecurityQuestionDao;

	@Autowired
	private EmailSubscribeDao emailSubscribeDao;
	
	@Autowired
	private IPRecordDao ipRecordDao;
	
	@Autowired
	private SmsSendDao smsSendDao;
	
	@Autowired
	private EmailSendDao emailSendDao;
	

	@Autowired
	private MessageSystemDao messageSystemDao;
	
	public User getUserByWxId(String wxId) throws ServiceException {
		if (StringUtil.isEmpty(wxId)) {
			return null;
		} else {
			try {
				User user = this.userDao.getUserByWxId(wxId);
				return user;
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException(e);
			}
		}
	}
	
	public User getUserByFbId(String fbId) throws ServiceException {
		if (StringUtil.isEmpty(fbId)) {
			return null;
		} else {
			try {
				User user = this.userDao.getUserByFbId(fbId);
				return user;
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException(e);
			}
		}
	}
	
	public User getUserByAccount(String account) throws ServiceException {
		if (StringUtil.isEmpty(account)) {
			return null;
		} else {
			try {
				User user = this.userDao.getUserByAccount(account);
				return user;
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException(e);
			}
		}
	}
	
	public boolean checkExistsByName(String username) throws ServiceException {
		if (StringUtil.isEmpty(username)) {
			return false;
		} else {
			try {
				User user = this.userDao.getUserByAccount(username);
				if (user != null && username.equalsIgnoreCase(user.getNickName())) {
					// this user is exists
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException(e);
			}
		}
	}
	public boolean checkExistsByEmail(String email) throws ServiceException {
		if (StringUtil.isEmpty(email)) {
			return false;
		} else {
			try {
				User user = this.userDao.getUserByAccount(email);
				if (user != null && email.equalsIgnoreCase(user.getEmail())) {
					// this user is exists
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException(e);
			}
		}
	}
	
	public boolean checkExistsByPhone(String areaCode, String phone) throws ServiceException {
		if (StringUtil.isEmpty(phone)) {
			return false;
		} else {
			try {
				User user = this.userDao.getUserByPhone(areaCode, phone);
				
				//User user = this.userDao.getUserByAccount(phone);
				if (user != null) {
					// this user is exists
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException(e);
			}
		}
	}


	public ResponseObject<User> checkLogin(String account, String password, String ip)
			throws ServiceException {
		ResponseObject<User> result = new ResponseObject<User>();
		try {
			String pwd = MD5Util.encode(password);
			User user = this.userDao.getUserByAccount(account);
		
			if (user != null && pwd.equals(user.getPassword())  
			        && (account.equals(user.getNickName())
			        		||account.equals(user.getEmail())
			        		||account.equals(user.getTelphone()))) {
				if(Constant.USER_BLOCK_STATE1.equals(user.getBlockState())){
					result.setCode(ResponseCode.USER_BLOCK_STATE1);
					result.setMessage("您目前违反用户协议，不能登录，如有疑问请联系客服！");
				}else{
					result.setCode(ResponseCode.SUCCESS_CODE);
					result.setData(getSecurityValue(user));
					this.loginInfoDao.insertUpdate(user.getId(), "0", DateUtil.date2String(new Date()));
				}
				
			} else {
				result.setCode(ResponseCode.USER_LOGIN_ACCOUNT_ERROR);
				result.setMessage("账户或者密码错误");
				IPRecord ipRecord = new IPRecord();
				ipRecord.setIp(ip);
				ipRecord.setType(Constant.IPRECORD_TYPE1);
				ipRecord.setCreateDate(DateUtil.date2String(new Date()));
				this.ipRecordDao.insert(ipRecord);
				int nResult = this.ipRecordDao.countByIP(Constant.IPRECORD_TYPE1, ip);
				if(nResult>2){
					result.setCode(ResponseCode.USER_LOGIN_NEEL_CODE);
				}
			}
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException("检验用户登录失败", e);
		}
		return result;
	}

	public ResponseObject<User> getUserById(String id) throws ServiceException {
		ResponseObject<User> result = new ResponseObject<User>();
		if (StringUtil.isEmpty(id)) {
			result.setCode(ResponseCode.PARAMETER_ERROR);
			result.setMessage("参数无效");
		} else {
			try {
				User user = this.userDao.getUserById(id);
				if (user != null) {
					user.setSecurityQuestion(String.valueOf(accountSecurityQuestionDao.getCountByUserId(user.getId())));
					result.setCode(ResponseCode.SUCCESS_CODE);
					result.setData(getSecurityValue(user));
				} else {
					result.setCode(ResponseCode.USER_ID_EXISTS);
					result.setMessage("数据库中没有对应id的用户，ID:" + id);
				}
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException(e);
			}
		}
		return result;
	}

	public ResponseObject<Map<String,Object>> getUserOpenInfoById(String id,String selfId) throws ServiceException {
		ResponseObject<Map<String,Object>> result = new ResponseObject<Map<String,Object>>();
		if (StringUtil.isEmpty(id)) {
			result.setCode(ResponseCode.PARAMETER_ERROR);
			result.setMessage("参数无效");
		} else {
			try {
				Map<String,Object> map = new HashMap<String,Object>();
				User user = this.userDao.getUserOpenInfoById(id);
				List<String> states = new ArrayList<String>();
				states.add(Constant.DEMAND_STATE_END);
				int nfinishCount = this.demandDao.countByRDReceiverId(id, states);
				int nPublishFinishCount = this.demandDao.countByUserIdAndState(id, states);
				
				double countAmount = this.accountDetailDao.sumAmountByType(id,Constant.ACCOUNT_DETAIL_TYPE22);
				double poundageAmount = this.accountDetailDao.sumAmountByType(id, Constant.ACCOUNT_DETAIL_TYPE38);
				
				map.put("finishCount",nfinishCount + nPublishFinishCount);
				map.put("countAmount",countAmount - poundageAmount);
				map.put("user",user);
				map.put("CountPulished",this.demandDao.countByKey(id, "",null));
				map.put("CountReceive",this.demandDao.countByRDReceiverId(id,null));
				if (user != null) {
					if(this.userDao.getUserIsOpen(selfId, id)<=0){
						if(user.getTelphone().length()>4){ //保留头两位和结尾两位
							String index = user.getTelphone().substring(0,2);
							String last = user.getTelphone().substring(user.getTelphone().length()-2);
							user.setTelphone(index+"********"+last);
						}else{
							user.setTelphone("********");
						}
						if(user.getEmail().length()>4){ //保留头两位和结尾两位
							String index = user.getEmail().substring(0,2);
							String last = user.getEmail().substring(user.getEmail().length()-2);
							user.setEmail(index+"********"+last);
						}else{
							user.setEmail("********");
						}
						
					}
					if(selfId!=null&&!"".equals(selfId)){
						user.setFollow(String.valueOf(this.friendDao.countByUIdAndFUId(id, selfId)));
					}
					result.setCode(ResponseCode.SUCCESS_CODE);
					result.setData(map);
				} else {
					result.setCode(ResponseCode.USER_ID_EXISTS);
					result.setMessage("数据库中没有对应的用户");
				}
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException(e);
			}
		}
		return result;
	}
	
	public ResponseObject<User> getUserOpenInfoByAccount(String account,String selfId) throws ServiceException {
		ResponseObject<User> result = new ResponseObject<User>();
		if (StringUtil.isEmpty(account)) {
			result.setCode(ResponseCode.PARAMETER_ERROR);
			result.setMessage("参数无效");
		} else {
			try {
				User user = this.userDao.getUserOpenInfoByAccount(account);
				if (user != null) {
					result.setCode(ResponseCode.SUCCESS_CODE);
					result.setData(user);
				} else {
					result.setCode(ResponseCode.USER_ID_EXISTS);
					result.setMessage("用户:" + account+"不存在");
				}
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException(e);
			}
		}
		return result;
	}
	
	public ResponseObject<Object> deleteUserByIds(String[] ids)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	public ResponseObject<Object> modifyUser(User user) throws ServiceException {
		ResponseObject<Object> result = new ResponseObject<Object>();
		if (user == null) {
			result.setCode(ResponseCode.PARAMETER_ERROR);
			result.setMessage("修改用户参数错误");
		} else {
			try {
				int iresult = this.userDao.updateUserById(user);
				if (iresult > 0) {
					result.setCode(ResponseCode.SUCCESS_CODE);
				} else {
					result.setCode(ResponseCode.USER_MODIFY_FAILURE);
					result.setMessage("修改用户信息失败，请重新尝试！");
				}
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException(e);
			}
		}
		return result;
	}
	
	public ResponseObject<Object> modifyPasswordByUser(String id,
			String password, String oldPassword) throws ServiceException {
		ResponseObject<Object> result = new ResponseObject<Object>();
		if (StringUtil.isEmpty(password)) {
			result.setCode(ResponseCode.PARAMETER_ERROR);
			result.setMessage("无效的参数");
		} else {
			try {
				User user = this.userDao.getUserById(id);
				if((user==null)||(user.equals("")))
				{
					result.setCode(ResponseCode.USER_MODIFY_FAILURE);
					result.setMessage("获取用户信息错误！");
					return result;
				}
				if(!user.getPassword().equals(MD5Util.encode(oldPassword)))
				{
					result.setCode(ResponseCode.USER_MODIFY_FAILURE);
					result.setMessage("旧密码不正确，请输入正确的旧密码！");
					return result;
				}
				int iresult = this.userDao.updatePassword(id, null, null, MD5Util.encode(password), null);
				if (iresult > 0) {
					
					String date = DateUtil.date2String(new Date());
					if(UserUtil.validatePhone(user.getTelphone())){
						Properties prop = null;
						String content = null;
						if(user.getAreaCode().equals("+86")|| user.getAreaCode().equals("0086")){
							prop = PropertiesReader.read(SmsConfigUtil.getSmsConfigFile(Constant.COUNTRY_CODE_CN));
							content = prop.getProperty("anyonehelps.modify.password.content");
						}else{
							prop = PropertiesReader.read(SmsConfigUtil.getSmsConfigFile(Constant.COUNTRY_CODE_US));
							content = prop.getProperty("anyonehelps.modify.password.content");
						}
						
						SmsSend ss = new SmsSend();
						ss.setContent(content); 
						ss.setFailCount(Constant.EMAILSEND_FAIL_COUNT0);
						ss.setState(Constant.EMAILSEND_STATE0);
						ss.setAreaCode(user.getAreaCode());
						ss.setTelphone(user.getTelphone());
						ss.setUserId(user.getId());
						ss.setCreateDate(date);
						ss.setModifyDate(date);
						smsSendDao.insert(ss);
					}
					
					if(user.getEmail()!=null){
						Properties prop = PropertiesReader.read(Constant.EMAIL_PROPERTIES_FILE);
						String subject = prop.getProperty("anyonehelps.modify.password.subject");
						String content = prop.getProperty("anyonehelps.modify.password.content");
				
						EmailSend es = new EmailSend();
						es.setContent(content);
						es.setCreateDate(date);
						es.setEmail(user.getEmail());
						es.setFailCount(Constant.EMAILSEND_FAIL_COUNT0);
						es.setModifyDate(date);
						es.setState(Constant.EMAILSEND_STATE0);
						es.setSubject(subject);
						es.setUserId(user.getId());
						this.emailSendDao.insert(es); 
					}
					
					result.setCode(ResponseCode.SUCCESS_CODE);
				} else {
					result.setCode(ResponseCode.USER_MODIFY_FAILURE);
					result.setMessage("修改失败，请重试！");
				}
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException(e);
			}
		}
		return result;
	}
	
	public ResponseObject<Object> modifyPasswordByPhone(String areaCode, String phone, 
			String password) throws ServiceException {
		ResponseObject<Object> result = new ResponseObject<Object>();
		if (StringUtil.isEmpty(password)) {
			result.setCode(ResponseCode.PARAMETER_ERROR);
			result.setMessage("无效的参数");
		} else {
			try {
				User user = this.userDao.getUserByPhone(areaCode, phone);
				if(user == null){
					result.setCode(ResponseCode.USER_MODIFY_FAILURE);
					result.setMessage("手机用户不存在！");
					return result;
				}
				
				int nResult = this.userDao.updatePasswordByPhone(areaCode, phone, MD5Util.encode(password));
				
				if (nResult > 0) {
					result.setCode(ResponseCode.SUCCESS_CODE);
					result.setData(user);
				} else {
					result.setCode(ResponseCode.USER_MODIFY_FAILURE);
					result.setMessage("没有对应手机账号");
				}
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException(e);
			}
		}
		return result;
	}
	
	public ResponseObject<Object> modifyPasswordByEmail(String id, String email, String password, String oldPassword)
	        throws ServiceException {
		ResponseObject<Object> result = new ResponseObject<Object>();
		if (StringUtil.isEmpty(password)) {
			result.setCode(ResponseCode.PARAMETER_ERROR);
			result.setMessage("无效的参数");
		} else {
			try {
				int iresult = this.userDao.updatePasswordByEmail(id, email, MD5Util.encode(password), MD5Util
				        .encode(oldPassword));
				if (iresult > 0) {
					result.setCode(ResponseCode.SUCCESS_CODE);
				} else {
					result.setCode(ResponseCode.USER_MODIFY_FAILURE);
					result.setMessage("数据库中没有对应账号的用户");
				}
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException(e);
			}
		}
		return result;
	}

	public ResponseObject<Object> addUser(User user) throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>();
		if (null == user) {
			responseObj.setCode(ResponseCode.PARAMETER_ERROR);
			responseObj.setMessage("参数无效");
		} else {
			String date = DateUtil.date2String(new Date());
			String pass = user.getPassword();
			user.setPassword(MD5Util.encode(user.getPassword()));
			user.setSignDate(date);
			
			MessageSystem ms = null;
			
			try {
				if(user.getRecommender().equals("-1")){
					if(user.getEmail()!=null&&user.getEmail()!=""){
						Recommend r = this.recommendDao.searchByEmail(user.getEmail());
						if(r!=null){ //有人邀请的
							user.setRecommender(r.getUserId());
							this.recommendDao.modifyState(date, Constant.RECOMMEND_STATE1, r.getId());
							
							ms= new MessageSystem();
							ms.setContent("您邀请用户"+user.getEmail()+",已经注册成功,点击查看他（她）的主页");
							ms.setTitle("您邀请用户"+user.getEmail()+",已经注册成功");
							ms.setUserId(r.getUserId());
						}
					}else if(user.getAreaCode()!=null&&!user.getAreaCode().equals("")&&user.getTelphone()!=null&&!user.getTelphone().equals("")){
						Recommend r = this.recommendDao.searchByPhone(user.getAreaCode(), user.getTelphone());
						if(r!=null){ //有人邀请的
							user.setRecommender(r.getUserId());
							this.recommendDao.modifyState(date, Constant.RECOMMEND_STATE1, r.getId());
							
							ms= new MessageSystem();
							ms.setContent("您邀请用户"+user.getEmail()+",已经注册成功,点击查看他（她）的主页");
							ms.setTitle("您邀请用户"+user.getEmail()+",已经注册成功");
							ms.setUserId(r.getUserId());
						}
					}
					
				}else {
					if(user.getEmail()!=null&&user.getEmail()!=""){

						Recommend r = this.recommendDao.getByUserIdAndEmail(user.getRecommender(), user.getEmail());
						if(r!=null){ //有人邀请的
							user.setRecommender(r.getUserId());
							this.recommendDao.modifyState(date, Constant.RECOMMEND_STATE1, r.getId());
						}else {
							Recommend recommend = new Recommend();
							recommend.setEmail(user.getEmail());
							recommend.setAreaCode("");
							recommend.setTelphone("");
							recommend.setUserId(user.getRecommender());
							recommend.setModifyDate(date);
							recommend.setCreateDate(date);
							recommend.setState(Constant.RECOMMEND_STATE1);
							recommend.setSmsState(Constant.RECOMMEND_SMS_STATE1);
							List<Recommend> recommendList = new ArrayList<Recommend>();
							recommendList.add(recommend);
							this.recommendDao.insertRecommends(recommendList);
						}
						ms= new MessageSystem();
						ms.setContent("您邀请用户"+user.getEmail()+",已经注册成功,点击查看他（她）的主页");
						ms.setTitle("您邀请用户"+user.getEmail()+",已经注册成功");
						ms.setUserId(user.getRecommender());
					}else if(user.getAreaCode()!=null&&!user.getAreaCode().equals("")&&user.getTelphone()!=null&&!user.getTelphone().equals("")){
						Recommend r = this.recommendDao.getByUserIdAndPhone(user.getRecommender(), user.getAreaCode(), user.getTelphone());
						if(r!=null){ //有人邀请的
							user.setRecommender(r.getUserId());
							this.recommendDao.modifyState(date, Constant.RECOMMEND_STATE1, r.getId());
						}else {
							Recommend recommend = new Recommend();
							recommend.setEmail("");
							recommend.setAreaCode(user.getAreaCode());
							recommend.setTelphone(user.getTelphone());
							recommend.setUserId(user.getRecommender());
							recommend.setModifyDate(date);
							recommend.setCreateDate(date);
							recommend.setState(Constant.RECOMMEND_STATE1);
							recommend.setSmsState(Constant.RECOMMEND_SMS_STATE1);
							List<Recommend> recommendList = new ArrayList<Recommend>();
							recommendList.add(recommend);
							this.recommendDao.insertRecommends(recommendList);
							
						}
						ms= new MessageSystem();
						ms.setContent("您邀请用户"+user.getEmail()+",已经注册成功,点击查看他（她）的主页");
						ms.setTitle("您邀请用户"+user.getEmail()+",已经注册成功");
						ms.setUserId(user.getRecommender());
					}
				}
				
				int iresult = this.userDao.insertUser(user);
				if (iresult > 0) {
					// 创建一个账户
					Account account = new Account();
					account.setUserId(user.getId());
					account.setUsd("0");
					account.setForwardUsd("0");
					account.setBonusPoint("0");
					account.setModifyDate(date);
					account.setCreateDate(date);
					this.accountDao.insertOrUpdateAccount(account);
					Map<String, String> map = new HashMap<String, String>();
					map.put("id", user.getId());
					responseObj.setCode(ResponseCode.SUCCESS_CODE);
					responseObj.setData(map);
					if(UserUtil.validatePhone(user.getTelphone())){
						//SmsSendUtil.sendNewUserMsg(pass, user.getAreaCode(), user.getTelphone());
						Properties prop = null;
						String content = null;
						if(user.getAreaCode().equals("+86")|| user.getAreaCode().equals("0086")){
							prop = PropertiesReader.read(SmsConfigUtil.getSmsConfigFile(Constant.COUNTRY_CODE_CN));
							content = prop.getProperty("anyonehelps.new.user.content");
							content = MessageFormat.format(content, new Object[] {pass});
						}else{
							prop = PropertiesReader.read(SmsConfigUtil.getSmsConfigFile(Constant.COUNTRY_CODE_US));
							content = prop.getProperty("anyonehelps.new.user.content");
							content = MessageFormat.format(content, new Object[] {pass});
						}
						
						SmsSend ss = new SmsSend();
						ss.setContent(content);
						ss.setFailCount(Constant.EMAILSEND_FAIL_COUNT0);
						ss.setState(Constant.EMAILSEND_STATE0);
						ss.setAreaCode(user.getAreaCode());
						ss.setTelphone(user.getTelphone());
						ss.setUserId(user.getId());
						ss.setCreateDate(date);
						ss.setModifyDate(date);
						int n = smsSendDao.insert(ss);
						if(n>0){
							//pass
						}else{
							throw new Exception("短信发送失败");
						}
					}
					
					if(user.getEmail()!=null){
						Properties prop = PropertiesReader.read(Constant.EMAIL_PROPERTIES_FILE);
						String subject = prop.getProperty("anyonehelps.email.new.user.subject");
						String content = prop.getProperty("anyonehelps.email.new.user.content");
						content = MessageFormat.format(content, new Object[] { user.getNickName().isEmpty()?user.getEmail():user.getNickName()});
				
						EmailSend es = new EmailSend();
						es.setContent(content);
						es.setCreateDate(date);
						es.setEmail(user.getEmail());
						es.setFailCount(Constant.EMAILSEND_FAIL_COUNT0);
						es.setModifyDate(date);
						es.setState(Constant.EMAILSEND_STATE0);
						es.setSubject(subject);
						es.setUserId(user.getId());
						
						this.emailSendDao.insert(es); 
					}
					
					
					/*推荐人系统推送 start*/
					if(ms!=null&&ms.getUserId()!=null&&!"".equals(ms.getUserId())){
						ms.setLink("/profile.jsp?userId=" + Base64Util.encode(user.getId()));
						ms.setCreateDate(date);
						ms.setModifyDate(date);
						ms.setLevel(Constant.MESSAGESYSTEM_LEVEL_INFO);
						ms.setState(Constant.MESSAGEUSER_STATE_UNREAD);
						this.messageSystemDao.insertMessage(ms);
					}
					
					/*推荐人系统推送 end*/
					
				} else {
					responseObj.setCode(ResponseCode.USER_INSERT_ERROR);
					responseObj.setMessage("添加新用户保存到数据库中失败");
				}
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException(e);
			}
		}
		return responseObj;
	} 
	
	public ResponseObject<Object> modifyUserPhone(String userId, String areaCode, String telphone, String telphoneState)
	        throws ServiceException{
		ResponseObject<Object> result = new ResponseObject<Object>();
		if (StringUtil.isEmpty(telphone)) {
			result.setCode(ResponseCode.PARAMETER_ERROR);
			result.setMessage("无效的参数");
		} else {
			try {
				User user= this.userDao.getUserById(userId);
				if((user==null)||(user.equals("")))
				{
					result.setCode(ResponseCode.USER_MODIFY_FAILURE);
					result.setMessage("获取用户信息错误！");
					return result;
				}
			    if((user.getTelphoneState()!=null)&&(user.getTelphoneState().equalsIgnoreCase(Constant.USER_TELPHONE_STATE1)))
			    {
			    	result.setCode(ResponseCode.USER_MODIFY_FAILURE);
					result.setMessage("手机已经校验过，不能修改！");
					return result;
			    }
			    if (this.checkExistsByPhone(areaCode, telphone)) { 
			    	result.setCode(ResponseCode.USER_PHONE_EXISTS);
					result.setMessage("手机已经验证过其他帐号，不能再验证！");
					return result;
			    }
			    
			    int nResult = this.userDao.updateUserPhoneById(userId, areaCode, telphone, telphoneState);
				
				if (nResult > 0) {
					result.setCode(ResponseCode.SUCCESS_CODE);
					/*String date = DateUtil.date2String(new Date());
					
					if(UserUtil.validatePhone(user.getTelphone())){
						Properties prop = null;
						String content = null;
						if(user.getAreaCode().equals("+86")|| user.getAreaCode().equals("0086")){
							prop = PropertiesReader.read(SmsConfigUtil.getSmsConfigFile(Constant.COUNTRY_CODE_CN));
							content = prop.getProperty("anyonehelps.user.modify.phone.content");
							content = MessageFormat.format(content, new Object[] {areaCode+areaCode});
						}else{
							prop = PropertiesReader.read(SmsConfigUtil.getSmsConfigFile(Constant.COUNTRY_CODE_US));
							content = prop.getProperty("anyonehelps.new.user.content");
							content = MessageFormat.format(content, new Object[] {areaCode+areaCode});
						}
						
						SmsSend ss = new SmsSend();
						ss.setContent(content);
						ss.setFailCount(Constant.EMAILSEND_FAIL_COUNT0);
						ss.setState(Constant.EMAILSEND_STATE0);
						ss.setAreaCode(user.getAreaCode());
						ss.setTelphone(user.getTelphone());
						ss.setUserId(user.getId());
						ss.setCreateDate(date);
						ss.setModifyDate(date);
						int n = smsSendDao.insert(ss);
						if(n>0){
							//pass
						}else{
							throw new Exception("短信发送失败");
						}
					}
					
					if(user.getEmail()!=null){
						Properties prop = PropertiesReader.read(Constant.EMAIL_PROPERTIES_FILE);
						String subject = prop.getProperty("anyonehelps.email.user.modify.phone.subject");
						String content = prop.getProperty("anyonehelps.email.user.modify.phone.content");
						content = MessageFormat.format(content, new Object[] {areaCode+areaCode});
				
						EmailSend es = new EmailSend();
						es.setContent(content);
						es.setCreateDate(date);
						es.setEmail(user.getEmail());
						es.setFailCount(Constant.EMAILSEND_FAIL_COUNT0);
						es.setModifyDate(date);
						es.setState(Constant.EMAILSEND_STATE0);
						es.setSubject(subject);
						es.setUserId(user.getId());
						
						this.emailSendDao.insert(es); 
					}*/
				} else {
					result.setCode(ResponseCode.USER_MODIFY_FAILURE);
					result.setMessage("验证手机失败，请刷新后再操作!");
				}
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException(e);
			}
		}
		return result;
		
	}
	public ResponseObject<Object> modifyUserEmail(String userId, String email,
			String emailState)
	        throws ServiceException{
		ResponseObject<Object> result = new ResponseObject<Object>();
		if (StringUtil.isEmpty(email)) {
			result.setCode(ResponseCode.PARAMETER_ERROR);
			result.setMessage("无效的参数");
		} else {
			try {
				User user= this.userDao.getUserById(userId);
				if((user==null)||(user.equals("")))
				{
					result.setCode(ResponseCode.USER_MODIFY_FAILURE);
					result.setMessage("获取用户信息错误！");
					return result;
				}
			    if((user.getEmailState()!=null)&&(user.getEmailState().equalsIgnoreCase(Constant.USER_EMAIL_STATE1)))
			    {
			    	result.setCode(ResponseCode.USER_MODIFY_FAILURE);
					result.setMessage("邮箱已经校验过，不能修改！");
					return result;
			    }
			    User u = this.userDao.getUserByAccount(email);
			    if (u!=null&&!u.getId().equals(userId)) { 
			    	result.setCode(ResponseCode.USER_EMAIL_EXISTS);
					result.setMessage("邮箱已经验证过其他帐号，不能再验证！");
					return result;
			    }
			    
			    int iresult = this.userDao.updateUserEmailById(userId,email,emailState);
				if (iresult > 0) {
					result.setCode(ResponseCode.SUCCESS_CODE);
					if(!email.equals(user.getEmail())){
						result.setMessage("邮箱验证成功，请使用新邮箱登录!");
					}else{
						result.setMessage("邮箱验证成功!");
					}
				} else {
					result.setCode(ResponseCode.USER_MODIFY_FAILURE);
					result.setMessage("验证邮箱失败，请刷新后再操作!");
				}
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException(e);
			}
		}
		return result;
		
	}
	@Override
	public ResponseObject<PageSplit<User>> getBySumAmount(String userId, String key, int pageNow,
	        int pageSize) throws ServiceException {
		ResponseObject<PageSplit<User>> responseObj = new ResponseObject<PageSplit<User>>(ResponseCode.SUCCESS_CODE);
	
		try {
			key = StringUtil.escapeStringOfSearchKey(key);  
			int rowCount = 0;
			try {
				rowCount = this.userDao.countUserByFamousEnable(key) + this.userDao.getCountUserBySumAmount(key);
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException("获取名人堂总数失败", e);
			}
			
			if (rowCount > 0) {
				pageSize = Math.max(pageSize, 1);
				int pageCount = rowCount / pageSize + (rowCount % pageSize == 0 ? 0 : 1);
				pageNow = Math.min(pageNow, pageCount);
				PageSplit<User> pageSplit = new PageSplit<User>();
				pageSplit.setPageCount(pageCount);
				pageSplit.setPageNow(pageNow);
				pageSplit.setRowCount(rowCount);
				pageSplit.setPageSize(pageSize);

				int startIndex = (pageNow - 1) * pageSize;
				try {
					int count = 0;
					List<User> users = this.userDao.getUserByFamousEnable(key, startIndex, pageSize);
					//List<User> users = this.userDao.getUserBySumAmount(key, startIndex, pageSize);
					if(users == null){
						count = 0;
					}else{
						count = users.size();
					}
					List<User> tempUsers = null;
					log.error("count:"+count);
					log.error("pageSize:"+pageSize);
					if(count!=pageSize){
						tempUsers = this.userDao.getUserBySumAmount(key, startIndex, pageSize-count);
					}
					if (tempUsers != null && !tempUsers.isEmpty()) {
						for (User u : tempUsers) {
							users.add(u);
						}
					}
					if (users != null && !users.isEmpty()) {
						for (User u : users) {
							//去掉不必要的信息
							u.setAbilityCertificateUrl("");
							u.setCity("");
							u.setCountry("");
							u.setEmail("");
							u.setEmailState("");
							u.setMajor("");
							u.setPassword("");
							u.setProvince("");
							u.setOtherContact("");
							u.setRecommender("");
							u.setSchool("");
							u.setSignDate("");
							u.setTelphone("");
							u.setTelphoneState("");
							u.setType("");
							u.setUsdBalance("");
							u.setForwardUsd("");
							
							if(userId!=null&&!"".equals(userId)){
								u.setFollow(String.valueOf(this.friendDao.countByUIdAndFUId(u.getId(), userId)));
							}
							
							pageSplit.addData(u);
						}
					}
					
				} catch (Exception e) {
					throw ExceptionUtil.handle2ServiceException("获取没有名人堂失败", e);
				}
				responseObj.setData(pageSplit);
			}else{
				responseObj.setMessage("没有名人堂");
			}
			return responseObj;
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
		
	}
	@Override
	public ResponseObject<Object> modifyUserPic(String userId,
			String picUrl) throws ServiceException {
		ResponseObject<Object> result = new ResponseObject<Object>();
		if (StringUtil.isEmpty(picUrl)||StringUtil.isEmpty(userId)) {
			result.setCode(ResponseCode.PARAMETER_ERROR);
			result.setMessage("无效的参数");
			return result;
		} 
		
		try {

			int iresult = this.userDao.updatePicUrlById(userId, picUrl);
			if (iresult > 0) {
				result.setCode(ResponseCode.SUCCESS_CODE);
			} else {
				result.setCode(ResponseCode.USER_PIC_UPDATE_ERROR);
				result.setMessage("修改头像错误，靖重试!");
			}
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public <T> ResponseObject<PageSplit<T>> searchByRecommender(String userId, int pageSize, int pageNow)
	        throws ServiceException {
		try {
			int rowCount = 0;
			try {
				rowCount = this.userDao.countByRecommender(userId);
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException("获取邀请人个数失败", e);
			}

			ResponseObject<PageSplit<T>> responseObj = new ResponseObject<PageSplit<T>>(ResponseCode.SUCCESS_CODE);
			if (rowCount > 0) {
				pageSize = Math.max(pageSize, 1);
				int pageCount = rowCount / pageSize + (rowCount % pageSize == 0 ? 0 : 1);
				pageNow = Math.min(pageNow, pageCount);
				PageSplit<T> pageSplit = new PageSplit<T>();
				pageSplit.setPageCount(pageCount);
				pageSplit.setPageNow(pageNow);
				pageSplit.setRowCount(rowCount);
				pageSplit.setPageSize(pageSize);

				int startIndex = (pageNow - 1) * pageSize;
				try {
					List<User> users = this.userDao.searchUserByRecommender(userId, startIndex, pageSize);
					if (users != null && !users.isEmpty()) {
						for (User u : users) {
							pageSplit.addData((T) getSecurityValue(u));
						}
					}
				} catch (Exception e) {
					throw ExceptionUtil.handle2ServiceException("获取邀请人列表失败", e);
				}
				responseObj.setData(pageSplit);
			} else {
				responseObj.setMessage("没有邀请人");
			}
			return responseObj;
		} catch (ServiceException e) {
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> ResponseObject<PageSplit<T>> searchUserByKey(String userId, String key, String sortType, String nationality,String specialtyType,
			String specialtyId, int pageSize, int pageNow) throws ServiceException {
		try {
			key = StringUtil.escapeStringOfSearchKey(key);  
			int rowCount = 0;
			try {
				rowCount = this.userDao.countUserByKey(key, nationality,specialtyType, specialtyId);
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException("获取人物个数失败", e);
			}

			ResponseObject<PageSplit<T>> responseObj = new ResponseObject<PageSplit<T>>(ResponseCode.SUCCESS_CODE);
			if (rowCount > 0) {
				pageSize = Math.max(pageSize, 1);
				int pageCount = rowCount / pageSize + (rowCount % pageSize == 0 ? 0 : 1);
				pageNow = Math.min(pageNow, pageCount);
				PageSplit<T> pageSplit = new PageSplit<T>();
				pageSplit.setPageCount(pageCount);
				pageSplit.setPageNow(pageNow);
				pageSplit.setRowCount(rowCount);
				pageSplit.setPageSize(pageSize);

				int startIndex = (pageNow - 1) * pageSize;
				try {
					List<User> users = this.userDao.searchUserByKey(key, sortType, nationality, specialtyType,specialtyId, startIndex, pageSize);
					if (users != null && !users.isEmpty()) {
						for (User u : users) {
							u.setCommentCount(String.valueOf(this.userCommentDao.countByKey("", u.getId())));
							
							if(userId!=null&&!"".equals(userId)){
								u.setFollow(String.valueOf(this.friendDao.countByUIdAndFUId(u.getId(), userId)));
							}
							
							pageSplit.addData((T) getSecurityValue(u));
							
						}
					}
				} catch (Exception e) {
					throw ExceptionUtil.handle2ServiceException("获取人物列表失败", e);
				}
				responseObj.setData(pageSplit);
			} else {
				responseObj.setMessage("没有人物");
			}
			return responseObj;
		} catch (ServiceException e) {
			throw e;
		}
	}

	@Override
	public ResponseObject<User> modifyRecommender(String userId,
			String account) throws ServiceException {
		ResponseObject<User> result = new ResponseObject<User>();
		if (StringUtil.isEmpty(account)||StringUtil.isEmpty(userId)) {
			result.setCode(ResponseCode.PARAMETER_ERROR);
			result.setMessage("参数错误");
		} else {
			try {
				User my = this.userDao.getUserById(userId);
				User user = this.userDao.getUserByAccount(account);
				if(user==null){
					result.setCode(ResponseCode.PARAMETER_ERROR);
					result.setMessage("没有该用户!");
				}else if(!my.getRecommender().equals("-1")){
					result.setCode(ResponseCode.PARAMETER_ERROR);
					result.setMessage("你已经提交过邀请您的用户了，不能再次提交，如有疑问，请联系客服!");
				}else if(user.getId().equals(userId)){
					result.setCode(ResponseCode.PARAMETER_ERROR);
					result.setMessage("不能提交自己!");
				}else{
					//还应该判断用户的注册时间 ，如果超过一个月，就不能再补充邀请我的用户
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
					Date signDate = df.parse(my.getSignDate());  
		            long signTime = signDate.getTime();  
		            long currTime = new Date().getTime();  
		            
		            if((currTime - signTime)/(1000*60*60*24)>31) { 
		            	result.setCode(ResponseCode.PARAMETER_ERROR);
						result.setMessage("注册时间已经超过一个月，无法再补充邀请人!");
		            } else {  
						int nResult = this.userDao.modifyRecommender(user.getId(), userId);
						if(nResult>0){
							String date = DateUtil.date2String(new Date());
							if(my.getEmail()!=null&&my.getEmail()!=""){
								Recommend r = this.recommendDao.getByUserIdAndEmail(user.getId(), my.getEmail());
								if(r!=null){ //有人邀请的
									this.recommendDao.modifyState(date, Constant.RECOMMEND_STATE1, r.getId());
								}else {
									Recommend recommend = new Recommend();
									recommend.setEmail(my.getEmail());
									recommend.setAreaCode("");
									recommend.setTelphone("");
									recommend.setUserId(user.getId());
									recommend.setModifyDate(date);
									recommend.setCreateDate(date);
									recommend.setState(Constant.RECOMMEND_STATE1);
									recommend.setSmsState(Constant.RECOMMEND_SMS_STATE1);
									List<Recommend> recommendList = new ArrayList<Recommend>();
									recommendList.add(recommend);
									this.recommendDao.insertRecommends(recommendList);
								}
							}else if(my.getAreaCode()!=null&&!my.getAreaCode().equals("")&&my.getTelphone()!=null&&!my.getTelphone().equals("")){
								Recommend r = this.recommendDao.getByUserIdAndPhone(user.getId(), my.getAreaCode(), my.getTelphone());
								if(r!=null){ //有人邀请的
									this.recommendDao.modifyState(date, Constant.RECOMMEND_STATE1, r.getId());
								}else {
									Recommend recommend = new Recommend();
									recommend.setEmail("");
									recommend.setAreaCode(my.getAreaCode());
									recommend.setTelphone(my.getTelphone());
									recommend.setUserId(user.getId());
									recommend.setModifyDate(date);
									recommend.setCreateDate(date);
									recommend.setState(Constant.RECOMMEND_STATE1);
									recommend.setSmsState(Constant.RECOMMEND_SMS_STATE1);
									List<Recommend> recommendList = new ArrayList<Recommend>();
									recommendList.add(recommend);
									this.recommendDao.insertRecommends(recommendList);
								}
							}
							
							
							result.setCode(ResponseCode.SUCCESS_CODE);
							result.setData(user);
							result.setMessage("提交成功!");
						}else{
							result.setCode(ResponseCode.SHOW_EXCEPTION);
							result.setMessage("提交失败，请重试!"); 
						}
		            }  
					
				}
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException("提交失败，请重试!",e);
			}
		}
		return result;
	}
	
	@Override
	public ResponseObject<User> modifyRecommenderByPhone(String userId,
			String areaCode, String telphone) throws ServiceException {
		ResponseObject<User> result = new ResponseObject<User>();
		
		try {
			User my = this.userDao.getUserById(userId);
			User user = this.userDao.getUserByPhone(areaCode, telphone);
			if(user==null){
				result.setCode(ResponseCode.PARAMETER_ERROR);
				result.setMessage("没有该用户!");
			}else if(!my.getRecommender().equals("-1")){
				result.setCode(ResponseCode.PARAMETER_ERROR);
				result.setMessage("你已经提交过邀请您的用户了，不能再次提交，如有疑问，请联系客服!");
			}else if(user.getId().equals(userId)){
				result.setCode(ResponseCode.PARAMETER_ERROR);
				result.setMessage("不能提交自己!");
			}else{
				//还应该判断用户的注册时间 ，如果超过一个月，就不能再补充邀请我的用户
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
				Date signDate = df.parse(my.getSignDate());  
		        long signTime = signDate.getTime();  
		     	long currTime = new Date().getTime();  
		        if((currTime - signTime)/(1000*60*60*24)>31) { 
		        	result.setCode(ResponseCode.PARAMETER_ERROR);
					result.setMessage("注册时间已经超过一个月，无法再补充邀请人!");
		      	} else {  
					int nResult = this.userDao.modifyRecommender(user.getId(), userId);
					if(nResult>0){
						result.setCode(ResponseCode.SUCCESS_CODE);
						result.setData(user);
						result.setMessage("提交成功!");
					}else{
						result.setCode(ResponseCode.SHOW_EXCEPTION);
						result.setMessage("提交失败，请重试!"); 
					}
		      	}  
					
			}
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException("提交失败，请重试!",e);
		}
		return result;
	}


	@Override
	public ResponseObject<User> getRecommendedByUserId(String userId)
			throws ServiceException {
		ResponseObject<User> result = new ResponseObject<User>();
		if (StringUtil.isEmpty(userId)) {
			result.setCode(ResponseCode.PARAMETER_ERROR);
			result.setMessage("无效的参数");
			return result;
		} 
		
		try {
			User my = this.userDao.getUserById(userId);
			User user = this.userDao.getUserOpenInfoById(my.getRecommender());
			result.setCode(ResponseCode.SUCCESS_CODE);
			result.setData(user);
			return result;
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
	}

	@Override
	public ResponseObject<User> checkLoginByPhone(String areaCode,
			String telphone, String password, String ip) throws ServiceException {
		ResponseObject<User> result = new ResponseObject<User>();
		try {
			String pwd = MD5Util.encode(password);
			User user = this.userDao.getUserByPhone(areaCode, telphone);
		
			if (user != null && pwd.equals(user.getPassword())) {
				if(Constant.USER_BLOCK_STATE1.equals(user.getBlockState())){
					result.setCode(ResponseCode.USER_BLOCK_STATE1);
					result.setMessage("您目前违反用户协议，不能登录，如有疑问请联系客服！");
				}else{
					result.setCode(ResponseCode.SUCCESS_CODE);
					result.setData(getSecurityValue(user));
					this.loginInfoDao.insertUpdate(user.getId(), "0", DateUtil.date2String(new Date()));
				}
			} else {
				result.setCode(ResponseCode.USER_LOGIN_ACCOUNT_ERROR);
				result.setMessage("密码错误!");
				IPRecord ipRecord = new IPRecord();
				ipRecord.setIp(ip);
				ipRecord.setType(Constant.IPRECORD_TYPE1);
				ipRecord.setCreateDate(DateUtil.date2String(new Date()));
				this.ipRecordDao.insert(ipRecord);
				int nResult = this.ipRecordDao.countByIP(Constant.IPRECORD_TYPE1, ip);
				if(nResult>2){
					result.setCode(ResponseCode.USER_LOGIN_NEEL_CODE);
				}
			}
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException("用户登录失败", e);
		}
		return result;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> ResponseObject<PageSplit<T>> adminSearchUserByKey(String key, String sortType, String nationality,String specialtyType,String demandId,
			int pageSize, int pageNow) throws ServiceException {
		try {
			key = StringUtil.escapeStringOfSearchKey(key);  
			int rowCount = 0;
			try {
				rowCount = this.userDao.countUserByKey(key, nationality,specialtyType,null);
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException("获取人物个数失败", e);
			}

			ResponseObject<PageSplit<T>> responseObj = new ResponseObject<PageSplit<T>>(ResponseCode.SUCCESS_CODE);
			if (rowCount > 0) {
				pageSize = Math.max(pageSize, 1);
				int pageCount = rowCount / pageSize + (rowCount % pageSize == 0 ? 0 : 1);
				pageNow = Math.min(pageNow, pageCount);
				PageSplit<T> pageSplit = new PageSplit<T>();
				pageSplit.setPageCount(pageCount);
				pageSplit.setPageNow(pageNow);
				pageSplit.setRowCount(rowCount);
				pageSplit.setPageSize(pageSize);
				int startIndex = (pageNow - 1) * pageSize;
				try {
					Demand demand = this.demandDao.getDemandById(demandId);
					if(demand == null){
						responseObj.setCode(ResponseCode.PARAMETER_ERROR);
						responseObj.setMessage("任务不存在错误！");
						return responseObj;
					}
					List<User> users = this.userDao.searchUserByKey(key, sortType, nationality, specialtyType, null, startIndex, pageSize);
					if (users != null && !users.isEmpty()) {
						for (User u : users) {
							if(u.getId().equals(demand.getUserId())){
								continue;
							}
							if(!StringUtil.isEmpty(demandId)){
								int nResult =  this.adminRecommendDao.getCountByUserId(u.getId(), demandId);
								if(nResult>0){
									u.setAdminRecommend("1");
								}else{
									u.setAdminRecommend("0");
								}	
							}
							u.setCommentCount(String.valueOf(this.userCommentDao.countByKey("", u.getId())));
							pageSplit.addData((T) getSecurityValue(u));
							
						}
					}
				} catch (Exception e) {
					throw ExceptionUtil.handle2ServiceException("获取用户列表失败", e);
				}
				responseObj.setData(pageSplit);
			} else {
				responseObj.setMessage("没有用户");
			}
			return responseObj;
		} catch (ServiceException e) {
			throw e;
		}
	}

	/*@Override
	public ResponseObject<Object> checkSecurityQuestion(String email,
			String areaCode, String phone, String index1, String index2,
			String index3, String answer1, String answer2, String answer3)
			throws ServiceException {
		ResponseObject<Object> result = new ResponseObject<Object>();
		try {
			boolean bCheck1 = false;
			boolean bCheck2 = false;
			boolean bCheck3 = false;
			User user = null;
			AccountSecurityQuestion asq = null;
			if (StringUtil.isEmpty(email)) {
				if(!UserUtil.validateAreaCode(areaCode)){
					result.setCode(ResponseCode.PARAMETER_ERROR);
					result.setMessage("邮箱或者手机号码输入错误！");
					return result;
				}else if(!UserUtil.validatePhone(phone)){
					result.setCode(ResponseCode.PARAMETER_ERROR);
					result.setMessage("邮箱或者手机号码输入错误！");
					return result;
				}else{
					//
					user = this.userDao.getUserByPhone(areaCode, phone);
					if(user==null){
						result.setCode(ResponseCode.PARAMETER_ERROR);
						result.setMessage("密保问题回答错误！");
						return result;
					}else{
						asq = this.accountSecurityQuestionDao.getByUserId(user.getId());
						if(asq==null){
							result.setCode(ResponseCode.PARAMETER_ERROR);
							result.setMessage("您没有设置密保问题，请使用邮箱或者手机找回密码！");
							return result;
						}
					}
				}
				
			}else{
				user = this.userDao.getUserByAccount(email);
				if(user==null){
					result.setCode(ResponseCode.PARAMETER_ERROR);
					result.setMessage("密保问题回答错误！");
					return result;
				}else{
					asq = this.accountSecurityQuestionDao.getByUserId(user.getId());
				}
			}
			log.error("====================");
			log.error(asq.getQuestion1());
			log.error(asq.getQuestion2());
			log.error(asq.getQuestion3());
		
			if(asq.getQuestion1().equals(index1)||asq.getQuestion2().equals(index1)||asq.getQuestion3().equals(index1)){
				if(asq.getQuestion1().equals(index1)){
					if(asq.getAnswer1().equals(answer1)){
						bCheck1 = true;
					}
				}
				if(asq.getQuestion2().equals(index1)){
					if(asq.getAnswer2().equals(answer1)){
						bCheck1 = true;
					}
				}
				if(asq.getQuestion3().equals(index1)){
					if(asq.getAnswer3().equals(answer1)){
						bCheck1 = true;
					}
				}
			}
			
			if(asq.getQuestion1().equals(index2)||asq.getQuestion2().equals(index2)||asq.getQuestion3().equals(index2)){
				if(asq.getQuestion1().equals(index2)){
					if(asq.getAnswer1().equals(answer2)){
						bCheck2 = true;
					}
				}
				if(asq.getQuestion2().equals(index2)){
					if(asq.getAnswer2().equals(answer2)){
						bCheck2 = true;
					}
				}
				if(asq.getQuestion3().equals(index2)){
					if(asq.getAnswer3().equals(answer2)){
						bCheck2 = true;
					}
				}
			}
			if(asq.getQuestion1().equals(index3)||asq.getQuestion2().equals(index3)||asq.getQuestion3().equals(index3)){
				if(asq.getQuestion1().equals(index3)){
					if(asq.getAnswer1().equals(answer3)){
						bCheck3 = true;
					}
				}
				if(asq.getQuestion2().equals(index3)){
					if(asq.getAnswer2().equals(answer3)){
						bCheck3 = true;
					}
				}
				if(asq.getQuestion3().equals(index3)){
					if(asq.getAnswer3().equals(answer3)){
						bCheck3= true;
					}
				}
			}
			
			if(bCheck1&&bCheck2&&bCheck3){
				result.setCode(ResponseCode.SUCCESS_CODE);
				return result;
			}else{
				result.setCode(ResponseCode.PARAMETER_ERROR);
				result.setMessage("密保问题回答错误！");
				return result;
			}
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
	}
	*/
	
	/*@Override
	public ResponseObject<Object> checkSecurityQuestionByUserId(String userId, String index1, String index2,
			String index3, String answer1, String answer2, String answer3)
			throws ServiceException {
		ResponseObject<Object> result = new ResponseObject<Object>();
		try {
			boolean bCheck1 = false;
			boolean bCheck2 = false;
			boolean bCheck3 = false;
			User user = null;
			AccountSecurityQuestion asq = null;
			user = this.userDao.getUserById(userId);
			if(user==null){
				result.setCode(ResponseCode.PARAMETER_ERROR);
				result.setMessage("密保问题回答错误！");
				return result;
			}else{
				asq = this.accountSecurityQuestionDao.getByUserId(user.getId());
			}
			if(asq.getQuestion1().equals(index1)||asq.getQuestion2().equals(index1)||asq.getQuestion3().equals(index1)){
				if(asq.getQuestion1().equals(index1)){
					if(asq.getAnswer1().equals(answer1)){
						bCheck1 = true;
					}
				}
				if(asq.getQuestion2().equals(index1)){
					if(asq.getAnswer2().equals(answer1)){
						bCheck1 = true;
					}
				}
				if(asq.getQuestion3().equals(index1)){
					if(asq.getAnswer3().equals(answer1)){
						bCheck1 = true;
					}
				}
			}
			
			if(asq.getQuestion1().equals(index2)||asq.getQuestion2().equals(index2)||asq.getQuestion3().equals(index2)){
				if(asq.getQuestion1().equals(index2)){
					if(asq.getAnswer1().equals(answer2)){
						bCheck2 = true;
					}
				}
				if(asq.getQuestion2().equals(index2)){
					if(asq.getAnswer2().equals(answer2)){
						bCheck2 = true;
					}
				}
				if(asq.getQuestion3().equals(index2)){
					if(asq.getAnswer3().equals(answer2)){
						bCheck2 = true;
					}
				}
			}
			if(asq.getQuestion1().equals(index3)||asq.getQuestion2().equals(index3)||asq.getQuestion3().equals(index3)){
				if(asq.getQuestion1().equals(index3)){
					if(asq.getAnswer1().equals(answer3)){
						bCheck3 = true;
					}
				}
				if(asq.getQuestion2().equals(index3)){
					if(asq.getAnswer2().equals(answer3)){
						bCheck3 = true;
					}
				}
				if(asq.getQuestion3().equals(index3)){
					if(asq.getAnswer3().equals(answer3)){
						bCheck3= true;
					}
				}
			}
			
			if(bCheck1&&bCheck2&&bCheck3){
				result.setCode(ResponseCode.SUCCESS_CODE);
				return result;
			}else{
				result.setCode(ResponseCode.PARAMETER_ERROR);
				result.setMessage("密保问题回答错误！");
				return result;
			}
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
	}*/

	@Override
	public ResponseObject<Object> modifyPhone(String userId, String areaCode, String telphone)
			throws ServiceException {
		ResponseObject<Object> result = new ResponseObject<Object>();
		if (!UserUtil.validateId(userId)) {
			result.setCode(ResponseCode.PARAMETER_ERROR);
			result.setMessage("用户id错误");
			return result;
		}else if(!UserUtil.validateAreaCode(areaCode) ){
			result.setCode(ResponseCode.PARAMETER_ERROR);
			result.setMessage("手机地区区号不正确");
			return result;
		}else if(!UserUtil.validatePhone(telphone) ){
			result.setCode(ResponseCode.PARAMETER_ERROR);
			result.setMessage("手机号码不正确");
			return result;
		}else {
			try {
				String oldPhone = this.userDao.getUserById(userId).getTelphone();   //haokun added 2017/02/10 获取旧电话
				int nResult = this.userDao.modifyPhoneById(userId, areaCode, telphone);
				if (nResult > 0) {
					//add by haokun 2017/01/26 修改手机成功 发送短信提醒
					User user = this.userDao.getUserById(userId);
					if(user.getTelphone()!=null&&!user.getTelphone().equals("")){
						//SmsSendUtil.sendInviteMsg(user.getNickName(),demand.getTitle(), url, user.getAreaCode(), user.getTelphone(), demand.getAmount());
						String content = null;
						content = "[Anyonehelps]尊敬的用户"+user.getNickName()+"!您成功更改了您的手机";
						
						SmsSend ss = new SmsSend();
						ss.setContent(content);
						ss.setFailCount(Constant.EMAILSEND_FAIL_COUNT0);
						ss.setState(Constant.EMAILSEND_STATE0);
						ss.setAreaCode(user.getAreaCode());
						ss.setTelphone(user.getTelphone());
						ss.setUserId(user.getId());
						ss.setCreateDate(new Date().toString());
						ss.setModifyDate(new Date().toString());
						this.smsSendDao.insert(ss);
					}
					
					//邮箱提醒 add by haokun 2017/01/26 用户更改手机，增加邮件提醒
					if(user.getEmail()!=null&&!user.getEmail().equals("")&&user.getEmailState().equals(Constant.USER_EMAIL_STATE1)){
						Properties prop = PropertiesReader.read(Constant.EMAIL_PROPERTIES_FILE);              //haokun modified  2017/02/10
						String subject = prop.getProperty("anyonehelps.email.user.modify.phone.subject");     //haokun modified  2017/02/10
						String content = prop.getProperty("anyonehelps.email.user.modify.phone.content");     //haokun modified  2017/02/10
						content = MessageFormat.format(content, new Object[] {oldPhone, user.getTelphone()});    //haokun modified  2017/02/10显示用户名不显示密码
				
						EmailSend es = new EmailSend();
						es.setContent(content);
						es.setCreateDate(new Date().toString());
						es.setEmail(user.getEmail());
						es.setFailCount(Constant.EMAILSEND_FAIL_COUNT0);
						es.setModifyDate(new Date().toString());
						es.setState(Constant.EMAILSEND_STATE0);
						es.setSubject(subject);
						es.setUserId(user.getId());
						this.emailSendDao.insert(es);
					}
					result.setCode(ResponseCode.SUCCESS_CODE);
				} else {
					result.setCode(ResponseCode.USER_MODIFY_FAILURE);
					result.setMessage("修改手机错误，请重新尝试！");
				}
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException(e);
			}
		}
		return result;
	}

	@Override
	public ResponseObject<Object> modifyEmail(String userId, String email)
			throws ServiceException {
		ResponseObject<Object> result = new ResponseObject<Object>();
		if (!UserUtil.validateId(userId)) {
			result.setCode(ResponseCode.PARAMETER_ERROR);
			result.setMessage("用户id错误");
			return result;
		}else if(!UserUtil.validateEmail(email)){
			result.setCode(ResponseCode.PARAMETER_ERROR);
			result.setMessage("邮箱不正确");
			return result;
		}else {
			try {
				String oldEmail = this.userDao.getUserById(userId).getEmail();   //haokun add 2017/02/10获取旧邮件地址
				int nResult = this.userDao.modifyEmailById(userId, email);
				if (nResult > 0) {
					//add by haokun 2017/01/26 修改邮箱成功 发送短信提醒
					User user = this.userDao.getUserById(userId);
					if(user.getTelphone()!=null&&!user.getTelphone().equals("")){
						//SmsSendUtil.sendInviteMsg(user.getNickName(),demand.getTitle(), url, user.getAreaCode(), user.getTelphone(), demand.getAmount());
						String content = null;
						content = "[Anyonehelps]尊敬的用户"+user.getNickName()+"!您成功更改了您的邮箱<br>";
						
						SmsSend ss = new SmsSend();
						ss.setContent(content);
						ss.setFailCount(Constant.EMAILSEND_FAIL_COUNT0);
						ss.setState(Constant.EMAILSEND_STATE0);
						ss.setAreaCode(user.getAreaCode());
						ss.setTelphone(user.getTelphone());
						ss.setUserId(user.getId());
						ss.setCreateDate(new Date().toString());
						ss.setModifyDate(new Date().toString());
						this.smsSendDao.insert(ss);
					}
					
					//邮箱提醒 add by haokun 2017/01/26 用户更改邮箱，增加邮件提醒
					if(user.getEmail()!=null&&!user.getEmail().equals("")&&user.getEmailState().equals(Constant.USER_EMAIL_STATE1)){
						Properties prop = PropertiesReader.read(Constant.EMAIL_PROPERTIES_FILE);              //haokun modified  2017/02/10
						String subject = prop.getProperty("anyonehelps.email.user.modify.email.subject");     //haokun modified  2017/02/10
						String content = prop.getProperty("anyonehelps.email.user.modify.email.content");     //haokun modified  2017/02/10
						content = MessageFormat.format(content, new Object[] {oldEmail, user.getEmail()});    //haokun modified  2017/02/10显示用户名不显示密码
				
						
						EmailSend es = new EmailSend();
						es.setContent(content);
						es.setCreateDate(new Date().toString());
						es.setEmail(user.getEmail());
						es.setFailCount(Constant.EMAILSEND_FAIL_COUNT0);
						es.setModifyDate(new Date().toString());
						es.setState(Constant.EMAILSEND_STATE0);
						es.setSubject(subject);
						es.setUserId(user.getId());
						this.emailSendDao.insert(es);
						
						//haokun added 2017/02/10 给旧邮箱发送消息
						es = new EmailSend();
						es.setContent(content);
						es.setCreateDate(new Date().toString());
						es.setEmail(oldEmail);
						es.setFailCount(Constant.EMAILSEND_FAIL_COUNT0);
						es.setModifyDate(new Date().toString());
						es.setState(Constant.EMAILSEND_STATE0);
						es.setSubject(subject);
						es.setUserId(user.getId());
						this.emailSendDao.insert(es);
					}
					
					result.setCode(ResponseCode.SUCCESS_CODE);
				} else {
					result.setCode(ResponseCode.USER_MODIFY_FAILURE);
					result.setMessage("修改邮箱错误，请重新尝试！");
				}
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException(e);
			}
		}
		return result;
	}

	@Override
	public ResponseObject<Object> emailSubscribe(String userId, String subscribe)
			throws ServiceException {
		ResponseObject<Object> result = new ResponseObject<Object>();
		if (!UserUtil.validateId(userId)) {
			result.setCode(ResponseCode.PARAMETER_ERROR);
			result.setMessage("用户id错误");
			return result;
		}else {
			if("0".equals(subscribe)){
				//退订
				String date = DateUtil.date2String(new Date());
				EmailSubscribe es = new EmailSubscribe();
				es.setUserId(userId);
				es.setModifyDate(date);
				es.setCreateDate(date);
				try {
					int nResult = this.emailSubscribeDao.insertOrUpdate(es);
					if(nResult>0){
						result.setCode(ResponseCode.SUCCESS_CODE);
						return result;
					}else{
						result.setCode(ResponseCode.SHOW_EXCEPTION);
						result.setMessage("退订失败！");
						return result;
					}
				} catch (Exception e) {
					throw ExceptionUtil.handle2ServiceException(e);
				}
				
			}else if("1".equals(subscribe)){
				//订阅 
				try {
					this.emailSubscribeDao.deleteByUserId(userId);
					result.setCode(ResponseCode.SUCCESS_CODE);
					return result;
				} catch (Exception e) {
					throw ExceptionUtil.handle2ServiceException(e);
				}
			}else{
				result.setCode(ResponseCode.PARAMETER_ERROR);
				result.setMessage("参数错误");
				return result;
			}
		}
	}

	@Override
	public ResponseObject<Object> modifyPwd(String userId, String password)
			throws ServiceException {
		ResponseObject<Object> result = new ResponseObject<Object>();
		if (StringUtil.isEmpty(password)) {
			result.setCode(ResponseCode.PARAMETER_ERROR);
			result.setMessage("密码错误！");
		} else {
			try {
				int nResult = this.userDao.updatePassword(userId, null, null, MD5Util.encode(password), null);
				if (nResult > 0) {
					result.setCode(ResponseCode.SUCCESS_CODE);
				} else {
					result.setCode(ResponseCode.USER_MODIFY_FAILURE);
					result.setMessage("修改失败，请重试！");
				}
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException(e);
			}
		}
		return result;
	}

	/*haokun modified 封面图片 2017/02/23*/
	@Override
	public ResponseObject<Object> modifyUserCover(String userId,
			String picUrl) throws ServiceException {
		ResponseObject<Object> result = new ResponseObject<Object>();
		if (StringUtil.isEmpty(picUrl)||StringUtil.isEmpty(userId)) {
			result.setCode(ResponseCode.PARAMETER_ERROR);
			result.setMessage("无效的参数");
			return result;
		} 
		
		try {

			int iresult = this.userDao.updateCoverUrlById(userId, picUrl);
			if (iresult > 0) {
				result.setCode(ResponseCode.SUCCESS_CODE);
				result.setData(picUrl);
			} else {
				result.setCode(ResponseCode.USER_PIC_UPDATE_ERROR);
				result.setMessage("修改封面错误，靖重试!");
			}
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
		return result;
	}
	
}
