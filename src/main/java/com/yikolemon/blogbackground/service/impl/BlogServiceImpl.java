package com.yikolemon.blogbackground.service.impl;

import com.yikolemon.blogbackground.entity.po.Blog;
import com.yikolemon.blogbackground.mapper.BlogMapper;
import com.yikolemon.blogbackground.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yikolemon
 * @since 2023-10-08
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {


}
