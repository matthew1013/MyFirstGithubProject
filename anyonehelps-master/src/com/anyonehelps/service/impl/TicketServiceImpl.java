package com.anyonehelps.service.impl;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anyonehelps.common.constants.Constant;
import com.anyonehelps.common.constants.ResponseCode;
import com.anyonehelps.common.util.DateUtil;
import com.anyonehelps.common.util.PropertiesReader;
import com.anyonehelps.common.util.StringUtil;
import com.anyonehelps.common.util.sms.MailSendUtil;
import com.anyonehelps.dao.EmailSendDao;
import com.anyonehelps.dao.TicketDao;
import com.anyonehelps.exception.ServiceException;
import com.anyonehelps.model.EmailSend;
import com.anyonehelps.model.ResponseObject;
import com.anyonehelps.model.Ticket;
import com.anyonehelps.service.TicketService;

@Service("ticketService")
public class TicketServiceImpl extends BasicService implements TicketService {
	private static final Logger log = Logger.getLogger(TicketServiceImpl.class);
	
	@Autowired
	private TicketDao ticketDao;
	@Autowired
	private EmailSendDao emailSendDao;
	
	public ResponseObject<Object> addTicket(Ticket ticket) throws ServiceException {
		ResponseObject<Object> responseObj = new ResponseObject<Object>();
		
		try {
			String date = DateUtil.date2String(new Date());
			ticket.setCreateDate(date);
			int nResult = this.ticketDao.insert(ticket);
			if(nResult>0){
				responseObj.setCode(ResponseCode.SUCCESS_CODE);
			}else {
				//进行事务回滚
				throw new Exception();
			}
			MailSendUtil.sendTicketMsg(ticket);
			Properties prop = null;
			prop = PropertiesReader.read(Constant.EMAIL_PROPERTIES_FILE);
			String recipient = prop.getProperty("anyonehelps.ticket.email");
			String subject = prop.getProperty("anyonehelps.ticket.subject");
			String phone = "";
			if(!StringUtil.isEmpty(ticket.getTelphone())){
				phone = ",phone:"+ticket.getAreaCode()+ticket.getTelphone();
			}
				
			subject = MessageFormat.format(subject, new Object[] {ticket.getName(), ticket.getEmail(), phone, ticket.getId(), ticket.getSubject()});
			String content = prop.getProperty("anyonehelps.ticket.content");
			content = MessageFormat.format(content, new Object[] {ticket.getDescription()});
			EmailSend es = new EmailSend();
			es.setContent(content);
			es.setCreateDate(date);
			es.setEmail(recipient);
			es.setFailCount(Constant.EMAILSEND_FAIL_COUNT0);
			es.setModifyDate(date);
			es.setState(Constant.EMAILSEND_STATE0);
			es.setSubject(subject);
			es.setUserId("-1");
			int n = emailSendDao.insert(es);
			if(n>0){
				responseObj.setCode(ResponseCode.SUCCESS_CODE);
			}else {
				//进行事务回滚
				throw new Exception();
			}
		} catch (Exception e) {
			log.error("提交失败");
		}
		return responseObj;
	}
	
	
}
