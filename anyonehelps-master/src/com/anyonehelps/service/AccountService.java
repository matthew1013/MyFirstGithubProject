package com.anyonehelps.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.Account;
import com.anyonehelps.model.AccountDetail;
import com.anyonehelps.model.PageSplit;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.model.User;
import com.anyonehelps.model.Withdrawals;


public interface AccountService {
	
	public ResponseObject<Object> addAccountDetail(AccountDetail detail) throws ServiceException;
	/**
	 * 增加取现
	 * @param withdrawals
	 * @return
	 * @throws ServiceException
	 */
	public ResponseObject<Object> addWithdrawals(Withdrawals withdrawals,User user) throws ServiceException;
	public ResponseObject<PageSplit<Withdrawals>> search(String userId, String type, 
			String state,int pageSize, int pageNow) throws ServiceException;

	public ResponseObject<AccountDetail> getAccountDetailById(String id) throws ServiceException;

	//public ResponseObject<Object> modifyAccountDetail(AccountDetail detail) throws ServiceException;
	
	//public ResponseObject<Object> insertAccountDetail(AccountDetail detail) throws ServiceException;
	
	//public ResponseObject<Object> procedure(AccountDetail detail) throws ServiceException;
	/**
	 * 任务消费
	 * @param detail
	 * @return
	 * @throws ServiceException
	 */
	//public ResponseObject<Object> consume(AccountDetail detail) throws ServiceException;
	
	public ResponseObject<PageSplit<AccountDetail>> search(String userId, String key,
	        String state, List<String> types, String demandId, String sdate, String edate, int pageSize, int pageNow) throws ServiceException;

	public ResponseObject<Account> getAccountByUserId(String userId) throws ServiceException;
	
	public ResponseObject<Object> PorcPaypalNotify(HttpServletRequest request) throws ServiceException;
	/**
	 * 模拟paypal测试数据
	 */
	//public ResponseObject<Object> PorcPaypalNotifyTest(HttpServletRequest request) throws ServiceException;
	public ResponseObject<Object> transfer(String userId, String nickName, String userAccount,
			String amount) throws ServiceException;
	
	/**
	 * stripe 信用卡充值
	 * @param brand
	 * @param name
	 * @param creditNo
	 * @param securityCode
	 * @param expireYear
	 * @param expireMonth
	 * @param money
	 * @param currency
	 * @return
	 * @throws ServiceException
	 */
	public ResponseObject<Object> rechargeByStripe(String userId, String brand, String name, String creditNo,
			String securityCode, String expireYear, String expireMonth,
			String amount, String currency) throws ServiceException;

	/**
	 * anet 信用卡充值
	 * @param userId
	 * @param creditNo
	 * @param securityCode
	 * @param expireYear
	 * @param expireMonth
	 * @param amount
	 * @return
	 * @throws ServiceException
	 */
	public ResponseObject<Object> rechargeByAnet(String userId, String creditNo, String securityCode, String expireYear,
			String expireMonth, String amount) throws ServiceException;
	
}
