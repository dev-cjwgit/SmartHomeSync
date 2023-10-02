package com.cjw.smarthomesync.smart_things.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocationsServiceImpl implements LocationsService {
    private final WebClient webClient;

    @Override
    public Map<?, ?> getLocationsData() {
        return (Map<?, ?>) webClient
                .get()
                .uri("/locations")
                .retrieve()
                .bodyToMono(Map.class)
                .map(json -> json).block();
    }
}
