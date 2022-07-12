package com.dean.my_blog.service;

import com.dean.my_blog.controllers.requests.UserRequest;
import com.dean.my_blog.controllers.responces.UserResponce;
import com.dean.my_blog.entity.InvitationCodes;
import com.dean.my_blog.entity.User;
import com.dean.my_blog.converter.UserConverter;
import com.dean.my_blog.repo.InvitationCodeRepo;
import com.dean.my_blog.repo.UserRepo;
import com.dean.my_blog.threadLocals.UserThreadLocal;
import com.dean.my_blog.util.EncryptUtil;
import com.dean.my_blog.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        invitationCode.setInviteTimes(invitationCode.getInviteTimes() - 1);
        invitationCodeRepo.save(invitationCode);
        user.setAuthenticationToken(TokenUtil.generateToken(user.getEmail()));
        user = userRepo.save(user);
        return userConverter.userToUserResponce(user);
    }

    public UserResponce loginUser(UserRequest userRequest) throws Exception {
        User firstByEmail = userRepo.findFirstByEmail(userRequest.getEmail());
        UserThreadLocal.set(firstByEmail);
        try {
            if (Objects.isNull(firstByEmail)) throw new Exception("User Not Found");
            if (!EncryptUtil.match(userRequest.getEncyptPassword(),firstByEmail.getEncyptPassword())){
                throw new Exception("password not right");
            }
            firstByEmail.setAuthenticationToken(TokenUtil.generateToken(UserThreadLocal.get().getEmail()));
            userRepo.save(firstByEmail);
        }finally {
            UserThreadLocal.remove();
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
