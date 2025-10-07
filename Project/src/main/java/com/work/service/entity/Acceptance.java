package com.work.service.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

@TableName(value = "acceptance")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Acceptance {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer postId;
    private Timestamp createTime;
    private Integer state;
    private Date deleteTime;
}
