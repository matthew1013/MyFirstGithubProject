package com.anyonehelps.listener;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.anyonehelps.common.util.SeoUtil;
import com.anyonehelps.common.util.SpringContextUtils;
import com.anyonehelps.dao.SeoDao;
import com.anyonehelps.model.Seo;

/**
 * 读seo信息线程。
 * @author chenkanghua
 */
@Service
public class SeoProcessor implements ApplicationListener<ContextRefreshedEvent> {
	public static boolean threadInit = false;
    /**
     * 启动加载执行
     */
    public void onApplicationEvent(ContextRefreshedEvent event) {
    	if(threadInit){
    		return;
    	}
    	threadInit = true;  
    	//new ReadSeoThread("Seo Thread").start();     
    }
    

}

/**读seo信息线程
 * @author chenkanghua 
 */  
class ReadSeoThread extends Thread {  
	private static final Logger log = Logger.getLogger(ReadSeoThread.class);
	private long sleepTime = 2*60*1000;
	
	private String name;  
	public ReadSeoThread(String name) {  
		super();  
		this.name = name; 
	}  

	@Override
	public void run() {  
        while (!this.isInterrupted()) {// 线程未中断执行循环
        	log.info(name+", run。。。");
        	SeoDao seoDao = (SeoDao) SpringContextUtils.getBean("seoDao");
        	try {
				List<Seo> seos = seoDao.getAll();
				SeoUtil.seos = seos;
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


