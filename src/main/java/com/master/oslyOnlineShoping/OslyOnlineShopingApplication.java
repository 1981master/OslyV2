package com.master.oslyOnlineShoping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.master.oslyOnlineShoping.entity", "com.master.oslyOnlineShoping.entity.products"})
public class OslyOnlineShopingApplication {

	public static void main(String[] args) {
		SpringApplication.run(OslyOnlineShopingApplication.class, args);
	}

}
