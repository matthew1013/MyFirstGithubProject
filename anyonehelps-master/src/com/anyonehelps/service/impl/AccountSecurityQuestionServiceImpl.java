package com.anyonehelps.service.impl;

import java.util.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anyonehelps.common.constants.ResponseCode;
import com.anyonehelps.common.util.DateUtil;
import com.anyonehelps.common.util.ExceptionUtil;
import com.anyonehelps.common.util.UserUtil;
import com.anyonehelps.dao.AccountSecurityQuestionDao;
import com.anyonehelps.dao.UserDao;
import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.AccountSecurityQuestion;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.model.User;
import com.anyonehelps.service.AccountSecurityQuestionService;

@Service("asqService")
public class AccountSecurityQuestionServiceImpl implements AccountSecurityQuestionService {
	//private static final Logger log = Logger.getLogger(AccountSecurityQuestionServiceImpl.class);
	
	@Autowired
	private AccountSecurityQuestionDao asqDao;
	@Autowired
	private UserDao userDao;
	
	public ResponseObject<Object> addASQ(AccountSecurityQuestion asq) throws ServiceException {
		try {
			String date = DateUtil.date2String(new Date());
			asq.setCreateDate(date);
			AccountSecurityQuestion asqTemp = this.asqDao.getByUserId(asq.getUserId());
			if(asqTemp==null){ 
				int nResult = this.asqDao.insert(asq);
				if (nResult > 0) {
					return new ResponseObject<Object>(ResponseCode.SUCCESS_CODE);
				} else {
					return new ResponseObject<Object>(ResponseCode.ACCOUNT_INSERT_FAILURE, "插入数据库失败");
				}
			}else{
				return new ResponseObject<Object>(ResponseCode.ACCOUNT_INSERT_FAILURE, "保密问题已经存在，无需再次提交！");
			}
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
	}


	@Override
	public ResponseObject<Object> getAsqByUserId(String userId) throws ServiceException {
		try {
			ResponseObject<Object> result = new ResponseObject<Object>(ResponseCode.SUCCESS_CODE);
			AccountSecurityQuestion asq = this.asqDao.getByUserId(userId);
			if(asq!=null){
				asq.setAnswer1(null);
				asq.setAnswer2(null);
				asq.setAnswer3(null);
			}
			result.setData(asq);
			return result;
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
	}


	@Override
	public ResponseObject<Object> getAsqByEmailOrPhone(String email,
			String areaCode, String phone) throws ServiceException {
		boolean isEmail = true;
		if (!UserUtil.validateEmail(email)) {
			isEmail = false;
			if (!UserUtil.validateAreaCode(areaCode)) {
				return new ResponseObject<Object>(ResponseCode.USER_PHONE_ERROR, "手机所属地区码输入错误，请重新输入!");
			} else if (!UserUtil.validatePhone(phone)) {
				return new ResponseObject<Object>(ResponseCode.USER_NICK_NAME_ERROR, "手机号码输入不正确，请重新输入!");
			}
		}
		
		try {
			User user = null;
			if(isEmail){
				user = this.userDao.getUserByAccount(email);
			}else {
				user= this.userDao.getUserByPhone(areaCode, phone);
			}
			if(user==null){
				return new ResponseObject<Object>(ResponseCode.PARAMETER_ERROR, "用户不存在，请确认你输入是否正确！");
			}
			
			ResponseObject<Object> result = new ResponseObject<Object>(ResponseCode.SUCCESS_CODE);
			AccountSecurityQuestion asq = this.asqDao.getByUserId(user.getId());
			if(asq!=null){
				asq.setAnswer1(null);
				asq.setAnswer2(null);
				asq.setAnswer3(null);
				asq.setUserId(user.getId());
			}
			result.setData(asq);
			return result;
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException(e);
		}
	}


	@Override
	public ResponseObject<Object> checkSecurityQuestion(String userId,
			String answer1, String answer2, String answer3)
			throws ServiceException {
		ResponseObject<Object> result = new ResponseObject<Object>();
		try {
			AccountSecurityQuestion asq = this.asqDao.getByUserId(userId);
			if(asq==null){
				result.setCode(ResponseCode.PARAMETER_ERROR);
				result.setMessage("您没有设置密保问题，请使用邮箱或者手机找回密码！");
				return result;
			}
			if(asq.getAnswer1().equals(answer1)
					&&asq.getAnswer2().equals(answer2)
					&&asq.getAnswer3().equals(answer3)){
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
}
