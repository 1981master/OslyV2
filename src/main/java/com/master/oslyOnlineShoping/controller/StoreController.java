package com.master.oslyOnlineShoping.controller;

import com.master.oslyOnlineShoping.entity.products.Store;
import com.master.oslyOnlineShoping.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
public class StoreController {

    @Autowired
    private StoreRepository storeRepository;

    // Fetch all stores
    @GetMapping
    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    // Add a new store
    @PostMapping
    public Store addStore(@RequestBody Store store) {
        return storeRepository.save(store);
    }
}
