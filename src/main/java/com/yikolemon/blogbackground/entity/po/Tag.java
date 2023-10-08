package com.yikolemon.blogbackground.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yikolemon
 * @since 2023-10-08
 */
@Data
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String name;
    private Integer deleted;

}
