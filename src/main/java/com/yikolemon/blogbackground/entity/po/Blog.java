package com.yikolemon.blogbackground.entity.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yikolemon
 * @since 2023-10-08
 */
@Data
public class Blog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String title;
    private String content;
    @TableLogic
    private Boolean recommend;
    private Date createTime;
    private Date updateTime;
    private Integer view;
    @TableField(value = "is_deleted")
    private Integer deleted;
    private String tagStr;
    private String categoryStr;
    private String categoryId;

}
