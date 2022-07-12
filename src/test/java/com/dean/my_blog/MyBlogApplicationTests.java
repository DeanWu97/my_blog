package com.dean.my_blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class MyBlogApplicationTests {
    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void contextLoads() {
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        Object securetyConfig = applicationContext.getBean("securityFilterChain");
        int i = 0;
    }

}
