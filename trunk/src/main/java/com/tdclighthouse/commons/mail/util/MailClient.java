/*
 *  Copyright 2012 Finalist B.V.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.tdclighthouse.commons.mail.util;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang.StringUtils;

/**
 * @author Ebrahim Aharpour
 * 
 */
public class MailClient {

	private String mailServer;
	private int portNumber = -1;
	private Session session;

	public MailClient(String mailServer) {
		if (StringUtils.isBlank(mailServer)) {
			throw new IllegalArgumentException("In valid mailServer argument is provided");
		}
		this.mailServer = mailServer;
	}

	public MailClient(String mailServer, int portNumber) {
		if (StringUtils.isBlank(mailServer) && (portNumber < 0) && (portNumber > 65535)) {
			throw new IllegalArgumentException("In valid mailServer or portNumber");
		}
		this.mailServer = mailServer;
		this.portNumber = portNumber;
	}

	public MailClient(Session mailSession) {
		this.session = mailSession;
	}

	public void sendMail(String from, String[] to, Mail mail) throws MessagingException, AddressException {
		// a brief validation
		if ((from == null) || "".equals(from) || (to.length == 0) || (mail == null)) {
			throw new IllegalArgumentException();
		}
		Session session = getSession();

		// Define a new mail message
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		for (int i = 0; i < to.length; i++) {
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to[i]));
		}
		message.setSubject(mail.getSubject(), "utf-8");

		// use a MimeMultipart as we need to handle the file attachments
		Multipart multipart = new MimeMultipart("alternative");

		if ((mail.getMessageBody() != null) && !"".equals(mail.getMessageBody())) {
			// add the message body to the mime message
			BodyPart textPart = new MimeBodyPart();
			textPart.setContent(mail.getMessageBody(), "text/plain; charset=utf-8"); // sets type to "text/plain"
			multipart.addBodyPart(textPart);
		}

		if (mail.getHtmlBody() != null) {
			BodyPart pixPart = new MimeBodyPart();
			pixPart.setContent(mail.getHtmlBody(), "text/html; charset=utf-8");
			multipart.addBodyPart(pixPart);
		}

		// add any file attachments to the message
		addAtachments(mail.getAttachments(), multipart);

		// Put all message parts in the message
		message.setContent(multipart);

		// Send the message
		Transport.send(message);

	}

	private Session getSession() {
		if (session == null) {
			// Setup mail server
			Properties props = System.getProperties();
			props.put("mail.smtp.host", mailServer);
			if (portNumber > 0) {
				props.put("mail.smtp.port", portNumber);
			}

			// Get a mail session
			session = Session.getDefaultInstance(props, null);
		}
		return session;
	}

	protected void addAtachments(String[] attachments, Multipart multipart) throws MessagingException, AddressException {
		for (int i = 0; i < attachments.length; i++) {
			String filename = attachments[i];
			MimeBodyPart attachmentBodyPart = new MimeBodyPart();

			// use a JAF FileDataSource as it does MIME type detection
			DataSource source = new FileDataSource(filename);
			attachmentBodyPart.setDataHandler(new DataHandler(source));

			// assume that the filename you want to send is the same as the
			// actual file name - could alter this to remove the file path
			attachmentBodyPart.setFileName(filename);

			// add the attachment
			multipart.addBodyPart(attachmentBodyPart);
		}
	}

}
