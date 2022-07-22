package com.dean.my_blog.configs;

import com.dean.my_blog.interceptor.WebApiAuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Bean
    public WebApiAuthInterceptor webApiAuthInterceptor() {
        return new WebApiAuthInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(webApiAuthInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
