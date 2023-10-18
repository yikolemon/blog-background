package com.yikolemon.blogbackground.entity.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yikolemon
 * @since 2023-10-08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String name;
    @TableLogic
    @TableField(value = "is_deleted")
    private Integer deleted;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    public Category(String name) {
        this.name = name;
    }
}
