package com.cjw.smarthomesync.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ResponseDto<T> {
    private Boolean result;
    private String message;
    private Integer size;
    private List<T> data;
}
