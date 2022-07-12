package com.dean.my_blog.interceptor;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
public class LoggingIntercepter implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String, String> params = convertArrayToString(request.getParameterMap());
        log.info("[{}]enter api logger: method={}, uri={},params={}",System.currentTimeMillis(),request.getMethod(),request.getRequestURI(),params);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("[{}]exit api logger: method={}, uri={},{},{}, ex={}",System.currentTimeMillis(),request.getMethod(),request.getRequestURI(),response.getStatus(),response.getOutputStream(),ex);
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
