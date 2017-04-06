package com.anyonehelps.listener;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.anyonehelps.common.constants.Constant;
import com.anyonehelps.common.util.Office2PdfUtil;
import com.anyonehelps.common.util.SpringContextUtils;
import com.anyonehelps.common.util.StringUtil;
import com.anyonehelps.dao.DemandDao;
import com.anyonehelps.dao.EducationDao;
import com.anyonehelps.dao.ProUserDao;
import com.anyonehelps.dao.ReceiveDemandDao;
import com.anyonehelps.dao.SpecialtyUserDao;
import com.anyonehelps.dao.WorkExperienceDao;
import com.anyonehelps.dao.WorksDao;
import com.anyonehelps.model.Demand;
import com.anyonehelps.model.Education;
import com.anyonehelps.model.ProUser;
import com.anyonehelps.model.ReceiveDemand;
import com.anyonehelps.model.SpecialtyUser;
import com.anyonehelps.model.WorkExperience;
import com.anyonehelps.model.Works;

/**
 * 文件转换线程。
 * @author chenkanghua
 */
@Service
public class Office2PdfProcessor implements ApplicationListener<ContextRefreshedEvent> {
	//private static final Logger log = Logger.getLogger(Office2PdfProcessor.class);
	public static boolean threadInit = false;
	/*@Autowired
	private EmailSendDao emailSendDao;
	@Autowired
	private SmsSendDao smsSendDao;*/
    /**
     * 启动加载执行
     */
    public void onApplicationEvent(ContextRefreshedEvent event) {
    	if(threadInit){
    		return;
    	}
    	threadInit = true; 
    	//new Office2PdfThread().start();   
    	//new ProcedureThread().start();   
         
    }
    

}


/**
 * 文件转换线程。
 * @author chenkanghua 
 */  
class ProcedureThread extends Thread {  
	private static final Logger log = Logger.getLogger(ProcedureThread.class);
	
	private long sleepTime = 60000;

	@Override
	public void run() {  
        while (!this.isInterrupted()) {// 线程未中断执行循环
        	log.error("==================================================");
        	log.error("ProcedureThread Start");
        	log.error("==================================================");
        	/*================== Demand Start===========================*/
        	DemandDao demandDao = (DemandDao) SpringContextUtils.getBean("demandDao");
        	/*临时 start*/
        	log.error("Auto Demand Start");
        	demandDao.exeAutoDemand();
        	log.error("Auto User Start");
        	demandDao.exeAutoUser();
        	/*临时 end*/
        	
        	log.error("==================================================");
        	log.error("ProcedureThread end");
        	log.error("=================================================="); 
        	try { 
    			Thread.sleep(sleepTime);
    		} catch (InterruptedException e) {
    		}//每隔20000ms执行一次
        }
	}
}  

/**
 * 文件转换线程。
 * @author chenkanghua 
 */  
class Office2PdfThread extends Thread {  
	//private static final Logger log = Logger.getLogger(Office2PdfThread.class);
	private long sleepTime = 2000;
	
	private String converterTempDir = "/upload/converter_tmp";
	private String S3ConverterFileDir = "converter";
	private String amazonS3Url = "https://s3-us-west-1.amazonaws.com/anyonehelps/";
	/*public Office2PdfThread(){
		Properties prop = PropertiesReader.read(Constant.PROPERTIES_FILE);
		this.converterTempDir = prop.getProperty("converter_file_temp_dir");
		this.S3ConverterFileDir = prop.getProperty("s3_converter_file_dir");
		log.info("Office2PdfThread Start");
		log.info("converterTempDir:"+converterTempDir);
		log.info("S3ConverterFileDir:"+S3ConverterFileDir);
	}*/

