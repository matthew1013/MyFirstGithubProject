package com.anyonehelps.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;

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
import com.anyonehelps.common.util.ArbitrationUtil;
import com.anyonehelps.common.util.StringUtil;
import com.anyonehelps.model.FileMeta;
import com.anyonehelps.model.ResponseObject;

@Controller
public class ArbitrationController extends BasicController {
	private static final long serialVersionUID = 5302710523661846053L;

	private static final Logger log = Logger.getLogger(ArbitrationController.class);
 
	//文件类型
	@Value(value = "${arbitration_file_type}")
	private String arbitrationFileType;
	//文件保存路径
	@Value(value = "${arbitration_file_save_dir}")
	private String arbitrationFileSaveDir; 
	//文件最大大小
	@Value(value = "${arbitration_file_size}")
	private long arbitrationFileSize;
	
	@Value(value = "${amazon_s3_url}")
	private String amazonS3Url;
	

	
	@RequestMapping(value = "/arbitration/upload_file", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> uploadFile(
			HttpServletRequest request,
			@RequestParam(value = "files[]", required = false) MultipartFile[] mpfs) {
		if (mpfs == null) {
			return generateResponseObject( ResponseCode.DEMAND_ENCLOSURE_ERROR, "没有附件,请选择附件后再上传!");
		}
		if(mpfs.length > 5){
			return generateResponseObject( ResponseCode.DEMAND_ENCLOSURE_ERROR, "附件多于3个，最多能上传3个附件!");
		}
		String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
		LinkedList<FileMeta> files = new LinkedList<FileMeta>();
		FileMeta fileMeta = null;
		
		// 解决火狐的反斜杠问题 kai 20151006
		String filetype = this.arbitrationFileType;// 要上传的文件类型

		for(int i=0;i<mpfs.length&&i<5;i++){
			MultipartFile mpf = mpfs[i];
			//2.3 create new fileMeta
			fileMeta = new FileMeta();
			fileMeta.setFileName(mpf.getOriginalFilename());
			fileMeta.setFileSize(mpf.getSize()/1024+" Kb");
			fileMeta.setFileType(mpf.getContentType());
			if (mpf != null && mpf.getSize() > 0) {
				if (mpf.getSize() > this.arbitrationFileSize) {
					return generateResponseObject(ResponseCode.DEMAND_ENCLOSURE_ERROR, "允许上传文件最大为20MB!");
				}
				String originalName = mpf.getOriginalFilename();
				if (!ArbitrationUtil.validateFileType(originalName, filetype)) {
					return generateResponseObject( ResponseCode.DEMAND_ENCLOSURE_ERROR, "上传附件格式不正确,请重新尝试!");
				}
				int index = originalName.lastIndexOf('.');
				index = Math.max(index, 0);
				String saveFileName = this.arbitrationFileSaveDir + "/" + userId + "_"
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
					log.error("保存文件失败！", e);
					return generateResponseObject(ResponseCode.DEMAND_ENCLOSURE_ERROR,"保存文件失败，请重试!");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace(); 
					log.error("保存文件失败！", e);
					return generateResponseObject(ResponseCode.DEMAND_ENCLOSURE_ERROR,"保存文件失败，请重试!");
				} catch (org.jets3t.service.ServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log.error("保存文件失败！", e);
					return generateResponseObject(ResponseCode.DEMAND_ENCLOSURE_ERROR,"保存文件失败，请重试!");
				} 
			 }
		}
		
		ResponseObject<Object> responseObj = new ResponseObject<Object>();
		responseObj.setCode(ResponseCode.SUCCESS_CODE);
		responseObj.setData(files);
		return responseObj;
	}
	
