package com.dean.my_blog.controllers;

import com.dean.my_blog.controllers.requests.UserRequest;
import com.dean.my_blog.controllers.responces.BaseResponse;
import com.dean.my_blog.controllers.responces.UserResponce;
import com.dean.my_blog.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

@RestController
@Slf4j
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginService loginService;
    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    @PostMapping
    public Integer login() {
        log.info("post login");
        return HttpServletResponse.SC_OK;
    }
    @PostMapping("/regist")
    public ResponseEntity<BaseResponse<UserResponce>> regist(@RequestBody UserRequest userRequest) {
        Set<ConstraintViolation<UserRequest>> validate = validator.validate(userRequest);
        if (!validate.isEmpty()) {
//            return ResponseEntity.badRequest().body(validate.iterator().next().getMessage());
            return ResponseEntity.badRequest().build();
        }
        UserResponce userResponce = loginService.registUser(userRequest);
        return ResponseEntity.ok(BaseResponse.ok(userResponce));
    }

}
