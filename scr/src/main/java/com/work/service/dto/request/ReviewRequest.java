package com.work.service.dto.request;

import lombok.Data;

@Data
public class ReviewRequest {
    private Integer userId;
    private Integer reportId;
    private Integer approval;
}
