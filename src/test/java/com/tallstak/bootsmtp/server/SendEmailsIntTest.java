package com.tallstak.bootsmtp.server;

import com.tallstak.bootsmtp.ApplicationConfiguration;
import com.tallstak.bootsmtp.BootSMTP;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(BootSMTP.class)
@WebIntegrationTest
public class SendEmailsIntTest {

	private final String HOST = "localhost";

	@Autowired
	ApplicationConfiguration applicationConfiguration;

	@Test
	public void sendSimpleTestEmail() throws EmailException {
		Email email = new SimpleEmail();
		email.setHostName(HOST);
		email.setSmtpPort(applicationConfiguration.getPort());
		email.setStartTLSEnabled(true);
		email.setFrom("user@gmail.com");
		email.setSubject("Simple email");
		email.setMsg("This is a simple plain text email :-)");
		email.addTo("foo@bar.com");
		email.send();
	}

	@Test
	public void sendEmailWithAttachment() throws EmailException {
		// Create the attachment
		EmailAttachment attachment = new EmailAttachment();
		attachment.setPath("src/main/resources/application.yml");
		attachment.setDisposition(EmailAttachment.ATTACHMENT);
		attachment.setDescription("Configuration file");
		attachment.setName("application.yml");

		// Create the email message
		MultiPartEmail email = new MultiPartEmail();
		email.setHostName(HOST);
		email.setSmtpPort(applicationConfiguration.getPort());
		email.addTo("jdoe@somewhere.org", "John Doe");
		email.setFrom("me@example.org", "Me");
		email.setSubject("File attachment");
		email.setMsg("This email contains an enclosed file.");

		// Add the attachment
		email.attach(attachment);

		// Send the email
		email.send();
	}

	@Test
	public void sendHTMLFormattedEmail() throws EmailException {
		// Create the email message
		HtmlEmail email = new HtmlEmail();
		email.setHostName(HOST);
		email.setSmtpPort(applicationConfiguration.getPort());
		email.addTo("jdoe@somewhere.org", "John Doe");
		email.setFrom("me@example.org", "Me");
		email.setSubject("HTML email");

		// Set the HTML message
		email.setHtmlMsg("<html><body>This is an <b>HTML</b> email.<br /><br /></body></html>");

		// Set the alternative message
		email.setTextMsg("Your email client does not support HTML messages");

		// Send the email
		email.send();
	}

    @Test
    public void sendEmailWithBase64Subject() throws EmailException {
        Email email = new SimpleEmail();
        email.setHostName(HOST);
        email.setSmtpPort(applicationConfiguration.getPort());
        email.setFrom("spammy@example.org");
        email.addTo("foo@bar.com");
        email.setSubject("=?UTF-8?B?4pyIIEJvc3RvbiBhaXJmYXJlIGRlYWxzIC0gd2hpbGUgdGhleSBsYXN0IQ==?=");
        email.setMsg("Not really interesting, huh?");
        email.send();
    }

	@Test
	public void sendEmailToManyRecipientsWithTwoHeaders() throws EmailException {
		Email email = new SimpleEmail();
		email.setHostName(HOST);
		email.setSmtpPort(applicationConfiguration.getPort());
		email.setFrom("info@example.com");
		email.addTo("test1@example.com");
		email.addTo("test2@example.com");
		email.addHeader("Foo", "Bar");
		email.addHeader("Foo2", "Bar2");
		email.setSubject("Hi");
		email.setMsg("Just to check if everything is OK");
		email.send();
	}

	@Test
	public void sendEmailWithDots() throws EmailException {
		Email email = new SimpleEmail();
		email.setDebug(true);
		email.setHostName(HOST);
		email.setSmtpPort(applicationConfiguration.getPort());
		email.setFrom("user@example.com");
		email.addTo("foo@example.com");
		email.setSubject("Two dots separated with a new line");
		email.setMsg(".\n.");
		email.send();
	}
}