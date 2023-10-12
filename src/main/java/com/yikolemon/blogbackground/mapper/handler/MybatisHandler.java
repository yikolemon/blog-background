package com.yikolemon.blogbackground.mapper.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MybatisHandler implements MetaObjectHandler {
	@Override
	public void insertFill(MetaObject metaObject) {
		//属性名
		this.setFieldValByName("createTime", new Date(), metaObject);
	}
 
	@Override
	public void updateFill(MetaObject metaObject) {
		//属性名
		this.setFieldValByName("updateTime", new Date(), metaObject);
	}
}