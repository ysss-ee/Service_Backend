package com.work.service.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * &#064;TableName  response
 */
@TableName(value ="response")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Response {
    private Integer userId;
    private Integer postId;
    private String content;
}
