package com.anyonehelps.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anyonehelps.common.constants.Constant;
import com.anyonehelps.common.constants.ParameterConstants;
import com.anyonehelps.common.constants.ResponseCode;
import com.anyonehelps.common.util.DateUtil;
import com.anyonehelps.common.util.PropertiesReader;
import com.anyonehelps.common.util.UserUtil;
import com.anyonehelps.common.util.barcode.Code128Barcode;
import com.anyonehelps.common.util.sms.SmsConfigUtil;
import com.anyonehelps.common.util.sms.SmsSendUtil;
import com.anyonehelps.model.IPRecord;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.model.SmsSend;
import com.anyonehelps.service.IPRecordService;
import com.anyonehelps.service.SmsSendService;

@Controller
public class CodeController extends BasicController {
	private static final long serialVersionUID = 9095917149775028196L;
	private static final Logger log = Logger.getLogger(CodeController.class);
	@Resource(name = "ipRecordService")
	private IPRecordService ipRecordService;
	@Resource(name = "smsSendService")
	private SmsSendService smsSendService;
	
	private final static char[] BASIC_CHARACTERS = "qwertyuioplkjhgfdsazxxcvbnm1234567890".toCharArray();
	private final static int[] DEFAULT_WORD_COLOR = { 0x000000, 0x292421, 0x708069, 0x4169E1, 0x6A5ACD, 0xFF6100,
	        0x082E54, 0x385E0F, 0xFF0000, 0x802A2A, 0x8A360F, 0x873324, 0x5E2612, 0x8B4513, 0x0000FF, 0xB0171F,
	        0xB22222, 0xE3170D, 0x228B22, 0x8A2BE2, 0x0B1746, 0x191970, 0x483D8B, 0x191970 };
	private static int WORD_COLOR_SIZE;

	static {
		WORD_COLOR_SIZE = DEFAULT_WORD_COLOR.length;
	}

	public int codeLength = 4;
	public int width = 100;
	public int height = 30;

	@RequestMapping(value = "/barcode/generate", method = { RequestMethod.GET })
	public void generateBarcode(HttpServletResponse response, @RequestParam(value = "code") String code) {
		
		BufferedImage src = null;
		try {
			src = new Code128Barcode().createBarcode(code);
		} catch (Exception e) {
			log.error("create the barcode buffered image occur error", e);
		}

		if (src != null) {
			try {
				int width = 400;
				int height = 60;
				Image image = src.getScaledInstance(width, height, Image.SCALE_DEFAULT);
				BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				Graphics g = tag.getGraphics();
				g.drawImage(image, 0, 0, null);
				g.dispose();
				response.setContentType("image/gif");
				response.setHeader("Pragma", "no-cache");
				response.setHeader("Cache-Control", "no-cache");
				response.setDateHeader("Expires", 0);
				ImageIO.write(tag, "JPEG", response.getOutputStream());
			} catch (IOException e) {
				log.error("写出数据error", e);
			}
		}
	}
	
	@RequestMapping(value = "/code/generate", method = { RequestMethod.GET })
	public void generateCode(HttpServletRequest request, HttpServletResponse response) {
		// 定义图像buffer
		BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = buffImg.createGraphics();

		// 创建一个随机数生成器类
		Random random = new Random(System.currentTimeMillis());

		// 将图像填充为白色
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);

