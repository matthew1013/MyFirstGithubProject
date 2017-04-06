package com.anyonehelps.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import jxl.common.Logger;
import net.authorize.api.contract.v1.CreateTransactionResponse;
import net.authorize.api.contract.v1.MessageTypeEnum;
import net.authorize.api.contract.v1.TransactionResponse;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.anyonehelps.common.util.aent.AentUtil;
import com.anyonehelps.common.util.paypal.PaypalConfig;
import com.anyonehelps.common.util.paypal.PaypalResultMode;
import com.anyonehelps.common.util.paypal.PaypalUtil;
import com.anyonehelps.common.util.sms.MailSendUtil;
import com.anyonehelps.common.util.stripe.StripeUtil;
import com.anyonehelps.dao.AccountDao;
import com.anyonehelps.dao.AccountDetailDao;
import com.anyonehelps.dao.DemandDao;
import com.anyonehelps.dao.EmailSendDao;
import com.anyonehelps.dao.InvoiceNoDao;
import com.anyonehelps.dao.ReceiveDemandDao;
import com.anyonehelps.dao.UserDao;
import com.anyonehelps.dao.WithdrawalsDao;
import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.Account;
import com.anyonehelps.model.AccountDetail;
import com.anyonehelps.model.Demand;
import com.anyonehelps.model.EmailSend;
import com.anyonehelps.model.InvoiceNo;
import com.anyonehelps.model.MessageSystem;
import com.anyonehelps.model.PageSplit;
import com.anyonehelps.model.ReceiveDemand;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.model.User;
import com.anyonehelps.model.Withdrawals;
import com.anyonehelps.service.AccountService;
import com.anyonehelps.service.MessageSystemService;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;

@Service("accountService")
public class AccountServiceImpl implements AccountService {
	private static final Logger log = Logger.getLogger(AccountServiceImpl.class);
	
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private AccountDetailDao accountDetailDao;
	@Autowired
	private WithdrawalsDao withdrawalsDao;
	@Autowired
	private InvoiceNoDao invoiceNoDao;
	@Autowired
	private EmailSendDao emailSendDao;
	
	@Autowired
	private DemandDao demandDao; 

	@Autowired
	private ReceiveDemandDao receiveDemandDao;
	@Resource(name = "messageSystemService")
	private MessageSystemService messageSystemService;
	
