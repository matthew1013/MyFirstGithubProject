package com.anyonehelps.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.anyonehelps.common.constants.Constant;
import com.anyonehelps.common.constants.ResponseCode;
import com.anyonehelps.common.util.AmazonS3Samples;
import com.anyonehelps.common.util.DemandUtil;
import com.anyonehelps.common.util.StringUtil;
import com.anyonehelps.common.util.UserUtil;
import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.Education;
import com.anyonehelps.model.FileMeta;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.service.EducationService;

@Controller
public class EducationController extends BasicController {
	private static final long serialVersionUID = 6517783699624173105L;

	private static final Logger log = Logger.getLogger(EducationController.class);

	@Resource(name = "educationService")
	private EducationService educationService;
	
	//文件类型
	@Value(value = "${education_file_type}")
	private String educationFileType;
	//文件保存路径
	@Value(value = "${education_file_save_dir}")
	private String educationFileSaveDir;
	//文件最大大小
	@Value(value = "${education_file_size}")
	private long educationFileSize;

	@Value(value = "${amazon_s3_url}")
	private String amazonS3Url;
		
	@RequestMapping(value = "/education/upload", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> educationUpload(
			HttpServletRequest request,
			@RequestParam(value = "education_upload", required = false) MultipartFile mpf) throws ServiceException {
		if (mpf == null) {
			return generateResponseObject( ResponseCode.WORKS_URL_NOT_EXISTS, "没有附件,请选择附件后再上传!");
		}
		String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
		//LinkedList<FileMeta> files = new LinkedList<FileMeta>();
		FileMeta fileMeta = null;
			
		// 解决火狐的反斜杠问题 kai 20151006
		String filetype = this.educationFileType;// 要上传的文件类型
			
		//2.3 create new fileMeta
		fileMeta = new FileMeta();
		fileMeta.setFileName(mpf.getOriginalFilename());
		fileMeta.setFileSize(mpf.getSize()/1024+" Kb");
		fileMeta.setFileType(mpf.getContentType());
		if (mpf != null && mpf.getSize() > 0) {
			if (mpf.getSize() > this.educationFileSize) { 
				return generateResponseObject(ResponseCode.WORKS_URL_ERROR, "允许上传文件最大为20MB!");
			}
			String originalName = mpf.getOriginalFilename();
			if (!DemandUtil.validateFileType(originalName, filetype)) {
				return generateResponseObject( ResponseCode.WORKS_URL_ERROR, "上传附件格式不正确,请重新尝试!");
			}
			int index = originalName.lastIndexOf('.');
			index = Math.max(index, 0);
			String saveFileName = this.educationFileSaveDir + "/" + userId + "_"
					+ StringUtil.generateRandomString(5) + "_"
					+ StringUtil.generateRandomInteger(5)
					+ originalName.substring(index);
			fileMeta.setSaveFileName(this.amazonS3Url + saveFileName);
			
			CommonsMultipartFile cf= (CommonsMultipartFile)mpf; 
			DiskFileItem fi = (DiskFileItem)cf.getFileItem(); 
			AmazonS3Samples amazonS3Samples = new AmazonS3Samples();
			try {
				amazonS3Samples.pushFile(saveFileName, fi.getStoreLocation());
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error("上传文件失败,请重试！", e);
				return generateResponseObject(ResponseCode.WORKS_URL_ERROR,"上传文件失败,请重试!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace(); 
				log.error("上传文件失败,请重试！", e);
				return generateResponseObject(ResponseCode.WORKS_URL_ERROR,"上传文件失败,请重试!");
			} catch (org.jets3t.service.ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error("上传文件失败,请重试！", e);
				return generateResponseObject(ResponseCode.WORKS_URL_ERROR,"上传文件失败,请重试!");
			}
			
			
		}
			
		ResponseObject<Object> responseObj = new ResponseObject<Object>();
		responseObj.setCode(ResponseCode.SUCCESS_CODE);
		responseObj.setData(fileMeta);
		return responseObj;
	}
	
	@ExceptionHandler(Exception.class)         
	@ResponseBody
	public ResponseObject<Object> handleException(Exception ex,HttpServletRequest request) {       
		ResponseObject<Object> responseObj = new ResponseObject<Object>();
		responseObj.setCode(ResponseCode.WORKS_URL_ERROR);
	  	if (ex instanceof MaxUploadSizeExceededException){  
	  		return generateResponseObject(ResponseCode.WORKS_URL_ERROR, "允许上传文件最大为20MB!");
	   	} else{  
	   		return generateResponseObject(ResponseCode.WORKS_URL_ERROR, "不知错误: " + ex.getMessage());
	 	}  
	}    
	
	@RequestMapping(value = "/education/modify", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> modifyEducation( HttpServletRequest request,
			Education education) {
		ResponseObject<Object> responseObj = null;
		if (education == null) {
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数无效");
		}
		if (StringUtil.isEmpty(education.getId())) {
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数无效");
		}
		try {
			String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			education.setUserId(userId);
			responseObj = this.educationService.modifyEducation(education);
		} catch (Exception e) {
			log.error("修改教育经历失败", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "修改教育经历失败!");
		}
		return responseObj;
	}
	
	@RequestMapping(value = "/education/add", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> addEducation( HttpServletRequest request,
			Education education) {
		ResponseObject<Object> responseObj = null;
		if (education == null) {
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数无效");
		}
		try {
			String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			education.setUserId(userId);
			responseObj = this.educationService.addEducation(education);
		} catch (Exception e) {
			log.error("教育经历添加失败", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "教育经历添加失败!");
		}
		return responseObj;
	}
	
	@RequestMapping(value = "/education/get_self", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseObject<List<Education>> searchEducationByUserId(HttpServletRequest request) {
		try {
			String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			return this.educationService.searchByUserId(userId);
		} catch (Exception e) {
			log.error("获取教育经历失败！", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取教育经历失败!");
		}
	}
	
	@RequestMapping(value = "/education/delete", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> deleteEducationById(HttpServletRequest request,
			@RequestParam(value = "id", required = true) String id) {
		try {
			String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			return this.educationService.deleteEducation(userId, id);
		} catch (Exception e) {
			log.error("删除教育经历异常", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "删除教育经历失败，请刷新页面再重试!");
		}
	}
	
	@RequestMapping(value = "/works/modify_enclosure_name", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> modifyEnclosureName(HttpServletRequest request,
			@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "enclosureName", required = true) String enclosureName) {
		if(StringUtil.isEmpty(enclosureName)){
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "附件显示名不能为空，请输入!");
		}
		if(!UserUtil.validateId(id)){
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数错误，请刷新页面再操作!");
		}
		try {
			String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			return this.educationService.modifyEnclosureName(userId, id, enclosureName);
		} catch (Exception e) {
			log.error("修改附件显示名失败，请重试", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "修改附件显示名失败，请重试!");
		}
	}
	
}
