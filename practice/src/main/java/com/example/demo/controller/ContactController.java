package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.form.ContactForm;
import com.example.demo.service.ContactService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ContactController {
	@Autowired
	private ContactService contactService;

	@GetMapping("/public/contact")
	public String contact(Model model) {
		model.addAttribute("contactForm", new ContactForm());

		return "contact";
	}

	@PostMapping("/public/contact")
	public String contact(@Validated @ModelAttribute("contactForm") ContactForm contactForm, BindingResult errorResult,
			HttpServletRequest request) {

		if (errorResult.hasErrors()) {
			return "contact";
		}

		HttpSession session = request.getSession();
		session.setAttribute("contactForm", contactForm);

		return "redirect:/public/contact/confirm";
	}

	@GetMapping("/public/contact/confirm")
	public String confirm(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();

		ContactForm contactForm = (ContactForm) session.getAttribute("contactForm");
		model.addAttribute("contactForm", contactForm);
		return "confirmation";
	}

	@PostMapping("/public/contact/register")
	public String register(Model model, HttpServletRequest request) {

		HttpSession session = request.getSession();
		ContactForm contactForm = (ContactForm) session.getAttribute("contactForm");

		contactService.saveContact(contactForm);

		return "redirect:/public/contact/complete";
	}

	@GetMapping("/public/contact/complete")
	public String complete(Model model, HttpServletRequest request) {
		if (request.getSession(false) == null) {
			return "redirect:/public/contact";
		}

		HttpSession session = request.getSession();
		ContactForm contactForm = (ContactForm) session.getAttribute("contactForm");
		model.addAttribute("contactForm", contactForm);

		session.invalidate();

		return "completion";
	}
}
