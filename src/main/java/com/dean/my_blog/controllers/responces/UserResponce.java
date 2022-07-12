package com.dean.my_blog.controllers.responces;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserResponce {
    private String nickname;
    private String cellphone;
    private String email;
}
