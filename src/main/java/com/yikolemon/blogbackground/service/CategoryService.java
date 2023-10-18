package com.yikolemon.blogbackground.service;

import com.yikolemon.blogbackground.entity.po.Category;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yikolemon
 * @since 2023-10-08
 */
public interface CategoryService extends IService<Category> {

    void saveOrUpdateByName(List<Category> categoryList);

}
