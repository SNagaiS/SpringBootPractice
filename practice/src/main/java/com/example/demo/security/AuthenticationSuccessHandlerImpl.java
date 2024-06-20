package com.example.demo.security;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Signup;
import com.example.demo.repository.SignupRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

	@Autowired
	private SignupRepository signupRepository;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		String email = authentication.getName();
		Signup signup = signupRepository.findByEmail(email);

		if (signup != null) {
			signup.setCurrentSignInAt(LocalDateTime.now());
			signupRepository.save(signup);
		}
		response.sendRedirect("/admin/contacts");
	}
}