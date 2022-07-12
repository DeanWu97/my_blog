package com.dean.my_blog.converter;

import com.dean.my_blog.controllers.requests.UserRequest;
import com.dean.my_blog.controllers.responces.UserResponce;
import com.dean.my_blog.entity.User;
import com.dean.my_blog.util.EncryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserConverterDecorater implements UserConverter{
    @Autowired
    @Qualifier("delegate")
    protected UserConverter delegate;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public User userRequestToUser(UserRequest userRequest) {
        User user = delegate.userRequestToUser(userRequest);
        user.setEncyptPassword(EncryptUtil.encrypt(userRequest.getEncyptPassword()));
        return user;
    }

    @Override
    public UserResponce userToUserResponce(User user) {
        UserResponce userResponce = delegate.userToUserResponce(user);
        return userResponce;
    }
}
