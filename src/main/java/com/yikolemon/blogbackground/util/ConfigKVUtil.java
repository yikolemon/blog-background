//package com.yikolemon.blogbackground.util;
//
//import com.yikolemon.blogbackground.mapper.ConfigMapper;
//import org.springframework.context.annotation.DependsOn;
//import org.springframework.core.env.Environment;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//
//import javax.annotation.Resource;
//
///**
// * @author yikolemon
// * @date 2023/10/14 20:47
// * @description
// */
//
//@Component
//public class ConfigKVUtil {
//
//    @Resource
//    ConfigMapper configMapper;
//
//    @Resource
//    Environment environment;
//
//    public String getValue(String key){
//        String value = environment.getProperty(key);
//        if (StringUtils.isEmpty(value)){
//            value=configMapper.selectValueByKey(key);
//        }
//        return value;
//    }
//}
