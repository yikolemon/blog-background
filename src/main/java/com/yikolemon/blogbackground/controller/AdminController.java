package com.yikolemon.blogbackground.controller;

import com.yikolemon.blogbackground.service.CnblogsSyncService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @author yikolemon
 * @date 2023/10/14 23:47
 * @description
 */

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Resource
    CnblogsSyncService cnblogsSyncService;

    @RequestMapping("/toggleSyncCnblogs")
    public void toggleSyncCnblogs(){
        cnblogsSyncService.switchSyncCnblogs();
    }
}
