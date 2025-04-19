package com.master.oslyOnlineShoping.repository;

import com.master.oslyOnlineShoping.entity.products.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findByName(String storeName);
}
