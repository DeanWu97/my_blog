package com.dean.my_blog.controllers.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.Getter;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotNull;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRequest {
    private Long id;
    private String nickname;
    @NotNull(message = "Password must be not null")
    private String encyptPassword;
    private String cellphone;
    @NotNull(message = "Email must be not null")
    private String email;
    private String code;

}
