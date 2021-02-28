package com.kuplumosk.spring.mvc_hibernate_aop.repositories;

import com.kuplumosk.spring.mvc_hibernate_aop.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
