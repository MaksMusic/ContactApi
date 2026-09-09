package org.example.aicore.controller;


import lombok.extern.slf4j.Slf4j;
import org.example.aicore.dto.HealthResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/internal/v1")
public class HealthController {

    @GetMapping("/health")
    public HealthResponse health() {
        log.info("Вызвана проверка работоспособности");
        return new HealthResponse("UP");
    }


}
