package com.anyonehelps.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anyonehelps.common.constants.ResponseCode;
import com.anyonehelps.common.util.DateUtil;
import com.anyonehelps.common.util.ExceptionUtil;
import com.anyonehelps.dao.SmsSendDao;
import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.model.SmsSend;
import com.anyonehelps.service.SmsSendService;

@Service("smsSendService")
public class SmsSendServiceImpl extends BasicService implements SmsSendService {
	@Autowired
	private SmsSendDao smsSendDao;

	@Override
	public ResponseObject<Object> addSmsSend(SmsSend ss) throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>();
		if(ss==null){
			return new ResponseObject<Object>(ResponseCode.PARAMETER_ERROR,"参数无效");
		}
		String date = DateUtil.date2String(new Date());
		ss.setCreateDate(date);
		ss.setModifyDate(date);
		try {
			int nR = this.smsSendDao.insert(ss);
			if(nR>0){
				responseObj.setCode(ResponseCode.SUCCESS_CODE);
				responseObj.setData(ss);
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
