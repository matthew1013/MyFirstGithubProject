package com.anyonehelps.listener;

import java.util.Date;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.anyonehelps.common.constants.Constant;
import com.anyonehelps.common.util.DateUtil;
import com.anyonehelps.common.util.SpringContextUtils;
import com.anyonehelps.common.util.sms.MailSenderFactory;
import com.anyonehelps.common.util.sms.SimpleMailSender;
import com.anyonehelps.common.util.sms.SimpleSmsSender;
import com.anyonehelps.common.util.sms.SmsSenderFactory;
import com.anyonehelps.common.util.sms.SmsTwilioSender;
import com.anyonehelps.dao.EmailSendDao;
import com.anyonehelps.dao.SmsSendDao;
import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.EmailSend;
import com.anyonehelps.model.SmsSend;

/**
 * 发短信与发邮件线程。
 * @author chenkanghua
 */
@Service
public class EmailAndSmsSendProcessor implements ApplicationListener<ContextRefreshedEvent> {
	//private static final Logger log = Logger.getLogger(EmailAndSmsSendProcessor.class);
	public static boolean threadInit = false;
    /**
     * 启动加载执行
     */
    public void onApplicationEvent(ContextRefreshedEvent event) {
    	if(threadInit){
    		return;
    	}
    	threadInit = true; 
    	//new EmailSendThread("EmailSend Thread1").start();   
    	//new SmsSendThread("SmsSend Thread2").start();    
    	 
         
    }
    

}

/**发邮件线程
 * @author chenkanghua 
 */  
class EmailSendThread extends Thread {  
	private static final Logger log = Logger.getLogger(EmailSendThread.class);
	private long sleepTime = 2000;
	
	private String name;  
	public EmailSendThread(String name) {  
		super();  
		this.name = name; 
	}  

	@Override
	public void run() {  
        while (!this.isInterrupted()) {// 线程未中断执行循环
        	log.info(name+", run。。。");
        	EmailSendDao emailSendDao = (EmailSendDao) SpringContextUtils.getBean("emailSendDao");
        	EmailSend es = null;
        	try {
				es = emailSendDao.getOneByState0();
				if(es!=null){
					String date = DateUtil.date2String(new Date());
					emailSendDao.modifyState(es.getId(), date, Constant.EMAILSEND_STATE1);
					SimpleMailSender sender = MailSenderFactory.generateMailSender();
					log.info(name+", email send start。。。");
					sender.send(es.getEmail(), es.getSubject(), es.getContent());
					log.info(name+", email send end。。。");
					date = DateUtil.date2String(new Date());
					emailSendDao.modifyState(es.getId(), date, Constant.EMAILSEND_STATE2);
				}
			} catch (Exception e) {
				e.printStackTrace();
				if(es!=null&&("0".equals(es.getFailCount())
						||"1".equals(es.getFailCount())
						||"2".equals(es.getFailCount()))){
					String date = DateUtil.date2String(new Date());
					try {
						emailSendDao.modifyFailCount(es.getId(), date);
					} catch (ServiceException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
        	
        	try {
    			Thread.sleep(sleepTime);
    		} catch (InterruptedException e) {
    		}//每隔20000ms执行一次
        }
        

	}
}  

/**发短信线程
 * @author chenkanghua 
 */  
class SmsSendThread extends Thread {  
	private static final Logger log = Logger.getLogger(SmsSendThread.class);
	private long sleepTime = 2000;
	
	private String name;  
	public SmsSendThread(String name) {  
		super();  
		this.name = name; 
	}  

	@Override
	public void run() {  
        while (!this.isInterrupted()) {// 线程未中断执行循环
        	log.info(name+", run。。。");
        	SmsSendDao smsSendDao = (SmsSendDao) SpringContextUtils.getBean("smsSendDao");
        	try {
        		SmsSend ss = smsSendDao.getOneByState0();
				if(ss!=null){
					String date = DateUtil.date2String(new Date());
					smsSendDao.modifyState(ss.getId(), date, Constant.EMAILSEND_STATE1);
					log.info(name+", sms send start。。。");
					if(ss.getAreaCode().equals("+86")||ss.getAreaCode().equals("0086")){
						SimpleSmsSender sender = SmsSenderFactory.generateSmsSender();
						String result = sender.sendSms(ss.getContent(), ss.getAreaCode()+ss.getTelphone());
						log.info(name+", sms send return:"+result);
						try{
							JSONObject json = JSONObject.fromObject(result);
							log.info(result);
							String code = json.getString("code");
							if("0".equals(code)){
								smsSendDao.modifyState(ss.getId(), date, Constant.EMAILSEND_STATE2);
							}else{
								if("0".equals(ss.getFailCount())
										||"1".equals(ss.getFailCount())
										||"2".equals(ss.getFailCount())){
									smsSendDao.modifyFailCount(ss.getId(), date);
								}else{
									smsSendDao.modifyState(ss.getId(), date, Constant.EMAILSEND_STATE3);
								}
							}
							
						}catch (Exception e) {
							log.error(name+", sms send error,sms id:"+ss.getId()+",phone:"+ss.getAreaCode()+ss.getTelphone()+",content:"+ss.getContent());
							smsSendDao.modifyFailCount(ss.getId(), date);
						}
					}else {
						String result = SmsTwilioSender.sendSms(ss.getContent(), ss.getAreaCode()+ss.getTelphone());
						log.info(result);
						log.error("SMS Send Result:" + result);
						try{
							JSONObject json = JSONObject.fromObject(result);
							log.info(result);
							String status = json.getString("status");
							if("queued".equals(status)){
								smsSendDao.modifyState(ss.getId(), date, Constant.EMAILSEND_STATE2);
							}else{
								if("0".equals(ss.getFailCount())
										||"1".equals(ss.getFailCount())
										||"2".equals(ss.getFailCount())){
									smsSendDao.modifyFailCount(ss.getId(), date);
								}else{
									smsSendDao.modifyState(ss.getId(), date, Constant.EMAILSEND_STATE3);
								}
							}
							
						}catch (Exception e) {
							log.error(name+", sms send error,sms id:"+ss.getId()+",phone:"+ss.getAreaCode()+ss.getTelphone()+",content:"+ss.getContent());
							smsSendDao.modifyFailCount(ss.getId(), date);
						}
						
					}
					
					log.info(name+", sms send end。。。");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
        	
        	try {
    			Thread.sleep(sleepTime);
    		} catch (InterruptedException e) {
    		}//每隔20000ms执行一次
        }
        

	}
}  

