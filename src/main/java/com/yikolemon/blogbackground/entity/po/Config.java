package com.yikolemon.blogbackground.entity.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * @author yikolemon
 * @date 2023/10/14 20:46
 * @description
 */
@Data
public class Config {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String key;
    private String value;
    @TableLogic(value = "is_deleted")
    private Integer deleted;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;


}