	@Override
	public void run() {  
        while (!this.isInterrupted()) {// 线程未中断执行循环
        	
        	/*================== Demand Start===========================*/
        	DemandDao demandDao = (DemandDao) SpringContextUtils.getBean("demandDao");

        	
        	List<Demand> demands = null;
        	
        	try {
				demands = demandDao.getByECS();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	if(demands != null){
        		for(Demand demand : demands){
        			//附件1
    				/*不为空，而且以http开头*/
    				String deps1 = Constant.ENCLOSURE_PDF_STATE0;
    				String dep1 = null;
    				if(!StringUtil.isEmpty(demand.getEnclosure1())
    						&&demand.getEnclosure1().startsWith("http")
    						&&( demand.getEnclosure1().endsWith(".doc")||
    							demand.getEnclosure1().endsWith(".docx")||
    							demand.getEnclosure1().endsWith(".ppt")||
    							demand.getEnclosure1().endsWith(".pptx")||
    							demand.getEnclosure1().endsWith(".xls")||
    							demand.getEnclosure1().endsWith(".xlsx") )){
    					dep1 = Office2PdfUtil.S3Office2PDF(demand.getEnclosure1(), converterTempDir + "/", S3ConverterFileDir);
    					if(dep1 != null){
    						deps1 = Constant.ENCLOSURE_PDF_STATE1;
    						dep1 =amazonS3Url + dep1;
    					}else{
    						dep1 = Office2PdfUtil.S3Office2PDF(demand.getEnclosure1(), converterTempDir + "/", S3ConverterFileDir);
    						if(dep1 != null){
    							deps1 = Constant.ENCLOSURE_PDF_STATE1;
    							dep1 =amazonS3Url + dep1;
    						}else{
    							deps1 = Constant.ENCLOSURE_PDF_STATE2;
    						}
    					}
    				}
    				
    				
    				//附件2
    				/*不为空，而且以http开头*/
    				String deps2 = Constant.ENCLOSURE_PDF_STATE0;
    				String dep2 = null;
    				if(!StringUtil.isEmpty(demand.getEnclosure2())
    						&&demand.getEnclosure1().startsWith("http")
    						&&( demand.getEnclosure1().endsWith(".doc")||
        							demand.getEnclosure1().endsWith(".docx")||
        							demand.getEnclosure1().endsWith(".ppt")||
        							demand.getEnclosure1().endsWith(".pptx")||
        							demand.getEnclosure1().endsWith(".xls")||
        							demand.getEnclosure1().endsWith(".xlsx") )){
    					dep2 = Office2PdfUtil.S3Office2PDF(demand.getEnclosure2(), converterTempDir + "/", S3ConverterFileDir);
    					if(dep2 != null){
    						deps2 = Constant.ENCLOSURE_PDF_STATE1;
    						dep2 =amazonS3Url + dep2;
    					}else{
    						dep2 = Office2PdfUtil.S3Office2PDF(demand.getEnclosure2(), converterTempDir + "/", S3ConverterFileDir);
    						if(dep2 != null){
    							deps2 = Constant.ENCLOSURE_PDF_STATE1;
    							dep2 =amazonS3Url + dep2;
    						}else{
    							deps2 = Constant.ENCLOSURE_PDF_STATE2;
    						}
    					}
    				}
    				
    				//附件3
    				/*不为空，而且以http开头*/
    				String deps3 = Constant.ENCLOSURE_PDF_STATE0;
    				String dep3 = null;
    				if(!StringUtil.isEmpty(demand.getEnclosure3())
    						&&demand.getEnclosure1().startsWith("http")
    						&&( demand.getEnclosure1().endsWith(".doc")||
        							demand.getEnclosure1().endsWith(".docx")||
        							demand.getEnclosure1().endsWith(".ppt")||
        							demand.getEnclosure1().endsWith(".pptx")||
        							demand.getEnclosure1().endsWith(".xls")||
        							demand.getEnclosure1().endsWith(".xlsx") )){
    					dep3 = Office2PdfUtil.S3Office2PDF(demand.getEnclosure3(), converterTempDir + "/", S3ConverterFileDir);
    					if(dep3 != null){
    						deps3 = Constant.ENCLOSURE_PDF_STATE1;
    						dep3 =amazonS3Url + dep3;
    					}else{
    						dep3 = Office2PdfUtil.S3Office2PDF(demand.getEnclosure3(), converterTempDir + "/", S3ConverterFileDir);
    						if(dep3 != null){
    							deps3 = Constant.ENCLOSURE_PDF_STATE1;
    							dep3 =amazonS3Url + dep3;
    						}else{
    							deps3 = Constant.ENCLOSURE_PDF_STATE2;
    						}
    					}
    				}
    				
    				//附件4
    				/*不为空，而且以http开头*/
    				String deps4 = Constant.ENCLOSURE_PDF_STATE0;
    				String dep4 = null;
    				if(!StringUtil.isEmpty(demand.getEnclosure4())
    						&&demand.getEnclosure1().startsWith("http")
    						&&( demand.getEnclosure1().endsWith(".doc")||
        							demand.getEnclosure1().endsWith(".docx")||
        							demand.getEnclosure1().endsWith(".ppt")||
        							demand.getEnclosure1().endsWith(".pptx")||
        							demand.getEnclosure1().endsWith(".xls")||
        							demand.getEnclosure1().endsWith(".xlsx") )){
    					dep4 = Office2PdfUtil.S3Office2PDF(demand.getEnclosure4(), converterTempDir + "/", S3ConverterFileDir);
    					if(dep4 != null){
    						deps4 = Constant.ENCLOSURE_PDF_STATE1;
    						dep4 =amazonS3Url + dep4;
    					}else{
    						dep4 = Office2PdfUtil.S3Office2PDF(demand.getEnclosure4(), converterTempDir + "/", S3ConverterFileDir);
    						if(dep4 != null){
    							deps4 = Constant.ENCLOSURE_PDF_STATE1;
    							dep4 =amazonS3Url + dep4;
    						}else{
    							deps4 = Constant.ENCLOSURE_PDF_STATE2;
    						}
    					}
    				}
    				
    				//附件5
    				/*不为空，而且以http开头*/
    				String deps5 = Constant.ENCLOSURE_PDF_STATE0;
    				String dep5 = null;
    				if(!StringUtil.isEmpty(demand.getEnclosure5())
    						&&demand.getEnclosure1().startsWith("http")
    						&&( demand.getEnclosure1().endsWith(".doc")||
        							demand.getEnclosure1().endsWith(".docx")||
        							demand.getEnclosure1().endsWith(".ppt")||
        							demand.getEnclosure1().endsWith(".pptx")||
        							demand.getEnclosure1().endsWith(".xls")||
        							demand.getEnclosure1().endsWith(".xlsx") )){
    					dep5 = Office2PdfUtil.S3Office2PDF(demand.getEnclosure5(), converterTempDir + "/", S3ConverterFileDir);
    					if(dep5 != null){
    						deps5 = Constant.ENCLOSURE_PDF_STATE1;
    						dep5 =amazonS3Url + dep5;
    					}else{
    						dep5 = Office2PdfUtil.S3Office2PDF(demand.getEnclosure5(), converterTempDir + "/", S3ConverterFileDir);
    						if(dep5 != null){
    							deps5 = Constant.ENCLOSURE_PDF_STATE1;
    							dep5 =amazonS3Url + dep5;
    						}else{
    							deps5 = Constant.ENCLOSURE_PDF_STATE2;
    						}
    					}
    				} 
    				
    				try {
						demandDao.modifyECS(demand.getId(), dep1, dep2, dep3, dep4, dep5, deps1, deps2, deps3, deps4, deps5);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    				
        		}
        	}
        	
        	/*================== Demand End===========================*/
        	
        	
        	
        	/*================== ReceiveDemand Start===========================*/
        	ReceiveDemandDao rdDao = (ReceiveDemandDao) SpringContextUtils.getBean("receiveDemandDao");
        	List<ReceiveDemand> rds = null;
        	
        	try {
        		rds = rdDao.getByECS();
			} catch (Exception e) {
				e.printStackTrace();
			}
        	if(rds != null){
        		for(ReceiveDemand rd : rds){
    				/*不为空，而且以http开头*/
    				String prps1 = Constant.ENCLOSURE_PDF_STATE0;
    				String prpp1 = null;
    				if(!StringUtil.isEmpty(rd.getPayReasonUrl1())
    						&&rd.getPayReasonUrl1().startsWith("http")
    						&&( rd.getPayReasonUrl1().endsWith(".doc")
    							||rd.getPayReasonUrl1().endsWith(".docx")
    							||rd.getPayReasonUrl1().endsWith(".ppt")
    							||rd.getPayReasonUrl1().endsWith(".pptx")
    							||rd.getPayReasonUrl1().endsWith(".xls")
    							||rd.getPayReasonUrl1().endsWith(".xlsx") )){
    					prpp1 = Office2PdfUtil.S3Office2PDF(rd.getPayReasonUrl1(), converterTempDir + "/", S3ConverterFileDir);
    					if(prpp1 != null){
    						prps1 = Constant.ENCLOSURE_PDF_STATE1;
    						prpp1 =amazonS3Url + prpp1;
    					}else{
    						prpp1 = Office2PdfUtil.S3Office2PDF(rd.getPayReasonUrl1(), converterTempDir + "/", S3ConverterFileDir);
    						if(prpp1 != null){
    							prps1 = Constant.ENCLOSURE_PDF_STATE1;
    							prpp1 =amazonS3Url + prpp1;
    						}else{
    							prps1 = Constant.ENCLOSURE_PDF_STATE2;
    						}
    					}
    				}
    				rd.setPayReasonUrl1Pdf(prpp1);
    				rd.setPayReasonPdf1State(prps1);
    				
    				/*不为空，而且以http开头*/
    				String prps2 = Constant.ENCLOSURE_PDF_STATE0;
    				String prpp2 = null;
    				if(!StringUtil.isEmpty(rd.getPayReasonUrl2())
    						&&rd.getPayReasonUrl2().startsWith("http")
    						&&( rd.getPayReasonUrl2().endsWith(".doc")
    							||rd.getPayReasonUrl2().endsWith(".docx")
    							||rd.getPayReasonUrl2().endsWith(".ppt")
    							||rd.getPayReasonUrl2().endsWith(".pptx")
    							||rd.getPayReasonUrl2().endsWith(".xls")
    							||rd.getPayReasonUrl2().endsWith(".xlsx") )){
    					prpp2 = Office2PdfUtil.S3Office2PDF(rd.getPayReasonUrl2(), converterTempDir + "/", S3ConverterFileDir);
    					if(prpp2 != null){
    						prps2 = Constant.ENCLOSURE_PDF_STATE1;
    						prpp2 =amazonS3Url + prpp2;
    					}else{
    						prpp2 = Office2PdfUtil.S3Office2PDF(rd.getPayReasonUrl2(), converterTempDir + "/", S3ConverterFileDir);
    						if(prpp2 != null){
    							prps2 = Constant.ENCLOSURE_PDF_STATE1;
    							prpp2 =amazonS3Url + prpp2;
    						}else{
    							prps2 = Constant.ENCLOSURE_PDF_STATE2;
    						}
    					}
    				}
    				rd.setPayReasonUrl2Pdf(prpp2);
    				rd.setPayReasonPdf2State(prps2);
    				
    				/*不为空，而且以http开头*/
    				String prps3 = Constant.ENCLOSURE_PDF_STATE0;
    				String prpp3 = null;
    				if(!StringUtil.isEmpty(rd.getPayReasonUrl3())
    						&&rd.getPayReasonUrl3().startsWith("http")
    						&&( rd.getPayReasonUrl3().endsWith(".doc")
    							||rd.getPayReasonUrl3().endsWith(".docx")
    							||rd.getPayReasonUrl3().endsWith(".ppt")
    							||rd.getPayReasonUrl3().endsWith(".pptx")
    							||rd.getPayReasonUrl3().endsWith(".xls")
    							||rd.getPayReasonUrl3().endsWith(".xlsx") )){
    					prpp3 = Office2PdfUtil.S3Office2PDF(rd.getPayReasonUrl3(), converterTempDir + "/", S3ConverterFileDir);
    					if(prpp3 != null){
    						prps3 = Constant.ENCLOSURE_PDF_STATE1;
    						prpp3 =amazonS3Url + prpp3;
    					}else{
    						prpp3 = Office2PdfUtil.S3Office2PDF(rd.getPayReasonUrl3(), converterTempDir + "/", S3ConverterFileDir);
    						if(prpp3 != null){
    							prps3 = Constant.ENCLOSURE_PDF_STATE1;
    							prpp3 =amazonS3Url + prpp3;
    						}else{
    							prps3 = Constant.ENCLOSURE_PDF_STATE2;
    						}
    					}
    				}
    				rd.setPayReasonUrl3Pdf(prpp3);
    				rd.setPayReasonPdf3State(prps3);
    				
    				/*不为空，而且以http开头*/
    				String prps4 = Constant.ENCLOSURE_PDF_STATE0;
    				String prpp4 = null;
    				if(!StringUtil.isEmpty(rd.getPayReasonUrl4())
    						&&rd.getPayReasonUrl4().startsWith("http")
    						&&( rd.getPayReasonUrl4().endsWith(".doc")
    							||rd.getPayReasonUrl4().endsWith(".docx")
    							||rd.getPayReasonUrl4().endsWith(".ppt")
    							||rd.getPayReasonUrl4().endsWith(".pptx")
    							||rd.getPayReasonUrl4().endsWith(".xls")
    							||rd.getPayReasonUrl4().endsWith(".xlsx") )){
    					prpp4 = Office2PdfUtil.S3Office2PDF(rd.getPayReasonUrl4(), converterTempDir + "/", S3ConverterFileDir);
    					if(prpp4 != null){
    						prps4 = Constant.ENCLOSURE_PDF_STATE1;
    						prpp4 =amazonS3Url + prpp4;
    					}else{
    						prpp4 = Office2PdfUtil.S3Office2PDF(rd.getPayReasonUrl4(), converterTempDir + "/", S3ConverterFileDir);
    						if(prpp4 != null){
    							prps4 = Constant.ENCLOSURE_PDF_STATE1;
    							prpp4 =amazonS3Url + prpp4;
    						}else{
    							prps4 = Constant.ENCLOSURE_PDF_STATE2;
    						}
    					}
    				}
    				rd.setPayReasonUrl4Pdf(prpp4);
    				rd.setPayReasonPdf4State(prps4);
    				
    				/*不为空，而且以http开头*/
    				String prps5 = Constant.ENCLOSURE_PDF_STATE0;
    				String prpp5 = null;
    				if(!StringUtil.isEmpty(rd.getPayReasonUrl5())
    						&&rd.getPayReasonUrl5().startsWith("http")
    						&&( rd.getPayReasonUrl5().endsWith(".doc")
    							||rd.getPayReasonUrl5().endsWith(".docx")
    							||rd.getPayReasonUrl5().endsWith(".ppt")
    							||rd.getPayReasonUrl5().endsWith(".pptx")
    							||rd.getPayReasonUrl5().endsWith(".xls")
    							||rd.getPayReasonUrl5().endsWith(".xlsx") )){
    					prpp5 = Office2PdfUtil.S3Office2PDF(rd.getPayReasonUrl5(), converterTempDir + "/", S3ConverterFileDir);
    					if(prpp5 != null){
    						prps5 = Constant.ENCLOSURE_PDF_STATE1;
    						prpp5 =amazonS3Url + prpp5;
    					}else{
    						prpp5 = Office2PdfUtil.S3Office2PDF(rd.getPayReasonUrl5(), converterTempDir + "/", S3ConverterFileDir);
    						if(prpp5 != null){
    							prps5 = Constant.ENCLOSURE_PDF_STATE1;
    							prpp5 =amazonS3Url + prpp5;
    						}else{
    							prps5 = Constant.ENCLOSURE_PDF_STATE2;
    						}
    					}
    				}
    				rd.setPayReasonUrl5Pdf(prpp5);
    				rd.setPayReasonPdf5State(prps5);
    		
    				
    				/*不为空，而且以http开头*/
    				String rrps1 = Constant.ENCLOSURE_PDF_STATE0;
    				String rrpp1 = null;
    				if(!StringUtil.isEmpty(rd.getRefuteReasonUrl1())
    						&&rd.getRefuteReasonUrl1().startsWith("http")
    						&&( rd.getRefuteReasonUrl1().endsWith(".doc")
    							||rd.getRefuteReasonUrl1().endsWith(".docx")
    							||rd.getRefuteReasonUrl1().endsWith(".ppt")
    							||rd.getRefuteReasonUrl1().endsWith(".pptx")
    							||rd.getRefuteReasonUrl1().endsWith(".xls")
    							||rd.getRefuteReasonUrl1().endsWith(".xlsx") )){
    					rrpp1 = Office2PdfUtil.S3Office2PDF(rd.getRefuteReasonUrl1(), converterTempDir + "/", S3ConverterFileDir);
    					if(rrpp1 != null){
    						rrps1 = Constant.ENCLOSURE_PDF_STATE1;
    						rrpp1 =amazonS3Url + rrpp1;
    					}else{
    						rrpp1 = Office2PdfUtil.S3Office2PDF(rd.getRefuteReasonUrl1(), converterTempDir + "/", S3ConverterFileDir);
    						if(rrpp1 != null){
    							rrps1 = Constant.ENCLOSURE_PDF_STATE1;
    							rrpp1 =amazonS3Url + rrpp1;
    						}else{
    							rrps1 = Constant.ENCLOSURE_PDF_STATE2;
    						}
    					}
    				}
    				rd.setRefuteReasonUrl1Pdf(rrpp1);
    				rd.setRefuteReasonPdf1State(rrps1);
    				
    				/*不为空，而且以http开头*/
    				String rrps2 = Constant.ENCLOSURE_PDF_STATE0;
    				String rrpp2 = null;
    				if(!StringUtil.isEmpty(rd.getRefuteReasonUrl2())
    						&&rd.getRefuteReasonUrl2().startsWith("http")
    						&&( rd.getRefuteReasonUrl2().endsWith(".doc")
    							||rd.getRefuteReasonUrl2().endsWith(".docx")
    							||rd.getRefuteReasonUrl2().endsWith(".ppt")
    							||rd.getRefuteReasonUrl2().endsWith(".pptx")
    							||rd.getRefuteReasonUrl2().endsWith(".xls")
    							||rd.getRefuteReasonUrl2().endsWith(".xlsx") )){
    					rrpp2 = Office2PdfUtil.S3Office2PDF(rd.getRefuteReasonUrl2(), converterTempDir + "/", S3ConverterFileDir);
    					if(rrpp2 != null){
    						rrps2 = Constant.ENCLOSURE_PDF_STATE1;
    						rrpp2 =amazonS3Url + rrpp2;
    					}else{
    						rrpp2 = Office2PdfUtil.S3Office2PDF(rd.getRefuteReasonUrl2(), converterTempDir + "/", S3ConverterFileDir);
    						if(rrpp2 != null){
    							rrps2 = Constant.ENCLOSURE_PDF_STATE1;
    							rrpp2 =amazonS3Url + rrpp2;
    						}else{
    							rrps2 = Constant.ENCLOSURE_PDF_STATE2;
    						}
    					}
    				}
    				rd.setRefuteReasonUrl2Pdf(rrpp2);
    				rd.setRefuteReasonPdf2State(rrps2);
    				
    				
    				/*不为空，而且以http开头*/
    				String rrps3 = Constant.ENCLOSURE_PDF_STATE0;
    				String rrpp3 = null;
    				if(!StringUtil.isEmpty(rd.getRefuteReasonUrl3())
    						&&rd.getRefuteReasonUrl3().startsWith("http")
    						&&( rd.getRefuteReasonUrl3().endsWith(".doc")
    							||rd.getRefuteReasonUrl3().endsWith(".docx")
    							||rd.getRefuteReasonUrl3().endsWith(".ppt")
    							||rd.getRefuteReasonUrl3().endsWith(".pptx")
    							||rd.getRefuteReasonUrl3().endsWith(".xls")
    							||rd.getRefuteReasonUrl3().endsWith(".xlsx") )){
    					rrpp3 = Office2PdfUtil.S3Office2PDF(rd.getRefuteReasonUrl3(), converterTempDir + "/", S3ConverterFileDir);
    					if(rrpp3 != null){
    						rrps3 = Constant.ENCLOSURE_PDF_STATE1;
    						rrpp3 =amazonS3Url + rrpp3;
    					}else{
    						rrpp3 = Office2PdfUtil.S3Office2PDF(rd.getRefuteReasonUrl3(), converterTempDir + "/", S3ConverterFileDir);
    						if(rrpp3 != null){
    							rrps3 = Constant.ENCLOSURE_PDF_STATE1;
    							rrpp3 =amazonS3Url + rrpp3;
    						}else{
    							rrps3 = Constant.ENCLOSURE_PDF_STATE2;
    						}
    					}
    				}
    				rd.setRefuteReasonUrl3Pdf(rrpp3);
    				rd.setRefuteReasonPdf3State(rrps3);
        	

    				/*不为空，而且以http开头*/
    				String rrps4 = Constant.ENCLOSURE_PDF_STATE0;
    				String rrpp4 = null;
    				if(!StringUtil.isEmpty(rd.getRefuteReasonUrl4())
    						&&rd.getRefuteReasonUrl4().startsWith("http")
    						&&( rd.getRefuteReasonUrl4().endsWith(".doc")
    							||rd.getRefuteReasonUrl4().endsWith(".docx")
    							||rd.getRefuteReasonUrl4().endsWith(".ppt")
    							||rd.getRefuteReasonUrl4().endsWith(".pptx")
    							||rd.getRefuteReasonUrl4().endsWith(".xls")
    							||rd.getRefuteReasonUrl4().endsWith(".xlsx") )){
    					rrpp4 = Office2PdfUtil.S3Office2PDF(rd.getRefuteReasonUrl4(), converterTempDir + "/", S3ConverterFileDir);
    					if(rrpp4 != null){
    						rrps4 = Constant.ENCLOSURE_PDF_STATE1;
    						rrpp4 =amazonS3Url + rrpp4;
    					}else{
    						rrpp4 = Office2PdfUtil.S3Office2PDF(rd.getRefuteReasonUrl4(), converterTempDir + "/", S3ConverterFileDir);
    						if(rrpp4 != null){
    							rrps4 = Constant.ENCLOSURE_PDF_STATE1;
    							rrpp4 =amazonS3Url + rrpp4;
    						}else{
    							rrps4 = Constant.ENCLOSURE_PDF_STATE2;
    						}
    					}
    				}
    				rd.setRefuteReasonUrl4Pdf(rrpp4);
    				rd.setRefuteReasonPdf4State(rrps4);
    				
    				
    				/*不为空，而且以http开头*/
    				String rrps5 = Constant.ENCLOSURE_PDF_STATE0;
    				String rrpp5 = null;
    				if(!StringUtil.isEmpty(rd.getRefuteReasonUrl5())
    						&&rd.getRefuteReasonUrl5().startsWith("http")
    						&&( rd.getRefuteReasonUrl5().endsWith(".doc")
    							||rd.getRefuteReasonUrl5().endsWith(".docx")
    							||rd.getRefuteReasonUrl5().endsWith(".ppt")
    							||rd.getRefuteReasonUrl5().endsWith(".pptx")
    							||rd.getRefuteReasonUrl5().endsWith(".xls")
    							||rd.getRefuteReasonUrl5().endsWith(".xlsx") )){
    					rrpp5 = Office2PdfUtil.S3Office2PDF(rd.getRefuteReasonUrl5(), converterTempDir + "/", S3ConverterFileDir);
    					if(rrpp5 != null){
    						rrps5 = Constant.ENCLOSURE_PDF_STATE1;
    						rrpp5 =amazonS3Url + rrpp5;
    					}else{
    						rrpp5 = Office2PdfUtil.S3Office2PDF(rd.getRefuteReasonUrl5(), converterTempDir + "/", S3ConverterFileDir);
    						if(rrpp5 != null){
    							rrps5 = Constant.ENCLOSURE_PDF_STATE1;
    							rrpp5 =amazonS3Url + rrpp5;
    						}else{
    							rrps5 = Constant.ENCLOSURE_PDF_STATE2;
    						}
    					}
    				}
    				rd.setRefuteReasonUrl5Pdf(rrpp5);
    				rd.setRefuteReasonPdf5State(rrps5);
    				
    				
    				
    				/*不为空，而且以http开头*/
    				String rrups1 = Constant.ENCLOSURE_PDF_STATE0;
    				String rrupp1 = null;
    				if(!StringUtil.isEmpty(rd.getRuleReasonUrl1())
    						&&rd.getRuleReasonUrl1().startsWith("http")
    						&&( rd.getRuleReasonUrl1().endsWith(".doc")
    							||rd.getRuleReasonUrl1().endsWith(".docx")
    							||rd.getRuleReasonUrl1().endsWith(".ppt")
    							||rd.getRuleReasonUrl1().endsWith(".pptx")
    							||rd.getRuleReasonUrl1().endsWith(".xls")
    							||rd.getRuleReasonUrl1().endsWith(".xlsx") )){
    					rrupp1 = Office2PdfUtil.S3Office2PDF(rd.getRuleReasonUrl1(), converterTempDir + "/", S3ConverterFileDir);
    					if(rrupp1 != null){
    						rrups1 = Constant.ENCLOSURE_PDF_STATE1;
    						rrupp1 =amazonS3Url + rrupp1;
    					}else{
    						rrupp1 = Office2PdfUtil.S3Office2PDF(rd.getRuleReasonUrl1(), converterTempDir + "/", S3ConverterFileDir);
    						if(rrupp1 != null){
    							rrups1 = Constant.ENCLOSURE_PDF_STATE1;
    							rrupp1 =amazonS3Url + rrupp1;
    						}else{
    							rrups1 = Constant.ENCLOSURE_PDF_STATE2;
    						}
    					}
    				}
    				rd.setRuleReasonUrl1Pdf(rrupp1);
    				rd.setRuleReasonPdf1State(rrups1);
    				
    				/*不为空，而且以http开头*/
    				String rrups2 = Constant.ENCLOSURE_PDF_STATE0;
    				String rrupp2 = null;
    				if(!StringUtil.isEmpty(rd.getRuleReasonUrl2())
    						&&rd.getRuleReasonUrl2().startsWith("http")
    						&&( rd.getRuleReasonUrl2().endsWith(".doc")
    							||rd.getRuleReasonUrl2().endsWith(".docx")
    							||rd.getRuleReasonUrl2().endsWith(".ppt")
    							||rd.getRuleReasonUrl2().endsWith(".pptx")
    							||rd.getRuleReasonUrl2().endsWith(".xls")
    							||rd.getRuleReasonUrl2().endsWith(".xlsx") )){
    					rrupp2 = Office2PdfUtil.S3Office2PDF(rd.getRuleReasonUrl2(), converterTempDir + "/", S3ConverterFileDir);
    					if(rrupp2 != null){
    						rrups2 = Constant.ENCLOSURE_PDF_STATE1;
    						rrupp2 =amazonS3Url + rrupp2;
    					}else{
    						rrupp2 = Office2PdfUtil.S3Office2PDF(rd.getRuleReasonUrl2(), converterTempDir + "/", S3ConverterFileDir);
    						if(rrupp2 != null){
    							rrups2 = Constant.ENCLOSURE_PDF_STATE1;
    							rrupp2 =amazonS3Url + rrupp2;
    						}else{
    							rrups2 = Constant.ENCLOSURE_PDF_STATE2;
    						}
    					}
    				}
    				rd.setRuleReasonUrl2Pdf(rrupp2);
    				rd.setRuleReasonPdf2State(rrups2);
    				
    				/*不为空，而且以http开头*/
    				String rrups3 = Constant.ENCLOSURE_PDF_STATE0;
    				String rrupp3 = null;
    				if(!StringUtil.isEmpty(rd.getRuleReasonUrl3())
    						&&rd.getRuleReasonUrl3().startsWith("http")
    						&&( rd.getRuleReasonUrl3().endsWith(".doc")
    							||rd.getRuleReasonUrl3().endsWith(".docx")
    							||rd.getRuleReasonUrl3().endsWith(".ppt")
    							||rd.getRuleReasonUrl3().endsWith(".pptx")
    							||rd.getRuleReasonUrl3().endsWith(".xls")
    							||rd.getRuleReasonUrl3().endsWith(".xlsx") )){
    					rrupp3 = Office2PdfUtil.S3Office2PDF(rd.getRuleReasonUrl3(), converterTempDir + "/", S3ConverterFileDir);
    					if(rrupp3 != null){
    						rrups3 = Constant.ENCLOSURE_PDF_STATE1;
    						rrupp3 =amazonS3Url + rrupp3;
    					}else{
    						rrupp3 = Office2PdfUtil.S3Office2PDF(rd.getRuleReasonUrl3(), converterTempDir + "/", S3ConverterFileDir);
    						if(rrupp3 != null){
    							rrups3 = Constant.ENCLOSURE_PDF_STATE1;
    							rrupp3 =amazonS3Url + rrupp3;
    						}else{
    							rrups3 = Constant.ENCLOSURE_PDF_STATE2;
    						}
    					}
    				}
    				rd.setRuleReasonUrl3Pdf(rrupp3);
    				rd.setRuleReasonPdf3State(rrups3);
    				
    				/*不为空，而且以http开头*/
    				String rrups4 = Constant.ENCLOSURE_PDF_STATE0;
    				String rrupp4 = null;
    				if(!StringUtil.isEmpty(rd.getRuleReasonUrl4())
    						&&rd.getRuleReasonUrl4().startsWith("http")
    						&&( rd.getRuleReasonUrl4().endsWith(".doc")
    							||rd.getRuleReasonUrl4().endsWith(".docx")
    							||rd.getRuleReasonUrl4().endsWith(".ppt")
    							||rd.getRuleReasonUrl4().endsWith(".pptx")
    							||rd.getRuleReasonUrl4().endsWith(".xls")
    							||rd.getRuleReasonUrl4().endsWith(".xlsx") )){
    					rrupp4 = Office2PdfUtil.S3Office2PDF(rd.getRuleReasonUrl4(), converterTempDir + "/", S3ConverterFileDir);
    					if(rrupp4 != null){
    						rrups4 = Constant.ENCLOSURE_PDF_STATE1;
    						rrupp4 =amazonS3Url + rrupp4;
    					}else{
    						rrupp4 = Office2PdfUtil.S3Office2PDF(rd.getRuleReasonUrl4(), converterTempDir + "/", S3ConverterFileDir);
    						if(rrupp4 != null){
    							rrups4 = Constant.ENCLOSURE_PDF_STATE1;
    							rrupp4 =amazonS3Url + rrupp4;
    						}else{
    							rrups4 = Constant.ENCLOSURE_PDF_STATE2;
    						}
    					}
    				}
    				rd.setRuleReasonUrl4Pdf(rrupp4);
    				rd.setRuleReasonPdf4State(rrups4);

    				/*不为空，而且以http开头*/
    				String rrups5 = Constant.ENCLOSURE_PDF_STATE0;
    				String rrupp5 = null;
    				if(!StringUtil.isEmpty(rd.getRuleReasonUrl5())
    						&&rd.getRuleReasonUrl5().startsWith("http")
    						&&( rd.getRuleReasonUrl5().endsWith(".doc")
    							||rd.getRuleReasonUrl5().endsWith(".docx")
    							||rd.getRuleReasonUrl5().endsWith(".ppt")
    							||rd.getRuleReasonUrl5().endsWith(".pptx")
    							||rd.getRuleReasonUrl5().endsWith(".xls")
    							||rd.getRuleReasonUrl5().endsWith(".xlsx") )){
    					rrupp5 = Office2PdfUtil.S3Office2PDF(rd.getRuleReasonUrl5(), converterTempDir + "/", S3ConverterFileDir);
    					if(rrupp5 != null){
    						rrups5 = Constant.ENCLOSURE_PDF_STATE1;
    						rrupp5 =amazonS3Url + rrupp5;
    					}else{
    						rrupp5 = Office2PdfUtil.S3Office2PDF(rd.getRuleReasonUrl5(), converterTempDir + "/", S3ConverterFileDir);
    						if(rrupp5 != null){
    							rrups5 = Constant.ENCLOSURE_PDF_STATE1;
    							rrupp5 =amazonS3Url + rrupp5;
    						}else{
    							rrups5 = Constant.ENCLOSURE_PDF_STATE2;
    						}
    					}
    				}
    				rd.setRuleReasonUrl5Pdf(rrupp5);
    				rd.setRuleReasonPdf5State(rrups5);
    				
    				
    				
    				/*不为空，而且以http开头*/
    				String rups1 = Constant.ENCLOSURE_PDF_STATE0;
    				String rupp1 = null;
    				if(!StringUtil.isEmpty(rd.getResultUrl1())
    						&&rd.getResultUrl1().startsWith("http")
    						&&( rd.getResultUrl1().endsWith(".doc")
    							||rd.getResultUrl1().endsWith(".docx")
    							||rd.getResultUrl1().endsWith(".ppt")
    							||rd.getResultUrl1().endsWith(".pptx")
    							||rd.getResultUrl1().endsWith(".xls")
    							||rd.getResultUrl1().endsWith(".xlsx") )){
    					rupp1 = Office2PdfUtil.S3Office2PDF(rd.getResultUrl1(), converterTempDir + "/", S3ConverterFileDir);
    					if(rupp1 != null){
    						rups1 = Constant.ENCLOSURE_PDF_STATE1;
    						rupp1 =amazonS3Url + rupp1;
    					}else{
    						rupp1 = Office2PdfUtil.S3Office2PDF(rd.getResultUrl1(), converterTempDir + "/", S3ConverterFileDir);
    						if(rupp1 != null){
    							rups1 = Constant.ENCLOSURE_PDF_STATE1;
    							rupp1 =amazonS3Url + rupp1;
    						}else{
    							rups1 = Constant.ENCLOSURE_PDF_STATE2;
    						}
    					}
    				}
    				rd.setResultUrl1Pdf(rupp1);
    				rd.setResultPdf1State(rups1);
    				
    				/*不为空，而且以http开头*/
    				String rups2 = Constant.ENCLOSURE_PDF_STATE0;
    				String rupp2 = null;
    				if(!StringUtil.isEmpty(rd.getResultUrl2())
    						&&rd.getResultUrl2().startsWith("http")
    						&&( rd.getResultUrl2().endsWith(".doc")
    							||rd.getResultUrl2().endsWith(".docx")
    							||rd.getResultUrl2().endsWith(".ppt")
    							||rd.getResultUrl2().endsWith(".pptx")
    							||rd.getResultUrl2().endsWith(".xls")
    							||rd.getResultUrl2().endsWith(".xlsx") )){
    					rupp2 = Office2PdfUtil.S3Office2PDF(rd.getResultUrl2(), converterTempDir + "/", S3ConverterFileDir);
    					if(rupp2 != null){
    						rups2 = Constant.ENCLOSURE_PDF_STATE1;
    						rupp2 =amazonS3Url + rupp2;
    					}else{
    						rupp2 = Office2PdfUtil.S3Office2PDF(rd.getResultUrl2(), converterTempDir + "/", S3ConverterFileDir);
    						if(rupp2 != null){
    							rups2 = Constant.ENCLOSURE_PDF_STATE1;
    							rupp2 =amazonS3Url + rupp2;
    						}else{
    							rups2 = Constant.ENCLOSURE_PDF_STATE2;
    						}
    					}
    				}
    				rd.setResultUrl2Pdf(rupp2);
    				rd.setResultPdf2State(rups2);
    				

    				/*不为空，而且以http开头*/
    				String rups3 = Constant.ENCLOSURE_PDF_STATE0;
    				String rupp3 = null;
    				if(!StringUtil.isEmpty(rd.getResultUrl3())
    						&&rd.getResultUrl3().startsWith("http")
    						&&( rd.getResultUrl3().endsWith(".doc")
    							||rd.getResultUrl3().endsWith(".docx")
    							||rd.getResultUrl3().endsWith(".ppt")
    							||rd.getResultUrl3().endsWith(".pptx")
    							||rd.getResultUrl3().endsWith(".xls")
    							||rd.getResultUrl3().endsWith(".xlsx") )){
    					rupp3 = Office2PdfUtil.S3Office2PDF(rd.getResultUrl3(), converterTempDir + "/", S3ConverterFileDir);
    					if(rupp3 != null){
    						rups3 = Constant.ENCLOSURE_PDF_STATE1;
    						rupp3 =amazonS3Url + rupp3;
    					}else{
    						rupp3 = Office2PdfUtil.S3Office2PDF(rd.getResultUrl3(), converterTempDir + "/", S3ConverterFileDir);
    						if(rupp3 != null){
    							rups3 = Constant.ENCLOSURE_PDF_STATE1;
    							rupp3 =amazonS3Url + rupp3;
    						}else{
    							rups3 = Constant.ENCLOSURE_PDF_STATE2;
    						}
    					}
    				}
    				rd.setResultUrl3Pdf(rupp3);
    				rd.setResultPdf3State(rups3);
    				
    				/*不为空，而且以http开头*/
    				String rups4 = Constant.ENCLOSURE_PDF_STATE0;
    				String rupp4 = null;
    				if(!StringUtil.isEmpty(rd.getResultUrl4())
    						&&rd.getResultUrl4().startsWith("http")
    						&&( rd.getResultUrl4().endsWith(".doc")
    							||rd.getResultUrl4().endsWith(".docx")
    							||rd.getResultUrl4().endsWith(".ppt")
    							||rd.getResultUrl4().endsWith(".pptx")
    							||rd.getResultUrl4().endsWith(".xls")
    							||rd.getResultUrl4().endsWith(".xlsx") )){
    					rupp4 = Office2PdfUtil.S3Office2PDF(rd.getResultUrl4(), converterTempDir + "/", S3ConverterFileDir);
    					if(rupp4 != null){
    						rups4 = Constant.ENCLOSURE_PDF_STATE1;
    						rupp4 =amazonS3Url + rupp4;
    					}else{
    						rupp4 = Office2PdfUtil.S3Office2PDF(rd.getResultUrl4(), converterTempDir + "/", S3ConverterFileDir);
    						if(rupp4 != null){
    							rups4 = Constant.ENCLOSURE_PDF_STATE1;
    							rupp4 =amazonS3Url + rupp4;
    						}else{
    							rups4 = Constant.ENCLOSURE_PDF_STATE2;
    						}
    					}
    				}
    				rd.setResultUrl4Pdf(rupp4);
    				rd.setResultPdf4State(rups4);
    				
    				/*不为空，而且以http开头*/
    				String rups5 = Constant.ENCLOSURE_PDF_STATE0;
    				String rupp5 = null;
    				if(!StringUtil.isEmpty(rd.getResultUrl5())
    						&&rd.getResultUrl5().startsWith("http")
    						&&( rd.getResultUrl5().endsWith(".doc")
    							||rd.getResultUrl5().endsWith(".docx")
    							||rd.getResultUrl5().endsWith(".ppt")
    							||rd.getResultUrl5().endsWith(".pptx")
    							||rd.getResultUrl5().endsWith(".xls")
    							||rd.getResultUrl5().endsWith(".xlsx") )){
    					rupp5 = Office2PdfUtil.S3Office2PDF(rd.getResultUrl5(), converterTempDir + "/", S3ConverterFileDir);
    					if(rupp5 != null){
    						rups5 = Constant.ENCLOSURE_PDF_STATE1;
    						rupp5 =amazonS3Url + rupp5;
    					}else{
    						rupp5 = Office2PdfUtil.S3Office2PDF(rd.getResultUrl5(), converterTempDir + "/", S3ConverterFileDir);
    						if(rupp5 != null){
    							rups5 = Constant.ENCLOSURE_PDF_STATE1;
    							rupp5 =amazonS3Url + rupp5;
    						}else{
    							rups5 = Constant.ENCLOSURE_PDF_STATE2;
    						}
    					}
    				}
    				rd.setResultUrl5Pdf(rupp5);
    				rd.setResultPdf5State(rups5);
    
    				try {
    					rdDao.modifyECS(rd);
					} catch (Exception e) {
						e.printStackTrace();
					}
    				
        		}
        	}
        	/*================== ReceiveDemand Start===========================*/
        	
        	
        	/*================== Education Start===========================*/
        	EducationDao educationDao = (EducationDao) SpringContextUtils.getBean("educationDao");
        	List<Education> educations = null;
        	
        	try {
        		educations = educationDao.getByECS();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	if(educations != null){
        		for(Education education : educations){
        			//附件1
    				/*不为空，而且以http开头*/
    				String eeps = Constant.ENCLOSURE_PDF_STATE0;
    				String eepp = null;
    				if(!StringUtil.isEmpty(education.getEnclosure())
    						&&education.getEnclosure().startsWith("http")
    						&&( education.getEnclosure().endsWith(".doc")||
    							education.getEnclosure().endsWith(".docx")||
    							education.getEnclosure().endsWith(".ppt")||
    							education.getEnclosure().endsWith(".pptx")||
    							education.getEnclosure().endsWith(".xls")||
    							education.getEnclosure().endsWith(".xlsx") )){
    					eepp = Office2PdfUtil.S3Office2PDF(education.getEnclosure(), converterTempDir + "/", S3ConverterFileDir);
    					if(eepp != null){
    						eeps = Constant.ENCLOSURE_PDF_STATE1;
    						eepp =amazonS3Url + eepp;
    					}else{
    						eepp = Office2PdfUtil.S3Office2PDF(education.getEnclosure(), converterTempDir + "/", S3ConverterFileDir);
    						if(eepp != null){
    							eeps = Constant.ENCLOSURE_PDF_STATE1;
    							eepp =amazonS3Url + eepp;
    						}else{
    							eeps = Constant.ENCLOSURE_PDF_STATE2;
    						}
    					}
    				}
    				
    				try {
    					educationDao.modifyECS(education.getId(), eepp, eeps);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    				
        		}
        	}
        	
        	/*================== Education End===========================*/
        	
        	/*================== Work Experience Start===========================*/
        	WorkExperienceDao workExperienceDao = (WorkExperienceDao) SpringContextUtils.getBean("workExperienceDao");
        	List<WorkExperience> workExperiences = null;
        	
        	try {
        		workExperiences = workExperienceDao.getByECS();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	if(workExperiences != null){
        		for(WorkExperience workExperience : workExperiences){
        			//附件1
    				/*不为空，而且以http开头*/
    				String eeps = Constant.ENCLOSURE_PDF_STATE0;
    				String eepp = null;
    				if(!StringUtil.isEmpty(workExperience.getEnclosure())
    						&&workExperience.getEnclosure().startsWith("http")
    						&&( workExperience.getEnclosure().endsWith(".doc")||
    							workExperience.getEnclosure().endsWith(".docx")||
    							workExperience.getEnclosure().endsWith(".ppt")||
    							workExperience.getEnclosure().endsWith(".pptx")||
    							workExperience.getEnclosure().endsWith(".xls")||
    							workExperience.getEnclosure().endsWith(".xlsx") )){
    					eepp = Office2PdfUtil.S3Office2PDF(workExperience.getEnclosure(), converterTempDir + "/", S3ConverterFileDir);
    					if(eepp != null){
    						eeps = Constant.ENCLOSURE_PDF_STATE1;
    						eepp =amazonS3Url + eepp;
    					}else{
    						eepp = Office2PdfUtil.S3Office2PDF(workExperience.getEnclosure(), converterTempDir + "/", S3ConverterFileDir);
    						if(eepp != null){
    							eeps = Constant.ENCLOSURE_PDF_STATE1;
    							eepp =amazonS3Url + eepp;
    						}else{
    							eeps = Constant.ENCLOSURE_PDF_STATE2;
    						}
    					}
    				}
    				
    				try {
    					workExperienceDao.modifyECS(workExperience.getId(), eepp, eeps);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    				
        		}
        	}
        	
        	/*================== Work Experience End===========================*/
        	
        	
        	
        	/*================== Works Start===========================*/
        	WorksDao worksDao = (WorksDao) SpringContextUtils.getBean("worksDao");
        	List<Works> worksList = null;
        	
        	try {
        		worksList = worksDao.getByECS();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	if(worksList != null){
        		for(Works works : worksList){
        			//附件1
    				/*不为空，而且以http开头*/
    				String ups = Constant.ENCLOSURE_PDF_STATE0;
    				String upp = null;
    				if(!StringUtil.isEmpty(works.getUrl())
    						&&works.getUrl().startsWith("http")
    						&&( works.getUrl().endsWith(".doc")||
    								works.getUrl().endsWith(".docx")||
    								works.getUrl().endsWith(".ppt")||
    								works.getUrl().endsWith(".pptx")||
    								works.getUrl().endsWith(".xls")||
    								works.getUrl().endsWith(".xlsx") )){
    					upp = Office2PdfUtil.S3Office2PDF(works.getUrl(), converterTempDir + "/", S3ConverterFileDir);
    					if(upp != null){
    						ups = Constant.ENCLOSURE_PDF_STATE1;
    						upp =amazonS3Url + upp;
    					}else{
    						upp = Office2PdfUtil.S3Office2PDF(works.getUrl(), converterTempDir + "/", S3ConverterFileDir);
    						if(upp != null){
    							ups = Constant.ENCLOSURE_PDF_STATE1;
    							upp =amazonS3Url + upp;
    						}else{
    							ups = Constant.ENCLOSURE_PDF_STATE2;
    						}
    					}
    				}
    				
    				try {
    					worksDao.modifyECS(works.getId(), upp, ups);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    				
        		}
        	}
        	
        	/*================== Works End===========================*/
        	
        	
        	
        	/*================== ProUser Start===========================*/
        	ProUserDao proUserDao = (ProUserDao) SpringContextUtils.getBean("proUserDao");
        	List<ProUser> proUsers = null;
        	
        	try {
        		proUsers = proUserDao.getByECS();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	if(proUsers != null){
        		for(ProUser proUser : proUsers){
    				/*ip upload 不为空，而且以http开头的office*/
    				String pidps = Constant.ENCLOSURE_PDF_STATE0;
    				String pidp = null;
    				if(!StringUtil.isEmpty(proUser.getIdUpload())
    						&&proUser.getIdUpload().startsWith("http")
    						&&( proUser.getIdUpload().endsWith(".doc")||
    								proUser.getIdUpload().endsWith(".docx")||
    								proUser.getIdUpload().endsWith(".ppt")||
    								proUser.getIdUpload().endsWith(".pptx")||
    								proUser.getIdUpload().endsWith(".xls")||
    								proUser.getIdUpload().endsWith(".xlsx") )){
    					pidp = Office2PdfUtil.S3Office2PDF(proUser.getIdUpload(), converterTempDir + "/", S3ConverterFileDir);
    					if(pidp != null){
    						pidps = Constant.ENCLOSURE_PDF_STATE1;
    						pidp =amazonS3Url + pidp;
    					}else{
    						pidp = Office2PdfUtil.S3Office2PDF(proUser.getIdUpload(), converterTempDir + "/", S3ConverterFileDir);
    						if(pidp != null){
    							pidps = Constant.ENCLOSURE_PDF_STATE1;
    							pidp =amazonS3Url + pidp;
    						}else{
    							pidps = Constant.ENCLOSURE_PDF_STATE2;
    						}
    					}
    				}
    				
    				/*education upload 不为空，而且以http开头的office*/
    				String peps = Constant.ENCLOSURE_PDF_STATE0;
    				String pep = null;
    				if(!StringUtil.isEmpty(proUser.getEducationUpload())
    						&&proUser.getEducationUpload().startsWith("http")
    						&&( proUser.getEducationUpload().endsWith(".doc")||
    								proUser.getEducationUpload().endsWith(".docx")||
    								proUser.getEducationUpload().endsWith(".ppt")||
    								proUser.getEducationUpload().endsWith(".pptx")||
    								proUser.getEducationUpload().endsWith(".xls")||
    								proUser.getEducationUpload().endsWith(".xlsx") )){
    					pep = Office2PdfUtil.S3Office2PDF(proUser.getEducationUpload(), converterTempDir + "/", S3ConverterFileDir);
    					if(pep != null){
    						peps = Constant.ENCLOSURE_PDF_STATE1;
    						pep =amazonS3Url + pep;
    					}else{
    						pep = Office2PdfUtil.S3Office2PDF(proUser.getEducationUpload(), converterTempDir + "/", S3ConverterFileDir);
    						if(pep != null){
    							peps = Constant.ENCLOSURE_PDF_STATE1;
    							pep =amazonS3Url + pep;
    						}else{
    							peps = Constant.ENCLOSURE_PDF_STATE2;
    						}
    					}
    				}
    				
    				/*other upload 不为空，而且以http开头的office*/
    				String pops = Constant.ENCLOSURE_PDF_STATE0;
    				String pop = null;
    				if(!StringUtil.isEmpty(proUser.getOtherUpload())
    						&&proUser.getOtherUpload().startsWith("http")
    						&&( proUser.getOtherUpload().endsWith(".doc")||
    								proUser.getOtherUpload().endsWith(".docx")||
    								proUser.getOtherUpload().endsWith(".ppt")||
    								proUser.getOtherUpload().endsWith(".pptx")||
    								proUser.getOtherUpload().endsWith(".xls")||
    								proUser.getOtherUpload().endsWith(".xlsx") )){
    					pop = Office2PdfUtil.S3Office2PDF(proUser.getOtherUpload(), converterTempDir + "/", S3ConverterFileDir);
    					if(pop != null){
    						pops = Constant.ENCLOSURE_PDF_STATE1;
    						pop =amazonS3Url + pop;
    					}else{
    						pop = Office2PdfUtil.S3Office2PDF(proUser.getOtherUpload(), converterTempDir + "/", S3ConverterFileDir);
    						if(pop != null){
    							pops = Constant.ENCLOSURE_PDF_STATE1;
    							pop =amazonS3Url + pop;
    						}else{
    							pops = Constant.ENCLOSURE_PDF_STATE2;
    						}
    					}
    				}
    				
    				try {
    					proUserDao.modifyECS(proUser.getId(), pidp, pidps, pep, peps, pop, pops);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    				
        		}
        	}
        	
        	/*================== ProUser End===========================*/
        	
        	
        	
        	
        	
        	/*================== Specialty User Start===========================*/
        	SpecialtyUserDao specialtyUserDao = (SpecialtyUserDao) SpringContextUtils.getBean("specialtyUserDao");
        	List<SpecialtyUser> specialtyUsers = null;
        	
        	try {
        		specialtyUsers = specialtyUserDao.getByECS();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	if(specialtyUsers != null){
        		for(SpecialtyUser specialtyUser : specialtyUsers){
        			//附件1
    				/*不为空，而且以http开头*/
    				String deps1 = Constant.ENCLOSURE_PDF_STATE0;
    				String dep1 = null;
    				if(!StringUtil.isEmpty(specialtyUser.getEnclosure1())
    						&&specialtyUser.getEnclosure1().startsWith("http")
    						&&( specialtyUser.getEnclosure1().endsWith(".doc")||
    							specialtyUser.getEnclosure1().endsWith(".docx")||
    							specialtyUser.getEnclosure1().endsWith(".ppt")||
    							specialtyUser.getEnclosure1().endsWith(".pptx")||
    							specialtyUser.getEnclosure1().endsWith(".xls")||
    							specialtyUser.getEnclosure1().endsWith(".xlsx") )){
    					dep1 = Office2PdfUtil.S3Office2PDF(specialtyUser.getEnclosure1(), converterTempDir + "/", S3ConverterFileDir);
    					if(dep1 != null){
    						deps1 = Constant.ENCLOSURE_PDF_STATE1;
    						dep1 =amazonS3Url + dep1;
    					}else{
    						dep1 = Office2PdfUtil.S3Office2PDF(specialtyUser.getEnclosure1(), converterTempDir + "/", S3ConverterFileDir);
    						if(dep1 != null){
    							deps1 = Constant.ENCLOSURE_PDF_STATE1;
    							dep1 =amazonS3Url + dep1;
    						}else{
    							deps1 = Constant.ENCLOSURE_PDF_STATE2;
    						}
    					}
    				}
    				
    				
    				//附件2
    				/*不为空，而且以http开头*/
    				String deps2 = Constant.ENCLOSURE_PDF_STATE0;
    				String dep2 = null;
    				if(!StringUtil.isEmpty(specialtyUser.getEnclosure2())
    						&&specialtyUser.getEnclosure1().startsWith("http")
    						&&( specialtyUser.getEnclosure1().endsWith(".doc")||
        							specialtyUser.getEnclosure1().endsWith(".docx")||
        							specialtyUser.getEnclosure1().endsWith(".ppt")||
        							specialtyUser.getEnclosure1().endsWith(".pptx")||
        							specialtyUser.getEnclosure1().endsWith(".xls")||
        							specialtyUser.getEnclosure1().endsWith(".xlsx") )){
    					dep2 = Office2PdfUtil.S3Office2PDF(specialtyUser.getEnclosure2(), converterTempDir + "/", S3ConverterFileDir);
    					if(dep2 != null){
    						deps2 = Constant.ENCLOSURE_PDF_STATE1;
    						dep2 =amazonS3Url + dep2;
    					}else{
    						dep2 = Office2PdfUtil.S3Office2PDF(specialtyUser.getEnclosure2(), converterTempDir + "/", S3ConverterFileDir);
    						if(dep2 != null){
    							deps2 = Constant.ENCLOSURE_PDF_STATE1;
    							dep2 =amazonS3Url + dep2;
    						}else{
    							deps2 = Constant.ENCLOSURE_PDF_STATE2;
    						}
    					}
    				}
    				
    				//附件3
    				/*不为空，而且以http开头*/
    				String deps3 = Constant.ENCLOSURE_PDF_STATE0;
    				String dep3 = null;
    				if(!StringUtil.isEmpty(specialtyUser.getEnclosure3())
    						&&specialtyUser.getEnclosure1().startsWith("http")
    						&&( specialtyUser.getEnclosure1().endsWith(".doc")||
        							specialtyUser.getEnclosure1().endsWith(".docx")||
        							specialtyUser.getEnclosure1().endsWith(".ppt")||
        							specialtyUser.getEnclosure1().endsWith(".pptx")||
        							specialtyUser.getEnclosure1().endsWith(".xls")||
        							specialtyUser.getEnclosure1().endsWith(".xlsx") )){
    					dep3 = Office2PdfUtil.S3Office2PDF(specialtyUser.getEnclosure3(), converterTempDir + "/", S3ConverterFileDir);
    					if(dep3 != null){
    						deps3 = Constant.ENCLOSURE_PDF_STATE1;
    						dep3 =amazonS3Url + dep3;
    					}else{
    						dep3 = Office2PdfUtil.S3Office2PDF(specialtyUser.getEnclosure3(), converterTempDir + "/", S3ConverterFileDir);
    						if(dep3 != null){
    							deps3 = Constant.ENCLOSURE_PDF_STATE1;
    							dep3 =amazonS3Url + dep3;
    						}else{
    							deps3 = Constant.ENCLOSURE_PDF_STATE2;
    						}
    					}
    				}
    				
    				//附件4
    				/*不为空，而且以http开头*/
    				String deps4 = Constant.ENCLOSURE_PDF_STATE0;
    				String dep4 = null;
    				if(!StringUtil.isEmpty(specialtyUser.getEnclosure4())
    						&&specialtyUser.getEnclosure1().startsWith("http")
    						&&( specialtyUser.getEnclosure1().endsWith(".doc")||
        							specialtyUser.getEnclosure1().endsWith(".docx")||
        							specialtyUser.getEnclosure1().endsWith(".ppt")||
        							specialtyUser.getEnclosure1().endsWith(".pptx")||
        							specialtyUser.getEnclosure1().endsWith(".xls")||
        							specialtyUser.getEnclosure1().endsWith(".xlsx") )){
    					dep4 = Office2PdfUtil.S3Office2PDF(specialtyUser.getEnclosure4(), converterTempDir + "/", S3ConverterFileDir);
    					if(dep4 != null){
    						deps4 = Constant.ENCLOSURE_PDF_STATE1;
    						dep4 =amazonS3Url + dep4;
    					}else{
    						dep4 = Office2PdfUtil.S3Office2PDF(specialtyUser.getEnclosure4(), converterTempDir + "/", S3ConverterFileDir);
    						if(dep4 != null){
    							deps4 = Constant.ENCLOSURE_PDF_STATE1;
    							dep4 =amazonS3Url + dep4;
    						}else{
    							deps4 = Constant.ENCLOSURE_PDF_STATE2;
    						}
    					}
    				}
    				
    				//附件5
    				/*不为空，而且以http开头*/
    				String deps5 = Constant.ENCLOSURE_PDF_STATE0;
    				String dep5 = null;
    				if(!StringUtil.isEmpty(specialtyUser.getEnclosure5())
    						&&specialtyUser.getEnclosure1().startsWith("http")
    						&&( specialtyUser.getEnclosure1().endsWith(".doc")||
        							specialtyUser.getEnclosure1().endsWith(".docx")||
        							specialtyUser.getEnclosure1().endsWith(".ppt")||
        							specialtyUser.getEnclosure1().endsWith(".pptx")||
        							specialtyUser.getEnclosure1().endsWith(".xls")||
        							specialtyUser.getEnclosure1().endsWith(".xlsx") )){
    					dep5 = Office2PdfUtil.S3Office2PDF(specialtyUser.getEnclosure5(), converterTempDir + "/", S3ConverterFileDir);
    					if(dep5 != null){
    						deps5 = Constant.ENCLOSURE_PDF_STATE1;
    						dep5 =amazonS3Url + dep5;
    					}else{
    						dep5 = Office2PdfUtil.S3Office2PDF(specialtyUser.getEnclosure5(), converterTempDir + "/", S3ConverterFileDir);
    						if(dep5 != null){
    							deps5 = Constant.ENCLOSURE_PDF_STATE1;
    							dep5 =amazonS3Url + dep5;
    						}else{
    							deps5 = Constant.ENCLOSURE_PDF_STATE2;
    						}
    					}
    				} 
    				
    				try {
						specialtyUserDao.modifyECS(specialtyUser.getId(), dep1, dep2, dep3, dep4, dep5, deps1, deps2, deps3, deps4, deps5);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    				
        		}
        	}
        	
        	/*================== Specialty User End===========================*/
        	
        	
        	
        	
        	try {
    			Thread.sleep(sleepTime);
    		} catch (InterruptedException e) {
    		}//每隔20000ms执行一次
        }
        

	}
}  