		// 画边框。
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, width - 1, height - 1);

		// 随机产生160条干扰线，使图象中的认证码不易被其它程序探测到。
		for (int i = 0; i < 20; i++) {
			int x = random.nextInt(width) - 5;
			int y = random.nextInt(height) - 5;
			int xl = random.nextInt(width);
			int yl = random.nextInt(height);
			g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
			g.drawLine(x, y, x + xl, y + yl);
		}

		// randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
		StringBuffer randomCode = new StringBuffer();
		int x = 5;
		int y = height - 5;

		g.setColor(Color.BLACK);
		g.setFont(g.getFont().deriveFont(Font.BOLD, 30f));

		// 随机产生codeCount数字的验证码。
		for (int i = 0; i < codeLength; i++) {
			// 得到随机产生的验证码数字。
			String strRand = String.valueOf(BASIC_CHARACTERS[random.nextInt(36)]);

			// 用随机产生的颜色将验证码绘制到图像中。
			g.setColor(new Color(DEFAULT_WORD_COLOR[random.nextInt(WORD_COLOR_SIZE)]));

			/**** 随机缩放文字并将文字旋转指定角度 **/
			// 将文字旋转指定角度
			Graphics2D g2d_word = (Graphics2D) g;
			AffineTransform trans = new AffineTransform();
			// trans.rotate(random.nextInt(20) * 3.14 / 180, 15 * i + 10, 7);
			// 缩放文字
			float scaleSize = random.nextFloat() + 0.8f;
			if (scaleSize > 1.1f) {
				scaleSize = 1f;
			}
			trans.scale(scaleSize, scaleSize);
			g2d_word.setTransform(trans);
			/************************/

			g.drawString(strRand, x, y);
			x += random.nextInt(10) + 20;

			// 将产生的四个随机数组合在一起。
			randomCode.append(strRand);
		}

		// 将四位数字的验证码保存到Session中。
		request.getSession().setAttribute(Constant.SECURITY_CODE_KEY, randomCode.toString());
		g.dispose();

		try {
			// 禁止缓存
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "No-cache");
			response.setDateHeader("Expires", 0);
			// 指定生成的响应是图片
			response.setContentType("image/jpeg");
			ImageIO.write(buffImg, "JPEG", response.getOutputStream());
		} catch (IOException e) {
			log.error("创建验证码失败", e);
		}
	}
	
	 @RequestMapping(value = "/code/phone", method = { RequestMethod.GET, RequestMethod.POST })
	 @ResponseBody  
	 protected ResponseObject<Object> sendCode( HttpServletRequest request,
			 @RequestParam(value = ParameterConstants.USER_AREA_CODE) String areaCode,
			 @RequestParam(value = ParameterConstants.USER_PHONE) String phone){  
		StringBuilder code = new StringBuilder();  
	    Random random = new Random();  
	     
	    if (!UserUtil.validateAreaCode(areaCode)) {
	    	return generateResponseObject(ResponseCode.PARAMETER_ERROR, "电话号码国家区号错误");
	    }
	     
	    if (!UserUtil.validatePhone(phone)) {
	    	return generateResponseObject(ResponseCode.PARAMETER_ERROR, "电话号码输入错误!");
		}
	    // 6位验证码 
	    for (int i = 0; i < 6; i++) {  
	    	code.append(String.valueOf(random.nextInt(10)));  
	    }  
	    
	    try {
	    	//同手机1分钟内不能超过一次
	    	int nResult = this.ipRecordService.countByPhone(Constant.IPRECORD_TYPE3, areaCode, phone);
	    	if(nResult>1){  
		    	return generateResponseObject(ResponseCode.PARAMETER_ERROR, "你获取验证码太频繁，请一分钟后再获取验证码!");
		    }
		    //同ip5分钟内不能超过10次
		    String ip = getClientIp(request);
		    int n = this.ipRecordService.countByIP(Constant.IPRECORD_TYPE3, ip);
		    if(n > 9){  
		    	return generateResponseObject(ResponseCode.PARAMETER_ERROR, "你获取验证码太频繁，请五分钟后再获取验证码!");
		    }
	    	System.out.println(SmsSendUtil.sendRegisterMsg(code.toString(), areaCode, phone));
	    	Properties prop = null;
			String content = null;
			if("+86".equals(areaCode)||"0086".equals(areaCode)){
				prop = PropertiesReader.read(SmsConfigUtil.getSmsConfigFile(Constant.COUNTRY_CODE_CN));
				content = prop.getProperty("anyonehelps.register.content");
				content = MessageFormat.format(content, new Object[] {code});
			}else {
				prop = PropertiesReader.read(SmsConfigUtil.getSmsConfigFile(Constant.COUNTRY_CODE_US));
				content = prop.getProperty("anyonehelps.register.content");
				content = MessageFormat.format(content, new Object[] {code});
			}
			String date = DateUtil.date2String(new Date());
			
			SmsSend ss = new SmsSend();
			ss.setContent(content);
			ss.setFailCount(Constant.EMAILSEND_FAIL_COUNT0);
			ss.setState(Constant.EMAILSEND_STATE0);
			ss.setAreaCode(areaCode);
			ss.setTelphone(phone);
			ss.setUserId("-1");
			ss.setCreateDate(date);
			ss.setModifyDate(date);
			ResponseObject<Object> ssObj = smsSendService.addSmsSend(ss);
			if(ResponseCode.SUCCESS_CODE.equals(ssObj.getCode())){
				//pass
			}else{
				throw new Exception("发送验证码失败");
			}
			
			HttpSession session = request.getSession();  
			session.setAttribute(Constant.PHONE_AREA_CODE, areaCode);  
		    session.setAttribute(Constant.PHONE_KEY, phone);  
		    session.setAttribute(Constant.PHONE_SEND_CODE, code.toString());  
		    session.setAttribute(Constant.PHONE_SEND_CODE_TIME, new Date().getTime());
		    IPRecord ipRecord = new IPRecord();
		    ipRecord.setIp(ip);
		    ipRecord.setAreaCode(areaCode);
		    ipRecord.setTelphone(phone);
		    ipRecord.setType(Constant.IPRECORD_TYPE3);
		    this.ipRecordService.addIPRecord(ipRecord);
		    return generateResponseObject(ResponseCode.SUCCESS_CODE);
	    } catch (Exception e) {
			log.error("/code/phone发送验证码异常,areaCode:"+areaCode+",phone:"+phone, e);
			return generateResponseObject(ResponseCode.SHOW_EXCEPTION, "发送验证码失败,请重试!");
		}
	 }  
	 
	 
		@RequestMapping(value = "/code/test", method = { RequestMethod.GET, RequestMethod.GET })
		public void test(HttpServletRequest request, HttpServletResponse response) {
			
			response.setContentType("application/octet-stream");
			response.setHeader("Content-disposition", "attachment;filename="
			+ "a.pdf");
	        ServletOutputStream out;   
	  
	        try {  
	        	
	        	URL url = new URL("https://s3-us-west-1.amazonaws.com/anyonehelps/converter/2017021600241852704.pdf");
				
		        // 打开连接    
		        URLConnection con = url.openConnection();  
		        //设置请求超时为5s    
		        con.setConnectTimeout(5 * 1000);  
		        // 输入流    
		        InputStream is = con.getInputStream(); 
	            //3.通过response获取ServletOutputStream对象(out)  
	            out = response.getOutputStream();  
	  
	            int b = 0;  
	            byte[] buffer = new byte[512];  
	            while (b != -1){  
	                b = is.read(buffer);  
	                //4.写到输出流(out)中  
	                out.write(buffer,0,b);  
	            }  
	            is.close();  
	            out.close();  
	            out.flush();  
	  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
		}

	 
}
