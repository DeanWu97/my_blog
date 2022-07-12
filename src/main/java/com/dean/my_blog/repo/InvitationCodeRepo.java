package com.dean.my_blog.repo;

import com.dean.my_blog.entity.InvitationCodes;
import org.springframework.data.repository.CrudRepository;

public interface InvitationCodeRepo extends CrudRepository<InvitationCodes,Long> {
    InvitationCodes findByCodeAndInviteTimesIsNot(String code, Long invitationTimes);
}
