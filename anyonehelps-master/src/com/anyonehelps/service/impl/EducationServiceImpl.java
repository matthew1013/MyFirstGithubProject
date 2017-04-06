package com.anyonehelps.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anyonehelps.common.constants.ResponseCode;
import com.anyonehelps.common.util.DateUtil;
import com.anyonehelps.common.util.ExceptionUtil;
import com.anyonehelps.dao.EducationDao;
import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.Education;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.service.EducationService;

@Service("educationService")
public class EducationServiceImpl extends BasicService implements EducationService {
	@Autowired
	private EducationDao educationDao;

	@Override
	public ResponseObject<Object> addEducation(Education education)
			throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>();
		if(education==null){
			return new ResponseObject<Object>(ResponseCode.PARAMETER_ERROR,"参数无效");
		}
		String date = DateUtil.date2String(new Date());
		education.setCreateDate(date);
		education.setModifyDate(date);
		try {
			int nR = this.educationDao.insertEducation(education);
			if(nR>0){
				responseObj.setCode(ResponseCode.SUCCESS_CODE);
				responseObj.setData(education);
			}else{
				//进行事务回滚
				throw new Exception();
			}
			
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException("添加教育经历失败，请重试！", e);
		}
		return responseObj;
	}

	@Override
	public ResponseObject<List<Education>> searchByUserId(String userId)
			throws ServiceException {
		ResponseObject<List<Education>> responseObj = new ResponseObject<List<Education>>(ResponseCode.SUCCESS_CODE);
		try {
			List<Education> educations = this.educationDao.getByUserId(userId);
			responseObj.setData(educations);
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException("获取教育经历失败！", e);
		}
		return responseObj;
		
	}

	@Override
	public ResponseObject<Object> deleteEducation(String userId, String id)
			throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>(ResponseCode.SUCCESS_CODE);
		if (id == null || id.equals("")) {
			responseObj.setCode(ResponseCode.PARAMETER_ERROR);
			responseObj.setMessage("参数无效");
			return responseObj;
		}
		try {
			
			int result = this.educationDao.deleteById(userId, id);
			if (result > 0) {
				responseObj.setCode(ResponseCode.SUCCESS_CODE);
				return responseObj;
			} else {
				//进行事务回滚
				throw new Exception();
			}
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException("删除教育经历失败！", e);
		}
	}

	@Override
	public ResponseObject<Object> modifyEducation(Education education)
			throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>();
		if(education==null){
			return new ResponseObject<Object>(ResponseCode.PARAMETER_ERROR,"参数无效");
		}
		String date = DateUtil.date2String(new Date());
		education.setModifyDate(date);
		try {
			Education e = this.educationDao.getById(education.getId());
			if(e==null){
				return new ResponseObject<Object>(ResponseCode.EDUCATION_NOT_EXISTS,"无法修改，原因不存在该教育经历！！");
			}
			if(!e.getUserId().equals(education.getUserId())){
				return new ResponseObject<Object>(ResponseCode.PARAMETER_ERROR,"无法修改，该教育经历不是您的教育经历！！");
			}
			
			int nR = this.educationDao.modifyEducation(education);
			if(nR>0){
				responseObj.setCode(ResponseCode.SUCCESS_CODE);
				responseObj.setData(education);
			}else{
				//进行事务回滚
				throw new Exception();
			}
			
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException("修改教育经历失败，请重试！", e);
		}
		return responseObj;
	}

	@Override
	public ResponseObject<Object> modifyEnclosureName(String userId, String id,
			String enclosureName) throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>(ResponseCode.SUCCESS_CODE);
		
		try {
			Education education = this.educationDao.getById(id);
			if(education!=null){
				if(education.getUserId().equals(userId)){
					int result = this.educationDao.modifyEnclosureName(id, enclosureName);
					if (result > 0) {
						responseObj.setCode(ResponseCode.SUCCESS_CODE);
					} else {
						responseObj.setCode(ResponseCode.WORKs_MOFIFY_FAIL);
					}
				}else{
					responseObj.setCode(ResponseCode.WORKS_NOT_EXISTS);
					responseObj.setMessage("这不是您的教育经历，无法修改附件显示名,请刷新页面再操作!");
				}
			}else{
				responseObj.setCode(ResponseCode.WORKS_NOT_EXISTS);
				responseObj.setMessage("教育经历不存在，无法修改附件显示名,请刷新页面再操作!");
				return responseObj;
			}
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException("修改附件显示名失败！", e);
		}
		return responseObj;
	}

}
