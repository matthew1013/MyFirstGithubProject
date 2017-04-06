package com.anyonehelps.service.impl;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anyonehelps.common.constants.Constant;
import com.anyonehelps.common.constants.ResponseCode;
import com.anyonehelps.common.util.Base64Util;
import com.anyonehelps.common.util.DateUtil;
import com.anyonehelps.common.util.DemandAttachUtil;
import com.anyonehelps.common.util.ExceptionUtil;
import com.anyonehelps.common.util.PropertiesReader;
import com.anyonehelps.common.util.StringUtil;
import com.anyonehelps.common.util.sms.SmsConfigUtil;
import com.anyonehelps.dao.AccountDao;
import com.anyonehelps.dao.AccountDetailDao;
import com.anyonehelps.dao.DemandAttachDao;
import com.anyonehelps.dao.DemandDao;
import com.anyonehelps.dao.EmailSendDao;
import com.anyonehelps.dao.InvoiceNoDao;
import com.anyonehelps.dao.SmsSendDao;
import com.anyonehelps.dao.UserDao;
import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.Account;
import com.anyonehelps.model.AccountDetail;
import com.anyonehelps.model.Demand;
import com.anyonehelps.model.DemandAttach;
import com.anyonehelps.model.EmailSend;
import com.anyonehelps.model.InvoiceNo;
import com.anyonehelps.model.MessageSystem;
import com.anyonehelps.model.ReceiveDemand;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.model.SmsSend;
import com.anyonehelps.model.User;
import com.anyonehelps.service.DemandAttachService;
import com.anyonehelps.service.MessageSystemService;

@Service("demandAttachService")
public class DemandAttachServiceImpl extends BasicService implements DemandAttachService {
	@Autowired
	private DemandAttachDao demandAttachDao;
	@Autowired
	private DemandDao demandDao;
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private AccountDetailDao accountDetailDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private InvoiceNoDao invoiceNoDao;
	@Autowired
	private EmailSendDao emailSendDao;
	@Autowired
	private SmsSendDao smsSendDao;
	
	@Resource(name = "messageSystemService")
	private MessageSystemService messageSystemService;

