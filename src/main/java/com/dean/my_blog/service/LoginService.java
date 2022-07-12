package com.dean.my_blog.service;

import com.dean.my_blog.controllers.requests.UserRequest;
import com.dean.my_blog.controllers.responces.UserResponce;
import com.dean.my_blog.entity.User;
import com.dean.my_blog.mapper.UserConverter;
import com.dean.my_blog.repo.UserRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class LoginService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserConverter userConverter;
    public UserResponce registUser(@RequestBody UserRequest userRequest) {
        User user = userConverter.userRequestToUser(userRequest);
        user = userRepo.save(user);
        return userConverter.userToUserResponce(user);
    }
}
