package com.anyonehelps.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anyonehelps.common.constants.ResponseCode;
import com.anyonehelps.common.util.DateUtil;
import com.anyonehelps.common.util.ExceptionUtil;
import com.anyonehelps.dao.EmailSendDao;
import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.EmailSend;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.service.EmailSendService;

@Service("emailSendService")
public class EmailSendServiceImpl extends BasicService implements EmailSendService {
	@Autowired
	private EmailSendDao emailSendDao;

	@Override
	public ResponseObject<Object> addEmailSend(EmailSend es) throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>();
		if(es==null){
			return new ResponseObject<Object>(ResponseCode.PARAMETER_ERROR,"参数无效");
		}
		String date = DateUtil.date2String(new Date());
		es.setCreateDate(date);
		es.setModifyDate(date);
		try {
			int nR = this.emailSendDao.insert(es);
			if(nR>0){
				responseObj.setCode(ResponseCode.SUCCESS_CODE);
				responseObj.setData(es);
			}else{
				//进行事务回滚
				throw new Exception();
			}
			
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException("服务器出错。请重试！", e);
		}
		return responseObj;
	}

}