	public ResponseObject<Object> addAccountDetail(AccountDetail detail) throws ServiceException {
		try {
			String date = DateUtil.date2String(new Date());
			detail.setCreateDate(date);
			detail.setModifyDate(date);
			int i = this.accountDetailDao.insertAccountDetail(detail);
			if (i > 0) {
				ResponseObject<Object> result = new ResponseObject<Object>(ResponseCode.SUCCESS_CODE);
				Map<String, String> map = new HashMap<String, String>();
				map.put("id", detail.getId());
				result.setData(map);
				return result;
			} else {
				return new ResponseObject<Object>(ResponseCode.ACCOUNT_INSERT_FAILURE, "插入数据库失败");
			}
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
	}
	
	public ResponseObject<PageSplit<AccountDetail>> search(String userId, String key,
	        String state, List<String> types, String demandId, String sdate, String edate, int pageSize, int pageNow) throws ServiceException {
		try {
			key = StringUtil.escapeStringOfSearchKey(key);
			state = StringUtil.isEmpty(state) ? null : state.trim();
			userId = StringUtil.isEmpty(userId) ? null : userId.trim();
			int rowCount = 0;
			try {
				rowCount = this.accountDetailDao.countByKey(userId, key, state, types, demandId, sdate, edate);
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException("获取财务记录总数失败", e);
			}

			ResponseObject<PageSplit<AccountDetail>> responseObj = new ResponseObject<PageSplit<AccountDetail>>(
			        ResponseCode.SUCCESS_CODE);
			if (rowCount > 0) {
				pageSize = Math.max(pageSize, 1);
				int pageCount = rowCount / pageSize + (rowCount % pageSize == 0 ? 0 : 1);
				pageNow = Math.min(pageNow, pageCount);
				PageSplit<AccountDetail> pageSplit = new PageSplit<AccountDetail>();
				pageSplit.setPageCount(pageCount);
				pageSplit.setPageNow(pageNow);
				pageSplit.setRowCount(rowCount);
				pageSplit.setPageSize(pageSize);

				int startIndex = (pageNow - 1) * pageSize;
				try {
					List<AccountDetail> details = this.accountDetailDao.searchByKey(userId, key, state, types, demandId, sdate, edate,
					        startIndex, pageSize);
					if (details != null && !details.isEmpty()) {
						for (AccountDetail detail : details) {
							pageSplit.addData(detail);
						}
					}
				} catch (Exception e) {
					throw ExceptionUtil.handle2ServiceException("获取财务记录列表失败", e);
				}
				responseObj.setData(pageSplit);
			} else {
				responseObj.setMessage("没有财务记录");
			}
			return responseObj;
		} catch (ServiceException e) {
			throw e;
		}
	}

	public ResponseObject<AccountDetail> getAccountDetailById(String id) throws ServiceException {
		if (StringUtil.isEmpty(id)) {
			return new ResponseObject<AccountDetail>(ResponseCode.PARAMETER_ERROR, "参数无效");
		}

		try {
			AccountDetail detail = this.accountDetailDao.getById(id);
			if (detail != null) {
				detail.getUser().setPassword(null);
				ResponseObject<AccountDetail> result = new ResponseObject<AccountDetail>(ResponseCode.SUCCESS_CODE);
				result.setData(detail);
				return result;
			} else {
				return new ResponseObject<AccountDetail>(ResponseCode.PARAMETER_ERROR, "财务记录不存在！");
			}
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
	}
	
	

	/*public ResponseObject<Object> modifyAccountDetail(AccountDetail detail) throws ServiceException {
		if (Constant.ACCOUNT_DETAIL_STATE1.equals(detail.getState())) {
			return new ResponseObject<Object>(ResponseCode.PARAMETER_ERROR, "参数无效");
		}

		try {
			String date = DateUtil.date2String(new Date());
			detail.setModifyDate(date);
			// modify the account detail state
			int i = this.accountDetailDao.modifyAccountDetail(detail);
			if (StringUtil.isEmpty(detail.getRealAmount())) {
				detail.setRealAmount(detail.getAmount());
			}
			if (i > 0) {
				if (Constant.ACCOUNT_DETAIL_STATE2.equals(detail.getState())) {
					String userId = detail.getUserId();
					Account account = new Account();
					account.setUserId(userId);
					if (Constant.ACCOUNT_DETAIL_TYPE11.equals(detail.getType())) {
						account.setUsd(detail.getRealAmount());
					}else {
						throw new Exception();
					}
					account.setModifyDate(date);
					int k = this.accountDao.insertOrUpdateAccount(account);
					if (k > 0) {
						// pass
					} else {
						throw new Exception();
					}
				}
				return new ResponseObject<Object>(ResponseCode.SUCCESS_CODE);
			} else {
				return new ResponseObject<Object>(ResponseCode.PARAMETER_ERROR, "没有对应的消费详情或者是该操作以及执行过了");
			}
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
	}*/
	
	/*public ResponseObject<Object> procedure(AccountDetail detail) throws ServiceException {
		try {
			String date = DateUtil.date2String(new Date());
			detail.setModifyDate(date);
			Account a = accountDao.getAccountByUserId(detail.getUserId());
			double usd= StringUtil.string2Double(a.getUsd());
			double newusd = usd - StringUtil.string2Double(detail.getAmount());
			// 账户支付，修改账户余额
			if (this.accountDao.modifyUsdByUserId(a.getUserId(), String.valueOf(newusd), date) > 0) {
				// pass
			} else {
				throw new Exception();
			}
			this.accountDetailDao.insertAccountDetail(detail);
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
		return new ResponseObject<Object>(ResponseCode.SUCCESS_CODE);
	}*/
	
	/*public ResponseObject<Object> consume(AccountDetail detail) throws ServiceException {
		try {
			String date = DateUtil.date2String(new Date());
			detail.setModifyDate(date);
			Account a = accountDao.getAccountByUserId(detail.getUserId());
			//只是消息美元
			double usd= StringUtil.string2Double(a.getUsd());
			double newusd = usd - StringUtil.string2Double(detail.getAmount());
			if (newusd >= 0) {
				// ignore
			} else {
				return new ResponseObject<Object>( ResponseCode.ACCOUNT_INSUFFICIENT_BALANCE, "帐户余额不足,请充值");
			}
			// 账户支付，修改账户余额
			if (this.accountDao.modifyUsdByUserId(a.getUserId(), String.valueOf(newusd), date) > 0) {
				// pass
			} else {
				throw new Exception();
			}
			this.accountDetailDao.insertAccountDetail(detail);
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
		return new ResponseObject<Object>(ResponseCode.SUCCESS_CODE);
	}*/
	
	/*public ResponseObject<Object> insertAccountDetail(AccountDetail detail) throws ServiceException {
		try {
			String date = DateUtil.date2String(new Date());
			detail.setModifyDate(date);
			// modify the account detail state
			int i = this.accountDetailDao.insertAccountDetail(detail);
			if (StringUtil.isEmpty(detail.getRealAmount())) {
				detail.setRealAmount(detail.getAmount());
			}
			
			if (i > 0) {
				if (Constant.ACCOUNT_DETAIL_STATE2.equals(detail.getState())) {
					String userId = detail.getUserId();
					Account account = new Account();
					account.setUserId(userId);
					if (Constant.ACCOUNT_DETAIL_TYPE11.equals(detail.getType())||Constant.ACCOUNT_DETAIL_TYPE14.equals(detail.getType())) {
						// 信用卡充值 或者paypal充值
						account.setUsd(detail.getRealAmount());
					} else {
						throw new Exception();
					}
					account.setModifyDate(date);
					int k = this.accountDao.insertOrUpdateAccount(account);
					if (k > 0) {
						// pass
					} else {
						throw new Exception();
					}
				}
				return new ResponseObject<Object>(ResponseCode.SUCCESS_CODE);
			} else {
				return new ResponseObject<Object>(ResponseCode.SHOW_EXCEPTION, "已经充值成功，但记录写入数据库失败，请联系客服，修改充值记录!");
			}
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
	}*/



	public ResponseObject<Account> getAccountByUserId(String userId) throws ServiceException {
		ResponseObject<Account> result = new ResponseObject<Account>();
		if (StringUtil.isEmpty(userId)) {
			result.setCode(ResponseCode.PARAMETER_ERROR);
			result.setMessage("参数不正确");
		} else {
			try {
				Account account = this.accountDao.getAccountByUserId(userId);
				if (account != null) {
					result.setCode(ResponseCode.SUCCESS_CODE);
					result.setData(account);
				} else {
					result.setCode(ResponseCode.PARAMETER_ERROR);
					result.setMessage("数据库中没有对应用户id的账户记录");
				}
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException(e);
			}
		}
		return result;
	}
	
	public ResponseObject<Object> addWithdrawals(Withdrawals withdrawals, User user) throws ServiceException {
		try {
			String date = DateUtil.date2String(new Date());
			withdrawals.setCreateDate(date);
			withdrawals.setModifyDate("");
			withdrawals.setProc("0");
			
			AccountDetail detail = new AccountDetail(); 
			detail.setAmount(withdrawals.getAmount());
			detail.setRealAmount(detail.getAmount());
			detail.setUserId(user.getId());
			detail.setState(Constant.ACCOUNT_DETAIL_STATE2);
			detail.setName("提现");
			detail.setCreateDate(date);
			detail.setCurrency("美元");
			detail.setModifyDate(date);
			detail.setType(Constant.ACCOUNT_DETAIL_TYPE40);
			detail.setRemark("");
			
			detail.setThirdNo("");
			InvoiceNo in = this.invoiceNoDao.getByType(Constant.INVOICE_TYPE5);
			if(in!=null){
				if(this.invoiceNoDao.modifyStateById(in.getId(),date) > 0){
					detail.setInvoiceNo(in.getInvoiceNo());
					withdrawals.setInvoiceNo(in.getInvoiceNo());
				}
			}
			
			Account a = accountDao.getAccountByUserId(detail.getUserId());
			double usd= StringUtil.string2Double(a.getUsd());
			double newusd = usd - StringUtil.string2Double(detail.getAmount());
			if (newusd >= 0) {
				// ignore
			} else {
				return new ResponseObject<Object>( ResponseCode.ACCOUNT_INSUFFICIENT_BALANCE, "帐户余额不足!");
			}
			
			// 账户支付，修改账户余额
			if (this.accountDao.modifyUsdByUserId(a.getUserId(), String.valueOf(newusd), date) > 0) {
				// 扣款成功
				int nResult  = this.accountDetailDao.insertAccountDetail(detail);
				if(nResult > 0){
					int i = this.withdrawalsDao.insertWithdrawals(withdrawals);
					if (i > 0) {
						System.out.println(MailSendUtil.sendRequestWithdrawalsMsg(user.getEmail(), withdrawals.getType(), withdrawals.getAmount()));
						Properties prop = null;
						prop = PropertiesReader.read(Constant.EMAIL_PROPERTIES_FILE);
						String subject = prop.getProperty("anyonehelps.request.withdrawals.subject");
						
						String content = prop.getProperty("anyonehelps.request.withdrawals.content"); 
						if("1".equals(withdrawals.getType())){
							content = MessageFormat.format(content, new Object[] {withdrawals.getAmount(), "银行卡", date});
						}else if("2".equals(withdrawals.getType())){
							content = MessageFormat.format(content, new Object[] {withdrawals.getAmount(), "paypal", date});
						}else{
							content = MessageFormat.format(content, new Object[] {withdrawals.getAmount(), "", date});
						}
						EmailSend es = new EmailSend();
						es.setContent(content);
						es.setCreateDate(date);
						es.setEmail(user.getEmail());
						es.setFailCount(Constant.EMAILSEND_FAIL_COUNT0);
						es.setModifyDate(date);
						es.setState(Constant.EMAILSEND_STATE0);
						es.setSubject(subject);
						es.setUserId(a.getUserId());
						emailSendDao.insert(es);
						
						ResponseObject<Object> result = new ResponseObject<Object>(ResponseCode.SUCCESS_CODE);
						return result;
					} else {
						return new ResponseObject<Object>(ResponseCode.WITHDRAWALS_INSERT_FAILURE, "提交取现失败，请重试！");
					}
				}else{
					throw new Exception();
				}
			}else{
				return new ResponseObject<Object>(ResponseCode.WITHDRAWALS_INSERT_FAILURE, "提交取现失败，请重试！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw ExceptionUtil.handle2ServiceException(e);
		}
	}

	@Override
	public ResponseObject<PageSplit<Withdrawals>> search(String userId,
			String type, String state, int pageSize, int pageNow)
			throws ServiceException {
		try {
			state = StringUtil.isEmpty(state) ? null : state.trim();
			type = StringUtil.isEmpty(type) ? null : type.trim();
			userId = StringUtil.isEmpty(userId) ? null : userId.trim();
			int rowCount = 0;
			try {
				rowCount = this.withdrawalsDao.count(userId, type,state);
			} catch (Exception e) {
				throw ExceptionUtil.handle2ServiceException("获取取现记录数量失败", e);
			}

			ResponseObject<PageSplit<Withdrawals>> responseObj = new ResponseObject<PageSplit<Withdrawals>>(
			        ResponseCode.SUCCESS_CODE);
			if (rowCount > 0) {
				pageSize = Math.max(pageSize, 1);
				int pageCount = rowCount / pageSize + (rowCount % pageSize == 0 ? 0 : 1);
				pageNow = Math.min(pageNow, pageCount);
				PageSplit<Withdrawals> pageSplit = new PageSplit<Withdrawals>();
				pageSplit.setPageCount(pageCount);
				pageSplit.setPageNow(pageNow);
				pageSplit.setRowCount(rowCount);
				pageSplit.setPageSize(pageSize);

				int startIndex = (pageNow - 1) * pageSize;
				try {
					List<Withdrawals> withdrawals = this.withdrawalsDao.search(userId, type, state,
					        startIndex, pageSize);
					if (withdrawals != null && !withdrawals.isEmpty()) {
						for (Withdrawals w : withdrawals) {
							pageSplit.addData(w);
						}
					}
				} catch (Exception e) {
					throw ExceptionUtil.handle2ServiceException("获取取现记录失败", e);
				}
				responseObj.setData(pageSplit);
			} else {
				responseObj.setMessage("没有取现记录");
			}
			return responseObj;
		} catch (ServiceException e) {
			throw e;
		}
	}

	@Override
	public ResponseObject<Object> PorcPaypalNotify(HttpServletRequest request) throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>(ResponseCode.SUCCESS_CODE);
		PaypalUtil pu = new PaypalUtil();
		PaypalResultMode prm =  pu.getPaypalResultMode(request);
		if(prm!=null){
			log.error("paypal result mode:"+prm.toString());
			//获取 PayPal 对回发信息的回复信息，判断刚才的通知是否为 PayPal 发出的
			if(prm.getResultVerified().equals("VERIFIED")) {
				//检查付款状态
				if(prm.getPaymentStatus().equals("Completed")){
					log.info("Paypal notify paymentStatus Completed.");
					//检查 receiver_email 是否是您的 PayPal 账户中的 EMAIL 地址
					//PaypalConfig pc = new PaypalConfig();
					if(prm.getReceiverEmail().equals(PaypalConfig.getBusiness())){ 
						log.info("Paypal notify receiver email is mine.");
						
						if(prm.getCustom()!=null&&prm.getCustom().startsWith("task pay")){
							//任务支付
							String demandId = null;
							String rdId = null;
							String[] array = prm.getCustom().split(",");
							for(int i=0;i<array.length;i++){
								if(i==1){
									demandId = array[i];
								}
								if(i==2){
									rdId = array[i];
								}
							}
							if (!ReceiveDemandUtil.validateId(rdId)) {
								log.error("Paypal 任务付款投标人信息不正确，不接受这笔款项！！！！！！！.");
								responseObj.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
								responseObj.setMessage("Paypal 任务付款投标人信息不正确，不接受这笔款项！！！！！！！.");
								return responseObj;
							} else if (!DemandUtil.validateId(demandId)) {
								log.error("Paypal 任务付款任务信息不正确，不接受这笔款项！！！！！！！.");
								responseObj.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
								responseObj.setMessage("Paypal 任务付款任务信息不正确，不接受这笔款项！！！！！！！.");
								return responseObj;
							}
							try {
								Demand demand = this.demandDao.getDemandById(demandId);
								if(demand==null){
									responseObj.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
									responseObj.setMessage("任务不存在！");
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
								}else{
									String procedureRatio = "";
									String procedure = "";
									try {
										Properties props = PropertiesReader.read(Constant.SYSTEM_PROPERTIES_FILE);
										procedureRatio = props.getProperty("paypal.recharge.procedure.ratio");
										procedure = props.getProperty("paypal.recharge.procedure");
									} catch (Exception e) {
										log.error("读取paypal手续费比例和固定手续费配置错误!");
										return new ResponseObject<Object>(ResponseCode.SHOW_EXCEPTION,"系统服务忙，请稍后再充值，或者请联系客服!");
									}
									
									//String.valueOf((StringUtil.string2Double(prm.getMcGross())-Double.valueOf(procedure))*(1-Double.valueOf(procedureRatio)));
									
									//double da = Double.valueOf(prm.getMcGross() != null ? prm.getMcGross().trim() : "");  //实际付款金额
									double rdAmount = Double.valueOf(selectRD.getAmount() != null ? selectRD.getAmount().trim() : "");
									double realAmount = (StringUtil.string2Double(prm.getMcGross())-Double.valueOf(procedure))*(1-Double.valueOf(procedureRatio)); //到帐金额
									
									if(rdAmount - realAmount<0.02&&rdAmount - realAmount>-0.02){
										
										String date = DateUtil.date2String(new Date());
										int nResult = this.demandDao.modifyPayState(demandId, Constant.DEMAND_STATE_SELECT, Constant.DEMAND_PAY_STATE1, date);
										int iResult = this.receiveDemandDao.modifyState(selectRD.getId(), Constant.RECEIVEDEMAND_STATE_CHECKED);
										if (nResult > 0&& iResult>0) {
											//修改账户冻结金额
											if(this.accountDao.addFreezeUsd(demand.getUserId(), selectRD.getAmount(), date) > 0){
												// pass
											}else{
												throw new Exception("支付失败，请重试");
											}
											AccountDetail detail = new AccountDetail(); 
											detail.setAmount(selectRD.getAmount());
											detail.setRealAmount(detail.getAmount());
											detail.setUserId(demand.getUserId());
											detail.setState(Constant.ACCOUNT_DETAIL_STATE2);
											detail.setName("任务金额冻结");
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
											rechargeDetail.setAmount(selectRD.getAmount());
											rechargeDetail.setRealAmount(rechargeDetail.getAmount());
											rechargeDetail.setUserId(demand.getUserId());
											rechargeDetail.setCreateDate(date);
											rechargeDetail.setModifyDate(date);
											rechargeDetail.setCurrency("美元");
											detail.setName("Paypal充值");
											detail.setType(Constant.ACCOUNT_DETAIL_TYPE14);
											detail.setState(Constant.ACCOUNT_DETAIL_STATE2);
											rechargeDetail.setDemandId(null);
											rechargeDetail.setRemark(prm.getTxnId());
											rechargeDetail.setDemandId(null);
											rechargeDetail.setThirdNo(prm.getTxnId());
											
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
											return responseObj;
										} else {
											// 进行事务回滚
											throw new Exception();
										}
									}else{
										log.error("任务付款金额不正确!");
										responseObj.setCode(ResponseCode.SHOW_EXCEPTION);
										responseObj.setMessage("任务付款金额不正确！");
										return responseObj;
									}
								}
							} catch (Exception e) {
								log.error("Paypal 任务支付处理异常！！！！！！！.");
								throw ExceptionUtil.handle2ServiceException("任务支付处理异常！", e);
							}
							
							
						}else{
							//paypal充值
							
							//检查 txn_id 是否已经处理过
							try {
								if(this.accountDetailDao.getByRemark(prm.getTxnId())!=null){ //已经处理过
									log.info("Paypal notify 已经处理过.");
									return responseObj;
								}else{  //未处理
									if(prm.getMcCurrency().equals("USD")){
										//处理其他数据，包括写数据库
										String date = DateUtil.date2String(new Date());
										AccountDetail detail = new AccountDetail();
										//2.9
										String procedureRatio ="";
										String procedure =""; 
										try {
											Properties props = PropertiesReader.read(Constant.SYSTEM_PROPERTIES_FILE);
											procedureRatio = props.getProperty("paypal.recharge.procedure.ratio");
											procedure = props.getProperty("paypal.recharge.procedure");
										} catch (Exception e) {
											log.error("读取paypal手续费比例和固定手续费配置错误!");
											return new ResponseObject<Object>(ResponseCode.SHOW_EXCEPTION,"系统服务忙，请稍后再充值，或者请联系客服!");
										}
										detail.setAmount(String.valueOf((StringUtil.string2Double(prm.getMcGross())-Double.valueOf(procedure))*(1-Double.valueOf(procedureRatio))));
										detail.setRealAmount(detail.getAmount());
										detail.setUserId(prm.getCustom());
										detail.setCurrency("美元");
										detail.setName("Paypal充值");
										detail.setType(Constant.ACCOUNT_DETAIL_TYPE14);
										detail.setCreateDate(date);
										detail.setState(Constant.ACCOUNT_DETAIL_STATE2);
										detail.setRemark(prm.getTxnId());
										detail.setModifyDate(date);
										
										detail.setDemandId(null);
										detail.setThirdNo(prm.getTxnId());
										InvoiceNo in = this.invoiceNoDao.getByType(Constant.INVOICE_TYPE0);
										if(in!=null){
											if(this.invoiceNoDao.modifyStateById(in.getId(),date) > 0){
												detail.setInvoiceNo(in.getInvoiceNo());
											}
										}

										String userId = detail.getUserId();
										Account account = accountDao.getAccountByUserId(userId);
										double usd= StringUtil.string2Double(account.getUsd());
										double newusd = usd + StringUtil.string2Double(detail.getAmount());
										
										if (this.accountDao.modifyUsdByUserId(userId, String.valueOf(newusd), date) > 0) {
											// pass
											this.accountDetailDao.insertAccountDetail(detail);
											return responseObj;
										} else {
											throw new Exception();
										}
									}else{
										log.warn("Paypal notify 币种不是美元.");
										return responseObj;
									}
								}
							} catch (Exception e) {
								log.error("Paypal 充值处理异常！！！！！！！.");
								throw ExceptionUtil.handle2ServiceException("数据库处理异常！", e);
							}
						}
						
					}else{
						log.warn("Paypal notify receiver email is not mine.");
						return responseObj;
					}
				} else {
					log.info("Paypal notify paymentStatus fail.");   
					return responseObj;
				}
			} else {
				//非法信息，可以将此记录到您的日志文件中以备调查
				log.error("Paypal notify err,");
				return responseObj;
			} 
		}else{
			log.error("paypal notify 异常");
			return responseObj;
		}
		//return responseObj;
	}
	
	/**
	 * 模拟paypal测试数据
	 */
	/*@Override
	public ResponseObject<Object> PorcPaypalNotifyTest(HttpServletRequest request) throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>(ResponseCode.SUCCESS_CODE);
		PaypalResultMode prm =  new PaypalResultMode();
		prm.setCustom("task pay,362,291");
		prm.setResultVerified("VERIFIED");
		prm.setPaymentStatus("Completed");
		prm.setMcGross("0.31");
		prm.setMcCurrency("USD");
		prm.setTxnId("561841540Y589950T");
		prm.setReceiverEmail("teanjuly@gmail.com");
		prm.setPayerEmail("846645133@qq.com");
		prm.setMcFee("0.31");
		
		
		log.error("paypal result mode:" + prm.toString());
		// 获取 PayPal 对回发信息的回复信息，判断刚才的通知是否为 PayPal 发出的
		if (prm.getResultVerified().equals("VERIFIED")) {
			// 检查付款状态
			if (prm.getPaymentStatus().equals("Completed")) {
				log.info("Paypal notify paymentStatus Completed.");
				// 检查 receiver_email 是否是您的 PayPal 账户中的 EMAIL 地址
				// PaypalConfig pc = new PaypalConfig();
				if (prm.getReceiverEmail().equals(PaypalConfig.getBusiness())) {
					log.info("Paypal notify receiver email is mine.");

					if (prm.getCustom() != null
							&& prm.getCustom().startsWith("task pay")) {
						log.error("==============2");
						// 任务支付
						String demandId = null;
						String rdId = null;
						String[] array = prm.getCustom().split(",");
						for (int i = 0; i < array.length; i++) {
							if (i == 1) {
								demandId = array[i];
							}
							if (i == 2) {
								rdId = array[i];
							}
						}
						if (!ReceiveDemandUtil.validateId(rdId)) {
							log.error("Paypal 任务付款投标人信息不正确，不接受这笔款项！！！！！！！.");
							responseObj
									.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
							responseObj
									.setMessage("Paypal 任务付款投标人信息不正确，不接受这笔款项！！！！！！！.");
							return responseObj;
						} else if (!DemandUtil.validateId(demandId)) {
							log.error("Paypal 任务付款任务信息不正确，不接受这笔款项！！！！！！！.");
							responseObj
									.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
							responseObj
									.setMessage("Paypal 任务付款任务信息不正确，不接受这笔款项！！！！！！！.");
							return responseObj;
						}
						log.error("==============3");
						try {
							Demand demand = this.demandDao
									.getDemandById(demandId);
							if (demand == null) {
								responseObj
										.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
								responseObj.setMessage("任务不存在！");
								return responseObj;
							}
							if (demand.getPayState().equals(
									Constant.DEMAND_PAY_STATE1)) {
								responseObj
										.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
								responseObj.setMessage("任务已经支付，无需再次支付！");
								return responseObj;
							}
							if (!demand.getState().equals(
									Constant.DEMAND_STATE_READY)
									&& !demand.getState().equals(
											Constant.DEMAND_STATE_RECEIVE)) {
								if (demand.getState().equals(
										Constant.DEMAND_STATE_CLOSE)) {
									responseObj.setMessage("任务已关闭！");
								} else {
									responseObj.setMessage("任务已匹配，无需再匹配！");
								}
								responseObj
										.setCode(ResponseCode.DEMAND_STATE_ERROR);
								return responseObj;
							}
							log.error("==============4");
							List<ReceiveDemand> rds = demand
									.getReceiveDemands();
							ReceiveDemand selectRD = null;
							if (rds != null) {
								for (ReceiveDemand rd : rds) {
									if (rd.getId().equals(rdId)) {
										selectRD = rd;
									}
								}
							}
							if (selectRD == null) {
								responseObj
										.setCode(ResponseCode.DEMAND_MODIFY_STATE_ERROR);
								responseObj.setMessage("任务没有该投标作息，请刷新页面后再操作！！");
								return responseObj;
							} else {
								String procedureRatio = "";
								String procedure = "";
								try {
									Properties props = PropertiesReader
											.read(Constant.SYSTEM_PROPERTIES_FILE);
									procedureRatio = props
											.getProperty("paypal.recharge.procedure.ratio");
									procedure = props
											.getProperty("paypal.recharge.procedure");
								} catch (Exception e) {
									log.error("读取paypal手续费比例和固定手续费配置错误!");
									return new ResponseObject<Object>(
											ResponseCode.SHOW_EXCEPTION,
											"系统服务忙，请稍后再充值，或者请联系客服!");
								}

								// String.valueOf((StringUtil.string2Double(prm.getMcGross())-Double.valueOf(procedure))*(1-Double.valueOf(procedureRatio)));

								log.error("==============5");
								// double da = Double.valueOf(prm.getMcGross()
								// != null ? prm.getMcGross().trim() : "");
								// //实际付款金额
								double rdAmount = Double.valueOf(selectRD
										.getAmount() != null ? selectRD
										.getAmount().trim() : "");
								double realAmount = (StringUtil
										.string2Double(prm.getMcGross()) - Double
										.valueOf(procedure))
										* (1 - Double.valueOf(procedureRatio)); // 到帐金额
								log.error(rdAmount);
								log.error(realAmount);
								if (rdAmount - realAmount < 0.02
										&& rdAmount - realAmount > -0.02) {

									String date = DateUtil
											.date2String(new Date());
									int nResult = this.demandDao
											.modifyPayState(
													demandId,
													Constant.DEMAND_STATE_SELECT,
													Constant.DEMAND_PAY_STATE1,
													date);
									int iResult = this.receiveDemandDao
											.modifyState(
													selectRD.getId(),
													Constant.RECEIVEDEMAND_STATE_CHECKED);
									if (nResult > 0 && iResult > 0) {
										// 修改账户冻结金额
										if (this.accountDao.addFreezeUsd(
												demand.getUserId(),
												selectRD.getAmount(), date) > 0) {
											// pass
										} else {
											throw new Exception("支付失败，请重试");
										}
										AccountDetail detail = new AccountDetail();
										detail.setAmount(selectRD.getAmount());
										detail.setRealAmount(detail.getAmount());
										detail.setUserId(demand.getUserId());
										detail.setState(Constant.ACCOUNT_DETAIL_STATE2);
										detail.setName("任务金额冻结");
										detail.setCreateDate(date);
										detail.setCurrency("美元");
										detail.setModifyDate(date);
										detail.setType(Constant.ACCOUNT_DETAIL_TYPE33);
										detail.setRemark("任务号:"
												+ demand.getId());
										detail.setDemandId(demand.getId());
										detail.setThirdNo("");
										InvoiceNo in = this.invoiceNoDao
												.getByType(Constant.INVOICE_TYPE1);
										if (in != null) {
											if (this.invoiceNoDao
													.modifyStateById(
															in.getId(), date) > 0) {
												detail.setInvoiceNo(in
														.getInvoiceNo());
											}
										}

										AccountDetail rechargeDetail = new AccountDetail();
										rechargeDetail.setAmount(selectRD
												.getAmount());
										rechargeDetail
												.setRealAmount(rechargeDetail
														.getAmount());
										rechargeDetail.setUserId(demand
												.getUserId());
										rechargeDetail.setCreateDate(date);
										rechargeDetail.setModifyDate(date);
										rechargeDetail.setCurrency("美元");
										rechargeDetail.setName("信用卡充值");
										rechargeDetail
												.setState(Constant.ACCOUNT_DETAIL_STATE2);
										rechargeDetail
												.setType(Constant.ACCOUNT_DETAIL_TYPE11);
										rechargeDetail.setDemandId(null);
										rechargeDetail
												.setRemark(prm.getTxnId());
										rechargeDetail.setDemandId(null);
										rechargeDetail.setThirdNo(prm
												.getTxnId());

										this.accountDetailDao
												.insertAccountDetail(rechargeDetail);
										this.accountDetailDao
												.insertAccountDetail(detail);

										// 邮箱提醒 add by haokun 2017/01/26
										// 选择接单人，增加邮件提醒
										User user = this.userDao
												.getUserById(selectRD
														.getUserId());
										if (user.getEmail() != null
												&& !user.getEmail().equals("")
												&& user.getEmailState()
														.equals(Constant.USER_EMAIL_STATE1)) {
											String subject = "[AnyoneHelps] 任务中标";
											String content = "尊敬的用户！<br>";
											content += "您投标'"
													+ demand.getTitle()
													+ "'任务中标了，请尽快到任务管理的已接收任务页面开始任务，并按要求完成任务.同时对方联系方式是:";
											if (demand.getUser() != null) {
												if (demand.getUser().getEmail() != null) {
													content += "邮箱:"
															+ demand.getUser()
																	.getEmail();
												}
												if (demand.getUser()
														.getTelphone() != null) {
													content += ",手机:"
															+ demand.getUser()
																	.getAreaCode()
															+ demand.getUser()
																	.getTelphone();
												}
											}
											content += "<br><br>感谢您对AnyoneHelps的支持!<br>AnyoneHelps团队<br>www.anyonehelps.com<br>";
											content += "<img src='https://www.anyonehelps.com/assets/pages/img/index/logo-bottom.png'/>";

											EmailSend es = new EmailSend();
											es.setContent(content);
											es.setCreateDate(new Date()
													.toString());
											es.setEmail(user.getEmail());
											es.setFailCount(Constant.EMAILSEND_FAIL_COUNT0);
											es.setModifyDate(new Date()
													.toString());
											es.setState(Constant.EMAILSEND_STATE0);
											es.setSubject(subject);
											es.setUserId(user.getId());
											this.emailSendDao.insert(es);
										}

										// 还没给接单人发短信或邮箱提醒。
										String content = "您投标'"
												+ demand.getTitle()
												+ "'任务中标了，请尽快到任务管理的已接收任务页面开始任务，并按要求完成任务.同时对方联系方式是:";
										if (demand.getUser() != null) {
											if (demand.getUser().getEmail() != null) {
												content += "邮箱:"
														+ demand.getUser()
																.getEmail();
											}
											if (demand.getUser().getTelphone() != null) {
												content += ",手机:"
														+ demand.getUser()
																.getAreaCode()
														+ demand.getUser()
																.getTelphone();
											}
										}
										MessageSystem ms = new MessageSystem();
										ms.setCreateDate(date);
										ms.setLevel(Constant.MESSAGESYSTEM_LEVEL_INFO);
										ms.setContent(content);
										// 还没写配置
										ms.setLink("/dashboard/Task/accDetail.jsp?id="
												+ Base64Util.encode(demandId));
										ms.setModifyDate(date);
										ms.setTitle("恭喜您！任务" + demandId
												+ "匹配成功，点击查看并尽快开始任务吧！");
										ms.setUserId(selectRD.getUserId());
										ms.setState(Constant.MESSAGESYSTEM_STATE_UNREAD);
										this.messageSystemService
												.saveMessage(ms);
										return responseObj;
									} else {
										// 进行事务回滚
										throw new Exception();
									}
								} else {
									log.error("任务付款金额不正确!");
									responseObj
											.setCode(ResponseCode.SHOW_EXCEPTION);
									responseObj.setMessage("任务付款金额不正确！");
									return responseObj;
								}
							}
						} catch (Exception e) {
							log.error("Paypal 任务支付处理异常！！！！！！！.");
							throw ExceptionUtil.handle2ServiceException(
									"任务支付处理异常！", e);
						}

					} else {
						// paypal充值

						// 检查 txn_id 是否已经处理过
						try {
							if (this.accountDetailDao.getByRemark(prm
									.getTxnId()) != null) { // 已经处理过
								log.info("Paypal notify 已经处理过.");
								return responseObj;
							} else { // 未处理
								if (prm.getMcCurrency().equals("USD")) {
									// 处理其他数据，包括写数据库
									String date = DateUtil
											.date2String(new Date());
									AccountDetail detail = new AccountDetail();
									// 2.9
									String procedureRatio = "";
									String procedure = "";
									try {
										Properties props = PropertiesReader
												.read(Constant.SYSTEM_PROPERTIES_FILE);
										procedureRatio = props
												.getProperty("paypal.recharge.procedure.ratio");
										procedure = props
												.getProperty("paypal.recharge.procedure");
									} catch (Exception e) {
										log.error("读取paypal手续费比例和固定手续费配置错误!");
										return new ResponseObject<Object>(
												ResponseCode.SHOW_EXCEPTION,
												"系统服务忙，请稍后再充值，或者请联系客服!");
									}
									detail.setAmount(String.valueOf((StringUtil
											.string2Double(prm.getMcGross()) - Double
											.valueOf(procedure))
											* (1 - Double
													.valueOf(procedureRatio))));
									detail.setRealAmount(detail.getAmount());
									detail.setUserId(prm.getCustom());
									detail.setCurrency("美元");
									detail.setName("Paypal充值");
									detail.setType(Constant.ACCOUNT_DETAIL_TYPE14);
									detail.setCreateDate(date);
									detail.setState(Constant.ACCOUNT_DETAIL_STATE2);
									detail.setRemark(prm.getTxnId());
									detail.setModifyDate(date);

									detail.setDemandId(null);
									detail.setThirdNo(prm.getTxnId());
									InvoiceNo in = this.invoiceNoDao
											.getByType(Constant.INVOICE_TYPE0);
									if (in != null) {
										if (this.invoiceNoDao.modifyStateById(
												in.getId(), date) > 0) {
											detail.setInvoiceNo(in
													.getInvoiceNo());
										}
									}

									String userId = detail.getUserId();
									Account account = accountDao
											.getAccountByUserId(userId);
									double usd = StringUtil
											.string2Double(account.getUsd());
									double newusd = usd
											+ StringUtil.string2Double(detail
													.getAmount());

									if (this.accountDao.modifyUsdByUserId(
											userId, String.valueOf(newusd),
											date) > 0) {
										// pass
										this.accountDetailDao
												.insertAccountDetail(detail);
										return responseObj;
									} else {
										throw new Exception();
									}
								} else {
									log.warn("Paypal notify 币种不是美元.");
									return responseObj;
								}
							}
						} catch (Exception e) {
							log.error("Paypal 充值处理异常！！！！！！！.");
							throw ExceptionUtil.handle2ServiceException(
									"数据库处理异常！", e);
						}
					}

				} else {
					log.warn("Paypal notify receiver email is not mine.");
					return responseObj;
				}
			} else {
				log.info("Paypal notify paymentStatus fail.");
				return responseObj;
			}
		} else {
			// 非法信息，可以将此记录到您的日志文件中以备调查
			log.error("Paypal notify err,");
			return responseObj;
		}
	}*/

	@Override
	public ResponseObject<Object> transfer(String userId, String nickName,String userAccount,
			String amount) throws ServiceException {
		if(StringUtil.isEmpty(userAccount)){
			return new ResponseObject<Object>( ResponseCode.PARAMETER_ERROR, "收款帐号错误!");
		}
		if(StringUtil.isEmpty(amount)){
			return new ResponseObject<Object>( ResponseCode.PARAMETER_ERROR, "转帐金额错误!");
		}
		if(StringUtil.isEmpty(userId)){
			return new ResponseObject<Object>( ResponseCode.PARAMETER_ERROR, "参数错误!");
		}
		String procedureRatio ="";
		String procedureEnable ="1";
		String arriveDay = "7";
		try {
			Properties props = PropertiesReader.read(Constant.SYSTEM_PROPERTIES_FILE);
			procedureRatio = props.getProperty("account.tansfer.procedure.ratio");
			procedureEnable = props.getProperty("account.tansfer.procedure.enable");
			arriveDay = props.getProperty("account.tansfer.arrive");
		} catch (Exception e) {
			log.error("读取手续费的配置错误!");
			return new ResponseObject<Object>( ResponseCode.SHOW_EXCEPTION, "系统服务忙，请稍后再转帐，或者请联系客服!");
		}
		try {
			
			User user = this.userDao.getUserByAccount(userAccount);
			if(user==null){
				return new ResponseObject<Object>( ResponseCode.USER_NAME_NOT_EXISTS, "收款帐号不存在!");
			}
			
			String date = DateUtil.date2String(new Date());
			AccountDetail detail = new AccountDetail(); 
			detail.setAmount(amount);
			detail.setRealAmount(detail.getAmount());
			detail.setUserId(userId);
			detail.setState(Constant.ACCOUNT_DETAIL_STATE2);
			detail.setName("转帐");
			detail.setCreateDate(date);
			detail.setCurrency("美元");
			detail.setModifyDate(date);
			detail.setType(Constant.ACCOUNT_DETAIL_TYPE31);
			detail.setRemark("收款人:"+userAccount);
			
			Account a = accountDao.getAccountByUserId(detail.getUserId());
			double usd= StringUtil.string2Double(a.getUsd());
			double newusd = usd - StringUtil.string2Double(detail.getAmount());
			if (newusd >= 0) {
				// ignore
			} else {
				return new ResponseObject<Object>( ResponseCode.ACCOUNT_INSUFFICIENT_BALANCE, "帐户余额不足,请充值");
			}
			
			// 账户支付，修改账户余额
			if (this.accountDao.modifyUsdByUserId(a.getUserId(), String.valueOf(newusd), date) > 0) {
				// 扣款成功
				int i = this.accountDetailDao.insertAccountDetail(detail);
				if(i>0){//写财务记录成功
					//改收款人收款金额
					AccountDetail accountDetail = new AccountDetail();  
					accountDetail.setAmount(amount);
					accountDetail.setRealAmount(accountDetail.getAmount());
					accountDetail.setUserId(user.getId());
					accountDetail.setState(Constant.ACCOUNT_DETAIL_STATE1);
					accountDetail.setName("收到转帐付款费用");
					accountDetail.setRemark("收到用户:"+nickName+"的转帐,金额将于"+arriveDay+"天内到帐"); 
					accountDetail.setCreateDate(date);
					accountDetail.setCurrency("美元");
					accountDetail.setType(Constant.ACCOUNT_DETAIL_TYPE17);
					if( this.accountDao.addForwardUsd(user.getId(), amount, date)>0){
						//pass
					}else{
						throw new Exception();
					}
					if(this.accountDetailDao.insertAccountDetail(accountDetail)>0){
						//pass
					}else{
						throw new Exception();
					}
					//扣手续费
					if(procedureEnable.equals("1")){
						AccountDetail procedureDetail = new AccountDetail(); //手续费
						double procedure = Double.valueOf(amount != null ? amount.trim() : "")*Double.valueOf(procedureRatio);
						procedureDetail.setAmount(String.valueOf(procedure));
						procedureDetail.setRealAmount(procedureDetail.getAmount());
						procedureDetail.setUserId(userId);
						procedureDetail.setCurrency("美元");
						procedureDetail.setName("转帐付款手续费");
						procedureDetail.setRemark("收到用户:"+nickName+"的转帐,扣取手续费用");
						procedureDetail.setCreateDate(date);
						procedureDetail.setState(Constant.ACCOUNT_DETAIL_STATE2);
						procedureDetail.setType(Constant.ACCOUNT_DETAIL_TYPE35);
						if( this.accountDao.reduceForwardUsd(user.getId(), String.valueOf(procedure), date)>0){
							//pass
						}else{
							throw new Exception();
						}
						if(this.accountDetailDao.insertAccountDetail(procedureDetail)>0){
							//pass
						}else{
							throw new Exception();
						}
					}
					return new ResponseObject<Object>(ResponseCode.SUCCESS_CODE);
					
				}else{
					throw new Exception();
				}
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
	}

/*	@Override
	public ResponseObject<Object> rechargeByStripe(String userId, String brand, String name, String creditNo,
			String securityCode, String expireYear, String expireMonth,
			String amount, String currency) throws ServiceException {
		
		double minRechargeAmount = 0.5;
		Charge charge = null;
		double da = Double.valueOf(amount != null ? amount.trim() : "");
		int money = (int) (da*100);
		if (da < minRechargeAmount) {
			log.error("充值金额必须大于"+minRechargeAmount+"!");
			return new ResponseObject<Object>(ResponseCode.PARAMETER_ERROR,"充值金额必须大于"+minRechargeAmount+"!");
		}
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
	        String order = df.format(new Date())+String.valueOf((int)(Math.random()*9000+1000));
	        charge = StripeUtil.createPayMoneyByStrip(brand,name,creditNo,securityCode,expireYear,expireMonth,money,currency,order);
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
		
		String procedureRatio ="";
		String giveRatio =""; 
		try {
			Properties props = PropertiesReader.read(Constant.SYSTEM_PROPERTIES_FILE);
			procedureRatio = props.getProperty("stripe.recharge.procedure.ratio");
			giveRatio = props.getProperty("stripe.recharge.give.ratio");
		} catch (Exception e) {
			log.error("读取手续费与赠送费的配置错误!");
			return new ResponseObject<Object>(ResponseCode.SHOW_EXCEPTION,"系统服务忙，请稍后再充值，或者请联系客服!");
		}
		
		String date = DateUtil.date2String(new Date());
		AccountDetail detail = new AccountDetail();
		AccountDetail giveDetail = new AccountDetail();  //赠送
		AccountDetail procedureDetail = new AccountDetail(); //手续费
		
		double give = da*Double.valueOf(giveRatio);
		double procedure = da*Double.valueOf(procedureRatio);
		
		giveDetail.setAmount(String.valueOf(give));
		giveDetail.setRealAmount(giveDetail.getAmount());
		giveDetail.setUserId(userId);
		giveDetail.setCurrency("美元");
		giveDetail.setName("信用卡充值赠送");
		giveDetail.setRemark(charge.getId()); 
		giveDetail.setCreateDate(date);
		giveDetail.setModifyDate(date);
		giveDetail.setState(Constant.ACCOUNT_DETAIL_STATE2);
		giveDetail.setType(Constant.ACCOUNT_DETAIL_TYPE16);
		giveDetail.setDemandId(null);
		giveDetail.setThirdNo(charge.getId());
		
		procedureDetail.setAmount(String.valueOf(procedure));
		procedureDetail.setRealAmount(procedureDetail.getAmount());
		procedureDetail.setUserId(userId);
		procedureDetail.setCurrency("美元");
		procedureDetail.setName("信用卡充值手续费");
		procedureDetail.setRemark(charge.getId());
		procedureDetail.setCreateDate(date);
		procedureDetail.setModifyDate(date);
		procedureDetail.setState(Constant.ACCOUNT_DETAIL_STATE2);
		procedureDetail.setType(Constant.ACCOUNT_DETAIL_TYPE32);
		procedureDetail.setDemandId(null);
		procedureDetail.setThirdNo(charge.getId());
		
		detail.setAmount(String.valueOf(da));
		detail.setRealAmount(detail.getAmount());
		detail.setUserId(userId);
		detail.setCreateDate(date);
		detail.setModifyDate(date);
		detail.setRemark(charge.getId());
		detail.setCurrency("美元");
		detail.setName("信用卡充值");
		detail.setState(Constant.ACCOUNT_DETAIL_STATE2);
		detail.setType(Constant.ACCOUNT_DETAIL_TYPE11);
		detail.setDemandId(null);
		detail.setThirdNo(charge.getId());
		
		try {
			Account a = accountDao.getAccountByUserId(userId);
			double usd= StringUtil.string2Double(a.getUsd());
			double newusd = usd + StringUtil.string2Double(detail.getAmount()) 
					+ StringUtil.string2Double(giveDetail.getAmount())
					- StringUtil.string2Double(procedureDetail.getAmount());
		
			if (this.accountDao.modifyUsdByUserId(userId, String.valueOf(newusd), date) > 0) {
				// pass
				InvoiceNo in = this.invoiceNoDao.getByType(Constant.INVOICE_TYPE0);
				if(in!=null){
					if(this.invoiceNoDao.modifyStateById(in.getId(),date) > 0){
						detail.setInvoiceNo(in.getInvoiceNo());
					}
				}
				InvoiceNo procedureDetailInvoiceNo = this.invoiceNoDao.getByType(Constant.INVOICE_TYPE0);
				if(procedureDetailInvoiceNo!=null){
					if(this.invoiceNoDao.modifyStateById(procedureDetailInvoiceNo.getId(),date) > 0){
						procedureDetail.setInvoiceNo(procedureDetailInvoiceNo.getInvoiceNo());
					}
				}
				InvoiceNo giveDetailInvoiceNo = this.invoiceNoDao.getByType(Constant.INVOICE_TYPE0);
				if(giveDetailInvoiceNo!=null){
					if(this.invoiceNoDao.modifyStateById(giveDetailInvoiceNo.getId(),date) > 0){
						giveDetail.setInvoiceNo(giveDetailInvoiceNo.getInvoiceNo());
					}
				}
				
				this.accountDetailDao.insertAccountDetail(detail);
				this.accountDetailDao.insertAccountDetail(procedureDetail);
				this.accountDetailDao.insertAccountDetail(giveDetail);
				return new ResponseObject<Object>(ResponseCode.SHOW_EXCEPTION,"充值成功");
			} else {
				return new ResponseObject<Object>(ResponseCode.SHOW_EXCEPTION,"已经扣款成功，但加入余额中出现异常，请联系客服，修改充值记录!");
			}
		} catch (Exception e) {
			return new ResponseObject<Object>(ResponseCode.SHOW_EXCEPTION,"已经充值成功，但加入余额中出现异常，请联系客服，修改充值记录!");
		}
		
	}*/
	
	
	@Override
	public ResponseObject<Object> rechargeByStripe(String userId, String brand, String name, String creditNo,
			String securityCode, String expireYear, String expireMonth,
			String amount, String currency) throws ServiceException {
		double minRechargeAmount = 0.5;
		//Charge charge = null;
		Charge charge = new Charge();
		double da = Double.valueOf(amount != null ? amount.trim() : "");
		int money = (int) (da*100);
		if (da < minRechargeAmount) {
			log.error("充值金额必须大于"+minRechargeAmount+"!");
			return new ResponseObject<Object>(ResponseCode.PARAMETER_ERROR,"充值金额必须大于"+minRechargeAmount+"!");
		}
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
	        String order = df.format(new Date())+String.valueOf((int)(Math.random()*9000+1000));
	        charge = StripeUtil.createPayMoneyByStrip(brand,name,creditNo,securityCode,expireYear,expireMonth,money,currency,order);
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
		
		
		String date = DateUtil.date2String(new Date());
		AccountDetail procedureDetail = new AccountDetail(); //手续费
		procedureDetail.setAmount(String.valueOf(money*Double.valueOf(procedureRatio)/100 + Double.valueOf(procedure)));
		procedureDetail.setRealAmount(procedureDetail.getAmount());
		procedureDetail.setUserId(userId);
		procedureDetail.setState(Constant.ACCOUNT_DETAIL_STATE2);
		procedureDetail.setName("信用卡充值手续费");
		procedureDetail.setRemark(charge.getId());
		procedureDetail.setCreateDate(date);
		procedureDetail.setModifyDate(date);
		procedureDetail.setCurrency("美元");
		procedureDetail.setType(Constant.ACCOUNT_DETAIL_TYPE32);
		procedureDetail.setDemandId(null);
		procedureDetail.setThirdNo(charge.getId());
		
		AccountDetail detail = new AccountDetail();
		detail.setAmount(String.valueOf((da-Double.valueOf(procedure))*(1-Double.valueOf(procedureRatio))));
		detail.setRealAmount(detail.getAmount());
		detail.setUserId(userId);
		detail.setCreateDate(date);
		detail.setModifyDate(date);
		detail.setRemark(charge.getId());
		detail.setCurrency("美元");
		detail.setName("信用卡充值");
		detail.setState(Constant.ACCOUNT_DETAIL_STATE2);
		detail.setType(Constant.ACCOUNT_DETAIL_TYPE11);
		detail.setDemandId(null);
		detail.setThirdNo(charge.getId());
		
		try {
			Account a = accountDao.getAccountByUserId(userId);
			double usd= StringUtil.string2Double(a.getUsd());
			double newusd = usd + StringUtil.string2Double(detail.getAmount());
		
			if (this.accountDao.modifyUsdByUserId(userId, String.valueOf(newusd), date) > 0) {
				// pass
				InvoiceNo in = this.invoiceNoDao.getByType(Constant.INVOICE_TYPE0);
				if(in!=null){
					if(this.invoiceNoDao.modifyStateById(in.getId(),date) > 0){
						detail.setInvoiceNo(in.getInvoiceNo());
					}
				}
				InvoiceNo procedureDetailInvoiceNo = this.invoiceNoDao.getByType(Constant.INVOICE_TYPE0);
				if(procedureDetailInvoiceNo!=null){
					if(this.invoiceNoDao.modifyStateById(procedureDetailInvoiceNo.getId(),date) > 0){
						procedureDetail.setInvoiceNo(procedureDetailInvoiceNo.getInvoiceNo());
					}
				}
				this.accountDetailDao.insertAccountDetail(detail);
				this.accountDetailDao.insertAccountDetail(procedureDetail);
				return new ResponseObject<Object>(ResponseCode.SUCCESS_CODE,"充值成功");
			} else {
				return new ResponseObject<Object>(ResponseCode.SHOW_EXCEPTION,"已经扣款成功，但加入余额中出现异常，请联系客服，修改充值记录!");
			}
		} catch (Exception e) { 
			return new ResponseObject<Object>(ResponseCode.SHOW_EXCEPTION,"已经充值成功，但加入余额中出现异常，请联系客服，修改充值记录!");
		}
		
	}

	@Override
	public ResponseObject<Object> rechargeByAnet(String userId,
			String creditNo, String securityCode, String expireYear,
			String expireMonth, String amount) throws ServiceException {
		double minRechargeAmount = 0.5;
		double money = new BigDecimal(amount).setScale(2, RoundingMode.CEILING).doubleValue();;
		CreateTransactionResponse response = null;
		if (money < minRechargeAmount) {
			log.error("充值金额必须大于"+minRechargeAmount+"!");
			return new ResponseObject<Object>(ResponseCode.PARAMETER_ERROR,"充值金额必须大于"+minRechargeAmount+"!");
		}
		response = AentUtil.payCreditcCard(creditNo, securityCode, expireYear, expireMonth, money);
		 if (response!=null) {
	        // If API Response is ok, go ahead and check the transaction response
	        if (response.getMessages().getResultCode() == MessageTypeEnum.OK) {
	        	TransactionResponse result = response.getTransactionResponse();
	        	if(result.getMessages() != null){
	        		log.info("Successfully created transaction with Transaction ID: " + result.getTransId());
	        		log.info("Response Code: " + result.getResponseCode());
	        		log.info("Message Code: " + result.getMessages().getMessage().get(0).getCode());
	        		log.info("Description: " + result.getMessages().getMessage().get(0).getDescription());
	        		log.info("Auth Code: " + result.getAuthCode());
	        	} else {
	        		log.error("Failed Transaction.");
	        		if(response.getTransactionResponse().getErrors() != null){
	        			log.error("Error Code: " + response.getTransactionResponse().getErrors().getError().get(0).getErrorCode());
	        			log.error("Error message: " + response.getTransactionResponse().getErrors().getError().get(0).getErrorText());
	        			return new ResponseObject<Object>(ResponseCode.SHOW_EXCEPTION,"充值错误，错误消息:"+response.getTransactionResponse().getErrors().getError().get(0).getErrorText());
	        		}
	        	}
	        }else {
	        	System.out.println("Failed Transaction.");
	        	if(response.getTransactionResponse() != null && response.getTransactionResponse().getErrors() != null){
	        		log.error("Error Code: " + response.getTransactionResponse().getErrors().getError().get(0).getErrorCode());
	        		log.error("Error message: " + response.getTransactionResponse().getErrors().getError().get(0).getErrorText());
	        		return new ResponseObject<Object>(ResponseCode.SHOW_EXCEPTION,"充值错误，错误消息:"+response.getTransactionResponse().getErrors().getError().get(0).getErrorText());
	        	}else {
	        		log.error("Error Code: " + response.getMessages().getMessage().get(0).getCode());
	        		log.error("Error message: " + response.getMessages().getMessage().get(0).getText());
	        		return new ResponseObject<Object>(ResponseCode.SHOW_EXCEPTION,"充值错误，错误消息:"+response.getMessages().getMessage().get(0).getText());
	        	}
	        }
	  	}else {
	        log.error("Null Response.");
	        return new ResponseObject<Object>(ResponseCode.SHOW_EXCEPTION,"系统服务忙，请稍后再充值!");
	  	}
			
		String procedureRatio ="";
		//String procedureEnable ="1"; 
		String giveRatio =""; 
		//String giveEnable ="0"; 
		try {
			Properties props = PropertiesReader.read(Constant.SYSTEM_PROPERTIES_FILE);
			procedureRatio = props.getProperty("stripe.recharge.procedure.ratio");
			//procedureEnable = props.getProperty("stripe.recharge.procedure.enable");
			giveRatio = props.getProperty("stripe.recharge.give.ratio");
			//giveEnable = props.getProperty("stripe.recharge.give.enable");
		} catch (Exception e) {
			log.error("读取手续费与赠送费的配置错误!");
			return new ResponseObject<Object>(ResponseCode.SHOW_EXCEPTION,"系统服务忙，请稍后再充值，或者请联系客服!");
		}
		String date = DateUtil.date2String(new Date());
		AccountDetail giveDetail = new AccountDetail();  //赠送
		AccountDetail procedureDetail = new AccountDetail(); //手续费
		double give = money*Double.valueOf(giveRatio);
		double procedure = money*Double.valueOf(procedureRatio);
		giveDetail.setAmount(String.valueOf(give));
		giveDetail.setRealAmount(giveDetail.getAmount());
		giveDetail.setUserId(userId);
		giveDetail.setState(Constant.ACCOUNT_DETAIL_STATE2);
		giveDetail.setName("信用卡充值赠送");
		giveDetail.setRemark("ANet:"+response.getTransactionResponse().getTransId()); 
		giveDetail.setCreateDate(date);
		giveDetail.setModifyDate(date);
		giveDetail.setCurrency("美元");
		giveDetail.setType(Constant.ACCOUNT_DETAIL_TYPE16);
		giveDetail.setDemandId(null);
		giveDetail.setThirdNo(response.getRefId());
			
		procedureDetail.setAmount(String.valueOf(procedure));
		procedureDetail.setRealAmount(procedureDetail.getAmount());
		procedureDetail.setUserId(userId);
		procedureDetail.setState(Constant.ACCOUNT_DETAIL_STATE2);
		procedureDetail.setName("信用卡充值手续费");
		procedureDetail.setRemark("ANet:"+response.getTransactionResponse().getTransId());
		procedureDetail.setCreateDate(date);
		procedureDetail.setModifyDate(date);
		procedureDetail.setCurrency("美元");
		procedureDetail.setType(Constant.ACCOUNT_DETAIL_TYPE32);
		procedureDetail.setDemandId(null);
		procedureDetail.setThirdNo(response.getRefId());
		
		AccountDetail detail = new AccountDetail();
		detail.setAmount(String.valueOf(money));
		detail.setRealAmount(detail.getAmount());
		detail.setUserId(userId);
		detail.setCreateDate(date);
		detail.setModifyDate(date);
		detail.setType(Constant.ACCOUNT_DETAIL_TYPE15);
		detail.setRemark("ANet:"+response.getTransactionResponse().getTransId());
		detail.setCurrency("美元");
		detail.setName("信用卡充值");
		detail.setState(Constant.ACCOUNT_DETAIL_STATE2);
		detail.setDemandId(null);
		detail.setThirdNo(response.getRefId());
		try {
			Account a = accountDao.getAccountByUserId(userId);
			double usd= StringUtil.string2Double(a.getUsd());
			double newusd = usd + StringUtil.string2Double(detail.getAmount()) 
					+ StringUtil.string2Double(giveDetail.getAmount())
					- StringUtil.string2Double(procedureDetail.getAmount());
			
			if (this.accountDao.modifyUsdByUserId(userId, String.valueOf(newusd), date) > 0) {
				// pass
				InvoiceNo in = this.invoiceNoDao.getByType(Constant.INVOICE_TYPE0);
				if(in!=null){
					if(this.invoiceNoDao.modifyStateById(in.getId(),date) > 0){
						detail.setInvoiceNo(in.getInvoiceNo());
					}
				}
				InvoiceNo procedureDetailInvoiceNo = this.invoiceNoDao.getByType(Constant.INVOICE_TYPE0);
				if(procedureDetailInvoiceNo!=null){
					if(this.invoiceNoDao.modifyStateById(procedureDetailInvoiceNo.getId(),date) > 0){
						procedureDetail.setInvoiceNo(procedureDetailInvoiceNo.getInvoiceNo());
					}
				}
				InvoiceNo giveDetailInvoiceNo = this.invoiceNoDao.getByType(Constant.INVOICE_TYPE0);
				if(giveDetailInvoiceNo!=null){
					if(this.invoiceNoDao.modifyStateById(giveDetailInvoiceNo.getId(),date) > 0){
						giveDetail.setInvoiceNo(giveDetailInvoiceNo.getInvoiceNo());
					}
				}
				this.accountDetailDao.insertAccountDetail(detail);
				this.accountDetailDao.insertAccountDetail(procedureDetail);
				this.accountDetailDao.insertAccountDetail(giveDetail);
				return new ResponseObject<Object>(ResponseCode.SHOW_EXCEPTION,"充值成功");
			} else {
				return new ResponseObject<Object>(ResponseCode.SHOW_EXCEPTION,"已经扣款成功，但加入余额中出现异常，请联系客服，修改充值记录!");
			}
		} catch (Exception e) {
			return new ResponseObject<Object>(ResponseCode.SHOW_EXCEPTION,"已经充值成功，但加入余额中出现异常，请联系客服，修改充值记录!");
		}
	}

	
}
