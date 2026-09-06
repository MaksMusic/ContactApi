package org.example.aicore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ExtractResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private String rawText;
}
