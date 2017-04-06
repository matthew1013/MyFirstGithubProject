package com.anyonehelps.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.anyonehelps.common.constants.Constant;
import com.anyonehelps.common.constants.ResponseCode;
import com.anyonehelps.common.util.DateUtil;
import com.anyonehelps.common.util.ExceptionUtil;
import com.anyonehelps.common.util.StringUtil;
import com.anyonehelps.dao.AccountDao;
import com.anyonehelps.dao.AccountDetailDao;
import com.anyonehelps.dao.InvoiceNoDao;
import com.anyonehelps.dao.SpecialtyDao;
import com.anyonehelps.dao.SpecialtyTypeDao;
import com.anyonehelps.dao.SpecialtyUserDao;
import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.Account;
import com.anyonehelps.model.AccountDetail;
import com.anyonehelps.model.InvoiceNo;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.model.Specialty;
import com.anyonehelps.model.SpecialtyType;
import com.anyonehelps.model.SpecialtyUser;
import com.anyonehelps.service.SpecialtyService;

@Service("specialtyService")
public class SpecialtyServiceImpl extends BasicService implements SpecialtyService {
	@Autowired
	private SpecialtyTypeDao specialtyTypeDao;
	@Autowired
	private SpecialtyUserDao specialtyUserDao;
	//@Autowired
	//private SpecialtyCustomDao specialtyCustomDao;
	@Autowired
	private SpecialtyDao specialtyDao;
	
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private AccountDetailDao accountDetailDao;
	@Autowired
	private InvoiceNoDao invoiceNoDao;
	
	@Value(value = "${specialty_auth_amount}")
	private double specialtyAuthAmount;
	
	public ResponseObject<List<SpecialtyType>> getAllSpecialtyType()
			throws ServiceException {
		ResponseObject<List<SpecialtyType>> responseObj = new ResponseObject<List<SpecialtyType>>(ResponseCode.SUCCESS_CODE);
		try {
			responseObj.setData(this.specialtyTypeDao.getAllSpecialtyType());
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
		return responseObj;
	}

	public ResponseObject<List<SpecialtyUser>> getSpecialtyUser(String userId, String type, String state)
			throws ServiceException {
		ResponseObject<List<SpecialtyUser>> responseObj = new ResponseObject<List<SpecialtyUser>>(ResponseCode.SUCCESS_CODE);
		try {
			responseObj.setData(this.specialtyUserDao.getByUserId(userId, type, state));
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
		return responseObj;
	}

	public ResponseObject<Object> deleteSpecialtyUser(String userId, String id)
			throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>(ResponseCode.SUCCESS_CODE);
		try {
			int nRet = this.specialtyUserDao.delete(userId, id);
			if(nRet>0){
				
			}else{
				responseObj.setCode(ResponseCode.SPECIALTY_DELETE_FAILURE);
			}
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
		return responseObj;
	}

	public ResponseObject<Object> addSpecialtyUser(SpecialtyUser su) throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>(ResponseCode.SUCCESS_CODE);
		try {
			int nResult = this.specialtyUserDao.countByName(su.getUserId(), su.getSpecialtyId(), su.getName());
			if(nResult>0){
				return new ResponseObject<Object>( ResponseCode.SPECIALTY_INSTER_FAILURE, "您已添加该技能，无需再次添加！");
			}
			int nRet = this.specialtyUserDao.insertSpecialtyUser(su);
			if(nRet>0){ 
				responseObj.setData(su);  
			}else{
				responseObj.setCode(ResponseCode.SPECIALTY_INSTER_FAILURE);
			}
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
		return responseObj;
	}

	
	@Override
	public ResponseObject<Object> authSpecialtyUser(SpecialtyUser su)
			throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>(ResponseCode.SUCCESS_CODE);
		String date = DateUtil.date2String(new Date());
		su.setAuthDate(date);
		try {
			SpecialtyUser s= this.specialtyUserDao.getById(su.getUserId(), su.getId());
			if(s!=null){
				if(s.getState()==Constant.SPECIALTY_STATE_AUTH_ING){
					return new ResponseObject<Object>( ResponseCode.SPECIALTY_AUTH_FAILURE, "技能正在认证中,请等待认证结果");
				}
				if(s.getState()==Constant.SPECIALTY_STATE_AUTH_SUCC){
					return new ResponseObject<Object>( ResponseCode.SPECIALTY_AUTH_FAILURE, "技能已经认证成功,无需再次提交");
				}
				su.setState(Constant.SPECIALTY_STATE_AUTH_ING);
				
				Specialty specialty = s.getSpecialty();
				String specialtyName = "";
				if(Constant.SPECIALTY_TYPE0.equals(s.getType())){
					specialtyName = specialty.getName();
				}else if(Constant.SPECIALTY_TYPE1.equals(s.getType())){
					specialtyName = s.getName();
				}
				
				
				AccountDetail detail = new AccountDetail(); 
				detail.setAmount(String.valueOf(specialtyAuthAmount));
				detail.setRealAmount(detail.getAmount());
				detail.setUserId(su.getUserId());
				detail.setState(Constant.ACCOUNT_DETAIL_STATE2);
				detail.setName("认证技能费用");
				detail.setCreateDate(date);
				detail.setCurrency("美元");
				detail.setModifyDate(date);
				detail.setType(Constant.ACCOUNT_DETAIL_TYPE34);
				detail.setRemark("认证技能:"+specialtyName);
				detail.setDemandId(null);
				detail.setThirdNo(""); 
				detail.setRemark1("认证技能:"+specialtyName);
				//发票号
				InvoiceNo in = this.invoiceNoDao.getByType(Constant.INVOICE_TYPE6);
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
					return new ResponseObject<Object>( ResponseCode.SPECIALTY_AUTH_FAILURE, "余额不足，请充足后再提交认证！");
				}
					
				// 账户支付，修改账户余额
				if (this.accountDao.modifyUsdByUserId(a.getUserId(), String.valueOf(newusd), date) > 0) {
					// pass
					int nRet = this.specialtyUserDao.authSpecialtyUser(su);
					if(nRet<=0){
						throw new Exception();
					}
				} else {
					throw new Exception();
				}
				this.accountDetailDao.insertAccountDetail(detail);
			}else{
				return new ResponseObject<Object>( ResponseCode.SPECIALTY_AUTH_FAILURE, "您没有选择该技能，无法认证！");
			}
			
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
		return responseObj;
	}

	@Override
	public ResponseObject<Object> getSpecialtyUserById(String userId, String id) throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>(ResponseCode.SUCCESS_CODE);
		try {
			SpecialtyUser s= this.specialtyUserDao.getById(userId, id);
			responseObj.setData(s);
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
		return responseObj;
	}
}
