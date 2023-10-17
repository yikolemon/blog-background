package com.yikolemon.blogbackground.mapper;

import com.yikolemon.blogbackground.entity.po.Blog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yikolemon
 * @since 2023-10-08
 */

public interface BlogMapper extends BaseMapper<Blog> {

    List<Blog> getBlogList();

    int updateBlogIgnoreLogicDelete(Blog blog);
}
