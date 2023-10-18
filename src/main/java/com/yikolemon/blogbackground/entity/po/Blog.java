package com.yikolemon.blogbackground.entity.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Blog blog = (Blog) o;
        return Objects.equals(id, blog.id) && Objects.equals(title, blog.title) && Objects.equals(content, blog.content) && Objects.equals(recommend, blog.recommend) && Objects.equals(createTime, blog.createTime) && Objects.equals(updateTime, blog.updateTime) && Objects.equals(view, blog.view) && Objects.equals(deleted, blog.deleted) && Objects.equals(tagStr, blog.tagStr) && Objects.equals(categoryStr, blog.categoryStr) && Objects.equals(categoryId, blog.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, recommend, createTime, updateTime, view, deleted, tagStr, categoryStr, categoryId);
    }
}
