package com.work.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class InformationResponse {
    private Integer userId;
    private String username;
    private Integer userType;
    private String sex;
    private String email;
    private String college;
    private String major;
    private String grade;
    private String phone;
}
