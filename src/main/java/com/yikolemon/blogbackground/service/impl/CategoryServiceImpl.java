package com.yikolemon.blogbackground.service.impl;

import com.yikolemon.blogbackground.entity.po.Category;
import com.yikolemon.blogbackground.mapper.CategoryMapper;
import com.yikolemon.blogbackground.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public void saveOrUpdateByName(List<Category> categoryList) {
        List<Category> dataBaseCategoryList = categoryMapper.selectList(null);
        Set<String> dataBaseCategoryNameSet = dataBaseCategoryList.stream().map(Category::getName).collect(Collectors.toSet());
        categoryList = categoryList.stream().filter(category -> (!dataBaseCategoryNameSet.contains(category.getName())&&!"".equals(category.getName()))).collect(Collectors.toList());
        this.saveBatch(categoryList);
    }
}
