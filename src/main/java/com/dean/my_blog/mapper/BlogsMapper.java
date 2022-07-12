package com.dean.my_blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dean.my_blog.entity.Blogs;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BlogsMapper extends BaseMapper<Blogs> {
//public interface BlogsMapper extends IService<Blogs> {
}
