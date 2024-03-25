package com.indikaudaya.bestfarmer.repositories;

import com.indikaudaya.bestfarmer.entities.Product;
import com.indikaudaya.bestfarmer.entities.ProductImage;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.seller.id=:sellerId")
    List<Product> getProductsBySellerID(Long sellerId);

    @Query("SELECT COUNT(p.id) FROM Product p")
    Integer getProductCount();

    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.status=:status WHERE p.seller.email=:email")
    int updateProductStatus(String email,boolean status);

    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.status=:status WHERE p.id=:pId")
    int updateProductStatusById(long pId,boolean status);

    List<Product> findByQtyGreaterThan(double qty);

    @Query("SELECT p FROM Product p WHERE lower(p.name) LIKE LOWER(CONCAT('%', :searchWord, '%')) AND p.status=:status")
    List<Product> searchByWordAndStatus(String searchWord, boolean status);

    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :searchWord, '%')) AND p.qty>0")
    List<Product> searchByWord(String searchWord);

    @Query("SELECT p FROM Product p WHERE p.status=:status")
    List<Product> searchByStatus(boolean status);

}



