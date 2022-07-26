package com.dean.my_blog.configs;

import com.dean.my_blog.interceptor.LoggingIntercepter;
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
    @Bean
    public LoggingIntercepter loggingIntercepter() {
        return new LoggingIntercepter();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingIntercepter()).addPathPatterns("/**");
        registry.addInterceptor(webApiAuthInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login/regist")
                .excludePathPatterns("/login");
        super.addInterceptors(registry);
    }
}
