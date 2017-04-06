package com.anyonehelps.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.anyonehelps.common.constants.ParameterConstants;
import com.anyonehelps.common.constants.ResponseCode;
import com.anyonehelps.common.util.AccountUtil;
import com.anyonehelps.common.util.AmazonS3Samples;
import com.anyonehelps.common.util.DemandMessageUtil;
import com.anyonehelps.common.util.DemandUtil;
import com.anyonehelps.common.util.ReceiveDemandUtil;
import com.anyonehelps.common.util.StringUtil;
import com.anyonehelps.common.util.UserUtil;
import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.Demand;
import com.anyonehelps.model.DemandMessage;
import com.anyonehelps.model.Evaluate;
import com.anyonehelps.model.FileMeta;
import com.anyonehelps.model.PageSplit;
import com.anyonehelps.model.ReceiveDemand;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.service.DemandService;

@Controller
public class DemandController extends BasicController {
	private static final long serialVersionUID = 1562088471869155201L;
	private static final Logger log = Logger.getLogger(DemandController.class);

	@Resource(name = "demandService")
	private DemandService demandService;
	
	@Value(value = "${page_size}")
	private int defaultPageSize;
	
	//文件类型
	@Value(value = "${demand_enclosure_file_type}")
	private String demandEnclosureFileType;
	//文件保存路径
	@Value(value = "${demand_enclosure_file_save_dir}")
	private String demandEnclosureFileSaveDir;
	//文件最大大小
	@Value(value = "${demand_enclosure_file_size}")
	private long demandEnclosureFileSize;
	
	//文件类型
	@Value(value = "${demand_result_file_type}")
	private String demandResultFileType;
	//文件保存路径
	@Value(value = "${demand_result_file_save_dir}")
	private String demandResultFileSaveDir;
	//文件最大大小
	@Value(value = "${demand_result_file_size}")
	private long demandResultFileSize;
	
	
	//如果任务截止时间没填，默认多少天自动到期
	@Value(value = "${default_demand_expire_date}")
	private long defaultDemandExpireDate;
	
	
	//文件类型
	@Value(value = "${summernote_file_type}")
	private String summernoteFileType;
	//文件保存路径
	@Value(value = "${summernote_file_save_dir}")
	private String summernoteFileSaveDir;
	//文件最大大小
	@Value(value = "${summernote_file_size}")
	private long summernoteFileSize;
	
	@Value(value = "${amazon_s3_url}")
	private String amazonS3Url;
	
