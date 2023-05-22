package com.example.demo.services;

import com.example.demo.domain.ProductImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductImageService {

    ProductImage uploadImage(MultipartFile file) throws IOException;

    byte[] downloadImage(String fileName);
}
