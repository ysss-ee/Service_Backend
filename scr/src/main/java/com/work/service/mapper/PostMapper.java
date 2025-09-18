package com.work.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.work.service.entity.Post;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author hp
 * @description 针对表【post】的数据库操作Mapper
 * @createDate 2025-08-27 21:44:36
 * @Entity com.example.demo.entity.Post
 */
@Mapper
public interface PostMapper extends BaseMapper<Post> {

}


