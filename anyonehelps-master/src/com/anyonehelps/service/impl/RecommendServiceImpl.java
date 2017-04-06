package com.anyonehelps.service.impl;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anyonehelps.common.constants.Constant;
import com.anyonehelps.common.constants.ResponseCode;
import com.anyonehelps.common.util.Base64Util;
import com.anyonehelps.common.util.DateUtil;
import com.anyonehelps.common.util.ExceptionUtil;
import com.anyonehelps.common.util.PropertiesReader;
import com.anyonehelps.common.util.StringUtil;
import com.anyonehelps.common.util.UserUtil;
import com.anyonehelps.common.util.sms.SmsConfigUtil;
import com.anyonehelps.dao.EmailSendDao;
import com.anyonehelps.dao.MessageSystemDao;
import com.anyonehelps.dao.RecommendDao;
import com.anyonehelps.dao.SmsSendDao;
import com.anyonehelps.dao.UserDao;
import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.EmailSend;
import com.anyonehelps.model.MessageSystem;
import com.anyonehelps.model.PageSplit;
import com.anyonehelps.model.Recommend;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.model.SmsSend;
import com.anyonehelps.model.User;
import com.anyonehelps.service.RecommendService;

@Service("recommendService")
public class RecommendServiceImpl extends BasicService implements RecommendService {
	@Autowired
	private RecommendDao recommendDao;
	@Autowired
	private EmailSendDao emailSendDao;
	@Autowired
	private SmsSendDao smsSendDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private MessageSystemDao messageSystemDao;
	
