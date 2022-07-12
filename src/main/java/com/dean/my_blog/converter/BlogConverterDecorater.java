package com.dean.my_blog.converter;

import com.dean.my_blog.controllers.requests.CreateBlogRequest;
import com.dean.my_blog.controllers.responces.BlogResponce;
import com.dean.my_blog.entity.Blogs;
import com.dean.my_blog.threadLocals.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class BlogConverterDecorater implements BlogConverter{
    @Autowired
    @Qualifier("delegate")
    protected BlogConverter delegate;
    @Override
    public Blogs createRequestToBlog(CreateBlogRequest createBlogRequest) {
        final Blogs requestToBlog = delegate.createRequestToBlog(createBlogRequest);
        requestToBlog.setAuthorId(UserThreadLocal.get().getId());
//        requestToBlog.setContent(HtmlEscape.escapeHtml(createBlogRequest.getContent(), HtmlEscapeType.HTML5_NAMED_REFERENCES_DEFAULT_TO_HEXA, HtmlEscapeLevel.LEVEL_4_ALL_CHARACTERS));
        return requestToBlog;
    }

    @Override
    public BlogResponce blogToResponce(Blogs blog) {
        final BlogResponce blogResponce = delegate.blogToResponce(blog);
        blogResponce.setDate(ZonedDateTime.of(blog.getCreatedAt(), ZoneId.systemDefault()).toInstant().toEpochMilli());
//        blogResponce.setContent(HtmlEscape.unescapeHtml(blog.getContent()));
        return blogResponce;
    }
}
