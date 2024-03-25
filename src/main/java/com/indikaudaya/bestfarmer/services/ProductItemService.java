package com.indikaudaya.bestfarmer.services;

import com.indikaudaya.bestfarmer.entities.Order;
import com.indikaudaya.bestfarmer.entities.ProductOrderItem;
import com.indikaudaya.bestfarmer.repositories.ProductOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductItemService {

    @Autowired
    ProductOrderRepository orderRepository;
    public List<ProductOrderItem> getAllOrdersBySellerId(Long sid) {
        return orderRepository.getProductOrderBySellerID(sid);
    }

}
