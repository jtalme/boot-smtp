package com.tallstak.bootsmtp;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

@Component
@ConfigurationProperties(prefix = "application")
public class ApplicationConfiguration {

	/**
	 * SMTP port number
	 */
	private int port;

	/**
	 * IP address or hostname to bind to. Binds to all local IP addresses if not specified
	 */
	private InetAddress bindAddress;

	/**
	 * Comma separated email domain(s) for which relay is accepted. If not specified, relays to any domain.
	 * If specified, relays only emails matching these domain(s), dropping (not saving) others
	 */
	private String relayDomains;

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public InetAddress getBindAddress() {
		// return InetAddress.getByName(bindAddressStr);
		return bindAddress;
	}

	public void setBindAddress(InetAddress bindAddress) {
		this.bindAddress = bindAddress;
	}

	public String getRelayDomains() {
		return relayDomains;
	}

	public void setRelayDomains(String relayDomains) {
		this.relayDomains = relayDomains;
	}
}
