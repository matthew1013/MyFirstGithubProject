package com.anyonehelps.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.anyonehelps.common.constants.Constant;
import com.anyonehelps.common.constants.ResponseCode;
import com.anyonehelps.common.util.Base64Util;
import com.anyonehelps.common.util.DateUtil;
import com.anyonehelps.common.util.DemandUtil;
import com.anyonehelps.common.util.ExceptionUtil;
import com.anyonehelps.common.util.PropertiesReader;
import com.anyonehelps.common.util.ReceiveDemandUtil;
import com.anyonehelps.common.util.StringUtil;
import com.anyonehelps.common.util.UserUtil;
import com.anyonehelps.common.util.stripe.StripeUtil;
import com.anyonehelps.dao.AccountDao;
import com.anyonehelps.dao.AccountDetailDao;
import com.anyonehelps.dao.BonusPointDao;
import com.anyonehelps.dao.ChargesDao;
import com.anyonehelps.dao.DemandDao;
import com.anyonehelps.dao.DemandFollowDao;
import com.anyonehelps.dao.DemandMessageDao;
import com.anyonehelps.dao.DemandSpecialtyDao;
import com.anyonehelps.dao.EmailSendDao;
import com.anyonehelps.dao.EvaluateDao;
import com.anyonehelps.dao.FriendDao;
import com.anyonehelps.dao.InviteDao;
import com.anyonehelps.dao.InvoiceNoDao;
import com.anyonehelps.dao.MessageSystemDao;
import com.anyonehelps.dao.MessageUserDao;
import com.anyonehelps.dao.ReceiveDemandDao;
import com.anyonehelps.dao.SmsSendDao;
import com.anyonehelps.dao.UserDao;
import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.Account;
import com.anyonehelps.model.AccountDetail;
import com.anyonehelps.model.BonusPoint;
import com.anyonehelps.model.Charges;
import com.anyonehelps.model.Demand;
import com.anyonehelps.model.DemandAttach;
import com.anyonehelps.model.DemandFollow;
import com.anyonehelps.model.DemandMessage;
import com.anyonehelps.model.DemandSpecialty;
import com.anyonehelps.model.EmailSend;
import com.anyonehelps.model.Evaluate;
import com.anyonehelps.model.Friend;
import com.anyonehelps.model.Invite;
import com.anyonehelps.model.InvoiceNo;
import com.anyonehelps.model.MessageSystem;
import com.anyonehelps.model.MessageUser;
import com.anyonehelps.model.NationalityCount;
import com.anyonehelps.model.PageSplit;
import com.anyonehelps.model.ReceiveDemand;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.model.User;
import com.anyonehelps.service.DemandService;
import com.anyonehelps.service.MessageSystemService;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;

@Service("demandService")
public class DemandServiceImpl extends BasicService implements DemandService {
	private static final Logger log = Logger.getLogger(DemandServiceImpl.class);
	@Autowired
	private DemandDao demandDao; 
	//去掉阶段 delete at 2015-12-22
	//@Autowired
	//private StageDao stageDao;
	@Autowired
	private ReceiveDemandDao receiveDemandDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private InviteDao inviteDao;
	@Autowired
	private DemandFollowDao demandFollowDao;
	@Autowired
	private DemandMessageDao demandMessageDao;
	@Autowired
	private MessageUserDao messageUserDao;
	@Autowired
	private MessageSystemDao messageSystemDao;
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private AccountDetailDao accountDetailDao;
	@Autowired
	private EvaluateDao evaluateDao;
	@Autowired
	private DemandSpecialtyDao demandSpecialtyDao;
	@Autowired
	private BonusPointDao bonusPointDao;
	@Autowired
	private InvoiceNoDao invoiceNoDao;
	@Autowired
	private FriendDao friendDao;
	@Autowired
	private EmailSendDao emailSendDao;
	@Autowired
	private SmsSendDao smsSendDao;
	
	@Autowired
	private ChargesDao chargesDao;

	@Resource(name = "messageSystemService")
	private MessageSystemService messageSystemService;
	
