package com.yikolemon.blogbackground.service;

import org.springframework.stereotype.Service;

/**
 * @author yikolemon
 * @date 2023/10/14 23:56
 * @description
 */
@Service
public interface CnblogsSyncService {

    void switchSyncCnblogs();

    void syncCnblogs();

}
