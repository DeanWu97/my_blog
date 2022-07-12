package com.dean.my_blog.converter;

import com.dean.my_blog.controllers.requests.CreateBlogRequest;
import com.dean.my_blog.controllers.responces.BlogResponce;
import com.dean.my_blog.entity.Blogs;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE,componentModel = "spring")
@DecoratedWith(BlogConverterDecorater.class)
public interface BlogConverter {
    @Mapping(target = "visible", expression = "java(Blogs.BlogVisibleEnum.getByValue(createBlogRequest.getVisible()))")
    Blogs createRequestToBlog(CreateBlogRequest createBlogRequest);
    @Mapping(source = "coverUrl", target = "cover")
    BlogResponce blogToResponce(Blogs blog);
}
