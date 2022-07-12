package com.dean.my_blog.controllers;

import com.dean.my_blog.annotations.SkipAuth;
import com.dean.my_blog.controllers.requests.CreateBlogRequest;
import com.dean.my_blog.controllers.responces.BaseResponse;
import com.dean.my_blog.controllers.responces.BlogResponce;
import com.dean.my_blog.converter.BlogConverter;
import com.dean.my_blog.entity.Blogs;
import com.dean.my_blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    private BlogService blogService;
    @SkipAuth
    @GetMapping
    public BaseResponse<List<BlogResponce>> getBlog() {
        return BaseResponse.ok(blogService.getBlogs());
    }
    @PostMapping
    public BaseResponse<Void> createBlog(@RequestBody CreateBlogRequest createBlogRequest) {
        final Blogs blog = blogService.createBlog(createBlogRequest);
        return BaseResponse.ok(null);
    }

    @PostMapping("update/{id}")
    public BaseResponse<Void> updateBlog(@PathVariable("id") Long id, @RequestBody CreateBlogRequest request) {
        blogService.updateBlog(request, id);
        return BaseResponse.ok(null);
    }

    @SkipAuth
    @GetMapping("{id}")
    public BaseResponse<BlogResponce> getBlogById(@PathVariable("id") Long id) throws Exception {
        BlogResponce blogResponce =  blogService.getBlogById(id);
        return BaseResponse.ok(blogResponce);
    }
}
