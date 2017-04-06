package com.anyonehelps.controller;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.anyonehelps.common.constants.Constant;
import com.anyonehelps.common.constants.ParameterConstants;
import com.anyonehelps.common.constants.ResponseCode;
import com.anyonehelps.common.util.AmazonS3Samples;
import com.anyonehelps.common.util.DateUtil;
import com.anyonehelps.common.util.HttpsClient;
import com.anyonehelps.common.util.PropertiesReader;
import com.anyonehelps.common.util.StringUtil;
import com.anyonehelps.common.util.UserUtil;
import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.EmailSend;
import com.anyonehelps.model.IPRecord;
import com.anyonehelps.model.PageSplit;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.model.User;
import com.anyonehelps.service.CommonService;
import com.anyonehelps.service.EmailSendService;
import com.anyonehelps.service.IPRecordService;
import com.anyonehelps.service.UserService;

@Controller
public class UserController extends BasicController {
	private static final long serialVersionUID = 1562088471869155201L;
	private static final Logger log = Logger.getLogger(UserController.class);
	private final static char[] BASIC_CHARACTERS = "qwertyuioplkjhgfdsazxxcvbnm1234567890".toCharArray();
	
	@Resource(name = "userService")
	private UserService userService;
	
	@Resource(name = "commonService")
	private CommonService commonService;

	@Resource(name = "emailSendService")
	private EmailSendService emailSendService;

	@Resource(name = "ipRecordService")
	private IPRecordService ipRecordService;
	//文件类型
	@Value(value = "${user_pic_file_type}")
	private String userPicFileType;
	
	//文件保存路径
	@Value(value = "${user_pic_file_save_dir}")
	private String userPicFileSaveDir;
	//文件最大大小
	@Value(value = "${user_pic_file_size}")
	private long userPicFileSize;
	
	//默认头像路径
	@Value(value = "${user_pic_default_url}")
	private String userPicDefaultUrl;
	
	@Value(value = "${weixin_appid}")
	private String weixinAppid;
	@Value(value = "${weixin_app_secret}")
	private String weixinAppSecret;
	@Value(value = "${weixin_api_oauth2}")
	private String weixinApiOauth2;

	@Value(value = "${amazon_s3_url}")
	private String amazonS3Url;
	
	//文件保存路径
	@Value(value = "${default_file_save_dir}")
	private String defaultFileSaveDir;
	
	
	/*haokun added 上传封面 2017/02/24*/
	@RequestMapping(value = "/user/cover_upload", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> uploadUserCover( HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "user_cover_img", required = true) MultipartFile mpf) {
		
		if (mpf == null) {
			   return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "没有选择封面图片，请选择后再提交!");
		}
		//ModelAndView mav = new ModelAndView();
		String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
		//LinkedList<FileMeta> files = new LinkedList<FileMeta>();
		//FileMeta fileMeta = null;
		String saveFileName = null;
		
		// 解决火狐的反斜杠问题 kai 20151006
		String filetype = this.userPicFileType;// 要上传的文件类型
		
