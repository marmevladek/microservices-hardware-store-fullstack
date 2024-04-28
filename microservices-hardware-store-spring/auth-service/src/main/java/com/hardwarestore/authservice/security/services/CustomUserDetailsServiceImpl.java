package com.hardwarestore.authservice.security.services;

import com.hardwarestore.authservice.exception.UserNotFoundException;
import com.hardwarestore.authservice.model.User;
import com.hardwarestore.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User Name " + username + " not found"));

        return CustomUserDetailsImpl.build(user);
    }
}
