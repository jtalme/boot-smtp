//package com.tallstak.bootsmtp.server;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.tallstak.bootsmtp.BootSMTP;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.boot.test.WebIntegrationTest;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.util.StringUtils;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.io.InputStream;
//import java.util.List;
//
//import static com.tallstak.bootsmtp.server.MailTestHelper.*;
//import static org.junit.Assert.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(BootSMTP.class)
//@WebIntegrationTest
//public class MailControllerIntTest {
//
//	@Autowired
//	private WebApplicationContext webApplicationContext;
//
//	@Autowired
//	private MailSaver mailSaver;
//
//	@Autowired
//	private EmailRepository emailRepository;
//
//	private MockMvc mockMvc;
//
//	@Before
//	public void setup() {
//		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//	}
//
//	@After
//	public void tearDown() {
//		emailRepository.deleteAll();
//	}
//
//	@Test
//	public void testGetMailReturnsSavedMail() throws Exception {
//		final String from = "from@example.com";
//		final String subject = "Hello";
//		final String content = "How are you?";
//		final String to = "to@example.com";
//
//		final InputStream exampleData = fromString(getMockEmail(from, "to@example.com", subject, content));
//		mailSaver.saveEmail(from, to, exampleData);
//		mailSaver.saveEmail(from, to, exampleData);
//		mailSaver.saveEmail(from, to, exampleData);
//
//		final MvcResult mailResult = mockMvc.perform(get("/mail"))
//			.andExpect(status().isOk())
//			.andReturn();
//
//		String jsonResult = new String(mailResult.getResponse().getContentAsByteArray());
//		List<Email> emails = new ObjectMapper().readValue(jsonResult, new TypeReference<List<Email>>(){});
//		Email firstEmail = emails.get(0);
//
//		assertTrue(emails.size() == 3);
//		assertTrue(emails.get(0) instanceof Email);
//		assertTrue(emails.get(1) instanceof Email);
//		assertTrue(emails.get(2) instanceof Email);
//
//		assertTrue(firstEmail.getTo().equals(to));
//		assertTrue(firstEmail.getWhoFrom().equals(from));
//		assertTrue(firstEmail.getSubject().equals(subject));
//		assertFalse(StringUtils.isEmpty(firstEmail.getEmailStr()));
//	}
//
//	@Test
//	public void testDeleteMail() throws Exception {
//		final String from = "from@example.com";
//		final String subject = "Hello";
//		final String content = "How are you?";
//		final String to = "to@example.com";
//
//		final InputStream exampleData = fromString(getMockEmail(from, "to@example.com", subject, content));
//		mailSaver.saveEmail(from, to, exampleData);
//		mailSaver.saveEmail(from, to, exampleData);
//		mailSaver.saveEmail(from, to, exampleData);
//
//		mockMvc.perform(delete("/mail"))
//			.andExpect(status().isOk());
//
//		List<Email> allEmails = (List<Email>) emailRepository.findAll();
//		assertTrue(allEmails.size() == 0);
//	}
//
//}