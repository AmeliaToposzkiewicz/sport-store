package com.example.sportstore.service;

import com.example.sportstore.dto.ProductDto;
import com.example.sportstore.entity.Product;
import com.example.sportstore.exception.ObjectNotFoundException;
import com.example.sportstore.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void addProduct(ProductDto productDto, MultipartFile multipartFile) {
        Product product = new Product(productDto.getName(), productDto.getDescription(), productDto.getImage(),
                BigDecimal.valueOf(Double.parseDouble(productDto.getPrice())));
        if (productDto.getId() != null) {
            product.setId(product.getId());
        }
        productRepository.save(product);
        saveProductImage(multipartFile);
    }

    private void saveProductImage(MultipartFile multipartFile) {
        Path uploads = Paths.get("./uploads");
        try {
            if (multipartFile != null && multipartFile.getOriginalFilename() != null) {
                Files.copy(multipartFile.getInputStream(), uploads.resolve(multipartFile.getOriginalFilename()));
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public List<ProductDto> getProducts() {
        return productRepository.findAll().stream().map(p -> new ProductDto(p.getId(), p.getName(), p.getDescription(),
                p.getImage(), p.getPrice().toString())).toList();
    }

    public ProductDto getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ObjectNotFoundException("Product with id: " + productId + " not found"));
        return new ProductDto(product.getId(), product.getName(), product.getDescription(),
                product.getImage(), product.getPrice().toString());
    }

    public void removeProductById(Long productId) {
        productRepository.deleteById(productId);
    }
}
