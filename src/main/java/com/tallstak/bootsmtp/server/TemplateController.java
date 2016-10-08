package com.tallstak.bootsmtp.server;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TemplateController {

	@RequestMapping("/")
	public String root() {
		return "redirect:inbox";
	}

	@RequestMapping("/inbox")
	public String inboxTemplate() {
		return "inbox";
	}

	@RequestMapping("/inbox/{id}")
	public String singleMailTemplate() {
		return "singleMail";
	}
}
