package com.anyonehelps.common.util.sms;

import java.util.List;
import java.util.Properties;

import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SimpleMailSender {
	private transient Properties props = null;
	private transient MailAuthenticator authenticator;
	private transient Session session;

	public SimpleMailSender(final String userName, final String password, Properties props) {
		// get the host by the email account. most email support this type.
		final String smtHostName = "smtp." + userName.split("@")[1];
		this.props = props;
		init(smtHostName, userName, password);
	}

	/**
	 * 
	 * @param smtHostName
	 * @param userName
	 * @param password
	 */
	public SimpleMailSender(final String smtHostName, final String userName, final String password, Properties props) {
		this.props = props;
		init(smtHostName, userName, password);
	}

	/**
	 * 
	 * @param smtHostName
	 * @param userName
	 * @param password
	 */
	protected void init(String smtHostName, String userName, String password) {
		if (props == null) {
			props = System.getProperties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.host", smtHostName);
		} else if (!props.containsKey("mail.smtp.host")) {
			props.put("mail.smtp.host", smtHostName);
		}

		authenticator = new MailAuthenticator(userName, password);
		session = Session.getInstance(props, authenticator);
//		session.setDebug(true);
	}

	/**
	 * Send one emial to one person
	 * 
	 * @param recipient
	 *            person's email
	 * @param subject
	 *            the email subject
	 * @param content
	 *            the email content
	 * @return
	 * @throws MessagingException
	 */
	public boolean send(String recipient, String subject, Object content) throws MessagingException {
		final MimeMessage msg = new MimeMessage(session);
		// set the from user email address
		msg.setFrom(new InternetAddress(authenticator.getUserName()));
		// set the to user email address
		msg.setRecipient(RecipientType.TO, new InternetAddress(recipient));
		// set the subject of the meail
		msg.setSubject(subject, "utf-8");
		// set the content of the content
		msg.setContent(content.toString(), "text/html;charset=utf-8");
		Transport.send(msg);
		return true;
	}

	/**
	 * 
	 * @param recipients
	 * @param subject
	 * @param content
	 * @return
	 * @throws MessagingException
	 */
	public boolean send(List<String> recipients, String subject, Object content) throws MessagingException {
		final MimeMessage msg = new MimeMessage(session);
		// set the from user email address
		msg.setFrom(new InternetAddress(authenticator.getUserName()));
		final int num = recipients.size();
		InternetAddress[] addresses = new InternetAddress[num];
		for (int i = 0; i < num; i++) {
			addresses[i] = new InternetAddress(recipients.get(i));
		}
		msg.setRecipients(RecipientType.TO, addresses);
		// set the subject of the meail
		msg.setSubject(subject, "utf-8");
		// set the content of the content
		msg.setContent(content.toString(), "text/html;charset=utf-8");
		Transport.send(msg);
		return true;
	}
}
