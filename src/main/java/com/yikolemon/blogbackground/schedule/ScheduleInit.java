package com.yikolemon.blogbackground.schedule;

import com.yikolemon.blogbackground.mapper.ConfigMapper;
import com.yikolemon.blogbackground.util.ConfigKeyUtil;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author yikolemon
 * @date 2023/10/14 23:38
 * @description
 */
@Component
public class ScheduleInit implements ApplicationRunner {

    @Resource
    CronTaskRegistrar cronTaskRegistrar;

    @Resource
    ConfigMapper configMapper;

    @Override
    public void run(ApplicationArguments args){
        syncCnblogsInit();
    }

    private void syncCnblogsInit(){
        String enableSync = configMapper.selectValueByKey(ConfigKeyUtil.CNBLOGS_SYNC_ENABLE);
        if (!"true".equals(enableSync)){
            return;
        }
        String core = configMapper.selectValueByKey(ConfigKeyUtil.CNBLOGS_SYNC_CORE);
        cronTaskRegistrar.addCronTask(new CnblogsSyncRunnable(),core);
    }

    //TODO 可动态开启/关闭同步
    //https://cloud.tencent.com/developer/article/1675699
}
