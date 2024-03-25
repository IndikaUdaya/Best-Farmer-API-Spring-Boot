package com.indikaudaya.bestfarmer.services;

import com.indikaudaya.bestfarmer.dto.ProductDTO;
import com.indikaudaya.bestfarmer.dto.SellerReviewDTO;
import com.indikaudaya.bestfarmer.dto.UserDTO;
import com.indikaudaya.bestfarmer.entities.Product;
import com.indikaudaya.bestfarmer.entities.SellerReview;
import com.indikaudaya.bestfarmer.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServices {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    private SellerReviewRepository sellerReviewRepository;


    public Integer getSellerCount(String userType) {
        return userRepository.findAllByUserType(userType);
    }

    public Integer getAllProductCount() {
        return productRepository.getProductCount();
    }

    public Double getProfitThisMonth(String startDate, String endDate) {
        return orderRepository.getAllProductWithProductOrder(startDate, endDate);
    }

    public int updateProductStatus(String sellerEmail, boolean status) {
        return productRepository.updateProductStatus(sellerEmail, status);
    }

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public int updateProductStatusById(Long pid, boolean status) {
        productRepository.findById(pid)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found with id: " + pid));
        return productRepository.updateProductStatusById(pid, status);
    }

    public List<SellerReview> getAllReviewById(long id) {
        return sellerReviewRepository.getAllBySellerReview(id);
    }

    public List<Product> getAllProductBySearch(String searchWord, boolean status) {
        return productRepository.searchByWordAndStatus(searchWord, status);
    }

    public List<Product> getAllProductName(String searchWord ) {
        return productRepository.searchByWord(searchWord);
    }

    public List<Product> getAllProductByStatus(boolean status) {
        return productRepository.searchByStatus(status);
    }

}
