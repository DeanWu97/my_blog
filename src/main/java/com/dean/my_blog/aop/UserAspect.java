package com.dean.my_blog.aop;

import com.dean.my_blog.entity.User;
import com.dean.my_blog.repo.UserRepo;
import com.dean.my_blog.threadLocals.UserThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Slf4j
@Aspect
@Component
public class UserAspect {
    private static final String POINT_CUT = "within(com.dean.my_blog.controllers..*)";
    @Autowired
    private UserRepo userRepo;
    @Before(POINT_CUT)
    public void before(JoinPoint joinPoint) throws Exception {
        final ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        final HttpServletRequest request = requestAttributes.getRequest();
        String email = request.getHeader("Email");
        if (Objects.isNull(email) || "null".equals(email)) {
//            throw  new Exception("user not found exception");
            UserThreadLocal.set(User.builder().nickname("guest").build());
            return;
        }
        final User firstByEmail = userRepo.findFirstByEmail(email);
        UserThreadLocal.set(firstByEmail);
        log.info("前置方法,{}", UserThreadLocal.get());
    }
    @After(POINT_CUT)
    public void after() {
        UserThreadLocal.remove();
        log.info("后置方法");
    }
    @AfterThrowing(POINT_CUT)
    public void afterThrow() {
        UserThreadLocal.remove();
        log.info("after throw");
    }
}
