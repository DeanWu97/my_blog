package com.dean.my_blog.mapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dean.my_blog.entity.Blogs;
import org.springframework.stereotype.Service;

@Service
public class IBlogsServiceImpl extends ServiceImpl<BlogsMapper, Blogs> implements IBlogsService {
}
