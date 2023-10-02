package com.cjw.smarthomesync.smart_things.controller;

import com.cjw.smarthomesync.common.domain.ResponseDto;
import com.cjw.smarthomesync.smart_things.service.RoomsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomsController {
    private final RoomsService roomsService;

    @GetMapping("/{location_id}")
    public ResponseEntity<ResponseDto<Map<?, ?>>> getRoomsData(@PathVariable("location_id") String locationId) {
        Map<?, ?> result = roomsService.getRoomsData(locationId);

        return ResponseEntity.ok(ResponseDto.<Map<?, ?>>builder()
                .result(true)
                .message("데이터 요청 완료")
                .size(1)
                .data(Collections.singletonList(result))
                .build());
    }
}
