package com.example.demo.repositories;

import com.example.demo.domain.ProductImage;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductImageRepository extends CrudRepository<ProductImage, Integer> {
    Optional<ProductImage> findByName(String fileName);
}
