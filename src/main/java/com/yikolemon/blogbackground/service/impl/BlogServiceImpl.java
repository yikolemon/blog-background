package com.yikolemon.blogbackground.service.impl;

import com.yikolemon.blogbackground.dao.MetaWeblogClient;
import com.yikolemon.blogbackground.entity.po.Blog;
import com.yikolemon.blogbackground.exception.FetchBlogException;
import com.yikolemon.blogbackground.mapper.BlogMapper;
import com.yikolemon.blogbackground.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    MetaWeblogClient metaWeblogClient;

    @Override
    public void syncCnblogs() {
        int pageNum=1;
        while (true){
            List<String> blogIdsList = metaWeblogClient.getBlogList(pageNum);
            if (blogIdsList.isEmpty()){
                break;
            }
            List<Blog> blogList = getBlogList(blogIdsList);
            saveOrUpdateBatch(blogList);
            pageNum++;
        }
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
