package com.master.oslyOnlineShoping.controller;

import com.master.oslyOnlineShoping.dto.ProductDTO;
import com.master.oslyOnlineShoping.entity.products.Category;
import com.master.oslyOnlineShoping.entity.products.Product;
import com.master.oslyOnlineShoping.entity.products.Store;
import com.master.oslyOnlineShoping.repository.CategoryRepository;
import com.master.oslyOnlineShoping.repository.ProductRepository;
import com.master.oslyOnlineShoping.repository.StoreRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // Fetch all products
    @PostMapping("/products")
    @Transactional
    public ResponseEntity<?> addProduct(@RequestBody ProductDTO dto) {
        logger.info("Received product DTO: {}", dto);
        logger.info("Store in DTO: {}", dto.getStore());
        logger.info("Image URL length: {}", dto.getImage().length());

        if (dto.getStore() != null) {
            logger.info("Store Name: {}", dto.getStore().getName());
        } else {
            logger.warn("Store is null in incoming DTO!");
        }



        // Check if store exists, else create it
        Optional<Store> optionalStore = storeRepository.findByName(dto.getStore().getName());
        Store store = optionalStore.orElseGet(() -> {
            Store newStore = new Store();
            newStore.setName(dto.getStore().getName());
            storeRepository.save(newStore);
            return newStore;
        });

        // Check if category exists, else create it
        logger.info("Category  name is: {}", dto.getCategory().getName());
        Optional<Category> optionalCategory = categoryRepository.findByName(dto.getCategory().getName());
        Category category = optionalCategory.orElseGet(() -> {
            Category newCategory = new Category();
            newCategory.setName(dto.getCategory().getName());
            categoryRepository.saveAndFlush(newCategory);
            return newCategory;
        });



        // Create the product
        Product product = new Product();
        product.setName(dto.getName());
        product.setSellPrice(dto.getPrice());
        product.setDescription(dto.getDescription());
        product.setImageUrl(dto.getImage());
        product.setStore(store);
        product.setCategory(category);
        product.setBarcode(dto.getBarcode());
        product.setQuantity(dto.getQuantity());
        product.setSellPrice(dto.getSellPrice());
        product.setBoughtPrice(dto.getBoughtPrice());

        // Save the product
        productRepository.save(product);

        return ResponseEntity.ok(product);
    }




    // Example of an endpoint to update product price
    @PutMapping("/products/{id}")
    public Product updateProductPrice(@PathVariable Long id, @RequestParam Double price) {
        logger.info("Received request to update price of product with id {} to {}", id, price);
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setSellPrice(price);
        productRepository.save(product);
        logger.info("Successfully updated price for product with id {} to {}", id, price);
        return product;
    }
    @GetMapping("/products")
    public List<Product> getAllProducts() {
        logger.info("Fetching all products...");
        return productRepository.findAll();
    }

   }
