package com.dean.my_blog.configs;

import com.dean.my_blog.interceptor.CrossInterceptor;
import com.dean.my_blog.interceptor.LoggingIntercepter;
import com.dean.my_blog.interceptor.WebApiAuthInterceptor;
import com.dean.my_blog.interceptor.mybatis.MyPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Autowired
    private CrossInterceptor crossInterceptor;
    @Bean
    public WebApiAuthInterceptor webApiAuthInterceptor() {
        return new WebApiAuthInterceptor();
    }
    @Bean
    public LoggingIntercepter loggingIntercepter() {
        return new LoggingIntercepter();
    }
    @Bean
    public MyPlugin myPlugin() {
        return new MyPlugin();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(loggingIntercepter()).addPathPatterns("/**");
        registry.addInterceptor(crossInterceptor).addPathPatterns("/**");
        registry.addInterceptor(webApiAuthInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
