package com.dean.my_blog.interceptor;

import com.dean.my_blog.entity.User;
import com.dean.my_blog.repo.UserRepo;
import com.google.common.collect.Maps;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class WebApiAuthInterceptor implements HandlerInterceptor {
    @Autowired
    private UserRepo userRepo;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String sign = request.getHeader("sign");
        Map<String, String> parameterMap = convertArrayToString(request.getParameterMap());
        if (System.currentTimeMillis() > Long.valueOf(parameterMap.get("timestamp"))+5*60*1_000) {
            throw new Exception("TimeStamp Is Expaired");
        }
        Long user_id = Long.valueOf(parameterMap.get("user_id"));
        User user = userRepo.findById(user_id).orElseThrow(() -> new Exception("User Not Found"));
        if (!sign.equals(DigestUtils.sha256Hex(parameterMap.get("timestame")+user.getAuthenticationToken()))) {
            throw new Exception("Sign Not Right");
        }
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