	public ResponseObject<Object> addRecommends(String userId,String userNickName,List<String> recommenders) throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>();
		if(recommenders==null||userId==null){
			return new ResponseObject<Object>(ResponseCode.PARAMETER_ERROR,"参数无效");
		}
		try {
			List<Recommend> recommendList = new ArrayList<Recommend>();
			List<String> failRecommend = new ArrayList<String>();
			String date = DateUtil.date2String(new Date());
			for(String recommender:recommenders){
				User user= this.userDao.getUserByAccount(recommender);
				if(user!=null){
					//已经注册了
					failRecommend.add(recommender);
					continue;
				}
				Recommend r = this.recommendDao.searchByEmail(recommender);
				if(r!=null){
					//已经有人邀请了
					failRecommend.add(recommender);
					continue;
				}
				if(UserUtil.validateEmail(recommender)){
					//是邮件
					//MailSendUtil.sendInviteUserMsg(userId,userNickName, recommender);
					Properties prop = PropertiesReader.read(Constant.EMAIL_PROPERTIES_FILE);
					String subject = prop.getProperty("anyonehelps.invite.user.subject");
					subject = MessageFormat.format(subject, new Object[] { userNickName });
					String content = prop.getProperty("anyonehelps.invite.user.content");
					content = MessageFormat.format(content, new Object[] { userNickName,userId });
					EmailSend es = new EmailSend();
					es.setContent(content);
					es.setCreateDate(date);
					es.setEmail(recommender);
					es.setFailCount(Constant.EMAILSEND_FAIL_COUNT0);
					es.setModifyDate(date);
					es.setState(Constant.EMAILSEND_STATE0);
					es.setSubject(subject);
					es.setUserId(userId);
					emailSendDao.insert(es);
				}/*else if(UserUtil.validatePhone(recommender)){
					//是手机
					SmsSendUtil.sendInviteUserMsg(userId,userNickName, "+1",recommender);
					
				}*/else{
					//不是邮箱或者手机
					failRecommend.add(recommender);
					continue;
				}
				Recommend newRecommend = new Recommend();
				//newRecommend.setRecommender(recommender);
				newRecommend.setEmail(recommender);
				newRecommend.setUserId(userId);
				newRecommend.setModifyDate(date);
				newRecommend.setCreateDate(date);
				newRecommend.setState(Constant.RECOMMEND_STATE0);
				newRecommend.setSmsState(Constant.RECOMMEND_SMS_STATE1);
				
				recommendList.add(newRecommend);
			}
			if(!recommendList.isEmpty()){
				this.recommendDao.insertRecommends(recommendList);
			}
			responseObj.setCode(ResponseCode.SUCCESS_CODE);
			responseObj.setData(failRecommend);
			
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException("获取提交邀请人失败", e);
		}
		return responseObj;
	}
	
	public ResponseObject<Object> addRecommendByPhone(String userId,String userNickName, String areaCode, String telphone) throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>();
		try {
			User user= this.userDao.getUserByPhone(areaCode, telphone);
			if(user!=null){
				//已经注册了
				responseObj.setCode(ResponseCode.RECOMMEND_INSERT_FAILURE);
				responseObj.setMessage("该用户已经注册了");
				return responseObj;
			}
			Recommend r = this.recommendDao.searchByPhone(areaCode, telphone);
			if(r!=null){
				//已经有人邀请了
				responseObj.setCode(ResponseCode.RECOMMEND_INSERT_FAILURE);
				responseObj.setMessage("已经有人正在邀请该用户，您可以自行把邀请链接发给该用户");
				return responseObj;
			}
			//SmsSendUtil.sendInviteUserMsg(userId,userNickName, areaCode, telphone);
			String date = DateUtil.date2String(new Date());
			
			Properties prop = null;
			String content = null;
			if(areaCode.equals("+86")||areaCode.equals("0086")){
				prop = PropertiesReader.read(SmsConfigUtil.getSmsConfigFile(Constant.COUNTRY_CODE_CN));
				content = prop.getProperty("anyonehelps.invite.user.content");
				content = MessageFormat.format(content, new Object[] {userNickName,userId});
			}else {
				prop = PropertiesReader.read(SmsConfigUtil.getSmsConfigFile(Constant.COUNTRY_CODE_US));
				content = prop.getProperty("anyonehelps.invite.user.content");
				content = MessageFormat.format(content, new Object[] {userNickName,userId});
			}
			Recommend recommend = new Recommend();
			//newRecommend.setRecommender(recommender);
			recommend.setEmail("");
			recommend.setAreaCode(areaCode);
			recommend.setTelphone(telphone);
			recommend.setUserId(userId);
			recommend.setModifyDate(date);
			recommend.setCreateDate(date);
			recommend.setState(Constant.RECOMMEND_STATE0);
			recommend.setSmsState(Constant.RECOMMEND_SMS_STATE1);
			List<Recommend> recommendList = new ArrayList<Recommend>();
			recommendList.add(recommend);
			int nResult = this.recommendDao.insertRecommends(recommendList);
			if(nResult>0){
				SmsSend ss = new SmsSend();
				ss.setContent(content);
				ss.setFailCount(Constant.EMAILSEND_FAIL_COUNT0);
				ss.setState(Constant.EMAILSEND_STATE0);
				ss.setAreaCode(areaCode);
				ss.setTelphone(telphone);
				ss.setUserId(userId);
				ss.setCreateDate(date);
				ss.setModifyDate(date);
				int n = smsSendDao.insert(ss);
				if(n>0){
					responseObj.setCode(ResponseCode.SUCCESS_CODE);
				}else{
					throw new Exception("发送邀请消息失败");
				}
				
			}else{
				responseObj.setCode(ResponseCode.RECOMMEND_INSERT_FAILURE);
				responseObj.setMessage("邀请失败，请重试！");
				return responseObj;
			}
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException("提交邀请人失败", e);
		}
		return responseObj;
	}
	
	@SuppressWarnings("unchecked")
	public <T> ResponseObject<PageSplit<T>> searchByKey(String userId, String key, String state, int pageSize, int pageNow)
	        throws ServiceException {
		try {
			key = StringUtil.escapeStringOfSearchKey(key);  
			int rowCount = 0;
			try {
				rowCount = this.recommendDao.countByKey(key,state,userId);
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException("获取邀请人总数失败", e);
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
					List<Recommend> recommends = this.recommendDao.searchByKey( key, state,userId,startIndex, pageSize);
					if (recommends != null && !recommends.isEmpty()) {
						for (Recommend d : recommends) {
							pageSplit.addData((T)d);
						}
					}
				} catch (Exception e) {
					throw ExceptionUtil.handle2ServiceException("获取邀请人列表失败", e);
				}
				responseObj.setData(pageSplit);
			} else {
				responseObj.setMessage("暂无邀请人");
			}
			return responseObj;
		} catch (ServiceException e) {
			throw e;
		}
	}

	@Override
	public ResponseObject<Object> modifyModifyDate(String userId, String id)
			throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>(ResponseCode.SUCCESS_CODE);
		try {
			Recommend recommend = this.recommendDao.getById(id);
			if(recommend==null){
				responseObj.setCode(ResponseCode.RECOMMEND_UPDATE_ERROR);
				responseObj.setMessage("没有这个邀请信息");
				return responseObj;
			}
			if(!recommend.getUserId().equals(userId)){
				responseObj.setCode(ResponseCode.RECOMMEND_UPDATE_ERROR);
				responseObj.setMessage("这个邀请信息不是您的！,请刷新后再操作");
				return responseObj;
			}
			if(recommend.getState().equals(Constant.RECOMMEND_STATE1)){
				responseObj.setCode(ResponseCode.RECOMMEND_UPDATE_ERROR);
				responseObj.setMessage("被邀请人已经接受该邀请，无需再刷新");
				return responseObj;
			}
			User user= null;
			if(recommend==null||recommend.getEmail().equals("")){
				user= this.userDao.getUserByPhone(recommend.getAreaCode(), recommend.getTelphone());
			}else{
				user= this.userDao.getUserByAccount(recommend.getEmail());
			}
			if(user!=null){
				//已经注册了
				responseObj.setCode(ResponseCode.RECOMMEND_UPDATE_ERROR);
				responseObj.setMessage("被邀请人已经注册了，无需再刷新");
				return responseObj;
			}
			String date = DateUtil.date2String(new Date());
			int nResult = this.recommendDao.updateModifyDate(date,Constant.RECOMMEND_SMS_STATE0,id);
			if(nResult>0){
				responseObj.setCode(ResponseCode.SUCCESS_CODE);
				responseObj.setMessage("");
				return responseObj;
			}else{
				responseObj.setCode(ResponseCode.RECOMMEND_UPDATE_ERROR);
				responseObj.setMessage("刷新邀请状态失败");
				return responseObj;
			}
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException("刷新邀请信息失败", e);
		}
	}

	@Override
	public ResponseObject<Object> addUserRecommend(String userId,
			String userNickName, String recommender) throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>();
		if(recommender==null|| userId==null){
			return new ResponseObject<Object>(ResponseCode.PARAMETER_ERROR,"参数无效");
		}
		try {
			String date = DateUtil.date2String(new Date());
			 
			Recommend r = this.recommendDao.getByUserIdAndEmail2(userId, recommender);
			if(r!=null){
				return new ResponseObject<Object>(ResponseCode.PARAMETER_ERROR,"您已对该用户发过请求关联");
			}
			User user= this.userDao.getUserByAccount(recommender);
			if(user==null){
				//未注册了
				responseObj.setCode(ResponseCode.PARAMETER_ERROR);
				responseObj.setMessage("用户还未注册，无法关联！");
			}else if(!"-1".equals(user.getRecommender())){
				responseObj.setCode(ResponseCode.PARAMETER_ERROR);
				responseObj.setMessage("操作失败，对方已被关联！");
			}else{
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
				if(user.getSignDate()!=null){
					Date start = df.parse(user.getSignDate());
					if(getMonth(start, new Date())>1){
						//老用户
						responseObj.setCode(ResponseCode.PARAMETER_ERROR);
						responseObj.setMessage("操作失败，对方注册超过30天，不是新用户！");
					}else{
						//新用户
						List<Recommend> recommendList = new ArrayList<Recommend>();
						Recommend recommend = new Recommend();
						recommend.setEmail(recommender);
						recommend.setUserId(userId);
						recommend.setModifyDate(date);
						recommend.setCreateDate(date);
						recommend.setRecommender(user.getId());
						recommend.setState(Constant.RECOMMEND_STATE4);
						recommend.setSmsState(Constant.RECOMMEND_SMS_STATE1);
						recommendList.add(recommend);
						int nResult = this.recommendDao.insertRecommends(recommendList);
						
						if(nResult>0){
							MessageSystem ms = new MessageSystem();
							ms.setCreateDate(date);
							ms.setLevel(Constant.MESSAGESYSTEM_LEVEL_INFO);
							ms.setContent(userNickName + "请求关联为邀请人");
							//还没写配置
							ms.setLink("/dashboard/Account/messages.jsp?#tab_1_1");
							ms.setModifyDate(date);
							ms.setTitle(userNickName + "请求关联为邀请人");
							ms.setUserId(user.getId());
							ms.setState(Constant.MESSAGESYSTEM_STATE_UNREAD);
							ms.setRecommender(userId);
							ms.setRecommendState("0");
							ms.setRecommendDate("");
							this.messageSystemDao.insertMessage(ms);
							responseObj.setCode(ResponseCode.SUCCESS_CODE);
						}else{
							responseObj.setCode(ResponseCode.PARAMETER_ERROR);
							responseObj.setMessage("操作失败，请重试！");
						}
					}
				}
			}
			
			
			
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException("请求关联邀请失败", e);
		}
		return responseObj;
	}

	@Override
	public ResponseObject<Object> addUserRecommendByPhone(String userId,
			String userNickName, String areaCode, String telphone)
			throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>();
		try {
			
			Recommend r = this.recommendDao.getByUserIdAndPhone2(userId, areaCode, telphone);
			if(r!=null){
				return new ResponseObject<Object>(ResponseCode.PARAMETER_ERROR,"您已对该用户发过请求关联");
			}
			
			User user= this.userDao.getUserByPhone(areaCode, telphone);
			
			if(user==null){
				//未注册了
				responseObj.setCode(ResponseCode.PARAMETER_ERROR);
				responseObj.setMessage("用户还未注册，无法关联！");
			}else if(!"-1".equals(user.getRecommender())){
				responseObj.setCode(ResponseCode.PARAMETER_ERROR);
				responseObj.setMessage("操作失败，对方已被关联！");
			}else{
				//新用户
				String date = DateUtil.date2String(new Date());
				
				List<Recommend> recommendList = new ArrayList<Recommend>();
				Recommend recommend = new Recommend();
				recommend.setEmail("");
				recommend.setAreaCode(areaCode);
				recommend.setTelphone(telphone);
				recommend.setUserId(userId);
				recommend.setModifyDate(date);
				recommend.setCreateDate(date);
				recommend.setRecommender(user.getId());
				recommend.setState(Constant.RECOMMEND_STATE4);
				recommend.setSmsState(Constant.RECOMMEND_SMS_STATE1);
				recommendList.add(recommend);
				int nResult = this.recommendDao.insertRecommends(recommendList);
				
				if(nResult>0){
					MessageSystem ms = new MessageSystem();
					ms.setCreateDate(date);
					ms.setLevel(Constant.MESSAGESYSTEM_LEVEL_INFO);
					ms.setContent(userNickName + "请求关联为邀请人");
					//还没写配置
					ms.setLink("/dashboard/Account/messages.jsp?#tab_1_1");
					ms.setModifyDate(date);
					ms.setTitle(userNickName + "请求关联为邀请人");
					ms.setUserId(user.getId());
					ms.setState(Constant.MESSAGESYSTEM_STATE_UNREAD);
					ms.setRecommender(userId);
					ms.setRecommendState("0");
					ms.setRecommendDate("");
					this.messageSystemDao.insertMessage(ms);
					responseObj.setCode(ResponseCode.SUCCESS_CODE);
				}else{
					responseObj.setCode(ResponseCode.PARAMETER_ERROR);
					responseObj.setMessage("操作失败，请重试！");
				}

			}
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException("请求关联邀请失败", e);
		}
		return responseObj;
	}
	
	
	public int getMonth(Date start, Date end) {
        if (start.after(end)) {
            Date t = start;
            start = end;
            end = t;
        }
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end);
        Calendar temp = Calendar.getInstance();
        temp.setTime(end);
        temp.add(Calendar.DATE, 1);

        int year = endCalendar.get(Calendar.YEAR)
                - startCalendar.get(Calendar.YEAR);
        int month = endCalendar.get(Calendar.MONTH)
                - startCalendar.get(Calendar.MONTH);

        if ((startCalendar.get(Calendar.DATE) == 1)
                && (temp.get(Calendar.DATE) == 1)) {
            return year * 12 + month + 1;
        } else if ((startCalendar.get(Calendar.DATE) != 1)
                && (temp.get(Calendar.DATE) == 1)) {
            return year * 12 + month;
        } else if ((startCalendar.get(Calendar.DATE) == 1)
                && (temp.get(Calendar.DATE) != 1)) {
            return year * 12 + month;
        } else {
            return (year * 12 + month - 1) < 0 ? 0 : (year * 12 + month);
        }
    }

	@Override
	public ResponseObject<Object> acceptedRecommend(String userId,
			String recommender) throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>();
		if(recommender==null|| userId==null){
			return new ResponseObject<Object>(ResponseCode.PARAMETER_ERROR,"参数无效");
		}
		try {
			String date = DateUtil.date2String(new Date());
			User user= this.userDao.getUserById(userId);
			System.out.println("=====================================");
			System.out.println("userId:"+userId);
			System.out.println("Recommender:"+user.getRecommender());
			System.out.println("-1".equals(user.getRecommender()));
			System.out.println("=====================================");
			
			if(!"-1".equals(user.getRecommender())){
				responseObj.setCode(ResponseCode.PARAMETER_ERROR);
				responseObj.setMessage("操作失败，您已被关联！");
			}else{
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
				if(user.getSignDate()!=null){
					Date start = df.parse(user.getSignDate());
					if(getMonth(start, new Date())>1){
						//老用户
						responseObj.setCode(ResponseCode.PARAMETER_ERROR);
						responseObj.setMessage("操作失败，您注册超过30天，不能再关联！");
					}else{
						//新用户
						User rUser= this.userDao.getOnlyUserOpenInfoById(userId);
						if(rUser==null){
							responseObj.setCode(ResponseCode.PARAMETER_ERROR);
							responseObj.setMessage("参数无效！");
						}else{
							int nResult = this.userDao.modifyRecommender(recommender, userId);
							if(nResult>0){
								this.recommendDao.modifyRecommendState(recommender, date, Constant.RECOMMEND_STATE5, userId);
								this.messageSystemDao.modifyRecommendState(userId, recommender, "1", date);
								this.messageSystemDao.modifyRecommendState2(userId, recommender, "2", date);
								
								MessageSystem ms = new MessageSystem();
								ms.setCreateDate(date);
								ms.setLevel(Constant.MESSAGESYSTEM_LEVEL_INFO);
								ms.setTitle(rUser.getNickName() + "接受了您的关联邀请");
								//还没写配置
								ms.setLink("/profile.jsp?userId="+Base64Util.encode(userId));
								ms.setModifyDate(date);
								ms.setContent(rUser.getNickName() + "接受了您的关联邀请");
								ms.setUserId(user.getId());
								ms.setState(Constant.MESSAGESYSTEM_STATE_UNREAD);
								this.messageSystemDao.insertMessage(ms);
								
								responseObj.setCode(ResponseCode.SUCCESS_CODE);
							}else{
								responseObj.setCode(ResponseCode.PARAMETER_ERROR);
								responseObj.setMessage("接受失败，请重试！");
							}
						}
					}
				}
			}
			
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException("请求关联邀请失败", e);
		}
		return responseObj;
	}

	@Override
	public ResponseObject<Object> rejectRecommend(String userId,
			String recommender) throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>();
		if(recommender==null|| userId==null){
			return new ResponseObject<Object>(ResponseCode.PARAMETER_ERROR,"参数无效");
		}
		try {
			String date = DateUtil.date2String(new Date());
			this.recommendDao.modifyRecommendState(recommender, date, Constant.RECOMMEND_STATE6, userId);
			this.messageSystemDao.modifyRecommendState(userId, recommender, "2", date);
			
			User user= this.userDao.getOnlyUserOpenInfoById(userId);
			
			MessageSystem ms = new MessageSystem();
			ms.setCreateDate(date);
			ms.setLevel(Constant.MESSAGESYSTEM_LEVEL_INFO);
			ms.setTitle(user.getNickName() + "拒绝了您的关联邀请");
			//还没写配置
			ms.setLink("/profile.jsp?userId="+Base64Util.encode(userId));
			ms.setModifyDate(date);
			ms.setContent(user.getNickName() + "拒绝了您的关联邀请");
			ms.setUserId(recommender);
			ms.setState(Constant.MESSAGESYSTEM_STATE_UNREAD);
			this.messageSystemDao.insertMessage(ms);
			
			responseObj.setCode(ResponseCode.SUCCESS_CODE);
			
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException("拒绝关联邀请失败", e);
		}
		return responseObj;
	}


}
