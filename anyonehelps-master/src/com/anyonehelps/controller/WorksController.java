package com.anyonehelps.controller;

import java.io.IOException;
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
import com.anyonehelps.common.util.StringUtil;
import com.anyonehelps.common.util.UserUtil;
import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.FileMeta;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.model.Works;
import com.anyonehelps.service.WorksService;

@Controller
public class WorksController extends BasicController {
	private static final long serialVersionUID = 429288571425947824L;

	private static final Logger log = Logger.getLogger(WorksController.class);
	
	@Resource(name = "worksService")
	private WorksService worksService;
	//文件类型
	@Value(value = "${works_file_type}")
	private String worksFileType;
	//文件保存路径
	@Value(value = "${works_file_save_dir}")
	private String worksFileSaveDir;
	//文件最大大小
	@Value(value = "${works_file_size}")
	private long worksFileSize;

	@Value(value = "${amazon_s3_url}")
	private String amazonS3Url;
	
	@RequestMapping(value = "/works/add", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> addWorks(
			HttpServletRequest request,
			@RequestParam(value = "files[]", required = false) MultipartFile[] mpfs) {
		if (mpfs == null) {
			return generateResponseObject( ResponseCode.WORKS_URL_NOT_EXISTS, "没有附件,请选择附件后再上传!");
		}
		if(mpfs.length > 9){
			return generateResponseObject( ResponseCode.WORKS_URL_ERROR, "附件多于3个，最多能上传3个附件!");
		}
		String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
		LinkedList<FileMeta> files = new LinkedList<FileMeta>();
		FileMeta fileMeta = null;
		
		// 解决火狐的反斜杠问题 kai 20151006
		String filetype = this.worksFileType;// 要上传的文件类型
		
		for(int i=0;i<mpfs.length&&i<9;i++){
			 MultipartFile mpf = mpfs[i];
			 //2.3 create new fileMeta
			 fileMeta = new FileMeta(); 
			 fileMeta.setFileName(mpf.getOriginalFilename());
			 fileMeta.setFileSize(mpf.getSize()/1024+" Kb");
			 fileMeta.setFileType(mpf.getContentType());
			 if (mpf != null && mpf.getSize() > 0) {
				 if (mpf.getSize() > this.worksFileSize) {
						return generateResponseObject(ResponseCode.WORKS_URL_ERROR, "允许上传文件最大为20MB!");
				 }
				 String originalName = mpf.getOriginalFilename();
				 if (!DemandUtil.validateFileType(originalName, filetype)) {
						return generateResponseObject( ResponseCode.WORKS_URL_ERROR, "上传附件格式不正确,请重新尝试!");
				 }
				 int index = originalName.lastIndexOf('.');
				 index = Math.max(index, 0);
				 String saveFileName = this.worksFileSaveDir + "/" + userId + "_"
						 + StringUtil.generateRandomString(5) + "_"
						 + StringUtil.generateRandomInteger(5)
						 + originalName.substring(index);
				 fileMeta.setSaveFileName(this.amazonS3Url + saveFileName);
				 
				 CommonsMultipartFile cf= (CommonsMultipartFile)mpf; 
				 DiskFileItem fi = (DiskFileItem)cf.getFileItem(); 
				 AmazonS3Samples amazonS3Samples = new AmazonS3Samples();
				 try {
					 amazonS3Samples.pushFile(saveFileName, fi.getStoreLocation());
				 
					 Works works = new Works();
					 works.setUserId(userId);
					 works.setTitle(fileMeta.getFileName());
					 works.setUrl(fileMeta.getSaveFileName());
					 works.setType(fileMeta.getFileType());
					 ResponseObject<Object> obj =this.worksService.addWorks(works);
					 if(obj.getCode()==ResponseCode.SUCCESS_CODE){
						 fileMeta.setWorksId(works.getId());
						 files.add(fileMeta);
					 }
				 
				 } catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					 log.error("作品增加写数据库失败！", e);
					 return generateResponseObject(ResponseCode.WORKS_URL_ERROR,"保存文件失败，请重试!");
				 } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace(); 
					 log.error("作品增加写数据库失败！", e);
					 return generateResponseObject(ResponseCode.WORKS_URL_ERROR,"保存文件失败，请重试!");
				 } catch (org.jets3t.service.ServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					 log.error("作品增加写数据库失败！", e);
					 return generateResponseObject(ResponseCode.WORKS_URL_ERROR,"保存文件失败，请重试!");
				 } catch (ServiceException e) {
					 log.error("作品增加写数据库失败！", e);
					 return generateResponseObject(ResponseCode.WORKS_URL_ERROR,"保存文件失败，请重试!");
				 } 
			 }
		}
		
