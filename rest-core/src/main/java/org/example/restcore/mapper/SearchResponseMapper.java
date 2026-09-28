package org.example.restcore.mapper;

import org.example.restcore.dto.AddressDto;
import org.example.restcore.dto.ExtractResponse;
import org.example.restcore.dto.SearchResponse;

import java.util.Collections;
import java.util.List;

/**
 * Преобразует плоский ответ от ai-core (ExtractResponse)
 * в вложенный JSON для клиента (SearchResponse).
 */
public class SearchResponseMapper {

    /**
     * @param extract         плоский ответ от ai-core
     * @param normalizedQuery нормализованный запрос клиента
     * @return SearchResponse для клиента
     */
    public SearchResponse map(ExtractResponse extract, String normalizedQuery) {
        SearchResponse response = new SearchResponse();

        response.setStatus(extract.getStatus());
        response.setOrganizationName(extract.getOrganizationName());
        response.setNormalizedQuery(normalizedQuery);
        response.setAddress(buildAddress(extract));
        response.setPhones(buildPhones(extract.getPhones()));
        response.setConfidence(extract.getConfidence());
        response.setMessage(null);

        return response;
    }

    private AddressDto buildAddress(ExtractResponse extract) {
        AddressDto address = new AddressDto();
        address.setFull(extract.getFull());
        address.setCountry(extract.getCountry());
        address.setCity(extract.getCity());
        address.setStreet(extract.getStreet());
        address.setBuilding(extract.getBuilding());
        address.setPostalCode(extract.getPostalCode());
        return address;
    }

    private List<String> buildPhones(String phones) {
        if (phones == null || phones.isBlank()) {
            return Collections.emptyList();
        }
        return List.of(phones);
    }
}
