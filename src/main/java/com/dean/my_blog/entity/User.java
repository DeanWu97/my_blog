package com.dean.my_blog.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity{
    private String nickname;
    private String encyptPassword;
    private String cellphone;
    private String email;
}
