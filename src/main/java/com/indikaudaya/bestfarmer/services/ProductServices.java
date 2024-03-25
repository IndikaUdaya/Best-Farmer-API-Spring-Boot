package com.indikaudaya.bestfarmer.services;

import com.indikaudaya.bestfarmer.dto.ProductDTO;
import com.indikaudaya.bestfarmer.dto.ProductImageDTO;
import com.indikaudaya.bestfarmer.entities.Category;
import com.indikaudaya.bestfarmer.entities.Product;
import com.indikaudaya.bestfarmer.entities.ProductImage;
import com.indikaudaya.bestfarmer.repositories.ProductImageRepository;
import com.indikaudaya.bestfarmer.repositories.ProductRepository;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServices {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findByQtyGreaterThan(0);
    }

    public List<Product> getProductBySellerId(Long sellerId) {
        return productRepository.getProductsBySellerID(sellerId);
    }

    public Product updateProduct(Product product) {

        Long productId = product.getId();
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found with id: " + productId));

        existingProduct.setName(product.getName());
        existingProduct.setProductImages(product.getProductImages());
        existingProduct.setType(product.getType());
        existingProduct.setUnit(product.getUnit());
        existingProduct.setQty(product.getQty());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setDeliveryAvailable(product.isDeliveryAvailable());

        return productRepository.save(product);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }


    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }


}
