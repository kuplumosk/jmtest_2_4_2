package com.kuplumosk.spring.mvc_hibernate_aop.service;

import com.kuplumosk.spring.mvc_hibernate_aop.entity.User;
import com.kuplumosk.spring.mvc_hibernate_aop.repositories.UserRepository;
import javax.persistence.EntityManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final EntityManager entityManager;
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository, EntityManager entityManager) {
        this.userRepository = userRepository;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =  findOneWithRoles(userRepository.findByUsername(username).getId());
        return user;
    }

    public User findOneWithRoles(Long id) {
        return entityManager.createQuery(
            "select u from User u left join fetch u.roles where u.id = :id", User.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
