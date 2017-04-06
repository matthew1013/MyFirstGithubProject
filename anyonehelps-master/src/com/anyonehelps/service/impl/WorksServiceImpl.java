package com.anyonehelps.service.impl;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anyonehelps.common.constants.ResponseCode;
import com.anyonehelps.common.util.DateUtil;
import com.anyonehelps.common.util.ExceptionUtil;
import com.anyonehelps.dao.WorksDao;
import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.model.Works;
import com.anyonehelps.service.WorksService;

@Service("worksService")
public class WorksServiceImpl extends BasicService implements WorksService {
	@Autowired
	private WorksDao worksDao;

	@Override
	public ResponseObject<Object> addWorks(Works works)
			throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>();
		if(works==null){
			return new ResponseObject<Object>(ResponseCode.PARAMETER_ERROR,"参数无效");
		}
		String date = DateUtil.date2String(new Date());
		works.setCreateDate(date);
		try {
			int nResult = this.worksDao.countByUserId(works.getUserId());
			if(nResult>8){
				responseObj.setCode(ResponseCode.WORKS_MAX_LIMIT);
				responseObj.setMessage("作品已经超出了最大限制数");
				return responseObj; 
			}
			int nR = this.worksDao.insertWorks(works);
			if(nR>0){
				responseObj.setCode(ResponseCode.SUCCESS_CODE);
			}else{
				//进行事务回滚
				throw new Exception();
			}
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException("添加作品展示失败，请重试！", e);
		}
		return responseObj;
	}

	@Override
	public ResponseObject<List<Works>> searchByUserId(String userId)
			throws ServiceException {
		ResponseObject<List<Works>> responseObj = new ResponseObject<List<Works>>(ResponseCode.SUCCESS_CODE);
		try {
			List<Works> works = this.worksDao.getByUserId(userId);
			responseObj.setData(works);
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException("获取展示失败！", e);
		}
		return responseObj;
	}

	@Override
	public ResponseObject<Object> deleteWorks(String userId, String id)
			throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>(ResponseCode.SUCCESS_CODE);
		if (id == null || id.equals("")) {
			responseObj.setCode(ResponseCode.PARAMETER_ERROR);
			responseObj.setMessage("参数无效");
			return responseObj;
		}
		try {
			Works works = this.worksDao.getById(id);
			if(works!=null){
				if(works.getUserId().equals(userId)){
					/* 开始删除文件 */
					File file = new File(works.getUrl());  
				    if (file.isFile() && file.exists()) {  
				        file.delete();  
				    }  
				    /* 结束删除文件 */
					int result = this.worksDao.deleteById(userId, id);
					if (result > 0) {
						responseObj.setCode(ResponseCode.SUCCESS_CODE);
						return responseObj;
					} else {
						//进行事务回滚
						throw new Exception();
					}
				}else{
					responseObj.setCode(ResponseCode.WORKS_NOT_EXISTS);
					responseObj.setMessage("这不是您的作品,请刷新页面再操作!");
					return responseObj;
				}
			}else{
				responseObj.setCode(ResponseCode.WORKS_NOT_EXISTS);
				responseObj.setMessage("作品不存在,请刷新页面再操作!");
				return responseObj;
			}
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException("删除作品展示失败！", e);
		}
	}

	@Override
	public ResponseObject<Object> modifyWorkTitle(String userId, String id,
			String title) throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>(ResponseCode.SUCCESS_CODE);
		
		try {
			Works works = this.worksDao.getById(id);
			if(works!=null){
				if(works.getUserId().equals(userId)){
					int result = this.worksDao.modifyTitle(id, title);
					if (result > 0) {
						responseObj.setCode(ResponseCode.SUCCESS_CODE);
					} else {
						responseObj.setCode(ResponseCode.WORKs_MOFIFY_FAIL);
					}
				}else{
					responseObj.setCode(ResponseCode.WORKS_NOT_EXISTS);
					responseObj.setMessage("这不是您的作品，无法修改显示名,请刷新页面再操作!");
				}
			}else{
				responseObj.setCode(ResponseCode.WORKS_NOT_EXISTS);
				responseObj.setMessage("作品不存在，无法修改显示名,请刷新页面再操作!");
				return responseObj;
			}
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException("修改作品显示名失败！", e);
		}
		return responseObj;
	}
}
