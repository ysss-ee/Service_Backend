package com.work.service.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PublishRequest {
    //@JsonProperty("userId")
    //private Integer userId;
    private String title;
    private String content;
    private Integer level;
    private Integer hide;


}
