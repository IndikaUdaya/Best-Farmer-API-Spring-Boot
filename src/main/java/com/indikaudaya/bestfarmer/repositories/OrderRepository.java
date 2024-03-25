package com.indikaudaya.bestfarmer.repositories;

import com.indikaudaya.bestfarmer.entities.Order;
import com.indikaudaya.bestfarmer.entities.ProductOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.ResultSet;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o INNER JOIN ProductOrderItem poi ON o.id=poi.order.id INNER JOIN Product p ON p.id=poi.product.id WHERE o.buyer.id=:bid order by o.orderDate DESC ")
    List<Order> getOrdersByBuyerID(Long bid);

    @Query("SELECT SUM( po.quantity * p.price) FROM Product p INNER JOIN ProductOrderItem po ON p.id=po.product.id INNER JOIN Order o ON o.id=po.order.id  WHERE o.orderDate BETWEEN :startDate AND :endDate")
    Double getAllProductWithProductOrder(String startDate,String endDate);


}
