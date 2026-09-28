package org.example.restcore.mapper;

import org.example.restcore.dto.AddressDto;
import org.example.restcore.dto.ExtractResponse;
import org.example.restcore.dto.SearchResponse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SearchResponseMapperTest {

    private final SearchResponseMapper mapper = new SearchResponseMapper();

    @Test
    void map_shouldTransformExtractToSearchResponse() {
        ExtractResponse extract = new ExtractResponse();
        extract.setStatus("FOUND");
        extract.setOrganizationName("Макдоналдс");
        extract.setFull("Россия, Москва, ул. Тверская, д. 1");
        extract.setCountry("RU");
        extract.setCity("Москва");
        extract.setStreet("ул. Тверская");
        extract.setBuilding("1");
        extract.setPostalCode("123456");
        extract.setPhones("+74951234567");
        extract.setConfidence(0.86);

        SearchResponse response = mapper.map(extract, "макдак на тверской");

        assertEquals("FOUND", response.getStatus());
        assertEquals("Макдоналдс", response.getOrganizationName());
        assertEquals("макдак на тверской", response.getNormalizedQuery());
        assertNotNull(response.getAddress());
        assertEquals("Россия, Москва, ул. Тверская, д. 1", response.getAddress().getFull());
        assertEquals("RU", response.getAddress().getCountry());
        assertEquals("Москва", response.getAddress().getCity());
        assertEquals("ул. Тверская", response.getAddress().getStreet());
        assertEquals("1", response.getAddress().getBuilding());
        assertEquals("123456", response.getAddress().getPostalCode());
        assertEquals(List.of("+74951234567"), response.getPhones());
        assertEquals(0.86, response.getConfidence());
        assertNull(response.getMessage());
    }

    @Test
    void map_shouldReturnEmptyPhonesWhenNull() {
        ExtractResponse extract = new ExtractResponse();
        extract.setStatus("FOUND");
        extract.setPhones(null);

        SearchResponse response = mapper.map(extract, "test");

        assertTrue(response.getPhones().isEmpty());
    }

    @Test
    void map_shouldReturnEmptyPhonesWhenBlank() {
        ExtractResponse extract = new ExtractResponse();
        extract.setStatus("FOUND");
        extract.setPhones("");

        SearchResponse response = mapper.map(extract, "test");

        assertTrue(response.getPhones().isEmpty());
    }

    @Test
    void map_shouldWrapSinglePhoneInList() {
        ExtractResponse extract = new ExtractResponse();
        extract.setStatus("FOUND");
        extract.setPhones("+79001234567");

        SearchResponse response = mapper.map(extract, "test");

        assertEquals(1, response.getPhones().size());
        assertEquals("+79001234567", response.getPhones().get(0));
    }

    @Test
    void map_shouldPreserveNullAddressFields() {
        ExtractResponse extract = new ExtractResponse();
        extract.setStatus("NOT_FOUND");
        extract.setOrganizationName(null);
        extract.setFull(null);
        extract.setCountry(null);
        extract.setCity(null);
        extract.setStreet(null);
        extract.setBuilding(null);
        extract.setPostalCode(null);
        extract.setPhones("");
        extract.setConfidence(null);

        SearchResponse response = mapper.map(extract, "test");

        AddressDto address = response.getAddress();
        assertNotNull(address);
        assertNull(address.getFull());
        assertNull(address.getCountry());
        assertNull(address.getCity());
        assertNull(address.getStreet());
        assertNull(address.getBuilding());
        assertNull(address.getPostalCode());
    }
}
