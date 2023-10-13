package com.yikolemon.blogbackground.schedule;

import com.yikolemon.blogbackground.service.BlogService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
/**
 * @author yikolemon
 * @date 2023/10/14 0:29
 * @description
 */
@Component
public class SyncCnblogsSchedule {

    @Resource
    BlogService blogService;

    //@Scheduled(cron = "0 0 */3 * * ?")
    @Scheduled(cron = "0/10 * * * * ?")
    public void syncCnblogs(){
        blogService.syncCnblogs();
    }

}
