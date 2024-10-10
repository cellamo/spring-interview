package com.iotiq.interview.repository;

import com.iotiq.interview.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    List<Category> findAllByNameContainingIgnoreCase(String name);
}
