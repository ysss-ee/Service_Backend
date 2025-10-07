package com.work.service.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PublishRequest {
    private String title;
    private String content;
    private Integer level;
    private Integer hide;


}
