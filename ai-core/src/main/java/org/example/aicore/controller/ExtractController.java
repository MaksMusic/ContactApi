package org.example.aicore.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.aicore.ExtractRequest;
import org.example.aicore.ExtractResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/internal/v1")
public class ExtractController {

    @PostMapping("/extract")
    public ResponseEntity<ExtractResponse> extract(@RequestBody ExtractRequest request) {
        log.debug("Вызван extract, text = {}", request.getText());

        ExtractResponse response = new ExtractResponse();
        response.setStatus("NOT_FOUND");
        response.setOrganizationName(null);
        response.setFull(null);
        response.setCountry(null);
        response.setCity(null);
        response.setStreet(null);
        response.setBuilding(null);
        response.setPostalCode(null);
        response.setPhones("");
        response.setConfidence(null);
        response.setModel(null);
        response.setRawText(null);

        return ResponseEntity.ok(response);
    }
}
