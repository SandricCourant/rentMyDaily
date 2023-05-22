package com.example.demo.services.impl;

import com.example.demo.domain.ProductImage;
import com.example.demo.repositories.ProductImageRepository;
import com.example.demo.services.ProductImageService;
import com.example.demo.utilities.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
@Service
public class ProductImageServiceImpl implements ProductImageService {
    @Autowired
    private ProductImageRepository imageRepository;
    @Override
    public ProductImage uploadImage(MultipartFile file) throws IOException {

        ProductImage pImage = new ProductImage();
        pImage.setName(file.getOriginalFilename());
        pImage.setType(file.getContentType());
        pImage.setImageData(ImageUtil.compressImage(file.getBytes()));

        return imageRepository.save(pImage);
    }

    @Override
    public byte[] downloadImage(String fileName) {

        Optional<ProductImage> imageData = imageRepository.findByName(fileName);

        return ImageUtil.decompressImage(imageData.get().getImageData());
    }
}
