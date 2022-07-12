package com.dean.my_blog.controllers;

import com.dean.my_blog.controllers.responces.BaseResponse;
import com.dean.my_blog.controllers.responces.UserResponce;
import com.dean.my_blog.service.LoginService;
import com.dean.my_blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("{id}")
    public BaseResponse<UserResponce> getUserById(@PathVariable("id") long id) {
        final UserResponce userResponceById = userService.getUserResponceById(id);
        return BaseResponse.ok(userResponceById);
    }
}
