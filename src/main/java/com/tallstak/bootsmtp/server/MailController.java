package com.tallstak.bootsmtp.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * HTTP endpoints for retreiving and deleting emails
 */
@Controller
public class MailController {

	private EmailRepository emailRepository;

	@Autowired
	public MailController(EmailRepository emailRepository) {
		this.emailRepository = emailRepository;
	}

//	@RequestMapping("/")
//	public String root() {
//		return "redirect:mail";
//	}

	/**
	 * Returns all in-memory emails
	 * @return
	 */
	@RequestMapping(
		path = "/mail",
		method = RequestMethod.GET
	)
	@ResponseBody
	public List<Email> getMail() {
		return (List<Email>) emailRepository.findAll();
	}

	/**
	 * Returns email identified by provided id
	 * @return
	 */
	@RequestMapping(
		path = "/mail/{id}",
		method = RequestMethod.GET
	)
	@ResponseBody
	public Email getMail(@PathVariable Long id) {
		return emailRepository.findOne(id);
	}

	/**
	 * Deletes all in-memory and file based emails
	 */
	@RequestMapping(
		path = "/mail",
		method = RequestMethod.DELETE
	)
	@ResponseBody
	public void deleteMails() {
		emailRepository.deleteAll();
	}

	/**
	 * Deletes all in-memory and file based emails
	 */
	@RequestMapping(
		path = "/mail/{id}",
		method = RequestMethod.DELETE
	)
	@ResponseBody
	public void deleteMail(@PathVariable long id) {
		emailRepository.delete(id);
	}
}
