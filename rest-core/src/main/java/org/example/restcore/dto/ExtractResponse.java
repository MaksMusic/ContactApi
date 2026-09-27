package org.example.restcore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Плоский ответ от ai-core (внутренний контракт).
 * Поля соответствуют контракту в architecture/contracts.md раздел "REST-CORE → AI-CORE".
 * model и rawText здесь отсутствуют — они не покидают ai-core.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExtractResponse {

    private String status;
    private String organizationName;
    private String full;
    private String country;
    private String city;
    private String street;
    private String building;
    private String postalCode;
    private String phones;
    private Double confidence;
}
