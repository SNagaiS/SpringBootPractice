package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Contact;
import com.example.demo.form.ContactForm;
import com.example.demo.repository.ContactRepository;

@Service
public class ContactServiceImpl implements ContactService {
	@Autowired
	private ContactRepository contactRepository;

	@Override
	public void saveContact(ContactForm contactForm) {
		Contact contact = new Contact();

		contact.setLastName(contactForm.getLastName());
		contact.setFirstName(contactForm.getFirstName());
		contact.setEmail(contactForm.getEmail());
		contact.setPhone(contactForm.getPhone());
		contact.setZipCode(contactForm.getZipCode());
		contact.setAddress(contactForm.getAddress());
		contact.setBuildingName(contactForm.getBuildingName());
		contact.setContactType(contactForm.getContactType());
		contact.setBody(contactForm.getBody());

		contactRepository.save(contact);
	}

	@Override
	public List<Contact> findAllContacts() {
        return contactRepository.findAll();
    }

	@Override
	public Contact findContactById(Long id) {
		return contactRepository.findById(id).orElse(null);
	}

	@Override
    public void updateContact(Long id, ContactForm contactForm) {
        Contact existingContact = contactRepository.findById(id).orElse(null);
        if (existingContact != null) {
            existingContact.setLastName(contactForm.getLastName());
            existingContact.setFirstName(contactForm.getFirstName());
            existingContact.setEmail(contactForm.getEmail());
            existingContact.setPhone(contactForm.getPhone());
            existingContact.setZipCode(contactForm.getZipCode());
            existingContact.setAddress(contactForm.getAddress());
            existingContact.setBuildingName(contactForm.getBuildingName());
            existingContact.setContactType(contactForm.getContactType());
            existingContact.setBody(contactForm.getBody());

            contactRepository.save(existingContact);
        }
    }

    @Override
    public void deleteContact(Long id) {
        contactRepository.deleteById(id);
    }
}