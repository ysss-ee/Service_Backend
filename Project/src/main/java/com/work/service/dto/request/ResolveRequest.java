package com.work.service.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResolveRequest {
    private Integer acceptanceId;
    private Integer postId;
}
