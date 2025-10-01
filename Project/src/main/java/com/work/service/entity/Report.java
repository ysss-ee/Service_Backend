package com.work.service.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * &#064;TableName  report
 */
@TableName(value ="report")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Report {
    @TableId(value = "reportId",type = IdType.AUTO )
    private Integer reportId;

    private Integer postId;

    private String content;

    private String reason;

    private Integer approval;
}