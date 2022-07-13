package com.dean.my_blog;

import com.dean.my_blog.entity.InvitationCodes;
import com.dean.my_blog.repo.InvitationCodeRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class MyBlogApplicationTests {
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private InvitationCodeRepo invitationCodeRepo;

    @Test
    void contextLoads() {
        Iterable<InvitationCodes> all = invitationCodeRepo.findAll();
        InvitationCodes byCodeAndInviteTimesIsNot = invitationCodeRepo.findByCodeAndInviteTimesIsNot("666", 0L);
        int i = 0;
    }

}
