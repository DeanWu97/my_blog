package com.dean.my_blog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dean.my_blog.controllers.requests.CreateBlogRequest;
import com.dean.my_blog.controllers.responces.BlogResponce;
import com.dean.my_blog.converter.BlogConverter;
import com.dean.my_blog.entity.Blogs;
import com.dean.my_blog.mapper.IBlogsService;
import com.dean.my_blog.threadLocals.UserThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BlogService {

    @Autowired
    private BlogConverter blogConverter;
    @Autowired
    private IBlogsService blogsService;

    public void updateBlog(CreateBlogRequest request, Long id) {
        final Blogs requestToBlog = blogConverter.createRequestToBlog(request);
        requestToBlog.setId(id);
        blogsService.updateById(requestToBlog);
        return ;
    }

    public Blogs createBlog(CreateBlogRequest createBlogRequest) {
        final Blogs blog = blogConverter.createRequestToBlog(createBlogRequest);
        blogsService.save(blog);
        return blog;
    }
    public List<BlogResponce> getBlogs(){
        QueryWrapper<Blogs> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("visible", Blogs.BlogVisibleEnum.ALL.getValue());
        if (UserThreadLocal.notGuest()) {
            queryWrapper.or().eq("visible", Blogs.BlogVisibleEnum.SELF.getValue());
            queryWrapper.and(blogsQueryWrapper -> blogsQueryWrapper.eq("author_id", UserThreadLocal.get().getId()));
        }
        final List<Blogs> blogs = blogsService.list(queryWrapper);
        final List<BlogResponce> collect = blogs.stream().map(blogItem -> blogConverter.blogToResponce(blogItem)).collect(Collectors.toList());
        return collect;
    }

    public BlogResponce getBlogById(Long id) throws Exception {
        final Blogs byId = blogsService.getById(id);
        if (!Objects.equals(byId.getAuthorId(), UserThreadLocal.get().getId())) {
            throw new Exception("not accessible！");
        }
        return blogConverter.blogToResponce(byId);
    }
}
