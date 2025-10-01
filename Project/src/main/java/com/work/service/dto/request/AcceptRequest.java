package com.work.service.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AcceptRequest {
   // private Integer userId;
    private Integer postId;
}
