package com.dean.my_blog.controllers;

import com.dean.my_blog.entity.User;
import com.dean.my_blog.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginService loginService;
    @PostMapping
    public Integer login() {
        log.info("post login");
        return HttpServletResponse.SC_OK;
    }
    @PostMapping("/regist")
    public Integer regist() {
        User user = loginService.registUser();
        return HttpServletResponse.SC_OK;
    }
}
