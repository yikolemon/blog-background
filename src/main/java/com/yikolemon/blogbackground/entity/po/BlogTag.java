package com.yikolemon.blogbackground.entity.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

/**
 * @author yikolemon
 * @since 2023-10-08
 */
@Data
public class BlogTag {

    private static final long serialVersionUID = 1L;

    private String blogId;
    private String tagId;
    @TableField(value = "is_deleted")
    private Integer deleted;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

}
