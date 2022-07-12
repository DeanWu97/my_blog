package com.dean.my_blog.service;

import com.dean.my_blog.controllers.responces.UserResponce;
import com.dean.my_blog.converter.UserConverter;
import com.dean.my_blog.entity.User;
import com.dean.my_blog.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserConverter userConverter;

    public UserResponce getUserResponceById(long id) {
        final Optional<User> byId = userRepo.findById(id);
        return userConverter.userToUserResponce(byId.orElse(new User()));
    }
}
