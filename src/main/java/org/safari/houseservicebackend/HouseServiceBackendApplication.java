package org.safari.houseservicebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class HouseServiceBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(HouseServiceBackendApplication.class, args);
    }
}
