package com.yikolemon.blogbackground.service.impl;

import com.yikolemon.blogbackground.entity.po.Category;
import com.yikolemon.blogbackground.mapper.CategoryMapper;
import com.yikolemon.blogbackground.service.CategoryService;
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
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

}
