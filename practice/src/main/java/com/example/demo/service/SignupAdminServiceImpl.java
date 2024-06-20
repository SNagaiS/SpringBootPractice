package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Signup;
import com.example.demo.form.SignupAdmin;
import com.example.demo.repository.SignupRepository;

@Service
public class SignupAdminServiceImpl implements SignupAdminService {

	@Autowired
	private SignupRepository signupRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public void saveAdmin(SignupAdmin signupAdmin) {
		Signup signup = new Signup();
		signup.setLastName(signupAdmin.getLastName());
		signup.setFirstName(signupAdmin.getFirstName());
		signup.setEmail(signupAdmin.getEmail());
		signup.setPassword(passwordEncoder.encode(signupAdmin.getPassword()));

		signupRepository.save(signup);
	}
}