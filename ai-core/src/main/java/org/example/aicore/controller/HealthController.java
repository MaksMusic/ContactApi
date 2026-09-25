package org.example.aicore.controller;


import lombok.extern.slf4j.Slf4j;
import org.example.aicore.ExtractRequest;
import org.example.aicore.dto.HealthResponse;
import org.example.aicore.enums.HealthStatus;
import org.example.aicore.error.ErrorResponse;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/internal/v1")
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity <HealthResponse> health() {
        log.debug("Вызвана проверка работоспособности");
        return  ResponseEntity.ok(new HealthResponse(HealthStatus.UP));
    }


    @PostMapping("/extract")
    public ResponseEntity <?> extract(@RequestBody ExtractRequest extractRequest) {
        if(extractRequest.getText()==null || extractRequest.getText().trim().isEmpty()){
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Укажите text"));
        }


        return ResponseEntity.ok(Map.of("status", "NOT_FOUND"));
    }



}
