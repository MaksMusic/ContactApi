package org.example.restcore.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "contact_searches", indexes = {
    @Index(name = "idx_query", columnList = "query"),
    @Index(name = "idx_city", columnList = "city")
})
public class ContactSearch{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String query;

    @Column
    private String city;

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

}
