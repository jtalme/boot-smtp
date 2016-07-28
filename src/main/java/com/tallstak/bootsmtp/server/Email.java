package com.tallstak.bootsmtp.server;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 * A model representing a received email.
 * <p>
 * This object contains useful data such as the content of the email and its path in the file system.
 * </p>
 */
@Entity
public final class Email {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private long id;

	private String dateReceived;
	private String whoFrom;
	private String to;
	private String subject;

	@Column(name="EMAIL_STR",
		columnDefinition="CLOB NOT NULL",
		table="EMAIL")
	@Lob
	private String emailStr;

	public long getId() {
		return id;
	}

	public String getDateReceived() {
		return dateReceived;
	}
	public void setDateReceived(String dateReceived) {
		this.dateReceived = dateReceived;
	}

	public String getWhoFrom() {
		return whoFrom;
	}
	public void setWhoFrom(String whoFrom) {
		this.whoFrom = whoFrom;
	}

	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getEmailStr() {
		return emailStr;
	}
	public void setEmailStr(String emailStr) {
		this.emailStr = emailStr;
	}

	@Override
	public String toString() {
		return String.format("Email[id: '%d', date: '%s', whoFrom: '%s', 'to: '%s', subject: '%s', content: '%s']",
			id, dateReceived, whoFrom, to, subject, emailStr);
	}
}
