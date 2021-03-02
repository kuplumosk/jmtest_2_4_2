package com.kuplumosk.spring.mvc_hibernate_aop.security;

import com.kuplumosk.spring.mvc_hibernate_aop.service.UserPrincipalDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsService userPrincipalDetailService;
    private LoginSuccessHandler loginSuccessHandler;

    public SecurityConfig(
        UserDetailsService userPrincipalDetailService,
        LoginSuccessHandler loginSuccessHandler) {
        this.userPrincipalDetailService = userPrincipalDetailService;
        this.loginSuccessHandler = loginSuccessHandler;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .formLogin()
            .loginPage("/login")
            .permitAll()
            .successHandler(loginSuccessHandler);

        http.logout()
            .permitAll()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/login")
            .and().csrf().disable();

        http.authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers("/login").permitAll()
            .antMatchers("/user").hasAnyRole("USER", "ADMIN")
            .antMatchers("/admin").hasRole("ADMIN")
            .antMatchers("/**").hasRole("ADMIN");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(this.userPrincipalDetailService);
        return authenticationProvider;
    }
}
