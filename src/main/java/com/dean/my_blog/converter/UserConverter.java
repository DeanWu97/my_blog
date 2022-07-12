package com.dean.my_blog.converter;

import com.dean.my_blog.controllers.requests.UserRequest;
import com.dean.my_blog.controllers.responces.UserResponce;
import com.dean.my_blog.entity.User;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE,componentModel = "spring")
@DecoratedWith(UserConverterDecorater.class)
public interface UserConverter {
    @Mapping(source = "code", target = "inviteCode")
    User userRequestToUser(UserRequest userRequest);
    UserResponce userToUserResponce(User user);
}
