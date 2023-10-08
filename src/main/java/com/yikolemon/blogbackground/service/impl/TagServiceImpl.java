package com.yikolemon.blogbackground.service.impl;

import com.yikolemon.blogbackground.entity.po.Tag;
import com.yikolemon.blogbackground.mapper.TagMapper;
import com.yikolemon.blogbackground.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yikolemon
 * @since 2023-10-08
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

}
