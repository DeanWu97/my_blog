package com.dean.my_blog.service;

import com.dean.my_blog.controllers.requests.UserRequest;
import com.dean.my_blog.controllers.responces.UserResponce;
import com.dean.my_blog.entity.InvitationCodes;
import com.dean.my_blog.entity.User;
import com.dean.my_blog.mapper.UserConverter;
import com.dean.my_blog.repo.InvitationCodeRepo;
import com.dean.my_blog.repo.UserRepo;
import com.dean.my_blog.util.EncryptUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.objenesis.ObjenesisHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Objects;
import java.util.UUID;

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

    public UserResponce loginUser(UserRequest userRequest) throws Exception {
        User firstByEmail = userRepo.findFirstByEmail(userRequest.getEmail());
        if (Objects.isNull(firstByEmail)) throw new Exception("User Not Found");
        if (Objects.nonNull(firstByEmail.getAuthenticationToken())) {
            return userConverter.userToUserResponce(firstByEmail);
        } else if (EncryptUtil.match(userRequest.getEncyptPassword(),firstByEmail.getEncyptPassword())){
            firstByEmail.setAuthenticationToken(UUID.randomUUID().toString());
            userRepo.save(firstByEmail);
            return userConverter.userToUserResponce(firstByEmail);
        }
        return userConverter.userToUserResponce(firstByEmail);
    }

    public InvitationCodes vertifyInviteCode(String code) {
        if (Objects.isNull(code)) return null;
        InvitationCodes byCodeAndInviteTimesIsNot = invitationCodeRepo.findByCodeAndInviteTimesIsNot(code, 0L);
        return byCodeAndInviteTimesIsNot;
    }

    public boolean logoutUser(UserRequest userRequest) throws Exception {
        User user = userRepo.findById(userRequest.getId()).get();
        if (Objects.isNull(user)) return false;
        if (Objects.isNull(user.getAuthenticationToken())) return true;
        user.setAuthenticationToken(null);
        userRepo.save(user);
        return true;
    }
}