	@Override
	public ResponseObject<Object> addDemandAttach(DemandAttach demandAttach)
			throws ServiceException {
		if(demandAttach==null){
			return new ResponseObject<Object>(ResponseCode.PARAMETER_ERROR,"参数无效");
		}
		if(!DemandAttachUtil.validateUserId(demandAttach.getUserId())||
				!DemandAttachUtil.validateDemandId(demandAttach.getDemandId())||
				!DemandAttachUtil.validateAmount(demandAttach.getAmount())||
				!DemandAttachUtil.isValidDateTime(demandAttach.getExpireDate())
				){
			return new ResponseObject<Object>(ResponseCode.PARAMETER_ERROR,"参数无效");
		}
		ResponseObject<Object> responseObj = new ResponseObject<Object>();
		try {
			Account a = accountDao.getAccountByUserId(demandAttach.getUserId());
			double usd= StringUtil.string2Double(a.getUsd());
			double newusd = usd - StringUtil.string2Double(demandAttach.getAmount());
			if (newusd >= 0) {
				// ignore
			} else {
				return new ResponseObject<Object>( ResponseCode.ACCOUNT_INSUFFICIENT_BALANCE, "帐户余额不足,请充值");
			}
			Demand demand = this.demandDao.getDemandById(demandAttach.getDemandId());
			if(demand==null){
				return new ResponseObject<Object>(ResponseCode.DEMAND_NOT_EXISTS,"增加附加任务失败,主任务不存在!");
			}
			if(!Constant.DEMAND_STATE_START.equals(demand.getState())&&
					!Constant.DEMAND_STATE_FINISH.equals(demand.getState())){
				return new ResponseObject<Object>(ResponseCode.DEMANDATTACH_DELETE_ERROR,"附加任任务只能在开始后，支付前增加!");
			}
			
			String date = DateUtil.date2String(new Date());
			demandAttach.setModifyDate(date);
			demandAttach.setCreateDate(date);
			int nResult = this.demandAttachDao.insertDemandAttach(demandAttach);
			if(nResult>0){
				// 账户支付，修改账户余额
				if (this.accountDao.modifyUsdByUserId(a.getUserId(), String.valueOf(newusd), date) > 0) {
					if(this.accountDao.addFreezeUsd(a.getUserId(), demandAttach.getAmount(), date) > 0){
						// pass
					}else{
						throw new Exception();
					}
				} else {
					throw new Exception();
				}
				AccountDetail detail = new AccountDetail(); 
				detail.setAmount(demandAttach.getAmount());
				detail.setRealAmount(detail.getAmount());
				detail.setUserId(demandAttach.getUserId());
				detail.setState(Constant.ACCOUNT_DETAIL_STATE2);
				//detail.setName("附加任务金额冻结");   haokun deleted 2017/03/27
				detail.setName("收取任务保证金（附加任务）");   //haokun added 2017/03/27 把名字改成“任务保证金”
				detail.setCreateDate(date);
				detail.setCurrency("美元");
				detail.setModifyDate(date);
				detail.setType(Constant.ACCOUNT_DETAIL_TYPE36);
				detail.setRemark("任务号:"+demandAttach.getDemandId()+",附加任务号:"+demandAttach.getId());
				detail.setDemandId(demandAttach.getDemandId());
				detail.setThirdNo("");
				InvoiceNo in = this.invoiceNoDao.getByType(Constant.INVOICE_TYPE1);
				if(in!=null){
					if(this.invoiceNoDao.modifyStateById(in.getId(),date) > 0){
						detail.setInvoiceNo(in.getInvoiceNo());
					}
				}
				
				this.accountDetailDao.insertAccountDetail(detail);
				//查找投标人,并且发邮件或者短信
				List<ReceiveDemand> rds = demand.getReceiveDemands();
				if(rds!=null&&rds.size()>0){
					for(int k=0;k<rds.size();k++){
						if(Constant.RECEIVEDEMAND_STATE_UNCHECKED.equals(rds.get(k).getState())){
							continue;
						}
						User user = this.userDao.getUserById(rds.get(k).getUserId());
						if(user==null){
							continue;
						}            
						// 这里还没做配置文件
						String url = "www.anyonehelps.com/task/detail.jsp?id="+Base64Util.encode(demand.getId());
						if(user.getEmail()!=null&&!user.getEmail().equals("")&&user.getEmailState().equals(Constant.USER_EMAIL_STATE1)){
							Properties prop = PropertiesReader.read(Constant.EMAIL_PROPERTIES_FILE);
							String subject = prop.getProperty("anyonehelps.attach.task.subject");
							subject = MessageFormat.format(subject, new Object[] { user.getNickName()});     //haokun modified 2017/02/10
							String content = prop.getProperty("anyonehelps.attach.task.content");
							content = MessageFormat.format(content, new Object[] { user.getNickName(), demand.getTitle(),demandAttach.getAmount(), url});   //haokun modified 2017/02/10
							
							EmailSend es = new EmailSend();
							es.setContent(content);
							es.setCreateDate(date);
							es.setEmail(user.getEmail());
							es.setFailCount(Constant.EMAILSEND_FAIL_COUNT0);
							es.setModifyDate(date);
							es.setState(Constant.EMAILSEND_STATE0);
							es.setSubject(subject);
							es.setUserId(demand.getUserId());
							emailSendDao.insert(es);
						}
						if(user.getTelphone()!=null&&!user.getTelphone().equals("")){
							//SmsSendUtil.sendAddDemandAttachMsg(user.getNickName(),demand.getTitle(), url, user.getAreaCode(), user.getTelphone(), demandAttach.getAmount());
							Properties prop = null;
							String content = null;
							if(user.getAreaCode().equals("+86")||user.getAreaCode().equals("0086")){
								prop = PropertiesReader.read(SmsConfigUtil.getSmsConfigFile(Constant.COUNTRY_CODE_CN));
								content = prop.getProperty("anyonehelps.task.attach.content");
								content = MessageFormat.format(content, new Object[] {user.getNickName(), demand.getTitle(), url});
							}else{ 
								prop = PropertiesReader.read(SmsConfigUtil.getSmsConfigFile(Constant.COUNTRY_CODE_US));
								content = prop.getProperty("anyonehelps.task.attach.content");
								content = MessageFormat.format(content, new Object[] {user.getNickName(), demand.getTitle(), url});
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
						//给接单人发系统消息
						MessageSystem ms = new MessageSystem();
						ms.setLevel(Constant.MESSAGESYSTEM_LEVEL_INFO);
						ms.setContent("您接受的任务("+demand.getTitle()+"，发单人发起了附加任务，请您快去接受或者拒绝");
						ms.setCreateDate(date);
						ms.setModifyDate(date);
						ms.setUserId(user.getId());
						ms.setTitle("编号为"+demand.getId()+"任务的发单人向您发起了任务，请您快去接受或者拒绝");
						ms.setLink("/dashboard/Task/accDetail.jsp?id="+Base64Util.encode(demand.getId()));
						ms.setState(Constant.MESSAGESYSTEM_STATE_UNREAD);
						this.messageSystemService.saveMessage(ms);
						
					} 
				}
				
				responseObj.setCode(ResponseCode.SUCCESS_CODE);
				responseObj.setData(demandAttach);
			}else{
				responseObj.setCode(ResponseCode.DEMANDATTACH_INSTER_ERROR);
				responseObj.setMessage("添加附加任务失败,请重试");
			}
			
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException("添加附加任务失败", e);
		}
		return responseObj;
	}

	@Override
	public ResponseObject<DemandAttach> searchById(String id)
			throws ServiceException {
		ResponseObject<DemandAttach> responseObj = new ResponseObject<DemandAttach>(ResponseCode.SUCCESS_CODE);
		try {
			DemandAttach demandAttach = this.demandAttachDao.getById(id);
			responseObj.setData(demandAttach);
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException("获取附加任务失败", e);
		}
		return responseObj;
	}

	@Override
	public ResponseObject<Object> deleteDemandAttach(String userId, String id)
			throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>(ResponseCode.SUCCESS_CODE);
		if (id == null || id.equals("")||userId == null || userId.equals("")) {
			responseObj.setCode(ResponseCode.PARAMETER_ERROR);
			responseObj.setMessage("参数无效");
			return responseObj;
		}
		try {
			String date = DateUtil.date2String(new Date());
			DemandAttach da = this.demandAttachDao.getById(id);
			if(da==null){
				return new ResponseObject<Object>(ResponseCode.DEMANDATTACH_NOT_EXISTS,"删除失败,该附加任务不存在!");
			}
			if(Constant.DEMAND_ATTACH_STATE1.equals(da.getState())){
				return new ResponseObject<Object>(ResponseCode.DEMANDATTACH_DELETE_ERROR,"对方已经接受附加任务，无法删除!");
			}
			if(Constant.DEMAND_ATTACH_STATE2.equals(da.getState())){
				return new ResponseObject<Object>(ResponseCode.DEMANDATTACH_DELETE_ERROR,"对方已经拒绝附加任务，无法删除!");
			}
			if(!da.getUserId().equals(userId)){
				return new ResponseObject<Object>(ResponseCode.DEMANDATTACH_NOT_EXISTS,"不能删除不属于您的附加任务!");
			}
			Demand demand = this.demandDao.getDemandById(da.getDemandId());
			if(demand==null){
				return new ResponseObject<Object>(ResponseCode.DEMAND_NOT_EXISTS,"删除失败,该任务不存在!");
			}
			if(demand.getState()==Constant.DEMAND_STATE_CLOSE||
					demand.getState()==Constant.DEMAND_STATE_PAY||
					demand.getState()==Constant.DEMAND_STATE_ARBITRATION||
					demand.getState()==Constant.DEMAND_STATE_END){
				return new ResponseObject<Object>(ResponseCode.DEMANDATTACH_DELETE_ERROR,"删除失败,该任务已经完成或者关闭!");
			}
			int nResult = this.demandAttachDao.deleteById(id);
			if(nResult>0){
				AccountDetail detail = new AccountDetail(); 
				detail.setAmount(da.getAmount());
				detail.setRealAmount(detail.getAmount());
				detail.setUserId(da.getUserId());
				detail.setState(Constant.ACCOUNT_DETAIL_STATE2);
				//detail.setName("附加任务金额冲正");  haokun deleted 2017/03/27
				detail.setName("返还任务保证金（附加任务）");  //haokun add 2017/03/27 改为任务保证金
				detail.setCreateDate(date);
				detail.setCurrency("美元");
				detail.setModifyDate(date);
				detail.setType(Constant.ACCOUNT_DETAIL_TYPE19);
				detail.setRemark("任务号:"+da.getDemandId()+"下的附加任务号:"+da.getId()+"删除");
				detail.setDemandId(da.getDemandId());
				detail.setThirdNo("");
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
				if (this.accountDao.modifyUsdByUserId(a.getUserId(), String.valueOf(newusd), date) > 0) {
					if(this.accountDao.reduceFreezeUsd(a.getUserId(), da.getAmount(), date) > 0){
						// pass
					}else{
						throw new Exception();
					}
				} else {
					throw new Exception();
				} 
				this.accountDetailDao.insertAccountDetail(detail);
				responseObj.setCode(ResponseCode.SUCCESS_CODE);
			}else{
				responseObj.setCode(ResponseCode.DEMANDATTACH_DELETE_ERROR);
				responseObj.setMessage("删除附加任务失败,请重试");
			}
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException("删除附加任务失败", e);
		}
		return responseObj;
	}

	@Override
	public ResponseObject<Object> modifyState(String userId, String id,
			String state) throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>(ResponseCode.SUCCESS_CODE);
		if (id == null || id.equals("")||userId == null || userId.equals("")) {
			responseObj.setCode(ResponseCode.PARAMETER_ERROR);
			responseObj.setMessage("参数无效");
			return responseObj;
		}
		try {
			String date = DateUtil.date2String(new Date()); 
			DemandAttach da = this.demandAttachDao.getById(id);
			if(da==null){
				return new ResponseObject<Object>(ResponseCode.DEMANDATTACH_NOT_EXISTS,"附加任务不存在!");
			}
			if(Constant.DEMAND_ATTACH_STATE1.equals(da.getState())){
				return new ResponseObject<Object>(ResponseCode.DEMANDATTACH_MODIFY_ERROR,"附加任务已接受!");
			}
			if(Constant.DEMAND_ATTACH_STATE2.equals(da.getState())){
				return new ResponseObject<Object>(ResponseCode.DEMANDATTACH_MODIFY_ERROR,"附加任务已拒绝!");
			}
			Demand demand = this.demandDao.getDemandById(da.getDemandId());
			if(demand==null){
				return new ResponseObject<Object>(ResponseCode.DEMAND_NOT_EXISTS,"主任务不存在!");
			}
			boolean isUser = false;
			List<ReceiveDemand> rds = demand.getReceiveDemands();
			if(rds!=null){
				for(ReceiveDemand rd:rds){
					if(userId.equals(rd.getUserId())){
						isUser = true;
						break;
					}
				}
			}
			if(!isUser){
				return new ResponseObject<Object>(ResponseCode.DEMANDATTACH_MODIFY_ERROR,"主任务不是您接受的，请刷新页面再操作!");
			}
			if(demand.getState()==Constant.DEMAND_STATE_CLOSE||
					demand.getState()==Constant.DEMAND_STATE_PAY||
					demand.getState()==Constant.DEMAND_STATE_ARBITRATION||
					demand.getState()==Constant.DEMAND_STATE_END){
				return new ResponseObject<Object>(ResponseCode.DEMANDATTACH_MODIFY_ERROR,"主任务已经完成或者关闭!");
			}
			int nResult = this.demandAttachDao.modifyState(id, state, date);
			if(nResult>0){
				if(Constant.DEMAND_ATTACH_STATE2.equals(state)){
					AccountDetail detail = new AccountDetail(); 
					detail.setAmount(da.getAmount());
					detail.setRealAmount(detail.getAmount());
					detail.setUserId(da.getUserId());
					detail.setState(Constant.ACCOUNT_DETAIL_STATE2);
					//detail.setName("附加任务金额冲正");   haokun deleted 2017/03/27
					detail.setName("返还任务保证金（附加任务）");  //haokun added 2017/03/27 改为任务保证金
					detail.setCreateDate(date);
					detail.setCurrency("美元");
					detail.setModifyDate(date);
					detail.setType(Constant.ACCOUNT_DETAIL_TYPE19);
					detail.setRemark("对方拒绝主任务编号:"+da.getDemandId()+"下的附加任务");
					detail.setDemandId(da.getDemandId());
					detail.setThirdNo("");
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
					if (this.accountDao.modifyUsdByUserId(a.getUserId(), String.valueOf(newusd), date) > 0) {
						if(this.accountDao.reduceFreezeUsd(a.getUserId(), da.getAmount(), date) > 0){
							// pass
						}else{
							throw new Exception();
						}
					} else {
						throw new Exception();
					} 
					this.accountDetailDao.insertAccountDetail(detail);
					
				}
				//给发单人发系统消息
				MessageSystem ms = new MessageSystem();
				ms.setLevel(Constant.MESSAGESYSTEM_LEVEL_INFO);
				if(Constant.DEMAND_ATTACH_STATE1.equals(state)){
					ms.setContent("接单人同意你发起任务编号("+demand.getTitle()+")的附加任务");
					ms.setTitle("接单人同意你发起的附加任务，请快去查看");
				}
				if(Constant.DEMAND_ATTACH_STATE2.equals(state)){
					ms.setContent("接单人拒绝你发起的附加任务，请快去查看");
					ms.setTitle("接单人拒绝你发起的附加任务，请快去查看");
				}
				ms.setCreateDate(date);
				ms.setModifyDate(date);
				ms.setUserId(demand.getUserId());
				ms.setLink("/dashboard/Task/pubDetail.jsp?id="+Base64Util.encode(demand.getId()));
				ms.setState(Constant.MESSAGESYSTEM_STATE_UNREAD);
				this.messageSystemService.saveMessage(ms);
				
				//邮箱提醒 add by haokun 2017/01/26 附加任务被接单人拒绝或接受，增加邮件提醒
				User user = this.userDao.getUserById(demand.getUserId());
				if(user.getEmail()!=null&&!user.getEmail().equals("")&&user.getEmailState().equals(Constant.USER_EMAIL_STATE1)){
					String subject = "", content="";
					if(Constant.DEMAND_ATTACH_STATE1.equals(state)){
					   subject = "[AnyoneHelps] 附加任务被接受";
					   content = "尊敬的用户"+demand.getUser().getNickName()+",<br>";
					   content += "您发起的任务（"+demand.getTitle()+"）的附加任务已被接单人接受。<a href='www.anyonehelps.com/task/detail.jsp?id="+Base64Util.encode(demand.getId())+"'>点击立即查看</a><br>";
					   content +="<br><br>感谢您对AnyoneHelps的支持!<br>AnyoneHelps团队<br>www.anyonehelps.com<br>";  /*haokun add*/
						content +="<img src='https://www.anyonehelps.com/assets/pages/img/index/logo-bottom.png'/>";/*haokun add*/

					}
					if(Constant.DEMAND_ATTACH_STATE2.equals(state)){
					   subject = "[AnyoneHelps] 附加任务被拒绝";
					   content = "尊敬的用户"+demand.getUser().getNickName()+",<br>";
					   content += "您发起的任务（"+demand.getTitle()+"）的附加任务已被接单人拒绝。<a href='www.anyonehelps.com/task/detail.jsp?id="+Base64Util.encode(demand.getId())+"'>点击立即查看</a><br>";
					   content +="<br><br>感谢您对AnyoneHelps的支持!<br>AnyoneHelps团队<br>www.anyonehelps.com<br>";  /*haokun add*/
						content +="<img src='https://www.anyonehelps.com/assets/pages/img/index/logo-bottom.png'/>";/*haokun add*/

						  
					}
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
				
				responseObj.setCode(ResponseCode.SUCCESS_CODE);
			}else{
				responseObj.setCode(ResponseCode.DEMANDATTACH_MODIFY_ERROR);
				responseObj.setMessage("请求失败,请重试");
			}
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException("请求失败,请重试!", e);
		}
		return responseObj;
		
	}
}
