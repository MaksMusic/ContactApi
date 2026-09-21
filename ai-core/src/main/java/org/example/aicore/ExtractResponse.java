package org.example.aicore;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

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
    private String model;
    @JsonIgnore
    private String rawText;
}
