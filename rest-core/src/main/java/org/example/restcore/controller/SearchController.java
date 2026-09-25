package org.example.restcore.controller;

import org.example.restcore.dto.SearchRequest;
import org.example.restcore.dto.SearchResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contacts")
public class SearchController {

    @PostMapping("/search")
    public SearchResponse search(@RequestBody SearchRequest request) {
        SearchResponse response = new SearchResponse();
        response.setStatus("NOT_FOUND");
        response.setOrganizationName(null);
        response.setNormalizedQuery(request.getQuery());
        response.setAddress(null);
        response.setPhones(List.of());
        response.setConfidence(null);
        response.setMessage("Поиск пока не подключен");
        return response;
    }
}
