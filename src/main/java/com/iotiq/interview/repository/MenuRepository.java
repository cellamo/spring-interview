package com.iotiq.interview.repository;

import com.iotiq.interview.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MenuRepository extends JpaRepository<Menu, UUID> {
    List<Menu> findAllByCategory(String name);
}
