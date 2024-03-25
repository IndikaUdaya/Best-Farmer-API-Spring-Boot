package com.indikaudaya.bestfarmer.services;

import com.indikaudaya.bestfarmer.entities.Product;
import com.indikaudaya.bestfarmer.repositories.DefaultProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultProductService {

    @Autowired
    DefaultProductRepository defaultProductRepository;

    public List<Product> getAllProduct() {
        return defaultProductRepository.getProduct(true);
    }

    public Product getProductById(long productId) {
        return defaultProductRepository.getReferenceById(productId);
    }

    public List<Product> getAllProductBySearch(String cateName, String searchWord) {
        return defaultProductRepository.searchByWordAndCateName(cateName, searchWord);
    }

    public List<Product> getAllProductName(String searchWord) {
        return defaultProductRepository.searchByProductName( searchWord);
    }

    public List<Product> getAllProductByCategoryName(String categoryName) {
        return defaultProductRepository.searchByCategoryName( categoryName);
    }
}
