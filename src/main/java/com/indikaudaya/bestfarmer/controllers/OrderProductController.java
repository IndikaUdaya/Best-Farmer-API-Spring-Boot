package com.indikaudaya.bestfarmer.controllers;

import com.indikaudaya.bestfarmer.dto.*;
import com.indikaudaya.bestfarmer.entities.*;
import com.indikaudaya.bestfarmer.services.ProductItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/product-order")
public class OrderProductController {

    @Autowired
    ProductItemService productItemService;

    @GetMapping("/all/{sid}")
    public List<ProductDTO> getAllOrders(@PathVariable Long sid) {

        List<ProductOrderItem> orderList = productItemService.getAllOrdersBySellerId(sid);

        List<ProductDTO> dtos = new ArrayList<>();

        orderList.forEach(order -> {

            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setOrderId(order.getId());
            orderDTO.setDeliveryStatus(order.getOrder().isDeliveryStatus());

            User buyer = order.getOrder().getBuyer();
            UserDTO userDTO = new UserDTO();
            userDTO.setId(buyer.getId());
            userDTO.setEmail(buyer.getEmail());
            orderDTO.setBuyer(userDTO);

            Invoice invoice = order.getOrder().getInvoice();
            InvoiceDTO invoiceDTO = new InvoiceDTO();
            invoiceDTO.setInvoiceNumber(invoice.getInvoiceNumber());
            orderDTO.setInvoice(invoiceDTO);

            List<ProductOrderItem> orderItems = order.getOrder().getOrderItems();
            List<ProductOrderItemDTO> orderItemDTOS = new ArrayList<>();
            orderItems.forEach(productOrderItem -> {
                ProductOrderItemDTO itemDTO = new ProductOrderItemDTO();
                itemDTO.setProductId(productOrderItem.getId());
                itemDTO.setQuantity(productOrderItem.getQuantity());
                orderItemDTOS.add(itemDTO);
            });

            orderDTO.setOrderItems(orderItemDTOS);

            ProductDTO productDTO = new ProductDTO();

            Product p = order.getProduct();

            ArrayList<ProductImageDTO> pi = new ArrayList<>();
            p.getProductImages().forEach(productImage -> {
                pi.add(new ProductImageDTO(productImage.getPath()));
            });

            productDTO.setProductImages(pi);
            productDTO.setId(p.getId());
            productDTO.setCategory(new CategoryDTO(p.getCategory().getId(), p.getCategory().getName()));
            productDTO.setName(p.getName());
            productDTO.setDescription(p.getDescription());
            productDTO.setQty(p.getQty());
            productDTO.setPrice(p.getPrice());
            productDTO.setType(p.getType());
            productDTO.setUnit(p.getUnit());
            productDTO.setDeliveryAvailable(p.isDeliveryAvailable());

            UserDTO seller = new UserDTO();
            seller.setId(p.getSeller().getId());
            seller.setEmail(p.getSeller().getEmail());
            seller.setMobile(p.getSeller().getMobile());

            productDTO.setSeller(seller);
            productDTO.setOrderId(order.getOrder().getId());

            productDTO.setOrderDate(order.getOrder().getOrderDate());
            productDTO.setProductDelivered(order.getOrder().isDeliveryStatus());
            productDTO.setOrderQty(order.getQuantity());

            dtos.add(productDTO);
        });

        return dtos;
    }



}
