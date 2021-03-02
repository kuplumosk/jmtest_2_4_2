package com.kuplumosk.spring.mvc_hibernate_aop.service;

import com.kuplumosk.spring.mvc_hibernate_aop.entity.User;
import com.kuplumosk.spring.mvc_hibernate_aop.entity.UserPrincipal;
import com.kuplumosk.spring.mvc_hibernate_aop.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserPrincipalDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserPrincipalDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        return new UserPrincipal(user);
    }
}
