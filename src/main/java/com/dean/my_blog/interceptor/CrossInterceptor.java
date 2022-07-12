package com.dean.my_blog.interceptor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CrossInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        response.addHeader("Access-Control-Allow-Origin", request.getHeader(HttpHeaders.ORIGIN));
        if (request.getMethod() != null & HttpMethod.OPTIONS.name().equals(request.getMethod().toUpperCase())) {
            response.addHeader("Access-Control-Allow-Credentials", "true");
            response.addHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE,PUT,HEAD");
            response.addHeader("Access-Control-Allow-Headers", "Content-Type,Token,Email");
            response.addHeader("Access-Control-Max-Age", "3600");
            return true;
        }
        return true;
    }
}
