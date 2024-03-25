package com.indikaudaya.bestfarmer.repositories;

import com.indikaudaya.bestfarmer.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DefaultProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p INNER JOIN User  u ON u.id=p.seller.id INNER JOIN SellerReview  sr ON u.id=sr.seller.id AND p.id=sr.product.id WHERE p.status=:pStatus")
    List<Product> getProduct(boolean pStatus);

    @Query("SELECT p FROM Product p WHERE (:cateName IS NULL OR p.category.name = :cateName) AND LOWER(p.name) LIKE LOWER(CONCAT('%', :searchWord, '%')) AND p.qty>0 AND p.status=true")
    List<Product> searchByWordAndCateName(String cateName, String searchWord);

    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :searchWord, '%')) AND p.qty>0 AND p.status=true ")
    List<Product> searchByProductName(String searchWord);

    @Query("SELECT p FROM Product p WHERE LOWER(p.category.name)=LOWER(:cateName) AND p.qty>0 AND p.status=true")
    List<Product> searchByCategoryName(String cateName);

}
