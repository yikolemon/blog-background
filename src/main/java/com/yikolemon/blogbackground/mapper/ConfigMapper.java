package com.yikolemon.blogbackground.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yikolemon.blogbackground.entity.po.Config;

/**
 * @author yikolemon
 * @date 2023/10/14 20:48
 * @description
 */
public interface ConfigMapper extends BaseMapper<Config> {

    String selectValueByKey(String key);

    int updateValueByKey(String key,String value);

}
