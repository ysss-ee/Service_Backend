package com.work.service.dto.request;

import lombok.Data;

@Data
public class MarkRequest {
    private Integer postId;
    private String reason;
}
