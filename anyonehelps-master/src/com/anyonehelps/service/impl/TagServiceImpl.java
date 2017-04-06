package com.anyonehelps.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anyonehelps.common.constants.ResponseCode;
import com.anyonehelps.common.util.DateUtil;
import com.anyonehelps.common.util.ExceptionUtil;
import com.anyonehelps.dao.TagDao;
import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.model.Tag;
import com.anyonehelps.service.TagService;

@Service("tagService")
public class TagServiceImpl extends BasicService implements TagService {
	@Autowired
	private TagDao tagDao;

	@Override
	public ResponseObject<Object> addTag(Tag tag)
			throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>();
		if(tag==null){
			return new ResponseObject<Object>(ResponseCode.PARAMETER_ERROR,"参数无效");
		}
		String date = DateUtil.date2String(new Date());
		tag.setCreateDate(date);
		try {
			int nR = this.tagDao.insertTag(tag);
			if(nR>0){
				responseObj.setCode(ResponseCode.SUCCESS_CODE);
			}else{
				//进行事务回滚
				throw new Exception();
			}
			
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException("添加标签失败，请重试！", e);
		}
		return responseObj;
	}

	@Override
	public ResponseObject<List<Tag>> searchAll()
			throws ServiceException {
		ResponseObject<List<Tag>> responseObj = new ResponseObject<List<Tag>>(ResponseCode.SUCCESS_CODE);
		try {
			List<Tag> tags = this.tagDao.getAll();
			responseObj.setData(tags);
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException("获取标签失败！", e);
		}
		return responseObj;
		
	}

	@Override
	public ResponseObject<Object> deleteById(String id)
			throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>(ResponseCode.SUCCESS_CODE);
		if (id == null || id.equals("")) {
			responseObj.setCode(ResponseCode.PARAMETER_ERROR);
			responseObj.setMessage("参数无效");
			return responseObj;
		}
		try {
			
			int result = this.tagDao.deleteById(id);
			if (result > 0) {
				responseObj.setCode(ResponseCode.SUCCESS_CODE);
				return responseObj;
			} else {
				//进行事务回滚
				throw new Exception();
			}
		} catch (Exception e) {
			throw ExceptionUtil.handle2ServiceException("删除标签失败！", e);
		}
	}

	

}
