package com.tallstak.bootsmtp.server;

import com.tallstak.bootsmtp.ApplicationConfiguration;

import javax.mail.*;
import javax.mail.internet.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Saves and deletes emails. Will save the email to a file if a path is configured in EmailConfig
 */
@Component
public final class MailSaver {

	private ApplicationConfiguration appConfig;
	private EmailRepository emailRepository;
	private Session session;

	@Autowired
	public MailSaver(ApplicationConfiguration applicationConfiguration, EmailRepository emailRepository) {
		this.appConfig = applicationConfiguration;
		this.emailRepository = emailRepository;
	}


	private static final Logger log = LoggerFactory.getLogger(MailSaver.class);
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	// This can be a static variable since it is Thread Safe
	private static final Pattern SUBJECT_PATTERN = Pattern.compile("^Subject: (.*)$");

	private final SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyhhmmssSSS");

	/**
	 * Saves incoming email in file system and notifies observers.
	 *
	 * @param from the user who send the email.
	 * @param to the recipient of the email.
	 * @param data an InputStream object containing the email.
	 */
	public void saveEmail(InputStream data) throws IOException {
		try {
			MimeMessage message = new MimeMessage(null, data);

			Email email = new Email();
			email.setEmailStr(message.toString());

			String receivedDateString = message.getReceivedDate() != null ? message.getReceivedDate().toString() : "";
			email.setDateReceived(receivedDateString);

			email.setSubject(message.getSubject());
			email.setTo(message.getReplyTo()[0].toString());
			email.setWhoFrom(message.getFrom()[0].toString());

			String messageString = "";
			if (message.getContent() instanceof MimeMultipart) {
				MimeMultipart messageContent = (MimeMultipart) message.getContent();
				for (int i = 0; i < messageContent.getCount(); i++) {
					BodyPart nextBodyPart = messageContent.getBodyPart(i);
					InputStream bpInputStream = nextBodyPart.getInputStream();
					Scanner s = new java.util.Scanner(bpInputStream).useDelimiter("\\A");
					while (s.hasNext()) {
						messageString += s.next();
					}
				}
			}
			email.setEmailStr(messageString);

			emailRepository.save(email);
		} catch (MessagingException e) {
			log.warn(e.toString());
		}
	}

	/**
	 * Converts an {@code InputStream} into a {@code String} object.
	 * <p>
	 * The method will not copy the first 4 lines of the input stream.<br>
	 * These 4 lines are SubEtha SMTP additional information.
	 * </p>
	 *
	 * @param is the InputStream to be converted.
	 * @return the converted string object, containing data from the InputStream passed in parameters.
	 */
	private String convertStreamToString(InputStream is) {
		final long lineNbToStartCopy = 4; // Do not copy the first 4 lines (received part)
		BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		StringBuilder sb = new StringBuilder();

		String line;
		long lineNb = 0;
		try {
			while ((line = reader.readLine()) != null) {
				if (++lineNb > lineNbToStartCopy) {
					sb.append(line).append(LINE_SEPARATOR);
				}
			}
		} catch (IOException e) {
			log.error("", e);
		}
		return sb.toString();
	}

	/**
	 * Gets the subject from the email data passed in parameters.
	 *
	 * @param data a string representing the email content.
	 * @return the subject of the email, or an empty subject if not found.
	 */
	private String getSubjectFromStr(String data) {
		try {
			BufferedReader reader = new BufferedReader(new StringReader(data));

			String line;
			while ((line = reader.readLine()) != null) {
				 Matcher matcher = SUBJECT_PATTERN.matcher(line);
				 if (matcher.matches()) {
					 return matcher.group(1);
				 }
			}
		} catch (IOException e) {
			log.error("", e);
		}
		return "";
	}

	private String getContentFromStr(String data) {
		String content = null;
		return content;
	}
}
