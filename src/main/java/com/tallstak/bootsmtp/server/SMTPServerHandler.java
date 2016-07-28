package com.tallstak.bootsmtp.server;

import com.tallstak.bootsmtp.ApplicationConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.subethamail.smtp.helper.SimpleMessageListenerAdapter;
import org.subethamail.smtp.server.SMTPServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Starts and stops the SMTP server.
 */
@Component
public class SMTPServerHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(SMTPServerHandler.class);

	private final ApplicationConfiguration appConfig;
	private final MailSaver mailSaver;
	private final MailListener myListener;
	private final SMTPServer smtpServer;

	@Autowired
	public SMTPServerHandler(ApplicationConfiguration applicationConfiguration,
							 MailSaver mailSaver) {
		this.appConfig = applicationConfiguration;
		this.mailSaver = mailSaver;
		this.myListener = new MailListener(mailSaver);
		this.smtpServer = new SMTPServer(new SimpleMessageListenerAdapter(myListener), new SMTPAuthHandlerFactory());
	}

	/**
	 * Starts the server on the port and address configured in application.yml
	 */
	@PostConstruct
	public void startServer() {
		LOGGER.debug("Starting server on port {}", appConfig.getPort());

		smtpServer.setBindAddress(appConfig.getBindAddress());
		smtpServer.setPort(appConfig.getPort());
		smtpServer.start();
	}

	/**
	 * Stops the server.
	 */
	@PreDestroy
	public void stopServer() {
		if (smtpServer.isRunning()) {
			LOGGER.debug("Stopping server");
			smtpServer.stop();
		}
	}
}
