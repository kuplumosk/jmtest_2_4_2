package com.kuplumosk.spring.mvc_hibernate_aop.service;

import com.kuplumosk.spring.mvc_hibernate_aop.entity.Role;
import com.kuplumosk.spring.mvc_hibernate_aop.entity.User;
import com.kuplumosk.spring.mvc_hibernate_aop.repositories.RoleRepository;
import com.kuplumosk.spring.mvc_hibernate_aop.repositories.UserRepository;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.stream.Collectors;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
        RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        if (!userRepository.getOne(user.getId()).getPassword().equals(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.saveAndFlush(user);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.saveAndFlush(user);
    }

    @Override
    @Transactional
    public User findById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public List<User> findAllUsers() {
        List<User> allUsers = userRepository.findAllAndFetchRolesEagerly();
        return allUsers.stream().sorted((u1, u2) -> (int) (u1.getId() - u2.getId())).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    @Transactional
    public Role getRoleById(Long id) {
        return roleRepository.getOne(id);
    }

    @Override
    @Transactional
    public void setRolesToUser(User user, Long[] roles) {
        Set<Role> roleList = new HashSet<>();
        for(Long id : roles) {
            roleList.add(roleRepository.findById(id).orElse(null));
        }
        user.setRoles(roleList);
    }
}
