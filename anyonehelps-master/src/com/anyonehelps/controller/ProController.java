package com.anyonehelps.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.security.NoSuchAlgorithmException;

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
import com.anyonehelps.common.util.AmazonS3Samples;
import com.anyonehelps.common.util.DemandUtil;
import com.anyonehelps.common.util.StringUtil;
import com.anyonehelps.common.util.UserUtil;
import com.anyonehelps.model.FileMeta;
import com.anyonehelps.model.PageSplit;
import com.anyonehelps.model.ProUser;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.model.User;
import com.anyonehelps.service.ProService;

@Controller
public class ProController extends BasicController {
	private static final long serialVersionUID = 1562088471869155201L;
	private static final Logger log = Logger.getLogger(ProController.class);

	@Resource(name = "proService") 
	private ProService proService;
	
	@Value(value = "${page_size}")
	private int defaultPageSize;
	
	//文件类型
	@Value(value = "${pro_id_file_type}")
	private String proIdFileType;
	//文件保存路径
	@Value(value = "${pro_id_file_save_dir}")
	private String proIdFileSaveDir;
	//文件最大大小
	@Value(value = "${pro_id_file_size}")
	private long proIdFileSize;

	@Value(value = "${amazon_s3_url}")
	private String amazonS3Url;
	
	@RequestMapping(value = "/pro_user/upload", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> uploadProId(
			HttpServletRequest request,
			@RequestParam(value = "file", required = false) MultipartFile mpf) {
		if (mpf == null) {
			return generateResponseObject( ResponseCode.PARAMETER_ERROR, "没有文件,请选择文件后再上传!");
		}
		
		String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
		//LinkedList<FileMeta> files = new LinkedList<FileMeta>();
		FileMeta fileMeta = null;
		
		// 解决火狐的反斜杠问题 kai 20151006
		String filetype = this.proIdFileType;// 要上传的文件类型
		
		//2.3 create new fileMeta
		fileMeta = new FileMeta();
		fileMeta.setFileName(mpf.getOriginalFilename());
		fileMeta.setFileSize(mpf.getSize()/1024+" Kb");
		fileMeta.setFileType(mpf.getContentType());
		if (mpf != null && mpf.getSize() > 0) {
			if (mpf.getSize() > this.proIdFileSize) {
				return generateResponseObject(ResponseCode.PARAMETER_ERROR, "允许上传文件最大为20MB!");
			}
			String originalName = mpf.getOriginalFilename();
			if (!DemandUtil.validateFileType(originalName, filetype)) {
				return generateResponseObject( ResponseCode.PARAMETER_ERROR, "上传附件格式不正确,请重新尝试!");
			}
			int index = originalName.lastIndexOf('.');
			index = Math.max(index, 0);
			String saveFileName = this.proIdFileSaveDir + "/" + userId + "_"
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
		
		ResponseObject<Object> responseObj = new ResponseObject<Object>();
		responseObj.setCode(ResponseCode.SUCCESS_CODE);
		responseObj.setData(fileMeta);
		return responseObj;
	}

	@RequestMapping(value = "/pro_user/add", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> addProUser( HttpServletRequest request,
	        ProUser pu) {
		if(!UserUtil.validateId(pu.getProTypeId())){
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "请选择申请专业领域类型!");
		}
		if(!UserUtil.validateId(pu.getProId())){
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "请选择领域!");
		}
		try {
			String userId = (String) request.getSession().getAttribute(
					Constant.USER_ID_SESSION_KEY);
			pu.setUserId(userId);
			pu.setState(Constant.PROUSER_STATE0);
			pu.setReason(URLDecoder.decode(pu.getReason(), "UTF-8"));
			pu.setRealname(URLDecoder.decode(pu.getRealname(), "UTF-8"));
			return this.proService.addProUser(pu);
		} catch (Exception e) {
			log.error("大牛申请提交失败", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "大牛申请提交失败!");
		}
		
	}
	
	/**
	 * 查找大牛
	 * @return
	 */
	@RequestMapping(value = "/pro_user/search", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseObject<PageSplit<User>> search( HttpServletRequest request,
			@RequestParam(value = ParameterConstants.COMMON_SEARCH_KEY, required = false, defaultValue = "") String key,
			@RequestParam(value = "proTypeId", required = false, defaultValue = "") String proTypeId,
			@RequestParam(value = "proId", required = false, defaultValue = "") String proId,
			@RequestParam(value = ParameterConstants.COMMON_PAGESPLIT_PAGE_INDEX, required = false, defaultValue = "1") int pageIndex,
			@RequestParam(value = ParameterConstants.COMMON_PAGESPLIT_PAGE_SIZE, required = false, defaultValue = "10") int pageSize
			) {
		try {
			key =  new String(key.getBytes("ISO-8859-1"), "utf-8");
			String userId = (String) request.getSession().getAttribute(
					Constant.USER_ID_SESSION_KEY);
			
			return this.proService.searchProUser(userId, key, proTypeId, proId, pageSize, pageIndex);
		} catch (Exception e) {
			log.error("查询大牛异常", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取大牛失败!");
		}
	}

	/**
	 * 查找自己申请大牛记录
	 * @return
	 */
	@RequestMapping(value = "/pro_user/search_self", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseObject<PageSplit<ProUser>> searchSelf( HttpServletRequest request,
			@RequestParam(value = ParameterConstants.COMMON_PAGESPLIT_PAGE_INDEX, required = false, defaultValue = "1") int pageIndex,
			@RequestParam(value = ParameterConstants.COMMON_PAGESPLIT_PAGE_SIZE, required = false, defaultValue = "10") int pageSize
			) {
		try {
			String userId = (String) request.getSession().getAttribute(
					Constant.USER_ID_SESSION_KEY);
			
			return this.proService.searchProUserByUserId(userId, pageSize, pageIndex);
		} catch (Exception e) {
			log.error("获取申请大牛记录异常", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取申请大牛记录失败!");
		}
	}
	
	/**
	 * 查找自己申请大牛记录
	 * @return
	 */
	@RequestMapping(value = "/pro_user/get_one", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseObject<ProUser> getById( HttpServletRequest request,
			@RequestParam(value = "id") String id) {
		try {
			String userId = (String) request.getSession().getAttribute(
					Constant.USER_ID_SESSION_KEY);
			
			return this.proService.getProUserById(userId, id);
		} catch (Exception e) {
			log.error("获取申请大牛信息异常", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取申请大牛信息失败!");
		}
	}
	
}
