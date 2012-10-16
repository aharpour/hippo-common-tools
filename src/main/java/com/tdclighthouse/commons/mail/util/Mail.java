package com.tdclighthouse.commons.mail.util;

/**
 * @author Ebrahim Aharpour
 * 
 */
public class Mail {
	private String subject;
	private String messageBody;
	private String htmlBody;
	private String[] attachments = new String[0];

	public Mail(String subject, String messageBody) {
		this.subject = subject;
		this.messageBody = messageBody;
	}

	public Mail(String subject, String messageBody, String htmlBody, String[] attachments) {
		this.subject = subject;
		this.messageBody = messageBody;
		this.attachments = attachments;
		this.htmlBody = htmlBody;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessageBody() {
		return messageBody;
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}

	public String[] getAttachments() {
		return attachments;
	}

	public void setAttachments(String[] attachments) {
		this.attachments = attachments;
	}

	public String getHtmlBody() {
		return htmlBody;
	}

	public void setHtmlBody(String htmlBody) {
		this.htmlBody = htmlBody;
	}

}
