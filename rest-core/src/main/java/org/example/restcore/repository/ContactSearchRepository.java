package org.example.restcore.repository;

import org.example.restcore.entity.ContactSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactSearchRepository extends JpaRepository<ContactSearch, Long> {
}
