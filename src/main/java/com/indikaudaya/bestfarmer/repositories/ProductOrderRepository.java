package com.indikaudaya.bestfarmer.repositories;

import com.indikaudaya.bestfarmer.entities.Order;
import com.indikaudaya.bestfarmer.entities.ProductOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductOrderRepository extends JpaRepository<ProductOrderItem, Long> {

    @Query("SELECT o FROM ProductOrderItem o WHERE o.product.seller.id=:id")
    List<ProductOrderItem> getProductOrderBySellerID(long id);

}
