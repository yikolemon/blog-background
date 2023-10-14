package com.yikolemon.blogbackground.schedule;

import com.yikolemon.blogbackground.service.BlogService;
import com.yikolemon.blogbackground.util.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CnblogsSyncRunnable implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(CnblogsSyncRunnable.class);

    @Override
    public void run() {
        logger.info("{},定时任务执行结束","博客园博客同步");
        try {
            BlogService blogService = SpringUtil.getBean(BlogService.class);
            blogService.syncCnblogs();
        } catch (Exception ex) {
            logger.error("{},定时任务执行结束 ===> {}","博客园博客同步 ", ex.getMessage());
        }
        logger.info("{},定时任务执行结束","博客园博客同步");
    }

    @Override
    public int hashCode() {
        return "SyncCnblogsRunnable".hashCode();
    }

    //用于从Map中删除这个任务
    @Override
    public boolean equals(Object obj) {
        return obj instanceof CnblogsSyncRunnable;
    }
}