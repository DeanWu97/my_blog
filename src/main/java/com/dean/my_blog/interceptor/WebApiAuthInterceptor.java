package com.dean.my_blog.interceptor;

import com.dean.my_blog.annotations.SkipAuth;
import com.dean.my_blog.entity.User;
import com.dean.my_blog.repo.UserRepo;
import com.dean.my_blog.util.TokenUtil;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.AnnotatedElement;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class WebApiAuthInterceptor implements HandlerInterceptor {
    @Autowired
    private UserRepo userRepo;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (HttpMethod.OPTIONS.name().equals(request.getMethod())) {
            return HandlerInterceptor.super.preHandle(request, response, handler);
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        if (Objects.nonNull(AnnotationUtils.findAnnotation(handlerMethod.getBean().getClass(), SkipAuth.class)) || Objects.nonNull(AnnotationUtils.findAnnotation(handlerMethod.getMethod(), SkipAuth.class))) {
            return HandlerInterceptor.super.preHandle(request, response, handler);
        }
        final String token = request.getHeader("Token").split(" ")[1];
        if (Objects.isNull(token) || "null".equals(token) || !TokenUtil.validToken(token)) {
            throw new Exception("user not found!!");
        }
        final String emailFromToken = TokenUtil.getEmailFromToken(token);
        if (!StringUtils.equals(request.getHeader("Email"), emailFromToken)) {
            throw new Exception("Auth Token Not Match");
        }
        //TODO:check user role
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    private Map<String, String> convertArrayToString(Map<String, String[]> stringMap) {
        Map<String, String> returnMap = Maps.newHashMapWithExpectedSize(stringMap.size());
        if (MapUtils.isNotEmpty(stringMap)) {
            for (Map.Entry<String, String[]> entry : stringMap.entrySet()) {
                returnMap.put(entry.getKey(), entry.getValue() == null ? "" : entry.getValue()[0]);
            }
        }
        return returnMap;
    }
}
