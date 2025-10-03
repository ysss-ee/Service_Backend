package com.work.service.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.work.service.constant.StateEnum;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * &#064;TableName  post
 */
@TableName(value = "post")
@Data
@Builder
public class Post {
    @TableId(value = "postId", type = IdType.AUTO)
    private Integer postId;
    private Integer userId;
    private String title;
    private String content;
    private Integer level;
    private Integer hide;
    private List<Response> response;
    private String comment;
    private Timestamp createTime;
    private Integer state;
    private Integer acceptUserId;
    private String image;


}
