package com.yikolemon.blogbackground.service.impl;

import com.yikolemon.blogbackground.mapper.ConfigMapper;
import com.yikolemon.blogbackground.schedule.CronTaskRegistrar;
import com.yikolemon.blogbackground.schedule.CnblogsSyncRunnable;
import com.yikolemon.blogbackground.service.CnblogsSyncService;
import com.yikolemon.blogbackground.util.ConfigKeyUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
}
