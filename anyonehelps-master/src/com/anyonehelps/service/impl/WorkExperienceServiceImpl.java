package com.anyonehelps.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anyonehelps.common.constants.ResponseCode;
import com.anyonehelps.common.util.DateUtil;
import com.anyonehelps.common.util.ExceptionUtil;
import com.anyonehelps.dao.WorkExperienceDao;
import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.model.WorkExperience;
import com.anyonehelps.service.WorkExperienceService;

@Service("workExperienceService")
public class WorkExperienceServiceImpl extends BasicService implements WorkExperienceService {
	@Autowired
	private WorkExperienceDao workExperienceDao;

	@Override
	public ResponseObject<Object> addWE(WorkExperience we)
			throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>();
		if(we==null){
			return new ResponseObject<Object>(ResponseCode.PARAMETER_ERROR,"参数无效");
		}
		String date = DateUtil.date2String(new Date());
		we.setCreateDate(date);
		we.setModifyDate(date);
		try {
			int nR = this.workExperienceDao.insertWE(we);
			if(nR>0){
				responseObj.setCode(ResponseCode.SUCCESS_CODE);
				responseObj.setData(we);
			}else{
				//进行事务回滚
				throw new Exception();
			}
			
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException("添加工作经验失败，请重试！", e);
		}
		return responseObj;
	}

	@Override
	public ResponseObject<List<WorkExperience>> searchByUserId(String userId)
			throws ServiceException {
		ResponseObject<List<WorkExperience>> responseObj = new ResponseObject<List<WorkExperience>>(ResponseCode.SUCCESS_CODE);
		try {
			List<WorkExperience> wes = this.workExperienceDao.getByUserId(userId);
			responseObj.setData(wes);
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException("获取工作经验失败！", e);
		}
		return responseObj;
		
	}

	@Override
	public ResponseObject<Object> deleteWE(String userId, String id)
			throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>(ResponseCode.SUCCESS_CODE);
		if (id == null || id.equals("")) {
			responseObj.setCode(ResponseCode.PARAMETER_ERROR);
			responseObj.setMessage("参数无效");
			return responseObj;
		}
		try {
			
			int result = this.workExperienceDao.deleteById(userId, id);
			if (result > 0) {
				responseObj.setCode(ResponseCode.SUCCESS_CODE);
				return responseObj;
			} else {
				//进行事务回滚
				throw new Exception();
			}
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException("删除工作经验失败！", e);
		}
	}

	@Override
	public ResponseObject<Object> modifyWE(WorkExperience we)
			throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>();
		if(we==null){
			return new ResponseObject<Object>(ResponseCode.PARAMETER_ERROR,"参数无效");
		}
		String date = DateUtil.date2String(new Date());
		we.setModifyDate(date);
		try {
			WorkExperience w = this.workExperienceDao.getById(we.getId());
			if(w==null){
				return new ResponseObject<Object>(ResponseCode.WORK_EXPERIENCE_NOT_EXISTS,"无法修改，原因不存在该工作经验！！");
			}
			if(!w.getUserId().equals(we.getUserId())){
				return new ResponseObject<Object>(ResponseCode.PARAMETER_ERROR,"无法修改，该工作经验不是您的工作经验！！");
			}
			
			int nR = this.workExperienceDao.modifyWE(we);
			if(nR>0){
				responseObj.setCode(ResponseCode.SUCCESS_CODE);
				responseObj.setData(we);
			}else{
				//进行事务回滚
				throw new Exception();
			}
			
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException("修改工作经验失败，请重试！", e);
		}
		return responseObj;
	}

	@Override
	public ResponseObject<Object> modifyEnclosureName(String userId, String id,
			String enclosureName) throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>(ResponseCode.SUCCESS_CODE);
		
		try {
			WorkExperience we = this.workExperienceDao.getById(id);
			if(we!=null){
				if(we.getUserId().equals(userId)){
					int result = this.workExperienceDao.modifyEnclosureName(id, enclosureName);
					if (result > 0) {
						responseObj.setCode(ResponseCode.SUCCESS_CODE);
					} else {
						responseObj.setCode(ResponseCode.WORKs_MOFIFY_FAIL);
					}
				}else{
					responseObj.setCode(ResponseCode.WORKS_NOT_EXISTS);
					responseObj.setMessage("这不是您的工作经历，无法修改附件显示名,请刷新页面再操作!");
				}
			}else{
				responseObj.setCode(ResponseCode.WORKS_NOT_EXISTS);
				responseObj.setMessage("工作不存在，无法修改附件显示名,请刷新页面再操作!");
				return responseObj;
			}
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException("修改附件显示名失败！", e);
		}
		return responseObj;
	}

}
