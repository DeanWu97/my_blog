package com.dean.my_blog.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "invitation_codes")
public class InvitationCodes extends BaseEntity{
    private Long inviteTimes;
    private String code;

}