	@RequestMapping(value = "/summernote/upload", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> summernoteUpload(HttpServletRequest request,
		@RequestParam(value = "file", required = false) MultipartFile mpf) throws ServiceException {
		if (mpf == null) {
			return generateResponseObject( ResponseCode.PARAMETER_ERROR, "没有选中文件");
		}
		String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
		FileMeta fileMeta = null;
		
		//2.3 create new fileMeta
		fileMeta = new FileMeta();
		fileMeta.setFileName(mpf.getOriginalFilename());
		fileMeta.setFileSize(mpf.getSize()/1024+" Kb");
		fileMeta.setFileType(mpf.getContentType());
		if (mpf != null && mpf.getSize() > 0) {
			if (mpf.getSize() > this.summernoteFileSize) {
				return generateResponseObject(ResponseCode.PARAMETER_ERROR, "文件大小不能超过20M!");
			}
			String originalName = mpf.getOriginalFilename();
			String filetype = this.summernoteFileType;
			if (!StringUtil.validateFileType(originalName, filetype)) {
				return generateResponseObject( ResponseCode.PARAMETER_ERROR, "格式不支持!");
			}
			int index = originalName.lastIndexOf('.');
			index = Math.max(index, 0);
			String saveFileName = this.summernoteFileSaveDir + "/" + userId + "_"
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
				return generateResponseObject(ResponseCode.PARAMETER_ERROR,"上传文件失败,请重试!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error("上传文件失败,请重试！", e);
				return generateResponseObject(ResponseCode.PARAMETER_ERROR,"上传文件失败,请重试!");
			} catch (org.jets3t.service.ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error("上传文件失败,请重试！", e);
				return generateResponseObject(ResponseCode.PARAMETER_ERROR,"上传文件失败,请重试!");
			}
		}
					
		ResponseObject<Object> responseObj = new ResponseObject<Object>();
		responseObj.setCode(ResponseCode.SUCCESS_CODE);
		responseObj.setData(fileMeta);
		return responseObj;
	}
	
	
	@RequestMapping(value = "/demand/result_upload_file", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> uploadDemandResult(
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
		String filetype = this.demandResultFileType;// 要上传的文件类型
		
		for(int i=0;i<mpfs.length&&i<3;i++){
			MultipartFile mpf = mpfs[i];
			//2.3 create new fileMeta
			fileMeta = new FileMeta();
			fileMeta.setFileName(mpf.getOriginalFilename());
			fileMeta.setFileSize(mpf.getSize()/1024+" Kb");
			fileMeta.setFileType(mpf.getContentType());
			if (mpf != null && mpf.getSize() > 0) {
				if (mpf.getSize() > this.demandResultFileSize) {
						return generateResponseObject(ResponseCode.DEMAND_ENCLOSURE_ERROR, "允许上传文件最大为20MB!");
				 }
				 String originalName = mpf.getOriginalFilename();
				 if (!DemandUtil.validateFileType(originalName, filetype)) {
						return generateResponseObject( ResponseCode.DEMAND_ENCLOSURE_ERROR, "上传附件格式不正确,请重新尝试!");
				 }
				 int index = originalName.lastIndexOf('.');
				 index = Math.max(index, 0);
				 String saveFileName = this.demandResultFileSaveDir + "/" + userId + "_"
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
		files.add(fileMeta);
		ResponseObject<Object> responseObj = new ResponseObject<Object>();
		responseObj.setCode(ResponseCode.SUCCESS_CODE);
		responseObj.setData(files);
		return responseObj;
	}
	
	@RequestMapping(value = "/demand/enclosure_upload", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> uploadDemandEnclosure(
			HttpServletRequest request,
			@RequestParam(value = "files[]", required = false) MultipartFile[] mpfs) {
		if (mpfs == null) {
			return generateResponseObject( ResponseCode.DEMAND_ENCLOSURE_ERROR, "没有附件,请选择附件后再上传!");
		}
		if(mpfs.length > 3){
			return generateResponseObject( ResponseCode.DEMAND_ENCLOSURE_ERROR, "附件多于3个，最多能上传3个附件!");
		}
		String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
		LinkedList<FileMeta> files = new LinkedList<FileMeta>();
		FileMeta fileMeta = null;
		
		// 解决火狐的反斜杠问题 kai 20151006
		String filetype = this.demandEnclosureFileType;// 要上传的文件类型
		
		for(int i=0;i<mpfs.length&&i<3;i++){
			 MultipartFile mpf = mpfs[i];
			 //2.3 create new fileMeta
			 fileMeta = new FileMeta();
			 fileMeta.setFileName(mpf.getOriginalFilename());
			 fileMeta.setFileSize(mpf.getSize()/1024+" Kb");
			 fileMeta.setFileType(mpf.getContentType());
			 if (mpf != null && mpf.getSize() > 0) {
				 if (mpf.getSize() > this.demandEnclosureFileSize) {
						return generateResponseObject(ResponseCode.DEMAND_ENCLOSURE_ERROR, "允许上传文件最大为20MB!");
				 }
				 String originalName = mpf.getOriginalFilename();
				 if (!DemandUtil.validateFileType(originalName, filetype)) {
						return generateResponseObject( ResponseCode.DEMAND_ENCLOSURE_ERROR, "上传附件格式不正确,请重新尝试!");
				 }
				 int index = originalName.lastIndexOf('.');
				 index = Math.max(index, 0);
				 String saveFileName = this.demandEnclosureFileSaveDir + "/" + userId + "_"
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
		files.add(fileMeta);
		ResponseObject<Object> responseObj = new ResponseObject<Object>();
		responseObj.setCode(ResponseCode.SUCCESS_CODE);
		responseObj.setData(files);
		return responseObj;
	}

		
	/*@RequestMapping(value = "/summernote/upload", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> summernoteUpload(HttpServletRequest request,
		@RequestParam(value = "file", required = false) MultipartFile mpf) throws ServiceException {
		if (mpf == null) {
			return generateResponseObject( ResponseCode.PARAMETER_ERROR, "没有选中文件");
		}
		String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
		FileMeta fileMeta = null;
				
		// 解决火狐的反斜杠问题
		String filetype = this.summernoteFileType;
		String strtest = this.summernoteFileSaveDir;
		String strseparator = "";
		if (strtest.indexOf("/") >= 0)
		{
			strseparator = "/";
		} else {
			strseparator = File.separator;
		}
				
		//2.3 create new fileMeta
		fileMeta = new FileMeta();
		fileMeta.setFileName(mpf.getOriginalFilename());
		fileMeta.setFileSize(mpf.getSize()/1024+" Kb");
		fileMeta.setFileType(mpf.getContentType());
		if (mpf != null && mpf.getSize() > 0) {
			if (mpf.getSize() > this.summernoteFileSize) {
				return generateResponseObject(ResponseCode.PARAMETER_ERROR, "文件大小不能超过20M!");
			}
			String originalName = mpf.getOriginalFilename();
			if (!StringUtil.validateFileType(originalName, filetype)) {
				return generateResponseObject( ResponseCode.PARAMETER_ERROR, "格式不支持!");
			}
			int index = originalName.lastIndexOf('.');
			index = Math.max(index, 0);
			String saveFileName = this.summernoteFileSaveDir + strseparator + userId + "_"
				+ StringUtil.generateRandomString(5) + "_"
				+ StringUtil.generateRandomInteger(5)
				+ originalName.substring(index);
			fileMeta.setSaveFileName(saveFileName);
						 
			try {
				 File file = new File(request.getSession().getServletContext().getRealPath("/")+ saveFileName);
				 mpf.transferTo(file);
					
			} catch (Exception e) {
				log.error("上传文件失败,请重试！", e);
				return generateResponseObject(ResponseCode.PARAMETER_ERROR,"上传文件失败,请重试!");
			}
		}
					
		ResponseObject<Object> responseObj = new ResponseObject<Object>();
		responseObj.setCode(ResponseCode.SUCCESS_CODE);
		responseObj.setData(fileMeta);
		return responseObj;
	}
	
	
	@RequestMapping(value = "/demand/result_upload_file", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> uploadDemandResult(
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
		String filetype = this.demandResultFileType;// 要上传的文件类型
		String strtest = this.demandResultFileSaveDir;
		String strseparator = "";
		if (strtest.indexOf("/") >= 0)// 包含有“/”字符，分隔符用此字符
		{
			strseparator = "/";
		} else {
			strseparator = File.separator;
		}
		
		for(int i=0;i<mpfs.length&&i<3;i++){
			 MultipartFile mpf = mpfs[i];
			 //2.3 create new fileMeta
			 fileMeta = new FileMeta();
			 fileMeta.setFileName(mpf.getOriginalFilename());
			 fileMeta.setFileSize(mpf.getSize()/1024+" Kb");
			 fileMeta.setFileType(mpf.getContentType());
			 if (mpf != null && mpf.getSize() > 0) {
				 if (mpf.getSize() > this.demandResultFileSize) {
						return generateResponseObject(ResponseCode.DEMAND_ENCLOSURE_ERROR, "允许上传文件最大为20MB!");
				 }
				 String originalName = mpf.getOriginalFilename();
				 if (!DemandUtil.validateFileType(originalName, filetype)) {
						return generateResponseObject( ResponseCode.DEMAND_ENCLOSURE_ERROR, "上传附件格式不正确,请重新尝试!");
				 }
				 int index = originalName.lastIndexOf('.');
				 index = Math.max(index, 0);
				 String saveFileName = this.demandResultFileSaveDir + strseparator + userId + "_"
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
	
	@RequestMapping(value = "/demand/enclosure_upload", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> uploadDemandEnclosure(
			HttpServletRequest request,
			@RequestParam(value = "files[]", required = false) MultipartFile[] mpfs) {
		if (mpfs == null) {
			return generateResponseObject( ResponseCode.DEMAND_ENCLOSURE_ERROR, "没有附件,请选择附件后再上传!");
		}
		if(mpfs.length > 3){
			return generateResponseObject( ResponseCode.DEMAND_ENCLOSURE_ERROR, "附件多于3个，最多能上传3个附件!");
		}
		String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
		LinkedList<FileMeta> files = new LinkedList<FileMeta>();
		FileMeta fileMeta = null;
		
		// 解决火狐的反斜杠问题 kai 20151006
		String filetype = this.demandEnclosureFileType;// 要上传的文件类型
		String strtest = this.demandEnclosureFileSaveDir;
		String strseparator = "";
		if (strtest.indexOf("/") >= 0)// 包含有“/”字符，分隔符用此字符
		{
			strseparator = "/";
		} else {
			strseparator = File.separator;
		}
		
		for(int i=0;i<mpfs.length&&i<3;i++){
			 MultipartFile mpf = mpfs[i];
			 //2.3 create new fileMeta
			 fileMeta = new FileMeta();
			 fileMeta.setFileName(mpf.getOriginalFilename());
			 fileMeta.setFileSize(mpf.getSize()/1024+" Kb");
			 fileMeta.setFileType(mpf.getContentType());
			 if (mpf != null && mpf.getSize() > 0) {
				 if (mpf.getSize() > this.demandEnclosureFileSize) {
						return generateResponseObject(ResponseCode.DEMAND_ENCLOSURE_ERROR, "允许上传文件最大为20MB!");
				 }
				 String originalName = mpf.getOriginalFilename();
				 if (!DemandUtil.validateFileType(originalName, filetype)) {
						return generateResponseObject( ResponseCode.DEMAND_ENCLOSURE_ERROR, "上传附件格式不正确,请重新尝试!");
				 }
				 int index = originalName.lastIndexOf('.');
				 index = Math.max(index, 0);
				 String saveFileName = this.demandEnclosureFileSaveDir + strseparator + userId + "_"
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

	@RequestMapping(value = "/demand/modify", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> modifyDemand( HttpServletRequest request,
	        Demand demand) {
		ResponseObject<Object> responseObj = null;
		if (!DemandUtil.validateTitle(demand.getTitle())) {
			responseObj = generateResponseObject(ResponseCode.DEMAND_TITLE_ERROR, "标题输入不正确，请重新输入!");
			return responseObj;
		}  
		  
		if( !DemandUtil.validateAmount(demand.getAmount())){
			responseObj = generateResponseObject(ResponseCode.DEMAND_AMOUNT_ERROR, "赏金金额不正确，请重新输入!");
			return responseObj;
		}
		if( !DemandUtil.validateAchieve(demand.getAchieve())){
			responseObj = generateResponseObject(ResponseCode.DEMAND_AMOUNT_ERROR, "成果要求不正确，请重新输入!");
			return responseObj;
		}
		
		if(!DemandUtil.isValidDateTime(demand.getExpireDate())){
			responseObj = generateResponseObject(ResponseCode.DEMAND_EXPIRE_DATE_ERROR, "截止时间格式不正确，请重新输入!");
			return responseObj;
		}
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = format.parse(demand.getExpireDate());
			if(date.getTime()<=new Date().getTime()){
				responseObj = generateResponseObject(ResponseCode.DEMAND_EXPIRE_DATE_ERROR, "截止时间小于当前时间，请重新输入!");
				return responseObj;
			}
		} catch (ParseException e) {
			responseObj = generateResponseObject(ResponseCode.DEMAND_EXPIRE_DATE_ERROR, "截止时间格式不正确，请重新输入!");
			return responseObj;
		}
		
		try {
			if (URLDecoder.decode(demand.getTitle(), "UTF-8").length()>60) {
				responseObj = generateResponseObject(ResponseCode.DEMAND_TITLE_ERROR, "标题太长，不能超过60个字!");
				return responseObj;
			}
			demand.setIp(ReceiveDemandUtil.getIpAddr(request));
			
			String userId = (String) request.getSession().getAttribute(
					Constant.USER_ID_SESSION_KEY);
			String nickName = (String) request.getSession().getAttribute(
					Constant.USER_NICK_NAME_SESSION_KEY);
			demand.setUserId(userId);
			demand.setState(Constant.DEMAND_STATE_READY);
			demand.setTitle(URLDecoder.decode(demand.getTitle(), "UTF-8")); 
			demand.setContent(URLDecoder.decode(demand.getContent(), "UTF-8")); 
			demand.setAchieve(URLDecoder.decode(demand.getAchieve(), "UTF-8")); 
			demand.setTag(URLDecoder.decode(demand.getTag(), "UTF-8")); 
			return this.demandService.modifyDemand(nickName, demand);
		} catch (Exception e) { 
			log.error("修改任务失败", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION,
					"修改任务失败!");
		}
		
	}
	/**
	 * 发单人修改备注
	 * @param request
	 * @param id 任务id
	 * @param remark 备注
	 * @return
	 */
	@RequestMapping(value = "/demand/modify_remark", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> modifyDemandRemark( HttpServletRequest request,
			@RequestParam(value = "id", required = false, defaultValue = "") String id,
			@RequestParam(value = "remark", required = false, defaultValue = "") String remark
			) {
		if (!ReceiveDemandUtil.validateDemandId(id)) {
			ResponseObject<Object> responseObj = generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数错误!");
			return responseObj;
		}
		
		try {
			String userId = (String) request.getSession().getAttribute(
					Constant.USER_ID_SESSION_KEY);
			return this.demandService.modifyDemandRemark(userId,id,remark);
		} catch (Exception e) {
			log.error("任务备注修改失败", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION,
					"任务备注修改失败，请重试!");
		}
		
	}
	
	/**
	 * 接单人修改备注
	 * @param request
	 * @param id 接单id
	 * @param remark 备注
	 * @return
	 */
	@RequestMapping(value = "/demand/receive_modify_remark", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> modifyReceiveDemandRemark( HttpServletRequest request,
			@RequestParam(value = "id", required = false, defaultValue = "") String id,
			@RequestParam(value = "remark", required = false, defaultValue = "") String remark
			) {
		if (!ReceiveDemandUtil.validateDemandId(id)) {
			ResponseObject<Object> responseObj = generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数错误!");
			return responseObj;
		}
		
		try {
			String userId = (String) request.getSession().getAttribute(
					Constant.USER_ID_SESSION_KEY);
			return this.demandService.modifyReceiveDemandRemark(userId,id,remark);
		} catch (Exception e) {
			log.error("任务备注修改失败", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION,
					"任务备注修改失败，请重试!");
		}
		
	}
	
	/**
	 * 取消投标
	 * @param request
	 * @param demandId 任务id
	 * @return
	 */
	@RequestMapping(value = "/demand/receive_delete", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> deleteReceiveDemand( HttpServletRequest request,
			@RequestParam(value = "demandId", required = false, defaultValue = "") String demandId
			) {
		if (!ReceiveDemandUtil.validateDemandId(demandId)) {
			ResponseObject<Object> responseObj = generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数错误!");
			return responseObj;
		}
		
		try {
			String userId = (String) request.getSession().getAttribute(
					Constant.USER_ID_SESSION_KEY);
			return this.demandService.deleteReceiveDemand(userId,demandId);
		} catch (Exception e) {
			log.error("取消投标失败", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION,"取消投标失败，请重试!");
		}
		
	}
	/**
	 * 接单人重新报价
	 * @param request
	 * @param id 接单id  
	 * @param amount 报价
	 * @return
	 */
	@RequestMapping(value = "/demand/receive_modify_amount", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> modifyReceiveDemandAmount( HttpServletRequest request,
			@RequestParam(value = "id") String id,
			@RequestParam(value = "amount") String amount
			) {
		if (!ReceiveDemandUtil.validateDemandId(id)) {
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数错误!");
		}
		if( !DemandUtil.validateAmount(amount)){
			return generateResponseObject(ResponseCode.DEMAND_AMOUNT_ERROR, "报价输入不正确，请重新输入!");
		}
		
		try {
			String userId = (String) request.getSession().getAttribute(
					Constant.USER_ID_SESSION_KEY);
			String username = (String) request.getSession().getAttribute(
					Constant.USER_NICK_NAME_SESSION_KEY);
			return this.demandService.modifyReceiveDemandAmount(userId,username,id,amount);
		} catch (Exception e) {
			log.error("任务报价失败", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION,
					"任务报价修改失败，请重试!");
		}
		
	}
	
	/**
	 * 邀请人来完成任务
	 * @param request
	 * @param demandId
	 * @param friendId
	 * @return
	 */
	@RequestMapping(value = "/demand/invite_add", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> addInvite( HttpServletRequest request,
			@RequestParam(value = ParameterConstants.DEMAND_ID) String demandId,
			@RequestParam(value = "friendId") String friendId) {
		ResponseObject<Object> responseObj = null;
		if (!DemandUtil.validateId(demandId)) {
			responseObj = generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数错误!");
			return responseObj;
		}  
		if( !UserUtil.validateId(friendId)){
			responseObj = generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数错误!");
			return responseObj;
		}
		
		try {
			String userId = (String) request.getSession().getAttribute(
					Constant.USER_ID_SESSION_KEY);
			String nickName = (String) request.getSession().getAttribute(
					Constant.USER_NICK_NAME_SESSION_KEY);
			return this.demandService.addInvite(nickName, demandId,friendId,userId);
		} catch (Exception e) {
			log.error("邀请错误，请重试", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION,
					"邀请错误，请重试!");
		}
		
	}

	/**
	 * 发布任务
	 * @param request
	 * @param demand
	 * @return
	 */
	@RequestMapping(value = "/demand/add", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> addDemand( HttpServletRequest request,
	        Demand demand) {
		ResponseObject<Object> responseObj = null;
		if (!DemandUtil.validateTitle(demand.getTitle())) {
			responseObj = generateResponseObject(ResponseCode.DEMAND_TITLE_ERROR, "标题输入不正确，请重新输入!");
			return responseObj;
		}  
		 
		if( !DemandUtil.validateAmount(demand.getAmount())){
			responseObj = generateResponseObject(ResponseCode.DEMAND_AMOUNT_ERROR, "任务赏金金额不正确，请重新输入!");
			return responseObj;
		}
		if( !DemandUtil.validateAchieve(demand.getAchieve())){
			responseObj = generateResponseObject(ResponseCode.DEMAND_AMOUNT_ERROR, "任务成果要求不正确，请重新输入!");
			return responseObj;
		}
		
		/*if(StringUtil.isEmpty(demand.getExpireDate())){
			long curren = System.currentTimeMillis();
			curren += defaultDemandExpireDate;
			Date da = new Date(curren);
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			demand.setExpireDate(dateFormat.format(da));
		}else */
		if(!DemandUtil.isValidDateTime(demand.getExpireDate())){
			responseObj = generateResponseObject(ResponseCode.DEMAND_EXPIRE_DATE_ERROR, "任务截止时间格式不正确，请重新输入!");
			return responseObj;
		}
		
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = format.parse(demand.getExpireDate());
			if(date.getTime()<=new Date().getTime()){
				responseObj = generateResponseObject(ResponseCode.DEMAND_EXPIRE_DATE_ERROR, "任务截止时间小于当前时间，请重新输入!");
				return responseObj;
			}
		} catch (ParseException e) {
			responseObj = generateResponseObject(ResponseCode.DEMAND_EXPIRE_DATE_ERROR, "任务截止时间格式不正确，请重新输入!");
			return responseObj;
		}
		try {
			if (URLDecoder.decode(demand.getTitle(), "UTF-8").length()>60) {
				responseObj = generateResponseObject(ResponseCode.DEMAND_TITLE_ERROR, "标题太长，不能超过60个字!");
				return responseObj;
			} 
			demand.setIp(ReceiveDemandUtil.getIpAddr(request));
			
			
			String userId = (String) request.getSession().getAttribute(
					Constant.USER_ID_SESSION_KEY);
			demand.setUserId(userId);
			demand.setState(Constant.DEMAND_STATE_READY);
			demand.setTitle(URLDecoder.decode(demand.getTitle(), "UTF-8")); 
			demand.setContent(URLDecoder.decode(demand.getContent(), "UTF-8")); 
			demand.setAchieve(URLDecoder.decode(demand.getAchieve(), "UTF-8")); 
			demand.setSecretAchieve(URLDecoder.decode(demand.getSecretAchieve(), "UTF-8")); 
			demand.setTag(URLDecoder.decode(demand.getTag(), "UTF-8")); 
			return this.demandService.addDemand(demand);
		} catch (Exception e) {
			log.error("任务添加失败", e);
			String msg = e.getMessage().substring(e.getMessage().indexOf(":")+1);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, msg); 
		}
		
	}
	
	/**
	 * 用户查找自己接收的任务
	 * @param request
	 * @param key
	 * @param pageIndex
	 * @return
	 */
	@RequestMapping(value = "/demand/receive_search", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseObject<PageSplit<Demand>> searchDemandsByReceiver(HttpServletRequest request,
			@RequestParam(value = ParameterConstants.DEMAND_STATE, required = false, defaultValue = "") String[] states,
			@RequestParam(value = ParameterConstants.COMMON_PAGESPLIT_PAGE_INDEX, required = false, defaultValue = "1") int pageIndex) {
		List<String> stateList = null;
		if(states!=null){
			stateList = Arrays.asList(states);
		}
		
		try {
			String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			return this.demandService.searchByReceiverId(userId, stateList, defaultPageSize, pageIndex);
		} catch (Exception e) {
			log.error("获取任务列表出现异常", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取任务列表失败!");
		}
	}
	
	/**
	 * 接单人查找一个投标任务
	 * @param request
	 * @param demandId 
	 * @return
	 */
	@RequestMapping(value = "/demand/receive_get_one", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseObject<Object> searchDemandByIdForReceiver(HttpServletRequest request,
			@RequestParam(value = "id") String demandId) {
		if (StringUtil.isEmpty(demandId)) {
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数错误！");
		}
		try {
			String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			return this.demandService.searchDemandByIdForReceiver(userId, demandId);
		} catch (Exception e) {
			log.error("获取任务出现异常", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取任务失败!");
		}
	}
	
	/**
	 * 用户查找自己的 demand
	 * @param request
	 * @param key
	 * @param pageIndex
	 * @return
	 */
	@RequestMapping(value = "/demand/search", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseObject<PageSplit<Demand>> searchDemandsByKey(HttpServletRequest request,
			@RequestParam(value = ParameterConstants.DEMAND_STATE, required = false, defaultValue = "") String[] states,
			@RequestParam(value = ParameterConstants.COMMON_SEARCH_KEY, required = false) String key,
			@RequestParam(value = ParameterConstants.COMMON_PAGESPLIT_PAGE_INDEX, required = false, defaultValue = "1") int pageIndex) {
		
		List<String> stateList = null;
		if(states!=null){
			stateList = Arrays.asList(states);
		}
		log.error("stateList:"+stateList.toString());
		try {
			key =  new String(key.getBytes("ISO-8859-1"), "utf-8");
			String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			
			return this.demandService.searchByKey(userId, key ,stateList, defaultPageSize, pageIndex);
		} catch (Exception e) {
			log.error("获取任务列表出现异常", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取任务列表失败!");
		}
	}
	
	/**
	 * 写手查找
	 * @param request
	 * @param key
	 * @param pageIndex
	 * @return
	 */
	@RequestMapping(value = "/demand/search_by_writer", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseObject<PageSplit<Demand>> searchDemandByKeyOfWriter(HttpServletRequest request,
			@RequestParam(value = ParameterConstants.COMMON_SORT_TYPE, required = false,defaultValue = "0") String sortType,
			@RequestParam(value = ParameterConstants.COMMON_SEARCH_KEY, required = false,defaultValue = "") String key,
			@RequestParam(value = ParameterConstants.DEMAND_NATIONALITY, required = false) String nationality,
			@RequestParam(value = ParameterConstants.DEMAND_TYPE, required = false) String type,
			@RequestParam(value = ParameterConstants.DEMAND_TYPE_SECOND, required = false) String typeSecond,
			@RequestParam(value = ParameterConstants.DEMAND_MIN_AMOUNT, required = false) String minAmount,
			@RequestParam(value = ParameterConstants.DEMAND_MAX_AMOUNT, required = false) String maxAmount,
			@RequestParam(value = ParameterConstants.DEMAND_TAG,required = false) String[] tags,
			@RequestParam(value = ParameterConstants.COMMON_PAGESPLIT_PAGE_INDEX, required = false, defaultValue = "1") int pageIndex,
			@RequestParam(value = ParameterConstants.COMMON_PAGESPLIT_PAGE_SIZE, required = false, defaultValue = "20") int pageSize) {
		try {
			key =  new String(key.getBytes("ISO-8859-1"), "utf-8").trim();; 
			Set<String> tagSet = new HashSet<String>();
			if(tags!=null){
				for (String tag : tags) {
					tagSet.add(StringUtil.escapeStringOfSearchKey(new String(tag.getBytes("ISO-8859-1"), "utf-8")));
				}
			}
			List<String> tagList = null;
			if(tagSet.size()>0){
				tagList = new ArrayList<String>(tagSet);  
			}
			//System.out.println("=========================>"+tagList.toString()); 
			return this.demandService.searchAllByKey(sortType, key, nationality, type, typeSecond, minAmount, maxAmount, tagList, pageSize, pageIndex);
		} catch (Exception e) {
			log.error("获取任务列表出现异常", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取任务列表失败!");
		}
	}
	
	@RequestMapping(value = "/demand/get_one", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseObject<Demand> getDemandById( HttpServletRequest request,
	        @RequestParam(value = "id") String id) {
		try {
			if (StringUtil.isEmpty(id)) {
				return generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数不能为空");
			}
			String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			return this.demandService.getDemandById(userId,id);
		} catch (Exception e) {
			log.error("获取任务信息失败", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取任务详细出现异常");
		}
	}
	
	@RequestMapping(value = "/demand/get_one_by_writer", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseObject<Demand> getDemandByIdOfWriter( HttpServletRequest request,
	        @RequestParam(value = "id") String id) {
		try {
			if (!DemandUtil.validateId(id)) {
				return generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数不能为空");
			}
			String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			return this.demandService.getDemandByIdOfWriter(id,userId);
		} catch (Exception e) {
			log.error("获取任务信息失败", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取任务详细出现异常");
		}
	}
	/**
	 * 相关任务
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/demand/relevant", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseObject<Set<Demand>> getRelevantDemandById( HttpServletRequest request,
	        @RequestParam(value = "id") String id) {
		try {
			if (!DemandUtil.validateId(id)) {
				return generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数不能为空");
			}
			return this.demandService.getRelevantDemandById(id);
		} catch (Exception e) {
			log.error("获取相关任务失败，任务id="+id, e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取相关任务出现异常");
		}
	}
	
	@RequestMapping(value = "/demand/receive_add", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> addReceiveDemand( HttpServletRequest request,
			ReceiveDemand receiveDemand) {
		ResponseObject<Object> responseObj = null;
		
		if(!StringUtil.isEmpty(receiveDemand.getAmount())&&Double.valueOf(receiveDemand.getAmount())<10){
			responseObj = generateResponseObject(ResponseCode.DEMAND_AMOUNT_ERROR, "报价不能低于10美金，请重新输入!");
			return responseObj;
		}
		
		receiveDemand.setIp(ReceiveDemandUtil.getIpAddr(request));
		if (!ReceiveDemandUtil.validateDemandId(receiveDemand.getDemandId())) {
			responseObj = generateResponseObject(ResponseCode.RECEIVEDEMAND_ID_ERROR, "您没有选中任务!");
		}   else {
			try {
				
				String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
				receiveDemand.setUserId(userId);
				responseObj = this.demandService.addReceiveDemand(receiveDemand);
			} catch (Exception e) {
				log.error("抢单出异常", e);
				return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "抢单失败，请重试!");
			}
		}
		return responseObj;
	}
	

	@RequestMapping(value = "/demand/receive_get_by_demandid", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseObject<List<ReceiveDemand>> searchReceiveDemandsByDemandId(HttpServletRequest request,
			@RequestParam(value = "id", required = false, defaultValue = "") String id) {
		if (!ReceiveDemandUtil.validateDemandId(id)) {
			ResponseObject<List<ReceiveDemand>> responseObj = generateResponseObject(ResponseCode.PARAMETER_ERROR, "您没有选中任务!");
			return responseObj;
		}
		try {
			String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			return this.demandService.searchByReceiveDemand(userId,id);
		} catch (Exception e) {
			log.error("获取接单人列表出现异常", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取接单人列表失败!");
		}
	}
	
	//匹配选中用户
	/*@RequestMapping(value = "/demand/receive_select", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> selectReceiveDemand(HttpServletRequest request,
			@RequestParam(value = ParameterConstants.RECEIVE_DEMAND_ID) String id,
			@RequestParam(value = ParameterConstants.RECEIVE_DEMAND_DEMAND_ID) String demandId) {
		ResponseObject<Object> responseObj = null;
		if (demandId == null || demandId.equals("")) {
			responseObj = generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数错误！");
		} else{
			try {
				String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
				responseObj = this.demandService.selectReceiveDemand(userId, demandId, id);
			} catch (Exception e) {
				log.error("匹配失败");
				responseObj = generateResponseObject(ResponseCode.SHOW_EXCEPTION, "匹配失败!");
			}
		}
		return responseObj;
	}*/
	/**
	 * 匹配接单人
	 * @param request
	 * @param rdId
	 * @param demandId
	 * @return
	 */
	@RequestMapping(value = "/demand/select", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> selectDemand(HttpServletRequest request,
			@RequestParam(value = ParameterConstants.RECEIVE_DEMAND_ID) String rdId,
			@RequestParam(value = ParameterConstants.RECEIVE_DEMAND_DEMAND_ID) String demandId) {
		ResponseObject<Object> responseObj = null;
		if (!ReceiveDemandUtil.validateId(rdId)) {
			responseObj = generateResponseObject(ResponseCode.PARAMETER_ERROR, "没有选中接单人！");
		} else if (!DemandUtil.validateId(demandId)) {
			responseObj = generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数错误！");
		} else{
			try {
				String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
				responseObj = this.demandService.selectDemand(userId,demandId,rdId);
			} catch (Exception e) {
				log.error("匹配失败");
				responseObj = generateResponseObject(ResponseCode.SHOW_EXCEPTION, "匹配失败，请重试!");
			}
		}
		return responseObj;
	}
	
	/**
	 * 开始任务
	 * @param request
	 * @param demandId
	 * @return
	 */
	@RequestMapping(value = "/demand/start", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> startDemand(HttpServletRequest request,
			@RequestParam(value = ParameterConstants.DEMAND_ID) String demandId) {
		ResponseObject<Object> responseObj = null;
		if (!DemandUtil.validateId(demandId)) {
			responseObj = generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数错误！");
		} else{
			try {
				String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
				String nickName = (String)request.getSession().getAttribute(Constant.USER_NICK_NAME_SESSION_KEY);
				responseObj = this.demandService.startDemand(userId, nickName, demandId);
			} catch (Exception e) {
				log.error("开始任务失败");
				responseObj = generateResponseObject(ResponseCode.SHOW_EXCEPTION, "开始任务失败，请重试!");
			}
		}
		return responseObj;
	}
	
	/**
	 * haokun added 2017/03/10 拒绝任务（接单人可在匹配后选择拒绝任务，任务重新回到匹配状态 1.receive_demand 状态变0 2.demand 状态改为1--已有接单状态 ）
	 * @param request
	 * @param demandId
	 * @return
	 */
	@RequestMapping(value = "/demand/refuse", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> refuseDemand(HttpServletRequest request,
			@RequestParam(value = ParameterConstants.DEMAND_ID) String demandId) {
		ResponseObject<Object> responseObj = null;
		if (!DemandUtil.validateId(demandId)) {
			responseObj = generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数错误！");
		} else{
			try {
				String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
				String nickName = (String)request.getSession().getAttribute(Constant.USER_NICK_NAME_SESSION_KEY);
				responseObj = this.demandService.refuseDemand(userId, nickName, demandId);
			} catch (Exception e) {
				log.error("开始任务失败");
				responseObj = generateResponseObject(ResponseCode.SHOW_EXCEPTION, "开始任务失败，请重试!");
			}
		}
		return responseObj;
	}
	
	/**
	 * 关闭任务
	 * @param request
	 * @param demandId
	 * @return
	 */
	@RequestMapping(value = "/demand/close", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> closeDemand(HttpServletRequest request,
			@RequestParam(value = ParameterConstants.RECEIVE_DEMAND_DEMAND_ID) String demandId) {
		ResponseObject<Object> responseObj = null;
		if (demandId == null || demandId.equals("")) {
			responseObj = generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数错误！");
		} else{
			try {
				String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
				responseObj = this.demandService.closeDemand(userId,demandId);
			} catch (Exception e) {
				log.error("关闭任务失败");
				responseObj = generateResponseObject(ResponseCode.SHOW_EXCEPTION, "关闭任务失败，请重试!");
			}
		}
		return responseObj;
	}
	
	/**
	 * 接单人提交任务成果，任务结束
	 * @param request
	 * @param rd
	 * @return
	 */
	@RequestMapping(value = "/demand/finish_by_receiver", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> finishDemandByReceiver(HttpServletRequest request,
			ReceiveDemand rd) {
		ResponseObject<Object> responseObj = null;
		if (!ReceiveDemandUtil.validateDemandId(rd.getDemandId())) {
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数无效");
		}
		if (!ReceiveDemandUtil.validateResultDesc(rd.getResultDesc())) {
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数无效");
		}
		
		try {
			String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			String nickName = (String)request.getSession().getAttribute(Constant.USER_NICK_NAME_SESSION_KEY);
			responseObj = this.demandService.finishDemandByReceiver(userId, nickName, rd);
		} catch (Exception e) {
			log.error("完成任务失败");
			responseObj = generateResponseObject(ResponseCode.SHOW_EXCEPTION, "完成任务失败，请重试!");
		}
		
		return responseObj;
	}
	
	/**
	 * 发单人结束任务
	 * @param request
	 * @param demandId
	 * @return
	 */
	@RequestMapping(value = "/demand/finish", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> finishDemand(HttpServletRequest request,
			@RequestParam(value = ParameterConstants.RECEIVE_DEMAND_DEMAND_ID) String demandId) {
		ResponseObject<Object> responseObj = null;
		if (demandId == null || demandId.equals("")) {
			responseObj = generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数错误！");
		} else{
			try {
				String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
				String nickName = (String)request.getSession().getAttribute(Constant.USER_NICK_NAME_SESSION_KEY);
				responseObj = this.demandService.finishDemand(userId, nickName, demandId);
			} catch (Exception e) {
				log.error("完成任务失败");
				responseObj = generateResponseObject(ResponseCode.SHOW_EXCEPTION, "完成任务失败，请重试!");
			}
		}
		return responseObj;
	}
	
	/**
	 * 接单人对支付结果满意
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/demand/pay_agree", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> agreePay( HttpServletRequest request,
			@RequestParam(value = "demandId") String demandId) {
		ResponseObject<Object> responseObj = null;
		if (demandId == null) {
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数无效");
		}
		if(!DemandUtil.validateId(demandId)){
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数无效");
		}
		try {
			String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			String nickName = (String)request.getSession().getAttribute(Constant.USER_NICK_NAME_SESSION_KEY);
			responseObj = this.demandService.agreePay(userId, nickName, demandId);
			
		} catch (Exception e) {
			log.error("收款成功失败", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "收款成功失败!");
		}
		return responseObj;
	}
	
	/**
	 * 发单人确认支付
	 * @param request
	 * @param rd
	 * @return
	 */
	@RequestMapping(value = "/demand/pay", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> PayDemand(HttpServletRequest request,
			ReceiveDemand rd) {
		ResponseObject<Object> responseObj = null;
		if (rd == null) {
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数无效");
		}
		if (!ReceiveDemandUtil.validateDemandId(rd.getDemandId())) {
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数无效");
		}
		if(!ReceiveDemandUtil.validatePercentage(rd.getPayPercent())){
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数无效");
		}
		try {
			String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			String nickName = (String)request.getSession().getAttribute(Constant.USER_NICK_NAME_SESSION_KEY);
			responseObj = this.demandService.payDemand(userId, nickName, rd);
		} catch (Exception e) {
			log.error("支付失败", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "支付失败!");
		}
		return responseObj;
	}
	
	/**
	 * 接单人仲裁提交
	 * @param request
	 * @param rd
	 * @return
	 */
	@RequestMapping(value = "/demand/pay_oppose", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> opposePay(HttpServletRequest request,
			ReceiveDemand rd) {
		ResponseObject<Object> responseObj = null;
		if (rd == null) {
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数无效");
		}
		if (!ReceiveDemandUtil.validateDemandId(rd.getDemandId())) {
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数无效");
		}
		if(!ReceiveDemandUtil.validatePercentage(rd.getRefutePercent())){
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数无效");
		}
		try {
			String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			String nickName = (String)request.getSession().getAttribute(Constant.USER_NICK_NAME_SESSION_KEY);
			responseObj = this.demandService.opposePay(userId, nickName, rd);
		} catch (Exception e) {
			log.error("仲裁提交失败", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "仲裁提交失败!");
		}
		return responseObj;
	}
	
	/**
	 * 发单人同意对方仲裁
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/demand/agree_arbitration", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> agreeArbitration( HttpServletRequest request,
			@RequestParam(value = "demandId") String demandId) {
		ResponseObject<Object> responseObj = null;
		if (demandId == null) {
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数无效");
		}
		if(!DemandUtil.validateId(demandId)){
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数无效");
		}
		try {
			String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			String nickName = (String)request.getSession().getAttribute(Constant.USER_NICK_NAME_SESSION_KEY);
			responseObj = this.demandService.agreeArbitration(userId, nickName, demandId);
			
		} catch (Exception e) {
			log.error("同意对方付款要求失败", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "同意对方付款要求失败!");
		}
		return responseObj;
	}
	
	/**
	 * 管理员裁决任务
	 * @param request
	 * @param rd
	 * @return
	 */
	@RequestMapping(value = "/admin/demand/end", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> endDemand(HttpServletRequest request,
			ReceiveDemand rd) {
		ResponseObject<Object> responseObj = null;
		if (rd == null) {
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数无效");
		}
		if (!ReceiveDemandUtil.validateDemandId(rd.getDemandId())) {
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数无效");
		}
		if(!ReceiveDemandUtil.validatePercentage(rd.getRulePercent())){
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数无效");
		}
		try {
			responseObj = this.demandService.endDemand(rd);
		} catch (Exception e) {
			log.error("任务裁决失败", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "任务裁决失败!");
		}
		return responseObj;
	}

	/**
	 * 评价
	 * add by chenkanghua qq:846645133
	 * @param request
	 * @param id
	 * @param evaluate
	 * @return
	 */
	@RequestMapping(value = "/demand/evaluate", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> evaluate(HttpServletRequest request,
			Evaluate evaluate){
		ResponseObject<Object> responseObj = null;
		
		if (evaluate == null) {
			responseObj = generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数错误！");
			return responseObj;
		} 
		Double dEvaluate = Double.valueOf(evaluate.getEvaluate() != null && !"".equals( evaluate.getEvaluate().trim())? evaluate.getEvaluate() : "5");
		Double dQuality = Double.valueOf(evaluate.getQuality() != null && !"".equals( evaluate.getQuality().trim())? evaluate.getQuality().trim() : "5");
		Double dPunctual = Double.valueOf(evaluate.getPunctual() != null && !"".equals( evaluate.getPunctual().trim())? evaluate.getPunctual().trim() : "5"); 
		//评分只能是0-5分
		if (dEvaluate < 0 && dEvaluate > 5) {
			responseObj = generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数错误！");
			return responseObj;
		} 
		if (dQuality < 0 && dQuality > 5) {
			evaluate.setQuality("5");
		} 
		if (dPunctual < 0 && dPunctual > 5) {
			evaluate.setPunctual("5");
		} 
		//被评价用户不能为空 
		if (evaluate.getEvaluateUserId() ==null || evaluate.getEvaluateUserId().equals("")) {
			responseObj = generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数错误！");
			return responseObj;
		}  
		//被评价任务不能为空
		if (evaluate.getDemandId() == null || evaluate.getDemandId().equals("")) {
			responseObj = generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数错误！");
			return responseObj; 
		} 
		
		try { 
			String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			responseObj = this.demandService.evaluate(userId,evaluate);
		} catch (Exception e) {
			log.error("评价出错！");
			e.printStackTrace();
			responseObj = generateResponseObject(ResponseCode.SHOW_EXCEPTION, "评价出错！请重试");
		}
		return responseObj;
	}
	
	/**
	 * 发布任务者给做任务者评价
	 * add by chenkanghua qq:846645133
	 * @param request
	 * @param id
	 * @param evaluate
	 * @return
	 */
	/*
	@RequestMapping(value = "/demand/evaluate", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> evaluate(HttpServletRequest request,
			@RequestParam(value = ParameterConstants.EVALUATE_RECEIVE_DEMAND_ID) String receiveDemandId,
			@RequestParam(value = ParameterConstants.EVALUATE_EVALUATE) String evaluate,
			@RequestParam(value = ParameterConstants.EVALUATE_DESCRIBE) String describe) {
		ResponseObject<Object> responseObj = null;
		if (receiveDemandId == null || receiveDemandId.equals("")) {
			responseObj = generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数错误！");
			return responseObj;
		} 
		double dEvaluate = Double.valueOf(evaluate != null ? evaluate.trim() : "");
		//评分只能是0-5分
		if (dEvaluate < 0 && dEvaluate > 5) {
			responseObj = generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数错误！");
			return responseObj;
		} 
		try {
			String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			responseObj = this.demandService.evaluate(userId,receiveDemandId,evaluate,describe);
		} catch (Exception e) {
			log.error("评价出错！");
			responseObj = generateResponseObject(ResponseCode.SHOW_EXCEPTION, "评价出错！请重试");
		}
		return responseObj;
	}
	*/
	/**
	 * 做任务者者给发布任务评价
	 * add by chenkanghua qq:846645133
	 * @param request
	 * @param id
	 * @param evaluate
	 * @return
	 */
	/*@RequestMapping(value = "/demand/evaluate_publish", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> evaluatePublish(HttpServletRequest request,
			@RequestParam(value = ParameterConstants.EVALUATE_RECEIVE_DEMAND_ID) String receiveDemandId,
			@RequestParam(value = ParameterConstants.EVALUATE_EVALUATE) String evaluate,
			@RequestParam(value = ParameterConstants.EVALUATE_DESCRIBE) String describe) {
		ResponseObject<Object> responseObj = null;
		if (receiveDemandId == null || receiveDemandId.equals("")) {
			responseObj = generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数错误！");
			return responseObj;
		} 
		double dEvaluate = Double.valueOf(evaluate != null ? evaluate.trim() : "");
		//评分只能是0-5分
		if (dEvaluate < 0 && dEvaluate > 5) {
			responseObj = generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数错误！");
			return responseObj;
		} 
		try {
			String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			responseObj = this.demandService.evaluatePublish(userId,receiveDemandId,evaluate,describe);
		} catch (Exception e) {
			log.error("评价出错！");
			responseObj = generateResponseObject(ResponseCode.SHOW_EXCEPTION, "评价出错！请重试");
		}
		return responseObj;
	}*/
	
	@RequestMapping(value = "/demand/get_sum_amount", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseObject<Object> getSumAmount() {
		try {
			List<String> states = new ArrayList<String>();
			states.add("0");
			states.add("1");
			states.add("2");
			states.add("3");   /*haokun added 2017/03/02 增加了任务类型*/
			states.add("5");   /*haokun added 2017/03/02 增加了任务类型*/
			states.add("6");   /*haokun added 2017/03/02 增加了任务类型*/
			states.add("7");   /*haokun added 2017/03/02 增加了任务类型*/
			return this.demandService.getDemandSumAmountByState(states);
		} catch (Exception e) {
			log.error("获取任务总金额异常", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取任务总金额异常!");
		}
	}
	
	@RequestMapping(value = "/demand/demand_msg_add", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> addDemandMessage( HttpServletRequest request,
			DemandMessage demandMessage) {
		ResponseObject<Object> responseObj = null;
		try {
			demandMessage.setContent(URLDecoder.decode(demandMessage.getContent(), "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			demandMessage.setContent(null);
		}
		if (!DemandMessageUtil.validateContent(demandMessage.getContent())) {
			return generateResponseObject(ResponseCode.EDEMANDMESSAGE_CONTENT_ERROR, "留言信息输入不正确，请重新输入!");
		}

		if (!DemandMessageUtil.validateParentId(demandMessage.getParentId())) {
			return generateResponseObject(ResponseCode.EDEMANDMESSAGE_FATHER_ID_ERROR, "参数错误!");
		}
		if (!DemandMessageUtil.validateDemandId(demandMessage.getDemandId())) {
			return responseObj = generateResponseObject(ResponseCode.EDEMANDMESSAGE_DEMAND_ID_ERROR, "参数错误!");
		} 
		if (!UserUtil.validateId(demandMessage.getReceiverId())) {
			return responseObj = generateResponseObject(ResponseCode.EDEMANDMESSAGE_DEMAND_ID_ERROR, "参数错误!");
		} 

		/*if (StringUtil.isEmpty(demandMessage.getParentId())) {
			demandMessage.setParentId("-1");
		}*/
		try {

			String userId = (String) request.getSession().getAttribute(
					Constant.USER_ID_SESSION_KEY);
			String userNick = (String) request.getSession().getAttribute(
					Constant.USER_NICK_NAME_SESSION_KEY);
			demandMessage.setUserId(userId);
			demandMessage.setUserNickName(userNick);
			demandMessage.setState("0");
			responseObj = this.demandService.addDemandMessage(demandMessage);
		} catch (Exception e) {
			log.error("提交留言失败", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION,
					"提交留言失败，请重试!");
		}
		return responseObj;
	}
	/**
	 * 获取赚取金额和完成任务数
	 * @return
	 */
	@RequestMapping(value = "/demand/sum_amount_and_count_finish", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseObject<Map<String,Object>> getSumAmountAndCountFinish(HttpServletRequest request) {
		try {
			String userId = (String) request.getSession().getAttribute(
					Constant.USER_ID_SESSION_KEY);
			return this.demandService.getSumAmountAndCountFinish(userId);
		} catch (Exception e) {
			log.error("获取已赚金额和完成任务总数异常", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取已赚金额和完成任务总数异常!");
		}
	}
	
	
	@RequestMapping(value = "/demand/all_region", method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Set<String>> searchDemandAllRegion(HttpServletRequest request,
			@RequestParam(value = "region", required = false, defaultValue = "") String region) {
		ResponseObject<Set<String>> responseObj = null;
		if(region.length()<3){
			responseObj = generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数错误！");
			return responseObj;
		}
		try {
			return this.demandService.searchDemandAllRegion(region);
		} catch (Exception e) {
			log.error("获取所有任务位置失败", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取所有任务位置失败!");
		}
	}

	
	
	
	/**
	 * 管理员查找任务
	 * @param request
	 * @param key
	 * @param pageIndex
	 * @return
	 */
	@RequestMapping(value = "/admin/demand/search", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseObject<PageSplit<Demand>> adminSearchDemandsByKey(HttpServletRequest request,
			@RequestParam(value = ParameterConstants.COMMON_SORT_TYPE, required = false,defaultValue = "0") String sortType,
			@RequestParam(value = ParameterConstants.DEMAND_STATE, required = false, defaultValue = "") String[] states,
			@RequestParam(value = ParameterConstants.COMMON_SEARCH_KEY, required = false) String key,
			@RequestParam(value = ParameterConstants.DEMAND_NATIONALITY, required = false) String nationality,
			@RequestParam(value = ParameterConstants.DEMAND_TYPE, required = false) String type,
			@RequestParam(value = ParameterConstants.DEMAND_TYPE_SECOND, required = false) String typeSecond,
			@RequestParam(value = ParameterConstants.DEMAND_MIN_AMOUNT, required = false) String minAmount,
			@RequestParam(value = ParameterConstants.DEMAND_MAX_AMOUNT, required = false) String maxAmount,
			@RequestParam(value = ParameterConstants.DEMAND_TAG,required = false) String[] tags,
			@RequestParam(value = ParameterConstants.COMMON_PAGESPLIT_PAGE_INDEX, required = false, defaultValue = "1") int pageIndex,
			@RequestParam(value = ParameterConstants.COMMON_PAGESPLIT_PAGE_SIZE, required = false, defaultValue = "20") int pageSize) {
		
		
		try { 
			List<String> stateList = null;
			if(states!=null){
				stateList = Arrays.asList(states);
			}
			key =  new String(key.getBytes("ISO-8859-1"), "utf-8");
			Set<String> tagSet = new HashSet<String>();
			if(tags!=null){
				for (String tag : tags) {
					tagSet.add(StringUtil.escapeStringOfSearchKey(new String(tag.getBytes("ISO-8859-1"), "utf-8")));
				}
			}
			List<String> tagList = null;
			if(tagSet.size()>0){
				tagList = new ArrayList<String>(tagSet);  
			}
			return this.demandService.adminSearchByKey(key, stateList, sortType, nationality, type, typeSecond, minAmount, maxAmount, tagList, pageSize, pageIndex);
		} catch (Exception e) {
			log.error("获取任务列表出现异常", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取任务列表失败!");
		}
	}
	
	@RequestMapping(value = "/admin/demand/modify_remark", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> adminModifyRemarkAdmin( HttpServletRequest request,
			@RequestParam(value = "id", required = false, defaultValue = "") String id,
			@RequestParam(value = "remark", required = false, defaultValue = "") String remark
			) {
		if (!ReceiveDemandUtil.validateDemandId(id)) {
			ResponseObject<Object> responseObj = generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数错误!");
			return responseObj;
		}
		
		try {
			return this.demandService.modifyDemandRemarkAdmin(id,remark);
		} catch (Exception e) {
			log.error("备注修改失败", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION,
					"备注修改失败，请重试!");
		}
		
	}
	
	
	
	/**
	 * 已发布的任务
	 * @param request
	 * @param userId
	 * @param pageIndex
	 * @return
	 */
	@RequestMapping(value = "/demand/pub_search", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseObject<PageSplit<Demand>> searchPubByUserId(HttpServletRequest request,
			@RequestParam(value = ParameterConstants.USER_ID) String userId,
			@RequestParam(value = ParameterConstants.COMMON_PAGESPLIT_PAGE_INDEX, required = false, defaultValue = "1") int pageIndex) {
		if(!UserUtil.validateId(userId)){
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数错误！");
		}
		try {
			return this.demandService.searchPubByUserId(userId, defaultPageSize, pageIndex);
		} catch (Exception e) {
			log.error("获取已发布任务列表出现异常", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取已发布任务列表失败!");
		}
	}
	/**
	 * 已解决的任务
	 * @param request
	 * @param userId
	 * @param pageIndex
	 * @return
	 */
	@RequestMapping(value = "/demand/acc_search", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseObject<PageSplit<Demand>> searchAccByUserId(HttpServletRequest request,
			@RequestParam(value = ParameterConstants.USER_ID) String userId,
			@RequestParam(value = ParameterConstants.COMMON_PAGESPLIT_PAGE_INDEX, required = false, defaultValue = "1") int pageIndex) {
		if(!UserUtil.validateId(userId)){
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数错误！");
		}
		try {
			return this.demandService.searchAccByUserId(userId, defaultPageSize, pageIndex);
		} catch (Exception e) {
			log.error("获取已解决任务列表出现异常", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取已解决任务列表失败!");
		}
	}
	
	/**
	 * 余额支付任务金额
	 * @param request
	 * @param rdId
	 * @param demandId
	 * @return
	 */
	@RequestMapping(value = "/demand/pay_balance", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> demandPayByBalance(HttpServletRequest request,
			@RequestParam(value = ParameterConstants.RECEIVE_DEMAND_ID) String rdId,
			@RequestParam(value = ParameterConstants.RECEIVE_DEMAND_DEMAND_ID) String demandId) {
		ResponseObject<Object> responseObj = null;
		if (!ReceiveDemandUtil.validateId(rdId)) {
			responseObj = generateResponseObject(ResponseCode.PARAMETER_ERROR, "没有选中接单人！");
		} else if (!DemandUtil.validateId(demandId)) {
			responseObj = generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数错误！");
		} else{
			try {
				String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
				responseObj = this.demandService.demandPayByBalance(userId,demandId,rdId);
			} catch (Exception e) {
				log.error("余额支付失败"); 
				e.printStackTrace();
				responseObj = generateResponseObject(ResponseCode.SHOW_EXCEPTION, "余额支付失败，请重试!");
			}
		}
		return responseObj;
	}
	
	/**
	 * 信用卡支付任务金额
	 * @param request
	 * @param rdId
	 * @param demandId
	 * @return
	 */
	@RequestMapping(value = "/demand/credit_balance", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> demandPayByCredit(HttpServletRequest request,
			@RequestParam(value = "brand") String brand,
			@RequestParam(value = "name") String name,
			@RequestParam(value = "creditNo") String creditNo,
			@RequestParam(value = "securityCode") String securityCode,
			@RequestParam(value = "expireYear") String expireYear,
			@RequestParam(value = "expireMonth") String expireMonth,
			@RequestParam(value = "currency") String currency,
			@RequestParam(value = "amount") String amount,
			@RequestParam(value = ParameterConstants.RECEIVE_DEMAND_ID) String rdId,
			@RequestParam(value = ParameterConstants.RECEIVE_DEMAND_DEMAND_ID) String demandId) {
		ResponseObject<Object> responseObj = null;
		if (!AccountUtil.validateCurrency(currency)) {
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "充值币种不正确，请重新币种！");
		}else if (!AccountUtil.validateCreditName(name)) {
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "信用卡持卡人姓名输入错误，请重新输入！");
		}else if (!AccountUtil.validateCreditNo(creditNo)) {
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "信用卡号码输入错误，请重新输入！");
		}else if (!AccountUtil.validateCreditSecurityCode(securityCode)) {
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "信用卡安全码输入错误，请重新输入！");
		}else if (!AccountUtil.validateCreditExpireYear(expireYear)) {
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "信用卡到期年信息输入错误，请重新输入！");
		}else if (!AccountUtil.validateCreditExpireMonth(expireMonth)) {
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "信用卡到期月信息输入错误，请重新输入！");
		}else if (!AccountUtil.validateAmount(amount)) {
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "充值金额输入错误，请重新输入！");
		}else if (!ReceiveDemandUtil.validateId(rdId)) {
			responseObj = generateResponseObject(ResponseCode.PARAMETER_ERROR, "没有选中接单人！");
		} else if (!DemandUtil.validateId(demandId)) {
			responseObj = generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数错误！");
		} else{
			try {
				String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
				responseObj = this.demandService.demandPayByCredit(userId,demandId,rdId,brand,name,creditNo,securityCode,expireYear,expireMonth,currency,amount);
			} catch (Exception e) {
				log.error("信用卡支付失败"); 
				e.printStackTrace();
				responseObj = generateResponseObject(ResponseCode.SHOW_EXCEPTION, "信用卡支付失败，请重试!");
			}
		}
		return responseObj;
	}
}