	@Value(value = "${bonus_point_parentlevel1}")
	private double bonusPointParentlevel1;
	@Value(value = "${bonus_point_parentlevel2}")
	private double bonusPointParentlevel2;
	@Value(value = "${bonus_point_parentlevel3}")
	private double bonusPointParentlevel3;

	
	public ResponseObject<Object> addDemand(Demand demand) throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>();
		if (null == demand) {
			responseObj.setCode(ResponseCode.PARAMETER_ERROR);
			responseObj.setMessage("参数无效");
		} else {
			String date = DateUtil.date2String(new Date());
			demand.setCreateDate(date);
			demand.setPayState(Constant.DEMAND_PAY_STATE1);
			double tempAmount = StringUtil.string2Double(demand.getAmount());
			BigDecimal bd = new BigDecimal(tempAmount);  
			demand.setAmount(String.valueOf(bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()));
			//DemandUtil.getRegionByIp(demand);
			try {
				Account a = accountDao.getAccountByUserId(demand.getUserId());
				double usd= StringUtil.string2Double(a.getUsd());
				boolean noPay = false;
				List<String> states = new ArrayList<String>();
				states.add(Constant.DEMAND_STATE_END);
				states.add(Constant.DEMAND_STATE_ARBITRATION);
				states.add(Constant.DEMAND_STATE_CLOSE);
				states.add(Constant.DEMAND_STATE_FINISH);
				states.add(Constant.DEMAND_STATE_PAY);
				states.add(Constant.DEMAND_STATE_READY);
				states.add(Constant.DEMAND_STATE_RECEIVE);
				states.add(Constant.DEMAND_STATE_SELECT);
				states.add(Constant.DEMAND_STATE_START);
				if (usd < StringUtil.string2Double(demand.getAmount())) {
					if(this.demandDao.countByUserIdAndState(demand.getUserId(), states)>Constant.DEMAND_NO_PAY_COUNT-1){
						Map<String, String> data = new HashMap<String, String>();
						data.put("usd", a.getUsd());
						data.put("freezeUsd", a.getFreezeUsd());
						responseObj.setCode(ResponseCode.ACCOUNT_INSUFFICIENT_BALANCE);
						responseObj.setMessage("帐户余额不足,请充值");
						responseObj.setData(data);
						return responseObj;
					}else{
						demand.setPayState(Constant.DEMAND_PAY_STATE0);
						noPay = true;
					}
					
				}
				
				int nResult = this.demandDao.insertDemand(demand);
				double tempNewusd = usd - StringUtil.string2Double(demand.getAmount());
				BigDecimal   b   =   new   BigDecimal(tempNewusd);  
				double newusd = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				if (newusd >= 0) {
					// ignore
				} else {
					if(this.demandDao.countByUserIdAndState(demand.getUserId(), states)>Constant.DEMAND_NO_PAY_COUNT-1){
						throw new Exception("帐户余额不足,请充值");
					}else{
						demand.setPayState(Constant.DEMAND_PAY_STATE0);
						noPay = true;
					}
				}
				
				if (nResult > 0) {
					if(!noPay){
						// 账户支付，修改账户余额
						if (this.accountDao.modifyUsdByUserId(a.getUserId(), String.valueOf(newusd), date) > 0) {
							if(this.accountDao.addFreezeUsd(a.getUserId(), demand.getAmount(), date) > 0){
								// pass
							}else{
								throw new Exception("任务添加失败");
							}
						} else {
							throw new Exception("任务添加失败");
						}
						
						AccountDetail detail = new AccountDetail(); 
						detail.setAmount(demand.getAmount());
						detail.setRealAmount(detail.getAmount());
						detail.setUserId(demand.getUserId());
						detail.setState(Constant.ACCOUNT_DETAIL_STATE2);
						//detail.setName("任务金额冻结");   haokun deleted 2017/03/27
                        detail.setName("收取任务保证金");   //haokun edit 2017/03/27 改成“任务保证金”	
						detail.setCreateDate(date);
						detail.setCurrency("美元");
						detail.setModifyDate(date);
						detail.setType(Constant.ACCOUNT_DETAIL_TYPE33);
						detail.setRemark("任务号:"+demand.getId());
						detail.setDemandId(demand.getId());
						detail.setThirdNo("");
						InvoiceNo in = this.invoiceNoDao.getByType(Constant.INVOICE_TYPE1);
						if(in!=null){
							if(this.invoiceNoDao.modifyStateById(in.getId(),date) > 0){
								detail.setInvoiceNo(in.getInvoiceNo());
							}
						}
						this.accountDetailDao.insertAccountDetail(detail);
					}
					
					/*插入技能 start*/ 
					List<DemandSpecialty> dss = new ArrayList<DemandSpecialty>();
					if(demand.getDs()!=null){
						for(int i=0;i<demand.getDs().size();i++){
							DemandSpecialty ds = new DemandSpecialty(); 
							ds.setDemandId(demand.getId());
							ds.setSpecialtyTypeId(demand.getDs().get(i).getSpecialtyTypeId());
							ds.setSpecialtyId(demand.getDs().get(i).getSpecialtyId());
							dss.add(ds);
						}
					}
					if(dss.size()>0) this.demandSpecialtyDao.insert(dss);
					/*插入技能 end*/
					
					List<Friend> friends = this.friendDao.getByFriendId(demand.getUserId());
					if(friends!=null){
						for(Friend f : friends){
							MessageSystem ms = new MessageSystem();
							ms.setLevel(Constant.MESSAGESYSTEM_LEVEL_INFO);
							ms.setContent("您关注的人"+f.getFriendUser().getNickName()+"发布了一个任务，快去查看");
							ms.setCreateDate(date);
							ms.setModifyDate(date);
							ms.setUserId(f.getUserId());
							ms.setTitle("您关注的人"+f.getFriendUser().getNickName()+"发布了一个任务，任务标题是"+demand.getTitle()+"，快去查看");
							ms.setLink("/task/detail.jsp?id="+Base64Util.encode(demand.getId()));
							ms.setState(Constant.MESSAGESYSTEM_STATE_UNREAD);
							this.messageSystemService.saveMessage(ms);
						}
					}
					
					/*求助大牛推送*/
					if(!StringUtil.isEmpty(demand.getProUserId())){
						User pu = this.userDao.getOnlyUserOpenInfoById(demand.getProUserId());
						if(pu!=null){
							MessageSystem ms = new MessageSystem();
							ms.setLevel(Constant.MESSAGESYSTEM_LEVEL_INFO);
							ms.setContent("有人向您发布了一个求助任务："+demand.getTitle()+"，快去帮助他完成任务吧");
							ms.setCreateDate(date);
							ms.setModifyDate(date);
							ms.setUserId(pu.getId());
							ms.setTitle("有人向您发布了一个求助任务，快去帮助他完成任务吧");
							ms.setLink("/task/detail.jsp?id="+Base64Util.encode(demand.getId()));
							ms.setState(Constant.MESSAGESYSTEM_STATE_UNREAD);
							this.messageSystemService.saveMessage(ms);
						}
					}
					/*求助大牛推送*/
						
					responseObj.setCode(ResponseCode.SUCCESS_CODE);
					responseObj.setData(demand.getId());
				} else {
					responseObj.setCode(ResponseCode.DEMAND_INSERT_ERROR);
					responseObj.setMessage("发布任务失败，请重试");
				}
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException(e);
			}
		}
		return responseObj;
	}
	@Override
	public ResponseObject<Object> addInvite(String nickName, String demandId, String friendId,
			String userId) throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>();
		if (!DemandUtil.validateId(demandId)
				||!UserUtil.validateId(friendId)
				||!UserUtil.validateId(userId)) {
			responseObj.setCode(ResponseCode.PARAMETER_ERROR);
			responseObj.setMessage("参数无效");
			return responseObj;
		} 
		try {
			Demand demand = this.demandDao.getDemandById(demandId);
			if(demand==null){
				responseObj.setCode(ResponseCode.DEMAND_NOT_EXISTS);
				responseObj.setMessage("该任务不存在或者已经删除");
				return responseObj;
			}
			if(!demand.getUserId().equals(userId)){
				responseObj.setCode(ResponseCode.PARAMETER_ERROR);
				responseObj.setMessage("任务不是您发布的，不能发起邀请！");
				return responseObj;
			}
			if(!demand.getState().equals(Constant.DEMAND_STATE_READY)&&!demand.getState().equals(Constant.DEMAND_STATE_RECEIVE)){
				responseObj.setCode(ResponseCode.DEMAND_NOT_EXISTS);
				responseObj.setMessage("任务已经选定开始了，不能发起邀请了");
				return responseObj;
			}
			User user = this.userDao.getUserById(friendId);
			if(user==null){
				responseObj.setCode(ResponseCode.DEMAND_NOT_EXISTS);
				responseObj.setMessage("邀请人不存在！");
				return responseObj;
			}
			List<Invite> invites = new ArrayList<Invite>();
			
			Invite invite = new Invite(); 
			invite.setDemandId(demandId);
			invite.setState("0");
			invite.setUserId(friendId);
			invite.setUsername(user.getNickName());
			invites.add(invite);
			
			if(invites.size()>0) this.inviteDao.insertInvites(invites);
			
			// 这里还没做配置文件
			String url = "www.anyonehelps.com/task/detail.jsp?id="+Base64Util.encode(demand.getId());
			String date = DateUtil.date2String(new Date());
			
			if(user.getEmail()!=null&&!user.getEmail().equals("")&&user.getEmailState().equals(Constant.USER_EMAIL_STATE1)){
				//MailSendUtil.sendInviteMsg(nickName,user.getNickName(),demand.getTitle(), url, user.getEmail(), demand.getAmount(), demand.getExpireDate());
				
				Properties prop = PropertiesReader.read(Constant.EMAIL_PROPERTIES_FILE);
				String subject = prop.getProperty("anyonehelps.invite.task.subject");
				subject = MessageFormat.format(subject, new Object[] { user.getNickName() , nickName });
				String content = prop.getProperty("anyonehelps.invite.task.content");
				content = MessageFormat.format(content, new Object[] { user.getNickName(), nickName, demand.getTitle(), demand.getAmount(), demand.getExpireDate(), url});
				
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
			
			MessageSystem ms= new MessageSystem();
			ms.setContent("您好！用户"+nickName+"邀请您参与任务"+demand.getTitle()+"，点击去接单赚取高额报酬！");
			ms.setTitle("您好！用户"+nickName+"邀请您参与任务"+demand.getTitle());
			ms.setUserId(friendId);
			ms.setLink("/task/detail.jsp?id=" + Base64Util.encode(demand.getId()));
			ms.setCreateDate(date);
			ms.setModifyDate(date);
			ms.setLevel(Constant.MESSAGESYSTEM_LEVEL_INFO);
			ms.setState(Constant.MESSAGEUSER_STATE_UNREAD);
			this.messageSystemDao.insertMessage(ms);
			/*if(user.getTelphone()!=null&&!user.getTelphone().equals("")){
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
					responseObj.setCode(ResponseCode.SUCCESS_CODE);
					return responseObj;
				}else{
					throw new Exception("发送短信失败");
				}
			}*/
			responseObj.setCode(ResponseCode.SUCCESS_CODE);
			return responseObj;
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public <T> ResponseObject<PageSplit<T>> searchByKey(String userId, String key, List<String>states, int pageSize, int pageNow)
	        throws ServiceException {
		try {
			key = StringUtil.escapeStringOfSearchKey(key);  
			int rowCount = 0;
			try {
				rowCount = this.demandDao.countByKey(userId, key,states);
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException("获取任务个数失败", e);
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
					List<Demand> demands = this.demandDao.searchByKey(userId, key, states, startIndex, pageSize);
					if (demands != null && !demands.isEmpty()) {
						for (Demand d : demands) {
							if (d.getReceiveDemands()!=null && !d.getReceiveDemands().isEmpty()){
								for (int i=0;i<d.getReceiveDemands().size();i++){
									d.getReceiveDemands().get(i).setUser(this.userDao.getOnlyUserOpenInfoById(d.getReceiveDemands().get(i).getUserId()));
								}
							}
								
							pageSplit.addData((T)d);
						}
					}
				} catch (Exception e) {
					throw ExceptionUtil.handle2ServiceException("获取任务列表失败", e);
				}
				responseObj.setData(pageSplit);
			} else {
				responseObj.setMessage("没有任务");
			}
			return responseObj;
		} catch (ServiceException e) {
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> ResponseObject<PageSplit<T>> searchAllByKey(String sortType,String key, String nationality, String type, String typeSecond, String minAmount, String maxAmount, List<String> tags, int pageSize, int pageNow)
	        throws ServiceException {
		try {
			key = StringUtil.escapeStringOfSearchKey(key);  
			int rowCount = 0;
			try {
				rowCount = this.demandDao.countAllByKey(sortType,key,nationality,type,typeSecond,minAmount,maxAmount, tags);
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException("获取任务个数失败", e);
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
					List<Demand> demands = this.demandDao.searchAllByKey(sortType,key,nationality,type,typeSecond,minAmount, maxAmount, tags, startIndex, pageSize);
					if (demands != null && !demands.isEmpty()) {
						for (Demand d : demands) {
							//本处应该删除敏感信息。暂未处理
							User user = this.userDao.getUserOpenInfoById(d.getUserId());
							d.setMessageCount(String.valueOf(this.demandMessageDao.countByDemandId(d.getId())));
							d.setUserNickName(user.getNickName());
							pageSplit.addData((T)d);
							
						}
					}
				} catch (Exception e) {
					throw ExceptionUtil.handle2ServiceException("获取任务列表失败", e);
				}
				responseObj.setData(pageSplit);
			} else {
				responseObj.setMessage("没有任务");
			}
			return responseObj;
		} catch (ServiceException e) {
			throw e;
		}
	}

	public ResponseObject<Demand> getDemandById(String userId,String id) throws ServiceException {
		ResponseObject<Demand> result = new ResponseObject<Demand>();
		if (StringUtil.isEmpty(id)||StringUtil.isEmpty(userId)) {
			result.setCode(ResponseCode.PARAMETER_ERROR);
			result.setMessage("参数无效");
		} else {
			try {
				Demand demand = this.demandDao.getDemandById(id);
				
				if (demand != null) {
					if(demand.getUserId().equals(userId)){
						
						if (demand.getReceiveDemands()!=null && !demand.getReceiveDemands().isEmpty()){
							for (int i=0;i<demand.getReceiveDemands().size();i++){
								demand.getReceiveDemands().get(i).setUser(this.userDao.getOnlyUserOpenInfoById(demand.getReceiveDemands().get(i).getUserId()));
							}
						}
						
						result.setCode(ResponseCode.SUCCESS_CODE);
						result.setData(demand);
					}else{
						result.setCode(ResponseCode.PARAMETER_ERROR);
						result.setMessage("该条任务不是您发布的任务！");
					}
				} else {
					result.setCode(ResponseCode.DEMAND_ID_EXISTS);
					result.setMessage("任务不存在");
				}
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException(e);
			}
		}
		return result;
	}
	
	public ResponseObject<Demand> getDemandByIdOfWriter(String id,String userId) throws ServiceException {
		ResponseObject<Demand> result = new ResponseObject<Demand>();
		if (StringUtil.isEmpty(id)) {
			result.setCode(ResponseCode.PARAMETER_ERROR);
			result.setMessage("参数无效");
		} else {
			try {
				Demand demand = this.demandDao.getDemandById(id); 
				if (demand != null) { 
					User user = this.userDao.getUserById(demand.getUserId());
					demand.setUserNickName(user.getNickName());
					if(userId!=null&&!userId.equals("")){
						//查看是否已经关注
						demand.setFollow(String.valueOf(this.demandFollowDao.countByUserIdAndDemandId(demand.getId(), userId)));
						//查看是否已经接受了该任务
						demand.setReceive(String.valueOf(this.receiveDemandDao.countByUserIdAndDemandId(demand.getId(), userId)));
					}
					demand.setDemandMessages(this.demandMessageDao.retrieveMessages(demand.getId()));
					if(demand.getReceiveDemands()!=null){
						for(int i = 0;i<demand.getReceiveDemands().size();i++){
							demand.getReceiveDemands().get(i).setUser(this.userDao.getOnlyUserOpenInfoById(demand.getReceiveDemands().get(i).getUserId()));
						}
					}
					if(demand.getDemandMessages()!=null){
						for(int i = 0;i<demand.getDemandMessages().size();i++){
							demand.getDemandMessages().get(i).setReplyMessages(this.demandMessageDao.retrieveMessagesByParentId(demand.getDemandMessages().get(i).getId()));
						}
					}
					result.setCode(ResponseCode.SUCCESS_CODE);
					result.setData(demand);
				} else {
					result.setCode(ResponseCode.DEMAND_ID_EXISTS);
					result.setMessage("任务不存在");
				}
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException(e);
			}
		}
		return result;
	}
	
	public ResponseObject<Set<Demand>> getRelevantDemandById(String id) throws ServiceException {
		ResponseObject<Set<Demand>> result = new ResponseObject<Set<Demand>>();
		if (!DemandUtil.validateId(id)) {
			result.setCode(ResponseCode.PARAMETER_ERROR);
			result.setMessage("参数无效");
		} else {
			try {
				Demand demand = this.demandDao.getDemandById(id);
				if (demand != null) { 
					//查找6条记录
					int demandLength = 6;
					List<Demand> demands = this.demandDao.searchAllByKey("1","","","",demand.getTypeSecond(),"","", null,1, demandLength);
					if(demands==null){
						demands = this.demandDao.searchAllByKey("1","","",demand.getType(),"","","", null,1, demandLength);
					}else if(demands.size()<demandLength){  //不够6条记录，再查
						List<Demand> tempDemands = this.demandDao.searchAllByKey("1","","",demand.getType(),"","","", null,1, demandLength-demands.size());
						if(tempDemands!=null){
							for(Demand d:tempDemands){
								boolean isExist = false;
								for(Demand td:demands){
									if(td.getId().equals(d.getId())){
										isExist = true;
									}
								}
								if(!isExist){
									demands.add(d);
								}
							}
						}
					}
					if(demands.size()<demandLength){ //不够6条记录，再查
						List<Demand> tempDemands = this.demandDao.searchAllByKey("1","","","","","","", null,1, demandLength-demands.size());
						if(tempDemands!=null){
							for(Demand d:tempDemands){
								boolean isExist = false;
								for(Demand td:demands){
									if(td.getId().equals(d.getId())){
										isExist = true;
									}
								}
								if(!isExist){
									demands.add(d);
								}
							}
						}
					}
					
					for(Demand d:demands){
						if(d.getId().equals(id)){
							demands.remove(d);
							break;
						}
					}
					
					result.setCode(ResponseCode.SUCCESS_CODE);
					result.setData(new HashSet<Demand>(demands));
				} else {
					result.setCode(ResponseCode.DEMAND_ID_EXISTS);
					result.setMessage("没有相关任务");
				}
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException(e);
			}
		}
		return result;
	}
	
	
	public ResponseObject<Object> addReceiveDemand(ReceiveDemand receiveDemand) throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>();
		if (null == receiveDemand) {
			responseObj.setCode(ResponseCode.PARAMETER_ERROR);
			responseObj.setMessage("参数无效");
		} else {
			String date = DateUtil.date2String(new Date());
			receiveDemand.setCreateDate(date);
			//ReceiveDemandUtil.getRegionByIp(receiveDemand);
			receiveDemand.setState(Constant.RECEIVEDEMAND_STATE_UNCHECKED); 
			try {
				Demand demand = this.demandDao.getDemandById(receiveDemand.getDemandId());
				if(demand==null){
					responseObj.setCode(ResponseCode.DEMAND_NOT_EXISTS);
					responseObj.setMessage("该任务不存在或者已经删除");
					return responseObj;
				}
				if(demand.getReceiveDemands()!=null){
					for(ReceiveDemand rd : demand.getReceiveDemands()){
						if(rd.getUserId().equals(receiveDemand.getUserId())){
							responseObj.setCode(ResponseCode.RECEIVEDEMAND_HAS_BENN_RECEIVE);
							responseObj.setMessage("您已接受过这个任务,请等待发布者开始任务！");
							return responseObj;
						}
					}
				}
				if(!demand.getState().equals(Constant.DEMAND_STATE_READY)&&!demand.getState().equals(Constant.DEMAND_STATE_RECEIVE)){
					responseObj.setCode(ResponseCode.DEMAND_NOT_EXISTS);
					responseObj.setMessage("该任务，已经选定开始了，不能再投标了");
					return responseObj;
				}
				
				if("".equals(receiveDemand.getAmount())||receiveDemand.getAmount()==null){
					receiveDemand.setAmount(demand.getAmount());
				}
				/*注意，如果程序与数据库布署在不同的机器上，那么这个当前时间与数据库的时间可能会不同，可能会出问题*/
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String currentDate = df.format(new Date());  
				if(demand.getExpireDate().compareTo(currentDate)<=0){
					responseObj.setCode(ResponseCode.DEMAND_NOT_EXISTS);
					responseObj.setMessage("任务完成截止时间已经过，不能再投标了");
					return responseObj;
				}
				if(demand.getUserId().equals(receiveDemand.getUserId())){
					responseObj.setCode(ResponseCode.RECEIVEDEMAND_INSERT_ERROR);
					responseObj.setMessage("不能接受自己发布的任务");
					return responseObj;
				}
				if(receiveDemand.getAmount()==""){
					receiveDemand.setAmount(null);
				}
				int iresult = this.receiveDemandDao.insertReceiveDemand(receiveDemand);
				if (iresult > 0) {
					if(demand.getState().equals(Constant.DEMAND_STATE_READY)){
						int n = this.demandDao.modifyDemandState(demand.getId(), demand.getUserId(), Constant.DEMAND_STATE_RECEIVE, date);
						if(n<=0){
							throw new Exception();
						}
					}
					User user = this.userDao.getUserById(demand.getUserId());
					if(user != null){
						//写系统消息
						MessageSystem ms = new MessageSystem();
						ms.setCreateDate(date);
						ms.setLevel(Constant.MESSAGESYSTEM_LEVEL_INFO);
						ms.setContent("您好！有人对您发布的任务"+demand.getTitle()+"进行了投标，点击立即查看");
						//还没写配置
						ms.setLink("/dashboard/Task/pubDetail.jsp?id="+Base64Util.encode(demand.getId()));
						ms.setModifyDate(date);
						ms.setTitle("您好！有人对您发布的任务"+demand.getTitle()+"进行了投标，点击立即查看");
						ms.setUserId(user.getId());
						ms.setState(Constant.MESSAGESYSTEM_STATE_UNREAD);
						this.messageSystemService.saveMessage(ms);
						
						//邮箱提醒。
						if(user.getEmail()!=null&&!user.getEmail().equals("")&&user.getEmailState().equals(Constant.USER_EMAIL_STATE1)){
							//String subject = "您好！有人对您发布的任务"+demand.getTitle()+"进行了投标，请查看";
							//String content = "您好！有人对您发布的任务"+demand.getTitle()+"进行了投标，<a href='www.anyonehelps.com/task/detail.jsp?id="+Base64Util.encode(demand.getId())+"'>点击立即查看</a>";
							//content +="<br><br>感谢您对AnyoneHelps的支持!<br>AnyoneHelps团队<br>www.anyonehelps.com<br>";  /*haokun add*/
							//content +="<img src='https://www.anyonehelps.com/assets/pages/img/index/logo-bottom.png'/>";/*haokun add*/
							
							Properties prop = PropertiesReader.read(Constant.EMAIL_PROPERTIES_FILE);              //haokun modified  2017/02/10
							String subject = prop.getProperty("anyonehelps.receive.task.subject");     //haokun modified  2017/02/10
							String content = prop.getProperty("anyonehelps.receive.task.content");     //haokun modified  2017/02/10
							content = MessageFormat.format(content, new Object[] {user.getNickName(), demand.getTitle(),"www.anyonehelps.com/task/detail.jsp?id="+Base64Util.encode(demand.getId())});    //haokun modified  2017/02/10显示用户名不显示密码
					
							/*Properties prop = PropertiesReader.read(Constant.EMAIL_PROPERTIES_FILE);
							String subject = prop.getProperty("anyonehelps.invite.task.subject");
							subject = MessageFormat.format(subject, new Object[] { user.getNickName() , nickName });
							String content = prop.getProperty("anyonehelps.invite.task.content");
							content = MessageFormat.format(content, new Object[] { user.getNickName(), nickName, demand.getTitle(), demand.getAmount(), demand.getExpireDate(), url});
							*/
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
						//短信提醒。
						/*  delete by Haokun 2017/01/26    有人接单时不需要短信提醒    
						if(user.getTelphone()!=null&&!user.getTelphone().equals("")){
							//SmsSendUtil.sendInviteMsg(user.getNickName(),demand.getTitle(), url, user.getAreaCode(), user.getTelphone(), demand.getAmount());
							String content = null;
							content = "您好！有人对您发布的任务"+demand.getTitle()+"进行了投标，任务链接:www.anyonehelps.com/task/detail.jsp?id="+Base64Util.encode(demand.getId());
							
							SmsSend ss = new SmsSend();
							ss.setContent(content);
							ss.setFailCount(Constant.EMAILSEND_FAIL_COUNT0);
							ss.setState(Constant.EMAILSEND_STATE0);
							ss.setAreaCode(user.getAreaCode());
							ss.setTelphone(user.getTelphone());
							ss.setUserId(user.getId());
							ss.setCreateDate(date);
							ss.setModifyDate(date);
							this.smsSendDao.insert(ss);
						}
						*/
					}
					responseObj.setCode(ResponseCode.SUCCESS_CODE);
				} else {
					responseObj.setCode(ResponseCode.RECEIVEDEMAND_INSERT_ERROR);
					responseObj.setMessage("接受任务失败，请重试");
				}
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException(e);
			}
		}
		return responseObj;
	}

	public ResponseObject<List<ReceiveDemand>> searchByReceiveDemand(
			String userId, String id) throws ServiceException {
		ResponseObject<List<ReceiveDemand>> result = new ResponseObject<List<ReceiveDemand>>();
		try {
			Demand demand = this.demandDao.getDemandById(id);
			if(demand==null){
				result.setCode(ResponseCode.RECEIVEDEMAND_ID_EXISTS);
				result.setMessage("系统中没有该任务记录或者已经被删除，请刷新页面后再操作");
				return result;
			}
			if(!demand.getUserId().equals(userId)){
				result.setCode(ResponseCode.RECEIVEDEMAND_ID_ERROR);
				result.setMessage("该任务记录不是您发布的任务，请刷新页面后再操作");
				return result;
			}
			List<ReceiveDemand> rds= this.receiveDemandDao.getReceiveDemandByDemandId(id);
			
			result.setCode(ResponseCode.SUCCESS_CODE);
			result.setData(rds);
			return result;
			
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
	}
	
	public ResponseObject<Object> selectDemand(String userId, String demandId, String rdId)
			throws ServiceException {
		ResponseObject<Object> result = new ResponseObject<Object>();
		if (demandId==null||userId==null) {
			result.setCode(ResponseCode.PARAMETER_ERROR);
			result.setMessage("参数无效");
			return result;
		} else {
			try {
				Demand d = this.demandDao.getDemandById(demandId);
				if(d==null){
					result.setCode(ResponseCode.DEMAND_NOT_EXISTS);
					result.setMessage("任务不存在！");
					return result;
				}
				if(!d.getUserId().equals(userId)){
					result.setCode(ResponseCode.RECEIVEDEMAND_ID_ERROR);
					result.setMessage("不是您发布的任务，您无法开始，请刷新页面后再重试！");
					return result;
				}
				if(!d.getState().equals(Constant.DEMAND_STATE_READY)&&!d.getState().equals(Constant.DEMAND_STATE_RECEIVE)){
					if(d.getState().equals(Constant.DEMAND_STATE_CLOSE)){
						result.setMessage("任务已关闭！");
					}else{
						result.setMessage("任务已匹配，无需再匹配！");
					}
					result.setCode(ResponseCode.DEMAND_STATE_ERROR);
					return result;
				} 
				if(d.getPayState().equals(Constant.DEMAND_PAY_STATE0)){
					result.setMessage("请先支付任务悬赏金额后再匹配！");
					result.setCode(ResponseCode.DEMAND_STATE_ERROR);
					return result;
				}
				String date = DateUtil.date2String(new Date());
				List<ReceiveDemand> rds = d.getReceiveDemands();
				
				ReceiveDemand selectRD  = null;
				if(rds!=null){
					for(ReceiveDemand rd:rds){
						if(rd.getId().equals(rdId)){
							selectRD = rd;
						}
					}
				}
				if(selectRD!=null){
					if(selectRD.getAmount()!=null&&!selectRD.getAmount().equals("")){
						//含有报价
						double demandAmount = Double.valueOf(d.getAmount());
						double amount = Double.valueOf(selectRD.getAmount());
						if(amount > demandAmount){                //报价高于发单人价格
							AccountDetail detail = new AccountDetail(); 
							detail.setAmount(String.valueOf(amount-demandAmount));
							detail.setRealAmount(detail.getAmount());
							detail.setUserId(d.getUserId());
							detail.setState(Constant.ACCOUNT_DETAIL_STATE2);
							//detail.setName("任务金额冻结(匹配任务)");   //haokun delete 2017/03/27
							detail.setName("收取任务保证金（匹配任务）"); //haokun edit 2017/03/27 改成“任务保证金”		  //haokun modified 2017/03/09 修改名字
							detail.setCreateDate(date);
							detail.setCurrency("美元");
							detail.setModifyDate(date);
							detail.setType(Constant.ACCOUNT_DETAIL_TYPE33);
							detail.setRemark("选择"+selectRD.getUserNickName()+"做单号为"+d.getId()+"的任务，其报价高于任务金额，冻结金额：$"+detail.getAmount());
							
							detail.setDemandId(demandId);
							detail.setThirdNo("");
							//发票号
							InvoiceNo in = this.invoiceNoDao.getByType(Constant.INVOICE_TYPE1);
							if(in!=null){
								if(this.invoiceNoDao.modifyStateById(in.getId(),date) > 0){
									detail.setInvoiceNo(in.getInvoiceNo());
								}
							}
							
							Account a = accountDao.getAccountByUserId(detail.getUserId());
							double usd= StringUtil.string2Double(a.getUsd());
							double newusd = usd - StringUtil.string2Double(detail.getAmount());
							if (newusd >= 0) {
								// ignore
							} else {
								result.setCode(ResponseCode.ACCOUNT_INSUFFICIENT_BALANCE);
								result.setMessage("您选择的做任务人中含有报价,但您帐户余额不足以支付其报价,请充值后再开始任务");
								return result;
							}
							// 账户支付，修改账户余额
							if (this.accountDao.modifyUsdByUserId(a.getUserId(), String.valueOf(newusd), date)  > 0) {
								//增加冻结金额
								if(this.accountDao.addFreezeUsd(a.getUserId(), detail.getAmount(), date) > 0){
									// pass
								}else{
									throw new Exception();
								}
							} else {
								throw new Exception();
							}
							this.accountDetailDao.insertAccountDetail(detail);
						}else if(amount<demandAmount){    //报价低于发单人价格
							AccountDetail detail = new AccountDetail(); 
							detail.setAmount(String.valueOf(demandAmount-amount));
							detail.setRealAmount(detail.getAmount());
							detail.setUserId(d.getUserId());
							detail.setState(Constant.ACCOUNT_DETAIL_STATE2);
							//detail.setName("冻结金额返还（匹配任务）");   //haokun delete 2017/03/27 修改名字
							detail.setName("返还任务保证金（匹配任务）");  //haokun edit 2017/03/27 改成“任务保证金”		 //haokun modified 2017/03/09 修改名字
							detail.setCreateDate(date);
							detail.setCurrency("美元");
							detail.setModifyDate(date);
							detail.setType(Constant.ACCOUNT_DETAIL_TYPE18);
							detail.setRemark("选择"+selectRD.getUserNickName()+"做单号为"+d.getId()+"的任务，其报价低于任务金额，冲正金额：$"+detail.getAmount());
							detail.setDemandId(demandId);
							detail.setThirdNo("");
							//发票号
							InvoiceNo in = this.invoiceNoDao.getByType(Constant.INVOICE_TYPE3);
							if(in!=null){
								if(this.invoiceNoDao.modifyStateById(in.getId(),date) > 0){
									detail.setInvoiceNo(in.getInvoiceNo());
								}
							}
							
							Account a = accountDao.getAccountByUserId(detail.getUserId());
							double usd= StringUtil.string2Double(a.getUsd());
							double newusd = usd + StringUtil.string2Double(detail.getAmount());
							
							// 账户支付，修改账户余额
							if (this.accountDao.modifyUsdByUserId(a.getUserId(), String.valueOf(newusd), date)  > 0) {
								//减小冻结金额
								if(this.accountDao.reduceFreezeUsd(a.getUserId(), detail.getAmount(), date) > 0){
									// pass
								}else{
									throw new Exception();
								}
							} else {
								throw new Exception();
							}
							this.accountDetailDao.insertAccountDetail(detail);
						}
					}
					int iResult = this.receiveDemandDao.modifyState(selectRD.getId(), Constant.RECEIVEDEMAND_STATE_CHECKED);

					if (iResult > 0) {
						int nResult = this.demandDao.modifyDemandState(demandId, userId, Constant.DEMAND_STATE_SELECT, date);
						if(nResult>0){
							//邮箱提醒 add by haokun 2017/01/26 选择接单人，增加邮件提醒
							User user = this.userDao.getUserById(selectRD.getUserId());
							if(user.getEmail()!=null&&!user.getEmail().equals("")&&user.getEmailState().equals(Constant.USER_EMAIL_STATE1)){
								/*
								String subject = "[AnyoneHelps] 恭喜您接收的任务成功中标";
								String content = "尊敬的用户！<br>";
								content += "您投标'"+d.getTitle()+"'任务中标了，请尽快到任务管理的已接收任务页面开始任务，并按要求完成任务<a href='www.anyonehelps.com/task/detail.jsp?id="+Base64Util.encode(demandId)+"'>点击立即查看</a>。<br>中标价格："+selectRD.getAmount()+"<br>发单人："+d.getUser().getNickName()+"<br>其他联系方式联系方式是:";
								if(d.getUser()!=null){
								   if(d.getUser().getEmail()!=null){
									  content += "邮箱:"+d.getUser().getEmail();
								   }
								   if(d.getUser().getTelphone()!=null){
									  content += ",手机:"+d.getUser().getAreaCode()+d.getUser().getTelphone();
								   }
								}
								content +="<br><br>感谢您对AnyoneHelps的支持!<br>AnyoneHelps团队<br>www.anyonehelps.com<br>";  /*haokun add*/
								
								Properties prop = PropertiesReader.read(Constant.EMAIL_PROPERTIES_FILE);              //haokun modified  2017/02/10
								String subject = prop.getProperty("anyonehelps.select.task.subject");     //haokun modified  2017/02/10
								String content = prop.getProperty("anyonehelps.select.task.content");     //haokun modified  2017/02/10
								content = MessageFormat.format(content, new Object[] {user.getNickName(),d.getTitle(),"www.anyonehelps.com/task/detail.jsp?id="+Base64Util.encode(demandId), selectRD.getAmount(),d.getUser().getNickName()});    //haokun modified  2017/02/10显示用户名不显示密码
						
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
							
							//还没给接单人发短信或邮箱提醒。
							String content = "您投标'"+d.getTitle()+"'任务中标了，请尽快到任务管理的已接收任务页面开始任务，并按要求完成任务.同时对方联系方式是:";
							if(d.getUser()!=null){
								if(d.getUser().getEmail()!=null){
									content += "邮箱:"+d.getUser().getEmail();
								}
								if(d.getUser().getTelphone()!=null){
									content += ",手机:"+d.getUser().getAreaCode()+d.getUser().getTelphone();
								}
							}
							MessageSystem ms = new MessageSystem();
							ms.setCreateDate(date);
							ms.setLevel(Constant.MESSAGESYSTEM_LEVEL_INFO);
							ms.setContent(content);
							//还没写配置
							ms.setLink("/dashboard/Task/accDetail.jsp?id="+Base64Util.encode(demandId));
							ms.setModifyDate(date);
							ms.setTitle("恭喜您！任务"+demandId+"匹配成功，点击查看并尽快开始任务吧！");
							ms.setUserId(selectRD.getUserId());
							ms.setState(Constant.MESSAGESYSTEM_STATE_UNREAD);
							this.messageSystemService.saveMessage(ms);
							result.setCode(ResponseCode.SUCCESS_CODE);
							return result;
						}else{
							// 进行事务回滚
							throw new Exception();
						}
					} else {
						// 进行事务回滚
						throw new Exception();
					}
				}else{
					result.setCode(ResponseCode.RECEIVEDEMAND_NOT_EXISTS);
					result.setMessage("没有选择要匹配的接单人");
					return result;
				}
				
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException(e);
			}
		}
	}
	
	public ResponseObject<Object> startDemand(String userId, String nickName, String demandId)
			throws ServiceException {
		ResponseObject<Object> result = new ResponseObject<Object>();
		if (demandId==null||userId==null) {
			result.setCode(ResponseCode.PARAMETER_ERROR);
			result.setMessage("参数无效");
			return result;
		} else {
			try {
				Demand d = this.demandDao.getDemandById(demandId);
				if(d==null){
					result.setCode(ResponseCode.DEMAND_NOT_EXISTS);
					result.setMessage("任务不存在！");
					return result;
				}
				if(!d.getState().equals(Constant.DEMAND_STATE_SELECT)){
					if(d.getState().equals(Constant.DEMAND_STATE_CLOSE)){
						result.setMessage("发单人已关闭任务,无法开始！");
					}else{
						result.setMessage("任务已开始，无需再开始！");
					}
					result.setCode(ResponseCode.DEMAND_STATE_ERROR);
					return result;
				}
				String date = DateUtil.date2String(new Date());
				List<ReceiveDemand> rds = d.getReceiveDemands();
				
				//int selectLenght = 0;
				ReceiveDemand selectRD  = null;
				if(rds!=null){
					for(ReceiveDemand rd:rds){
						if(rd.getState().equals(Constant.RECEIVEDEMAND_STATE_CHECKED)&&rd.getUserId().equals(userId)){
							selectRD = rd;
						}
					}
				}
				
				if(selectRD!=null){
					int nResult = this.demandDao.modifyDemandState(demandId, d.getUserId(), Constant.DEMAND_STATE_START, date);
					if(nResult>0){
						//还没给接单人发短信或邮箱提醒。
						String content = "您好!"+nickName+"已经开始执行您发布'"+d.getTitle()+"'的任务";
						MessageSystem ms = new MessageSystem();
						ms.setCreateDate(date);
						ms.setLevel(Constant.MESSAGESYSTEM_LEVEL_INFO);
						ms.setContent(content);
						//还没写配置
						ms.setLink("/dashboard/Task/pubDetail.jsp?id="+Base64Util.encode(demandId));
						ms.setModifyDate(date);
						ms.setTitle(content);
						ms.setUserId(d.getUserId());
						ms.setState(Constant.MESSAGESYSTEM_STATE_UNREAD);
						this.messageSystemService.saveMessage(ms);
						result.setCode(ResponseCode.SUCCESS_CODE);
						return result;
					}else{
						result.setCode(ResponseCode.DEMAND_STATE_ERROR);
						result.setMessage("任务开始失败,请重试!");
						return result;
					}
					
				}else{
					result.setCode(ResponseCode.DEMAND_STATE_ERROR);
					result.setMessage("任务开始失败,请刷新页面再操作!");
					return result;
				}
				
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException(e);
			}
		}
	}
	
	
	//haokun added 2017/03/10 start 增加了任务匹配后，接单人可以拒绝任务
		public ResponseObject<Object> refuseDemand(String userId, String nickName, String demandId)
				throws ServiceException {// star refuse
			ResponseObject<Object> result = new ResponseObject<Object>();
			if (demandId==null||userId==null) {
				result.setCode(ResponseCode.PARAMETER_ERROR);
				result.setMessage("参数无效");
				return result;
			} else {
				try {
					Demand d = this.demandDao.getDemandById(demandId);
					if(d==null){
						result.setCode(ResponseCode.DEMAND_NOT_EXISTS);
						result.setMessage("任务不存在！");
						return result;
					}
					if(!d.getState().equals(Constant.DEMAND_STATE_SELECT)){
						if(d.getState().equals(Constant.DEMAND_STATE_CLOSE)){
							result.setMessage("发单人已关闭任务,无法拒绝！");
						}else{
							result.setMessage("任务已开始，无法拒绝！");
						}
						result.setCode(ResponseCode.DEMAND_STATE_ERROR);
						return result;
					}
					String date = DateUtil.date2String(new Date());
					List<ReceiveDemand> rds = d.getReceiveDemands();
					
					//在recieve_demand中找到匹配的接单人
					ReceiveDemand selectRD  = null;
					if(rds!=null){
						for(ReceiveDemand rd:rds){
							if(rd.getState().equals(Constant.RECEIVEDEMAND_STATE_CHECKED)&&rd.getUserId().equals(userId)){
								selectRD = rd;
							}
						}
					}
					
					
					if(selectRD!=null){
						if(selectRD.getAmount()!=null&&!selectRD.getAmount().equals("")){
							//含有报价
							double demandAmount = Double.valueOf(d.getAmount());
							double amount = Double.valueOf(selectRD.getAmount());
							if(amount > demandAmount){                //报价高于发单人价格--取消匹配，需要还给发单人金额
								AccountDetail detail = new AccountDetail(); 
								detail.setAmount(String.valueOf(amount-demandAmount));
								detail.setRealAmount(detail.getAmount());
								detail.setUserId(d.getUserId());
								detail.setState(Constant.ACCOUNT_DETAIL_STATE2);
								//detail.setName("冻结金额返还(取消匹配)");   //haokun deleted 2017/03/27
								detail.setName("返还任务保证金（取消匹配）");   //haokun edit 2017/03/27 改成“任务保证金”		
								detail.setCreateDate(date);
								detail.setCurrency("美元");
								detail.setModifyDate(date);
								detail.setType(Constant.ACCOUNT_DETAIL_TYPE20);
								detail.setRemark("拒绝"+selectRD.getUserNickName()+"做单号为"+d.getId()+"的任务，其报价高于任务金额，任务保证金返还：$"+detail.getAmount());   //haokun added 2017/03/27 改为任务保证金
								
								detail.setDemandId(demandId);
								detail.setThirdNo("");
								//发票号
								InvoiceNo in = this.invoiceNoDao.getByType(Constant.INVOICE_TYPE3);  //金额返还
								if(in!=null){
									if(this.invoiceNoDao.modifyStateById(in.getId(),date) > 0){
										detail.setInvoiceNo(in.getInvoiceNo());
									}
								}
								
								Account a = accountDao.getAccountByUserId(detail.getUserId());
								double usd= StringUtil.string2Double(a.getUsd());
								double newusd = usd + StringUtil.string2Double(detail.getAmount());   //金额返还，用余额增加 

								// 账户支付，修改账户余额
								if (this.accountDao.modifyUsdByUserId(a.getUserId(), String.valueOf(newusd), date)  > 0) {
									//减少冻结金额
									if(this.accountDao.reduceFreezeUsd(a.getUserId(), detail.getAmount(), date) > 0){
										// pass
									}else{
										throw new Exception();
									}
								} else {
									throw new Exception();
								}
								this.accountDetailDao.insertAccountDetail(detail);
							}else if(amount<demandAmount){    //报价低于发单人价格--
								AccountDetail detail = new AccountDetail(); 
								detail.setAmount(String.valueOf(demandAmount-amount));
								detail.setRealAmount(detail.getAmount());
								detail.setUserId(d.getUserId());
								detail.setState(Constant.ACCOUNT_DETAIL_STATE2);
								//detail.setName("任务金额冻结（取消匹配）");   haokun added 2017/03/27 delted 2017/3/27
								detail.setName("返还任务保证金(取消匹配)");  //haokun edit 2017/03/27 改成“任务保证金”	
								detail.setCreateDate(date);
								detail.setCurrency("美元");
								detail.setModifyDate(date);
								detail.setType(Constant.ACCOUNT_DETAIL_TYPE33);   //写入type33
								detail.setRemark("接单人"+selectRD.getUserNickName()+"拒绝做单号为"+d.getId()+"的任务，增加任务保证金：$"+detail.getAmount());  //haokun added 2017/03/27 改为任务保证金
								detail.setDemandId(demandId);
								detail.setThirdNo("");
								//发票号
								InvoiceNo in = this.invoiceNoDao.getByType(Constant.INVOICE_TYPE1);   //冻结金额发票
								if(in!=null){
									if(this.invoiceNoDao.modifyStateById(in.getId(),date) > 0){
										detail.setInvoiceNo(in.getInvoiceNo());
									}
								}
								
								Account a = accountDao.getAccountByUserId(detail.getUserId());
								double usd= StringUtil.string2Double(a.getUsd());
								double newusd = usd - StringUtil.string2Double(detail.getAmount()); 
								
								// 减少账户余额
								if (this.accountDao.modifyUsdByUserId(a.getUserId(), String.valueOf(newusd), date)  > 0) {
									//增加冻结金额
									if(this.accountDao.reduceFreezeUsd(a.getUserId(), detail.getAmount(), date) > 0){
										// pass
									}else{
										throw new Exception();
									}
								} else {
									throw new Exception();
								}
								this.accountDetailDao.insertAccountDetail(detail);
							}
						}
											
						//重新修改接人的接单状态为0 --表示未匹配
						int iResult = this.receiveDemandDao.modifyState(selectRD.getId(), Constant.RECEIVEDEMAND_STATE_UNCHECKED);
						//重新修改任务状态为1-- 表示有接单人，等待匹配
						int nResult = this.demandDao.modifyDemandState(demandId, d.getUserId(), Constant.DEMAND_STATE_RECEIVE, date);
						if(nResult>0){
							//还没给发单人发短信或邮箱提醒。
							//给发单人发送系统消息
							String content = "您好!"+nickName+"拒绝执行您发布'"+d.getTitle()+"'的任务，请重新匹配";
							MessageSystem ms = new MessageSystem();
							ms.setCreateDate(date);
							ms.setLevel(Constant.MESSAGESYSTEM_LEVEL_INFO);
							ms.setContent(content);
							//还没写配置
							ms.setLink("/dashboard/Task/pubDetail.jsp?id="+Base64Util.encode(demandId));
							ms.setModifyDate(date);
							ms.setTitle(content);
							ms.setUserId(d.getUserId());
							ms.setState(Constant.MESSAGESYSTEM_STATE_UNREAD);
							this.messageSystemService.saveMessage(ms);
							result.setCode(ResponseCode.SUCCESS_CODE);
							return result;
						}else{
							result.setCode(ResponseCode.DEMAND_STATE_ERROR);
							result.setMessage("拒绝任务失败,请重试!");
							return result;
						}
				    }else{
						result.setCode(ResponseCode.DEMAND_STATE_ERROR);
						result.setMessage("拒绝任务失败，请重试!");
						return result;
					}
				} catch (Exception e) {
					throw ExceptionUtil.handle2ServiceException(e);
				}
			}
			//return result;
		}
		//haokun added 2017/03/10 end  增加了任务匹配后，接单人可以拒绝任务
	
	public ResponseObject<Object> closeDemand(String userId,String demandId)
			throws ServiceException {
		ResponseObject<Object> result = new ResponseObject<Object>();
		try {
			//检查任务状态
			Demand demand = this.demandDao.getDemandById(demandId);
			if(demand==null){
				result.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
				result.setMessage("任务不存在，请刷新页面后再重试！");
				return result;
			}
			if(!demand.getUserId().equals(userId)){
				result.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
				result.setMessage("不是您发布的任务，您无法关闭，请刷新页面后再重试！");
				return result;
			}
			
			if(demand.getState().equals(Constant.DEMAND_STATE_CLOSE)){
				result.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
				result.setMessage("任务已关闭，无需重复操作！");
				return result;
			}else if(demand.getState().equals(Constant.DEMAND_STATE_START)
					||demand.getState().equals(Constant.DEMAND_STATE_FINISH)
					||demand.getState().equals(Constant.DEMAND_STATE_PAY)
					||demand.getState().equals(Constant.DEMAND_STATE_ARBITRATION)
					||demand.getState().equals(Constant.DEMAND_STATE_END)){
				result.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
				result.setMessage("任务已开始，无法关闭，请刷新页面后再重试！");
				return result;
			}
			
			String date = DateUtil.date2String(new Date());
			
			if(demand.getPayState().equals(Constant.DEMAND_PAY_STATE0)){ //未付款，不返还金额
				int nResult = this.demandDao.modifyDemandState(demandId, userId, Constant.DEMAND_STATE_CLOSE, date);
				if(nResult>0){
					result.setCode(ResponseCode.SUCCESS_CODE);
					return result;
				}else{
					throw new Exception();
				}
				
			}else{  //已经付款，需要返还金额
				double amount = 0;//Double.valueOf(demand.getAmount());
				
				List<ReceiveDemand> rds = demand.getReceiveDemands();
				
				//int selectLenght = 0;
				ReceiveDemand selectRD  = null;
				if(rds!=null){
					for(ReceiveDemand rd:rds){
						if(rd.getState().equals(Constant.RECEIVEDEMAND_STATE_CHECKED)){
							selectRD = rd;
							break;
						}
					}
				}
				//如果任务已经匹配，那么返还任务以接单人金额为准，否则以发布任务为准
				if(selectRD!=null){
					amount = Double.valueOf(selectRD.getAmount());
				}else{
					amount = Double.valueOf(demand.getAmount());
				}
				
				
				double daAmount = 0.00; //附加任务的金额
				//加上附加任务的金额
				List<DemandAttach> das = demand.getDa();
				if(das!=null){
					for(DemandAttach da:das){
						daAmount+=Double.valueOf(da.getAmount() != null ? da.getAmount().trim() : "");
					}
				}
				
				//修改任务状态,关闭任务
				int nResult = this.demandDao.modifyDemandState(demandId, userId, Constant.DEMAND_STATE_CLOSE, date);
				if(nResult>0){
					//开始返还任务金额
					/*String procedureRatio ="";
					try {
						Properties props = PropertiesReader.read("/anyonehelps.properties");
						procedureRatio = props.getProperty("demand.close.procedure.ratio");
					} catch (Exception e) {
						log.error("读取手续费配置错误!");
						throw ExceptionUtil.handle2ServiceException(e);
					}*/
					
					AccountDetail detail = new AccountDetail(); 
					detail.setAmount(String.valueOf(amount+daAmount));
					detail.setRealAmount(detail.getAmount());
					detail.setUserId(userId);
					detail.setState(Constant.ACCOUNT_DETAIL_STATE2);
					//detail.setName("冻结金额返还（任务关闭）"); //haokun deleted 2017/03/27
					detail.setName("返还任务保证金（任务关闭）"); //haokun modified 2017/	03/27 改为"任务保证金"detail.setCreateDate(date);
					detail.setCurrency("美元");
					detail.setModifyDate(date);
					detail.setType(Constant.ACCOUNT_DETAIL_TYPE21);
					detail.setRemark(demand.getId()+"号关闭任务获得返还金额");
					
					detail.setDemandId(demandId);
					detail.setThirdNo("");
					//发票号
					InvoiceNo in = this.invoiceNoDao.getByType(Constant.INVOICE_TYPE3);
					if(in!=null){
						if(this.invoiceNoDao.modifyStateById(in.getId(),date) > 0){
							detail.setInvoiceNo(in.getInvoiceNo());
						}
					}
					
					/*AccountDetail ad = new AccountDetail(); 
					ad.setAmount(String.valueOf((amount+daAmount)*Double.valueOf(procedureRatio)));
					ad.setRealAmount(ad.getAmount());
					ad.setUserId(userId);
					ad.setState(Constant.ACCOUNT_DETAIL_STATE2);
					ad.setName("关闭任务扣取手续费");
					ad.setCreateDate(date);
					ad.setCurrency("美元");
					ad.setModifyDate(date);
					ad.setType(Constant.ACCOUNT_DETAIL_TYPE2);
					ad.setRemark(demand.getId()+"号关闭任务扣取手续费");
					*/
					Account a = accountDao.getAccountByUserId(detail.getUserId());
					double usd= StringUtil.string2Double(a.getUsd());
					
					//double newusd = usd + StringUtil.string2Double(detail.getAmount())*(1-Double.valueOf(procedureRatio));
					double newusd = usd + StringUtil.string2Double(detail.getAmount());
					
					// 账户支付，修改账户余额
					if (this.accountDao.modifyUsdByUserId(a.getUserId(), String.valueOf(newusd), date)  > 0) {
						//减小任务冻结金额
						if(this.accountDao.reduceFreezeUsd(a.getUserId(), detail.getAmount(), date) > 0){
							// pass
						}else{
							throw new Exception();
						}
					} else {
						throw new Exception();
					}
					this.accountDetailDao.insertAccountDetail(detail);
					//this.accountDetailDao.insertAccountDetail(ad);
					
					result.setCode(ResponseCode.SUCCESS_CODE);
					return result;
				}else{
					throw new Exception();
				}
			}
			
			
				
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
		
	}
	
	public ResponseObject<Object> finishDemandByReceiver(String userId, String nickName, ReceiveDemand rd)
			throws ServiceException {
		ResponseObject<Object> result = new ResponseObject<Object>();
		try {
			String demandId = rd.getDemandId();
			Demand demand = this.demandDao.getDemandById(demandId);
			if(demand==null){
				result.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
				result.setMessage("任务不存在！");
				return result;
			}else if(demand.getState().equals(Constant.DEMAND_STATE_READY)
				||demand.getState().equals(Constant.DEMAND_STATE_RECEIVE)
				||demand.getState().equals(Constant.DEMAND_STATE_SELECT)){
				result.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
				result.setMessage("任务未开始！");
				return result;
			}else if(demand.getState().equals(Constant.DEMAND_STATE_CLOSE)){
				result.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
				result.setMessage("任务已关闭！");
				return result;
			}else if(demand.getState().equals(Constant.DEMAND_STATE_FINISH)){
				result.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
				result.setMessage("任务已完成！");
				return result;
			}else if(demand.getState().equals(Constant.DEMAND_STATE_PAY)){
				result.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
				result.setMessage("任务已支付，等待对方确认！");
				return result;
			}else if(demand.getState().equals(Constant.DEMAND_STATE_ARBITRATION)){
				result.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
				result.setMessage("对方提出仲裁，等待平台裁决！");
				return result;
			}else if(demand.getState().equals(Constant.DEMAND_STATE_END)){
				result.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
				result.setMessage("任务已经结束！");
				return result;
			}
			
			
			String date = DateUtil.date2String(new Date());
			List<ReceiveDemand> rds = demand.getReceiveDemands();
			
			//int selectLenght = 0;
			ReceiveDemand selectRD  = null;
			if(rds!=null){
				for(ReceiveDemand tempRD:rds){
					if(tempRD.getState().equals(Constant.RECEIVEDEMAND_STATE_CHECKED)&&tempRD.getUserId().equals(userId)){
						selectRD = tempRD;
					}
				}
			}
			
			if(selectRD!=null){
				int nResult = this.demandDao.modifyDemandState(demandId, demand.getUserId(), Constant.DEMAND_STATE_FINISH, date);
				if(nResult>0){
					int iResult = this.receiveDemandDao.modifyResult(rd.getResultDesc(), rd.getResultUrl1(), rd.getResultUrl2(), rd.getResultUrl3(), rd.getResultUrl4(), rd.getResultUrl5(),
							rd.getResultUrl1Name(), rd.getResultUrl2Name(), rd.getResultUrl3Name(), rd.getResultUrl4Name(), rd.getResultUrl5Name(), selectRD.getId(), demandId, date);
					if(iResult>0){ 
						//系统消息推送
						MessageSystem ms = new MessageSystem();
						ms.setCreateDate(date);
						ms.setLevel(Constant.MESSAGESYSTEM_LEVEL_INFO);
						ms.setContent("恭喜您!"+nickName+"已经完成您发布的'"+demand.getTitle()+"'任务并提交成果，点击立即查看");
						//还没写配置
						ms.setLink("/dashboard/Task/pubDetail.jsp?id="+Base64Util.encode(demandId));
						ms.setModifyDate(date);
						ms.setTitle("恭喜您!"+nickName+"已经完成您发布的'"+demand.getTitle()+"'任务并提交成果，点击立即查看");
						ms.setUserId(demand.getUserId());
						ms.setState(Constant.MESSAGESYSTEM_STATE_UNREAD);
						this.messageSystemService.saveMessage(ms);
						
						User user = this.userDao.getUserById(demand.getUserId());
						if(user != null){
							//邮箱提醒。
							if(user.getEmail()!=null&&!user.getEmail().equals("")&&user.getEmailState().equals(Constant.USER_EMAIL_STATE1)){
								String subject = "恭喜您!"+nickName+"已经完成您发布的'"+demand.getTitle()+"'任务并提交成果，请查看";
								String content = "恭喜您!"+nickName+"已经完成您发布的'"+demand.getTitle()+"'任务并提交成果，<a href='www.anyonehelps.com/task/detail.jsp?id="+Base64Util.encode(demand.getId());
								content +="<br><br>感谢您对AnyoneHelps的支持!<br>AnyoneHelps团队<br>www.anyonehelps.com<br>";  /*haokun add*/
								content +="<img src='https://www.anyonehelps.com/assets/pages/img/index/logo-bottom.png'/>";/*haokun add*/
								
								/*Properties prop = PropertiesReader.read(Constant.EMAIL_PROPERTIES_FILE);
								String subject = prop.getProperty("anyonehelps.invite.task.subject");
								subject = MessageFormat.format(subject, new Object[] { user.getNickName() , nickName });
								String content = prop.getProperty("anyonehelps.invite.task.content");
								content = MessageFormat.format(content, new Object[] { user.getNickName(), nickName, demand.getTitle(), demand.getAmount(), demand.getExpireDate(), url});
								*/
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
							//短信提醒。
						    //接单人完成人无提交成果，不需要发送短信   delete by haokun 2017/01/30
							/*
							if(user.getTelphone()!=null&&!user.getTelphone().equals("")){
								//SmsSendUtil.sendInviteMsg(user.getNickName(),demand.getTitle(), url, user.getAreaCode(), user.getTelphone(), demand.getAmount());
								String content = null;
								content = "恭喜您!"+nickName+"已经完成您发布的'"+demand.getTitle()+"'任务并提交成果，任务链接：www.anyonehelps.com/task/detail.jsp?id="+Base64Util.encode(demand.getId());
								
								SmsSend ss = new SmsSend();
								ss.setContent(content);
								ss.setFailCount(Constant.EMAILSEND_FAIL_COUNT0);
								ss.setState(Constant.EMAILSEND_STATE0);
								ss.setAreaCode(user.getAreaCode());
								ss.setTelphone(user.getTelphone());
								ss.setUserId(user.getId());
								ss.setCreateDate(date);
								ss.setModifyDate(date);
								this.smsSendDao.insert(ss);
							}
							*/
						}
						
						result.setCode(ResponseCode.SUCCESS_CODE);
						return result;
					}else{
						throw new Exception();
					}
				}else{
					result.setCode(ResponseCode.DEMAND_STATE_ERROR);
					result.setMessage("任务完成失败,请刷新页面再操作!");
					return result;
				}
			}else{
				result.setCode(ResponseCode.DEMAND_STATE_ERROR);
				result.setMessage("任务完成失败,请刷新页面再操作!");
				return result;
			}
				
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
	}
	
	public ResponseObject<Object> finishDemand(String userId, String nickName, String demandId)
			throws ServiceException {
		ResponseObject<Object> result = new ResponseObject<Object>();
		try {
			Demand demand = this.demandDao.getDemandById(demandId);
			if(demand==null){
				result.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
				result.setMessage("任务不存在！");
				return result;
			}
			if(!demand.getUserId().equals(userId)){
				result.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
				result.setMessage("不是您发布的任务，您无法结束，请刷新页面后再重试！");
				return result;
			}
			
			if(demand.getState().equals(Constant.DEMAND_STATE_READY)||
					demand.getState().equals(Constant.DEMAND_STATE_RECEIVE)){
				result.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
				result.setMessage("任务未开始！");
				return result;
			}else if(demand.getState().equals(Constant.DEMAND_STATE_CLOSE)){
				result.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
				result.setMessage("任务已关闭！");
				return result;
			}else if(demand.getState().equals(Constant.DEMAND_STATE_FINISH)){
				result.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
				result.setMessage("任务已结束！");
				return result;
			}else if(demand.getState().equals(Constant.DEMAND_STATE_PAY)){
				result.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
				result.setMessage("任务已支付，等待对方确认！");
				return result;
			}else if(demand.getState().equals(Constant.DEMAND_STATE_ARBITRATION)){
				result.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
				result.setMessage("对方提出仲裁，等待平台裁决！");
				return result;
			}else if(demand.getState().equals(Constant.DEMAND_STATE_END)){
				result.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
				result.setMessage("任务已经完全结束！");
				return result;
			}
			
			
			String date = DateUtil.date2String(new Date());
			int nResult = this.demandDao.modifyDemandState(demandId, userId, Constant.DEMAND_STATE_FINISH, date);
			if(nResult>0){
				
				//系统消息推送
				List<ReceiveDemand> rds = demand.getReceiveDemands();
				if(rds!=null){
					for(ReceiveDemand rd:rds){
						if(rd.getState().equals(Constant.RECEIVEDEMAND_STATE_CHECKED)){
							//还没给接单人发短信或邮箱提醒。
							MessageSystem ms = new MessageSystem();
							ms.setCreateDate(date);
							ms.setLevel(Constant.MESSAGESYSTEM_LEVEL_INFO);
							ms.setContent("您好！"+nickName+"终止了已经开始的任务:'"+demand+"'，点击立即查看，请等待对方付款");
							//还没写配置
							ms.setLink("/dashboard/Task/accDetail.jsp?id="+Base64Util.encode(demandId));
							ms.setModifyDate(date);
							ms.setTitle("您好！"+nickName+"终止了已经开始的任务，点击立即查看");
							ms.setUserId(rd.getUserId());
							ms.setState(Constant.MESSAGESYSTEM_STATE_UNREAD);
							this.messageSystemService.saveMessage(ms);
							break;
						}
					}
				}
				
				result.setCode(ResponseCode.SUCCESS_CODE);
				return result;
			}else{
				throw new Exception();
			}
				
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
	}
	
	@Override
	public ResponseObject<Object> payDemand(String userId, String nickName, ReceiveDemand rd) throws ServiceException {
		ResponseObject<Object> result = new ResponseObject<Object>();
		String demandId = rd.getDemandId();
		try {
			//检查任务状态
			Demand demand = this.demandDao.getDemandById(demandId);
			if(demand==null){
				result.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
				result.setMessage("任务不存在！");
				return result;
			}
			if(!demand.getUserId().equals(userId)){
				result.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
				result.setMessage("不是您发布的任务，您无法支付，请刷新页面后再重试！");
				return result;
			}
			
			if(!demand.getState().equals(Constant.DEMAND_STATE_FINISH)){
				result.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
				result.setMessage("任务不是支付状态，无法支付！");
				return result;
			}
			
			String date = DateUtil.date2String(new Date());
			List<ReceiveDemand> rds = demand.getReceiveDemands();
			String rdId  = null;   //中标接单id
			double demandAmount = 0; 
			
			if(rds!=null){
				for(ReceiveDemand tempRD:rds){
					if(tempRD.getState().equals(Constant.RECEIVEDEMAND_STATE_CHECKED)){
						rdId = tempRD.getId();
						rd.setUserId(tempRD.getUserId());
						demandAmount = StringUtil.string2Double(tempRD.getAmount());
						break;
					}
				}
			}else{
				result.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
				result.setMessage("接单人不存在！请联系客服");
				return result;
			}
			if(rdId==null){
				result.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
				result.setMessage("接单人不存在！请联系客服");
				return result;
			}
			int nResult = 0;
			//修改任务状态
			if(Constant.RECEIVEDEMAND_PAY_PERCENT100.equals(rd.getPayPercent())){
				//全额付款，任务结束
				nResult = this.demandDao.modifyDemandState(demandId, userId, Constant.DEMAND_STATE_END, date);
			}else{
				//部分付款
				nResult = this.demandDao.modifyDemandState(demandId, userId, Constant.DEMAND_STATE_PAY, date);
			}
			if(nResult>0){
				if(Constant.RECEIVEDEMAND_PAY_PERCENT100.equals(rd.getPayPercent())){
					//全额付款
					int n = this.receiveDemandDao.modifyPay(Constant.RECEIVEDEMAND_PAY_STATE1, rd.getPayPercent(), rd.getPayReason(),
							rd.getPayReasonUrl1(), rd.getPayReasonUrl2(), rd.getPayReasonUrl3(), rd.getPayReasonUrl4(), rd.getPayReasonUrl5(), 
							rd.getPayReasonUrl1Name(), rd.getPayReasonUrl2Name(), rd.getPayReasonUrl3Name(), rd.getPayReasonUrl4Name(), rd.getPayReasonUrl5Name(), 
							rdId, demandId, date);
					if(n>0){
						//double amount = Double.valueOf(demand.getAmount());
						double amount = demandAmount;
						double daAmount = 0.00; //附加任务总金额
						//加上附加任务的金额
						List<DemandAttach> das = demand.getDa();
						if(das!=null){
							for(DemandAttach da:das){
								if(Constant.DEMAND_ATTACH_STATE1.equals(da.getState())){
									daAmount+=Double.valueOf(da.getAmount() != null ? da.getAmount().trim() : "");
								}
							}
						}
						
						//手续费
						String procedureRatio = "0.13";
						try {
							Properties props = PropertiesReader.read("/anyonehelps.properties");
							procedureRatio = props.getProperty("demand.finish.procedure.ratio");
						} catch (Exception e) {
							log.error("用户("+userId+")全额付款任务("+rd.getDemandId()+")时,读取任务报酬手续费比例配置失败!");
							throw ExceptionUtil.handle2ServiceException("付款异常,请联系客服！",e);
						}
						
						String payAmount = String.valueOf((amount+daAmount));
						AccountDetail detail = new AccountDetail(); 
						detail.setAmount(payAmount);
						detail.setRealAmount(detail.getAmount());
						detail.setUserId(rd.getUserId());
						detail.setState(Constant.ACCOUNT_DETAIL_STATE2);
						//detail.setName("完成任务获取任务报酬");   //haokun modified 2017/03/09 修改名字
						detail.setName("任务完成报酬"); //haokun modified 2017/03/09 修改名字
						detail.setCreateDate(date);
						detail.setCurrency("美元");
						detail.setModifyDate(date);
						detail.setType(Constant.ACCOUNT_DETAIL_TYPE22);
						detail.setRemark("成功完成单号:"+rd.getDemandId()+"的任务，获得奖励");
						detail.setDemandId(demandId);
						detail.setThirdNo("");
						//发票号
						InvoiceNo in = this.invoiceNoDao.getByType(Constant.INVOICE_TYPE4);
						if(in!=null){
							if(this.invoiceNoDao.modifyStateById(in.getId(),date) > 0){
								detail.setInvoiceNo(in.getInvoiceNo());
							}
						}
						
						
						AccountDetail procedureDetail = new AccountDetail();
						procedureDetail.setAmount(String.valueOf((amount+daAmount)*Double.valueOf(procedureRatio)));
						procedureDetail.setRealAmount(procedureDetail.getAmount());
						procedureDetail.setUserId(rd.getUserId());
						procedureDetail.setState(Constant.ACCOUNT_DETAIL_STATE2);
						//procedureDetail.setName("完成任务扣取收款手续费");     //haokun modified 2017/03/09 修改名字
						procedureDetail.setName("收取任务报酬手续费");     //haokun modified 2017/03/09 修改名字
						procedureDetail.setCreateDate(date);
						procedureDetail.setCurrency("美元");
						procedureDetail.setModifyDate(date);
						procedureDetail.setType(Constant.ACCOUNT_DETAIL_TYPE38);
						procedureDetail.setRemark("完成编号"+rd.getDemandId()+"任务扣取手续费");
						procedureDetail.setDemandId(demandId);
						procedureDetail.setThirdNo("");
						//发票号
						InvoiceNo procedureInvoiceNo = this.invoiceNoDao.getByType(Constant.INVOICE_TYPE7);
						if(procedureInvoiceNo != null){
							if(this.invoiceNoDao.modifyStateById(procedureInvoiceNo.getId(),date) > 0){
								procedureDetail.setInvoiceNo(procedureInvoiceNo.getInvoiceNo());
							}
						}
						
						/*手续费返还 start*/ 
						User rdUser = this.userDao.getOnlyUserOpenInfoById(rd.getUserId());
						Charges charges = this.chargesDao.getOne();
						if(rdUser == null||charges == null){
							throw new Exception();
						}
						String chargesPercent = "1.00";
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
						DecimalFormat    chargesDf   = new DecimalFormat("######0.00"); 
						if(rdUser.getSignDate()!=null){
							Date start = df.parse(rdUser.getSignDate());
							if(getMonth(start, new Date())>3){
								//老用户
								chargesPercent = chargesDf.format(Double.valueOf(charges.getOldcustomerChargerate())/100);
							}else{
								//新用户
								chargesPercent = chargesDf.format(Double.valueOf(charges.getNewcustomerChargerate())/100);
							}
						}
						AccountDetail chargesDetail = new AccountDetail();
						if(!chargesPercent.equals("1.00")){
							chargesDetail.setAmount(String.valueOf((amount+daAmount)*Double.valueOf(procedureRatio)*(1-Double.valueOf(chargesPercent))));
							chargesDetail.setRealAmount(chargesDetail.getAmount());
							chargesDetail.setUserId(rd.getUserId());
							chargesDetail.setState(Constant.ACCOUNT_DETAIL_STATE2);
							//chargesDetail.setName("返还完成任务收款手续费");  //haokun modified 2017/03/09
							chargesDetail.setName("任务报酬手续费返还");    //haokun modified 2017/03/09
							chargesDetail.setCreateDate(date);
							chargesDetail.setCurrency("美元");
							chargesDetail.setModifyDate(date);
							chargesDetail.setType(Constant.ACCOUNT_DETAIL_TYPE26);
							chargesDetail.setRemark("返还编号"+rd.getDemandId()+"任务扣取手续费");
							chargesDetail.setDemandId(demandId);
							chargesDetail.setThirdNo("");
							//发票号
							InvoiceNo chargesInvoiceNo = this.invoiceNoDao.getByType(Constant.INVOICE_TYPE7);
							if(chargesInvoiceNo != null){
								if(this.invoiceNoDao.modifyStateById(chargesInvoiceNo.getId(),date) > 0){
									chargesDetail.setInvoiceNo(chargesInvoiceNo.getInvoiceNo());
								}
							}
						}
						/*手续费返还 end*/
						
						AccountDetail payDetail = new AccountDetail();
						payDetail.setAmount(payAmount);
						payDetail.setRealAmount(payDetail.getAmount());
						payDetail.setUserId(userId);
						payDetail.setState(Constant.ACCOUNT_DETAIL_STATE2);
						payDetail.setName("任务支付");
						payDetail.setCreateDate(date);
						payDetail.setCurrency("美元");
						payDetail.setModifyDate(date);
						payDetail.setType(Constant.ACCOUNT_DETAIL_TYPE41);
						payDetail.setRemark("完成编号"+rd.getDemandId()+"任务，支付接单人报酬");
						payDetail.setDemandId(demandId);
						payDetail.setThirdNo("");
						//发票号
						InvoiceNo payInvoiceNo = this.invoiceNoDao.getByType(Constant.INVOICE_TYPE2);
						if(payInvoiceNo != null){
							if(this.invoiceNoDao.modifyStateById(payInvoiceNo.getId(),date) > 0){
								payDetail.setInvoiceNo(payInvoiceNo.getInvoiceNo());
							}
						}
						//接单人增加金额
						Account a = accountDao.getAccountByUserId(detail.getUserId());
						double usd= StringUtil.string2Double(a.getUsd());
						double newusd = usd + StringUtil.string2Double(detail.getAmount())*(1-Double.valueOf(procedureRatio)*Double.valueOf(chargesPercent));
						if (this.accountDao.modifyUsdByUserId(a.getUserId(), String.valueOf(newusd), date)  > 0) {
							// pass
						} else {
							throw new Exception();
						}
						
						//减小发单人冻结金额
						if(this.accountDao.reduceFreezeUsd(demand.getUserId(), payDetail.getAmount(), date) > 0){
							// pass
						}else{
							throw new Exception();
						}
						
						
						this.accountDetailDao.insertAccountDetail(detail);
						this.accountDetailDao.insertAccountDetail(procedureDetail);
						this.accountDetailDao.insertAccountDetail(payDetail);
						if(!chargesPercent.equals("1.00")){
							this.accountDetailDao.insertAccountDetail(chargesDetail);
						}
						//写发单人经验值
						if(this.userDao.modifyGradeById(demand.getUserId(),payDetail.getAmount())>0){
							// pass
						}else{
							throw new Exception();
						}
						//写接单人经验值
						if(this.userDao.modifyGradeById(detail.getUserId(),payDetail.getAmount())>0){
							// pass
						}else{
							throw new Exception();
						}
						
						//接单人系统消息推送
						//还没给接单人发短信或邮箱提醒。 --- haokun增加邮件提醒 2017/01/30
						//邮箱提醒 add by haokun 2017/01/26 用户更改邮箱，增加邮件提醒
						User user = this.userDao.getUserById(rd.getUserId());
						if(user.getEmail()!=null&&!user.getEmail().equals("")&&user.getEmailState().equals(Constant.USER_EMAIL_STATE1)){
							String subject = "[AnyoneHelps] 收款通知";
							String content = "尊敬的用户"+user.getNickName()+",<br>";
							content += ""+nickName+"已对您完成的任务'"+demand.getTitle()+"'进行了全额支付,<a href='www.anyonehelps.com/dashboard/Task/accDetail.jsp?id=="+Base64Util.encode(demandId)+"'>点击立即查看</a><br>";
							content +="<br><br>感谢您对AnyoneHelps的支持!<br>AnyoneHelps团队<br>www.anyonehelps.com<br>";  /*haokun add*/
							content +="<img src='https://www.anyonehelps.com/assets/pages/img/index/logo-bottom.png'/>";/*haokun add*/
							
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
						//系统消息
						MessageSystem ms = new MessageSystem();
						ms.setCreateDate(date);
						ms.setLevel(Constant.MESSAGESYSTEM_LEVEL_INFO);
						ms.setContent("恭喜您!"+nickName+"已对您完成的任务'"+demand.getTitle()+"'进行了全额支付");
						//还没写配置
						ms.setLink("/dashboard/Task/accDetail.jsp?id="+Base64Util.encode(demandId));
						ms.setModifyDate(date);
						ms.setTitle("恭喜您!"+nickName+"对您完成的任务进行了全额付款，点击立即查看");
						ms.setUserId(rd.getUserId());
						ms.setState(Constant.MESSAGESYSTEM_STATE_UNREAD);
						this.messageSystemService.saveMessage(ms);
						
						//开始奖励分配
						double consumeAmount = amount + daAmount;
						//父级推荐人
						User pUser = this.userDao.getUserById(demand.getUserId());
						if(pUser!=null&&consumeAmount>0){  
							if(!pUser.getRecommender().equals("-1")
									&&pUser.getRecommender()!=null
									&&!pUser.getRecommender().equals("")){
								
								AccountDetail pdetail = new AccountDetail(); 
								pdetail.setAmount(String.valueOf(consumeAmount*bonusPointParentlevel1));
								pdetail.setRealAmount(pdetail.getAmount());
								pdetail.setUserId(pUser.getRecommender());
								pdetail.setState(Constant.ACCOUNT_DETAIL_STATE2);
								pdetail.setName("任务奖励");
								pdetail.setCreateDate(date);
								pdetail.setCurrency("美元");
								pdetail.setModifyDate(date);
								pdetail.setType(Constant.ACCOUNT_DETAIL_TYPE25);
								pdetail.setRemark("");
								pdetail.setThirdNo("");
								pdetail.setDemandId(demandId);
								InvoiceNo pin = this.invoiceNoDao.getByType(Constant.INVOICE_TYPE4);
								if(pin!=null){
									if(this.invoiceNoDao.modifyStateById(pin.getId(),date) > 0){
										pdetail.setInvoiceNo(pin.getInvoiceNo());
									}
								}
								
								Account pAccount = accountDao.getAccountByUserId(pdetail.getUserId());
								double pUsd= StringUtil.string2Double(pAccount.getUsd());
								double pNewUsd = pUsd + StringUtil.string2Double(pdetail.getAmount());
								// 账户支付，修改账户余额
								if (this.accountDao.modifyUsdByUserId(pAccount.getUserId(), String.valueOf(pNewUsd), date) > 0) {
									int pResult  = this.accountDetailDao.insertAccountDetail(pdetail);
									if(pResult > 0){
										BonusPoint pBonusPoint = new BonusPoint();
										pBonusPoint.setConsumerId(demand.getUserId());
										pBonusPoint.setCreateDate(date);
										pBonusPoint.setDemandId(demandId);
										pBonusPoint.setPoint(String.valueOf(consumeAmount*bonusPointParentlevel1));
										pBonusPoint.setSublevel(Constant.BONUSPOINT_SUBLEVEL1);
										pBonusPoint.setUserId(pUser.getRecommender());
										//pBonusPoint.setRemark(Constant.BONUSPOINT_SUBLEVEL1+"级邀请人任务号"+demandId+"完成，赠送积分");
										pBonusPoint.setRemark("邀请人任务号"+demandId+"完成，奖励金额");
										this.bonusPointDao.insertBonusPoint(pBonusPoint);
										MessageSystem pms = new MessageSystem();
										pms.setLevel(Constant.MESSAGESYSTEM_LEVEL_INFO);
										pms.setContent(pBonusPoint.getRemark());
										pms.setCreateDate(date);
										pms.setModifyDate(date);
										pms.setUserId(pBonusPoint.getUserId());
										pms.setTitle("您获得了来自任务号"+demandId+"完成的奖励金额！点击立即查看");
										pms.setLink("/dashboard/Reward/bonus_point.jsp");
										pms.setState(Constant.MESSAGESYSTEM_STATE_UNREAD);
										this.messageSystemService.saveMessage(pms);
									}
								}else{
									throw new Exception();
								}
								//this.accountDao.modifyBonusPoint(pUser.getRecommender(), String.valueOf(consumeAmount*bonusPointParentlevel1), date);
								
								
								/*//父级的父级推荐人
								User ppUser = this.userDao.getUserById(pUser.getRecommender());
								if(ppUser!=null&&!ppUser.getRecommender().equals("-1")
										&&ppUser.getRecommender()!=null
										&&!ppUser.getRecommender().equals("")){
									
									
									this.accountDao.modifyBonusPoint(ppUser.getRecommender(), String.valueOf(consumeAmount*bonusPointParentlevel2), date);
									BonusPoint ppBonusPoint = new BonusPoint();
									ppBonusPoint.setConsumerId(demand.getUserId());
									ppBonusPoint.setCreateDate(date);
									ppBonusPoint.setDemandId(demandId);
									ppBonusPoint.setPoint(String.valueOf(consumeAmount*bonusPointParentlevel2));
									ppBonusPoint.setSublevel(Constant.BONUSPOINT_SUBLEVEL2);
									ppBonusPoint.setUserId(ppUser.getRecommender());
									ppBonusPoint.setRemark(Constant.BONUSPOINT_SUBLEVEL2+"级邀请人任务号"+demandId+"完成，赠送积分");
									this.bonusPointDao.insertBonusPoint(ppBonusPoint);
									MessageSystem ppms = new MessageSystem();
									ppms.setLevel(Constant.MESSAGESYSTEM_LEVEL_INFO);
									ppms.setContent(ppBonusPoint.getRemark());
									ppms.setCreateDate(date);
									ppms.setModifyDate(date);
									ppms.setUserId(ppBonusPoint.getUserId());
									ppms.setTitle("您获得了来自任务号"+demandId+"完成的积分！点击立即查看");
									ppms.setLink("/dashboard/Reward/bonus_point.jsp");
									ppms.setState(Constant.MESSAGESYSTEM_STATE_UNREAD);
									this.messageSystemService.saveMessage(ppms);
									//父级的父级的父级推荐人
									User pppUser = this.userDao.getUserById(ppUser.getRecommender());
									if(pppUser!=null&&!pppUser.getRecommender().equals("-1")
											&&pppUser.getRecommender()!=null
											&&!pppUser.getRecommender().equals("")){
										
										this.accountDao.modifyBonusPoint(pppUser.getRecommender(), String.valueOf(consumeAmount*bonusPointParentlevel3), date);
										BonusPoint pppBonusPoint = new BonusPoint();
										pppBonusPoint.setConsumerId(demand.getUserId());
										pppBonusPoint.setCreateDate(date);
										pppBonusPoint.setDemandId(demandId);
										pppBonusPoint.setPoint(String.valueOf(consumeAmount*bonusPointParentlevel3));
										pppBonusPoint.setSublevel(Constant.BONUSPOINT_SUBLEVEL3);
										pppBonusPoint.setUserId(pppUser.getRecommender());
										pppBonusPoint.setRemark(Constant.BONUSPOINT_SUBLEVEL3+"级邀请人任务号"+demandId+"完成，赠送积分");
										this.bonusPointDao.insertBonusPoint(pppBonusPoint);
										
										MessageSystem pppms = new MessageSystem();
										pppms.setLevel(Constant.MESSAGESYSTEM_LEVEL_INFO);
										pppms.setContent(pppBonusPoint.getRemark());
										pppms.setCreateDate(date);
										pppms.setModifyDate(date);
										pppms.setUserId(pppBonusPoint.getUserId());
										pppms.setTitle("您获得了来自任务号"+demandId+"完成的积分！点击立即查看");
										pppms.setLink("/dashboard/Reward/bonus_point.jsp");
										pppms.setState(Constant.MESSAGESYSTEM_STATE_UNREAD);
										this.messageSystemService.saveMessage(pppms);
									}
								}*/
							}
						}
						result.setCode(ResponseCode.SUCCESS_CODE);
						return result;
					}else{
						throw new Exception();
					}
				}else{
					//部分付款
					int n = this.receiveDemandDao.modifyPay(Constant.RECEIVEDEMAND_PAY_STATE2, rd.getPayPercent(), rd.getPayReason(),
							rd.getPayReasonUrl1(), rd.getPayReasonUrl2(), rd.getPayReasonUrl3(), rd.getPayReasonUrl4(), rd.getPayReasonUrl5(), 
							rd.getPayReasonUrl1Name(), rd.getPayReasonUrl2Name(), rd.getPayReasonUrl3Name(), rd.getPayReasonUrl4Name(), rd.getPayReasonUrl5Name(), 
							rdId, demandId, date);
					if(n>0){
						//接单人系统消息推送
						//还没给接单人发短信或邮箱提醒。 --- haokun增加邮件提醒 2017/01/30
						//邮箱提醒 add by haokun 2017/01/26 用户更改邮箱，增加邮件提醒
						User user = this.userDao.getUserById(rd.getUserId());
						if(user.getEmail()!=null&&!user.getEmail().equals("")&&user.getEmailState().equals(Constant.USER_EMAIL_STATE1)){
							String subject = "[AnyoneHelps] 收款通知";
							String content = "尊敬的用户"+user.getNickName()+",<br>";
							content += ""+nickName+"向您支付了"+rd.getPayPercent()+"%的任务报酬("+demand.getTitle()+")，请到任务管理--->接受的任务找到该任务进行确认或者提出您的任务报酬比例进行任务仲裁！<br>";
							content +="<br><br>感谢您对AnyoneHelps的支持!<br>AnyoneHelps团队<br>www.anyonehelps.com<br>";  /*haokun add*/
							content +="<img src='https://www.anyonehelps.com/assets/pages/img/index/logo-bottom.png'/>";/*haokun add*/
							
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
						//系统消息
						MessageSystem ms = new MessageSystem();
						ms.setCreateDate(date);
						ms.setLevel(Constant.MESSAGESYSTEM_LEVEL_INFO);
						ms.setContent(""+nickName+"向您支付了"+rd.getPayPercent()+"%的任务报酬("+demand.getTitle()+")，请到任务管理--->接受的任务找到该任务进行确认或者提出您的任务报酬比例进行任务仲裁！");
						//还没写配置
						ms.setLink("/dashboard/Task/accDetail.jsp?id="+Base64Util.encode(demandId));
						ms.setModifyDate(date);
						ms.setTitle("您好!"+nickName+"对您完成的任务"+demandId+"进行了部分支付并给出部分支付理由，点击立即查看 ");
						ms.setUserId(rd.getUserId());
						ms.setState(Constant.MESSAGESYSTEM_STATE_UNREAD);
						this.messageSystemService.saveMessage(ms);
						
						result.setCode(ResponseCode.SUCCESS_CODE);
						return result;
					}else{
						throw new Exception();
					}
				}
				
				
			}else{
				throw new Exception();
			}
				
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
	}
	
	@Override
	public ResponseObject<Object> agreePay(String userId, String nickName, String demandId)
			throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>();
		if(userId==null||demandId==null||!DemandUtil.validateId(demandId)){
			return new ResponseObject<Object>(ResponseCode.PARAMETER_ERROR,"参数无效");
		}
		String date = DateUtil.date2String(new Date());
		try {
			Demand demand = this.demandDao.getDemandById(demandId);
			if(demand==null){
				return new ResponseObject<Object>( ResponseCode.DEMAND_NOT_EXISTS, "任务不存在！");
			}
			if(!Constant.DEMAND_STATE_PAY.equals(demand.getState())){
				return new ResponseObject<Object>( ResponseCode.DEMAND_MODIFY_STATE_ERROR, "任务不在收款状态！");
			}
			
			List<ReceiveDemand> rds = demand.getReceiveDemands();
			ReceiveDemand rd  = null;
			if(rds!=null){
				for(ReceiveDemand tempRD:rds){
					if(Constant.RECEIVEDEMAND_STATE_CHECKED.equals(tempRD.getState())&&
							userId.equals(tempRD.getUserId())&&
							Constant.RECEIVEDEMAND_PAY_STATE2.equals(tempRD.getPayState())){
						rd = tempRD;
						break;
					}
				}
			}else{
				responseObj.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
				responseObj.setMessage("你不是任务的中标人，无法收款！");
				return responseObj;
			}
			
			if(rd==null){
				responseObj.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
				responseObj.setMessage("任务不在收款状态!请联系客服");
				return responseObj;
			}
			int nResult = this.demandDao.modifyDemandState(demandId, demand.getUserId(), Constant.DEMAND_STATE_END, date);
			
			if(nResult>0){
				int result = this.receiveDemandDao.modifyPayState(rd.getId(), Constant.RECEIVEDEMAND_PAY_STATE3, date);
				if(result>0){
					//金额分配
					double amount = Double.valueOf(rd.getAmount());
					double daAmount = 0.00; //附加任务的金额
					//加上附加任务的金额
					List<DemandAttach> das = demand.getDa();
					if(das!=null){
						for(DemandAttach da:das){
							daAmount+=Double.valueOf(da.getAmount() != null ? da.getAmount().trim() : "");
						}
					}
					double percent = Double.valueOf(rd.getPayPercent() != null ? rd.getPayPercent().trim() : "1");
					if(Constant.RECEIVEDEMAND_PAY_PERCENT100.equals(rd.getPayPercent())){
						//全额付款
						
					}else{
						//部分付款
						
						String returnAmount = String.valueOf(new BigDecimal((amount+daAmount)*(1-percent/100)).setScale(2, RoundingMode.DOWN).doubleValue()); //返款金额
						//返回部分任务款给发单人
						if((amount+daAmount)*(1-percent/100)>0){
							AccountDetail returnDetail = new AccountDetail(); 
							returnDetail.setAmount(returnAmount);
							returnDetail.setRealAmount(returnDetail.getAmount());
							returnDetail.setUserId(demand.getUserId());
							returnDetail.setState(Constant.ACCOUNT_DETAIL_STATE2);
							//returnDetail.setName("任务部分付款，返还金额");   //haokun modifed 2017/03/09
							//returnDetail.setName("冻结金额返还（部分支付）");   //haokun deleted 2017/03/27
							returnDetail.setName("返还任务保证金（部分支付）");  //haokun modified 2017/03/27  改为“任务保证金”
							returnDetail.setCreateDate(date);
							returnDetail.setCurrency("美元");
							returnDetail.setModifyDate(date);
							returnDetail.setType(Constant.ACCOUNT_DETAIL_TYPE23);
							returnDetail.setRemark("接单人同意任务单号:"+demand.getId()+"的付款，返还部分任务金额");
							returnDetail.setDemandId(demandId);
							returnDetail.setThirdNo("");
							//发票号
							InvoiceNo in = this.invoiceNoDao.getByType(Constant.INVOICE_TYPE3);
							if(in != null){
								if(this.invoiceNoDao.modifyStateById(in.getId(),date) > 0){
									returnDetail.setInvoiceNo(in.getInvoiceNo());
								}
							}
							Account returnA = accountDao.getAccountByUserId(returnDetail.getUserId());
							double returnUsd= StringUtil.string2Double(returnA.getUsd());
							
							double returnNewusd = returnUsd + StringUtil.string2Double(returnDetail.getAmount());
						
							if (this.accountDao.modifyUsdByUserId(returnA.getUserId(), String.valueOf(returnNewusd), date)  > 0) {
								// pass
							} else {
								throw new Exception();
							}
							this.accountDetailDao.insertAccountDetail(returnDetail);
						}
					}
					//手续费
					String procedureRatio = "0.13";
					try {
						Properties props = PropertiesReader.read("/anyonehelps.properties");
						procedureRatio = props.getProperty("demand.finish.procedure.ratio");
					} catch (Exception e) {
						log.error("接单人同意任务("+rd.getDemandId()+")付款,读取任务报酬手续费比例配置失败!");
						throw ExceptionUtil.handle2ServiceException("同意异常,请联系客服！",e);
					}
					
					String payAmount = String.valueOf((amount+daAmount)*(percent/100));
					
					AccountDetail detail = new AccountDetail(); 
					detail.setAmount(payAmount);
					detail.setRealAmount(detail.getAmount());
					detail.setUserId(rd.getUserId());
					detail.setState(Constant.ACCOUNT_DETAIL_STATE2);
					//detail.setName("完成任务获取任务报酬");   // haokun modified 2017/03/09
					detail.setName("任务完成报酬");    //haokun modified 2017/03/09
					detail.setCreateDate(date);
					detail.setCurrency("美元");
					detail.setModifyDate(date);
					detail.setType(Constant.ACCOUNT_DETAIL_TYPE22);
					detail.setRemark("成功完成单号:"+rd.getDemandId()+"的任务，获得奖励");
					detail.setDemandId(demandId);
					detail.setThirdNo("");
					//发票号
					InvoiceNo in = this.invoiceNoDao.getByType(Constant.INVOICE_TYPE4);
					if(in != null){
						if(this.invoiceNoDao.modifyStateById(in.getId(),date) > 0){
							detail.setInvoiceNo(in.getInvoiceNo());
						}
					}
					AccountDetail procedureDetail = new AccountDetail();
					//procedureDetail.setAmount(String.valueOf(new BigDecimal((amount+daAmount)*(percent/100)*Double.valueOf(procedureRatio)).setScale(2, RoundingMode.DOWN).doubleValue()));  //haokun modified 2017/03/27 该处手续费金额计算不对，差0.01
					procedureDetail.setAmount(String.valueOf((amount+daAmount)*(percent/100)*Double.valueOf(procedureRatio)));  //haokun added 2017/03/27 重新
					procedureDetail.setRealAmount(procedureDetail.getAmount());
					procedureDetail.setUserId(rd.getUserId());
					procedureDetail.setState(Constant.ACCOUNT_DETAIL_STATE2);
					//procedureDetail.setName("完成任务扣取收款手续费");  haokun modified 2017/03/09
					procedureDetail.setName("收取任务报酬手续费");  //haokun modified 2017/03/09
					procedureDetail.setCreateDate(date);
					procedureDetail.setCurrency("美元");
					procedureDetail.setModifyDate(date);
					procedureDetail.setType(Constant.ACCOUNT_DETAIL_TYPE38);
					procedureDetail.setRemark("完成编号"+rd.getDemandId()+"任务扣取手续费");
					procedureDetail.setDemandId(demandId);
					procedureDetail.setThirdNo("");
					//发票号
					InvoiceNo procedureInvoiceNo = this.invoiceNoDao.getByType(Constant.INVOICE_TYPE7);
					if(procedureInvoiceNo != null){
						if(this.invoiceNoDao.modifyStateById(procedureInvoiceNo.getId(),date) > 0){
							procedureDetail.setInvoiceNo(procedureInvoiceNo.getInvoiceNo());
						}
					}
					
					/*手续费返还 start*/
					User rdUser = this.userDao.getOnlyUserOpenInfoById(rd.getUserId());
					Charges charges = this.chargesDao.getOne();
					if(rdUser == null||charges == null){
						throw new Exception();
					}
					String chargesPercent = "1.00";
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
					DecimalFormat    chargesDf   = new DecimalFormat("######0.00");   
					if(rdUser.getSignDate()!=null){
						Date start = df.parse(rdUser.getSignDate());
						if(getMonth(start, new Date())>3){
							//老用户
							chargesPercent = chargesDf.format(Double.valueOf(charges.getOldcustomerChargerate())/100);
						}else{
							//新用户
							chargesPercent = chargesDf.format(Double.valueOf(charges.getNewcustomerChargerate())/100);
						}
					}
					AccountDetail chargesDetail = new AccountDetail();
					if(!chargesPercent.equals("1.00")){
						chargesDetail.setAmount(String.valueOf((amount+daAmount)*(percent/100)*Double.valueOf(procedureRatio)*(1-Double.valueOf(chargesPercent)))); //haokun added 2017/03/07 增加了*(percent/100)
						chargesDetail.setRealAmount(chargesDetail.getAmount());
						chargesDetail.setUserId(rd.getUserId());
						chargesDetail.setState(Constant.ACCOUNT_DETAIL_STATE2);
						chargesDetail.setName("返还完成任务收款手续费");
						chargesDetail.setCreateDate(date);
						chargesDetail.setCurrency("美元");
						chargesDetail.setModifyDate(date);
						chargesDetail.setType(Constant.ACCOUNT_DETAIL_TYPE26);
						chargesDetail.setRemark("返还编号"+rd.getDemandId()+"任务扣取手续费");
						chargesDetail.setDemandId(demandId);
						chargesDetail.setThirdNo("");
						//发票号
						InvoiceNo chargesInvoiceNo = this.invoiceNoDao.getByType(Constant.INVOICE_TYPE7);
						if(chargesInvoiceNo != null){
							if(this.invoiceNoDao.modifyStateById(chargesInvoiceNo.getId(),date) > 0){
								chargesDetail.setInvoiceNo(chargesInvoiceNo.getInvoiceNo());
							}
						}
					}
					/*手续费返还 end*/
					
					AccountDetail payDetail = new AccountDetail();
					payDetail.setAmount(payAmount);
					payDetail.setRealAmount(payDetail.getAmount());
					payDetail.setUserId(demand.getUserId());
					payDetail.setState(Constant.ACCOUNT_DETAIL_STATE2);
					payDetail.setName("任务支付");
					payDetail.setCreateDate(date);
					payDetail.setCurrency("美元");
					payDetail.setModifyDate(date);
					payDetail.setType(Constant.ACCOUNT_DETAIL_TYPE41);
					payDetail.setRemark("完成编号"+rd.getDemandId()+"任务，支付接单人报酬");
					payDetail.setDemandId(demandId);
					payDetail.setThirdNo("");
					//发票号
					InvoiceNo payInvoiceNo = this.invoiceNoDao.getByType(Constant.INVOICE_TYPE2);
					if(payInvoiceNo != null){
						if(this.invoiceNoDao.modifyStateById(payInvoiceNo.getId(),date) > 0){
							payDetail.setInvoiceNo(payInvoiceNo.getInvoiceNo());
						}
					}
					Account a = accountDao.getAccountByUserId(detail.getUserId());
					double usd= StringUtil.string2Double(a.getUsd());
					
					double newusd = usd + StringUtil.string2Double(detail.getAmount())*(1-Double.valueOf(procedureRatio)*Double.valueOf(chargesPercent));
				
					if (this.accountDao.modifyUsdByUserId(a.getUserId(), String.valueOf(newusd), date) > 0) {
						// pass
					} else {
						throw new Exception();
					}
					this.accountDetailDao.insertAccountDetail(detail);
					this.accountDetailDao.insertAccountDetail(procedureDetail);
					this.accountDetailDao.insertAccountDetail(payDetail);
					
					/*手续费返还 start*/
					if(!chargesPercent.equals("1.00")){
						this.accountDetailDao.insertAccountDetail(chargesDetail);
					}
					/*手续费返还 end*/
					
					//发单人减小冻结金额
					if(this.accountDao.reduceFreezeUsd(demand.getUserId(), String.valueOf(new BigDecimal(amount+daAmount)), date) > 0){
						// pass
					}else{
						throw new Exception();
					}
					
					//写发单经验值
					if(this.userDao.modifyGradeById(demand.getUserId(),payDetail.getAmount())>0){
						// pass
					}else{
						throw new Exception();
					}
					
					//写接单人经验值
					if(this.userDao.modifyGradeById(detail.getUserId(),payDetail.getAmount())>0){
						// pass
					}else{
						throw new Exception();
					}
					
					//还没给接单人发短信或邮箱提醒。 - haokun 增加邮件推送 2017
					//add by haokun 2017/01/26 ，增加邮件提醒
					User user = this.userDao.getUserById(demand.getUserId());
					if(user.getEmail()!=null&&!user.getEmail().equals("")&&user.getEmailState().equals(Constant.USER_EMAIL_STATE1)){
						String subject = "[AnyoneHelps] 付款确认";
						String content ="尊敬的用户"+user.getNickName()+",<br>";
						content +=nickName+"同意您在"+demand.getTitle()+"的任务中提出的任务报酬，任务已经全部完成结束，请对任务该任务进行评价。<a href='www.anyonehelps.com/dashboard/Task/pubDetail.jsp?id="+Base64Util.encode(demandId)+"'>点击立即查看</a><br>"; 
						content +="<br><br>感谢您对AnyoneHelps的支持!<br>AnyoneHelps团队<br>www.anyonehelps.com<br>";  /*haokun add*/
						content +="<img src='https://www.anyonehelps.com/assets/pages/img/index/logo-bottom.png'/>";/*haokun add*/

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
					//发单人系统消息推送
					MessageSystem ms = new MessageSystem();
					ms.setCreateDate(date);
					ms.setLevel(Constant.MESSAGESYSTEM_LEVEL_INFO);
					ms.setContent("您好！"+nickName+"同意您在"+demand.getTitle()+"的任务中提出的任务报酬，任务已经全部完成结束，请对任务该任务进行评价");
					//还没写配置
					ms.setLink("/dashboard/Task/pubDetail.jsp?id="+Base64Util.encode(demandId));
					ms.setModifyDate(date);
					ms.setTitle("您好！"+nickName+"同意了提出的任务单号"+demandId+"报酬支付");
					ms.setUserId(demand.getUserId());
					ms.setState(Constant.MESSAGESYSTEM_STATE_UNREAD);
					this.messageSystemService.saveMessage(ms);
					
					//开始奖励分配
					double consumeAmount = (amount + daAmount)*(percent/100);
					//父级推荐人
					User pUser = this.userDao.getUserById(demand.getUserId());
					if(pUser!=null&&consumeAmount>0){  
						if(pUser.getRecommender()!=null
								&&!pUser.getRecommender().equals("-1")
								&&!pUser.getRecommender().equals("")){
							
							AccountDetail pdetail = new AccountDetail(); 
							pdetail.setAmount(String.valueOf(consumeAmount*bonusPointParentlevel1));
							pdetail.setRealAmount(pdetail.getAmount());
							pdetail.setUserId(pUser.getRecommender());
							pdetail.setState(Constant.ACCOUNT_DETAIL_STATE2);
							pdetail.setName("任务奖励");
							pdetail.setCreateDate(date);
							pdetail.setCurrency("美元");
							pdetail.setModifyDate(date);
							pdetail.setType(Constant.ACCOUNT_DETAIL_TYPE25);
							pdetail.setRemark("");
							pdetail.setThirdNo("");
							pdetail.setDemandId(demandId);
							
							InvoiceNo pin = this.invoiceNoDao.getByType(Constant.INVOICE_TYPE4);
							if(pin!=null){
								if(this.invoiceNoDao.modifyStateById(pin.getId(),date) > 0){
									pdetail.setInvoiceNo(pin.getInvoiceNo());
								}
							}
							
							Account pAccount = accountDao.getAccountByUserId(detail.getUserId());
							double pUsd= StringUtil.string2Double(pAccount.getUsd());
							double pNewUsd = pUsd + StringUtil.string2Double(detail.getAmount());
							
							// 账户支付，修改账户余额
							if (this.accountDao.modifyUsdByUserId(pAccount.getUserId(), String.valueOf(pNewUsd), date) > 0) {
								int pResult  = this.accountDetailDao.insertAccountDetail(pdetail);
								if(pResult > 0){
									BonusPoint pBonusPoint = new BonusPoint();
									pBonusPoint.setConsumerId(demand.getUserId());
									pBonusPoint.setCreateDate(date);
									pBonusPoint.setDemandId(demandId);
									pBonusPoint.setPoint(String.valueOf(consumeAmount*bonusPointParentlevel1));
									pBonusPoint.setSublevel(Constant.BONUSPOINT_SUBLEVEL1);
									pBonusPoint.setUserId(pUser.getRecommender());
									//pBonusPoint.setRemark(Constant.BONUSPOINT_SUBLEVEL1+"级邀请人任务号"+demandId+"完成，赠送积分");
									pBonusPoint.setRemark("邀请人任务'"+demand.getTitle()+"'完成，奖励金额");
									this.bonusPointDao.insertBonusPoint(pBonusPoint);
									MessageSystem pms = new MessageSystem();
									pms.setLevel(Constant.MESSAGESYSTEM_LEVEL_INFO);
									pms.setContent(pBonusPoint.getRemark());
									pms.setCreateDate(date);
									pms.setModifyDate(date);
									pms.setUserId(pBonusPoint.getUserId());
									pms.setTitle("您获得了来自任务单号"+demandId+"完成的奖励金额！点击立即查看");
									pms.setLink("/dashboard/Reward/bonus_point.jsp");
									pms.setState(Constant.MESSAGESYSTEM_STATE_UNREAD);
									this.messageSystemService.saveMessage(pms);
								}
							}else{
								throw new Exception();
							}
							
							//this.accountDao.modifyBonusPoint(pUser.getRecommender(), String.valueOf(consumeAmount*bonusPointParentlevel1), date);
							
							
							/*//父级的父级推荐人
							User ppUser = this.userDao.getUserById(pUser.getRecommender());
							if(ppUser!=null&&ppUser.getRecommender()!=null
									&&!ppUser.getRecommender().equals("-1")
									&&!ppUser.getRecommender().equals("")){
								
								
								this.accountDao.modifyBonusPoint(ppUser.getRecommender(), String.valueOf(consumeAmount*bonusPointParentlevel2), date);
								BonusPoint ppBonusPoint = new BonusPoint();
								ppBonusPoint.setConsumerId(demand.getUserId());
								ppBonusPoint.setCreateDate(date);
								ppBonusPoint.setDemandId(demandId);
								ppBonusPoint.setPoint(String.valueOf(consumeAmount*bonusPointParentlevel2));
								ppBonusPoint.setSublevel(Constant.BONUSPOINT_SUBLEVEL2);
								ppBonusPoint.setUserId(ppUser.getRecommender());
								ppBonusPoint.setRemark(Constant.BONUSPOINT_SUBLEVEL2+"级邀请人任务号"+demandId+"完成，赠送积分");
								this.bonusPointDao.insertBonusPoint(ppBonusPoint);
								MessageSystem ppms = new MessageSystem();
								ppms.setLevel(Constant.MESSAGESYSTEM_LEVEL_INFO);
								ppms.setContent(ppBonusPoint.getRemark());
								ppms.setCreateDate(date);
								ppms.setModifyDate(date);
								ppms.setUserId(ppBonusPoint.getUserId());
								ppms.setTitle("您获得了来自任务号"+demandId+"完成的积分！点击立即查看");
								ppms.setLink("/dashboard/Reward/bonus_point.jsp");
								ppms.setState(Constant.MESSAGESYSTEM_STATE_UNREAD);
								this.messageSystemService.saveMessage(ppms);
								//父级的父级的父级推荐人
								User pppUser = this.userDao.getUserById(ppUser.getRecommender());
								if(pppUser!=null&&pppUser.getRecommender()!=null
										&&!pppUser.getRecommender().equals("-1")
										&&!pppUser.getRecommender().equals("")){
									
									this.accountDao.modifyBonusPoint(pppUser.getRecommender(), String.valueOf(consumeAmount*bonusPointParentlevel3), date);
									BonusPoint pppBonusPoint = new BonusPoint();
									pppBonusPoint.setConsumerId(demand.getUserId());
									pppBonusPoint.setCreateDate(date);
									pppBonusPoint.setDemandId(demandId);
									pppBonusPoint.setPoint(String.valueOf(consumeAmount*bonusPointParentlevel3));
									pppBonusPoint.setSublevel(Constant.BONUSPOINT_SUBLEVEL3);
									pppBonusPoint.setUserId(pppUser.getRecommender());
									pppBonusPoint.setRemark(Constant.BONUSPOINT_SUBLEVEL3+"级邀请人任务号"+demandId+"完成，赠送积分");
									this.bonusPointDao.insertBonusPoint(pppBonusPoint);
									
									MessageSystem pppms = new MessageSystem();
									pppms.setLevel(Constant.MESSAGESYSTEM_LEVEL_INFO);
									pppms.setContent(pppBonusPoint.getRemark());
									pppms.setCreateDate(date);
									pppms.setModifyDate(date);
									pppms.setUserId(pppBonusPoint.getUserId());
									pppms.setTitle("您获得了来自任务号"+demandId+"完成的积分！点击立即查看");
									pppms.setLink("/dashboard/Reward/bonus_point.jsp");
									pppms.setState(Constant.MESSAGESYSTEM_STATE_UNREAD);
									this.messageSystemService.saveMessage(pppms);
								}
							}*/
						}
					}
					responseObj.setCode(ResponseCode.SUCCESS_CODE);
					return responseObj;
				}else{
					throw new Exception();
				}
			}else{
				throw new Exception();
			}
			
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException("收款失败，请重试！", e);
		}
	}
	
	@Override
	public ResponseObject<Object> opposePay(String userId, String nickName, ReceiveDemand rd)
			throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>();
		String demandId = rd.getDemandId();
		if(userId==null||demandId==null||!DemandUtil.validateId(demandId)){
			return new ResponseObject<Object>(ResponseCode.PARAMETER_ERROR,"参数无效");
		}
		String date = DateUtil.date2String(new Date());
		try {
			Demand demand = this.demandDao.getDemandById(demandId);
			if(demand==null){
				return new ResponseObject<Object>( ResponseCode.DEMAND_NOT_EXISTS, "任务不存在！");
			}
			if(!Constant.DEMAND_STATE_PAY.equals(demand.getState())){
				return new ResponseObject<Object>( ResponseCode.DEMAND_MODIFY_STATE_ERROR, "任务不在收款状态！");
			}
			
			List<ReceiveDemand> rds = demand.getReceiveDemands();
			String rdId = "";
			if(rds!=null){
				for(ReceiveDemand tempRD:rds){
					if(Constant.RECEIVEDEMAND_STATE_CHECKED.equals(tempRD.getState())&&
							userId.equals(tempRD.getUserId())&&
							Constant.RECEIVEDEMAND_PAY_STATE2.equals(tempRD.getPayState())){
						rdId = tempRD.getId();
						break;
					}
				}
			}else{
				responseObj.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
				responseObj.setMessage("你不是任务的中标人，无法收款！");
				return responseObj;
			}
			
			if(rdId==null||rdId==""){
				responseObj.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
				responseObj.setMessage("任务不在收款状态!");
				return responseObj;
			}
			int nResult = this.demandDao.modifyDemandState(demandId, demand.getUserId(), Constant.DEMAND_STATE_ARBITRATION, date);
			if(nResult>0){
				int result = this.receiveDemandDao.opposePay(Constant.RECEIVEDEMAND_PAY_STATE4, rd.getRefutePercent(), rd.getRefuteReason(),
						rd.getRefuteReasonUrl1(), rd.getRefuteReasonUrl2(), rd.getRefuteReasonUrl3(), rd.getRefuteReasonUrl4(), rd.getRefuteReasonUrl5(), 
						rd.getRefuteReasonUrl1Name(), rd.getRefuteReasonUrl2Name(), rd.getRefuteReasonUrl3Name(), rd.getRefuteReasonUrl4Name(), rd.getRefuteReasonUrl5Name(), 
						rdId, demandId, date);
				//int result = this.receiveDemandDao.modifyPayState(rd.getId(), Constant.RECEIVEDEMAND_PAY_STATE4, date);
				if(result>0){
					
					//add by haokun 2017/01/26 ，增加邮件提醒
					User user = this.userDao.getUserById(demand.getUserId());
					if(user.getEmail()!=null&&!user.getEmail().equals("")&&user.getEmailState().equals(Constant.USER_EMAIL_STATE1)){
						String subject = "[AnyoneHelps] 任务仲裁";
						String content ="尊敬的用户"+user.getNickName()+"<br>";
						content +=""+nickName+"对任务'"+demand.getTitle()+"'提出仲裁请求并提交了理由，<a href='www.anyonehelps.com/dashboard/Task/pubDetail.jsp?id="+Base64Util.encode(demandId)+"'>点击立即查看</a><br><br>"; 
						content +="<br><br>感谢您对AnyoneHelps的支持!<br>AnyoneHelps团队<br>www.anyonehelps.com<br>";  /*haokun add*/
						content +="<img src='https://www.anyonehelps.com/assets/pages/img/index/logo-bottom.png'/>";/*haokun add*/

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
					//系统消息
					MessageSystem pm = new MessageSystem();
					pm.setLevel(Constant.MESSAGESYSTEM_LEVEL_INFO);
					pm.setContent("您好！"+nickName+"对任务'"+demand.getTitle()+"'提出仲裁请求并提交了理由，点击立即查看");
					pm.setCreateDate(date);
					pm.setModifyDate(date);
					pm.setUserId(demand.getUserId());
					pm.setTitle("您好！"+nickName+"对任务"+demandId+"提出仲裁请求并提交了理由，点击立即查看");
					pm.setLink("/dashboard/Task/pubDetail.jsp?id="+Base64Util.encode(demandId));
					pm.setState(Constant.MESSAGESYSTEM_STATE_UNREAD);
					this.messageSystemService.saveMessage(pm);
					responseObj.setCode(ResponseCode.SUCCESS_CODE);
					return responseObj;
				}else{
					throw new Exception();
				}
			}else{
				throw new Exception();
			}
			
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException("不同意收款，提交仲裁失败！", e);
		}
	}
	
	/*这个方法可能有问题*/
	public ResponseObject<Object> evaluate(String userId,  Evaluate evaluate) throws ServiceException {
		ResponseObject<Object> result = new ResponseObject<Object>();
		if (userId == null|| evaluate == null||
				evaluate.getDemandId().equals("")) {
			result.setCode(ResponseCode.PARAMETER_ERROR);
			result.setMessage("参数无效");
		} else {
			try {
				String demandId = evaluate.getDemandId();
				Demand demand = this.demandDao.getDemandById(demandId);
				if(demand==null){
					result.setCode(ResponseCode.DEMAND_NOT_EXISTS);
					result.setMessage("没有对应的任务");
					return result;
				}
				if(demand.getUserId().equals(evaluate.getEvaluateUserId())){  //接受任务者向发布任务者评价
					List<ReceiveDemand> rds = demand.getReceiveDemands();
					ReceiveDemand tempRD = null;
					for(ReceiveDemand rd :rds){
						if(!rd.getUserId().equals(userId)){
							continue;
						}
						if(rd.getEvaluateStateReceiver().equals(Constant.RECEIVEDEMAND_EVALUATE_STATE_PUBLISH_CHECKED)){
							continue;
						}
						tempRD = rd;
					}
					if(tempRD==null){
						result.setCode(ResponseCode.PARAMETER_ERROR);
						result.setMessage("任务已评价，请刷新页面");
					}else{
						int nResult = this.receiveDemandDao.modifyEvaluatePublish(tempRD.getId());
						if(nResult>0){
							
							double dEvaluate = Double.valueOf(evaluate.getEvaluate() != null ? evaluate.getEvaluate().trim() : "");
							String avg = String.format("%.2f",dEvaluate);
								
							int nR = this.userDao.updatePublishEvaluateById(demand.getUserId(), avg, avg);
							if(nR>0){
								String date = DateUtil.date2String(new Date());
								Evaluate e = new Evaluate();
								e.setCreateDate(date);
								e.setDemandId(demandId);
								e.setDescription(evaluate.getDescription());
								e.setDirection(Constant.EVALUATE_DIRECTION0);
								e.setEvaluate(evaluate.getEvaluate());
								e.setQuality("5");
								e.setPunctual("5");
								e.setEvaluateUserId(demand.getUserId());
								e.setReceiveDemandId(tempRD.getId());
								e.setUserId(userId);
								int n = this.evaluateDao.insertEvaluate(e);
								if(n>0){
									result.setCode(ResponseCode.SUCCESS_CODE);
								}else{
									result.setCode(ResponseCode.PARAMETER_ERROR);
									result.setMessage("任务出错，请重试");
								}
							}
						}else{
							// 进行事务回滚
							throw new Exception();
						}
					}
				}else{           //发布任务者向接受任务者评价
					List<ReceiveDemand> rds = demand.getReceiveDemands();
					ReceiveDemand tempRD = null;
					for(ReceiveDemand rd :rds){
						if(!rd.getUserId().equals(evaluate.getEvaluateUserId())||
								rd.getEvaluateState().equals(Constant.RECEIVEDEMAND_EVALUATE_STATE_CHECKED)||
								!rd.getState().equals(Constant.RECEIVEDEMAND_STATE_CHECKED)){
							continue;
						}
						tempRD = rd;
					}
					
					if(tempRD==null){
						result.setCode(ResponseCode.PARAMETER_ERROR);
						result.setMessage("任务已评价，请刷新页面");
					}else{
						int nResult = this.receiveDemandDao.modifyEvaluate(tempRD.getId());
						if(nResult>0){
								
							double dEvaluate = Double.valueOf(evaluate.getEvaluate() != null ? evaluate.getEvaluate().trim() : "");
							double dQuality = Double.valueOf(evaluate.getQuality() != null ? evaluate.getQuality().trim() : "");
							double dPunctual = Double.valueOf(evaluate.getPunctual() != null ? evaluate.getPunctual().trim() : "");
							
							String avg = String.format("%.2f",dEvaluate*Constant.EVALUATE_EVALUATE_PERCENT+
								dQuality*Constant.EVALUATE_QUALITY_PERCENT+dPunctual*Constant.EVALUATE_PUNCTUAL_PERCENT);
							int nR = this.userDao.updateEvaluateById(evaluate.getEvaluateUserId(), avg, 
							String.valueOf(dEvaluate), String.valueOf(dQuality), String.valueOf(dPunctual));
							if(nR>0){
									
								String date = DateUtil.date2String(new Date());
								Evaluate e = new Evaluate();
								e.setCreateDate(date);
								e.setDemandId(demandId);
								e.setDescription(evaluate.getDescription()); 
								e.setDirection(Constant.EVALUATE_DIRECTION1);
								e.setQuality(evaluate.getQuality());
								e.setPunctual(evaluate.getPunctual());
								e.setEvaluate(evaluate.getEvaluate());
								e.setEvaluateUserId(tempRD.getUserId());
								e.setReceiveDemandId(tempRD.getId());
								e.setUserId(userId);
								int n = this.evaluateDao.insertEvaluate(e);
								if(n>0){
									result.setCode(ResponseCode.SUCCESS_CODE);
								}else{
									result.setCode(ResponseCode.PARAMETER_ERROR);
									result.setMessage("任务出错，请重试");
								}
							}
						}else{
							// 进行事务回滚
							throw new Exception();
						}
					}
				}
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException(e);
			}
		}
		return result;
	}

	@Override
	public ResponseObject<Object> getDemandSumAmountByState(List<String> states) throws ServiceException {
		ResponseObject<Object> result = new ResponseObject<Object>();
		try {
			String sum = this.demandDao.getSumAmount(states);
			int count = this.demandDao.countDemandByState(states);
			List<NationalityCount> nc = this.demandDao.searchNCByState(states);
			Map<String, Object> sumAndCount = new HashMap<String, Object>();
			sumAndCount.put("sum", sum);
			sumAndCount.put("count", count);
			sumAndCount.put("nc", nc);
			result.setData(sumAndCount);
			result.setCode(ResponseCode.SUCCESS_CODE);
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> ResponseObject<PageSplit<T>> searchByReceiverId(String userId, List<String>states,int pageSize, int pageNow)
			throws ServiceException {
		try {
			int rowCount = 0;
			try {
				rowCount = this.demandDao.countByRDReceiverId(userId,states);
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException("获取接收的任务个数失败", e);
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
					List<Demand> demands = this.demandDao.searchByRDReceiverId(userId, states, startIndex, pageSize);
					if (demands != null && !demands.isEmpty()) {
						for (Demand d : demands) {
							List<ReceiveDemand> rds = new ArrayList<ReceiveDemand>();
							//去掉不是自己的接收任务信息
							if( d.getReceiveDemands()!= null && !d.getReceiveDemands().isEmpty()){
								for(int i=0;i<d.getReceiveDemands().size();i++){
									ReceiveDemand rd = d.getReceiveDemands().get(i);
									if(rd.getUserId().equals(userId))
										rds.add(rd);
										//d.getReceiveDemands().remove(i);
								}
							}
							d.setReceiveDemands(rds);
							pageSplit.addData((T)d);
						}
					}
				} catch (Exception e) {
					throw ExceptionUtil.handle2ServiceException("获取接收的任务列表失败", e);
				}
				responseObj.setData(pageSplit);
			} else {
				responseObj.setMessage("没有接收的任务");
			}
			return responseObj;
		} catch (ServiceException e) {
			throw e;
		}
	}
	
	@Override
	public ResponseObject<Object> searchDemandByIdForReceiver(String userId,
			String demandId) throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>();
		if(userId==null||demandId==null||!DemandUtil.validateId(demandId)){
			return new ResponseObject<Object>(ResponseCode.PARAMETER_ERROR,"参数无效");
		}
		try {
			Demand demand = this.demandDao.getDemandById(demandId);
			if(demand==null){
				return new ResponseObject<Object>( ResponseCode.DEMAND_NOT_EXISTS, "任务不存在！");
			}
			
			List<ReceiveDemand> rds = demand.getReceiveDemands();
			ReceiveDemand rd  = null;
			if(rds!=null){
				for(ReceiveDemand tempRD:rds){
					if(userId.equals(tempRD.getUserId())){
						rd = tempRD;
						break;
					}
				}
			}else{
				responseObj.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
				responseObj.setMessage("你没有投标该任务！");
				return responseObj;
			}
			
			if(rd==null){
				responseObj.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
				responseObj.setMessage("你没有投标该任务！");
				return responseObj;
			}
			rds.clear();
			rds.add(rd);
			demand.setReceiveDemands(rds);
			responseObj.setData(demand);
			return responseObj;
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
	}

	@Override
	public ResponseObject<Object> addDemandMessage(DemandMessage demandMessage)
			throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>();
		if (null == demandMessage) {
			responseObj.setCode(ResponseCode.PARAMETER_ERROR);
			responseObj.setMessage("参数无效");
		} else {
			String date = DateUtil.date2String(new Date());
			demandMessage.setCreateDate(date);
			try {
				
				int result = this.demandMessageDao.insertMessage(demandMessage);
				if (result > 0) {
					//Demand demand = this.demandDao.getDemandById(demandMessage.getDemandId());
					//如果自己给自己的任务留言，那么就不给自己推送消息
					if(!demandMessage.getUserId().equals(demandMessage.getReceiverId())){
						MessageUser mu = new MessageUser();
						mu.setContent("您好！"+demandMessage.getUserNickName()+"在任务"+demandMessage.getDemandId()+"给您留言了");
						mu.setCreateDate(date);
						mu.setLink("/task/detail.jsp?id="+Base64Util.encode(demandMessage.getDemandId())+"&view=tab_15_1#tab_15_1");
						mu.setModifyDate(date);
						mu.setSendUserId(demandMessage.getUserId());
						mu.setSendUserNick(demandMessage.getUserNickName());
						mu.setState(Constant.MESSAGEUSER_STATE_UNREAD);
						mu.setUserId(demandMessage.getReceiverId());
						this.messageUserDao.insertMessage(mu);
					}
					if(!"-1".equals(demandMessage.getParentId())){
						Demand demand = this.demandDao.getDemandById(demandMessage.getDemandId());
						
						if(!demand.getUserId().equals(demandMessage.getUserId())&&!demand.getUserId().equals(demandMessage.getReceiverId())){
							MessageUser mu = new MessageUser();
							mu.setContent("您好！"+demandMessage.getUserNickName()+"在任务"+demandMessage.getDemandId()+"回复了您");
							mu.setCreateDate(date);
							mu.setLink("/task/detail.jsp?id="+Base64Util.encode(demandMessage.getDemandId())+"&view=tab_15_1#tab_15_1");
							mu.setModifyDate(date);
							mu.setSendUserId(demandMessage.getUserId());
							mu.setSendUserNick(demandMessage.getUserNickName());
							mu.setState(Constant.MESSAGEUSER_STATE_UNREAD);
							mu.setUserId(demand.getUserId());
							this.messageUserDao.insertMessage(mu);
						}
						/*List<DemandMessage> dms = this.demandMessageDao.getUserByParentId(demandMessage.getParentId());
						if(dms!=null){
							for(DemandMessage dm:dms){
								if(!dm.getUserId().equals(demandMessage.getUserId())&&!dm.getUserId().equals(demandMessage.getReceiverId())){
									MessageUser mu = new MessageUser();
									mu.setContent("任务单号("+demandMessage.getDemandId()+")有留言回复，请查看");
									mu.setCreateDate(date);
									mu.setLink("/task/detail.jsp?id="+demandMessage.getDemandId()+"&view=tab_15_1#tab_15_1");
									mu.setModifyDate(date);
									mu.setSendUserId(demandMessage.getUserId());
									mu.setSendUserNick(demandMessage.getUserNickName());
									mu.setState(Constant.MESSAGEUSER_STATE_UNREAD);
									mu.setUserId(dm.getUserId());
									this.messageUserDao.insertMessage(mu);
								}
							}
						}*/
					}
					responseObj.setCode(ResponseCode.SUCCESS_CODE);
				} else {
					responseObj.setCode(ResponseCode.EDEMANDMESSAGE_INSERT_ERROR);
					responseObj.setMessage("提交留言失败，请重试");
				}
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException(e);
			}
		}
		return responseObj;
	}
	@Override
	public ResponseObject<Map<String, Object>> getSumAmountAndCountFinish(
			String userId) throws ServiceException {
		ResponseObject<Map<String,Object>> result = new ResponseObject<Map<String,Object>>();
		if (StringUtil.isEmpty(userId)) {
			result.setCode(ResponseCode.PARAMETER_ERROR);
			result.setMessage("参数无效");
		} else { 
			try {
				Map<String,Object> map = new HashMap<String,Object>();
				List<String> states = new ArrayList<String>();
				states.add(Constant.DEMAND_STATE_END);
				int countPublishDemand = this.demandDao.countByKey(userId, "",null);
				int nFinishCount = this.demandDao.countByRDReceiverId(userId, states);
				double countAmount = this.accountDetailDao.sumAmountByType(userId, Constant.ACCOUNT_DETAIL_TYPE22);
				double poundageAmount = this.accountDetailDao.sumAmountByType(userId, Constant.ACCOUNT_DETAIL_TYPE38);
				map.put("finishCount",nFinishCount);
				map.put("countPublishDemand",countPublishDemand);
				map.put("countAmount",countAmount -poundageAmount);
				
				result.setCode(ResponseCode.SUCCESS_CODE);
				result.setData(map);
				
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException(e);
			}
		}
		return result;
	}
	@Override
	public ResponseObject<Set<String>> searchDemandAllRegion(String region) throws ServiceException {

		ResponseObject<Set<String>> responseObj = new ResponseObject<Set<String>>(
				ResponseCode.SUCCESS_CODE);
		try {
			String key = StringUtil.escapeStringOfSearchKey(region);  
			List<Demand> demands = this.demandDao.searchDemandAllRegion(key);
			if (demands != null && !demands.isEmpty()) {
				Set<String> set = new HashSet<String>();
				for (Demand d : demands) {
					if(d.getCity()!=null&&d.getCity().toLowerCase().contains(region.toLowerCase()))
						set.add(d.getCity());
					if(d.getRegion()!=null&&d.getRegion().toLowerCase().contains(region.toLowerCase()))
						set.add(d.getRegion());
					if(d.getCountry()!=null&&d.getCountry().toLowerCase().contains(region.toLowerCase()))
						set.add(d.getCountry());
					if(d.getLocationName()!=null&&d.getLocationName().toLowerCase().contains(region.toLowerCase()))
						set.add(d.getLocationName());
				}
				responseObj.setData(set);
			}
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException("获取所有任务位置失败", e);
		}

		return responseObj;
	}
	@Override
	public ResponseObject<Object> modifyDemand(String nickName, Demand demand)
			throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>();
		if (null == demand) {
			responseObj.setCode(ResponseCode.PARAMETER_ERROR);
			responseObj.setMessage("参数无效");
		} else {
			String date = DateUtil.date2String(new Date());
			demand.setModifyDate(date);
			//DemandUtil.getRegionByIp(demand);
			try {
				
				Demand oldDemand = this.demandDao.getDemandById(demand.getId());
				/*查看任务是否存在*/
				if(oldDemand==null){
					return new ResponseObject<Object>( ResponseCode.DEMAND_NOT_EXISTS, "任务不存在,无修改!");
				}
				/*查看任务是否是自己发布的*/
				if(!oldDemand.getUserId().equals(demand.getUserId())){
					return new ResponseObject<Object>( ResponseCode.DEMAND_NOT_EXISTS, "无法修改不属于您的任务,请刷新页面后再修改!");
				}
				/*查看任务状态是否是可修改的*/
				if(!oldDemand.getState().equals(Constant.DEMAND_STATE_READY)&&!oldDemand.getState().equals(Constant.DEMAND_STATE_RECEIVE)){
					return new ResponseObject<Object>( ResponseCode.DEMAND_MODIFY_ERROR, "任务已开始或者已关闭，不能再修改!");
				}
				
				if(oldDemand.getPayState().equals(Constant.DEMAND_PAY_STATE0)){ //未付款，直接修改任务即可
					int iresult = this.demandDao.modifyDemand(demand);
					if(iresult>0){
					} else {
						responseObj.setCode(ResponseCode.DEMAND_MODIFY_ERROR);
						responseObj.setMessage("修改任务失败，请重试");
					}
					
				}else{  //已经付款，需要比较金额
					/*比较任务金额*/
					double newDemandAmount = StringUtil.string2Double(demand.getAmount());
					double oldDemandAmount = StringUtil.string2Double(oldDemand.getAmount());
					if(newDemandAmount > oldDemandAmount){
						//还要扣款
						AccountDetail detail = new AccountDetail(); 
						detail.setAmount(String.valueOf(newDemandAmount-oldDemandAmount));
						detail.setRealAmount(detail.getAmount());
						detail.setUserId(demand.getUserId());
						detail.setState(Constant.ACCOUNT_DETAIL_STATE2);
						//detail.setName("任务金额冻结（修改任务）");   // haokun delete 2017/03/27
						detail.setName("收取任务保证金（修改任务）");   //haokun added 2017/03/27 改为任务保证金
						detail.setCreateDate(date);
						detail.setCurrency("美元");
						detail.setModifyDate(date);
						detail.setType(Constant.ACCOUNT_DETAIL_TYPE39);
						detail.setRemark("任务号:"+demand.getId());
						detail.setDemandId(demand.getId());
						detail.setThirdNo("");
						//发票号
						InvoiceNo in = this.invoiceNoDao.getByType(Constant.INVOICE_TYPE1);
						if(in != null){
							if(this.invoiceNoDao.modifyStateById(in.getId(),date) > 0){
								detail.setInvoiceNo(in.getInvoiceNo());
							}
						}
						
						Account a = accountDao.getAccountByUserId(detail.getUserId());
						double usd= StringUtil.string2Double(a.getUsd());
						
						double newusd = usd - StringUtil.string2Double(detail.getAmount());
						if (newusd >= 0) {
							// ignore
						} else {
							Map<String, String> data = new HashMap<String, String>();
							data.put("usd", a.getUsd());
							data.put("freezeUsd", a.getFreezeUsd());
							responseObj.setCode(ResponseCode.ACCOUNT_INSUFFICIENT_BALANCE);
							responseObj.setMessage("帐户余额不足,请充值");
							responseObj.setData(data);
							return responseObj;
						}
						
						int iresult = this.demandDao.modifyDemand(demand);
						if(iresult>0){
							// 账户支付，修改账户余额
							if (this.accountDao.modifyUsdByUserId(a.getUserId(), String.valueOf(newusd), date) > 0) {
								if(this.accountDao.addFreezeUsd(a.getUserId(), detail.getAmount(), date) > 0){
									// pass
								}else{
									throw new Exception();
								}
							} else {
								throw new Exception();
							}
							this.accountDetailDao.insertAccountDetail(detail);
						} else {
							responseObj.setCode(ResponseCode.DEMAND_MODIFY_ERROR);
							responseObj.setMessage("修改任务失败，请重试");
						}
					}else if(newDemandAmount == oldDemandAmount){
						//金额没变，不用处理金额
						int iresult = this.demandDao.modifyDemand(demand);
						if(iresult>0){
							//pass
						} else {
							responseObj.setCode(ResponseCode.DEMAND_MODIFY_ERROR);
							responseObj.setMessage("修改任务失败，请重试");
						}
					}else{
						//返款
						AccountDetail detail = new AccountDetail(); 
						detail.setAmount(String.valueOf(oldDemandAmount-newDemandAmount));
						detail.setRealAmount(detail.getAmount());
						detail.setUserId(demand.getUserId());
						detail.setState(Constant.ACCOUNT_DETAIL_STATE2);
						//detail.setName("冻结金额返还（任务修改）");   //haokun deleted 2017/03/27
						detail.setName("返还任务保证金（任务修改）");  //haokun added 2017/03/27 改为任务保证金
						detail.setCreateDate(date);
						detail.setCurrency("美元");
						detail.setModifyDate(date);
						detail.setType(Constant.ACCOUNT_DETAIL_TYPE20);
						detail.setRemark("任务号:"+demand.getId());
						detail.setDemandId(demand.getId());
						detail.setThirdNo("");
						//发票号
						InvoiceNo in = this.invoiceNoDao.getByType(Constant.INVOICE_TYPE3);
						if(in != null){
							if(this.invoiceNoDao.modifyStateById(in.getId(),date) > 0){
								detail.setInvoiceNo(in.getInvoiceNo());
							}
						}
						
						Account a = accountDao.getAccountByUserId(detail.getUserId());
						double usd= StringUtil.string2Double(a.getUsd());
							
						double newusd = usd + StringUtil.string2Double(detail.getAmount());
						int iresult = this.demandDao.modifyDemand(demand);
						if(iresult>0){
							// 账户支付，修改账户余额
							if (this.accountDao.modifyUsdByUserId(a.getUserId(), String.valueOf(newusd), date) > 0) {
								if(this.accountDao.reduceFreezeUsd(a.getUserId(), detail.getAmount(), date) > 0){
									// pass
								}else{
									throw new Exception();
								}
							} else {
								throw new Exception();
							}
							this.accountDetailDao.insertAccountDetail(detail);
						} else {
							responseObj.setCode(ResponseCode.DEMAND_MODIFY_ERROR);
							responseObj.setMessage("修改任务失败，请重试");
						}
					}
				}
				
					
					
				/*删除原来的技能 start*/
				this.demandSpecialtyDao.deleteByDemandId(demand.getId());
				/*删除原来的技能 end*/
				
				/*插入技能 start*/
				List<DemandSpecialty> dss = new ArrayList<DemandSpecialty>();
				if(demand.getDs()!=null){
					for(int i=0;i<demand.getDs().size();i++){
						DemandSpecialty ds = new DemandSpecialty(); 
						ds.setDemandId(demand.getId());
						ds.setSpecialtyTypeId(demand.getDs().get(i).getSpecialtyTypeId());
						ds.setSpecialtyId(demand.getDs().get(i).getSpecialtyId());
						dss.add(ds);
					}
				}
				if(dss.size()>0) this.demandSpecialtyDao.insert(dss);
				/*插入技能 end*/
					
				List<DemandFollow> dfs = this.demandFollowDao.getByDemandId(demand.getId()); 
				List<DemandFollow> tempdfs = null; 
				List<ReceiveDemand> rds = new ArrayList<ReceiveDemand>();
				rds = oldDemand.getReceiveDemands();
				if(rds!=null){
					for(ReceiveDemand rd:rds){
						
						//如果关注人已经接单，那么就不需要再发消息推送
						/*操作迭代器时不能让迭代的对象发生改变
						if(dfs!=null){      
							for(DemandFollow df:dfs){
								if(rd.getUserId().equals(df.getUserId())){
									dfs.remove(df);
								}
							}
						}
						*/
						if(dfs!=null){   
							tempdfs = new ArrayList<DemandFollow>();
							for(DemandFollow df:dfs){
								if(!rd.getUserId().equals(df.getUserId())){
									tempdfs.add(df);
								}
							}
						}
						
						int nResult = this.receiveDemandDao.deleteById(rd.getId());
						if(nResult>0){ 
							MessageSystem ms = new MessageSystem();
							ms.setLevel(Constant.MESSAGESYSTEM_LEVEL_INFO);
							ms.setContent("您好！任务发起者"+nickName+"对您投标的任务'"+demand.getTitle()+"'进行了修改，请您根据修改后的内容重新接单，点击立即查看");
							ms.setCreateDate(date);
							ms.setModifyDate(date);
							ms.setUserId(rd.getUserId());
							ms.setTitle("您好！任务发起者"+nickName+"对您投标的任务"+rd.getDemandId()+"进行了修改，请您根据修改后的内容重新接单，点击立即查看");
							ms.setLink("/task/detail.jsp?id="+Base64Util.encode(rd.getDemandId()));
							ms.setState(Constant.MESSAGESYSTEM_STATE_UNREAD);
							this.messageSystemService.saveMessage(ms);
						}else{
							throw new Exception();
						}
					}
				}
				
				//如果关注人已经接单，那么就不需要再发消息推送
				if(tempdfs!=null){      
					for(DemandFollow df:tempdfs){
						MessageSystem ms = new MessageSystem();
						ms.setLevel(Constant.MESSAGESYSTEM_LEVEL_INFO);
						ms.setContent("您好！您关注的任务'"+oldDemand.getTitle()+"'有修改，点击立即查看");
						ms.setCreateDate(date);
						ms.setModifyDate(date);
						ms.setUserId(df.getUserId());
						ms.setTitle("您好！您关注的任务"+oldDemand.getTitle()+"有修改，点击立即查看");
						ms.setLink("/task/detail.jsp?id="+Base64Util.encode(oldDemand.getId()));
						ms.setState(Constant.MESSAGESYSTEM_STATE_UNREAD);
						this.messageSystemService.saveMessage(ms);
					}
					
				}
 					
				responseObj.setCode(ResponseCode.SUCCESS_CODE);
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException(e);
			}
		}
		return responseObj;
	}
	@Override
	public ResponseObject<Object> modifyDemandRemark(String userId, String id,
			String remark) throws ServiceException {
		ResponseObject<Object> result = new ResponseObject<Object>();
		if (userId==null||("").equals(userId)||id==null||("").equals(id)) {
			result.setCode(ResponseCode.PARAMETER_ERROR);
			result.setMessage("参数无效");
		} else {
			try {
				String date = DateUtil.date2String(new Date());
				int nResult = this.demandDao.modifyRemark(userId,id,remark,date);
				if(nResult>0){
					result.setCode(ResponseCode.SUCCESS_CODE);
				}
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException(e);
			}
		}
		return result;
	}
	
	@Override
	public ResponseObject<Object> modifyReceiveDemandRemark(String userId, String id,
			String remark) throws ServiceException {
		ResponseObject<Object> result = new ResponseObject<Object>();
		if (userId==null||("").equals(userId)||id==null||("").equals(id)) {
			result.setCode(ResponseCode.PARAMETER_ERROR);
			result.setMessage("参数无效");
		} else {
			try {
				String date = DateUtil.date2String(new Date());
				int nResult = this.receiveDemandDao.modifyRemark(userId,id,remark,date);
				if(nResult>0){
					result.setCode(ResponseCode.SUCCESS_CODE);
				}
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException(e);
			}
		}
		return result;
	}
	@Override
	public ResponseObject<Object> modifyReceiveDemandAmount(String userId,String username,
			String id, String amount) throws ServiceException {
		ResponseObject<Object> result = new ResponseObject<Object>();
		double dAmount =Double.valueOf(amount != null ? amount.trim() : "");
		if(dAmount<0.01){
			result.setCode(ResponseCode.PARAMETER_ERROR);
			result.setMessage("报价金额错误，请重新输入");
			return result;
		}
		if (userId==null||("").equals(userId)||id==null||("").equals(id)) {
			result.setCode(ResponseCode.PARAMETER_ERROR);
			result.setMessage("参数无效");
			return result;
		} 
		try {
			ReceiveDemand rd = this.receiveDemandDao.getReceiveDemandById(id);
			if(rd==null){
				result.setCode(ResponseCode.PARAMETER_ERROR);
				result.setMessage("投标不存在");
				return result;
			}
			if(!userId.equals(rd.getUserId())){
				result.setCode(ResponseCode.PARAMETER_ERROR);
				result.setMessage("这不是您的投标，请刷新页面再操作");
				return result;
			}
			if(Constant.RECEIVEDEMAND_STATE_TEMP_CHECKED.equals(rd.getState())){
				result.setCode(ResponseCode.PARAMETER_ERROR);
				result.setMessage("发单人正在匹配中，无法修改，如需修改，请联系发单人取消匹配，再修改");
				return result;
			}
			if(Constant.RECEIVEDEMAND_STATE_CHECKED.equals(rd.getState())){
				result.setCode(ResponseCode.PARAMETER_ERROR);
				result.setMessage("任务已经开始，无法修改报价！");
				return result;
			}
			Demand demand = this.demandDao.getDemandById(rd.getDemandId());
			if(demand==null){
				result.setCode(ResponseCode.PARAMETER_ERROR);
				result.setMessage("投标不存在");
				return result;
			}
			if(!Constant.DEMAND_STATE_READY.equals(demand.getState())&&!Constant.DEMAND_STATE_RECEIVE.equals(demand.getState())){
				result.setCode(ResponseCode.PARAMETER_ERROR);
				result.setMessage("任务已开始或者已关闭，无法修改");
				return result;
			}
			String date = DateUtil.date2String(new Date());
			int nResult = this.receiveDemandDao.modifyAmount(userId,id,amount,date);
			if(nResult>0){
				MessageSystem ms= new MessageSystem();
				ms.setContent("接单人"+username+"修改了你发布的任务'"+demand.getTitle()+"'的报价，报价修改为:$"+amount);
				ms.setCreateDate(date);
				ms.setLink("/dashboard/Task/pubDetail.jsp?id="+Base64Util.encode(demand.getId()));
				ms.setModifyDate(date);
				ms.setLevel(Constant.MESSAGESYSTEM_LEVEL_INFO);
				ms.setTitle("任务单号("+demand.getId()+")有人修改报价啦");
				ms.setState(Constant.MESSAGEUSER_STATE_UNREAD);
				ms.setUserId(demand.getUserId());
				this.messageSystemDao.insertMessage(ms);
				result.setCode(ResponseCode.SUCCESS_CODE);
				return result;
			}else{
				result.setCode(ResponseCode.PARAMETER_ERROR);
				result.setMessage("修改失败，请重试");
				return result;
			}
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
	}
	@Override
	public ResponseObject<Object> deleteReceiveDemand(String userId,
			String demandId) throws ServiceException {
		ResponseObject<Object> result = new ResponseObject<Object>();
		if (demandId==null||userId==null) {
			result.setCode(ResponseCode.PARAMETER_ERROR);
			result.setMessage("参数无效");
			return result;
		} else {
			try {
				Demand d = this.demandDao.getDemandById(demandId);
				if(d==null){
					result.setCode(ResponseCode.DEMAND_NOT_EXISTS);
					result.setMessage("任务不存在！");
					return result;
				}
				
				if(!d.getState().equals(Constant.DEMAND_STATE_READY)&&!d.getState().equals(Constant.DEMAND_STATE_RECEIVE)){
					if(d.getState().equals(Constant.DEMAND_STATE_CLOSE)){
						result.setMessage("任务已关闭！");
					}else{
						result.setMessage("任务已开始，无法删除！");
					}
					result.setCode(ResponseCode.DEMAND_STATE_ERROR);
					return result;
				}
				List<ReceiveDemand> rds = d.getReceiveDemands();
				
				if(rds!=null){
					for(ReceiveDemand rd:rds){
						if(rd.getUserId().equals(userId)){
							this.receiveDemandDao.deleteById(rd.getId());
						}
					}
				}
				result.setCode(ResponseCode.SUCCESS_CODE);
				return result;
				
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException(e);
			}
		}
	}
	
	@Override
	public ResponseObject<Object> agreeArbitration(String userId, String nickName,
			String demandId) throws ServiceException {
		ResponseObject<Object> result = new ResponseObject<Object>();
		try {
			Demand demand = this.demandDao.getDemandById(demandId);
			if(demand==null){
				result.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
				result.setMessage("任务不存在！");
				return result;
			}
			if(!demand.getUserId().equals(userId)){
				result.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
				result.setMessage("不是您发布的任务，您无法操作，请刷新页面后再重试！");
				return result;
			}
			
			if(!demand.getState().equals(Constant.DEMAND_STATE_ARBITRATION)){
				result.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
				result.setMessage("任务不是仲裁状态，无法同意对方仲裁！");
				return result;
			}
			
			String date = DateUtil.date2String(new Date());
			List<ReceiveDemand> rds = demand.getReceiveDemands();
			ReceiveDemand rd  = null;
			if(rds!=null){
				for(ReceiveDemand tempRD:rds){
					if(tempRD.getState().equals(Constant.RECEIVEDEMAND_STATE_CHECKED)){
						rd = tempRD;
						break;
					}
				}
			}else{
				result.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
				result.setMessage("接单人不存在！请联系客服");
				return result;
			}
			if(rd==null){
				result.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
				result.setMessage("接单人不存在！请联系客服");
				return result;
			}
			
			if(!ReceiveDemandUtil.validatePercentage(rd.getRefutePercent())){
				result.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
				result.setMessage("接单人提出付款比例异常！");
				return result;
			}
			
			int nResult = 0;
			nResult = this.demandDao.modifyDemandState(demandId, userId, Constant.DEMAND_STATE_END, date);
			if(nResult>0){
				int n = this.receiveDemandDao.modifyPayState(rd.getId(),Constant.RECEIVEDEMAND_PAY_STATE7, date);
				if(n>0){
					double payPercent = StringUtil.string2Double(rd.getRefutePercent())/100;   //支付比例
					
					double amount = StringUtil.string2Double(rd.getAmount());
					double daAmount = 0.00; //附加任务的金额
					//加上附加任务的金额
					List<DemandAttach> das = demand.getDa();
					if(das!=null){
						for(DemandAttach da:das){
							daAmount+=StringUtil.string2Double(da.getAmount() != null ? da.getAmount().trim() : "");
						}
					}
					
					//手续费
					String procedureRatio = "0.13";
					try {
						Properties props = PropertiesReader.read("/anyonehelps.properties");
						procedureRatio = props.getProperty("demand.finish.procedure.ratio");
					} catch (Exception e) {
						log.error("用户("+userId+")全额付款任务("+rd.getDemandId()+")时,读取任务报酬手续费比例配置失败!");
						throw ExceptionUtil.handle2ServiceException("付款异常,请联系客服！",e);
					}
					
					String payAmount = String.valueOf(new BigDecimal((amount+daAmount)*payPercent).setScale(2, RoundingMode.DOWN).doubleValue());    //支付金额
					String returnAmount = String.valueOf(new BigDecimal((amount+daAmount)*(1-payPercent)).setScale(2, RoundingMode.DOWN).doubleValue()); //返款金额
					
					AccountDetail detail = new AccountDetail(); 
					detail.setAmount(payAmount);
					detail.setRealAmount(detail.getAmount());
					detail.setUserId(rd.getUserId());
					detail.setState(Constant.ACCOUNT_DETAIL_STATE2);
					//detail.setName("完成任务获取任务报酬");   haokun modified 2017/03/09
					detail.setName("任务完成报酬");  // haokun modified 2017/03/09
					detail.setCreateDate(date);
					detail.setCurrency("美元");
					detail.setModifyDate(date);
					detail.setType(Constant.ACCOUNT_DETAIL_TYPE22);
					detail.setRemark("成功完成单号:"+rd.getDemandId()+"的任务，获得奖励");
					detail.setDemandId(demand.getId());
					detail.setThirdNo("");
					//发票号
					InvoiceNo in = this.invoiceNoDao.getByType(Constant.INVOICE_TYPE4);
					if(in != null){
						if(this.invoiceNoDao.modifyStateById(in.getId(),date) > 0){
							detail.setInvoiceNo(in.getInvoiceNo());
						}
					}
					
					AccountDetail procedureDetail = new AccountDetail();
					procedureDetail.setAmount(String.valueOf((amount+daAmount)*(1-payPercent)*new BigDecimal(Double.valueOf(procedureRatio)).setScale(2, RoundingMode.DOWN).doubleValue()));
					procedureDetail.setRealAmount(procedureDetail.getAmount());
					procedureDetail.setUserId(rd.getUserId());
					procedureDetail.setState(Constant.ACCOUNT_DETAIL_STATE2);
					//procedureDetail.setName("完成任务扣取收款手续费");    haokun modified 2017/03/09
					procedureDetail.setName("收取任务报酬手续费");    //haokun modified 2017/03/09
					procedureDetail.setCreateDate(date);
					procedureDetail.setCurrency("美元");
					procedureDetail.setModifyDate(date);
					procedureDetail.setType(Constant.ACCOUNT_DETAIL_TYPE38);
					procedureDetail.setRemark("完成编号"+rd.getDemandId()+"任务扣取手续费");
					procedureDetail.setDemandId(demand.getId());
					procedureDetail.setThirdNo("");
					//发票号
					InvoiceNo procedureInvoiceNo = this.invoiceNoDao.getByType(Constant.INVOICE_TYPE7);
					if(procedureInvoiceNo != null){
						if(this.invoiceNoDao.modifyStateById(procedureInvoiceNo.getId(),date) > 0){
							procedureDetail.setInvoiceNo(procedureInvoiceNo.getInvoiceNo());
						}
					}
					
					/*手续费返还 start*/
					User rdUser = this.userDao.getOnlyUserOpenInfoById(rd.getUserId());
					Charges charges = this.chargesDao.getOne();
					if(rdUser == null||charges == null){
						throw new Exception();
					}
					String chargesPercent = "1.00";
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
					DecimalFormat    chargesDf   = new DecimalFormat("######0.00");   
					if(rdUser.getSignDate()!=null){
						Date start = df.parse(rdUser.getSignDate());
						if(getMonth(start, new Date())>3){
							//老用户
							chargesPercent = chargesDf.format(Double.valueOf(charges.getOldcustomerChargerate())/100);
						}else{
							//新用户
							chargesPercent = chargesDf.format(Double.valueOf(charges.getNewcustomerChargerate())/100);
						}
					}
					AccountDetail chargesDetail = new AccountDetail();
					if(!chargesPercent.equals("1.00")){
						chargesDetail.setAmount(String.valueOf((amount+daAmount)*Double.valueOf(procedureRatio)*(1-Double.valueOf(chargesPercent))));
						chargesDetail.setRealAmount(chargesDetail.getAmount());
						chargesDetail.setUserId(rd.getUserId());
						chargesDetail.setState(Constant.ACCOUNT_DETAIL_STATE2);
						//chargesDetail.setName("返还完成任务收款手续费");   //haokun modified 2017/03/09
						chargesDetail.setName("任务报酬手续费返还");   //haokun modified 2017/03/09			
						chargesDetail.setCreateDate(date);
						chargesDetail.setCurrency("美元");
						chargesDetail.setModifyDate(date);
						chargesDetail.setType(Constant.ACCOUNT_DETAIL_TYPE26);
						chargesDetail.setRemark("返还编号"+rd.getDemandId()+"任务扣取手续费");
						chargesDetail.setDemandId(demandId);
						chargesDetail.setThirdNo("");
						//发票号
						InvoiceNo chargesInvoiceNo = this.invoiceNoDao.getByType(Constant.INVOICE_TYPE7);
						if(chargesInvoiceNo != null){
							if(this.invoiceNoDao.modifyStateById(chargesInvoiceNo.getId(),date) > 0){
								chargesDetail.setInvoiceNo(chargesInvoiceNo.getInvoiceNo());
							}
						}
					}
					/*手续费返还 end*/
					
					AccountDetail payDetail = new AccountDetail();
					payDetail.setAmount(payAmount);
					payDetail.setRealAmount(payDetail.getAmount());
					payDetail.setUserId(demand.getUserId());
					payDetail.setState(Constant.ACCOUNT_DETAIL_STATE2);
					payDetail.setName("任务支付");
					payDetail.setCreateDate(date);
					payDetail.setCurrency("美元");
					payDetail.setModifyDate(date);
					payDetail.setType(Constant.ACCOUNT_DETAIL_TYPE41);
					payDetail.setRemark("完成编号"+rd.getDemandId()+"任务，支付接单人报酬");
					payDetail.setDemandId(demandId);
					payDetail.setThirdNo("");
					//发票号
					InvoiceNo payInvoiceNo = this.invoiceNoDao.getByType(Constant.INVOICE_TYPE2);
					if(payInvoiceNo != null){
						if(this.invoiceNoDao.modifyStateById(payInvoiceNo.getId(),date) > 0){
							payDetail.setInvoiceNo(payInvoiceNo.getInvoiceNo());
						}
					}
					
					//接单人余额增加
					Account a = accountDao.getAccountByUserId(detail.getUserId());
					double usd= StringUtil.string2Double(a.getUsd());
					double newusd = usd + StringUtil.string2Double(detail.getAmount())*(1-Double.valueOf(procedureRatio)*Double.valueOf(chargesPercent));
					if (this.accountDao.modifyUsdByUserId(a.getUserId(), String.valueOf(newusd), date) > 0) {
						// pass
					} else {
						throw new Exception();
					}
					this.accountDetailDao.insertAccountDetail(detail);
					this.accountDetailDao.insertAccountDetail(procedureDetail);
					this.accountDetailDao.insertAccountDetail(payDetail);
					/*手续费返还 start*/
					if(!chargesPercent.equals("1.00")){
						this.accountDetailDao.insertAccountDetail(chargesDetail);
					}
					/*手续费返还 end*/
					 
					//写发单人经验值
					if(this.userDao.modifyGradeById(demand.getUserId(),payDetail.getAmount())>0){
						// pass
					}else{
						throw new Exception();
					}
					
					//写接单人经验值
					if(this.userDao.modifyGradeById(detail.getUserId(),payDetail.getAmount())>0){
						// pass
					}else{
						throw new Exception();
					}
					
					//返回部分任务款给发单人
					if((amount+daAmount)*(1-payPercent)>0){
						AccountDetail returnDetail = new AccountDetail(); 
						returnDetail.setAmount(returnAmount);
						returnDetail.setRealAmount(returnDetail.getAmount());
						returnDetail.setUserId(demand.getUserId());
						returnDetail.setState(Constant.ACCOUNT_DETAIL_STATE2);
						//returnDetail.setName("冻结金额返还（部分支付）");  //haokun deleted 2017/03/27
						returnDetail.setName("返还任务保证金（部分支付）"); //haokun added 2017/03/27 改为任务保证金
						returnDetail.setCreateDate(date);
						returnDetail.setCurrency("美元");
						returnDetail.setModifyDate(date);
						returnDetail.setType(Constant.ACCOUNT_DETAIL_TYPE23);
						returnDetail.setRemark("同意任务单号:"+demand.getId()+"的接单人付款，返还奖励");
						returnDetail.setDemandId(demandId);
						returnDetail.setThirdNo("");
						//发票号
						InvoiceNo returnInvoiceNo = this.invoiceNoDao.getByType(Constant.INVOICE_TYPE3);
						if(returnInvoiceNo != null){
							if(this.invoiceNoDao.modifyStateById(returnInvoiceNo.getId(),date) > 0){
								returnDetail.setInvoiceNo(returnInvoiceNo.getInvoiceNo());
							}
						}
						Account returnA = accountDao.getAccountByUserId(returnDetail.getUserId());
						double returnUsd= StringUtil.string2Double(returnA.getUsd());
						double returnNewusd = returnUsd + StringUtil.string2Double(returnDetail.getAmount());
						if (this.accountDao.modifyUsdByUserId(returnA.getUserId(), String.valueOf(returnNewusd), date) > 0) {
							// pass
						} else {
							throw new Exception();
						}
						
						this.accountDetailDao.insertAccountDetail(returnDetail);
					}
					
					//减小发单人冻结金额
					if(this.accountDao.reduceFreezeUsd(demand.getUserId(), detail.getAmount(), date) > 0){
						// pass
					}else{
						throw new Exception();
					}
					
					//接单人系统消息推送
					//还没给接单人发短信或邮箱提醒。
					MessageSystem ms = new MessageSystem();
					ms.setCreateDate(date);
					ms.setLevel(Constant.MESSAGESYSTEM_LEVEL_INFO);
					ms.setContent(nickName+"同意你对任务'"+demand.getTitle()+"'发起的支付仲裁，向您支付了$"+payAmount+"任务报酬，请对任务进行评价");
					//还没写配置
					ms.setLink("/dashboard/Task/accDetail.jsp?id="+Base64Util.encode(demandId));
					ms.setModifyDate(date);
					ms.setTitle("您好! "+nickName+"同意了您对任务"+demandId+"的仲裁请求，点击立即查看");
					ms.setUserId(rd.getUserId());
					ms.setState(Constant.MESSAGESYSTEM_STATE_UNREAD);
					this.messageSystemService.saveMessage(ms);
					
					//开始积分分配
					double consumeAmount = new BigDecimal((amount + daAmount)*payPercent).setScale(2, RoundingMode.CEILING).doubleValue();
					//父级推荐人
					User pUser = this.userDao.getUserById(demand.getUserId());
					if(pUser!=null&&consumeAmount>0){  
						if(!pUser.getRecommender().equals("-1")
								&&pUser.getRecommender()!=null
								&&!pUser.getRecommender().equals("")){
							
							
							
							AccountDetail pdetail = new AccountDetail(); 
							pdetail.setAmount(String.valueOf(consumeAmount*bonusPointParentlevel1));
							pdetail.setRealAmount(pdetail.getAmount());
							pdetail.setUserId(pUser.getRecommender());
							pdetail.setState(Constant.ACCOUNT_DETAIL_STATE2);
							pdetail.setName("任务奖励");
							pdetail.setCreateDate(date);
							pdetail.setCurrency("美元");
							pdetail.setModifyDate(date);
							pdetail.setType(Constant.ACCOUNT_DETAIL_TYPE25);
							pdetail.setRemark("");
							pdetail.setThirdNo("");
							pdetail.setDemandId(demandId);
							InvoiceNo pin = this.invoiceNoDao.getByType(Constant.INVOICE_TYPE4);
							if(pin!=null){
								if(this.invoiceNoDao.modifyStateById(pin.getId(),date) > 0){
									pdetail.setInvoiceNo(pin.getInvoiceNo());
								}
							}
							
							Account pAccount = accountDao.getAccountByUserId(pdetail.getUserId());
							double pUsd= StringUtil.string2Double(pAccount.getUsd());
							double pNewUsd = pUsd + StringUtil.string2Double(pdetail.getAmount());
							// 账户支付，修改账户余额
							if (this.accountDao.modifyUsdByUserId(pAccount.getUserId(), String.valueOf(pNewUsd), date) > 0) {
								int pResult  = this.accountDetailDao.insertAccountDetail(pdetail);
								if(pResult > 0){
									BonusPoint pBonusPoint = new BonusPoint();
									pBonusPoint.setConsumerId(demand.getUserId());
									pBonusPoint.setCreateDate(date);
									pBonusPoint.setDemandId(demandId);
									pBonusPoint.setPoint(String.valueOf(consumeAmount*bonusPointParentlevel1));
									pBonusPoint.setSublevel(Constant.BONUSPOINT_SUBLEVEL1);
									pBonusPoint.setUserId(pUser.getRecommender());
									pBonusPoint.setRemark("邀请人任务号"+demandId+"完成，奖励金额");
									this.bonusPointDao.insertBonusPoint(pBonusPoint);
									MessageSystem pms = new MessageSystem();
									pms.setLevel(Constant.MESSAGESYSTEM_LEVEL_INFO);
									pms.setContent(pBonusPoint.getRemark());
									pms.setCreateDate(date);
									pms.setModifyDate(date);
									pms.setUserId(pBonusPoint.getUserId());
									pms.setTitle("您获得了来自任务号"+demandId+"完成的奖励金额！点击立即查看");
									pms.setLink("/dashboard/Reward/bonus_point.jsp");
									pms.setState(Constant.MESSAGESYSTEM_STATE_UNREAD);
									this.messageSystemService.saveMessage(pms);
								}
							}else{
								throw new Exception();
							}
							
							//this.accountDao.modifyBonusPoint(pUser.getRecommender(), String.valueOf(consumeAmount*bonusPointParentlevel1), date);
							
							
							/*//父级的父级推荐人
							User ppUser = this.userDao.getUserById(pUser.getRecommender());
							if(ppUser!=null&&!ppUser.getRecommender().equals("-1")
									&&ppUser.getRecommender()!=null
									&&!ppUser.getRecommender().equals("")){
								
								
								this.accountDao.modifyBonusPoint(ppUser.getRecommender(), String.valueOf(consumeAmount*bonusPointParentlevel2), date);
								BonusPoint ppBonusPoint = new BonusPoint();
								ppBonusPoint.setConsumerId(demand.getUserId());
								ppBonusPoint.setCreateDate(date);
								ppBonusPoint.setDemandId(demandId);
								ppBonusPoint.setPoint(String.valueOf(consumeAmount*bonusPointParentlevel2));
								ppBonusPoint.setSublevel(Constant.BONUSPOINT_SUBLEVEL2);
								ppBonusPoint.setUserId(ppUser.getRecommender());
								ppBonusPoint.setRemark(Constant.BONUSPOINT_SUBLEVEL2+"级邀请人任务号"+demandId+"完成，赠送积分");
								this.bonusPointDao.insertBonusPoint(ppBonusPoint);
								MessageSystem ppms = new MessageSystem();
								ppms.setLevel(Constant.MESSAGESYSTEM_LEVEL_INFO);
								ppms.setContent(ppBonusPoint.getRemark());
								ppms.setCreateDate(date);
								ppms.setModifyDate(date);
								ppms.setUserId(ppBonusPoint.getUserId());
								ppms.setTitle("您获得了来自任务号"+demandId+"完成的积分！点击立即查看");
								ppms.setLink("/dashboard/Reward/bonus_point.jsp");
								ppms.setState(Constant.MESSAGESYSTEM_STATE_UNREAD);
								this.messageSystemService.saveMessage(ppms);
								//父级的父级的父级推荐人
								User pppUser = this.userDao.getUserById(ppUser.getRecommender());
								if(pppUser!=null&&!pppUser.getRecommender().equals("-1")
										&&pppUser.getRecommender()!=null
										&&!pppUser.getRecommender().equals("")){
									
									this.accountDao.modifyBonusPoint(pppUser.getRecommender(), String.valueOf(consumeAmount*bonusPointParentlevel3), date);
									BonusPoint pppBonusPoint = new BonusPoint();
									pppBonusPoint.setConsumerId(demand.getUserId());
									pppBonusPoint.setCreateDate(date);
									pppBonusPoint.setDemandId(demandId);
									pppBonusPoint.setPoint(String.valueOf(consumeAmount*bonusPointParentlevel3));
									pppBonusPoint.setSublevel(Constant.BONUSPOINT_SUBLEVEL3);
									pppBonusPoint.setUserId(pppUser.getRecommender());
									pppBonusPoint.setRemark(Constant.BONUSPOINT_SUBLEVEL3+"级邀请人任务号"+demandId+"完成，赠送积分");
									this.bonusPointDao.insertBonusPoint(pppBonusPoint);
									
									MessageSystem pppms = new MessageSystem();
									pppms.setLevel(Constant.MESSAGESYSTEM_LEVEL_INFO);
									pppms.setContent(pppBonusPoint.getRemark());
									pppms.setCreateDate(date);
									pppms.setModifyDate(date);
									pppms.setUserId(pppBonusPoint.getUserId());
									pppms.setTitle("您获得了来自任务号"+demandId+"完成的积分！点击立即查看");
									pppms.setLink("/dashboard/Reward/bonus_point.jsp");
									pppms.setState(Constant.MESSAGESYSTEM_STATE_UNREAD);
									this.messageSystemService.saveMessage(pppms);
								}
							}*/
						}
					}
					
					result.setCode(ResponseCode.SUCCESS_CODE);
					return result;
				}else{
					throw new Exception();
				}
				
				
			}else{
				throw new Exception();
			}
				
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
	}
	
	@Override
	public ResponseObject<Object> endDemand(ReceiveDemand rd)
			throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>();
		String demandId = rd.getDemandId();
		if(demandId==null||!DemandUtil.validateId(demandId)){
			return new ResponseObject<Object>(ResponseCode.PARAMETER_ERROR,"参数无效");
		}
		String date = DateUtil.date2String(new Date());
		try {
			Demand demand = this.demandDao.getDemandById(demandId);
			if(demand==null){
				return new ResponseObject<Object>( ResponseCode.DEMAND_NOT_EXISTS, "任务不存在！");
			}
			if(!Constant.DEMAND_STATE_ARBITRATION.equals(demand.getState())){
				return new ResponseObject<Object>( ResponseCode.DEMAND_MODIFY_STATE_ERROR, "任务不在待裁决状态！");
			}
			
			List<ReceiveDemand> rds = demand.getReceiveDemands();
			ReceiveDemand tRd  = null;
			if(rds!=null){
				for(ReceiveDemand tempRD:rds){
					if(Constant.RECEIVEDEMAND_STATE_CHECKED.equals(tempRD.getState())&&
							(Constant.RECEIVEDEMAND_PAY_STATE4.equals(tempRD.getPayState())||
									Constant.RECEIVEDEMAND_PAY_STATE5.equals(tempRD.getPayState()))){
						tRd = tempRD;
						break;
					}
				}
			}else{
				responseObj.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
				responseObj.setMessage("付款状态不在裁决中,请联系开发人员！");
				return responseObj;
			}
			
			if(tRd==null){
				responseObj.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
				responseObj.setMessage("付款状态不在裁决中,请联系开发人员!");
				return responseObj;
			}
			tRd.setRulePercent(rd.getRulePercent());
			tRd.setRuleReason(rd.getRuleReason());
			tRd.setRuleReasonUrl1(rd.getRuleReasonUrl1());
			tRd.setRuleReasonUrl2(rd.getRuleReasonUrl2());
			tRd.setRuleReasonUrl3(rd.getRuleReasonUrl3());
			tRd.setRuleReasonUrl4(rd.getRuleReasonUrl4());
			tRd.setRuleReasonUrl5(rd.getRuleReasonUrl5());
			tRd.setRuleReasonUrl1Name(rd.getRuleReasonUrl1Name());
			tRd.setRuleReasonUrl2Name(rd.getRuleReasonUrl2Name());
			tRd.setRuleReasonUrl3Name(rd.getRuleReasonUrl3Name());
			tRd.setRuleReasonUrl4Name(rd.getRuleReasonUrl4Name());
			tRd.setRuleReasonUrl5Name(rd.getRuleReasonUrl5Name());
			int nResult = this.demandDao.modifyDemandState(demandId, demand.getUserId(), Constant.DEMAND_STATE_END, date);
			
			if(nResult>0){
				int result = this.receiveDemandDao.endDemand(Constant.RECEIVEDEMAND_PAY_STATE6, tRd.getRulePercent(), tRd.getRuleReason(),
						tRd.getRuleReasonUrl1(), tRd.getRuleReasonUrl2(), tRd.getRuleReasonUrl3(), tRd.getRuleReasonUrl4(), tRd.getRuleReasonUrl5(), 
						tRd.getRuleReasonUrl1Name(), tRd.getRuleReasonUrl2Name(), tRd.getRuleReasonUrl3Name(), tRd.getRuleReasonUrl4Name(), tRd.getRuleReasonUrl5Name(), 
						tRd.getId(), date);
				//int result = this.receiveDemandDao.modifyPayState(tRd.getId(),  date);
				if(result>0){
					//金额分配
					
					double amount = Double.valueOf(tRd.getAmount());
					double daAmount = 0.00; //附加任务的金额
					//加上附加任务的金额
					List<DemandAttach> das = demand.getDa();
					if(das!=null){
						for(DemandAttach da:das){
							daAmount+=Double.valueOf(da.getAmount() != null ? da.getAmount().trim() : "");
						}
					}
					double percent = Double.valueOf(tRd.getRulePercent() != null ? tRd.getRulePercent().trim() : "1");
					
					if(Constant.RECEIVEDEMAND_PAY_PERCENT100.equals(tRd.getRulePercent())){
						//全额付款
						
					}else{
						//部分付款
						String returnAmount = String.valueOf(new BigDecimal((amount+daAmount)*(1-percent/100)).setScale(2, RoundingMode.DOWN).doubleValue()); //返款金额
						//返回部分任务款给发单人
						if((amount+daAmount)*(1-percent/100)>0){
							AccountDetail returnDetail = new AccountDetail(); 
							returnDetail.setAmount(returnAmount);
							returnDetail.setRealAmount(returnDetail.getAmount());
							returnDetail.setUserId(demand.getUserId());
							returnDetail.setState(Constant.ACCOUNT_DETAIL_STATE2);
							//returnDetail.setName("冻结金额返还（部分支付）");   //haokun deleted 2017/03/27
							returnDetail.setName("返还任务保证金（部分支付）");  //haokun added 2017/03/27 改为任务保证金
							returnDetail.setCreateDate(date);
							returnDetail.setCurrency("美元");
							returnDetail.setModifyDate(date);
							returnDetail.setType(Constant.ACCOUNT_DETAIL_TYPE23);
							returnDetail.setRemark("系统裁决任务单号:"+demand.getId()+"付款，返还奖励");
							returnDetail.setDemandId(demandId);
							returnDetail.setThirdNo("");
							//发票号
							InvoiceNo returnInvoiceNo = this.invoiceNoDao.getByType(Constant.INVOICE_TYPE3);
							if(returnInvoiceNo != null){
								if(this.invoiceNoDao.modifyStateById(returnInvoiceNo.getId(),date) > 0){
									returnDetail.setInvoiceNo(returnInvoiceNo.getInvoiceNo());
								}
							}
							Account returnA = accountDao.getAccountByUserId(returnDetail.getUserId());
							double returnUsd= StringUtil.string2Double(returnA.getUsd());
							
							double returnNewusd = returnUsd + StringUtil.string2Double(returnDetail.getAmount());
							
							if (this.accountDao.modifyUsdByUserId(returnA.getUserId(), String.valueOf(returnNewusd), date) > 0) {
								// pass
							} else {
								throw new Exception();
							}
							this.accountDetailDao.insertAccountDetail(returnDetail);
						}
					}
					if(!Constant.RECEIVEDEMAND_PAY_PERCENT0.equals(tRd.getRulePercent())){
						String payAmount = String.valueOf(new BigDecimal((amount+daAmount)*(percent/100)).setScale(2, RoundingMode.DOWN).doubleValue()); //支付金额
						AccountDetail payDetail = new AccountDetail();
						payDetail.setAmount(payAmount);
						payDetail.setRealAmount(payDetail.getAmount());
						payDetail.setUserId(demand.getUserId());
						payDetail.setState(Constant.ACCOUNT_DETAIL_STATE2);
						payDetail.setName("任务支付");
						payDetail.setCreateDate(date);
						payDetail.setCurrency("美元");
						payDetail.setModifyDate(date);
						payDetail.setType(Constant.ACCOUNT_DETAIL_TYPE41);
						payDetail.setRemark("完成编号"+rd.getDemandId()+"任务，支付接单人报酬");
						payDetail.setDemandId(demandId);
						payDetail.setThirdNo("");
						//发票号
						InvoiceNo payInvoiceNo = this.invoiceNoDao.getByType(Constant.INVOICE_TYPE2);
						if(payInvoiceNo != null){
							if(this.invoiceNoDao.modifyStateById(payInvoiceNo.getId(),date) > 0){
								payDetail.setInvoiceNo(payInvoiceNo.getInvoiceNo());
							}
						}
						this.accountDetailDao.insertAccountDetail(payDetail);
					}
					
					//手续费
					String procedureRatio = "0.13";
					try {
						Properties props = PropertiesReader.read("/anyonehelps.properties");
						procedureRatio = props.getProperty("demand.finish.procedure.ratio");
					} catch (Exception e) {
						log.error("系统裁决任务("+tRd.getDemandId()+")时,读取任务报酬手续费比例配置失败!");
						throw ExceptionUtil.handle2ServiceException("裁决异常,请联系客服！",e);
					}
					if(percent >= 0.01){
						String payAmount = String.valueOf((amount+daAmount)*(percent/100));
						
						AccountDetail detail = new AccountDetail(); 
						detail.setAmount(payAmount);
						detail.setRealAmount(detail.getAmount());
						detail.setUserId(tRd.getUserId());
						detail.setState(Constant.ACCOUNT_DETAIL_STATE2);
						//detail.setName("完成任务获取任务报酬");  //haokun modified 2017/03/09
						detail.setName("任务完成报酬");  //haokun modified 2017/03/09
						detail.setCreateDate(date);
						detail.setCurrency("美元");
						detail.setModifyDate(date);
						detail.setType(Constant.ACCOUNT_DETAIL_TYPE22);
						detail.setRemark("系统完成单号:"+tRd.getDemandId()+"的裁决，获得奖励");
						detail.setDemandId(demandId);
						detail.setThirdNo("");
						//发票号
						InvoiceNo in = this.invoiceNoDao.getByType(Constant.INVOICE_TYPE4);
						if(in != null){
							if(this.invoiceNoDao.modifyStateById(in.getId(),date) > 0){
								detail.setInvoiceNo(in.getInvoiceNo());
							}
						}
						AccountDetail procedureDetail = new AccountDetail();
						
						procedureDetail.setAmount(String.valueOf(new BigDecimal((amount+daAmount)*(percent/100)*Double.valueOf(procedureRatio)).setScale(2, RoundingMode.DOWN).doubleValue()));
						procedureDetail.setRealAmount(procedureDetail.getAmount());
						procedureDetail.setUserId(tRd.getUserId());
						procedureDetail.setState(Constant.ACCOUNT_DETAIL_STATE2);
						//procedureDetail.setName("完成任务扣取收款手续费");  //haokun modified 2017/03/09
						procedureDetail.setName("收取任务报酬手续费");  //haokun modified 2017/03/09
						procedureDetail.setCreateDate(date);
						procedureDetail.setCurrency("美元");
						procedureDetail.setModifyDate(date);
						procedureDetail.setType(Constant.ACCOUNT_DETAIL_TYPE38);
						procedureDetail.setRemark("完成编号"+tRd.getDemandId()+"任务扣取手续费");
						procedureDetail.setDemandId(demandId);
						procedureDetail.setThirdNo("");
						//发票号
						InvoiceNo procedureInvoiceNo = this.invoiceNoDao.getByType(Constant.INVOICE_TYPE7);
						if(procedureInvoiceNo != null){
							if(this.invoiceNoDao.modifyStateById(procedureInvoiceNo.getId(),date) > 0){
								procedureDetail.setInvoiceNo(procedureInvoiceNo.getInvoiceNo());
							}
						}
						
						/*手续费返还 start*/
						User rdUser = this.userDao.getOnlyUserOpenInfoById(rd.getUserId());
						Charges charges = this.chargesDao.getOne();
						if(rdUser == null||charges == null){
							throw new Exception();
						}
						String chargesPercent = "1.00";
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
						DecimalFormat    chargesDf   = new DecimalFormat("######0.00");   
						if(rdUser.getSignDate()!=null){
							Date start = df.parse(rdUser.getSignDate());
							if(getMonth(start, new Date())>3){
								//老用户
								chargesPercent = chargesDf.format(Double.valueOf(charges.getOldcustomerChargerate())/100);
							}else{
								//新用户
								chargesPercent = chargesDf.format(Double.valueOf(charges.getNewcustomerChargerate())/100);
							}
						}
						AccountDetail chargesDetail = new AccountDetail();
						if(!chargesPercent.equals("1.00")){
							chargesDetail.setAmount(String.valueOf((amount+daAmount)*Double.valueOf(procedureRatio)*(1-Double.valueOf(chargesPercent))));
							chargesDetail.setRealAmount(chargesDetail.getAmount());
							chargesDetail.setUserId(rd.getUserId());
							chargesDetail.setState(Constant.ACCOUNT_DETAIL_STATE2);
							//chargesDetail.setName("返还完成任务收款手续费");   //haokun modified 2017/03/09
							chargesDetail.setName("任务报酬手续费返还");   //haokun modified 2017/03/09				
							chargesDetail.setCreateDate(date);
							chargesDetail.setCurrency("美元");
							chargesDetail.setModifyDate(date);
							chargesDetail.setType(Constant.ACCOUNT_DETAIL_TYPE26);
							chargesDetail.setRemark("返还编号"+rd.getDemandId()+"任务扣取手续费");
							chargesDetail.setDemandId(demandId);
							chargesDetail.setThirdNo("");
							//发票号
							InvoiceNo chargesInvoiceNo = this.invoiceNoDao.getByType(Constant.INVOICE_TYPE7);
							if(chargesInvoiceNo != null){
								if(this.invoiceNoDao.modifyStateById(chargesInvoiceNo.getId(),date) > 0){
									chargesDetail.setInvoiceNo(chargesInvoiceNo.getInvoiceNo());
								}
							}
						}
						/*手续费返还 end*/
						
						Account a = accountDao.getAccountByUserId(detail.getUserId());
						double usd= StringUtil.string2Double(a.getUsd());
						double newusd = usd + StringUtil.string2Double(detail.getAmount())*(1-Double.valueOf(procedureRatio)*Double.valueOf(chargesPercent));
						//double newusd = usd + StringUtil.string2Double(detail.getAmount());
						
						if (this.accountDao.modifyUsdByUserId(a.getUserId(), String.valueOf(newusd), date) > 0) {
							// pass
						} else {
							throw new Exception();
						}
						
						this.accountDetailDao.insertAccountDetail(detail);
						this.accountDetailDao.insertAccountDetail(procedureDetail);
						/*手续费返还 start*/
						if(!chargesPercent.equals("1.00")){
							this.accountDetailDao.insertAccountDetail(chargesDetail);
						}
						/*手续费返还 end*/
						//减小冻结金额
						if(this.accountDao.reduceFreezeUsd(demand.getUserId(), String.valueOf(new BigDecimal((amount+daAmount)).setScale(2, RoundingMode.DOWN).doubleValue()), date) > 0){
							// pass
						}else{
							throw new Exception();
						}
						
						//写发单人经验值
						if(this.userDao.modifyGradeById(demand.getUserId(),detail.getAmount())>0){
							// pass
						}else{
							throw new Exception();
						}
						
						//写接单人经验值
						if(this.userDao.modifyGradeById(detail.getUserId(),detail.getAmount())>0){
							// pass
						}else{
							throw new Exception();
						}
						
						//发单人系统消息推送
						//还没给接单人发短信或邮箱提醒。
						MessageSystem ms = new MessageSystem();
						ms.setCreateDate(date);
						ms.setLevel(Constant.MESSAGESYSTEM_LEVEL_INFO);
						ms.setContent("您好! 任务'"+demand.getTitle()+"'的仲裁请求已经审理完毕，点击立即查看结果");
						//还没写配置
						ms.setLink("/dashboard/Task/pubDetail.jsp?id="+Base64Util.encode(demandId));
						ms.setModifyDate(date);
						ms.setTitle("您好! 任务"+demandId+"的仲裁请求已经审理完毕，点击立即查看结果");
						ms.setUserId(demand.getUserId());
						ms.setState(Constant.MESSAGESYSTEM_STATE_UNREAD);
						this.messageSystemService.saveMessage(ms);
						
						//接单人系统消息推送
						//还没给接单人发短信或邮箱提醒。
						MessageSystem ms0 = new MessageSystem();
						ms0.setCreateDate(date);
						ms0.setLevel(Constant.MESSAGESYSTEM_LEVEL_INFO);
						ms0.setContent("您好! 任务'"+demand.getTitle()+"'的仲裁请求已经审理完毕，点击立即查看结果，任务已经全部完成结束，获得$"+detail.getAmount()+"的奖励");
						//还没写配置
						ms0.setLink("/dashboard/Task/pubDetail.jsp?id="+Base64Util.encode(demandId));
						ms0.setModifyDate(date);
						ms0.setTitle("您好! 任务"+demandId+"的仲裁请求已经审理完毕，点击立即查看结果");
						ms0.setUserId(tRd.getUserId());
						ms0.setState(Constant.MESSAGESYSTEM_STATE_UNREAD);
						this.messageSystemService.saveMessage(ms0);
						
						//开始积分分配 
						double consumeAmount = (amount + daAmount)*(percent/100);
						//父级推荐人
						User pUser = this.userDao.getUserById(demand.getUserId());
						if(pUser!=null&&consumeAmount>0){  
							if(pUser.getRecommender()!=null
									&&!pUser.getRecommender().equals("-1")
									&&!pUser.getRecommender().equals("")){
								
								AccountDetail pdetail = new AccountDetail(); 
								pdetail.setAmount(String.valueOf(consumeAmount*bonusPointParentlevel1));
								pdetail.setRealAmount(pdetail.getAmount());
								pdetail.setUserId(pUser.getRecommender());
								pdetail.setState(Constant.ACCOUNT_DETAIL_STATE2);
								pdetail.setName("任务奖励");
								pdetail.setCreateDate(date);
								pdetail.setCurrency("美元");
								pdetail.setModifyDate(date);
								pdetail.setType(Constant.ACCOUNT_DETAIL_TYPE25);
								pdetail.setRemark("");
								pdetail.setThirdNo("");
								pdetail.setDemandId(demandId);
								InvoiceNo pin = this.invoiceNoDao.getByType(Constant.INVOICE_TYPE4);
								if(pin!=null){
									if(this.invoiceNoDao.modifyStateById(pin.getId(),date) > 0){
										pdetail.setInvoiceNo(pin.getInvoiceNo());
									}
								}
								
								Account pAccount = accountDao.getAccountByUserId(pdetail.getUserId());
								double pUsd= StringUtil.string2Double(pAccount.getUsd());
								double pNewUsd = pUsd + StringUtil.string2Double(pdetail.getAmount());
								// 账户支付，修改账户余额
								if (this.accountDao.modifyUsdByUserId(pAccount.getUserId(), String.valueOf(pNewUsd), date) > 0) {
									int pResult  = this.accountDetailDao.insertAccountDetail(pdetail);
									if(pResult > 0){
										BonusPoint pBonusPoint = new BonusPoint();
										pBonusPoint.setConsumerId(demand.getUserId());
										pBonusPoint.setCreateDate(date);
										pBonusPoint.setDemandId(demandId);
										pBonusPoint.setPoint(String.valueOf(consumeAmount*bonusPointParentlevel1));
										pBonusPoint.setSublevel(Constant.BONUSPOINT_SUBLEVEL1);
										pBonusPoint.setUserId(pUser.getRecommender());
										pBonusPoint.setRemark("邀请人任务号"+demandId+"完成，奖励金额");
										this.bonusPointDao.insertBonusPoint(pBonusPoint);
										MessageSystem pms = new MessageSystem();
										pms.setLevel(Constant.MESSAGESYSTEM_LEVEL_INFO);
										pms.setContent(pBonusPoint.getRemark());
										pms.setCreateDate(date);
										pms.setModifyDate(date);
										pms.setUserId(pBonusPoint.getUserId());
										pms.setTitle("您获得了来自任务号"+demandId+"完成的奖励金额！点击立即查看");
										pms.setLink("/dashboard/Reward/bonus_point.jsp");
										pms.setState(Constant.MESSAGESYSTEM_STATE_UNREAD);
										this.messageSystemService.saveMessage(pms);
									}
								}else{
									throw new Exception();
								}
								//this.accountDao.modifyBonusPoint(pUser.getRecommender(), String.valueOf(consumeAmount*bonusPointParentlevel1), date);
								
								/*//父级的父级推荐人
								User ppUser = this.userDao.getUserById(pUser.getRecommender());
								if(ppUser!=null&&ppUser.getRecommender()!=null
										&&!ppUser.getRecommender().equals("-1")
										&&!ppUser.getRecommender().equals("")){
									
									
									this.accountDao.modifyBonusPoint(ppUser.getRecommender(), String.valueOf(consumeAmount*bonusPointParentlevel2), date);
									BonusPoint ppBonusPoint = new BonusPoint();
									ppBonusPoint.setConsumerId(demand.getUserId());
									ppBonusPoint.setCreateDate(date);
									ppBonusPoint.setDemandId(demandId);
									ppBonusPoint.setPoint(String.valueOf(consumeAmount*bonusPointParentlevel2));
									ppBonusPoint.setSublevel(Constant.BONUSPOINT_SUBLEVEL2);
									ppBonusPoint.setUserId(ppUser.getRecommender());
									ppBonusPoint.setRemark(Constant.BONUSPOINT_SUBLEVEL2+"级邀请人任务号"+demandId+"完成，赠送积分");
									this.bonusPointDao.insertBonusPoint(ppBonusPoint);
									MessageSystem ppms = new MessageSystem();
									ppms.setLevel(Constant.MESSAGESYSTEM_LEVEL_INFO);
									ppms.setContent(ppBonusPoint.getRemark());
									ppms.setCreateDate(date);
									ppms.setModifyDate(date);
									ppms.setUserId(ppBonusPoint.getUserId());
									ppms.setTitle("您获得了来自任务号"+demandId+"完成的积分！点击立即查看");
									ppms.setLink("/dashboard/Reward/bonus_point.jsp");
									ppms.setState(Constant.MESSAGESYSTEM_STATE_UNREAD);
									this.messageSystemService.saveMessage(ppms);
									//父级的父级的父级推荐人
									User pppUser = this.userDao.getUserById(ppUser.getRecommender());
									if(pppUser!=null&&pppUser.getRecommender()!=null
											&&!pppUser.getRecommender().equals("-1")
											&&!pppUser.getRecommender().equals("")){
										
										this.accountDao.modifyBonusPoint(pppUser.getRecommender(), String.valueOf(consumeAmount*bonusPointParentlevel3), date);
										BonusPoint pppBonusPoint = new BonusPoint();
										pppBonusPoint.setConsumerId(demand.getUserId());
										pppBonusPoint.setCreateDate(date);
										pppBonusPoint.setDemandId(demandId);
										pppBonusPoint.setPoint(String.valueOf(consumeAmount*bonusPointParentlevel3));
										pppBonusPoint.setSublevel(Constant.BONUSPOINT_SUBLEVEL3);
										pppBonusPoint.setUserId(pppUser.getRecommender());
										pppBonusPoint.setRemark(Constant.BONUSPOINT_SUBLEVEL3+"级邀请人任务号"+demandId+"完成，赠送积分");
										this.bonusPointDao.insertBonusPoint(pppBonusPoint);
										
										MessageSystem pppms = new MessageSystem();
										pppms.setLevel(Constant.MESSAGESYSTEM_LEVEL_INFO);
										pppms.setContent(pppBonusPoint.getRemark());
										pppms.setCreateDate(date);
										pppms.setModifyDate(date);
										pppms.setUserId(pppBonusPoint.getUserId());
										pppms.setTitle("您获得了来自任务号"+demandId+"完成的积分！点击立即查看");
										pppms.setLink("/dashboard/Reward/bonus_point.jsp");
										pppms.setState(Constant.MESSAGESYSTEM_STATE_UNREAD);
										this.messageSystemService.saveMessage(pppms);
									}
								}*/
							}
						}
						
					}
					responseObj.setCode(ResponseCode.SUCCESS_CODE);
					return responseObj;
				}else{
					throw new Exception();
				}
			}else{
				throw new Exception();
			}
			
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException("裁决失败，请重试！", e);
		}
	}
	
	
	/*@Override
	public ResponseObject<Object> getCountPulishedDemandById(String userId)
			throws ServiceException {
		ResponseObject<Object> result = new ResponseObject<Object>();
		try {
			int nResult = this.demandDao.countByKey(userId, "");
			result.setData(nResult);
			result.setCode(ResponseCode.SUCCESS_CODE);
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
		return result;
	}
	@Override
	public ResponseObject<Object> getCountReceiveDemandById(String userId)
			throws ServiceException {
		ResponseObject<Object> result = new ResponseObject<Object>();
		try {
			int nResult = this.demandDao.countByRDReceiverId(userId,"");
			result.setData(nResult);
			result.setCode(ResponseCode.SUCCESS_CODE);
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
		return result;
	}*/
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> ResponseObject<PageSplit<T>> adminSearchByKey(String key,List<String>states, String sortType,String nationality, String type, String typeSecond, String minAmount, String maxAmount, List<String> tags, int pageSize, int pageNow)
			throws ServiceException {
		try {
			key = StringUtil.escapeStringOfSearchKey(key);  
			int rowCount = 0;
			try {
				rowCount = this.demandDao.adminCountByKey(sortType,key,states,nationality,type,typeSecond,minAmount,maxAmount, tags);
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException("获取任务个数失败", e);
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
					List<Demand> demands = this.demandDao.adminSearchByKey(sortType,key,states,nationality,type,typeSecond,minAmount, maxAmount, tags, startIndex, pageSize);
					
					if (demands != null && !demands.isEmpty()) {
						for (Demand d : demands) {
							if (d.getReceiveDemands()!=null && !d.getReceiveDemands().isEmpty()){
								for (int i=0;i<d.getReceiveDemands().size();i++){
									d.getReceiveDemands().get(i).setUser(this.userDao.getOnlyUserOpenInfoById(d.getReceiveDemands().get(i).getUserId()));
								}
							}
								
							pageSplit.addData((T)d);
						}
					}
				} catch (Exception e) {
					throw ExceptionUtil.handle2ServiceException("获取任务列表失败", e);
				}
				responseObj.setData(pageSplit);
			} else {
				responseObj.setMessage("没有任务");
			}
			return responseObj;
		} catch (ServiceException e) {
			throw e;
		}
	}
	
	@Override
	public ResponseObject<Object> modifyDemandRemarkAdmin(String id,String remarkAdmin) throws ServiceException {
		ResponseObject<Object> result = new ResponseObject<Object>();
		if (id==null||("").equals(id)) {
			result.setCode(ResponseCode.PARAMETER_ERROR);
			result.setMessage("参数无效");
		} else {
			try {
				String date = DateUtil.date2String(new Date());
				int nResult = this.demandDao.modifyRemarkAdmin(id,remarkAdmin,date);
				if(nResult>0){
					result.setCode(ResponseCode.SUCCESS_CODE);
				}
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException(e);
			}
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> ResponseObject<PageSplit<T>> searchPubByUserId(String userId,
			int pageSize, int pageNow) throws ServiceException {
		try {
			int rowCount = 0;
			try {
				rowCount = this.demandDao.countByUserId(userId);
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException("获取任务总数失败", e);
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
					List<Demand> demands = this.demandDao.searchByUserId(userId, startIndex, pageSize);
					pageSplit.setDatas((List<T>) demands);
				} catch (Exception e) {
					throw ExceptionUtil.handle2ServiceException("获取任务列表失败", e);
				}
				responseObj.setData(pageSplit);
			} else {
				responseObj.setMessage("没有任务");
			}
			return responseObj;
		} catch (ServiceException e) {
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> ResponseObject<PageSplit<T>> searchAccByUserId(String userId,
			int pageSize, int pageNow) throws ServiceException {
		try {
			int rowCount = 0;
			try {
				rowCount = this.demandDao.countRDByState8(userId);
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException("获取任务总数失败", e);
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
					List<Demand> demands = this.demandDao.searchRDByState8(userId, startIndex, pageSize);
					pageSplit.setDatas((List<T>) demands);
				} catch (Exception e) {
					throw ExceptionUtil.handle2ServiceException("获取任务列表失败", e);
				}
				responseObj.setData(pageSplit);
			} else {
				responseObj.setMessage("没有任务");
			}
			return responseObj;
		} catch (ServiceException e) {
			throw e;
		}
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
	public ResponseObject<Object> demandPayByBalance(String userId,
			String demandId, String rdId) throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>();
		try {
			Demand demand = this.demandDao.getDemandById(demandId);
			if(demand==null){
				responseObj.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
				responseObj.setMessage("任务不存在！");
				return responseObj;
			}
			if(!demand.getUserId().equals(userId)){
				responseObj.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
				responseObj.setMessage("不是您发布的任务，无法支付！");
				return responseObj;
			}
			if(demand.getPayState().equals(Constant.DEMAND_PAY_STATE1)){
				responseObj.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
				responseObj.setMessage("任务已经支付，无需再次支付！");
				return responseObj;
			}
			if(!demand.getState().equals(Constant.DEMAND_STATE_READY)&&!demand.getState().equals(Constant.DEMAND_STATE_RECEIVE)){
				if(demand.getState().equals(Constant.DEMAND_STATE_CLOSE)){
					responseObj.setMessage("任务已关闭！");
				}else{
					responseObj.setMessage("任务已匹配，无需再匹配！");
				}
				responseObj.setCode(ResponseCode.DEMAND_STATE_ERROR);
				return responseObj;
			} 
			
			
			List<ReceiveDemand> rds = demand.getReceiveDemands();
			ReceiveDemand selectRD  = null;
			if(rds!=null){
				for(ReceiveDemand rd:rds){
					if(rd.getId().equals(rdId)){
						selectRD = rd;
					}
				}
			}
			if(selectRD==null){
				responseObj.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
				responseObj.setMessage("任务没有该投标作息，请刷新页面后再操作！！");
				return responseObj;
			}
			Account a = accountDao.getAccountByUserId(userId);
			double usd= StringUtil.string2Double(a.getUsd());
			if (usd < StringUtil.string2Double(selectRD.getAmount())) {
				responseObj.setCode(ResponseCode.ACCOUNT_INSUFFICIENT_BALANCE);
				responseObj.setMessage("帐户余额不足,请充值");
				return responseObj;
			}
			
			double tempNewusd = usd - StringUtil.string2Double(selectRD.getAmount());
			BigDecimal   b   =   new   BigDecimal(tempNewusd);  
			double newusd = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			if (newusd >= 0) {
				// ignore
			} else {
				responseObj.setCode(ResponseCode.ACCOUNT_INSUFFICIENT_BALANCE);
				responseObj.setMessage("帐户余额不足,请充值");
				return responseObj;
			}
			
			String date = DateUtil.date2String(new Date());
			int nResult = this.demandDao.modifyPayState(demandId, Constant.DEMAND_STATE_SELECT, Constant.DEMAND_PAY_STATE1, date);
			int iResult = this.receiveDemandDao.modifyState(selectRD.getId(), Constant.RECEIVEDEMAND_STATE_CHECKED);
			if (nResult > 0&& iResult>0) {
				// 账户支付，修改账户余额
				if (this.accountDao.modifyUsdByUserId(a.getUserId(), String.valueOf(newusd), date) > 0) {
					if(this.accountDao.addFreezeUsd(a.getUserId(), selectRD.getAmount(), date) > 0){
						// pass
					}else{
						throw new Exception("支付失败，请重试");
					}
				} else {
					throw new Exception("支付失败，请重试");
				}
				AccountDetail detail = new AccountDetail(); 
				detail.setAmount(selectRD.getAmount());
				detail.setRealAmount(detail.getAmount());
				detail.setUserId(demand.getUserId());
				detail.setState(Constant.ACCOUNT_DETAIL_STATE2);
				//detail.setName("任务金额冻结");   haokun deleted 2017/03/27
				detail.setName("收取任务保证金");  //haokun added 2017/03/27 改为任务保证金
				detail.setCreateDate(date);
				detail.setCurrency("美元");
				detail.setModifyDate(date);
				detail.setType(Constant.ACCOUNT_DETAIL_TYPE33);
				detail.setRemark("任务号:"+demand.getId());
				detail.setDemandId(demand.getId());
				detail.setThirdNo("");
				InvoiceNo in = this.invoiceNoDao.getByType(Constant.INVOICE_TYPE1);
				if(in!=null){
					if(this.invoiceNoDao.modifyStateById(in.getId(),date) > 0){
						detail.setInvoiceNo(in.getInvoiceNo());
					}
				}
				this.accountDetailDao.insertAccountDetail(detail);
				
				//邮箱提醒 add by haokun 2017/01/26 选择接单人，增加邮件提醒
				User user = this.userDao.getUserById(selectRD.getUserId());
				if(user.getEmail()!=null&&!user.getEmail().equals("")&&user.getEmailState().equals(Constant.USER_EMAIL_STATE1)){
					String subject = "[AnyoneHelps] 任务中标";
					String content = "尊敬的用户！<br>";
					content += "您投标'"+demand.getTitle()+"'任务中标了，请尽快到任务管理的已接收任务页面开始任务，并按要求完成任务.同时对方联系方式是:";
					if(demand.getUser()!=null){
						if(demand.getUser().getEmail()!=null){
							content += "邮箱:"+demand.getUser().getEmail();
					 	}
						if(demand.getUser().getTelphone()!=null){
							content += ",手机:"+demand.getUser().getAreaCode()+demand.getUser().getTelphone();
						}
					}
					content +="<br><br>感谢您对AnyoneHelps的支持!<br>AnyoneHelps团队<br>www.anyonehelps.com<br>"; 
					content +="<img src='https://www.anyonehelps.com/assets/pages/img/index/logo-bottom.png'/>";

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
						
				//还没给接单人发短信或邮箱提醒。
				String content = "您投标'"+demand.getTitle()+"'任务中标了，请尽快到任务管理的已接收任务页面开始任务，并按要求完成任务.同时对方联系方式是:";
				if(demand.getUser()!=null){
					if(demand.getUser().getEmail()!=null){
						content += "邮箱:"+demand.getUser().getEmail();
					}
					if(demand.getUser().getTelphone()!=null){
						content += ",手机:"+demand.getUser().getAreaCode()+demand.getUser().getTelphone();
					}
				}
				MessageSystem ms = new MessageSystem();
				ms.setCreateDate(date);
				ms.setLevel(Constant.MESSAGESYSTEM_LEVEL_INFO);
				ms.setContent(content);
				//还没写配置
				ms.setLink("/dashboard/Task/accDetail.jsp?id="+Base64Util.encode(demandId));
				ms.setModifyDate(date);
				ms.setTitle("恭喜您！任务"+demandId+"匹配成功，点击查看并尽快开始任务吧！");
				ms.setUserId(selectRD.getUserId());
				ms.setState(Constant.MESSAGESYSTEM_STATE_UNREAD);
				this.messageSystemService.saveMessage(ms);
				responseObj.setCode(ResponseCode.SUCCESS_CODE);
			} else {
				// 进行事务回滚
				throw new Exception();
			}
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
		return responseObj;
	}
	@Override
	public ResponseObject<Object> demandPayByCredit(String userId,
			String demandId, String rdId, String brand, String name,
			String creditNo, String securityCode, String expireYear,
			String expireMonth, String currency, String amount)
			throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>();
		try {
			Demand demand = this.demandDao.getDemandById(demandId);
			if(demand==null){
				responseObj.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
				responseObj.setMessage("任务不存在！");
				return responseObj;
			}
			if(!demand.getUserId().equals(userId)){
				responseObj.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
				responseObj.setMessage("不是您发布的任务，无法支付！");
				return responseObj;
			}
			if(demand.getPayState().equals(Constant.DEMAND_PAY_STATE1)){
				responseObj.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
				responseObj.setMessage("任务已经支付，无需再次支付！");
				return responseObj;
			}
			if(!demand.getState().equals(Constant.DEMAND_STATE_READY)&&!demand.getState().equals(Constant.DEMAND_STATE_RECEIVE)){
				if(demand.getState().equals(Constant.DEMAND_STATE_CLOSE)){
					responseObj.setMessage("任务已关闭！");
				}else{
					responseObj.setMessage("任务已匹配，无需再匹配！");
				}
				responseObj.setCode(ResponseCode.DEMAND_STATE_ERROR);
				return responseObj;
			} 
			
			List<ReceiveDemand> rds = demand.getReceiveDemands();
			ReceiveDemand selectRD  = null;
			if(rds!=null){
				for(ReceiveDemand rd:rds){
					if(rd.getId().equals(rdId)){
						selectRD = rd;
					}
				}
			}
			if(selectRD==null){
				responseObj.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
				responseObj.setMessage("任务没有该投标作息，请刷新页面后再操作！！");
				return responseObj;
			}
			
			String procedureRatio = "";
			String procedure = "";
			try {
				Properties props = PropertiesReader.read(Constant.SYSTEM_PROPERTIES_FILE);
				procedureRatio = props.getProperty("stripe.recharge.procedure.ratio");
				procedure = props.getProperty("stripe.recharge.procedure");
			} catch (Exception e) {
				log.error("读取Stripe手续费比例和固定手续费配置错误!");
				return new ResponseObject<Object>(ResponseCode.SHOW_EXCEPTION,"系统服务忙，请稍后再充值，或者请联系客服!");
			}
			double da = Double.valueOf(amount != null ? amount.trim() : "");  //实际付款金额
			double rdAmount = Double.valueOf(selectRD.getAmount() != null ? selectRD.getAmount().trim() : "");
			double realAmount = (da-Double.valueOf(procedure))*(1-Double.valueOf(procedureRatio)); //到帐金额
			
			log.error("amount"+amount);
			log.error("rdAmount:"+rdAmount);
			log.error("realAmount:"+realAmount);
			//0.02是计算手续费允许的误差
			if(rdAmount - realAmount<0.02&&rdAmount - realAmount>-0.02){
				Charge charge = null;
				int money = (int) (da*100);
				try {
			        charge = StripeUtil.createPayMoneyByStrip(brand,name,creditNo,securityCode,expireYear,expireMonth,money,currency,demand.getId());
			        log.info("charge.getId:"+charge.getId());
			        log.info("charge:"+charge.toString());
				} catch (AuthenticationException e) {
					log.error("Authentication with Stripe's API failed！");
					return new ResponseObject<Object>(ResponseCode.SHOW_EXCEPTION,"Authentication with Stripe's API failed！");
				} catch (InvalidRequestException e) {
					e.printStackTrace();
					log.error("Invalid parameters were supplied to Stripe's API！");
					return new ResponseObject<Object>(ResponseCode.SHOW_EXCEPTION,"Invalid parameters were supplied to Stripe's API！");
				} catch (APIConnectionException e) {
					log.error("Network communication with Stripe failed！");
					return new ResponseObject<Object>(ResponseCode.SHOW_EXCEPTION,"Network communication with Stripe failed！");
				} catch (CardException e) {
					String code = e.getCode();
					if(code.equals("incorrect_number")){
						log.error("信用卡号码输入错误，请重新输入！");
						return new ResponseObject<Object>(ResponseCode.SHOW_EXCEPTION,"信用卡号码输入错误，请重新输入！");
					}else if(e.getCode().equals("invalid_number")){
						log.error("信用卡号码不是一个有效的卡号，请重新输入！");
						return new ResponseObject<Object>(ResponseCode.SHOW_EXCEPTION,"信用卡号码不是一个有效的卡号，请重新输入！");
					}else if(e.getCode().equals("invalid_expiry_month")){
						log.error("失效月错误，请重新输入！");
						return new ResponseObject<Object>(ResponseCode.SHOW_EXCEPTION,"失效月错误，请重新输入！");
					}else if(e.getCode().equals("invalid_expiry_year")){
						log.error("失效年错误，请重新输入！");
						return new ResponseObject<Object>(ResponseCode.SHOW_EXCEPTION,"失效年错误，请重新输入！");
					}else if(e.getCode().equals("invalid_cvc")){
						log.error("信用卡安全码错误，请重新输入！");
						return new ResponseObject<Object>(ResponseCode.SHOW_EXCEPTION,"信用卡安全码错误，请重新输入！");
					}else if(e.getCode().equals("expired_card")){
						log.error("信用卡就失效卡，请输入有效信用卡！");
						return new ResponseObject<Object>(ResponseCode.SHOW_EXCEPTION,"信用卡就失效卡，请输入有效信用卡！");
					}else if(e.getCode().equals("incorrect_cvc")){
						log.error("信用卡就失效卡，请输入有效信用卡！");
						return new ResponseObject<Object>(ResponseCode.SHOW_EXCEPTION,"信用卡就失效卡，请输入有效信用卡！");
					}else if(e.getCode().equals("incorrect_zip")){
						log.error("信用卡邮编错误，请输入正确邮编！");
						return new ResponseObject<Object>(ResponseCode.SHOW_EXCEPTION,"信用卡邮编错误，请输入正确邮编！");
					}else if(e.getCode().equals("card_declined")){
						log.error("There is no card on a customer that is being charged！");
						return new ResponseObject<Object>(ResponseCode.SHOW_EXCEPTION,"There is no card on a customer that is being charged！");
					
					}else if(e.getCode().equals("missing")){
						log.error("信用卡不可用！");
						return new ResponseObject<Object>(ResponseCode.SHOW_EXCEPTION,"信用卡不可用！");
					}else if(e.getCode().equals("processing_error")){
						log.error("信用卡扣款出错，请重试！");
						return new ResponseObject<Object>(ResponseCode.SHOW_EXCEPTION,"信用卡扣款出错，请重试！");
					
					}else if(e.getCode().equals("rate_limit")){
						//alert("An error occurred due to requests hitting the API too quickly. Please let us know if you're consistently running into this error.");
						log.error("您输入错误过多，此暂时不可用！");
						return new ResponseObject<Object>(ResponseCode.SHOW_EXCEPTION,"您输入错误过多，此卡暂时不可用！");
					}else{
						//alert("The card has expired.Unknown reason");
						log.error("信用卡不可用，原因未知！");
						return new ResponseObject<Object>(ResponseCode.SHOW_EXCEPTION,"信用卡不可用");
					}
					
				} catch (APIException e) {
					log.error("Display a very generic error to the user, and maybe send！");
					return new ResponseObject<Object>(ResponseCode.SHOW_EXCEPTION,"Display a very generic error to the user, and maybe send！");
				}catch (Exception e) {
					log.error("支付错误，原因未知！");
					return new ResponseObject<Object>(ResponseCode.SHOW_EXCEPTION,"支付错误！");
				}
				String date = DateUtil.date2String(new Date());
				int nResult = this.demandDao.modifyPayState(demandId, Constant.DEMAND_STATE_SELECT, Constant.DEMAND_PAY_STATE1, date);
				int iResult = this.receiveDemandDao.modifyState(selectRD.getId(), Constant.RECEIVEDEMAND_STATE_CHECKED);
				if (nResult > 0&& iResult>0) {
					//修改账户冻结金额
					if(this.accountDao.addFreezeUsd(userId, selectRD.getAmount(), date) > 0){
						// pass
					}else{
						throw new Exception("支付失败，请重试");
					}
					AccountDetail detail = new AccountDetail(); 
					detail.setAmount(selectRD.getAmount());
					detail.setRealAmount(detail.getAmount());
					detail.setUserId(demand.getUserId());
					detail.setState(Constant.ACCOUNT_DETAIL_STATE2);
					//detail.setName("任务金额冻结");   haokun deleted 2017/03/27
					detail.setName("收取任务保证金");   //haokun added 2017/03/27 改为任务保证金
					detail.setCreateDate(date);
					detail.setCurrency("美元");
					detail.setModifyDate(date);
					detail.setType(Constant.ACCOUNT_DETAIL_TYPE33);
					detail.setRemark("任务号:"+demand.getId());
					detail.setDemandId(demand.getId());
					detail.setThirdNo("");
					InvoiceNo in = this.invoiceNoDao.getByType(Constant.INVOICE_TYPE1);
					if(in!=null){
						if(this.invoiceNoDao.modifyStateById(in.getId(),date) > 0){
							detail.setInvoiceNo(in.getInvoiceNo());
						}
					}
					
					
					AccountDetail rechargeDetail = new AccountDetail();
					
					rechargeDetail.setAmount(String.valueOf(realAmount));
					//detail.setAmount(String.valueOf(da));
					rechargeDetail.setRealAmount(rechargeDetail.getAmount());
					rechargeDetail.setUserId(userId);
					rechargeDetail.setCreateDate(date);
					rechargeDetail.setModifyDate(date);
					rechargeDetail.setRemark(charge.getId());
					rechargeDetail.setCurrency("美元");
					rechargeDetail.setName("信用卡充值");
					rechargeDetail.setState(Constant.ACCOUNT_DETAIL_STATE2);
					rechargeDetail.setType(Constant.ACCOUNT_DETAIL_TYPE11);
					rechargeDetail.setDemandId(null);
					rechargeDetail.setThirdNo(charge.getId());
					
					this.accountDetailDao.insertAccountDetail(rechargeDetail);
					this.accountDetailDao.insertAccountDetail(detail);
					
					//邮箱提醒 add by haokun 2017/01/26 选择接单人，增加邮件提醒
					User user = this.userDao.getUserById(selectRD.getUserId());
					if(user.getEmail()!=null&&!user.getEmail().equals("")&&user.getEmailState().equals(Constant.USER_EMAIL_STATE1)){
						String subject = "[AnyoneHelps] 任务中标";
						String content = "尊敬的用户！<br>";
						content += "您投标'"+demand.getTitle()+"'任务中标了，请尽快到任务管理的已接收任务页面开始任务，并按要求完成任务.同时对方联系方式是:";
						if(demand.getUser()!=null){
							if(demand.getUser().getEmail()!=null){
								content += "邮箱:"+demand.getUser().getEmail();
						 	}
							if(demand.getUser().getTelphone()!=null){
								content += ",手机:"+demand.getUser().getAreaCode()+demand.getUser().getTelphone();
							}
						}
						content +="<br><br>感谢您对AnyoneHelps的支持!<br>AnyoneHelps团队<br>www.anyonehelps.com<br>"; 
						content +="<img src='https://www.anyonehelps.com/assets/pages/img/index/logo-bottom.png'/>";

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
							
					//还没给接单人发短信或邮箱提醒。
					String content = "您投标'"+demand.getTitle()+"'任务中标了，请尽快到任务管理的已接收任务页面开始任务，并按要求完成任务.同时对方联系方式是:";
					if(demand.getUser()!=null){
						if(demand.getUser().getEmail()!=null){
							content += "邮箱:"+demand.getUser().getEmail();
						}
						if(demand.getUser().getTelphone()!=null){
							content += ",手机:"+demand.getUser().getAreaCode()+demand.getUser().getTelphone();
						}
					}
					MessageSystem ms = new MessageSystem();
					ms.setCreateDate(date);
					ms.setLevel(Constant.MESSAGESYSTEM_LEVEL_INFO);
					ms.setContent(content);
					//还没写配置
					ms.setLink("/dashboard/Task/accDetail.jsp?id="+Base64Util.encode(demandId));
					ms.setModifyDate(date);
					ms.setTitle("恭喜您！任务"+demandId+"匹配成功，点击查看并尽快开始任务吧！");
					ms.setUserId(selectRD.getUserId());
					ms.setState(Constant.MESSAGESYSTEM_STATE_UNREAD);
					this.messageSystemService.saveMessage(ms);
					responseObj.setCode(ResponseCode.SUCCESS_CODE);
				} else {
					// 进行事务回滚
					throw new Exception();
				}
				
				
				
			}else{
				log.error("任务付款金额不正确!");
				return new ResponseObject<Object>(ResponseCode.SHOW_EXCEPTION,"付款失败，请联系客服!");
			}

		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
		return responseObj;
	}
	

}
