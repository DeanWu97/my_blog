package com.dean.my_blog.controllers;

import com.dean.my_blog.annotations.SkipAuth;
import com.dean.my_blog.controllers.requests.UserRequest;
import com.dean.my_blog.controllers.responces.BaseResponse;
import com.dean.my_blog.controllers.responces.UserResponce;
import com.dean.my_blog.entity.InvitationCodes;
import com.dean.my_blog.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Objects;
import java.util.Set;

@Slf4j
@SkipAuth
@RestController
@RequestMapping
public class LoginController {
    @Autowired
    private LoginService loginService;
    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<UserResponce>> login(@RequestBody UserRequest userRequest) throws Exception {
        log.info("post login");
            UserResponce userResponce = loginService.loginUser(userRequest);
            if (Objects.isNull(userRequest.getEncyptPassword())) {
                throw new Exception("Login Faild");
            }
            return ResponseEntity.ok(BaseResponse.ok(userResponce));
    }

    @PostMapping("/logout")
    public ResponseEntity<BaseResponse<String>> logout(@RequestBody UserRequest userRequest) throws Exception {
        if (loginService.logoutUser(userRequest)) {
            return ResponseEntity.ok(BaseResponse.ok("SUCCESS"));
        }
        return ResponseEntity.ok(BaseResponse.ok("FAIL"));
    }

    @PostMapping("/regist")
    public ResponseEntity<BaseResponse<UserResponce>> regist(@RequestBody UserRequest userRequest) throws Exception {
        InvitationCodes invitationCode = loginService.vertifyInviteCode(userRequest.getCode());
        if (Objects.isNull(invitationCode)) {
            throw new Exception(new Throwable("InviteCodeUsedUpOrNotExistException"));
        }
        Set<ConstraintViolation<UserRequest>> validate = validator.validate(userRequest);
        if (!validate.isEmpty()) {
            throw new Exception("ParamsValidateException");
        }
        UserResponce userResponce = loginService.registUser(userRequest, invitationCode);
        return ResponseEntity.ok(BaseResponse.ok(userResponce));
    }

}
