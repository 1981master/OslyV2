package com.master.oslyOnlineShoping.controller;

import com.master.oslyOnlineShoping.entity.products.Category;
import com.master.oslyOnlineShoping.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    // Fetch all categories
    @GetMapping
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Add a new category
    @PostMapping
    public Category addCategory(@RequestBody Category category) {
        return categoryRepository.save(category);
    }
}

