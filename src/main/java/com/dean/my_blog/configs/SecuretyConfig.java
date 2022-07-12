package com.dean.my_blog.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class SecuretyConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeRequests().antMatchers("/login/regist").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/login/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable();
        return http.build();
    }
}
