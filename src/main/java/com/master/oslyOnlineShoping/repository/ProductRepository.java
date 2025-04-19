package com.master.oslyOnlineShoping.repository;

import com.master.oslyOnlineShoping.entity.products.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // Custom query to find products by name
    List<Product> findByNameContainingIgnoreCase(String name);

    // Custom query to find products by barcode
    List<Product> findByBarcode(String barcode);

    // Custom query to find products by store ID
    List<Product> findByStoreId(Long storeId);

    // You can add more custom methods as needed
}
