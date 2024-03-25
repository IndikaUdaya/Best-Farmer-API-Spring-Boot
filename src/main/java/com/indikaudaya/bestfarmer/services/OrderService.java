package com.indikaudaya.bestfarmer.services;

import com.indikaudaya.bestfarmer.entities.Order;
import com.indikaudaya.bestfarmer.entities.Product;
import com.indikaudaya.bestfarmer.entities.ProductOrderItem;
import com.indikaudaya.bestfarmer.repositories.OrderRepository;
import com.indikaudaya.bestfarmer.repositories.ProductOrderRepository;
import com.indikaudaya.bestfarmer.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    public Order saveOrder(Order order) {
        Order savedOrder = orderRepository.save(order);
        List<ProductOrderItem> orderItems = savedOrder.getOrderItems();

        List<Product> updateProducts = new ArrayList<>();
        orderItems.forEach(poi -> {
            Long productId = poi.getProduct().getId();
            Optional<Product> optionalProduct = productRepository.findById(productId);

            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                product.setQty(product.getQty() - poi.getQuantity());
                updateProducts.add(product);
            } else {
//                throw new ProductNotFoundException("Product with ID " + productId + " not found");
            }
        });

        productRepository.saveAll(updateProducts);
        return savedOrder;
    }

    public Order updateOrderStatus(Order order) {
        Long orderId = order.getId();
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found with id: " + orderId));

        existingOrder.setDeliveryStatus(true);

        return orderRepository.save(existingOrder);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Product> getAllOrdersByBuyerId(Long bid) {
        List<Order> ordersByBuyerID = orderRepository.getOrdersByBuyerID(bid);

        List<Product> products=new ArrayList<>();

        ordersByBuyerID.forEach(order -> {
            order.getOrderItems().forEach(productOrderItem -> {
                Product product = productOrderItem.getProduct();
                products.add(product);
            });

        });
        return products;
    }

}
