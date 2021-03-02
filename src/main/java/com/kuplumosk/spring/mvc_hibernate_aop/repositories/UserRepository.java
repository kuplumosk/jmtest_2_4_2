package com.kuplumosk.spring.mvc_hibernate_aop.repositories;

import com.kuplumosk.spring.mvc_hibernate_aop.entity.User;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT p FROM User p JOIN FETCH p.roles WHERE p.username = (:username)")
    User findByUsername(@Param("username")String username);

    @Query("SELECT p FROM User p JOIN FETCH p.roles WHERE p.id = (:id)")
    User findByIdAndFetchRolesEagerly(@Param("id") Long id);

    @Query("SELECT DISTINCT p FROM User p JOIN FETCH p.roles ORDER BY p.id")
    List<User> findAllAndFetchRolesEagerly();
}
