package com.dean.my_blog.service;

import com.dean.my_blog.entity.User;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    public User registUser() {
        return User.builder().nickname("张三").build();
    }
}
