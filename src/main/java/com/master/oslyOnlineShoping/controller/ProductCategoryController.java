//package com.master.oslyOnlineShoping.controller;
//
//import com.master.oslyOnlineShoping.entity.products.ProductCategory;
//import com.master.oslyOnlineShoping.repository.ProductCategoryRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/categories")
//@CrossOrigin(origins = "*")
//public class ProductCategoryController {
//
//    @Autowired
//    private ProductCategoryRepository productCategoryRepository;
//
//    // Endpoint to fetch all categories
//    @GetMapping
//    public List<ProductCategory> getAllCategories() {
//        return productCategoryRepository.findAll();
//    }
//
//    // Endpoint to add a category
//    @PostMapping
//    public ProductCategory addCategory(@RequestBody ProductCategory productCategory) {
//        return productCategoryRepository.save(productCategory);
//    }
//}
