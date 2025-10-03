package com.work.service.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * &#064;TableName  message
 */
@TableName(value ="message")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {
    @TableId(value = "id",type = IdType.AUTO )
    private Integer id;
    private Integer acceptUserId;
    private String content;
    private Timestamp createTime;

}