		ResponseObject<Object> responseObj = new ResponseObject<Object>();
		responseObj.setCode(ResponseCode.SUCCESS_CODE);
		responseObj.setData(files);
		return responseObj;
	}

	
	/*@RequestMapping(value = "/works/add", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> addWorks(
			HttpServletRequest request,
			@RequestParam(value = "files[]", required = false) MultipartFile[] mpfs) {
		if (mpfs == null) {
			return generateResponseObject( ResponseCode.WORKS_URL_NOT_EXISTS, "没有附件,请选择附件后再上传!");
		}
		if(mpfs.length > 9){
			return generateResponseObject( ResponseCode.WORKS_URL_ERROR, "附件多于3个，最多能上传3个附件!");
		}
		String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
		LinkedList<FileMeta> files = new LinkedList<FileMeta>();
		FileMeta fileMeta = null;
		
		// 解决火狐的反斜杠问题 kai 20151006
		String filetype = this.worksFileType;// 要上传的文件类型
		String strtest = this.worksFileSaveDir;
		String strseparator = "";
		if (strtest.indexOf("/") >= 0)// 包含有“/”字符，分隔符用此字符
		{
			strseparator = "/";
		} else {
			strseparator = File.separator;
		}
		
		for(int i=0;i<mpfs.length&&i<9;i++){
			 MultipartFile mpf = mpfs[i];
			 //2.3 create new fileMeta
			 fileMeta = new FileMeta(); 
			 fileMeta.setFileName(mpf.getOriginalFilename());
			 fileMeta.setFileSize(mpf.getSize()/1024+" Kb");
			 fileMeta.setFileType(mpf.getContentType());
			 if (mpf != null && mpf.getSize() > 0) {
				 if (mpf.getSize() > this.worksFileSize) {
						return generateResponseObject(ResponseCode.WORKS_URL_ERROR, "允许上传文件最大为20MB!");
				 }
				 String originalName = mpf.getOriginalFilename();
				 if (!DemandUtil.validateFileType(originalName, filetype)) {
						return generateResponseObject( ResponseCode.WORKS_URL_ERROR, "上传附件格式不正确,请重新尝试!");
				 }
				 int index = originalName.lastIndexOf('.');
				 index = Math.max(index, 0);
				 String saveFileName = this.worksFileSaveDir + strseparator + userId + "_"
						 + StringUtil.generateRandomString(5) + "_"
						 + StringUtil.generateRandomInteger(5)
						 + originalName.substring(index);
				 fileMeta.setSaveFileName(saveFileName);
				 
				 try {
					 File file1 = new File(request.getSession().getServletContext()
							.getRealPath("/")+ saveFileName);
					 mpf.transferTo(file1);
					 Works works = new Works();
					 works.setUserId(userId);
					 works.setTitle(fileMeta.getFileName());
					 works.setUrl(fileMeta.getSaveFileName());
					 works.setType(fileMeta.getFileType());
					 ResponseObject<Object> obj =this.worksService.addWorks(works);
					 if(obj.getCode()==ResponseCode.SUCCESS_CODE){
						 fileMeta.setWorksId(works.getId());
						 files.add(fileMeta);
					 }
				 } catch (ServiceException e) {
					 log.error("作品增加写数据库失败！", e);
					 return generateResponseObject(ResponseCode.WORKS_URL_ERROR,"保存文件失败，请重试!");
				 } catch (Exception e) {
					 log.error("保存文件失败！", e);
					 return generateResponseObject(ResponseCode.WORKS_URL_ERROR,"保存文件失败，请重试!");
				}
			 }
		}
		
		ResponseObject<Object> responseObj = new ResponseObject<Object>();
		responseObj.setCode(ResponseCode.SUCCESS_CODE);
		responseObj.setData(files);
		return responseObj;
	}*/

	@RequestMapping(value = "/works/get_self", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseObject<List<Works>> searchWorksByUserId(HttpServletRequest request) {
		try {
			String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			return this.worksService.searchByUserId(userId);
		} catch (Exception e) {
			log.error("获取作品失败！", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取作品失败!");
		}
	}
	
	@RequestMapping(value = "/works/delete", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> deleteWorksById(HttpServletRequest request,
			@RequestParam(value = "id", required = true) String id) {
		try {
			String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			return this.worksService.deleteWorks(userId, id);
		} catch (Exception e) {
			log.error("删除作品异常", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "删除作品失败，请刷新页面再重试!");
		}
	}
	
	@RequestMapping(value = "/works/modify_title", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> modifyWorkTitle(HttpServletRequest request,
			@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "title", required = true) String title) {
		if(StringUtil.isEmpty(title)){
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "作品显示名不能为空，请输入!");
		}
		if(!UserUtil.validateId(id)){
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数错误，请刷新页面再操作!");
		}
		try {
			String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			return this.worksService.modifyWorkTitle(userId, id, title);
		} catch (Exception e) {
			log.error("修改作品显示名失败，请重试", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "修改作品显示名失败，请重试!");
		}
	}
	
}
