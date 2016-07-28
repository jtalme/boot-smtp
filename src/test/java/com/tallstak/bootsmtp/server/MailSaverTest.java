package com.tallstak.bootsmtp.server;

import com.tallstak.bootsmtp.ApplicationConfiguration;
import com.tallstak.bootsmtp.BootSMTP;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static com.tallstak.bootsmtp.server.MailTestHelper.*;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(BootSMTP.class)
@WebIntegrationTest
public class MailSaverTest {

	private MailSaver saver;
	private ApplicationConfiguration applicationConfiguration;

	@Autowired
	private EmailRepository emailRepository;

	@Before
	public void createMailSaver() {
		applicationConfiguration = new ApplicationConfiguration();
		applicationConfiguration.setRelayDomains("example.com,gmail.com");
		saver = new MailSaver(applicationConfiguration, emailRepository);
	}

	@After
	public void tearDown() {
		saver = null;
	}

	@Test
	public void testSaveEmail() throws UnsupportedEncodingException, InterruptedException {
		final String from = "from@example.com";
		final String to = "to@example.com";
		final String subject = "Hello";
		final String content = "How are you?";

		// Save
		final InputStream data = fromString(getMockEmail(from, to, subject, content));
		saver.saveEmail(from, to, data);

		Email savedMail = getFirstSavedEmail();

		assertEquals(from, savedMail.getWhoFrom());
		assertEquals(to, savedMail.getTo());
		assertEquals(subject, savedMail.getSubject());
		assertEquals(to, savedMail.getTo());
		assertNotNull(savedMail.getEmailStr());
		assertFalse(savedMail.getEmailStr().isEmpty());

		emailRepository.deleteAll();
		assertNull(getFirstSavedEmail());
	}

	@Test
	public void testRelayDomain() throws UnsupportedEncodingException, InterruptedException {
		final String from = "from@example.com";
		final String subject = "Hello";
		final String content = "How are you?";

		// Save with matching relay domain
		final InputStream exampleData = fromString(getMockEmail(from, "to@example.com", subject, content));
		saver.saveEmail(from, "to@example.com", exampleData);
		Email exampleEmail = getFirstSavedEmail();
		assertNotNull(exampleEmail);
		emailRepository.deleteAll();

		// Save with matching relay domain
		final InputStream gmailData = fromString(getMockEmail(from, "to@gmail.com", subject, content));
		saver.saveEmail(from, "to@gmail.com", gmailData);
		Email gmailEmail = getFirstSavedEmail();
		assertNotNull(gmailEmail);
		emailRepository.deleteAll();

		// Save with matching relay domain
		final InputStream hotmailData = fromString(getMockEmail(from, "to@hotmail.com", subject, content));
		saver.saveEmail(from, "to@hotmail.com", hotmailData);
		assertNull(getFirstSavedEmail());
	}

	private Email getFirstSavedEmail() {
		List<Email> emails = (List<Email>) emailRepository.findAll();
		if (emails.size() > 0) {
			return emails.get(0);
		} else {
			return null;
		}
	}
}