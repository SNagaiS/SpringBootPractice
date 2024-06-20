package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.service.AdminDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
	private AdminDetailsService adminDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationSuccessHandlerImpl authenticationSuccessHandler;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests(authorizeRequests -> authorizeRequests
						.requestMatchers("/public/**", "/admin/signin", "/admin/signup").permitAll()
						.anyRequest().authenticated())
				.formLogin(formLogin -> formLogin
						.loginPage("/admin/signin")
						.loginProcessingUrl("/admin/signin")
						.usernameParameter("email")
						.successHandler(authenticationSuccessHandler)
						.permitAll())
				.logout(logout -> logout
						.logoutUrl("/admin/logout")
						.logoutSuccessUrl("/admin/signin")
						.permitAll());
		return http.build();
	}

	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(adminDetailsService).passwordEncoder(passwordEncoder);
	}
}
