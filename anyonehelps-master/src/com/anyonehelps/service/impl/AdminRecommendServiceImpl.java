package com.anyonehelps.service.impl;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anyonehelps.common.constants.Constant;
import com.anyonehelps.common.constants.ResponseCode;
import com.anyonehelps.common.util.Base64Util;
import com.anyonehelps.common.util.DateUtil;
import com.anyonehelps.common.util.ExceptionUtil;
import com.anyonehelps.common.util.PropertiesReader;
import com.anyonehelps.common.util.sms.MailSendUtil;
import com.anyonehelps.common.util.sms.SmsConfigUtil;
import com.anyonehelps.dao.AdminRecommendDao;
import com.anyonehelps.dao.DemandDao;
import com.anyonehelps.dao.EmailSendDao;
import com.anyonehelps.dao.SmsSendDao;
import com.anyonehelps.dao.UserDao;
import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.AdminRecommend;
import com.anyonehelps.model.Demand;
import com.anyonehelps.model.EmailSend;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.model.SmsSend;
import com.anyonehelps.model.User;
import com.anyonehelps.service.AdminRecommendService;

@Service("adminRecommendService")
public class AdminRecommendServiceImpl extends BasicService implements AdminRecommendService {
	@Autowired
	private AdminRecommendDao adminRecommendDao;
	@Autowired
	private DemandDao demandDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private EmailSendDao emailSendDao;
	@Autowired
	private SmsSendDao smsSendDao;
	
	public ResponseObject<Object> addRecommend(String userId,String demandId) throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>();
		if(demandId==null||userId==null){
			return new ResponseObject<Object>(ResponseCode.PARAMETER_ERROR,"参数无效");
		}
		try {
			Demand demand = this.demandDao.getDemandById(demandId);
			if(demand == null){
				return new ResponseObject<Object>(ResponseCode.DEMAND_FOLLOW_FAIL,"任务不存在！");
			}
			if(!demand.getState().equals(Constant.DEMAND_STATE_READY)&&
					!demand.getState().equals(Constant.DEMAND_STATE_RECEIVE)){
				return new ResponseObject<Object>(ResponseCode.DEMAND_FOLLOW_FAIL,"任务已开始，或者已关闭！");
			}
			
			User user = this.userDao.getUserById(userId);
			if(user==null){
				return new ResponseObject<Object>(ResponseCode.DEMAND_FOLLOW_FAIL,"用户不存在！");
			}
			// 这里还没做配置文件
			String url = "www.anyonehelps.com/task/detail.jsp?id="+Base64Util.encode(demandId);
			String date = DateUtil.date2String(new Date());
			
			if(user.getEmail()!=null&&!user.getEmail().equals("")&&user.getEmailState().equals(Constant.USER_EMAIL_STATE1)){
				MailSendUtil.adminSendInviteMsg(user.getNickName(), demand.getTitle(), demand.getAmount(), demand.getExpireDate(), url, user.getEmail());
				Properties prop = PropertiesReader.read(Constant.EMAIL_PROPERTIES_FILE);
				String subject = prop.getProperty("anyonehelps.admin.invite.task.subject");
				subject = MessageFormat.format(subject, new Object[] { user.getNickName()});
				String content = prop.getProperty("anyonehelps.admin.invite.task.content");
				content = MessageFormat.format(content, new Object[] { user.getNickName(), demand.getTitle(), demand.getAmount(), demand.getExpireDate(), url});
				 
				EmailSend es = new EmailSend();
				es.setContent(content);
				es.setCreateDate(date);
				es.setEmail(user.getEmail());
				es.setFailCount(Constant.EMAILSEND_FAIL_COUNT0);
				es.setModifyDate(date);
				es.setState(Constant.EMAILSEND_STATE0);
				es.setSubject(subject);
				es.setUserId(userId);
				emailSendDao.insert(es);
			}
			if(user.getTelphone()!=null&&!user.getTelphone().equals("")&&user.getAreaCode()!=null&&!user.getAreaCode().equals("")){
				//SmsSendUtil.sendInviteMsg("系统",demand.getTitle(), url, user.getAreaCode(), user.getTelphone(), demand.getAmount());
				Properties prop = null;
				String content = null;
				if(user.getAreaCode().equals("+86")||user.getAreaCode().equals("0086")){
					prop = PropertiesReader.read(SmsConfigUtil.getSmsConfigFile(Constant.COUNTRY_CODE_CN));
					content = prop.getProperty("anyonehelps.invite.task.content");
					content = MessageFormat.format(content, new Object[] {"系统", demand.getTitle(), url});
				}else{
					prop = PropertiesReader.read(SmsConfigUtil.getSmsConfigFile(Constant.COUNTRY_CODE_US));
					content = prop.getProperty("anyonehelps.invite.task.content");
					content = MessageFormat.format(content, new Object[] {"系统", demand.getTitle(), url});
				}
				
				SmsSend ss = new SmsSend();
				ss.setContent(content);
				ss.setFailCount(Constant.EMAILSEND_FAIL_COUNT0);
				ss.setState(Constant.EMAILSEND_STATE0);
				ss.setAreaCode(user.getAreaCode());
				ss.setTelphone(user.getTelphone());
				ss.setUserId(userId);
				ss.setCreateDate(date);
				ss.setModifyDate(date);
				int n = smsSendDao.insert(ss);
				if(n>0){
					//pass
				}else{
					throw new Exception("发送短信失败");
				}
			}
			
			AdminRecommend ar = new AdminRecommend();
			ar.setDemandId(demandId);
			ar.setUserId(userId);
			ar.setCreateDate(date);
			ar.setModifyDate(date);
			int nResult = this.adminRecommendDao.insert(ar);
			if(nResult>0){
				responseObj.setCode(ResponseCode.SUCCESS_CODE);
			}else{
				responseObj.setCode(ResponseCode.DEMAND_FOLLOW_FAIL);
				responseObj.setMessage("推荐任务失败，请重试！");
			}
			
			
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException("推荐任务失败", e);
		}
		return responseObj;
	}
	
	

}
