package com.yikolemon.blogbackground.service.impl;

import com.yikolemon.blogbackground.dao.MetaWeblogClient;
import com.yikolemon.blogbackground.entity.po.Blog;
import com.yikolemon.blogbackground.entity.po.Category;
import com.yikolemon.blogbackground.exception.FetchBlogException;
import com.yikolemon.blogbackground.mapper.ConfigMapper;
import com.yikolemon.blogbackground.schedule.CronTaskRegistrar;
import com.yikolemon.blogbackground.schedule.CnblogsSyncRunnable;
import com.yikolemon.blogbackground.service.BlogService;
import com.yikolemon.blogbackground.service.CategoryService;
import com.yikolemon.blogbackground.service.CnblogsSyncService;
import com.yikolemon.blogbackground.util.ConfigKeyUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yikolemon
 * @date 2023/10/14 23:58
 * @description
 */
@Service
public class CnblogsSyncServiceImpl implements CnblogsSyncService {

    @Resource
    ConfigMapper configMapper;

    @Resource
    CronTaskRegistrar cronTaskRegistrar;

    @Resource
    BlogService blogService;
    @Resource
    CategoryService categoryService;
    @Resource
    MetaWeblogClient metaWeblogClient;


    @Override
    public void switchSyncCnblogs() {
        String enable = configMapper.selectValueByKey(ConfigKeyUtil.CNBLOGS_SYNC_ENABLE);
        if ("true".equals(enable)){
            cronTaskRegistrar.removeCronTask(new CnblogsSyncRunnable());
            configMapper.updateValueByKey(ConfigKeyUtil.CNBLOGS_SYNC_ENABLE,"false");
        }else{
            configMapper.updateValueByKey(ConfigKeyUtil.CNBLOGS_SYNC_ENABLE,"true");
            String core = configMapper.selectValueByKey(ConfigKeyUtil.CNBLOGS_SYNC_CORE);
            cronTaskRegistrar.addCronTask(new CnblogsSyncRunnable(),core);
        }
    }

    @Override
    public void syncCnblogs() {
        int pageNum=1;
        List<Blog> allBlogList=new ArrayList<>();
        while (true){
            List<String> blogIdsList = metaWeblogClient.getBlogList(pageNum);
            if (blogIdsList.isEmpty()){
                break;
            }
            List<Blog> blogList = getBlogList(blogIdsList);
            allBlogList.addAll(blogList);
            pageNum++;
        }
        List<Category> categoryList = getCategoryList(allBlogList);
        categoryService.saveOrUpdateByName(categoryList);
        List<Category> dataBaseCategoryList = categoryService.list();
        Map<String, String> categoryNameToId = dataBaseCategoryList.stream().collect(Collectors.toMap(Category::getName, Category::getId));
        allBlogList.forEach(blog -> blog.setCategoryId(categoryNameToId.getOrDefault(blog.getCategoryStr(),null)));
        blogService.saveOrUpdateBatchIgnoreLogicDelete(allBlogList);
    }

    private List<Category> getCategoryList(List<Blog> blogList){
        Set<String> collect = blogList.stream().map(Blog::getCategoryStr).collect(Collectors.toSet());
        return collect.stream().map(Category::new).collect(Collectors.toList());
    }


    private List<Blog> getBlogList(List<String> blogIdsList){
        if (blogIdsList.isEmpty()){
            return Collections.emptyList();
        }
        ArrayList<Blog> blogList = new ArrayList<>();
        for (String blogId : blogIdsList) {
            Blog blog=null;
            try {
                blog= metaWeblogClient.getPost(blogId);
            } catch (FetchBlogException e) {
                e.printStackTrace();
            }
            if (blog!=null){
                blogList.add(blog);
            }
        }
        return blogList;
    }

}
