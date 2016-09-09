package com.tallstak.bootsmtp.server;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TemplateController {

	@RequestMapping("/")
	public String root() {
		return "redirect:inbox";
	}

	@RequestMapping("/foo")
	public String inbox() {
		return "inbox";
	}

	@RequestMapping("/inbox")
	public String inboxTemplate() {
		return "inbox";
	}
}
