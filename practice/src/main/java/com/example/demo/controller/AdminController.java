package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Contact;
import com.example.demo.form.ContactForm;
import com.example.demo.form.SignupAdmin;
import com.example.demo.service.ContactService;
import com.example.demo.service.SignupAdminService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

	@Autowired
	private SignupAdminService signupAdminService;

	@Autowired
	private ContactService contactService;

	@GetMapping("/admin/signup")
	public String signup(Model model) {
		model.addAttribute("signupAdmin", new SignupAdmin());
		return "signup_admin";
	}

	@PostMapping("/admin/signup")
	public String signup(@Validated @ModelAttribute("signupAdmin") SignupAdmin signupAdmin, BindingResult errorResult,
			HttpServletRequest request) {
		if (errorResult.hasErrors()) {
			return "signup_admin";
		}
		HttpSession session = request.getSession();
		session.setAttribute("signupAdmin", signupAdmin);
		signupAdminService.saveAdmin(signupAdmin);
		return "redirect:/admin/contacts";
	}

	@GetMapping("/admin/signin")
	public String showLoginForm() {
		return "login";
	}

	@PostMapping("/admin/signin")
	public String login() {
		return "redirect:/admin/contacts";
	}

	@GetMapping("/admin/contacts")
	public String getAllContacts(Model model) {
		model.addAttribute("contacts", contactService.findAllContacts());
		return "contacts_admin";
	}

	@GetMapping("/admin/contacts/{id}")
	public String getContactById(@PathVariable Long id, Model model) {
		Contact contact = contactService.findContactById(id);
		model.addAttribute("contact", contact);
		return "contact_detail";
	}

	@GetMapping("/admin/contacts/{id}/edit")
	public String showEditContactForm(@PathVariable Long id, Model model) {
		Contact contact = contactService.findContactById(id);
		model.addAttribute("contactForm", contact);
		return "edit_contact";
	}

	@PostMapping("/admin/contacts/{id}/edit")
	public String processEditContactForm(@PathVariable Long id,
			@ModelAttribute("contactForm") @Validated ContactForm contactForm,
			BindingResult result) {
		if (result.hasErrors()) {
			return "edit_contact";
		}
		contactService.updateContact(id, contactForm);
		return "redirect:/admin/contacts";
	}

	@PostMapping("/admin/contacts/{id}/delete")
	public String deleteContact(@PathVariable Long id) {
		contactService.deleteContact(id);
		return "redirect:/admin/contacts";
	}

	@GetMapping("/admin/logout")
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/admin/signin";
	}
}
