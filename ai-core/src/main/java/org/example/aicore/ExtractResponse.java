package org.example.aicore;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ExtractResponse {
    private int id;
    private String status;
    private String organizationName;
    private String full;
    private String country;
    private String city;
    private String street;
    private String building;
    private String postalCode;
    private String phones;
    private String confidence;
    private String model;
    @JsonIgnore
    private String rawText;
}
