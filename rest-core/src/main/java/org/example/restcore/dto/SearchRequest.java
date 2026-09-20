package org.example.restcore.dto;

import jakarta.validation.constraints.NotBlank;

public class SearchRequest {
    @NotBlank(message = "поле не может быть пустым")
    private String query;
    private String city;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}