		if (mpf != null && mpf.getSize() > 0) {
			if (mpf.getSize() > this.userPicFileSize) {
                return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "上传图片最大为5MB!");
			}
			String originalName = mpf.getOriginalFilename();
			if (!UserUtil.validateFileType(originalName, filetype)) {
				generateResponseObject(ResponseCode.SHOW_EXCEPTION, "相片格式不正确!");
			}
			int index = originalName.lastIndexOf('.');
			index = Math.max(index, 0);
			saveFileName = this.userPicFileSaveDir + "/" + userId + "_"
				+ StringUtil.generateRandomString(5) + "_"
				+ StringUtil.generateRandomInteger(5)
				+ originalName.substring(index);
		    CommonsMultipartFile cf= (CommonsMultipartFile)mpf; 
		    DiskFileItem fi = (DiskFileItem)cf.getFileItem(); 
			AmazonS3Samples amazonS3Samples = new AmazonS3Samples();
			try {
				amazonS3Samples.pushFile(saveFileName, fi.getStoreLocation());
			} catch (NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "修改封面失败!");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "修改封面失败!");
			} catch (org.jets3t.service.ServiceException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "修改封面失败!");
			}
		}
		try {
			ResponseObject<Object> ro = this.userService.modifyUserCover(userId, this.amazonS3Url + saveFileName);  //把封面图片保存到user 的cover_url字段
			if(ro.getCode()==ResponseCode.SUCCESS_CODE){
				//HttpSession session = request.getSession();
				//session.setAttribute(Constant.USER_PIC_URL_SESSION_KEY, this.amazonS3Url + saveFileName);  //传递
				ResponseObject<Object> responseObj = new ResponseObject<Object>();
				responseObj.setCode(ResponseCode.SUCCESS_CODE);
				return responseObj;
			}else{
				return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "修改封面失败!"); 
			}
		} catch (ServiceException e) {
			log.error("修改封面失败！", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "修改封面失败!");
		}
		
	}
	
	
	@RequestMapping(value = "/user/pic_upload", method = { RequestMethod.POST })
	@ResponseBody
	public ModelAndView uploadUserPic( HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "picFile", required = true) MultipartFile mpf) {
		
		ModelAndView mav = new ModelAndView();
		if (mpf == null) {
			    mav.addObject("picMessage", "没有选择相片,请选择相片后再提交!");
			    mav.setViewName("redirect:/dashboard/Account/setting.jsp#tab_1_2");
				return mav;
		}
		//ModelAndView mav = new ModelAndView();
		String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
		//LinkedList<FileMeta> files = new LinkedList<FileMeta>();
		//FileMeta fileMeta = null;
		String saveFileName = null;
		
		// 解决火狐的反斜杠问题 kai 20151006
		String filetype = this.userPicFileType;// 要上传的文件类型
		//String strtest = this.userPicFileSaveDir;
		/*String strseparator = "";
		if (strtest.indexOf("/") >= 0)// 包含有“/”字符，分隔符用此字符
		{
			strseparator = "/";
		} else {
			strseparator = File.separator;
		}*/
		
		if (mpf != null && mpf.getSize() > 0) {
			if (mpf.getSize() > this.userPicFileSize) {
				mav.addObject("picMessage", "允许上传文件最大为5MB!");
			    mav.setViewName("redirect:/dashboard/Account/setting.jsp#tab_1_2");
				return mav;
			}
			String originalName = mpf.getOriginalFilename();
			if (!UserUtil.validateFileType(originalName, filetype)) {
				mav.addObject("picMessage", "相片格式不正确,请按提交规定的格式!");
			    mav.setViewName("redirect:/dashboard/Account/setting.jsp#tab_1_2");
				return mav;
			}
			int index = originalName.lastIndexOf('.');
			index = Math.max(index, 0);
			saveFileName = this.userPicFileSaveDir + "/" + userId + "_"
				+ StringUtil.generateRandomString(5) + "_"
				+ StringUtil.generateRandomInteger(5)
				+ originalName.substring(index);
			//fileMeta.setSaveFileName(saveFileName);
		    CommonsMultipartFile cf= (CommonsMultipartFile)mpf; 
		    DiskFileItem fi = (DiskFileItem)cf.getFileItem(); 
			AmazonS3Samples amazonS3Samples = new AmazonS3Samples();
			try {
				amazonS3Samples.pushFile(saveFileName, fi.getStoreLocation());
			} catch (NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

				mav.addObject("picMessage", "修改头像失败，请重试!");
			    mav.setViewName("redirect:/dashboard/Account/setting.jsp#tab_1_2");
				return mav;
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

				mav.addObject("picMessage", "修改头像失败，请重试!");
			    mav.setViewName("redirect:/dashboard/Account/setting.jsp#tab_1_2");
				return mav;
			} catch (org.jets3t.service.ServiceException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

				mav.addObject("picMessage", "修改头像失败，请重试!");
			    mav.setViewName("redirect:/dashboard/Account/setting.jsp#tab_1_2");
				return mav;
			}
			
			
			/*try {
				File file1 = new File(request.getSession().getServletContext()
					.getRealPath("/")+ saveFileName);
				mpf.transferTo(file1);
				//files.add(fileMeta);
			} catch (Exception e) {
				log.error("更改头像失败！", e);
				mav.addObject("picMessage", "修改头像失败，请重试!");
			    mav.setViewName("redirect:/dashboard/Account/setting.jsp#tab_1_2");
				return mav;
			}*/
		}
		try {
			ResponseObject<Object> ro = this.userService.modifyUserPic(userId, this.amazonS3Url + saveFileName);
			if(ro.getCode()==ResponseCode.SUCCESS_CODE){
				HttpSession session = request.getSession();
				session.setAttribute(Constant.USER_PIC_URL_SESSION_KEY, this.amazonS3Url + saveFileName);
				mav.addObject("picMessage", "修改头像成功!");
			    mav.setViewName("redirect:/dashboard/Account/setting.jsp#tab_1_2");
				return mav;
				//return new ModelAndView("redirect:/dashboard/Account/setting.jsp?picMessage="+"修改头像成功!"+"#tab_1_2"); 
			}else{
				mav.addObject("picMessage", "修改头像失败，请重试!");
			    mav.setViewName("redirect:/dashboard/Account/setting.jsp#tab_1_2");
				return mav;
				//return new ModelAndView("redirect:/dashboard/Account/setting.jsp?picMessage="+"修改头像失败，请重试!"+"#tab_1_2"); 
			}
			//return ro;
		} catch (ServiceException e) {
			log.error("修改头像失败，请重试！", e);
			//return new ModelAndView("redirect:/dashboard/Account/setting.jsp?picMessage="+"修改头像失败，请重试!"+"#tab_1_2"); 
			mav.addObject("picMessage", "修改头像失败，请重试!");
		    mav.setViewName("redirect:/dashboard/Account/setting.jsp#tab_1_2");
			return mav;
			//return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "修改头像失败，请重试！");
		}
		
	}
	
	/*@RequestMapping(value = "/user/pic_upload", method = { RequestMethod.POST })
	@ResponseBody
	public ModelAndView uploadUserPic(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "picFile", required = true) MultipartFile mpf) {
		ModelAndView mav = new ModelAndView();
		if (mpf == null) {
			    mav.addObject("picMessage", "没有选择相片,请选择相片后再提交!");
			    mav.setViewName("redirect:/dashboard/Account/setting.jsp#tab_1_2");
				return mav;
			//return generateResponseObject( ResponseCode.USER_PIC_ERROR, );
		}
		//ModelAndView mav = new ModelAndView();
		String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
		//LinkedList<FileMeta> files = new LinkedList<FileMeta>();
		//FileMeta fileMeta = null;
		String saveFileName = null;
		
		// 解决火狐的反斜杠问题 kai 20151006
		String filetype = this.userPicFileType;// 要上传的文件类型
		String strtest = this.userPicFileSaveDir;
		String strseparator = "";
		if (strtest.indexOf("/") >= 0)// 包含有“/”字符，分隔符用此字符
		{
			strseparator = "/";
		} else {
			strseparator = File.separator;
		}
		
		//2.3 create new fileMeta
		//fileMeta = new FileMeta();
		//fileMeta.setFileName(mpf.getOriginalFilename());
		//fileMeta.setFileSize(mpf.getSize()/1024+" Kb");
		//fileMeta.setFileType(mpf.getContentType());
		if (mpf != null && mpf.getSize() > 0) {
			if (mpf.getSize() > this.userPicFileSize) {
				mav.addObject("picMessage", "允许上传文件最大为5MB!");
			    mav.setViewName("redirect:/dashboard/Account/setting.jsp#tab_1_2");
				return mav;
				//return new ModelAndView("redirect:/dashboard/Account/setting.jsp?picMessage="+"相片过大,请处理相片符合规定大小后再提交!"+"#tab_1_2");
				//return new ModelAndView("redirect:/dashboard/Account/setting.jsp?picMessage="+"相片过大,请处理相片符合规定大小后再提交!"+"#tab_1_2");  
				//return generateResponseObject(ResponseCode.USER_PIC_ERROR, "相片过大,请处理相片符合规定大小后再提交!");
			}
			String originalName = mpf.getOriginalFilename();
			if (!UserUtil.validateFileType(originalName, filetype)) {
				mav.addObject("picMessage", "相片格式不正确,请按提交规定的格式!");
			    mav.setViewName("redirect:/dashboard/Account/setting.jsp#tab_1_2");
				return mav;
				//return new ModelAndView("redirect:/dashboard/Account/setting.jsp?picMessage="+"相片格式不正确,请按提交规定的格式!"+"#tab_1_2");  
				//return new ModelAndView("redirect:/dashboard/Account/seting.jsp#tab_1_2?picMessage="+"");  
				//return generateResponseObject( ResponseCode.USER_PIC_ERROR, "相片格式不正确,请按提交规定的格式!");
			}
			int index = originalName.lastIndexOf('.');
			index = Math.max(index, 0);
			saveFileName = this.userPicFileSaveDir + strseparator + userId + "_"
				+ StringUtil.generateRandomString(5) + "_"
				+ StringUtil.generateRandomInteger(5)
				+ originalName.substring(index);
			//fileMeta.setSaveFileName(saveFileName);
				 
			try {
				File file1 = new File(request.getSession().getServletContext()
					.getRealPath("/")+ saveFileName);
				mpf.transferTo(file1);
				//files.add(fileMeta);
			} catch (Exception e) {
				log.error("更改头像失败！", e);
				mav.addObject("picMessage", "修改头像失败，请重试!");
			    mav.setViewName("redirect:/dashboard/Account/setting.jsp#tab_1_2");
				return mav;
				//return new ModelAndView("redirect:/dashboard/Account/setting.jsp?picMessage="+"修改头像失败，请重试!"+"#tab_1_2"); 
				//return generateResponseObject(ResponseCode.USER_PIC_ERROR,"更改头像失败，请重试!");
			}
		}
		try {
			ResponseObject<Object> ro = this.userService.modifyUserPic(userId, saveFileName);
			if(ro.getCode()==ResponseCode.SUCCESS_CODE){
				HttpSession session = request.getSession();
				session.setAttribute(Constant.USER_PIC_URL_SESSION_KEY, saveFileName);
				mav.addObject("picMessage", "修改头像成功!");
			    mav.setViewName("redirect:/dashboard/Account/setting.jsp#tab_1_2");
				return mav;
				//return new ModelAndView("redirect:/dashboard/Account/setting.jsp?picMessage="+"修改头像成功!"+"#tab_1_2"); 
			}else{
				mav.addObject("picMessage", "修改头像失败，请重试!");
			    mav.setViewName("redirect:/dashboard/Account/setting.jsp#tab_1_2");
				return mav;
				//return new ModelAndView("redirect:/dashboard/Account/setting.jsp?picMessage="+"修改头像失败，请重试!"+"#tab_1_2"); 
			}
			//return ro;
		} catch (ServiceException e) {
			log.error("修改头像失败，请重试！", e);
			//return new ModelAndView("redirect:/dashboard/Account/setting.jsp?picMessage="+"修改头像失败，请重试!"+"#tab_1_2"); 
			mav.addObject("picMessage", "修改头像失败，请重试!");
		    mav.setViewName("redirect:/dashboard/Account/setting.jsp#tab_1_2");
			return mav;
			//return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "修改头像失败，请重试！");
		}
		
	}*/
	
	@RequestMapping(value = "/user/modify_pic", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> modifyPic(HttpServletRequest request,
	        @RequestParam(value = ParameterConstants.USER_PIC) String picUrl) {
		ResponseObject<Object> responseObj = null;
		if (picUrl==null||picUrl.equals("")) {
			responseObj = generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数错误，请重新输入");
		} else {
			try {
				String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
				responseObj = this.userService.modifyUserPic(userId, picUrl);
				if(ResponseCode.SUCCESS_CODE.equals(responseObj.getCode())){
					HttpSession session = request.getSession();
					session.setAttribute(Constant.USER_PIC_URL_SESSION_KEY, picUrl);
				}
				
			} catch (ServiceException e) {
				log.error("修改头像失败!", e);
				responseObj = generateResponseObject(ResponseCode.SHOW_EXCEPTION, "修改头像失败!");
			}
		}
		return responseObj;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/user/fb_login", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> fbLogin(
	        HttpServletRequest request,
	        //@RequestParam(value = "fbEmail") String fbEmail,
	        @RequestParam(value = "fbName") String fbName,
	        @RequestParam(value = "fbToken") String fbToken,
	        @RequestParam(value = "fbId") String fbId,
	        @RequestParam(value = "picture", required = false, defaultValue = "") String picture,
	        @RequestParam(value = "isloging", required = false, defaultValue = "0") String isLoging) {
		ResponseObject<Object> responseObj = new ResponseObject<Object>(ResponseCode.SUCCESS_CODE);;

		if (StringUtil.isEmpty(fbId)) {
			responseObj = generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数错误!");
		} else if (StringUtil.isEmpty(fbToken)) {
			responseObj = generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数错误!");
		} else {
			try {
				User bUser = this.userService.getUserByFbId(fbId); 
				if (bUser==null) {// facebook 用户不存在
					/*创建唯一用户名 start*/
					Random random = new Random(System.currentTimeMillis());
					String strRand = String.valueOf(BASIC_CHARACTERS[random.nextInt(36)]);
					String newNickName = fbName;
					while(this.userService.getUserByAccount(newNickName)!=null){
						strRand = String.valueOf(BASIC_CHARACTERS[random.nextInt(36)]);
						newNickName +=strRand;
					}
					/*创建唯一用户名 end*/
					User user = new User();
					user.setNickName(newNickName);
					user.setPassword(fbToken);
					user.setEmail("");
					user.setEmailState(Constant.USER_EMAIL_STATE0);
					user.setRecommender("-1");
					user.setAbilityCertificateUrl("");
					user.setCity("");
					user.setCountry("");
					user.setMajor("");
					user.setProvince("");
					user.setOtherContact("");
					user.setSchool("");
					user.setTelphone("");
					user.setAreaCode("");
					user.setTelphoneState(Constant.USER_TELPHONE_STATE0);
						
					user.setFbId(fbId);
					user.setFbName(fbName);
					user.setFbToken(fbToken);
					user.setSignIp(getClientIp(request));
					
					if(picture!=null&&picture!=""){
						if(picture.startsWith("https")){
							picture.replace( "https","http" ) ;
						}
						try{
							/*保存微信头像*/
							// 解决火狐的反斜杠问题 kai 20151006
							//String filetype = this.userPicFileType;// 要上传的文件类型
							/*String strtest = this.userPicFileSaveDir;
							String strseparator = "";
							if (strtest.indexOf("/") >= 0)// 包含有“/”字符，分隔符用此字符
							{
								strseparator = "/";
							} else {
								strseparator = File.separator;
							}*/
								
							String saveFileName = "fb_"
									+ StringUtil.generateRandomString(5) + "_"
									+ StringUtil.generateRandomInteger(5);
							//UserUtil.downloadPic(picture, saveFileName, request.getSession().getServletContext()
							//		.getRealPath("/")+this.userPicFileSaveDir); 
								
							//log.info(request.getSession().getServletContext()
							//		.getRealPath("/")+this.userPicFileSaveDir+saveFileName);
						
							UserUtil.downloadPicToS3(picture, saveFileName, defaultFileSaveDir, this.userPicFileSaveDir);
							
							/*UserUtil.downloadPic(headimgurl, saveFileName, request.getSession().getServletContext()
									.getRealPath("/")+this.userPicFileSaveDir);
							
							log.info(request.getSession().getServletContext()
									.getRealPath("/")+this.userPicFileSaveDir+saveFileName);
							*/
							user.setPicUrl(this.amazonS3Url + this.userPicFileSaveDir +"/"+ saveFileName);
						
						}catch (Exception e) {
							//异常则设置默认头像
							log.info("获取facebook头像失败,设置随机头像");   //haokun modified 2017/02/21
							//user.setPicUrl(this.userPicDefaultUrl); //haokun delete 2017/02/21
							user.setPicUrl("/assets/pages/img/avatars/team"+String.valueOf(random.nextInt(50)+1)+".jpg");   //haokun added 2017/02/21
						}
					}else{
						//user.setPicUrl(this.userPicDefaultUrl);  //haokun delete 2017/02/21
						user.setPicUrl("/assets/pages/img/avatars/team"+String.valueOf(random.nextInt(50)+1)+".jpg");   //haokun added 2017/02/21
					}
					
					
						
					responseObj = this.userService.addUser(user);
					if ("1".equals(isLoging) && responseObj != null
							&& ResponseCode.SUCCESS_CODE.equals(responseObj.getCode())) {
						// 创建帐号成功后直接登录
						String userId = ((Map<String, String>)responseObj.getData()).get("id");
						HttpSession session = request.getSession();
						session.setAttribute(Constant.USER_ID_SESSION_KEY, userId);
						session.setAttribute(Constant.USER_NICK_NAME_SESSION_KEY, user.getNickName());
						session.setAttribute(Constant.USER_PIC_URL_SESSION_KEY, user.getPicUrl());
						session.setAttribute(Constant.USER_EMAIL_SESSION_KEY, user.getEmail());
						session.setAttribute(Constant.USER_TYPE_SESSION_KEY, user.getType());
						session.setAttribute(Constant.LOGIN_ACCOUNT_TYPE, "0");
					}
				} else {
					// facebook用户已存在
					if(Constant.USER_BLOCK_STATE1.equals(bUser.getBlockState())){
						responseObj.setCode(ResponseCode.USER_BLOCK_STATE1);
						responseObj.setMessage("您目前违反用户协议，不能登录，如有疑问请联系客服！");
					}else{
						//直接登录 
						HttpSession session = request.getSession();
						session.setAttribute(Constant.USER_ID_SESSION_KEY, bUser.getId());
						session.setAttribute(Constant.USER_NICK_NAME_SESSION_KEY, bUser.getNickName());
						session.setAttribute(Constant.USER_PIC_URL_SESSION_KEY, bUser.getPicUrl());
						session.setAttribute(Constant.USER_EMAIL_SESSION_KEY, bUser.getEmail());
						session.setAttribute(Constant.USER_TYPE_SESSION_KEY, bUser.getType());
						session.setAttribute(Constant.LOGIN_ACCOUNT_TYPE, "0");
						responseObj.setCode(ResponseCode.SUCCESS_CODE);
					}
					
				}
				
			} catch (Exception e) {
				log.error("facebook登录失败", e);
				return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "facebook登录失败,请重试!");
			}
		}

		return responseObj;
	}
	
	
	@RequestMapping(value = "/user/wx_login", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView wxLogin(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(); 
		ResponseObject<Object> responseObj = new ResponseObject<Object>(ResponseCode.SUCCESS_CODE);;
		String code = request.getParameter("code");
		//String state = request.getParameter("state");
		String accessToken = "";
		String openid = "";
		String nickname = "";
		String headimgurl = "";
		if (code!=null&&code!=""&&!"authdeny".equals(code)) {
			//HttpRequestUtil hru = new HttpRequestUtil();
			HttpsClient hc = new HttpsClient();
			String url = MessageFormat.format(weixinApiOauth2, new Object[] {weixinAppid,weixinAppSecret,code,"authorization_code"});
			log.info(url);
			String result="";
			try {
				result = hc.send(url,"");
			} catch (KeyManagementException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ClientProtocolException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try{
				JSONObject json = JSONObject.fromObject(result);
				log.info(result);
				accessToken = json.getString("access_token");
				openid = json.getString("openid");
				
			}catch (Exception e) {
				
			}
			if(accessToken!=""&&accessToken!=null&&openid!=null&&openid!=""){
				String userInfo = "";
				try {
					userInfo = hc.send("https://api.weixin.qq.com/sns/userinfo?access_token="+accessToken+"&openid="+openid,"");
					log.info("userInfo:"+userInfo);
				} catch (KeyManagementException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClientProtocolException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try{
					JSONObject json = JSONObject.fromObject(userInfo);
					nickname = json.getString("nickname");
					headimgurl = json.getString("headimgurl");
				}catch (Exception e) {
					
				}
			}else{
				//无法获取access_token,openid
				mav.setViewName("redirect:https://www.anyonehelps.com/login.jsp");
				return mav;
			}
		}else{
			//不是正确的code
			mav.setViewName("redirect:https://www.anyonehelps.com/login.jsp");
			return mav;
		}
		log.info("accessToken:"+accessToken);
		log.info("openid:"+openid);
		log.info("nickname:"+nickname);
		if (StringUtil.isEmpty(accessToken)) {
			mav.setViewName("redirect:https://www.anyonehelps.com/login.jsp");
			return mav;
		} else if (StringUtil.isEmpty(openid)) {
			mav.setViewName("redirect:https://www.anyonehelps.com/login.jsp");
			return mav;
		} else {
			if(nickname==""||nickname==null){
				nickname="weixinuser";
			}
			try {
				User bUser = this.userService.getUserByWxId(openid); 
				if (bUser==null) {	// 微信用户不存在
					
					/*创建唯一用户名 start*/
					Random random = new Random(System.currentTimeMillis());
					String strRand = String.valueOf(BASIC_CHARACTERS[random.nextInt(36)]);
					String newNickName = nickname;
					while(this.userService.getUserByAccount(newNickName)!=null){
						strRand = String.valueOf(BASIC_CHARACTERS[random.nextInt(36)]);
						newNickName +=strRand;
					}
					/*创建唯一用户名 end*/
					User user = new User();
					user.setNickName(newNickName);
					user.setPassword(accessToken);
					user.setEmail("");
					user.setEmailState(Constant.USER_EMAIL_STATE0);
					user.setRecommender("-1");
					user.setAbilityCertificateUrl("");
					user.setCity("");
					user.setCountry("");
					user.setMajor("");
					user.setProvince("");
					user.setOtherContact("");
					user.setSchool("");
					user.setTelphone("");
					user.setTelphoneState(Constant.USER_TELPHONE_STATE0);
					user.setSignIp(getClientIp(request));
					
					try{
						/*保存微信头像*/
						// 解决火狐的反斜杠问题 kai 20151006
						//String filetype = this.userPicFileType;// 要上传的文件类型
						//String strtest = this.userPicFileSaveDir;
						/*String strseparator = "";
						if (strtest.indexOf("/") >= 0)// 包含有“/”字符，分隔符用此字符
						{
							strseparator = "/";
						} else {
							strseparator = File.separator;
						}*/
						
						String saveFileName = "wx_"
								+ StringUtil.generateRandomString(5) + "_"
								+ StringUtil.generateRandomInteger(5);     /*haokun modified 2017/03/03  图片转化为jpg格式*/
						log.error("===============================");
						log.error(headimgurl);
						log.error(this.userPicFileSaveDir);
						log.error(saveFileName);
						log.error("==============================="); 
						UserUtil.downloadPicToS3(headimgurl, saveFileName, defaultFileSaveDir, this.userPicFileSaveDir);
						
						/*UserUtil.downloadPic(headimgurl, saveFileName, request.getSession().getServletContext()
								.getRealPath("/")+this.userPicFileSaveDir);
						
						log.info(request.getSession().getServletContext()
								.getRealPath("/")+this.userPicFileSaveDir+saveFileName);
						*/
						user.setPicUrl(this.amazonS3Url + this.userPicFileSaveDir +"/"+ saveFileName);   /*haokun modified 2017/03/03  增加了'/'斜杠 */
					}catch (Exception e) {
						//异常则设置默认头像
						user.setPicUrl(this.userPicDefaultUrl);
						e.printStackTrace();
					}
						
					user.setWxId(openid);
					user.setWxToken(accessToken);
						
					responseObj = this.userService.addUser(user);
					if (responseObj != null&& ResponseCode.SUCCESS_CODE.equals(responseObj.getCode())) {
						System.out.println("注册成功, 需要直接登录的");
						// 创建帐号成功后直接登录
						@SuppressWarnings("unchecked")
						String userId = ((Map<String, String>)responseObj.getData()).get("id");
						HttpSession session = request.getSession();
						session.setAttribute(Constant.USER_ID_SESSION_KEY, userId);
						session.setAttribute(Constant.USER_NICK_NAME_SESSION_KEY, user.getNickName());
						session.setAttribute(Constant.USER_PIC_URL_SESSION_KEY, user.getPicUrl());
						session.setAttribute(Constant.USER_EMAIL_SESSION_KEY, user.getEmail());
						session.setAttribute(Constant.USER_TYPE_SESSION_KEY, user.getType());
						session.setAttribute(Constant.LOGIN_ACCOUNT_TYPE, "0");
					}
				} else { // 微信用户已经存在, 直接登录
					if(Constant.USER_BLOCK_STATE1.equals(bUser.getBlockState())){
						mav.setViewName("redirect:https://www.anyonehelps.com/forbidden/userForbidden.jsp");  
						return mav;
					}else{
						HttpSession session = request.getSession();
						session.setAttribute(Constant.USER_ID_SESSION_KEY, bUser.getId());
						session.setAttribute(Constant.USER_NICK_NAME_SESSION_KEY, bUser.getNickName());
						session.setAttribute(Constant.USER_PIC_URL_SESSION_KEY, bUser.getPicUrl());
						session.setAttribute(Constant.USER_EMAIL_SESSION_KEY, bUser.getEmail());
						session.setAttribute(Constant.USER_TYPE_SESSION_KEY, bUser.getType());
						session.setAttribute(Constant.LOGIN_ACCOUNT_TYPE, "0");
						responseObj.setCode(ResponseCode.SUCCESS_CODE);
					}
					
				}
				
			} catch (Exception e) {
				mav.setViewName("redirect:https://www.anyonehelps.com/login.jsp");
				return mav;
			}
		}

		mav.setViewName("redirect:https://www.anyonehelps.com/index.jsp");  
		return mav;
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/user/email_register", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> emailReg(
	        HttpServletRequest request,
	        @RequestParam(value = ParameterConstants.USER_NICK_NAME) String name,
	        @RequestParam(value = ParameterConstants.USER_PASSWORD) String password,
	        @RequestParam(value = ParameterConstants.USER_EMAIL) String email,
	        @RequestParam(value = "recommend" , required = false, defaultValue = "") String recommend,  //直接填写推荐人
	        @RequestParam(value = ParameterConstants.USER_RECOMMENDER, required = false, defaultValue = "") String recommender,
	        @RequestParam(value = "isloging", required = false, defaultValue = "0") String isLoging,
	        @RequestParam(value = ParameterConstants.COMMON_VALIDATE_CODE ,required = false, defaultValue = "") String vcode) {
		ResponseObject<Object> responseObj = null;

		if (!UserUtil.validateNickName(name)) {
			responseObj = generateResponseObject(ResponseCode.USER_NICK_NAME_ERROR, "用户名输入不正确，请重新输入!");
		} else if (!UserUtil.validatePassword(password)) {
			responseObj = generateResponseObject(ResponseCode.USER_PASSWORD_ERROR, "密码输入不正确，请重新输入!");
		} else if (!UserUtil.validateEmail(email)) {
			responseObj = generateResponseObject(ResponseCode.USER_EMAIL_ERROR, "邮箱输入不正确，请重新输入！");
		} else if (!UserUtil.validateRecommender(recommender)) {
			responseObj = generateResponseObject(ResponseCode.USER_RECOMMENDER_ERROR, "推荐人账号不正确，请重新输入！");
		} else {
			try {
				 //同ip5分钟内不能超过10次
			    String ip = getClientIp(request);
			   
			    if(!checkCode(request, vcode, Constant.IPRECORD_TYPE2, ip)){
					responseObj = generateResponseObject(ResponseCode.USER_LOGIN_NEEL_CODE, "验证码不正确，请重新输入!");
					return responseObj;
				}
			    
				IPRecord ipRecord = new IPRecord();
				ipRecord.setIp(ip);
				ipRecord.setType(Constant.IPRECORD_TYPE2);
				ipRecord.setCreateDate(DateUtil.date2String(new Date()));
				this.ipRecordService.addIPRecord(ipRecord);
				if (!this.userService.checkExistsByName(name)) {
					// 该用户名对应的用户不存在
					if (!this.userService.checkExistsByEmail(email)) {
						// 该email对应的用户不存在
						User user = new User();
						user.setNickName(name);
						user.setPassword(password);
						user.setEmail(email);
						user.setEmailState(Constant.USER_EMAIL_STATE0);
						user.setSignIp(getClientIp(request));
						
						if(recommend!=null && !recommend.equals("")){
							User rUser= this.userService.getUserByAccount(recommend);
							if(rUser!=null){
								user.setRecommender(rUser.getId());
							}else{
								user.setRecommender(StringUtil.isEmpty(recommender) ? "-1" : recommender);
							}
						}else{
							user.setRecommender(StringUtil.isEmpty(recommender) ? "-1" : recommender);
						}
						
						user.setType(Constant.USER_TYPE_NORMAL);
						user.setAbilityCertificateUrl("");
						user.setCity("");
						user.setCountry("");
						user.setMajor("");
						Random random=new Random();//haokun modified 2017/02/21
						user.setPicUrl("/assets/pages/img/avatars/team"+String.valueOf(random.nextInt(50)+1)+".jpg");    //haokun modified 2017/02/21
						user.setProvince("");
						user.setOtherContact("");
						user.setSchool("");
						user.setTelphone("");
						user.setTelphoneState(Constant.USER_TELPHONE_STATE0);
						
						responseObj = this.userService.addUser(user);
						if ("1".equals(isLoging) && responseObj != null
						        && ResponseCode.SUCCESS_CODE.equals(responseObj.getCode())) {
							// 注册成功, 需要直接登录的
							String userId = ((Map<String, String>)responseObj.getData()).get("id");
							HttpSession session = request.getSession();
							session.setAttribute(Constant.USER_ID_SESSION_KEY, userId);
							session.setAttribute(Constant.USER_NICK_NAME_SESSION_KEY, user.getNickName());
							session.setAttribute(Constant.USER_PIC_URL_SESSION_KEY, user.getPicUrl());
							session.setAttribute(Constant.USER_EMAIL_SESSION_KEY, user.getEmail());
							session.setAttribute(Constant.USER_TYPE_SESSION_KEY, user.getType());
							session.setAttribute(Constant.LOGIN_ACCOUNT_TYPE, "0");
						}
					} else {
						responseObj = generateResponseObject(ResponseCode.USER_EMAIL_EXISTS, "该email已经注册过了，找回密码或者登录！");
					}
				} else {
					responseObj = generateResponseObject(ResponseCode.USER_NAME_EXISTS, "该用户名存在，请重新输入或者登录！");
				}
			} catch (Exception e) {
				log.error("用户注册失败", e);
				return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "用户注册失败!");
			}
		}

		return responseObj;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/user/phone_register", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> registerByPhone(
	        HttpServletRequest request,
	        @RequestParam(value = ParameterConstants.USER_NICK_NAME) String name,
	        @RequestParam(value = ParameterConstants.USER_AREA_CODE) String areaCode,
	        @RequestParam(value = ParameterConstants.USER_PHONE) String telphone,
	        @RequestParam(value = ParameterConstants.USER_PASSWORD) String password,
	        @RequestParam(value = ParameterConstants.COMMON_VALIDATE_CODE) String vcode,
	        @RequestParam(value = "recommend" , required = false, defaultValue = "") String recommend,   //直接填写推荐人
	        @RequestParam(value = ParameterConstants.USER_RECOMMENDER, required = false, defaultValue = "") String recommender,
	        @RequestParam(value = "isloging", required = false, defaultValue = "1") String isLoging) {
		ResponseObject<Object> responseObj = null;
		
		if (!UserUtil.validateNickName(name)) {
			responseObj = generateResponseObject(ResponseCode.USER_NICK_NAME_ERROR, "用户名输入不正确，请重新输入!");
		} else if (!checkVerifyPhoneCode(request,areaCode, telphone,vcode)) {
			responseObj = generateResponseObject(ResponseCode.VALIDATE_CODE_ERROR, "验证码输入错误，请重新输入");
		} else if (!UserUtil.validateAreaCode(areaCode)) {
			responseObj = generateResponseObject(ResponseCode.USER_PHONE_ERROR, "手机所属地区码输入错误，请重新输入!");
		} else if (!UserUtil.validatePhone(telphone)) {
			responseObj = generateResponseObject(ResponseCode.USER_PHONE_ERROR, "手机号码输入不正确，请重新输入!");
		} else if (!UserUtil.validatePassword(password)) {
			responseObj = generateResponseObject(ResponseCode.USER_PASSWORD_ERROR, "密码输入不正确，请重新输入!");
		} else if (!UserUtil.validateRecommender(recommender)) {
			responseObj = generateResponseObject(ResponseCode.USER_RECOMMENDER_ERROR, "推荐人账号不正确，请重新输入！");
		} else {
			try {
				if (!this.userService.checkExistsByName(name)) {
					if (!this.userService.checkExistsByPhone(areaCode,telphone)) { 
						User user = new User();
						user.setNickName(name);
						user.setPassword(password);
						user.setEmail("");
						user.setEmailState(Constant.USER_EMAIL_STATE0);
						if(recommend!=null && !recommend.equals("")){
							User rUser= this.userService.getUserByAccount(recommend);
							if(rUser!=null){
								user.setRecommender(rUser.getId());
							}else{
								user.setRecommender(StringUtil.isEmpty(recommender) ? "-1" : recommender);
							}
						}else{
							user.setRecommender(StringUtil.isEmpty(recommender) ? "-1" : recommender);
						}
						user.setType(Constant.USER_TYPE_NORMAL);
						user.setAbilityCertificateUrl("");
						user.setCity("");
						user.setCountry("");
						user.setMajor("");
						Random random=new Random();            //haokun modified 2017/02/21
						user.setPicUrl("/assets/pages/img/avatars/team"+String.valueOf(random.nextInt(50)+1)+".jpg");      //haokun modified 2017/02/21
						user.setProvince("");
						user.setOtherContact("");
						user.setSchool("");
						user.setAreaCode(areaCode);
						user.setTelphone(telphone);
						user.setTelphoneState(Constant.USER_TELPHONE_STATE1);
						user.setSignIp(getClientIp(request));
						responseObj = this.userService.addUser(user);
						if ("1".equals(isLoging) && responseObj != null
						        && ResponseCode.SUCCESS_CODE.equals(responseObj.getCode())) {
							// 注册成功, 需要直接登录的
							String userId = ((Map<String, String>) responseObj.getData()).get("id");
							HttpSession session = request.getSession();
							session.setAttribute(Constant.USER_ID_SESSION_KEY, userId);
							session.setAttribute(Constant.USER_NICK_NAME_SESSION_KEY, user.getNickName());
							session.setAttribute(Constant.USER_PIC_URL_SESSION_KEY, user.getPicUrl());
							session.setAttribute(Constant.USER_EMAIL_SESSION_KEY, user.getEmail());
							session.setAttribute(Constant.USER_TYPE_SESSION_KEY, user.getType());
							session.setAttribute(Constant.LOGIN_ACCOUNT_TYPE, "0");
							
							session.removeAttribute(Constant.PHONE_KEY);
							session.removeAttribute(Constant.PHONE_AREA_CODE);
							session.removeAttribute(Constant.PHONE_SEND_CODE);
							session.removeAttribute(Constant.PHONE_SEND_CODE_TIME);
						}
					} else {
						responseObj = generateResponseObject(ResponseCode.USER_PHONE_EXISTS, "该手机号码已经注册过了，找回密码或者登录！");
					}
				} else {
					responseObj = generateResponseObject(ResponseCode.USER_NAME_EXISTS, "该用户名存在，请重新输入或者登录！");
				}
			} catch (Exception e) {
				log.error("用户注册失败", e);
				return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "用户注册失败!");
			}
		}

		return responseObj;
	}
	
	
	@RequestMapping(value = "/user/phone_login", method = { RequestMethod.POST })  
	@ResponseBody
	public ResponseObject<User> PhoneLogin(HttpServletRequest request,
	        @RequestParam(value = ParameterConstants.USER_AREA_CODE) String areaCode,
	        @RequestParam(value = ParameterConstants.USER_PHONE) String telphone,
	        @RequestParam(value = ParameterConstants.USER_PASSWORD) String password,
	        @RequestParam(value = ParameterConstants.COMMON_VALIDATE_CODE ,required = false, defaultValue = "") String vcode
	        ) {
		ResponseObject<User> responseObj = null;
		if (!UserUtil.validateAreaCode(areaCode)) {
			return generateResponseObject(ResponseCode.USER_PHONE_ERROR, "手机所属地区码输入错误，请重新输入!");
		}
		if (!UserUtil.validatePhone(telphone)) {
			return generateResponseObject(ResponseCode.USER_PHONE_ERROR, "手机号码输入不正确，请重新输入!");
		} 
		if(!UserUtil.validatePassword(password)){
			return generateResponseObject(ResponseCode.USER_PASSWORD_ERROR, "密码输入不正确，请重新输入!");
		}
		try {
			String ip = getClientIp(request);
			if(!checkCode(request, vcode, Constant.IPRECORD_TYPE1, ip)){
				responseObj = generateResponseObject(ResponseCode.USER_LOGIN_NEEL_CODE, "验证码不正确，请重新输入!");
				return responseObj;
			}
			responseObj = this.userService.checkLoginByPhone(areaCode, telphone , password,ip);
			String code = responseObj.getCode();
			if (ResponseCode.SUCCESS_CODE.equals(code)) {
				HttpSession session = request.getSession();
				session.setAttribute(Constant.USER_ID_SESSION_KEY, responseObj.getData().getId());
				session.setAttribute(Constant.USER_PIC_URL_SESSION_KEY, responseObj.getData().getPicUrl());
				session.setAttribute(Constant.USER_NICK_NAME_SESSION_KEY, responseObj.getData().getNickName());
				session.setAttribute(Constant.USER_TYPE_SESSION_KEY, responseObj.getData().getType());
				session.setAttribute(Constant.USER_EMAIL_SESSION_KEY, responseObj.getData().getEmail());
				session.setAttribute(Constant.LOGIN_ACCOUNT_TYPE, "0");
			} else {
				log.error("用户登录失败,code:" + code);
			}
		} catch (Exception e) {
			log.error("调用后台代码出错", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取个人信息失败!");
		}
		return responseObj;
	}

	/**
	 * 邮箱或者用户名登录
	 * @param request
	 * @param account
	 * @param password
	 * @param vcode 验证码
	 * @return
	 */
	@RequestMapping(value = "/user/login", method = { RequestMethod.POST })  
	@ResponseBody
	public ResponseObject<User> login(HttpServletRequest request,
	        @RequestParam(value = ParameterConstants.COMMON_ACCOUNT) String account,
	        @RequestParam(value = ParameterConstants.USER_PASSWORD) String password,
	        @RequestParam(value = ParameterConstants.COMMON_VALIDATE_CODE ,required = false, defaultValue = "") String vcode
	        ) {
		ResponseObject<User> responseObj = null;
		if(account==null||account.equals("")){
			responseObj = generateResponseObject(ResponseCode.USER_NICK_NAME_ERROR, "用户名/邮箱/手机输入不正确，请重新输入!");
			return responseObj;
		}
		if(!UserUtil.validatePassword(password)){
			responseObj = generateResponseObject(ResponseCode.USER_PASSWORD_ERROR, "密码输入不正确，请重新输入!");
			return responseObj;
		}
		try {
			String ip = getClientIp(request);
			if(!checkCode(request, vcode, Constant.IPRECORD_TYPE1, ip)){
				responseObj = generateResponseObject(ResponseCode.USER_LOGIN_NEEL_CODE, "验证码不正确，请重新输入!");
				return responseObj;
			}
			ResponseObject<User> ruser = this.userService.checkLogin(account, password, ip);
			String code = ruser.getCode();
			if (ResponseCode.SUCCESS_CODE.equals(code)) {
				HttpSession session = request.getSession();
				session.setAttribute(Constant.USER_ID_SESSION_KEY, ruser.getData().getId());
				session.setAttribute(Constant.USER_PIC_URL_SESSION_KEY, ruser.getData().getPicUrl());
				session.setAttribute(Constant.USER_NICK_NAME_SESSION_KEY, ruser.getData().getNickName());
				session.setAttribute(Constant.USER_TYPE_SESSION_KEY, ruser.getData().getType());
				session.setAttribute(Constant.USER_EMAIL_SESSION_KEY, ruser.getData().getEmail());
				session.setAttribute(Constant.LOGIN_ACCOUNT_TYPE, "0");
				
			} else {
				log.error("用户登录失败,code:" + code);
			}
			responseObj = ruser;
		} catch (Exception e) {
			log.error("调用后台代码出错", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取个人信息失败!");
		}
		return responseObj;
	}

	@RequestMapping(value = "/user/logout", method = {  RequestMethod.GET, RequestMethod.POST }) //RequestMethod.GET,
	@ResponseBody
	public ResponseObject<Object> logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute(Constant.USER_ID_SESSION_KEY);
		session.removeAttribute(Constant.USER_PIC_URL_SESSION_KEY);
		session.removeAttribute(Constant.USER_NICK_NAME_SESSION_KEY);
		session.removeAttribute(Constant.USER_EMAIL_SESSION_KEY);
		session.removeAttribute(Constant.USER_TYPE_SESSION_KEY);
		
		session.invalidate();
		
		return generateResponseObject(ResponseCode.SUCCESS_CODE);
	}
	
	@RequestMapping(value = "/user/get_self", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseObject<User> getSelf(HttpServletRequest request) {
		ResponseObject<User> responseObj = null;
		try {
			String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			responseObj = this.userService.getUserById(userId);
		} catch (Exception e) {
			log.error("获取个人信息失败", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取个人信息失败!");
		}
		return responseObj;
	}
	/**
	 * 查找邀请我的用户的信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/user/get_recommended", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseObject<User> getRecommended(HttpServletRequest request) {
		ResponseObject<User> responseObj = null;
		try {
			String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			responseObj = this.userService.getRecommendedByUserId(userId);
		} catch (Exception e) {
			log.error("获取邀请我的用户信息失败", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取邀请我的用户信息失败!");
		}
		return responseObj;
	}
	
	@RequestMapping(value = "/user/open_info", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseObject<Map<String,Object>> getOpenInfo(HttpServletRequest request,
			@RequestParam(value = ParameterConstants.USER_ID) String userId
			) {
		ResponseObject<Map<String,Object>> responseObj = null;
		try {
			String selfId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			responseObj = this.userService.getUserOpenInfoById(userId,selfId);
		} catch (Exception e) {
			log.error("获取用户信息失败", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取用户信息失败!");
		}
		return responseObj;
	}

	@RequestMapping(value = "/user/open_info_by_account", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseObject<User> getOpenInfoByAccount(HttpServletRequest request,
			@RequestParam(value = ParameterConstants.COMMON_ACCOUNT) String account
			) {
		ResponseObject<User> responseObj = null;
		try {
			String selfId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			responseObj = this.userService.getUserOpenInfoByAccount(account,selfId);
		} catch (Exception e) {
			log.error("获取用户信息失败", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取用户信息失败!");
		}
		return responseObj;
	}


	@RequestMapping(value = "/user/del", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> delUser(@RequestParam(value = ParameterConstants.USER_ID) String[] ids) {
		ResponseObject<Object> responseObj = null;
		if (ids == null || ids.length < 1) {
			responseObj = generateResponseObject(ResponseCode.USER_ID_ERROR, "必须给定要删除的用户id");
		} else {
			try {
				responseObj = this.userService.deleteUserByIds(ids);
			} catch (Exception e) {
				log.error("根据用户id获取用户数据失败");
				responseObj = generateResponseObject(ResponseCode.SHOW_EXCEPTION, "删除会员失败!");
			}
		}
		return responseObj;
	}
	
	/**
	 * 根据手机找回密码
	 * @param request
	 * @param phone  
	 * @param password
	 * @param vcode  发送到手机的验证码
	 * @return
	 */
	@RequestMapping(value = "/user/reset_pwd", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseObject<Object> resetPwd(HttpServletRequest request,
			@RequestParam(value = ParameterConstants.USER_AREA_CODE) String areaCode,
	        @RequestParam(value = ParameterConstants.USER_PHONE) String phone,
	        @RequestParam(value = ParameterConstants.USER_PASSWORD) String password,
	        @RequestParam(value = ParameterConstants.COMMON_VALIDATE_CODE) String vcode) {
		ResponseObject<Object> responseObj = null;
		if (!checkVerifyPhoneCode(request, areaCode, phone,vcode)) {
			responseObj = generateResponseObject(ResponseCode.VALIDATE_CODE_ERROR, "验证码输入错误，请重新输入");
		} else if (!UserUtil.validateAreaCode(areaCode)) {
			responseObj = generateResponseObject(ResponseCode.USER_PHONE_ERROR, "手机所属地区码输入错误，请重新输入!");
		} else if (!UserUtil.validatePhone(phone)) {
			responseObj = generateResponseObject(ResponseCode.USER_EMAIL_ERROR, "手机号码输入不正确，请重新输入！");
		} else if (!UserUtil.validatePassword(password)) {
			responseObj = generateResponseObject(ResponseCode.USER_PASSWORD_ERROR, "密码输入不正确，请重新输入！");
		}  else {
			try {
				ResponseObject<Object> nResult = this.userService.modifyPasswordByPhone(areaCode, phone, password);
				if (nResult != null && ResponseCode.SUCCESS_CODE.equals(nResult.getCode())) {
					responseObj = generateResponseObject(ResponseCode.SUCCESS_CODE);
					HttpSession session = request.getSession();
					session.removeAttribute(Constant.PHONE_KEY);
					session.removeAttribute(Constant.PHONE_AREA_CODE);
					session.removeAttribute(Constant.PHONE_SEND_CODE);
					session.removeAttribute(Constant.PHONE_SEND_CODE_TIME);
					User user = (User) nResult.getData();
					session.setAttribute(Constant.USER_ID_SESSION_KEY, user.getId());
					session.setAttribute(Constant.USER_NICK_NAME_SESSION_KEY, user.getNickName());
					session.setAttribute(Constant.USER_PIC_URL_SESSION_KEY, user.getPicUrl());
					session.setAttribute(Constant.USER_EMAIL_SESSION_KEY, user.getEmail());
					session.setAttribute(Constant.USER_TYPE_SESSION_KEY, user.getType());
					session.setAttribute(Constant.LOGIN_ACCOUNT_TYPE, "0");
				} else {
					responseObj = generateResponseObject(ResponseCode.USER_MODIFY_FAILURE, "修改密码失败");
				}
			} catch (ServiceException e) {
				log.error("修改/重置密码失败!", e);
				responseObj = generateResponseObject(ResponseCode.SHOW_EXCEPTION, "修改/重置密码失败!");
			}

		}
		return responseObj;
	}
	
	/**
	 * 根据邮箱找回密码
	 * @param request
	 * @param email
	 * @param vcode
	 * @return
	 */
	@RequestMapping(value = "/user/reset_email_pwd", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseObject<Object> resetPwdOfEmail(HttpServletRequest request,
	        @RequestParam(value = ParameterConstants.USER_EMAIL) String email,
	        @RequestParam(value = ParameterConstants.COMMON_VALIDATE_CODE) String vcode) {
		ResponseObject<Object> responseObj = null;

		if (!checkVerifyCode(request, vcode)) {
			responseObj = generateResponseObject(ResponseCode.VALIDATE_CODE_ERROR, "验证码输入错误，请重新输入");
		} else if (!UserUtil.validateEmail(email)) {
			responseObj = generateResponseObject(ResponseCode.USER_EMAIL_ERROR, "邮箱输入不正确，请重新输入！");
		} else {
			// 验证通过, 检测用户是否存在
			boolean exists = false;
			try {
				exists = this.userService.checkExistsByEmail(email);
			} catch (ServiceException e) {
				log.error("根据email获取账户失败,email=" + email, e);
				responseObj = generateResponseObject(ResponseCode.SHOW_EXCEPTION, "根据email检测会员是否存在失败!");
			}

			if (exists) {
				try {
					String token = this.commonService.getLastToken(email,Constant.TOKEN_TYPE_PWD0);
					if (StringUtil.isEmpty(token)) {
						token = this.generateResetPwdToken(email);
					}

					ResponseObject<String> result = this.commonService.insertToken(null,email, token,Constant.TOKEN_TYPE_PWD0);
					if (request != null && ResponseCode.SUCCESS_CODE.equals(result.getCode())) {
						try {
							String baseUrl = request.getServerName(); /*+ ":" + request.getServerPort()
							        + request.getContextPath() + "/"*/
							//MailSendUtil.sendResetPwdMsg(baseUrl, email, result.getData());
							Properties prop = PropertiesReader.read(Constant.EMAIL_PROPERTIES_FILE);
							String subject = prop.getProperty("anyonehelps.reset.pwd.subject");
							String url = baseUrl + prop.getProperty("anyonehelps.reset.pwd.page.name", "/resetPwdByEmail.jsp") + "?email="
							        + email + "&token=" + token;
							String content = prop.getProperty("anyonehelps.reset.pwd.content");
							content = MessageFormat.format(content, new Object[] { Constant.RESET_PWD_TOKEN_TIME_OF_HOUR, url, url });
							
							String date = DateUtil.date2String(new Date());
							
							EmailSend es = new EmailSend();
							es.setContent(content);
							es.setCreateDate(date);
							es.setEmail(email);
							es.setFailCount(Constant.EMAILSEND_FAIL_COUNT0);
							es.setModifyDate(date);
							es.setState(Constant.EMAILSEND_STATE0);
							es.setSubject(subject);
							es.setUserId("-1");
							ResponseObject<Object> emailSendObj = emailSendService.addEmailSend(es);
							if(ResponseCode.SUCCESS_CODE.equals(emailSendObj.getCode())){
								responseObj = generateResponseObject(ResponseCode.SUCCESS_CODE, "重置密码邮件已发送");
							}else{
								responseObj = generateResponseObject(ResponseCode.TOKEN_INSERT_ERROR, "邮件发送失败");
							}
						} catch (Throwable e) {
							log.error("发送邮件失败", e);
							responseObj = generateResponseObject(ResponseCode.SHOW_EXCEPTION, "发送邮件失败!");
						}
					} else {
						responseObj = generateResponseObject(ResponseCode.TOKEN_INSERT_ERROR, "发送邮件失败");
					}
				} catch (Exception e) {
					log.error("保存token失败", e);
					responseObj = generateResponseObject(ResponseCode.SHOW_EXCEPTION, "发送邮件失败!");
				}
			} else {
				responseObj = generateResponseObject(ResponseCode.USER_EMAIL_NOT_EXISTS, "该email没有注册!");
			}
		}
		return responseObj;
	}
	
	/*@RequestMapping(value = "/user/reset_pwd2", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> resetPwd2(
	        HttpServletRequest request,
	        @RequestParam(value = ParameterConstants.USER_EMAIL, required = false, defaultValue = "") String email,
	        @RequestParam(value = ParameterConstants.COMMON_TOKEN, required = false, defaultValue = "") String token,
	        @RequestParam(value = ParameterConstants.USER_PASSWORD) String password,
	        @RequestParam(value = ParameterConstants.COMMON_OLD_PASSWORD, required = false, defaultValue = "") String oldPassword,
	        @RequestParam(value = ParameterConstants.COMMON_VALIDATE_CODE) String vcode) {
		ResponseObject<Object> responseObj = null;

		if (StringUtil.isEmpty(email)) {
			email = StringUtil.obj2String(request.getSession().getAttribute(Constant.USER_EMAIL_SESSION_KEY));
		}

		if (StringUtil.isEmpty(email)) {
			return new ResponseObject<Object>(ResponseCode.NEED_LOGIN, "请登录后操作，email不能为空");
		}

		if (!UserUtil.validateEmail(email)) {
			responseObj = generateResponseObject(ResponseCode.USER_EMAIL_ERROR, "邮箱输入不正确，请重新输入！");
		} else if (!UserUtil.validatePassword(password)) {
			responseObj = generateResponseObject(ResponseCode.USER_PASSWORD_ERROR, "密码输入不正确，请重新输入！");
		} else {
			try {
				if (!StringUtil.isEmpty(token)) {
					ResponseObject<String> result = this.commonService.checkToken(email, token);
					if (result != null && ResponseCode.SUCCESS_CODE.equals(result.getCode())) {
						// 可以修改用户密码
						ResponseObject<Object> tresult = this.userService.modifyPasswordByEmail(null, email, password, null);
						if (tresult != null && ResponseCode.SUCCESS_CODE.equals(tresult.getCode())) {
							responseObj = generateResponseObject(ResponseCode.SUCCESS_CODE);
						} else {
							responseObj = generateResponseObject(ResponseCode.USER_MODIFY_FAILURE, "重置密码失败");
						}
					} else if (!StringUtil.isEmpty(oldPassword) && UserUtil.validatePassword(oldPassword)) {
						// 可以修改用户密码
						ResponseObject<Object> tresult = this.userService.modifyPassword(null, email, password,
						        oldPassword);
						if (tresult != null && ResponseCode.SUCCESS_CODE.equals(tresult.getCode())) {
							responseObj = generateResponseObject(ResponseCode.SUCCESS_CODE);
						} else {
							responseObj = generateResponseObject(ResponseCode.USER_MODIFY_FAILURE, "修改密码失败");
						}
					} else {
						responseObj = generateResponseObject(ResponseCode.TOKEN_ERROR, "已过期或者是参数无效，请重新操作！");
					}
				} else if (UserUtil.validatePassword(oldPassword)) {
					// 可以修改用户密码
					ResponseObject<Object> tresult = this.userService
					        .modifyPassword(null, email, password, oldPassword);
					if (tresult != null && ResponseCode.SUCCESS_CODE.equals(tresult.getCode())) {
						responseObj = generateResponseObject(ResponseCode.SUCCESS_CODE);
					} else {
						responseObj = generateResponseObject(ResponseCode.USER_MODIFY_FAILURE, "原密码错误");
					}
				} else {
					responseObj = generateResponseObject(ResponseCode.TOKEN_ERROR, "已过期或者是参数无效，请重新操作！");
				}

			} catch (Exception e) {
				log.error("检测token出现异常", e);
				responseObj = generateResponseObject(ResponseCode.SHOW_EXCEPTION, "修改/重置密码失败!");
			}
		}
		return responseObj;
	}*/
	
	@RequestMapping(value = "/user/reset_pwd2", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> resetPwd2(
	        HttpServletRequest request,
	        @RequestParam(value = ParameterConstants.USER_EMAIL, required = false, defaultValue = "") String email,
	        @RequestParam(value = ParameterConstants.COMMON_TOKEN, required = false, defaultValue = "") String token,
	        @RequestParam(value = ParameterConstants.USER_PASSWORD) String password,
	        @RequestParam(value = ParameterConstants.COMMON_VALIDATE_CODE) String vcode) {
		ResponseObject<Object> responseObj = null;
		
		if (!UserUtil.validateEmail(email)) {
			responseObj = generateResponseObject(ResponseCode.USER_EMAIL_ERROR, "邮箱输入不正确，请重新输入！");
		} else if (!UserUtil.validatePassword(password)) {
			responseObj = generateResponseObject(ResponseCode.USER_PASSWORD_ERROR, "密码输入不正确，请重新输入！");
		} else {
			try {
				if (!StringUtil.isEmpty(token)) {
					ResponseObject<String> result = this.commonService.checkToken(email, token,Constant.TOKEN_TYPE_PWD0);
					if (result != null && ResponseCode.SUCCESS_CODE.equals(result.getCode())) {
						// 可以修改用户密码
						ResponseObject<Object> tresult = this.userService.modifyPasswordByEmail(null, email, password, null);
						if (tresult != null && ResponseCode.SUCCESS_CODE.equals(tresult.getCode())) {
							responseObj = generateResponseObject(ResponseCode.SUCCESS_CODE);
						} else {
							responseObj = generateResponseObject(ResponseCode.USER_MODIFY_FAILURE, "重置密码失败");
						}
					} else {
						responseObj = generateResponseObject(ResponseCode.TOKEN_ERROR, "已过期或者是参数无效，请重新操作！");
					}
				} else {
					responseObj = generateResponseObject(ResponseCode.TOKEN_ERROR, "已过期或者是参数无效，请重新操作！");
				}

			} catch (Exception e) {
				log.error("检测token出现异常", e);
				responseObj = generateResponseObject(ResponseCode.SHOW_EXCEPTION, "修改/重置密码失败!");
			}
		}
		return responseObj;
	}
	
	
	@RequestMapping(value = "/user/reset_pwd4", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> resetPwd4(
	        HttpServletRequest request,
	        @RequestParam(value = ParameterConstants.USER_PASSWORD) String password) {
		ResponseObject<Object> responseObj = null;
		
		if (!UserUtil.validatePassword(password)) {
			responseObj = generateResponseObject(ResponseCode.USER_PASSWORD_ERROR, "密码输入不正确，请重新输入！");
		} 
		HttpSession session = request.getSession();
		Object objSQ = session.getAttribute(Constant.SECURITY_QUESTION);
		Object objPhone = session.getAttribute(Constant.SECURITY_QUESTION_PHONE);
		Object objAreaCode = session.getAttribute(Constant.SECURITY_QUESTION_AREA_CODE);
		Object objEmail = session.getAttribute(Constant.SECURITY_QUESTION_EMAIL);
		if(objSQ!=null){
			try {
				String sq = objSQ.toString();
				if("1".equals(sq)){
					if(objEmail!=null){
						String email = objEmail.toString();
						ResponseObject<Object> nResult = this.userService.modifyPasswordByEmail(null, email, password, null);
						if (nResult != null && ResponseCode.SUCCESS_CODE.equals(nResult.getCode())) {
							responseObj = generateResponseObject(ResponseCode.SUCCESS_CODE);
							session.removeAttribute(Constant.SECURITY_QUESTION);
							session.removeAttribute(Constant.SECURITY_QUESTION_PHONE);
							session.removeAttribute(Constant.SECURITY_QUESTION_AREA_CODE);
							session.removeAttribute(Constant.SECURITY_QUESTION_EMAIL);
						} else {
							responseObj = generateResponseObject(ResponseCode.USER_MODIFY_FAILURE, "修改密码失败,请重试");
						}
					}else if(objAreaCode!=null&&objPhone!=null){
						String areaCode = objAreaCode.toString();
						String phone = objPhone.toString();
						ResponseObject<Object> nResult = this.userService.modifyPasswordByPhone(areaCode, phone, password);
						if (nResult != null && ResponseCode.SUCCESS_CODE.equals(nResult.getCode())) {
							responseObj = generateResponseObject(ResponseCode.SUCCESS_CODE);
							session.removeAttribute(Constant.SECURITY_QUESTION);
							session.removeAttribute(Constant.SECURITY_QUESTION_PHONE);
							session.removeAttribute(Constant.SECURITY_QUESTION_AREA_CODE);
							session.removeAttribute(Constant.SECURITY_QUESTION_EMAIL);
						} else {
							responseObj = generateResponseObject(ResponseCode.USER_MODIFY_FAILURE, "修改密码失败,请重试");
						}
					}else{
						responseObj = generateResponseObject(ResponseCode.PARAMETER_ERROR, "请先进行密保验证再修改密码！");
					}
				}else{
					responseObj = generateResponseObject(ResponseCode.PARAMETER_ERROR, "请先进行密保验证再修改密码！");
				}
				
				
			} catch (Exception e) {
				// no action
			}
		}
		
		return responseObj;
	}

	
	/*@RequestMapping(value = "/user/reset_pwd3", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> resetPwd3(
	        HttpServletRequest request,
	        @RequestParam(value = ParameterConstants.USER_EMAIL, required = false, defaultValue = "") String email,
	        @RequestParam(value = ParameterConstants.USER_PHONE, required = false, defaultValue = "") String phone,
	        @RequestParam(value = ParameterConstants.USER_AREA_CODE, required = false, defaultValue = "") String areaCode,
	        @RequestParam(value = "index1") String index1,
	        @RequestParam(value = "index2") String index2,
	        @RequestParam(value = "index3") String index3,
	        @RequestParam(value = "answer1") String answer1,
	        @RequestParam(value = "answer2") String answer2,
	        @RequestParam(value = "answer3") String answer3) {
		
		ResponseObject<Object> responseObj = null;
		
		if (StringUtil.isEmpty(index1)||StringUtil.isEmpty(index2)||StringUtil.isEmpty(index3)) {
			responseObj = generateResponseObject(ResponseCode.PARAMETER_ERROR, "请认真填写三个问题！");
		} else if (StringUtil.isEmpty(answer1)||StringUtil.isEmpty(answer2)||StringUtil.isEmpty(answer3)) {
			responseObj = generateResponseObject(ResponseCode.PARAMETER_ERROR, "问题回答不能为空！");
		} else {
			try {
				responseObj = this.userService.checkSecurityQuestion(email,areaCode,phone,index1,index2,index3,answer1,answer2,answer3);
				if(ResponseCode.SUCCESS_CODE.equals(responseObj.getCode())){
					
					HttpSession session = request.getSession();
					if (StringUtil.isEmpty(email)) {
						request.getSession().setAttribute(Constant.SECURITY_QUESTION_AREA_CODE, areaCode);  
						request.getSession().setAttribute(Constant.SECURITY_QUESTION_PHONE, phone);
					}else{
						request.getSession().setAttribute(Constant.SECURITY_QUESTION_EMAIL, email);  
					}
					request.getSession().setAttribute(Constant.SECURITY_QUESTION, "1");  
					request.getSession().setAttribute(Constant.SECURITY_QUESTION_TIME, new Date().getTime());
				}
				
			} catch (Exception e) {
				responseObj = generateResponseObject(ResponseCode.SHOW_EXCEPTION, "重置密码失败,请重试!");
			}
		}
		return responseObj;
	}*/
	
	@RequestMapping(value = "/user/reset_pwd3", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> resetPwd3(
	        HttpServletRequest request,
	        @RequestParam(value = ParameterConstants.USER_PASSWORD) String password) {
		
		ResponseObject<Object> responseObj = null;
		if (!UserUtil.validatePassword(password)) {
			responseObj = generateResponseObject(ResponseCode.USER_PASSWORD_ERROR, "密码输入不正确，请重新输入！");
			return responseObj;
		} 

		try {
			String userId = (String)request.getSession().getAttribute(Constant.SECURITY_QUESTION_RESETPW_USERID);
				
			responseObj = this.userService.modifyPwd(userId,password);
			if(ResponseCode.SUCCESS_CODE.equals(responseObj.getCode())){
				request.getSession().removeAttribute(Constant.SECURITY_QUESTION_RESETPW_USERID);
			}
				
		} catch (Exception e) {
			responseObj = generateResponseObject(ResponseCode.SHOW_EXCEPTION, "重置密码失败,请重试!");
		}
		return responseObj;
	}
	
	/**
	 * 验证邮箱
	 * @param request
	 * @param email
	 * @param vcode
	 * @return
	 */
	@RequestMapping(value = "/user/verify_email2", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> emailVerify2(HttpServletRequest request,
	        @RequestParam(value = ParameterConstants.USER_EMAIL) String email,
	        @RequestParam(value = ParameterConstants.COMMON_VALIDATE_CODE) String vcode) {
		ResponseObject<Object> responseObj = null;

		if (!checkVerifyCode(request, vcode)) {
			responseObj = generateResponseObject(ResponseCode.VALIDATE_CODE_ERROR, "验证码输入错误，请重新输入");
		} else if (!UserUtil.validateEmail(email)) {
			responseObj = generateResponseObject(ResponseCode.USER_EMAIL_ERROR, "邮箱输入不正确，请重新输入！");
		} else {
			// 验证通过, 检测能否使用这个邮箱验证
			boolean bVerify = false;
			String userId = StringUtil.obj2String(request.getSession()
					.getAttribute(Constant.USER_ID_SESSION_KEY));
			try {
				User user = this.userService.getUserByAccount(email);// checkExistsByEmail(email);
				if(user==null){ //邮箱不存在
					bVerify = true;
				}else {   //邮箱存在
					if(user.getId().equals(userId)){  //存在的邮箱是属于本人
						bVerify = true;
					}else{ //存在的邮箱是不属于本人
						bVerify = false;
					}
				}
			} catch (ServiceException e) {
				log.error("根据email获取账户失败,email=" + email, e);
				responseObj = generateResponseObject(ResponseCode.SHOW_EXCEPTION, "根据email检测会员是否存在失败!");
			}

			if (bVerify) {
				try {
					String token = this.commonService.getLastToken(email,Constant.TOKEN_TYPE_EMAIL1);
					if (StringUtil.isEmpty(token)) {
						token = this.generateResetPwdToken(email);
					}

					ResponseObject<String> result = this.commonService.insertToken(userId, email, token,Constant.TOKEN_TYPE_EMAIL1);
					if (request != null && ResponseCode.SUCCESS_CODE.equals(result.getCode())) {
						try {
							//String baseUrl = "http://" + request.getServerName() + ":" + request.getServerPort()
							//        + request.getContextPath() + "/";
							String baseUrl = request.getServerName();
							//MailSendUtil.sendVerifyMsg2(baseUrl, email, result.getData());
							
							Properties prop = PropertiesReader.read(Constant.EMAIL_PROPERTIES_FILE);
							String subject = prop.getProperty("anyonehelps.email.verify.subject");
							String url = baseUrl + "/emailVerify.jsp?email="
							        + email + "&token=" + token;
							String content = prop.getProperty("anyonehelps.email.verify.content");
							content = MessageFormat.format(content, new Object[] { url, url });
							
							String date = DateUtil.date2String(new Date());
							
							EmailSend es = new EmailSend();
							es.setContent(content);
							es.setCreateDate(date);
							es.setEmail(email);
							es.setFailCount(Constant.EMAILSEND_FAIL_COUNT0);
							es.setModifyDate(date);
							es.setState(Constant.EMAILSEND_STATE0);
							es.setSubject(subject);
							es.setUserId("-1");
							ResponseObject<Object> emailSendObj = emailSendService.addEmailSend(es);
							if(ResponseCode.SUCCESS_CODE.equals(emailSendObj.getCode())){
								responseObj = generateResponseObject(ResponseCode.SUCCESS_CODE, "重置密码邮件已发送");
							}else{
								responseObj = generateResponseObject(ResponseCode.TOKEN_INSERT_ERROR, "邮件发送失败");
							}
						} catch (Throwable e) {
							log.error("发送邮件失败", e);
							responseObj = generateResponseObject(ResponseCode.SHOW_EXCEPTION, "发送邮件失败!");
						}
					} else {
						responseObj = generateResponseObject(ResponseCode.TOKEN_INSERT_ERROR, "发送邮件失败");
					}
				} catch (Exception e) {
					log.error("保存token失败", e);
					responseObj = generateResponseObject(ResponseCode.SHOW_EXCEPTION, "发送邮件失败!");
				}
			} else {
				responseObj = generateResponseObject(ResponseCode.USER_EMAIL_NOT_EXISTS, "该email已经注册,不能再验证!");
			}
		}
		return responseObj;
	}
	
	@RequestMapping(value = "/user/email_verify2", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> emailVerify(
	        @RequestParam(value = ParameterConstants.USER_EMAIL, required = false, defaultValue = "") String email,
	        @RequestParam(value = ParameterConstants.COMMON_TOKEN, required = false, defaultValue = "") String token,
	        @RequestParam(value = ParameterConstants.COMMON_VALIDATE_CODE) String vcode) {
		ResponseObject<Object> responseObj = null;
		
		if (!UserUtil.validateEmail(email)) {
			responseObj = generateResponseObject(ResponseCode.USER_EMAIL_ERROR, "邮箱输入不正确，请重新输入！");
		} else {
			try {
				if (!StringUtil.isEmpty(token)) {
					ResponseObject<String> result = this.commonService.checkToken(email, token,Constant.TOKEN_TYPE_EMAIL1);
					if (result != null && ResponseCode.SUCCESS_CODE.equals(result.getCode())) {
						// 验证成功
						/*ResponseObject<Object> tresult = this.userService.modifyPasswordByEmail(null, email, password, null);
						if (tresult != null && ResponseCode.SUCCESS_CODE.equals(tresult.getCode())) {
							responseObj = generateResponseObject(ResponseCode.SUCCESS_CODE);
						} else {
							responseObj = generateResponseObject(ResponseCode.USER_MODIFY_FAILURE, "重置密码失败");
						}*/
						String userId = this.commonService.getUserIdByToken(token, Constant.TOKEN_TYPE_EMAIL1);
						//User user = this.userService.getUserByAccount(email);// checkExistsByEmail(email);
						if(userId!=null){
							responseObj = this.userService.modifyUserEmail(userId,email,Constant.USER_EMAIL_STATE1);
						}else{
							responseObj = generateResponseObject(ResponseCode.TOKEN_ERROR, "已过期或者是参数无效，请重新操作！");
						}
						
					} else {
						responseObj = generateResponseObject(ResponseCode.TOKEN_ERROR, "已过期或者是参数无效，请重新操作！");
					}
				} else {
					responseObj = generateResponseObject(ResponseCode.TOKEN_ERROR, "已过期或者是参数无效，请重新操作！");
				}

			} catch (Exception e) {
				log.error("检测token出现异常", e);
				responseObj = generateResponseObject(ResponseCode.SHOW_EXCEPTION, "邮箱验证失败!");
			}
		}
		return responseObj;
	}

	
	/**
	 * 用户修改手机时进行验证并保存
	 * @param request
	 * @param telphone
	 * @param vcode
	 * @return
	 */
	@RequestMapping(value = "/user/verify_telphone", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> verifyUserPhone(
	        HttpServletRequest request,
	        @RequestParam(value = ParameterConstants.USER_AREA_CODE) String areaCode,
	        @RequestParam(value = ParameterConstants.USER_PHONE) String telphone,
	        @RequestParam(value = ParameterConstants.COMMON_VALIDATE_CODE) String vcode) {
		ResponseObject<Object> responseObj = null;
		
		if (!checkVerifyPhoneCode(request, areaCode, telphone,vcode)) {
			responseObj = generateResponseObject(ResponseCode.VALIDATE_CODE_ERROR, "验证码输入错误，请重新输入");
		} else if (!UserUtil.validateAreaCode(areaCode)) {
			responseObj = generateResponseObject(ResponseCode.USER_PHONE_ERROR, "手机所属地区码输入错误，请重新输入!");
		} else if (!UserUtil.validatePhone(telphone)) {
			responseObj = generateResponseObject(ResponseCode.USER_NICK_NAME_ERROR, "手机号码输入不正确，请重新输入!");
		
		} else {
			String userId = StringUtil.obj2String(request.getSession()
					.getAttribute(Constant.USER_ID_SESSION_KEY));
			
			if((userId==null)||(userId.equalsIgnoreCase("")))
			{
				return new ResponseObject<Object>(ResponseCode.NEED_LOGIN, "请登录后，再操作!");
			}
			try {
				HttpSession session = request.getSession();  
				session.removeAttribute(Constant.PHONE_KEY);
				session.removeAttribute(Constant.PHONE_AREA_CODE);
				session.removeAttribute(Constant.PHONE_SEND_CODE);
				session.removeAttribute(Constant.PHONE_SEND_CODE_TIME);
				return this.userService.modifyUserPhone(userId, areaCode, telphone, Constant.USER_TELPHONE_STATE1);
			} catch (Exception e) {
				log.error("用户验证手机失败", e);
				return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "用户验证手机失败，请重试!");
			}
		}
		return responseObj;
	}
	
	/**
	 * 用户修改邮箱时进行验证并保存
	 * @param request
	 * @param email
	 * @param vcode
	 * @return
	 */
	@RequestMapping(value = "/user/verify_email", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> verifyUserEmail(
	        HttpServletRequest request,
	        @RequestParam(value = ParameterConstants.USER_EMAIL) String email,
	        @RequestParam(value = ParameterConstants.COMMON_VALIDATE_CODE) String vcode) {
		ResponseObject<Object> responseObj = null;
		
		if(!checkVerifyEmailCode(request, email,vcode))
		{
			responseObj = generateResponseObject(ResponseCode.VALIDATE_CODE_ERROR, "验证码输入错误，请重新输入");
		}
		else if (!UserUtil.validateEmail(email)) {
			responseObj = generateResponseObject(ResponseCode.USER_NICK_NAME_ERROR, "邮箱输入不正确，请重新输入!");
		
		} else {
			String userId = StringUtil.obj2String(request.getSession()
					.getAttribute(Constant.USER_ID_SESSION_KEY));
			
			if((userId==null)||(userId.equalsIgnoreCase("")))
			{
				return new ResponseObject<Object>(ResponseCode.NEED_LOGIN, "请登录后操作!");
			}
			try {
				return this.userService.modifyUserEmail(userId,email,Constant.USER_EMAIL_STATE1);
			} catch (Exception e) {
				log.error("用户验证邮箱失败", e);
				return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "用户验证邮箱失败，请重试!");
			}
		}

		return responseObj;
	}
	
	@RequestMapping(value = "/user/reset_pwd_byuser", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseObject<Object> resetPwdByUser(HttpServletRequest request,
	        @RequestParam(value = ParameterConstants.USER_OLD_PASSWORD) String oldPass,
	        @RequestParam(value = ParameterConstants.USER_NEW_PASSWORD) String newPass) {
		ResponseObject<Object> responseObj = null;

		String userId = StringUtil.obj2String(request.getSession()
				.getAttribute(Constant.USER_ID_SESSION_KEY));
		
		if((userId==null)||(userId.equalsIgnoreCase("")))
		{
			return new ResponseObject<Object>(ResponseCode.NEED_LOGIN, "请登录后操作!");
		}
		if (!UserUtil.validatePassword(oldPass)) {
			return new ResponseObject<Object>(ResponseCode.USER_PASSWORD_ERROR, "旧密码输入不正确，至少6位，至多20位字符，请重新输入！");
		}
		if (!UserUtil.validatePassword(newPass)) {
			return new ResponseObject<Object>(ResponseCode.USER_PASSWORD_ERROR, "新密码输入不正确，至少6位，至多20位字符，请重新输入！");
		} 
		if (oldPass.endsWith(newPass)) {
			return new ResponseObject<Object>(ResponseCode.USER_PASSWORD_ERROR, "您输入旧密码和新密码相同，无需修改！");
		} 
		try {
			ResponseObject<Object> result = this.userService.modifyPasswordByUser(userId,newPass, oldPass);
				
			if (result != null && ResponseCode.SUCCESS_CODE.equals(result.getCode())) {
				responseObj = generateResponseObject(ResponseCode.SUCCESS_CODE);
			} else {
				responseObj = generateResponseObject(ResponseCode.USER_MODIFY_FAILURE, result.getMessage());
			}
		} catch (Exception e) {
			log.error("修改/重置密码失败!", e);
			responseObj = generateResponseObject(ResponseCode.SHOW_EXCEPTION, "修改/重置密码失败!");
		}
		return responseObj;
	}
	
	@RequestMapping(value = "/user/save_info", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> saveUserInfo(
	        HttpServletRequest request,
	        @RequestParam(value = ParameterConstants.USER_COUNTRY, defaultValue = "", required = false) String country,
	        @RequestParam(value = ParameterConstants.USER_PROVINCE, defaultValue = "", required = false) String province,
	        @RequestParam(value = ParameterConstants.USER_CITY, defaultValue = "", required = false) String city,
	        @RequestParam(value = ParameterConstants.USER_SCHOOL, defaultValue = "", required = false) String school,
	        @RequestParam(value = ParameterConstants.USER_MAJOR, defaultValue = "", required = false) String major,
	        @RequestParam(value = ParameterConstants.USER_OTHER_CONTACT, defaultValue = "", required = false) String otherContact,
	        @RequestParam(value = ParameterConstants.USER_BRIEF , defaultValue = "", required = false) String brief,
	        @RequestParam(value = ParameterConstants.USER_OCCUPATION, defaultValue = "", required = false) String occupation,
	        @RequestParam(value = ParameterConstants.USER_WECHAT, defaultValue = "", required = false) String wechat,
	        @RequestParam(value = ParameterConstants.USER_ZIP_CODE, defaultValue = "", required = false) String zipCode,
	        @RequestParam(value = ParameterConstants.USER_DEGREE, defaultValue = "", required = false) String degree,
	        @RequestParam(value = ParameterConstants.USER_SEX, defaultValue = "0", required = false) String sex){
		
		if(!UserUtil.validateZipCode(zipCode)){
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "邮编填写不正确,请重新输入!");
		}
		if(!UserUtil.validateWechat(wechat)){
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "微信仅支持6-20个字母、数字、下划线、减号和加号组成!");
		}
		if(!UserUtil.validateSex(sex)){
			sex = "0";
		}
	    
		try {
			String id = StringUtil.obj2String(request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY));
			User user = new User();
			user.setId(id);
			user.setCity(city);
			user.setCountry(country);
			user.setMajor(major);
			user.setProvince(province);
			user.setOtherContact(otherContact);
			user.setSchool(school);
			user.setBrief(brief);
			user.setOccupation(occupation);
			user.setWechat(wechat);
			user.setDegree(degree);
			user.setZipCode(zipCode);
			user.setSex(sex);
			return this.userService.modifyUser(user);
		} catch (Exception e) {
			log.error("修改用户数据出现exception", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "修改个人信息失败,请重试!");
		}
	}
	
	/**
	 * 人物查找
	 * @return
	 */
	@RequestMapping(value = "/user/search_by_key", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseObject<PageSplit<User>> searchByKey(
			HttpServletRequest request,
			@RequestParam(value = ParameterConstants.COMMON_SEARCH_KEY, required = false, defaultValue = "") String key,
			@RequestParam(value = ParameterConstants.COMMON_SORT_TYPE, required = false,defaultValue = "0") String sortType,
			@RequestParam(value = ParameterConstants.USER_COUNTRY, required = false, defaultValue = "") String nationality,
			@RequestParam(value = ParameterConstants.SPECIALTY_TYPE_ID, required = false, defaultValue = "") String specialtyTypeId,
			@RequestParam(value = ParameterConstants.SPECIALTY_ID, required = false, defaultValue = "") String specialtyId,
			@RequestParam(value = ParameterConstants.COMMON_PAGESPLIT_PAGE_INDEX, required = false, defaultValue = "1") int pageIndex,
			@RequestParam(value = ParameterConstants.COMMON_PAGESPLIT_PAGE_SIZE, required = false, defaultValue = "10") int pageSize
			) {
		try {
			String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			
			key =  new String(key.getBytes("ISO-8859-1"), "utf-8");
			return this.userService.searchUserByKey(userId, key, sortType, nationality, specialtyTypeId,specialtyId , pageSize, pageIndex);
		} catch (Exception e) {
			log.error("获取人物异常", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "查找人物异常!");
		}
	}

	/**
	 * 首页名人堂
	 * @return
	 */
	@RequestMapping(value = "/user/by_sum_amount", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseObject<PageSplit<User>> getBySumAmount(
	        HttpServletRequest request,
			@RequestParam(value = ParameterConstants.COMMON_SEARCH_KEY, required = false, defaultValue = "") String key,
			@RequestParam(value = ParameterConstants.COMMON_PAGESPLIT_PAGE_INDEX, required = false, defaultValue = "1") int pageIndex,
			@RequestParam(value = ParameterConstants.COMMON_PAGESPLIT_PAGE_SIZE, required = false, defaultValue = "6") int pageSize
			) {
		try {
			String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			
			key =  new String(key.getBytes("ISO-8859-1"), "utf-8");
			return this.userService.getBySumAmount(userId, key, pageIndex, pageSize);
		} catch (Exception e) {
			log.error("获取名人堂异常", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取名人堂异常!");
		}
	}
	
	/**
	 * 获取用户的邀请人
	 * @return
	 */
	@RequestMapping(value = "/user/search_recommender", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseObject<PageSplit<User>> getByRecommender(HttpServletRequest request,
			@RequestParam(value = ParameterConstants.COMMON_PAGESPLIT_PAGE_INDEX, required = false, defaultValue = "1") int pageIndex,
			@RequestParam(value = ParameterConstants.COMMON_PAGESPLIT_PAGE_SIZE, required = false, defaultValue = "10") int pageSize) {
		try {
			String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			return this.userService.searchByRecommender(userId, pageSize, pageIndex);
		} catch (Exception e) {
			log.error("获取邀请人异常", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "获取邀请人异常!");
		}
	}
	
	@RequestMapping(value = "/user/modify_recommender", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<User> modifyRecommender(
	        HttpServletRequest request,
	        @RequestParam(value = ParameterConstants.COMMON_ACCOUNT) String account) {
		ResponseObject<User> responseObj = null;
		//暂时停掉接口 2017-02-14
		/*if (!UserUtil.validateEmail(account) &&!UserUtil.validateNickName(account)) {
			responseObj = generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数错误，请重新输入!");
		}  else {
			try {
				String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
				return this.userService.modifyRecommender(userId, account);
			} catch (Exception e) {
				log.error("补充邀请我的用户出错,请重试", e);
				return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "补充邀请我的用户出错,请重试");
			}
		}*/

		return responseObj;
	}
	@RequestMapping(value = "/user/modify_recommender_phone", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<User> modifyRecommenderByPhone(
	        HttpServletRequest request,
	        @RequestParam(value = ParameterConstants.USER_AREA_CODE) String areaCode,
	        @RequestParam(value = ParameterConstants.USER_PHONE) String telphone) {
		ResponseObject<User> responseObj = null;
		//暂时停掉接口 2017-02-14
		/*if (!UserUtil.validateAreaCode(areaCode) &&!UserUtil.validatePhone(telphone)) {
			responseObj = generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数错误，请重新输入!");
		}  else {
			try {
				String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
				return this.userService.modifyRecommenderByPhone(userId, areaCode,telphone);
			} catch (Exception e) {
				log.error("补充邀请我的用户出错,请重试", e);
				return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "补充邀请我的用户出错,请重试");
			}
		}*/

		return responseObj;
	}
	
	
	/**
	 * 管理员查找用户
	 * @return
	 */
	@RequestMapping(value = "/admin/user_search", method = { RequestMethod.GET })
	@ResponseBody
	public ResponseObject<PageSplit<User>> adminSearchUserByKey(
			@RequestParam(value = ParameterConstants.COMMON_SEARCH_KEY, required = false) String key,
			@RequestParam(value = ParameterConstants.COMMON_SORT_TYPE, required = false,defaultValue = "0") String sortType,
			@RequestParam(value = ParameterConstants.USER_COUNTRY, required = false) String nationality,
			@RequestParam(value = ParameterConstants.SPECIALTY_TYPE_ID, required = false) String specialtyTypeId,
			@RequestParam(value = ParameterConstants.DEMAND_ID, required = false, defaultValue = "") String demandId,  /*这个参数是为了获取这个任务是否邀请了用户*/
			@RequestParam(value = ParameterConstants.COMMON_PAGESPLIT_PAGE_INDEX, required = false, defaultValue = "1") int pageIndex,
			@RequestParam(value = ParameterConstants.COMMON_PAGESPLIT_PAGE_SIZE, required = false, defaultValue = "10") int pageSize
			) {
		try {
			key =  new String(key.getBytes("ISO-8859-1"), "utf-8");
			return this.userService.adminSearchUserByKey(key, sortType, nationality, specialtyTypeId,demandId, pageSize, pageIndex);
		} catch (Exception e) {
			log.error("获取用户异常", e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "查找用户异常!");
		}
	}
	
	
	@RequestMapping(value = "/email/subscribe", method = { RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> emailSubscribe(HttpServletRequest request,
			@RequestParam(value = "subscribe") String subscribe) {
		if (!"0".equals(subscribe)&&!"1".equals(subscribe)) {
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数错误！");
		} 
		try {
			String userId = (String)request.getSession().getAttribute(Constant.USER_ID_SESSION_KEY);
			return this.userService.emailSubscribe(userId,subscribe);

		} catch (Exception e) {
			if("0".equals(subscribe)){
				return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "退订异常，请重试!");
			}else{
				return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "订阅异常，请重试!");
			}
			
		}
	}
	
	/**
	 * 查询要不要验证码
	 * @param request
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/user/check_code", method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody
	public ResponseObject<Object> checkCode(
			HttpServletRequest request, 
			@RequestParam(value = "codeType") String codeType) {
		if (!Constant.IPRECORD_TYPE0.equals(codeType)
				&&!Constant.IPRECORD_TYPE1.equals(codeType)
				&&!Constant.IPRECORD_TYPE2.equals(codeType)
				&&!Constant.IPRECORD_TYPE3.equals(codeType)) {
			return generateResponseObject(ResponseCode.PARAMETER_ERROR, "参数错误！");
		} 
		
		ResponseObject<Object> responseObj = new ResponseObject<Object>(ResponseCode.SUCCESS_CODE);;
		try {
			if(this.ipRecordService.countByIP(codeType, getClientIp(request))>2){
				responseObj.setData(1);
			}else{
				responseObj.setData(0);
			}
		} catch (ServiceException e) {
			responseObj.setCode(ResponseCode.SHOW_EXCEPTION);
		}
		return responseObj;
	}
	
	

}
