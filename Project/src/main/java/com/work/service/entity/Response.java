package com.work.service.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * &#064;TableName  response
 */
@TableName(value = "response")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Response {
    @TableId(value = "user_id")
    private Integer userId;
    private Integer postId;
    private String content;
    private Timestamp createTime;
}
