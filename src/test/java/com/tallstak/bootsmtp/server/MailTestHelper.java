package com.tallstak.bootsmtp.server;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * Helper methods used across tests
 *
 * @since 1.0
 */
public class MailTestHelper {

	/**
	 * Generate a mock email with 4 initial buffer lines that are removed by {@link MailSaver#convertStreamToString(InputStream)}
	 */
	public static String getMockEmail(String from, String to, String subject, String content) {
		String br = System.getProperty("line.separator");

		StringBuilder sb = new StringBuilder()
			.append("Line 1 will be removed").append(br)
			.append("Line 2 will be removed").append(br)
			.append("Line 3 will be removed").append(br)
			.append("Line 4 will be removed").append(br)
			.append("Date: Thu, 15 May 2042 04:42:42 +0800 (CST)").append(br)
			.append(String.format("From: \"%s\" <%s>%n", from, from))
			.append(String.format("To: \"%s\" <%s>%n", to, to))
			.append("Message-ID: <17000042.0.1300000000042.JavaMail.wtf@OMG00042>").append(br)
			.append(String.format("Subject: %s%n", subject))
			.append("MIME-Version: 1.0").append(br)
			.append("Content-Type: text/plain; charset=us-ascii").append(br)
			.append("Content-Transfer-Encoding: 7bit").append(br).append(br)
			.append(content).append(br).append(br);
		return sb.toString();
	}

	/**
	 * UTF8 conversion
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static InputStream fromString(String str) throws UnsupportedEncodingException {
		byte[] bytes = str.getBytes("UTF8");
		return new ByteArrayInputStream(bytes);
	}
}