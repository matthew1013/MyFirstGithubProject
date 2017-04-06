package com.anyonehelps.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.anyonehelps.common.constants.Constant;
import com.anyonehelps.common.constants.ResponseCode;
import com.anyonehelps.common.util.AmazonS3Samples;
import com.anyonehelps.common.util.DemandUtil;
import com.anyonehelps.common.util.SpecialtyUserUtil;
import com.anyonehelps.common.util.StringUtil;
import com.anyonehelps.model.FileMeta;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.model.SpecialtyType;
import com.anyonehelps.model.SpecialtyUser;
import com.anyonehelps.service.SpecialtyService;

@Controller
public class SpecialtyController extends BasicController {
	private static final long serialVersionUID = 1562088471869155201L;
	private static final Logger log = Logger.getLogger(SpecialtyController.class);

	@Resource(name = "specialtyService")
	private SpecialtyService specialtyService;
	
	//@Value(value = "${page_size}")
	//private int defaultPageSize;
	//文件类型
	@Value(value = "${specialty_enclosure_file_type}")
	private String specialtyEnclosureFileType;
	//文件保存路径
	@Value(value = "${specialty_enclosure_file_save_dir}")
	private String specialtyEnclosureFileSaveDir;
	//文件最大大小
	@Value(value = "${specialty_enclosure_file_size}")
	private long specialtyEnclosureFileSize;
	
	@Value(value = "${amazon_s3_url}")
	private String amazonS3Url;
	
	@RequestMapping(value = "/specialty/enclosure_upload", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> uploadEnclosure(
			HttpServletRequest request,
			@RequestParam(value = "files[]", required = false) MultipartFile[] mpfs) {
		if (mpfs == null) {
			return generateResponseObject( ResponseCode.DEMAND_ENCLOSURE_ERROR, "没有附件,请选择附件后再上传!");
		}
		if(mpfs.length > 5){
			return generateResponseObject( ResponseCode.DEMAND_ENCLOSURE_ERROR, "附件多于5个，最多能上传5个附件!");
		}
		String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
		LinkedList<FileMeta> files = new LinkedList<FileMeta>();
		FileMeta fileMeta = null;
		
		// 解决火狐的反斜杠问题 kai 20151006
		String filetype = this.specialtyEnclosureFileType;// 要上传的文件类型
		
		for(int i=0;i<mpfs.length&&i<5;i++){
			 MultipartFile mpf = mpfs[i];
			 //2.3 create new fileMeta
			 fileMeta = new FileMeta();
			 fileMeta.setFileName(mpf.getOriginalFilename());
			 fileMeta.setFileSize(mpf.getSize()/1024+" Kb");
			 fileMeta.setFileType(mpf.getContentType());
			 if (mpf != null && mpf.getSize() > 0) {
				 if (mpf.getSize() > this.specialtyEnclosureFileSize) {
						return generateResponseObject(ResponseCode.DEMAND_ENCLOSURE_ERROR, "允许上传文件最大为20MB!");
				 }
				 String originalName = mpf.getOriginalFilename();
				 if (!DemandUtil.validateFileType(originalName, filetype)) {
						return generateResponseObject( ResponseCode.DEMAND_ENCLOSURE_ERROR, "上传附件格式不正确,请重新尝试!");
				 }
				 int index = originalName.lastIndexOf('.');
				 index = Math.max(index, 0);
				 String saveFileName = this.specialtyEnclosureFileSaveDir + "/" + userId + "_"
						 + StringUtil.generateRandomString(5) + "_"
						 + StringUtil.generateRandomInteger(5)
						 + originalName.substring(index);
				 fileMeta.setSaveFileName(this.amazonS3Url + saveFileName);
				 
				 CommonsMultipartFile cf= (CommonsMultipartFile)mpf; 
				 DiskFileItem fi = (DiskFileItem)cf.getFileItem(); 
				 AmazonS3Samples amazonS3Samples = new AmazonS3Samples();
				 try {
					 amazonS3Samples.pushFile(saveFileName, fi.getStoreLocation());
					 files.add(fileMeta);
				 } catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log.error("上传文件失败,请重试！", e);
					return generateResponseObject(ResponseCode.DEMAND_ENCLOSURE_ERROR,"上传文件失败,请重试!");
				 } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace(); 
					log.error("上传文件失败,请重试！", e);
					return generateResponseObject(ResponseCode.DEMAND_ENCLOSURE_ERROR,"上传文件失败,请重试!");
				 } catch (org.jets3t.service.ServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log.error("上传文件失败,请重试！", e);
					return generateResponseObject(ResponseCode.DEMAND_ENCLOSURE_ERROR,"上传文件失败,请重试!");
				 }
				 
			 }
		}
		
