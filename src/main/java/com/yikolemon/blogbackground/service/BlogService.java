package com.yikolemon.blogbackground.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yikolemon.blogbackground.entity.po.Blog;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yikolemon
 * @since 2023-10-08
 */
public interface BlogService extends IService<Blog> {

    void saveOrUpdateBatchIgnoreLogicDelete(List<Blog> blogList);

}
