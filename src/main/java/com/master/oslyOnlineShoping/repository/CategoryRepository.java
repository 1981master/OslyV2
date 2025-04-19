package com.master.oslyOnlineShoping.repository;

import com.master.oslyOnlineShoping.entity.products.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String categoryName);
}
