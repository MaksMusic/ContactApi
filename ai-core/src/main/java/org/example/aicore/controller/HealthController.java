package org.example.aicore.controller;


import lombok.extern.slf4j.Slf4j;
import org.example.aicore.dto.HealthResponse;
import org.example.aicore.enums.HealthStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/internal/v1")
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity <HealthResponse> health() {
        log.debug("Вызвана проверка работоспособности");
        return  ResponseEntity.ok(new HealthResponse(HealthStatus.UP));
    }


}
