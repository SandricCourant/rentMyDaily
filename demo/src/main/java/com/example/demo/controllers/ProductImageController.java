package com.example.demo.controllers;

import com.example.demo.domain.ProductImage;
import com.example.demo.dto.ProductImageDto;
import com.example.demo.services.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class ProductImageController {

    @Autowired
    private ProductImageService productImageService;

    @PostMapping("/upload")
    public ResponseEntity<ProductImageDto> uploadImage(@RequestParam("productImage")MultipartFile file) throws IOException{
        ProductImageDto response = new ProductImageDto();
        ProductImage image = productImageService.uploadImage(file);

        response.setName(image.getName());
        response.setType(image.getType());
        response.setImageData(image.getImageData());

        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.valueOf("image/png")).body(response);
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable String fileName) {
        byte[] image = productImageService.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(image);
    }
}
