package com.yikolemon.blogbackground.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yikolemon.blogbackground.entity.po.Blog;
import com.yikolemon.blogbackground.service.BlogService;
import org.springframework.stereotype.Service;
import com.yikolemon.blogbackground.mapper.BlogMapper;

import javax.annotation.Resource;
import java.util.*;
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
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

    @Resource
    BlogMapper blogMapper;

    @Override
    public void saveOrUpdateBatchIgnoreLogicDelete(List<Blog> blogList) {
        List<Blog> updateBlogList=new ArrayList<>();
        List<Blog> dataBaseBlogList = blogMapper.getBlogList();
        Set<Blog> dataBaseBlogSet = new HashSet<>(dataBaseBlogList);
        Set<String> dataBaseBlogIdSet = dataBaseBlogList.stream().map(Blog::getId).collect(Collectors.toSet());
        Iterator<Blog> iterator = blogList.iterator();
        while (iterator.hasNext()){
            Blog next = iterator.next();
            if (dataBaseBlogSet.contains(next)){
                iterator.remove();
            }else {
                if (dataBaseBlogIdSet.contains(next.getId())){
                    updateBlogList.add(next);
                    iterator.remove();
                }
            }
        }
        this.saveBatch(blogList);
        for (Blog blog : updateBlogList) {
            blogMapper.updateBlogIgnoreLogicDelete(blog);
        }
    }
}