		ResponseObject<Object> responseObj = new ResponseObject<Object>();
		responseObj.setCode(ResponseCode.SUCCESS_CODE);
		responseObj.setData(files);
		return responseObj;
	}


	@RequestMapping(value = "/specialty/get_specialty_type_all", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseObject<List<SpecialtyType>> getAllSpecialtyType(HttpServletRequest request) {
		try {
			return  this.specialtyService.getAllSpecialtyType();
		} catch (Exception e) {
			log.error("获取系统技能失败，请重试", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取系统技能失败，请重试!");
		}
	}
	
	

	@RequestMapping(value = "/specialty/get_user_specialty", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseObject<List<SpecialtyUser>> getSpecialtyUser(HttpServletRequest request,
			@RequestParam(value = "type", required = false, defaultValue = "") String type,
			@RequestParam(value = "state", required = false, defaultValue = "") String state) {
		String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
		try {
			return this.specialtyService.getSpecialtyUser(userId, type, state);
		} catch (Exception e) {
			log.error("获取用户技能失败，请重试", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取用户技能失败，请重试!");
		}
	}
	
	@RequestMapping(value = "/specialty/user_specialty_add", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> addSpecialtyUser( HttpServletRequest request,
			SpecialtyUser su) {
		ResponseObject<Object> responseObj = null;
		
		if (!SpecialtyUserUtil.validateType(su.getType())) {
			responseObj = generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数错误!");
			return responseObj;
		}else{
			if(Constant.SPECIALTY_TYPE0.equals(su.getType())){
				if (!SpecialtyUserUtil.validateSpecialtyId(su.getSpecialtyId())) {
					responseObj = generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数错误!");
					return responseObj;
				}
				if (!SpecialtyUserUtil.validateSpecialtyTypeId(su.getSpecialtyTypeId())) {
					responseObj = generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数错误!");
					return responseObj;
				}
				su.setName(null);
			}else if(Constant.SPECIALTY_TYPE1.equals(su.getType())){
				if (StringUtil.isEmpty(su.getName())) {
					responseObj = generateResponseObject(ResponseCode.PARAMETER_ERROR, "请输入自定义技能后再添加!");
					return responseObj;
				}
				su.setSpecialtyId(null);
				su.setSpecialtyTypeId(null);
			}
		}
	
		try {
			String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			su.setUserId(userId);
			su.setState(Constant.SPECIALTY_STATE_NOT_AUTH);
				
			responseObj = this.specialtyService.addSpecialtyUser(su);
		} catch (Exception e) {
			log.error("技能添加失败", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "技能添加失败，请重试!");
		}
		return responseObj;
	}
	
	@RequestMapping(value = "/specialty/user_specialty_delete", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> deleteSpecialtyUser( HttpServletRequest request,
			@RequestParam(value = "id") String id) {
		ResponseObject<Object> responseObj = null;
		
		if (!SpecialtyUserUtil.validateId(id)) {
			responseObj = generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数错误!");
			return responseObj;
		}
	
		try {
			String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			responseObj = this.specialtyService.deleteSpecialtyUser(userId, id);
		} catch (Exception e) {
			log.error("技能删除失败", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "技能删除失败，请刷新页面后重试!");
		}
		return responseObj;
	}

	@RequestMapping(value = "/specialty/user_specialty_auth", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> authSpecialtyUser( HttpServletRequest request,
			SpecialtyUser su) {
		ResponseObject<Object> responseObj = null;
		
		if (!SpecialtyUserUtil.validateId(su.getId())) {
			responseObj = generateResponseObject(ResponseCode.PARAMETER_ERROR, "请选择认证的技能再提交!");
			return responseObj;
		} 
		
		try {
			String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			su.setUserId(userId);
			su.setAuthContent(URLDecoder.decode(su.getAuthContent(), "UTF-8"));
			responseObj = this.specialtyService.authSpecialtyUser(su);
		} catch (Exception e) {
			log.error("技能认证失败", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "技能认证失败，请重试!");
		}
		return responseObj;
	}
	
	@RequestMapping(value = "/specialty/user_specialty_get_one", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseObject<Object> getSpecialtyUserById(
			HttpServletRequest request,
			@RequestParam(value = "id") String id) {
		try {
			String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			return this.specialtyService.getSpecialtyUserById(userId, id);
		} catch (Exception e) {
			log.error("用户获取技能信息失败，请重试", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取技能信息失败，请重试!");
		}
	}
}
