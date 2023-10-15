package com.yikolemon.blogbackground.entity.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yikolemon
 * @since 2023-10-08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Blog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String id;
    private String title;
    private String content;
    private Boolean recommend;
    private Date createTime;
    private Date updateTime;
    private Integer view;
    @TableField(value = "is_deleted",fill = FieldFill.INSERT)
    private Integer deleted;
    private String tagStr;
    private String categoryStr;
    private String categoryId;

}
