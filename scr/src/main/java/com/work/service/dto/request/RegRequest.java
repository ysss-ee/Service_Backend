package com.work.service.dto.request;

import lombok.Data;

@Data
public class RegRequest {
    private String username;
    private String password;
    private String email;
}
