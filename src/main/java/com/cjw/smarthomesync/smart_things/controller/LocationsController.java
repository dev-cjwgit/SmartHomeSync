package com.cjw.smarthomesync.smart_things.controller;

import com.cjw.smarthomesync.common.domain.ResponseDto;
import com.cjw.smarthomesync.smart_things.service.LocationsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/locations")
public class LocationsController {
    private final LocationsService locationsService;

    @GetMapping()
    public ResponseEntity<ResponseDto<Map<?, ?>>> getLoactionsData() {
        Map<?, ?> result = locationsService.getLocationsData();

        return ResponseEntity.ok(ResponseDto.<Map<?, ?>>builder()
                .result(true)
                .message("데이터 요청 완료")
                .size(1)
                .data(Collections.singletonList(result))
                .build());
    }
}
