package com.dean.my_blog.service;

import com.dean.my_blog.controllers.requests.UserRequest;
import com.dean.my_blog.controllers.responces.UserResponce;
import com.dean.my_blog.entity.InvitationCodes;
import com.dean.my_blog.entity.User;
import com.dean.my_blog.mapper.UserConverter;
import com.dean.my_blog.repo.InvitationCodeRepo;
import com.dean.my_blog.repo.UserRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Objects;

@Service
public class LoginService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private InvitationCodeRepo invitationCodeRepo;
    @Autowired
    private UserConverter userConverter;

    @Transactional
    public UserResponce registUser(UserRequest userRequest, InvitationCodes invitationCode) {
        User user = userConverter.userRequestToUser(userRequest);
        user = userRepo.save(user);
        invitationCode.setInviteTimes(invitationCode.getInviteTimes() - 1);
        invitationCodeRepo.save(invitationCode);
        return userConverter.userToUserResponce(user);
    }
    public InvitationCodes vertifyInviteCode(String code) {
        InvitationCodes byCodeAndInviteTimesIsNot = invitationCodeRepo.findByCodeAndInviteTimesIsNot(code, 0L);
        return byCodeAndInviteTimesIsNot;
    }
}
