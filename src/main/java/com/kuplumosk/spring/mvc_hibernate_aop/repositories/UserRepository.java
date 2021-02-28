package com.kuplumosk.spring.mvc_hibernate_aop.repositories;

import com.kuplumosk.spring.mvc_hibernate_aop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
