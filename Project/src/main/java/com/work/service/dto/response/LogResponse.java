package com.work.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LogResponse {
    private Integer userType;
    private String token;

}
