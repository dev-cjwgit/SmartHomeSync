package com.cjw.smarthomesync.smart_things.controller;

import com.cjw.smarthomesync.common.domain.AuthEntity;
import com.cjw.smarthomesync.common.domain.ResponseDto;
import com.cjw.smarthomesync.common.exception.BaseException;
import com.cjw.smarthomesync.common.exception.ErrorMessage;
import com.cjw.smarthomesync.smart_things.service.RoomsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<ResponseDto<Map<?, ?>>> getRoomsData(final Authentication authentication, @PathVariable("location_id") String locationId) {
        Object temp = authentication.getPrincipal();
        if (!(temp instanceof AuthEntity))
            throw new BaseException(ErrorMessage.UNDEFINED_EXCEPTION);

        AuthEntity auth = (AuthEntity) temp;

        Map<?, ?> result = roomsService.getRoomsData(locationId);

        return ResponseEntity.ok(ResponseDto.<Map<?, ?>>builder()
                .result(true)
                .message("데이터 요청 완료")
                .size(1)
                .data(Collections.singletonList(result))
                .build());
    }
}
