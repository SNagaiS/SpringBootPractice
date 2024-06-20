package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Signup;
import com.example.demo.repository.SignupRepository;

@Service
public class AdminDetailsService implements UserDetailsService {

    @Autowired
    private SignupRepository signupRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Signup signup = signupRepository.findByEmail(email);
        if (signup == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return User.builder()
                .username(signup.getEmail())
                .password(signup.getPassword())
                .roles("ADMIN")
                .build();
    }
}