	@RequestMapping(value = "/admin/arbitration_upload_file", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> adminUploadFile(
			HttpServletRequest request,
			@RequestParam(value = "files[]", required = false) MultipartFile[] mpfs) {
		if (mpfs == null) {
			return generateResponseObject( ResponseCode.DEMAND_ENCLOSURE_ERROR, "没有附件,请选择附件后再上传!");
		}
		if(mpfs.length > 5){
			return generateResponseObject( ResponseCode.DEMAND_ENCLOSURE_ERROR, "附件多于3个，最多能上传3个附件!");
		}
		String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
		LinkedList<FileMeta> files = new LinkedList<FileMeta>();
		FileMeta fileMeta = null;
		
		// 解决火狐的反斜杠问题 kai 20151006
		String filetype = this.arbitrationFileType;// 要上传的文件类型
		for(int i=0;i<mpfs.length&&i<5;i++){
			MultipartFile mpf = mpfs[i];
			//2.3 create new fileMeta
			fileMeta = new FileMeta();
			fileMeta.setFileName(mpf.getOriginalFilename());
			fileMeta.setFileSize(mpf.getSize()/1024+" Kb");
			fileMeta.setFileType(mpf.getContentType());
			if (mpf != null && mpf.getSize() > 0) {
				if (mpf.getSize() > this.arbitrationFileSize) {
					return generateResponseObject(ResponseCode.DEMAND_ENCLOSURE_ERROR, "允许上传文件最大为20MB!");
				}
				String originalName = mpf.getOriginalFilename();
				if (!ArbitrationUtil.validateFileType(originalName, filetype)) {
					return generateResponseObject( ResponseCode.DEMAND_ENCLOSURE_ERROR, "上传附件格式不正确,请重新尝试!");
				}
				int index = originalName.lastIndexOf('.');
				index = Math.max(index, 0);
				String saveFileName = this.arbitrationFileSaveDir + "/" + userId + "_"
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
					log.error("保存文件失败！", e);
					return generateResponseObject(ResponseCode.DEMAND_ENCLOSURE_ERROR,"保存文件失败，请重试!");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace(); 
					log.error("保存文件失败！", e);
					return generateResponseObject(ResponseCode.DEMAND_ENCLOSURE_ERROR,"保存文件失败，请重试!");
				} catch (org.jets3t.service.ServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log.error("保存文件失败！", e);
					return generateResponseObject(ResponseCode.DEMAND_ENCLOSURE_ERROR,"保存文件失败，请重试!");
				} 
			 }
		}
		
		ResponseObject<Object> responseObj = new ResponseObject<Object>();
		responseObj.setCode(ResponseCode.SUCCESS_CODE);
		responseObj.setData(files);
		return responseObj;
	}
	
