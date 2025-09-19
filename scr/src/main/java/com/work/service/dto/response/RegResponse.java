package com.work.service.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegResponse {
    @JsonProperty("userId")
    @NotNull
    private Integer userId;
}
