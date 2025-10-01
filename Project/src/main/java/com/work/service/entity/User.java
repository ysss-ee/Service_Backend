package com.work.service.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * &#064;TableName  user
 */
@TableName(value ="user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @TableId(value = "userId" ,type = IdType.AUTO )
    private Integer userId;

    private String username;

    private String password;

    private Integer userType;

    private String sex;

    private String email;

    private String picture;

    private String college;

    private String major;

    private String grade;

    private String phone;

}