	/*@RequestMapping(value = "/arbitration/upload_file", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> uploadFile(
			HttpServletRequest request,
			@RequestParam(value = "files[]", required = false) MultipartFile[] mpfs) {
		if (mpfs == null) {
			return generateResponseObject( ResponseCode.DEMAND_ENCLOSURE_ERROR, "没有附件,请选择附件后再上传!");
		}
		if(mpfs.length > 5){
			return generateResponseObject( ResponseCode.DEMAND_ENCLOSURE_ERROR, "附件多于3个，最多能上传3个附件!");
		}
		String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
		LinkedList<FileMeta> files = new LinkedList<FileMeta>();
		FileMeta fileMeta = null;
		
		// 解决火狐的反斜杠问题 kai 20151006
		String filetype = this.arbitrationFileType;// 要上传的文件类型
		String strtest = this.arbitrationFileSaveDir;
		String strseparator = "";
		if (strtest.indexOf("/") >= 0)// 包含有“/”字符，分隔符用此字符
		{
			strseparator = "/";
		} else {
			strseparator = File.separator;
		}
		
		for(int i=0;i<mpfs.length&&i<5;i++){
			MultipartFile mpf = mpfs[i];
			//2.3 create new fileMeta
			fileMeta = new FileMeta();
			fileMeta.setFileName(mpf.getOriginalFilename());
			fileMeta.setFileSize(mpf.getSize()/1024+" Kb");
			fileMeta.setFileType(mpf.getContentType());
			if (mpf != null && mpf.getSize() > 0) {
				if (mpf.getSize() > this.arbitrationFileSize) {
					return generateResponseObject(ResponseCode.DEMAND_ENCLOSURE_ERROR, "允许上传文件最大为20MB!");
				}
				String originalName = mpf.getOriginalFilename();
				if (!ArbitrationUtil.validateFileType(originalName, filetype)) {
					return generateResponseObject( ResponseCode.DEMAND_ENCLOSURE_ERROR, "上传附件格式不正确,请重新尝试!");
				}
				int index = originalName.lastIndexOf('.');
				index = Math.max(index, 0);
				String saveFileName = this.arbitrationFileSaveDir + strseparator + userId + "_"
						+ StringUtil.generateRandomString(5) + "_"
						+ StringUtil.generateRandomInteger(5)
						+ originalName.substring(index);
				fileMeta.setSaveFileName(saveFileName);
				 
				try {
					File file1 = new File(request.getSession().getServletContext()
						.getRealPath("/")+ saveFileName);
					mpf.transferTo(file1);
					files.add(fileMeta);
				} catch (Exception e) {
					log.error("保存文件失败！", e);
					return generateResponseObject(ResponseCode.DEMAND_ENCLOSURE_ERROR,"保存文件失败，请重试!");
				}
			 }
		}
		
		ResponseObject<Object> responseObj = new ResponseObject<Object>();
		responseObj.setCode(ResponseCode.SUCCESS_CODE);
		responseObj.setData(files);
		return responseObj;
	}
	
	@RequestMapping(value = "/admin/arbitration_upload_file", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> adminUploadFile(
			HttpServletRequest request,
			@RequestParam(value = "files[]", required = false) MultipartFile[] mpfs) {
		if (mpfs == null) {
			return generateResponseObject( ResponseCode.DEMAND_ENCLOSURE_ERROR, "没有附件,请选择附件后再上传!");
		}
		if(mpfs.length > 5){
			return generateResponseObject( ResponseCode.DEMAND_ENCLOSURE_ERROR, "附件多于3个，最多能上传3个附件!");
		}
		String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
		LinkedList<FileMeta> files = new LinkedList<FileMeta>();
		FileMeta fileMeta = null;
		
		// 解决火狐的反斜杠问题 kai 20151006
		String filetype = this.arbitrationFileType;// 要上传的文件类型
		String strtest = this.arbitrationFileSaveDir;
		String strseparator = "";
		if (strtest.indexOf("/") >= 0)// 包含有“/”字符，分隔符用此字符
		{
			strseparator = "/";
		} else {
			strseparator = File.separator;
		}
		
		for(int i=0;i<mpfs.length&&i<5;i++){
			 MultipartFile mpf = mpfs[i];
			 //2.3 create new fileMeta
			 fileMeta = new FileMeta();
			 fileMeta.setFileName(mpf.getOriginalFilename());
			 fileMeta.setFileSize(mpf.getSize()/1024+" Kb");
			 fileMeta.setFileType(mpf.getContentType());
			 if (mpf != null && mpf.getSize() > 0) {
				 if (mpf.getSize() > this.arbitrationFileSize) {
						return generateResponseObject(ResponseCode.DEMAND_ENCLOSURE_ERROR, "允许上传文件最大为20MB!");
				 }
				 String originalName = mpf.getOriginalFilename();
				 if (!ArbitrationUtil.validateFileType(originalName, filetype)) {
						return generateResponseObject( ResponseCode.DEMAND_ENCLOSURE_ERROR, "上传附件格式不正确,请重新尝试!");
				 }
				 int index = originalName.lastIndexOf('.');
				 index = Math.max(index, 0);
				 String saveFileName = this.arbitrationFileSaveDir + strseparator + userId + "_"
						 + StringUtil.generateRandomString(5) + "_"
						 + StringUtil.generateRandomInteger(5)
						 + originalName.substring(index);
				 fileMeta.setSaveFileName(saveFileName);
				 
				 try {
					 File file1 = new File(request.getSession().getServletContext()
							.getRealPath("/")+ saveFileName);
					 mpf.transferTo(file1);
					 files.add(fileMeta);
				 } catch (Exception e) {
					 log.error("保存文件失败！", e);
					 return generateResponseObject(ResponseCode.DEMAND_ENCLOSURE_ERROR,"保存文件失败，请重试!");
				}
			 }
		}
		
		ResponseObject<Object> responseObj = new ResponseObject<Object>();
		responseObj.setCode(ResponseCode.SUCCESS_CODE);
		responseObj.setData(files);
		return responseObj;
	}*/
	
}
